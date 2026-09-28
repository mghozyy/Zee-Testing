package org.zee.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MOrder;
import org.compiere.util.DB;
//import org.compiere.util.Env;


public class CalloutSalesRep_SO extends CalloutEngine implements IColumnCallout   {



	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		if (mField.getColumnName().equals("C_B" + "Partner_ID")) {
			return this.calloutCBPartner(ctx, WindowNo, mTab, mField, value);
		}
		return null;
	}

	private String calloutCBPartner(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {
		// TODO Auto-generated method stub
		Integer C_BPartner_ID = (Integer) value;
		if (C_BPartner_ID == null || C_BPartner_ID.intValue() == 0) {
			return "";}
		
		StringBuilder SQLGetDocType = new StringBuilder();
		SQLGetDocType.append("SELECT SalesRep_ID");
		SQLGetDocType.append(" FROM CustomRep");
		SQLGetDocType.append(" WHERE C_BPartner_ID="+ C_BPartner_ID);

		Integer CustomRep_ID = DB.getSQLValueEx(null, SQLGetDocType.toString());
		
		mTab.setValue(MOrder.COLUMNNAME_SalesRep_ID, CustomRep_ID );

		
		return null;
	}

	
	}
