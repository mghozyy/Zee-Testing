package org.zee.model;

import java.sql.ResultSet;
import java.util.Properties;

public class BookMain extends X_C_Book {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8388862654385354651L;

	public BookMain(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public BookMain(Properties ctx, int C_Book_ID, String trxName) {
		super(ctx, C_Book_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		return super.afterSave(newRecord, success);
	}
}
