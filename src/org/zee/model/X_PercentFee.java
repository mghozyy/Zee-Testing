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

/** Generated Model for PercentFee
 *  @author iDempiere (generated) 
 *  @version Release 6.2 - $Id$ */
public class X_PercentFee extends PO implements I_PercentFee, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20230727L;

    /** Standard Constructor */
    public X_PercentFee (Properties ctx, int PercentFee_ID, String trxName)
    {
      super (ctx, PercentFee_ID, trxName);
      /** if (PercentFee_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_PercentFee (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_PercentFee[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fee.
		@param Fee Fee	  */
	public void setFee (BigDecimal Fee)
	{
		set_Value (COLUMNNAME_Fee, Fee);
	}

	/** Get Fee.
		@return Fee	  */
	public BigDecimal getFee () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Fee);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Percent fee.
		@param Percent_fee Percent fee	  */
	public void setPercent_fee (BigDecimal Percent_fee)
	{
		set_Value (COLUMNNAME_Percent_fee, Percent_fee);
	}

	/** Get Percent fee.
		@return Percent fee	  */
	public BigDecimal getPercent_fee () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Percent_fee);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Percent Fee.
		@param PercentFee_ID Percent Fee	  */
	public void setPercentFee_ID (int PercentFee_ID)
	{
		if (PercentFee_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PercentFee_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PercentFee_ID, Integer.valueOf(PercentFee_ID));
	}

	/** Get Percent Fee.
		@return Percent Fee	  */
	public int getPercentFee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PercentFee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PercentFee_UU.
		@param PercentFee_UU PercentFee_UU	  */
	public void setPercentFee_UU (String PercentFee_UU)
	{
		set_ValueNoCheck (COLUMNNAME_PercentFee_UU, PercentFee_UU);
	}

	/** Get PercentFee_UU.
		@return PercentFee_UU	  */
	public String getPercentFee_UU () 
	{
		return (String)get_Value(COLUMNNAME_PercentFee_UU);
	}

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SalesRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Lines.
		@param TotalLines 
		Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines)
	{
		set_ValueNoCheck (COLUMNNAME_TotalLines, TotalLines);
	}

	/** Get Total Lines.
		@return Total of all document lines
	  */
	public BigDecimal getTotalLines () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLines);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}