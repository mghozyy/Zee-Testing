package org.zee.process;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.compiere.model.MUser;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * REST API Endpoint Servlet for C_Invoice Reporting (with HTTP Basic Auth Security).
 * 
 * Authentication:
 * - Basic Auth Header (Authorization: Basic <base64(username:password)>) -> WAJIB
 * 
 * Query Parameters (Mandatory):
 * - client : Client ID or Client Search Key (Value) -> WAJIB
 * - organization : Organization ID or Org Search Key (Value) -> WAJIB
 * - documentDateStart : Tanggal Mulai Dokumen Invoice (YYYY-MM-DD) -> WAJIB
 * - documentDateEnd : Tanggal Akhir Dokumen Invoice (YYYY-MM-DD) -> WAJIB
 * 
 * Query Parameters (Optional):
 * - businessPartner : Business Partner ID or BPartner Search Key (Value) -> OPSIONAL
 * - docStatus : Document Status (default: 'CO', 'CL')
 * 
 * @author Zee
 */
public class InvoiceApiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final CLogger log = CLogger.getCLogger(InvoiceApiServlet.class);

	/**
	 * Helper class to force org.json.JSONObject to maintain key insertion order via LinkedHashMap.
	 */
	public static class OrderedJSONObject extends JSONObject {
		public OrderedJSONObject() {
			try {
				Field mapField = JSONObject.class.getDeclaredField("map");
				mapField.setAccessible(true);
				mapField.set(this, new LinkedHashMap<String, Object>());
			} catch (Exception e) {
				// Fallback to standard
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Set Header Respons REST JSON & CORS
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");

		PrintWriter out = resp.getWriter();
		OrderedJSONObject jsonResponse = new OrderedJSONObject();

		// Handle OPTIONS request for CORS preflight
		if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
			resp.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		try {
			// 1. Validasi HTTP Basic Authentication (Username & Password iDempiere)
			String authHeader = req.getHeader("Authorization");
			if (authHeader == null || !authHeader.toUpperCase().startsWith("BASIC ")) {
				resp.setHeader("WWW-Authenticate", "Basic realm=\"iDempiere REST API\"");
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				jsonResponse.put("status", "error");
				jsonResponse.put("code", 401);
				jsonResponse.put("message", "Otentikasi Gagal: Header 'Authorization' (Basic Auth) wajib diisi.");
				out.print(jsonResponse.toString());
				return;
			}

			// Dekode Base64 Kredensial
			String base64Credentials = authHeader.substring(6).trim();
			String credentials = "";
			try {
				byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
				credentials = new String(decodedBytes, StandardCharsets.UTF_8);
			} catch (Exception e) {
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				jsonResponse.put("status", "error");
				jsonResponse.put("code", 401);
				jsonResponse.put("message", "Otentikasi Gagal: Format Base64 pada Header Authorization tidak valid.");
				out.print(jsonResponse.toString());
				return;
			}

			String[] values = credentials.split(":", 2);
			if (values.length < 2) {
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				jsonResponse.put("status", "error");
				jsonResponse.put("code", 401);
				jsonResponse.put("message", "Otentikasi Gagal: Format kredensial harus 'username:password'.");
				out.print(jsonResponse.toString());
				return;
			}

			String username = values[0];
			String password = values[1];

			// Verifikasi Kredensial Pengguna ke iDempiere AD_User
			MUser user = MUser.get(Env.getCtx(), username, password);
			if (user == null || !user.isActive()) {
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				jsonResponse.put("status", "error");
				jsonResponse.put("code", 401);
				jsonResponse.put("message", "Otentikasi Gagal: Username atau Password iDempiere tidak valid / akun tidak aktif.");
				out.print(jsonResponse.toString());
				return;
			}

			// 2. Ekstraksi Parameter Input
			String paramClient = getParam(req, "client", "ad_client_id");
			String paramOrg = getParam(req, "organization", "ad_org_id", "org");
			String paramDocDateStart = getParam(req, "documentDateStart", "documentDate_start", "document_date_start", "dateFrom", "startDate");
			String paramDocDateEnd = getParam(req, "documentDateEnd", "documentDate_end", "document_date_end", "dateTo", "endDate");
			String paramBPartner = getParam(req, "businessPartner", "business_partner", "c_bpartner_id", "bpartner");
			String paramDocStatus = getParam(req, "docStatus", "doc_status");

			// 3. Validasi Parameter Mandatory
			if (paramClient == null || paramClient.trim().isEmpty()) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				jsonResponse.put("status", "error");
				jsonResponse.put("code", 400);
				jsonResponse.put("message", "Parameter mandatory 'client' wajib diisi (Contoh: client=1000000 atau client=GardenWorld).");
				out.print(jsonResponse.toString());
				return;
			}

			if (paramOrg == null || paramOrg.trim().isEmpty()) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				jsonResponse.put("status", "error");
				jsonResponse.put("code", 400);
				jsonResponse.put("message", "Parameter mandatory 'organization' wajib diisi (Contoh: organization=1000000 atau organization=HQ).");
				out.print(jsonResponse.toString());
				return;
			}

			if (paramDocDateStart == null || paramDocDateStart.trim().isEmpty()) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				jsonResponse.put("status", "error");
				jsonResponse.put("code", 400);
				jsonResponse.put("message", "Parameter mandatory 'documentDateStart' wajib diisi dengan format YYYY-MM-DD (Contoh: documentDateStart=2025-09-01).");
				out.print(jsonResponse.toString());
				return;
			}

			if (paramDocDateEnd == null || paramDocDateEnd.trim().isEmpty()) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				jsonResponse.put("status", "error");
				jsonResponse.put("code", 400);
				jsonResponse.put("message", "Parameter mandatory 'documentDateEnd' wajib diisi dengan format YYYY-MM-DD (Contoh: documentDateEnd=2025-09-28).");
				out.print(jsonResponse.toString());
				return;
			}

			// Parsing Timestamp Tanggal
			Timestamp tsStart = parseTimestampStart(paramDocDateStart.trim());
			Timestamp tsEnd = parseTimestampEnd(paramDocDateEnd.trim());

			if (tsStart == null || tsEnd == null) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				jsonResponse.put("status", "error");
				jsonResponse.put("code", 400);
				jsonResponse.put("message", "Format tanggal harus YYYY-MM-DD (Contoh: 2025-09-01).");
				out.print(jsonResponse.toString());
				return;
			}

			// 4. Membangun SQL Query Dinamis
			StringBuilder sqlWhere = new StringBuilder(" WHERE inv.IsActive = 'Y' ");
			List<Object> params = new ArrayList<>();

			// Filter Mandatory Client
			if (isInteger(paramClient)) {
				sqlWhere.append(" AND inv.AD_Client_ID = ? ");
				params.add(Integer.parseInt(paramClient));
			} else {
				sqlWhere.append(" AND client.Value = ? ");
				params.add(paramClient.trim());
			}

			// Filter Mandatory Organization
			if (isInteger(paramOrg)) {
				sqlWhere.append(" AND inv.AD_Org_ID = ? ");
				params.add(Integer.parseInt(paramOrg));
			} else {
				sqlWhere.append(" AND ao.Value = ? ");
				params.add(paramOrg.trim());
			}

			// Filter Mandatory Document Date Range
			sqlWhere.append(" AND inv.DateInvoiced >= ? ");
			params.add(tsStart);

			sqlWhere.append(" AND inv.DateInvoiced <= ? ");
			params.add(tsEnd);

			// Filter Business Partner (OPSIONAL)
			if (paramBPartner != null && !paramBPartner.trim().isEmpty()) {
				if (isInteger(paramBPartner)) {
					sqlWhere.append(" AND inv.C_BPartner_ID = ? ");
					params.add(Integer.parseInt(paramBPartner));
				} else {
					sqlWhere.append(" AND bpa.Value = ? ");
					params.add(paramBPartner.trim());
				}
			}

			// Filter Status Dokumen (Default: 'CO', 'CL')
			if (paramDocStatus != null && !paramDocStatus.trim().isEmpty()) {
				sqlWhere.append(" AND inv.DocStatus = ? ");
				params.add(paramDocStatus.trim());
			} else {
				sqlWhere.append(" AND inv.DocStatus IN ('CO', 'CL') ");
			}

			// SQL Header Query
			StringBuilder sqlHeader = new StringBuilder();
			sqlHeader.append("SELECT inv.C_Invoice_ID, ");
			sqlHeader.append("client.Name AS client_name, ");
			sqlHeader.append("ao.Name AS organization, ");
			sqlHeader.append("inv.DocumentNo AS documentno, ");
			sqlHeader.append("inv.Description AS headerdescription, ");
			sqlHeader.append("inv.DateAcct AS dateinvoiced, ");
			sqlHeader.append("bpa.Name AS customer_name, ");
			sqlHeader.append("loc.Address1 || COALESCE(' ' || loc.Address2, '') || COALESCE(' ' || loc.Address3, '') || ");
			sqlHeader.append("COALESCE(' ' || loc.Address4, '') || COALESCE(' ' || loc.Address5, '') || ");
			sqlHeader.append("COALESCE(' ' || ctr.Name, '') AS customer_address, ");
			sqlHeader.append("trm.Name AS payment_term, ");
			sqlHeader.append("inv.TotalLines AS totamt, ");
			sqlHeader.append("ROUND(inv.GrandTotal - inv.TotalLines, 2) AS taxamt, ");
			sqlHeader.append("CASE WHEN bpa.Value = 'CK' THEN FLOOR(inv.GrandTotal) ELSE inv.GrandTotal END AS invamt, ");
			sqlHeader.append("CASE WHEN inv.C_Currency_ID = 303 THEN 'Amount (IDR)' ELSE 'Amount (USD)' END AS iso_code ");
			sqlHeader.append("FROM C_Invoice inv ");
			sqlHeader.append("JOIN AD_Client client ON (inv.AD_Client_ID = client.AD_Client_ID) ");
			sqlHeader.append("LEFT JOIN AD_Org ao ON (inv.AD_Org_ID = ao.AD_Org_ID) ");
			sqlHeader.append("LEFT JOIN C_BPartner bpa ON (inv.C_BPartner_ID = bpa.C_BPartner_ID) ");
			sqlHeader.append("LEFT JOIN C_BPartner_Location cbl ON (inv.C_BPartner_Location_ID = cbl.C_BPartner_Location_ID) ");
			sqlHeader.append("LEFT JOIN C_Location loc ON (cbl.C_Location_ID = loc.C_Location_ID) ");
			sqlHeader.append("LEFT JOIN C_Country ctr ON (loc.C_Country_ID = ctr.C_Country_ID) ");
			sqlHeader.append("LEFT JOIN C_PaymentTerm trm ON (inv.C_PaymentTerm_ID = trm.C_PaymentTerm_ID) ");
			sqlHeader.append(sqlWhere.toString());
			sqlHeader.append(" ORDER BY inv.DateInvoiced DESC, inv.C_Invoice_ID DESC ");

			JSONArray dataArray = new JSONArray();
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				pstmt = DB.prepareStatement(sqlHeader.toString(), null);
				for (int i = 0; i < params.size(); i++) {
					Object p = params.get(i);
					if (p instanceof Integer) {
						pstmt.setInt(i + 1, (Integer) p);
					} else if (p instanceof Timestamp) {
						pstmt.setTimestamp(i + 1, (Timestamp) p);
					} else {
						pstmt.setString(i + 1, p.toString());
					}
				}

				rs = pstmt.executeQuery();

				while (rs.next()) {
					int invoiceId = rs.getInt("C_Invoice_ID");
					String headerDesc = rs.getString("headerdescription");

					OrderedJSONObject invObj = new OrderedJSONObject();
					invObj.put("organization", rs.getString("organization"));
					invObj.put("Client", rs.getString("client_name"));
					invObj.put("c_invoice_id", invoiceId);
					invObj.put("documentno", rs.getString("documentno"));
					invObj.put("headerdescription", headerDesc);
					invObj.put("dateinvoiced", rs.getTimestamp("dateinvoiced") != null ? rs.getTimestamp("dateinvoiced").toInstant().toString() : null);
					invObj.put("customer_name", rs.getString("customer_name"));
					invObj.put("customer_address", rs.getString("customer_address"));
					invObj.put("payment_term", rs.getString("payment_term"));
					invObj.put("totamt", rs.getBigDecimal("totamt"));
					invObj.put("taxamt", rs.getBigDecimal("taxamt"));
					invObj.put("invamt", rs.getBigDecimal("invamt"));
					invObj.put("iso_code", rs.getString("iso_code"));

					// Lines Details
					JSONArray linesArray = getInvoiceLineDetails(invoiceId, headerDesc);
					invObj.put("total_lines", linesArray.length());
					invObj.put("lines", linesArray);

					dataArray.put(invObj);
				}
			} finally {
				DB.close(rs, pstmt);
			}

			// 5. Menyusun Metadata Response JSON
			jsonResponse.put("status", "success");
			jsonResponse.put("code", 200);
			jsonResponse.put("message", "Data Form Invoice C_Invoice berhasil diambil.");
			jsonResponse.put("totalRecords", dataArray.length());
			jsonResponse.put("data", dataArray);

			resp.setStatus(HttpServletResponse.SC_OK);

		} catch (Exception e) {
			log.log(Level.SEVERE, "Error in InvoiceApiServlet GET", e);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			jsonResponse.put("status", "error");
			jsonResponse.put("code", 500);
			jsonResponse.put("message", "Terjadi kesalahan internal server: " + e.getMessage());
		}

		out.print(jsonResponse.toString());
	}

	/**
	 * Helper untuk mengambil list baris/line C_InvoiceLine (Ordered Layout).
	 */
	private JSONArray getInvoiceLineDetails(int cInvoiceId, String headerDescription) throws Exception {
		JSONArray lines = new JSONArray();
		String sql = "SELECT invl.c_invoiceline_id, "
				+ "COALESCE(invl.linenetamt, 0) AS linenetamt, "
				+ "ROW_NUMBER() OVER(PARTITION BY invl.c_invoice_id ORDER BY invl.line) AS lineno, "
				+ "chr.name AS coa, "
				+ "COALESCE(invl.description, ?) AS description "
				+ "FROM c_invoiceline invl "
				+ "LEFT JOIN c_charge chr ON (invl.c_charge_id = chr.c_charge_id) "
				+ "WHERE invl.c_invoice_id = ? AND invl.isprinted = 'Y' "
				+ "ORDER BY invl.line ASC";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, headerDescription != null ? headerDescription : "");
			pstmt.setInt(2, cInvoiceId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderedJSONObject lineObj = new OrderedJSONObject();
				lineObj.put("c_invoiceline_id", rs.getInt("c_invoiceline_id"));
				lineObj.put("linenetamt", rs.getBigDecimal("linenetamt"));
				lineObj.put("lineno", rs.getInt("lineno"));
				lineObj.put("coa", rs.getString("coa"));
				lineObj.put("description", rs.getString("description"));

				lines.put(lineObj);
			}
		} finally {
			DB.close(rs, pstmt);
		}

		return lines;
	}

	private Timestamp parseTimestampStart(String dateStr) {
		try {
			if (dateStr.length() == 10) {
				dateStr = dateStr + " 00:00:00";
			}
			return Timestamp.valueOf(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	private Timestamp parseTimestampEnd(String dateStr) {
		try {
			if (dateStr.length() == 10) {
				dateStr = dateStr + " 23:59:59";
			}
			return Timestamp.valueOf(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	private String getParam(HttpServletRequest req, String... paramNames) {
		for (String name : paramNames) {
			String val = req.getParameter(name);
			if (val != null && !val.trim().isEmpty()) {
				return val.trim();
			}
		}
		return null;
	}

	private boolean isInteger(String str) {
		if (str == null) return false;
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
