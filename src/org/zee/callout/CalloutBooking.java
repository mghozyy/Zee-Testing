package org.zee.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.zee.model.X_C_Book;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartner;

public class CalloutBooking extends CalloutEngine implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
		if (mField.getColumnName().equals("C_B" + "Partner_ID")) {
			return this.calloutCBPartner(ctx, WindowNo, mTab, mField, value);
		}
			
		return null;
		
	}
	
	
	public String calloutCBPartner(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		Integer C_BPartner_ID = (Integer) value;
		if (C_BPartner_ID == null || C_BPartner_ID.intValue() == 0) {
			return "";
		}

	
		MBPartner bp = new MBPartner(ctx, C_BPartner_ID, null);
		
		mTab.setValue(X_C_Book.COLUMNNAME_Description, bp.getName());
		return "";
	}

} 
	
	



