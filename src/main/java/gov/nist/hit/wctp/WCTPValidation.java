package gov.nist.hit.wctp;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

public class WCTPValidation {
	
	public WCTPValidation() {
		
	}
	
	public static WCTPReport generic(String message) {
		Report messageReport = new Report("Generic", "N/A -- *Extremely limited content validation is performed for generic messages.", "message");
		Validator messageValidator = new Validator(message, messageReport, false, false, false, false, false, false, false, false, false, false, false, false);
		WCTPReport wctpreport = messageValidator.validate();
		return wctpreport;
//		return messageReport.getReport();
	}
	
	
	public static WCTPReport generic(File message) throws IOException {
		Report messageReport = new Report("Generic", "N/A -- *Extremely limited content validation is performed for generic messages.", message.getName());
		Validator messageValidator = new Validator(FileUtils.readFileToString(message,StandardCharsets.UTF_16), messageReport, false, false, false, false, false, false, false, false, false, false, false, false);
		WCTPReport wctpreport = messageValidator.validate();
		return wctpreport;
//		return messageReport.getReport();
	}
//	
//	public void PCD_06_01(File message) {
//		Report messageReport = new Report("[PCD-06]", "No MCR", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, true, false, false, false, false, false, false, false, false, false, false, false);
//		messageValidator.validate();
//		messageReport.getReport();
//	}
//	
//	public void PCD_06_02(File message) {
//		Report messageReport = new Report("[PCD-06]", "Unpaired MCR", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, true, false, true, false, true, false, false, false, false, false, false, false);
//		messageValidator.validate();
//		messageReport.getReport();
//	}
//	
//	public void PCD_06_03(File message) {
//		Report messageReport = new Report("[PCD-06]", "Paired MCR with No Additions", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, false, false, false, false, false, false, false);
//		messageValidator.validate();
//		messageReport.getReport();
//	}
//	
//	public void PCD_06_04(File message) {
//		Report messageReport = new Report("[PCD-06]", "Paired MCR with Callback", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, true, false, false, false, false, false, false);
//		messageValidator.validate();
//		messageReport.getReport();
//	}
//	
//	public void PCD_06_05(File message) {
//		Report messageReport = new Report("[PCD-06]", "Paired MCR with WCM", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, false, true, false, false, false, false, false);
//		messageValidator.validate();
//		messageReport.getReport();
//	}
//	
//	public void PCD_06_06(File message) {
//		Report messageReport = new Report("[PCD-06]", "Paired MCR with Alert Information", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, false, false, false, false, false, false, true);
//		messageValidator.validate();
//		messageReport.getReport();
//	}
//	
//	public void PCD_06_07(File message) {
//		Report messageReport = new Report("[PCD-06]", "Paired MCR with Callback and WCM", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, true, true, false, false, false, false, false);
//		messageValidator.validate();
//		messageReport.getReport();
//	}
//	
//	public void PCD_06_08(File message) {
//		Report messageReport = new Report("[PCD-06]", "Paired MCR with Callback and Alert Information", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, true, false, false, false, false, false, true);
//		messageValidator.validate();
//		messageReport.getReport();
//	}
//	
//	public void PCD_06_09(File message) {
//		Report messageReport = new Report("[PCD-06]", "Paired MCR with WCM and Alert Information", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, false, true, false, false, false, false, true);
//		messageValidator.validate();
//		messageReport.getReport();
//	}
//	
//	public void PCD_06_10(File message) {
//		Report messageReport = new Report("[PCD-06]", "Paired MCR with Callback, WCM, and Alert Information", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, true, false, true, true, false, true, true, false, false, false, false, true);
//		messageValidator.validate();
//		messageReport.getReport();
//	}
//	
//	public void PCD_07_01(File message) {
//		Report messageReport = new Report("[PCD-07]", "Synchronous Status Update with Success", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, false, true, false, false, false, false, false, true, true, false, false, false);
//		messageValidator.validate();
//		messageReport.getReport();
//	}
//	
//	public void PCD_07_02(File message) {
//		Report messageReport = new Report("[PCD-07]", "Synchronous Status Update with Failure", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, false, true, false, false, false, false, false, true, false, true, false, false);
//		messageValidator.validate();
//		messageReport.getReport();
//	}
//	
//	public void PCD_07_03(File message) {
//		Report messageReport = new Report("[PCD-07]", "Asynchronous Status Update", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, false, true, false, false, false, false, false, false, false, false, true, false);
//		messageValidator.validate();
//		messageReport.getReport();
//	}
//	
//	public void PCD_07_04(File message) {
//		Report messageReport = new Report("[PCD-07]", "Asynchronous Response with MCR", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, false, true, true, false, false, false, false, false, false, false, true, false);
//		messageValidator.validate();
//		messageReport.getReport();
//	}
//	
//	public void PCD_07_05(File message) {
//		Report messageReport = new Report("[PCD-07]", "Asynchronous Status Update with Alert Information", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, false, true, true, false, false, false, false, false, false, false, true, false);
//		messageValidator.validate();
//		messageReport.getReport();
//	}
//	
//	public void PCD_07_06(File message) {
//		//Testing GitHub...
//		Report messageReport = new Report("[PCD-07]", "Asynchronous Response with MCR and Alert Information", message.getName());
//		Validator messageValidator = new Validator(message, messageReport, false, true, true, false, false, false, false, false, false, false, true, true);
//		messageValidator.validate();
//		messageReport.getReport();
//		
//	}

	
	
	
	
//	public static void main(String[] args) {
////		File inputFile = new File("/resources/TestCase2.xml");
////		InputStream is = WCTPValidation.class.getResourceAsStream("/resources/TestCase2.xml");
////        printInputStream(is);
//
////        System.out.println("\ngetResource : " + fileName);
//		
//		InputStream is = WCTPValidation.class.getResourceAsStream("/resources");
//        File inputFile;
//		try {
//			inputFile = new File(WCTPValidation.class.getResource("/resources/TestCase2.xml").toURI());
//			generic(inputFile);
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}

}
