package gov.nist.hit.wctp;

import org.xml.sax.SAXParseException;

public class Warning extends Issue {

	public Warning(SAXParseException e) {
		super(e);
		
	}

	public Warning(String s, String path) {
		super(s, path);
	
	}
	
	public Warning(String s) {
		super(s);
	}

}
