package org.zee.process;

import java.util.logging.Level;

import org.compiere.model.MBPartner;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

public class Process_UpdateDataSO extends SvrProcess{
	
	int p_C_Order_ID = 0;
	String p_Description = "";
	int p_M_Product_ID = 0;

	@Override
	protected void prepare() {


		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
						
			if (para[i].getParameter() == null)
				;		
			else if (name.equals("Description")) {
				p_Description = para[i].getParameterAsString();
			}
			else if (name.equals("M_Product_ID")) {
				p_M_Product_ID = para[i].getParameterAsInt();
			}else
			

				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
		
		p_C_Order_ID = getRecord_ID();
		
	}

	@Override
	protected String doIt() throws Exception {
		
		
		MOrder ord = new MOrder(getCtx(), p_C_Order_ID, get_TrxName());
		
		
		MBPartner bp = new MBPartner(getCtx(), ord.getC_BPartner_ID(), get_TrxName());
		
		
		ord.setDescription(bp.getDescription());
		ord.setPOReference(p_Description);
		ord.saveEx();
		

		MOrderLine line = new MOrderLine(getCtx(), 0, get_TrxName());
		
		
		line.setAD_Org_ID(ord.getAD_Org_ID());
		line.setC_Order_ID(p_C_Order_ID);
		line.setLine(10);
		line.setM_Product_ID(p_M_Product_ID);
		line.setQty(Env.ONE);
		line.setQtyEntered(Env.ONE);
		line.setQtyOrdered(Env.ONE);
		line.setPriceActual(Env.ONEHUNDRED);
		line.setPriceEntered(Env.ONEHUNDRED);
		line.setPriceLimit(Env.ONEHUNDRED);
		line.setC_Tax_ID(104);
		line.saveEx();
			
		
		return "";
	}

}