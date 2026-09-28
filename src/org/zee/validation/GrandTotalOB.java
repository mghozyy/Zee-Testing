package org.zee.validation;

import java.math.BigDecimal;

import org.adempiere.base.event.IEventTopics;
import org.zee.model.X_Order_Book;
import org.zee.model.X_Order_Book_Line;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.osgi.service.event.Event;


public class GrandTotalOB {
	
	public static CLogger log = CLogger.getCLogger(GrandTotalOB.class);
	
	public static String executeOrderEvent(Event event, PO po) {

		String msgPay = "";
		X_Order_Book_Line obl = (X_Order_Book_Line) po;
		
		if(event.getTopic().equals(IEventTopics.PO_AFTER_NEW)) {
			msgPay = TotalPaymentNew(obl);
			}else if (event.getTopic().equals(IEventTopics.PO_AFTER_CHANGE)) {
				msgPay = TotalPaymentChange(obl);
			}else if (event.getTopic().equals(IEventTopics.PO_AFTER_DELETE)) {
				msgPay = TotalPaymentDelete(obl);

			}
		return msgPay;
}
	
	private static String TotalPaymentDelete(X_Order_Book_Line obl) {
		// TODO Auto-generated method stub
		String rt = "";
		int Order_Book_ID = (int) obl.get_Value("Order_Book_ID");
		
		X_Order_Book header = new X_Order_Book(obl.getCtx(), Order_Book_ID, obl.get_TrxName());	
		
		int Order_Book_Line_ID = obl.getOrder_Book_ID();
		BigDecimal totalline = obl.getLineNetAmt();
		BigDecimal totalheader = header.getGrandTotal();
		
		StringBuilder SQLGetDocType = new StringBuilder();
		SQLGetDocType.append("SELECT sum(LineNetAmt) ");
		SQLGetDocType.append("FROM Order_Book_Line ");
		SQLGetDocType.append(" WHERE Order_Book_ID="+Order_Book_ID);
		SQLGetDocType.append(" and Order_Book_Line_ID not in ("+Order_Book_Line_ID+")");
		
		BigDecimal pay = DB.getSQLValueBDEx(obl.get_TrxName(), SQLGetDocType.toString());
		
		if (pay == null) {
			pay = Env.ZERO;
		}

		BigDecimal total = pay;
		header.setGrandTotal(total);
		header.saveEx();
		
		
		return rt;
	}

	private static String TotalPaymentChange(X_Order_Book_Line obl) {
		// TODO Auto-generated method stub
		String rt = "";
		int Order_Book_ID = (int) obl.get_Value("Order_Book_ID");
		
		X_Order_Book header = new X_Order_Book(obl.getCtx(), Order_Book_ID, obl.get_TrxName());	
		
		int Order_Book_Line_ID = obl.getOrder_Book_ID();
		BigDecimal totalline = obl.getLineNetAmt();
		BigDecimal totalheader = header.getGrandTotal();
		
		StringBuilder SQLGetDocType = new StringBuilder();
		SQLGetDocType.append("SELECT sum(LineNetAmt) ");
		SQLGetDocType.append("FROM Order_Book_Line ");
		SQLGetDocType.append(" WHERE Order_Book_ID="+Order_Book_ID);
		SQLGetDocType.append(" and Order_Book_Line_ID not in ("+Order_Book_Line_ID+")");
		
		BigDecimal pay = DB.getSQLValueBDEx(obl.get_TrxName(), SQLGetDocType.toString());
		
		if (pay == null) {
			pay = Env.ZERO;
		}

		BigDecimal total = pay;
		header.setGrandTotal(total);
		header.saveEx();
		
		
		return rt;
	}
		

	private static String TotalPaymentNew(X_Order_Book_Line obl) {
		// TODO Auto-generated method stub
		String rt = "";
		int Order_Book_ID = (int) obl.get_Value("Order_Book_ID");
		
		X_Order_Book header = new X_Order_Book(obl.getCtx(), Order_Book_ID, obl.get_TrxName());	
		
		int Order_Book_Line_ID = obl.getOrder_Book_ID();
		BigDecimal totalline = obl.getLineNetAmt();
		BigDecimal totalheader = header.getGrandTotal();
		
		StringBuilder SQLGetDocType = new StringBuilder();
		SQLGetDocType.append("SELECT sum(LineNetAmt) ");
		SQLGetDocType.append("FROM Order_Book_Line ");
		SQLGetDocType.append(" WHERE Order_Book_ID="+Order_Book_ID);
		SQLGetDocType.append(" and Order_Book_Line_ID not in ("+Order_Book_Line_ID+")");
		
		BigDecimal pay = DB.getSQLValueBDEx(obl.get_TrxName(), SQLGetDocType.toString());
		
		if (pay == null) {
			pay = Env.ZERO;
		}

		BigDecimal total = pay;
		header.setGrandTotal(total);
		header.saveEx();
		
		
		return rt;
	}
}	