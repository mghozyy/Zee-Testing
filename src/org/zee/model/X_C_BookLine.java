/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.zee.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for C_BookLine
 *  @author iDempiere (generated) 
 *  @version Release 6.2 - $Id$ */
public class X_C_BookLine extends PO implements I_C_BookLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20230712L;

    /** Standard Constructor */
    public X_C_BookLine (Properties ctx, int C_BookLine_ID, String trxName)
    {
      super (ctx, C_BookLine_ID, trxName);
      /** if (C_BookLine_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_C_BookLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_BookLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_Book getC_Book() throws RuntimeException
    {
		return (I_C_Book)MTable.get(getCtx(), I_C_Book.Table_Name)
			.getPO(getC_Book_ID(), get_TrxName());	}

	/** Set Book ID.
		@param C_Book_ID Book ID	  */
	public void setC_Book_ID (int C_Book_ID)
	{
		if (C_Book_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Book_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Book_ID, Integer.valueOf(C_Book_ID));
	}

	/** Get Book ID.
		@return Book ID	  */
	public int getC_Book_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Book_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Book Line.
		@param C_BookLine_ID Book Line	  */
	public void setC_BookLine_ID (int C_BookLine_ID)
	{
		if (C_BookLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BookLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BookLine_ID, Integer.valueOf(C_BookLine_ID));
	}

	/** Get Book Line.
		@return Book Line	  */
	public int getC_BookLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BookLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_BookLine_UU.
		@param C_BookLine_UU C_BookLine_UU	  */
	public void setC_BookLine_UU (String C_BookLine_UU)
	{
		set_ValueNoCheck (COLUMNNAME_C_BookLine_UU, C_BookLine_UU);
	}

	/** Get C_BookLine_UU.
		@return C_BookLine_UU	  */
	public String getC_BookLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_C_BookLine_UU);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Line Amount.
		@param LineNetAmt 
		Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Line Amount.
		@return Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Line.
		@param LineNo 
		Line No
	  */
	public void setLineNo (int LineNo)
	{
		set_Value (COLUMNNAME_LineNo, Integer.valueOf(LineNo));
	}

	/** Get Line.
		@return Line No
	  */
	public int getLineNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Price.
		@param PriceEntered 
		Price Entered - the price based on the selected/base UoM
	  */
	public void setPriceEntered (BigDecimal PriceEntered)
	{
		set_Value (COLUMNNAME_PriceEntered, PriceEntered);
	}

	/** Get Price.
		@return Price Entered - the price based on the selected/base UoM
	  */
	public BigDecimal getPriceEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param QtyEntered 
		The Quantity Entered is based on the selected UoM
	  */
	public void setQtyEntered (BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Quantity.
		@return The Quantity Entered is based on the selected UoM
	  */
	public BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}