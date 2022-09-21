package gov.nist.hit.wctp;

import org.xml.sax.SAXParseException;

public class Error extends Issue {

	public Error(SAXParseException e) {
		super(e);
	}

	public Error(String s, String path) {
		super(s, path);
	}
	
	public Error(String s) {
		super(s);
	}

}
