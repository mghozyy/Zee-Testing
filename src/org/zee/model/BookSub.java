package org.zee.model;

import java.sql.ResultSet;
import java.util.Properties;

public class BookSub extends X_C_BookLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 343641123115632524L;

	public BookSub(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public BookSub(Properties ctx, int C_BookLine_ID, String trxName) {
		super(ctx, C_BookLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	

}
