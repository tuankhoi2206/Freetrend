package dsc.echo2app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.webcontainer.WebContainerServlet;
import nextapp.echo2.webrender.ServiceRegistry;
import nextapp.echo2.webrender.WebRenderServlet;

import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * EchoServer implementation.
 */
public class Echo2AppServlet extends WebContainerServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String	SPRING_CONTEXT	= "ctx";

	/**
	 * Logger for this class
	 */
// private static final Log logger = LogFactory.getLog(Echo2AppServlet.class);
//
// private String fApplicationName;

	private ApplicationContext	fSpringContext;

	public Echo2AppServlet() {
        super();
        ServiceRegistry serviceRegistry = WebRenderServlet.getServiceRegistry();
        serviceRegistry.add(ReportService.INSTANCE);
	}
	/**
	 * Returns the ApplicationInstance object defined within the Spring context.
	 */
	public ApplicationInstance newApplicationInstance() {
		/*
		 * Configure Log4J library and periodically monitor log4j.xml for any
		 * update.
		 */
// fApplicationName = "app";
		initApplicationContextIfNeeded();
		ApplicationInstance app = new Application();
		app.setContextProperty(SPRING_CONTEXT, fSpringContext);
		Resource res = fSpringContext.getResource("classpath:log4j.xml");
		try {
			DOMConfigurator.configureAndWatch(res.getFile().getAbsolutePath(), 10000);
		} catch (IOException e) {
			System.err.println("can not found your log configuration file [log4j.xml] in classpath");
		}
		assert app != null : "spring context init fail!";
		return (ApplicationInstance) app;
	}

	/**
	 * Initializes the Spring application context.
	 */
	private void initApplicationContextIfNeeded() {
		if (fSpringContext == null) {
			fSpringContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		}
	}
	@Override
	protected void process(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {	    
		String enc = arg0.getCharacterEncoding();
	    if (enc == null) {
	        enc = "UTF-8";
	        arg0.setCharacterEncoding(enc);
	    }
	    super.process(arg0, arg1);
	}	
}
