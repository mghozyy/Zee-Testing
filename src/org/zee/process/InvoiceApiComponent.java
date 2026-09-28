package org.zee.process;

import org.compiere.util.CLogger;
import org.osgi.service.http.HttpService;

/**
 * OSGi DS Component to register InvoiceApiServlet on iDempiere's Jetty HttpService.
 * 
 * @author Zee
 */
public class InvoiceApiComponent {

	private static final CLogger log = CLogger.getCLogger(InvoiceApiComponent.class);
	private HttpService httpService;

	public void bindHttpService(HttpService httpService) {
		this.httpService = httpService;
		try {
			// Register servlet at /api/v1/invoices
			httpService.registerServlet("/api/v1/invoices", new InvoiceApiServlet(), null, null);
			log.info("=======================================================================");
			log.info(">>> SUCCESS: REST API Servlet Registered at /api/v1/invoices in Jetty <<<");
			log.info("=======================================================================");
		} catch (Exception e) {
			log.severe("Failed to register InvoiceApiServlet on HttpService: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void unbindHttpService(HttpService httpService) {
		if (this.httpService == httpService) {
			try {
				httpService.unregister("/api/v1/invoices");
				log.info(">>> InvoiceApiServlet unregistered from HttpService <<<");
			} catch (Exception e) {
				log.severe("Failed to unregister InvoiceApiServlet: " + e.getMessage());
			}
			this.httpService = null;
		}
	}
}
