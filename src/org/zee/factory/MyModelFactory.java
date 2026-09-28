package org.zee.factory;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.zee.model.BookMain;
import org.zee.model.BookSub;
import org.zee.model.X_Order_Book;
import org.zee.model.X_Order_Book_Line;
import org.compiere.model.PO;
import org.compiere.util.Env;

public class MyModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {
		// TODO Auto-generated method stub
		if(tableName.equalsIgnoreCase(BookSub.Table_Name))
			return BookSub.class;
		
		if(tableName.equalsIgnoreCase(BookSub.Table_Name))
			return  BookMain.class;
		
		if(tableName.equalsIgnoreCase(X_Order_Book_Line.Table_Name))
			return  X_Order_Book_Line.class;
		
		if(tableName.equalsIgnoreCase(X_Order_Book.Table_Name))
			return  X_Order_Book.class;
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		// TODO Auto-generated method stub
		if(tableName.equalsIgnoreCase(BookSub.Table_Name))
			return new BookSub(Env.getCtx(),Record_ID, trxName);
		
		if(tableName.equalsIgnoreCase(BookSub.Table_Name))
			return new BookMain(Env.getCtx(),Record_ID, trxName);
		
		if(tableName.equalsIgnoreCase(X_Order_Book_Line.Table_Name))
			return new X_Order_Book_Line(Env.getCtx(),Record_ID, trxName);
		
		if(tableName.equalsIgnoreCase(X_Order_Book.Table_Name))
			return new X_Order_Book(Env.getCtx(),Record_ID, trxName);
		
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		// TODO Auto-generated method stub
		if(tableName.equalsIgnoreCase(BookSub.Table_Name))
			return new BookSub(Env.getCtx(),rs, trxName);
		
		if(tableName.equalsIgnoreCase(BookSub.Table_Name))
			return new BookMain(Env.getCtx(),rs, trxName);
		
		if(tableName.equalsIgnoreCase(X_Order_Book_Line.Table_Name))
			return new X_Order_Book_Line(Env.getCtx(),rs, trxName);
		
		if(tableName.equalsIgnoreCase(X_Order_Book.Table_Name))
			return new X_Order_Book(Env.getCtx(),rs, trxName);
		
		return null;
	}

}
