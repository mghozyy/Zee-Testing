//package org.ghozi.factories;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.adempiere.base.IColumnCallout;
//import org.adempiere.base.IColumnCalloutFactory;
//import org.compiere.model.MOrder;
//import org.ghozi.callouts.MyCallout;
//
//public class MyCalloutFactory implements IColumnCalloutFactory {
//
//	@Override
//	public IColumnCallout[] getColumnCallouts(String tableName, String columnName) {
//		// TODO Auto-generated method stub
//		
//		List<IColumnCallout> list = new ArrayList<IColumnCallout>();
//		
//		if(tableName.equalsIgnoreCase(MOrder.Table_Name) && columnName.equalsIgnoreCase(MOrder.COLUMNNAME_Description))
//			list.add(new MyCallout());
//		
//		if(tableName.equalsIgnoreCase(MOrder.Table_Name) && columnName.equalsIgnoreCase(MOrder.COLUMNNAME_AD_Org_ID))
//			list.add(new MyCallout());
//		
//		return list != null ? list.toArray(new IColumnCallout[0]): new IColumnCallout[0];
//	}
//
//}

package org.zee.factory;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.base.IColumnCallout;
import org.adempiere.base.IColumnCalloutFactory;
import org.zee.callout.CalloutBooking;
import org.zee.callout.CalloutSalesRep;
import org.zee.callout.CalloutSalesRep_SO;
import org.zee.callout.CalloutUnitPrice_LineAMT;
//import org.zee.callout.CalloutQTY_PRICE;
import org.zee.model.X_C_Book;
import org.zee.model.X_Order_Book;
import org.zee.model.X_Order_Book_Line;
//import org.zee.model.X_C_BookLine;
import org.compiere.model.MOrder;

/**
 * 
 * @author Tegar N
 *
 */

public class MyCalloutFactory implements IColumnCalloutFactory {

	@Override
	public IColumnCallout[] getColumnCallouts(String tableName,
			String columnName) {
		
		List<IColumnCallout> list = new ArrayList<IColumnCallout>();
		
		if (tableName.equals(X_C_Book.Table_Name)){
			list.add (new CalloutBooking());
		}
		else if (tableName.equals(X_Order_Book.Table_Name)){
			list.add (new CalloutSalesRep());
		}
		else if (tableName.equals(X_Order_Book_Line.Table_Name)){
			list.add (new CalloutUnitPrice_LineAMT());
//			list.add (new CallOutGrandTotal());
		}
		else if (tableName.equals(MOrder.Table_Name)){
			list.add (new CalloutSalesRep_SO());
		}
//		else if (tableName.equals(X_Order_Book_Line.Table_Name)){
//			list.add(new CallOutGrandTotal());
//		}

		
		return list != null ? list.toArray(new IColumnCallout[0])
				: new IColumnCallout[0];
	}

}
