package gov.nist.hit.wctp;

import java.util.ArrayList;
import java.util.List;

public class Report {
	private String messageType;
	private String messageDescription;
	private String fileName;
	private String messageSchema;
	private boolean documentValidation;
	private boolean schemaValidation;
	private boolean contentValidation;
	private boolean finalValidation;
	private List<Warning> warningList;
	private List<Error> errorList;
	private List<FatalError> fatalErrorList;

	public Report(String messageType, String messageDescription, String fileName) {
		this.messageType = messageType;
		this.messageDescription = messageDescription;
		this.fileName = fileName;
		this.warningList = new ArrayList<Warning>();
		this.errorList = new ArrayList<Error>();
		this.fatalErrorList = new ArrayList<FatalError>();
	}

	public void setMessageSchema(String messageSchema) {
		this.messageSchema = messageSchema;
	}

	public void setDocumentValidation(boolean documentValidation) {
		this.documentValidation = documentValidation;
	}

	public void setSchemaValidation(boolean schemaValidation) {
		this.schemaValidation = schemaValidation;
	}

	public void setContentValidation(boolean contentValidation) {
		this.contentValidation = contentValidation;
	}

	public boolean getDocumentValidation() {
		return documentValidation;
	}

	public boolean getSchemaValidation() {
		return schemaValidation;
	}

	public boolean getContentValidation() {
		return contentValidation;
	}

	public void addWarning(Warning warning) {
		warningList.add(warning);
	}

	public void addError(Error error) {
		errorList.add(error);
	}

	public void addFatalError(FatalError fatalError) {
		fatalErrorList.add(fatalError);
	}

	public int numWarnings() {
		return warningList.size();
	}

	public int numErrors() {
		return errorList.size();
	}

	public int numFatalErrors() {
		return fatalErrorList.size();
	}

	public String getReport() {
		String result = "";

		result+="Message Validation Report" + "\n";
		result+="\n";
		result+="File Name: " + fileName + "\n";
		result+="Transaction Type: " + messageType + "\n";
		result+="Message Type: " + messageDescription + "\n";
		result+="Schema Used: " + messageSchema + "\n";
		result+="";

		if (documentValidation == true) {
			result+="Document Creation & Wellformedness Validation Result: PASS" + "\n";
		} else {
			result+="Document Creation & Wellformedness Validation Result: FAIL" + "\n";
		}
		if (schemaValidation == true) {
			result+="Schema Validation Result: PASS" + "\n";
		} else {
			result+="Schema Validation Result: FAIL" + "\n";
		}
		if (contentValidation == true) {
			result+="Message Parsing Validation Result: PASS" + "\n";
		} else {
			result+="Message Parsing Validation Result: FAIL" + "\n";
		}
		if (documentValidation == true && schemaValidation == true && contentValidation == true) {
			result+="Overall Validation Result: PASS" + "\n";
		} else {
			result+="Overall Validation Result: FAIL" + "\n";
		}
		result+="*Only components that failed will have errors or fatal errors.  Components that passed or failed can have warnings."+ "\n";
		result+="\n";

		result+="Warnings: " + numWarnings();
		for (int i = 0; i < warningList.size(); i++) {
			int num = i + 1;
			result+="#" + num + "\n";
			result+=warningList.get(i).getMessage() + "\n";
			if (warningList.get(i).getLineNum() != -1) {
				result+="Line Number: " + warningList.get(i).getLineNum() + "\n";
			}
			if (warningList.get(i).getColNum() != -1) {
				result+="Column Number: " + warningList.get(i).getColNum() + "\n";
			}
			if (warningList.get(i).getPath() != null) {
				result+="Path: " + warningList.get(i).getPath() + "\n";
			}
		}
		result+="\n";

		result+="Errors: " + numErrors();
		for (int i = 0; i < errorList.size(); i++) {
			int num = i + 1;
			result+="#" + num + "\n";
			result+=errorList.get(i).getMessage() + "\n";
			if (errorList.get(i).getLineNum() != -1) {
				result+="Line Number: " + errorList.get(i).getLineNum() + "\n";
			}
			if (errorList.get(i).getColNum() != -1) {
				result+="Column Number: " + errorList.get(i).getColNum() + "\n";
			}
			if (errorList.get(i).getPath() != null) {
				result+="Path: " + errorList.get(i).getPath() + "\n";
			}
		}
		result+="\n";

		result+="Fatal Errors: " + numFatalErrors();
		for (int i = 0; i < fatalErrorList.size(); i++) {
			int num = i + 1;
			result+="#" + num + "\n";
			result+=fatalErrorList.get(i).getMessage() + "\n";
			if (fatalErrorList.get(i).getLineNum() != -1) {
				result+="Line Number: " + fatalErrorList.get(i).getLineNum() + "\n";
			}
			if (fatalErrorList.get(i).getColNum() != -1) {
				result+="Column Number: " + fatalErrorList.get(i).getColNum() + "\n";
			}
			if (fatalErrorList.get(i).getPath() != null) {
				result+="Path: " + fatalErrorList.get(i).getPath() + "\n";
			}
		}
		result+="\n";
		System.out.println(result);
		return result;
	}

}
