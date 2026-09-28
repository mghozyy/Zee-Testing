package org.zee.process;

import java.math.BigDecimal;
import java.util.logging.Level;

import org.zee.model.X_C_Book;
import org.zee.model.X_C_BookLine;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;



public class ProcessUpdate extends SvrProcess{
	
	int C_Book = 0;
	int C_BPartner_ID=0;
	
	@Override
	protected void prepare() {


		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
						
			if (para[i].getParameter() == null)
				;		
			else if (name.equals("C_BPartner_ID")) {
				C_BPartner_ID = para[i].getParameterAsInt();
				}else
			

				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
		
		C_Book = getRecord_ID();
		
	}

	@Override
	protected String doIt() throws Exception {
		
		
		X_C_Book book = new X_C_Book(getCtx(), C_Book, get_TrxName());
		
		X_C_BookLine line = new X_C_BookLine(getCtx(), 0, get_TrxName());

		
		line.setAD_Org_ID(book.getAD_Org_ID());
		line.setC_Book_ID(book.getC_Book_ID());
		line.setLineNo(1000);
		line.setM_Product_ID(line.getM_Product_ID());
		line.setDescription(book.getDescription());
		line.setQtyEntered(Env.ONEHUNDRED);
		line.setPriceEntered(new BigDecimal("1000"));
		line.saveEx();
		return null;
	}

}
		
	
//		line.setC_RentalB_ID(rntl.getC_RentalB_ID());
//		line.set_CustomColumn("Description",rntl.getDescription());
//		line.setLine(10);
//		line.setQty(Env.ONEHUNDRED);
//		line.setPriceEntered(new BigDecimal("1000"));
//		line.setC_BPartner_ID(C_BPartner_ID);
//		line.saveEx();

		
		
	