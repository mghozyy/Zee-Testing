package org.zee.callout;

import java.math.BigDecimal;
//import java.sql.ResultSet;
import java.util.Properties;
//import java.util.logging.Level;

import org.adempiere.base.IColumnCallout;
import org.zee.model.X_Order_Book_Line;
//import org.zee.model.X_Order_Book_Line;
//import org.zee.model.X_Order_Book_Line;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
//import org.compiere.model.MBPartner;
import org.compiere.model.MPayment;
import org.compiere.util.Env;



public class CalloutUnitPrice_LineAMT extends CalloutEngine implements IColumnCallout  {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		if (mField.getColumnName().equals("QtyEntered")) {
			return this.calloutQtyEntered(ctx, WindowNo, mTab, mField, value);
		}else if (mField.getColumnName().equals("PriceEntered")) {
			return this.calloutPriceEntered(ctx, WindowNo, mTab, mField, value);
		}
		return null;
	}

	private String calloutPriceEntered(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		// TODO Auto-generated method stub
		
		BigDecimal Qty = (BigDecimal) mTab.getValue("QtyEntered");
		BigDecimal Price = (BigDecimal) mTab.getValue("PriceEntered");
		
		if( Qty == null || Price == null) {
			
			return"";
		}
					
		if(Price.compareTo(Env.ZERO)>= 0 ) {
		
		BigDecimal total = Qty.multiply(Price);
		
		mTab.setValue(X_Order_Book_Line.COLUMNNAME_LineNetAmt, total);
		}
		return "";
	}


	private String calloutQtyEntered(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		// TODO Auto-generated method stub
		BigDecimal Qty = (BigDecimal) mTab.getValue("QtyEntered");
		BigDecimal Price = (BigDecimal) mTab.getValue("PriceEntered");
		
		if( Qty == null || Price == null) {
			
			return"";
		}
					
		if(Price.compareTo(Env.ZERO)>= 0 ) {
			BigDecimal total = Qty.multiply(Price);
			
			mTab.setValue(X_Order_Book_Line.COLUMNNAME_LineNetAmt, total);
		}
	
		return "";
	}


}