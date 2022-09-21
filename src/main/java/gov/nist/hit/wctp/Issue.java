package gov.nist.hit.wctp;

import org.xml.sax.SAXParseException;

public class Issue {
	private Exception e;
	private int lineNum = -1;
	private int colNum = -1;
	private String s;
	private String path;
	private String message;
	
	public Issue(SAXParseException e) {
		this.e = e;
		this.lineNum = e.getLineNumber();
		this.colNum = e.getColumnNumber();
		this.message = e.getMessage();
	}
	
	public Issue(String s, String path) {
		this.message = s;
		this.path = path;
		
	}
	
	public Issue(String s) {
		this.message = s;
	}

	public Exception getE() {
		return e;
	}

	public int getLineNum() {
		return lineNum;
	}

	public int getColNum() {
		return colNum;
	}

	public String getS() {
		return s;
	}

	public String getPath() {
		return path;
	}

	public String getMessage() {
		return message;
	}
	
	
	
}
