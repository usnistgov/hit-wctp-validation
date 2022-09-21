package gov.nist.hit.wctp;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SchemaErrorHandler implements ErrorHandler {
	
	private boolean validation = true;
	private Report messageReport;
	private WCTPReport wctpreport;
	private String category;
	
	public SchemaErrorHandler(Report messageReport, WCTPReport wctpreport, String category) {
		this.messageReport = messageReport;
		this.wctpreport = wctpreport;
		this.category = category;
	}
	
	public void warning(SAXParseException exception) throws SAXException {
		messageReport.addWarning(new Warning(exception));
		wctpreport.addContentEntry(new WCTPEntry(exception.getMessage(),category,"warning"));
		//System.out.println("Warning: ");
		//System.out.println(exception.getMessage());
	}

	
	public void error(SAXParseException exception) throws SAXException {
		messageReport.addError(new Error(exception));
		wctpreport.addContentEntry(new WCTPEntry(exception.getMessage(),category,"error"));
		//System.out.println("Error: ");
		//System.out.println(exception.getMessage());
		validation = false;
	}

	
	public void fatalError(SAXParseException exception) throws SAXException {
		wctpreport.addContentEntry(new WCTPEntry(exception.getMessage(),category,"fatalerror"));
		messageReport.addFatalError(new FatalError(exception));
		//System.out.println("Fatal Error: ");
		//System.out.println(exception.getMessage());
		validation = false;
	}
	
	public boolean getValidation() {
		return validation;
	}

}
