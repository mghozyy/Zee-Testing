package org.zee.factory;

import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.base.event.IEventTopics;
import org.adempiere.exceptions.AdempiereException;
import org.zee.model.I_Order_Book_Line;
import org.zee.model.X_Order_Book_Line;
import org.zee.validation.GrandTotalOB;
import org.zee.validation.PercentFeeSalesRep;
import org.compiere.model.I_C_Order;
import org.compiere.model.PO;
import org.compiere.model.X_C_Order;
import org.compiere.model.X_M_InOut;
import org.compiere.util.CLogger;
import org.osgi.service.event.Event;

public class MyValidationFactory extends AbstractEventHandler{

	private CLogger log = CLogger.getCLogger(MyValidationFactory.class);

	
	@Override
	protected void doHandleEvent(Event event) {
		
		log.info("ISM EVENT MANAGER // INITIALIZED");
		String msg = "";
		
		if (event.getTopic().equals(IEventTopics.AFTER_LOGIN)) {
	
		} 

		else  {
			
			if (getPO(event).get_TableName().equals(I_C_Order.Table_Name)) {
				msg = PercentFeeSalesRep.executeOrderEvent(event, getPO(event));
			}else if(getPO(event).get_TableName().equals(I_Order_Book_Line.Table_Name)) {
				msg = GrandTotalOB.executeOrderEvent(event, getPO(event));
			}
			logEvent(event, getPO(event), msg);

		}
		
	}
	@Override
	protected void initialize() {
		
		registerEvent(IEventTopics.AFTER_LOGIN);
		
		registerTableEvent(IEventTopics.DOC_BEFORE_COMPLETE, X_C_Order.Table_Name);
		registerTableEvent(IEventTopics.DOC_BEFORE_REACTIVATE, X_C_Order.Table_Name);
		registerTableEvent(IEventTopics.DOC_BEFORE_COMPLETE, X_M_InOut.Table_Name);
		registerTableEvent(IEventTopics.DOC_BEFORE_REACTIVATE, X_M_InOut.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_NEW, X_Order_Book_Line.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, X_Order_Book_Line.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_DELETE, X_Order_Book_Line.Table_Name);

	}
	
	private void logEvent (Event event, PO po, String msg) {
		log.fine("EVENT MANAGER // "+event.getTopic()+" po="+po+" MESSAGE ="+msg);
		if (msg.length()  > 0) 
			throw new AdempiereException(msg);	
	}
}

