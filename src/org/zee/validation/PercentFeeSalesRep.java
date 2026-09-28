package org.zee.validation;

import java.math.BigDecimal;

import org.adempiere.base.event.IEventTopics;
import org.zee.model.X_PercentFee;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MTab;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.osgi.service.event.Event;

//import com.sun.tools.classfile.Opcode.Set;

import jdk.jfr.Percentage;

public class PercentFeeSalesRep {
	public static CLogger log = CLogger.getCLogger(PercentFeeSalesRep.class);

	public static String executeOrderEvent(Event event, PO po) {

		String msgInOut = "";
		MOrder ord = (MOrder) po;
//		MInOut inout = (MInOut) po;

		if(ord.isSOTrx()== true) {
			if (event.getTopic().equals(IEventTopics.DOC_BEFORE_COMPLETE)) {
				msgInOut = PercentFeeSalesRepSO(ord);
			}
		}
		
		else if(ord.isSOTrx()== false){
			if (event.getTopic().equals(IEventTopics.DOC_BEFORE_COMPLETE)) {
				msgInOut = PercentFeeSalesRepPO(ord);
		}
		}
		

		return msgInOut;
	}

	public static String PercentFeeSalesRepSO ( MOrder order) {
		
		String rt = "";
		
		int c_bpartner_id = order.getC_BPartner_ID();
		int c_order_id = order.getC_Order_ID();
		int salesrep_id = order.getSalesRep_ID();
		BigDecimal totallines = order.getTotalLines();
		
		StringBuilder SQLGetDocType = new StringBuilder();
		SQLGetDocType.append("SELECT Percent_fee ");
		SQLGetDocType.append("FROM CustomRep ");
		SQLGetDocType.append(" WHERE C_BPartner_ID="+ c_bpartner_id);
		SQLGetDocType.append(" AND SalesRep_ID="+ salesrep_id);

		
		BigDecimal Percent_fee = DB.getSQLValueBDEx(order.get_TrxName(), SQLGetDocType.toString());
		
		BigDecimal prcntfee = totallines.multiply(Percent_fee.divide(Env.ONEHUNDRED));
		
		X_PercentFee fee = new X_PercentFee(order.getCtx(),0 ,order.get_TrxName());
		
		fee.setC_BPartner_ID(c_bpartner_id);
		fee.setAD_Org_ID(order.getAD_Org_ID());
		fee.setSalesRep_ID(salesrep_id);
		fee.setC_Order_ID(c_order_id);
		fee.setPercent_fee(Percent_fee);
		fee.setTotalLines(totallines);
		fee.setFee(prcntfee);
		fee.set_CustomColumn("DateOrdered",order.getDateOrdered());
		fee.saveEx();
		
		return rt;
		
		
	}
	
public static String PercentFeeSalesRepSO1 ( MOrder order) {
		
		String rt = "";
		
		int C_Order_ID = order.getC_Order_ID();
		if (C_Order_ID <=0)
			return"";
		MOrderLine[] line = order.getLines();
		for (MOrderLine orderline:line) {
			
			orderline.setDescription("Dokumen Complate SALES ORDER");
			orderline.saveEx();
			
			
			
		}
		
		
		return rt;
		
		
	}
	
public static String PercentFeeSalesRepPO ( MOrder order) {
		
		String rt = "";
		
		int C_Order_ID = order.getC_Order_ID();
		if (C_Order_ID <=0)
			return"";
		MOrderLine[] line = order.getLines();
		for (MOrderLine orderline:line) {
			
			orderline.setDescription("Dokumen Complate Purchase ORDER");
			orderline.saveEx();
			
			
			
		}
		
		
		return rt;
		
		
	}
}
//StringBuilder getPayAmt = new StringBuilder();
//getPayAmt.append("SELECT C_Payment_ID ");
//getPayAmt.append(" FROM C_Payment_Hdr ");
//getPayAmt.append(" WHERE C_Payment_Hdr_ID = ? ");