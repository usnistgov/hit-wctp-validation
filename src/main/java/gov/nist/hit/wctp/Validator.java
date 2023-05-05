package gov.nist.hit.wctp;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

public class Validator {

	private String messageFile;
	private Document messageDocument;
	private Accessor messageAccessor;
	private Report messageReport;

	private WCTPReport wctpreport;

	private String messageSchema;
	private boolean PCD_06;
	private boolean PCD_07;
	private boolean MCR;
	private boolean pairedMCR;
	private boolean unpairedMCR;
	private boolean callback;
	private boolean attachments;
	private boolean synchronous;
	private boolean synchronous_pass;
	private boolean synchronous_fail;
	private boolean asynchronous;
	private boolean alertInformation;

	private static String v1r1sxd = "src/main/resources/wctp-dtd-ihepcd-pcd06-v1r1.xsd";
	private static String v1r2sxd = "src/main/resources/wctp-dtd-ihepcd-pcd06-v1r2.xsd";
	private static String v1r3sxd = "/wctp-dtd-v1r3.xsd";

	public Validator(String messageFile, Report messageReport, boolean PCD_06, boolean PCD_07, boolean MCR,
			boolean pairedMCR, boolean unpairedMCR, boolean callback, boolean attachments, boolean synchronous,
			boolean synchronous_pass, boolean synchronous_fail, boolean asynchronous, boolean alertInformation) {
		this.messageFile = messageFile;
		this.messageReport = messageReport;
		this.PCD_06 = PCD_06;
		this.PCD_07 = PCD_07;
		this.MCR = MCR;
		this.pairedMCR = pairedMCR;
		this.unpairedMCR = unpairedMCR;
		this.callback = callback;
		this.attachments = attachments;
		this.synchronous = synchronous;
		this.synchronous_pass = synchronous_pass;
		this.synchronous_fail = synchronous_fail;
		this.asynchronous = asynchronous;
		this.alertInformation = alertInformation;
	}

	public WCTPReport validate() {
		wctpreport = new WCTPReport();
		messageReport.setDocumentValidation(createDocument());
		if (messageReport.getDocumentValidation() == true) {
			messageReport.setSchemaValidation(schemaValidation());
		} else {
			messageReport.addFatalError(new FatalError("A document was unable to be created from the file provided."));
		}
		if (messageReport.getSchemaValidation() == true) {
			this.messageAccessor = new Accessor(messageDocument);
			messageReport.setContentValidation(contentValidation());
		} else {
			messageReport.addFatalError(
					new FatalError("The document was unable to be validated against the appropriate schema."));
		}
		if (messageReport.getContentValidation() == true) {
		} else {
			messageReport.addFatalError(new FatalError("The document's content was unable to be validated."));
		}
		return wctpreport;

	}

	private boolean createDocument() {
		SchemaErrorHandler errorHandler = new SchemaErrorHandler(messageReport, wctpreport, "schema");
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			factory.setXIncludeAware(false);			
			SAXParser parser = factory.newSAXParser();
			SAXReader reader = new SAXReader(parser.getXMLReader());
			reader.setValidation(false);
			reader.setErrorHandler(errorHandler);
			reader.setIncludeExternalDTDDeclarations(false);
		
			this.messageDocument = reader.read(IOUtils.toInputStream(messageFile, StandardCharsets.UTF_16));
		} catch (DocumentException e) {
			System.out.println("A system error occured with document creation.");
//			System.exit(1);
			wctpreport.addSchemaEntry(new WCTPEntry("Could not ", "schema", "error", "N/A"));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorHandler.getValidation();
	}

	private boolean schemaValidation() {
		SchemaErrorHandler errorHandler = new SchemaErrorHandler(messageReport, wctpreport, "schema");
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			schemaFinder();
			InputStream is = Validator.class.getResourceAsStream(messageSchema);
			factory.setSchema(schemaFactory.newSchema(new StreamSource(is)));
			factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			factory.setXIncludeAware(false);			
			SAXParser parser = factory.newSAXParser();
			SAXReader reader = new SAXReader(parser.getXMLReader());
			reader.setValidation(false);
			reader.setErrorHandler(errorHandler);
			reader.setIncludeExternalDTDDeclarations(false);
			reader.read(IOUtils.toInputStream(messageFile, StandardCharsets.UTF_16));
		} catch (SAXException e) {
			e.printStackTrace();
			System.out.println("A system error occured with schema validation.");
//			System.exit(2);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			System.out.println("A system error occured with schema validation.");
//			System.exit(2);
		} catch (DocumentException e) {
			e.printStackTrace();
			System.out.println("A system error occured with schema validation.");
//			System.exit(2);
		}

		return errorHandler.getValidation();

	}

	private void schemaFinder() {
		if (alertInformation == true) {
			messageSchema = v1r2sxd;// "wctp-dtd-ihepcd-pcd06-v1r2.xsd";
		} else if (callback == true || attachments == true) {
			messageSchema = v1r1sxd;// "wctp-dtd-ihepcd-pcd06-v1r1.xsd";
		} else {
			messageSchema = v1r3sxd;// "wctp-dtd-v1r3.xsd";
		}

		messageReport.setMessageSchema(messageSchema);

	}

	private boolean contentValidation() {
		boolean contentValidation = true;

		// Version
		if (messageAccessor.getVersion() == null) {
			contentValidation = false;
			messageReport.addError(new Error("Invalid WCTP message version.", messageAccessor.getVersion().getPath()));
			wctpreport.addContentEntry(new WCTPEntry("Invalid WCTP message version.", "content", "error",
					messageAccessor.getVersion().getPath()));
		} else {
			String version = messageAccessor.getVersion().getValue();
//			System.out.println(version);
			if (alertInformation == true) {
				if (!version.equals("wctp-dtd-ihepcd-pcd06-v1r2")) {
					contentValidation = false;
					messageReport.addError(
							new Error("Invalid WCTP message version.", messageAccessor.getVersion().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("Invalid WCTP message version.", "content", "error",
							messageAccessor.getVersion().getPath()));

				}
			} else if (callback == true || attachments == true) {
				if (!(version.equals("wctp-dtd-ihepcd-pcd06-v1r1") || version.equals("wctp-dtd-ihepcd-pcd06-v1r2"))) {
					contentValidation = false;
					messageReport.addError(
							new Error("Invalid WCTP message version.", messageAccessor.getVersion().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("Invalid WCTP message version.", "content", "error",
							messageAccessor.getVersion().getPath()));

				}
			} else if (pairedMCR == true) {
				if (!(version.equals("wctp-dtd-ihepcd-pcd06-v1r1") || version.equals("wctp-dtd-ihepcd-pcd06-v1r2")
						|| version.equals("wctp-dtd-v1r3"))) {
					contentValidation = false;
					messageReport.addError(
							new Error("Invalid WCTP message version.", messageAccessor.getVersion().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("Invalid WCTP message version.", "content", "error",
							messageAccessor.getVersion().getPath()));

				}
			} else if (unpairedMCR == true) {
				if (!(version.equals("wctp-dtd-ihepcd-pcd06-v1r1") || version.equals("wctp-dtd-ihepcd-pcd06-v1r2")
						|| version.equals("wctp-dtd-v1r3") || version.equals("wctp-dtd-v1r2"))) {
					contentValidation = false;
					messageReport.addError(
							new Error("Invalid WCTP message version.", messageAccessor.getVersion().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("Invalid WCTP message version.", "content", "error",
							messageAccessor.getVersion().getPath()));

				}
				if (version.equals("wctp-dtd-v1r2")) {
					messageReport.addWarning(new Warning("WCTP version of wctp-dtd-v1r3 or after is suggested.",
							messageAccessor.getVersion().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("WCTP version of wctp-dtd-v1r3 or after is suggested.", "content", "error",
							messageAccessor.getVersion().getPath()));

				}
			} else {
				if (!(version.equals("wctp-dtd-ihepcd-pcd06-v1r1") || version.equals("wctp-dtd-ihepcd-pcd06-v1r2")
						|| version.equals("wctp-dtd-v1r3") || version.equals("wctp-dtd-v1r2")
						|| version.equals("wctp-dtd-v1r1"))) {
					contentValidation = false;
					messageReport.addError(
							new Error("Invalid WCTP message version.", messageAccessor.getVersion().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("Invalid WCTP message version.", "content", "error",
							messageAccessor.getVersion().getPath()));
				}
				if (version.equals("wctp-dtd-v1r2") || version.equals("wctp-dtd-v1r1")) {
					messageReport.addWarning(new Warning("WCTP version of wctp-dtd-v1r3 or after is suggested.",
							messageAccessor.getVersion().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("WCTP version of wctp-dtd-v1r3 or after is suggested.", "content", "error",
							messageAccessor.getVersion().getPath()));
				}
			}
		}

		// PCD-06 Messages
		if (PCD_06 == true) {

			// SubmitHeader -- Universal to all PCD-06 Messages

			// SubmitTimestamp
			if (messageAccessor.getSubmitTimestamp() != null) {
				try {
					// Need to clarify about precision verification...
					LocalDateTime timestamp = LocalDateTime.parse(messageAccessor.getSubmitTimestamp().getValue());
				} catch (DateTimeParseException e) {
					contentValidation = false;
					messageReport.addError(new Error("Invalid Submit Timestamp value.",
							messageAccessor.getSubmitTimestamp().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("Invalid Submit Timestamp value.", "content", "error",
							messageAccessor.getSubmitTimestamp().getPath()));
				}
			} else {
				contentValidation = false;
				messageReport.addError(
						new Error("Invalid Submit Timestamp value.", messageAccessor.getSubmitTimestamp().getPath()));
				wctpreport.addContentEntry(new WCTPEntry("Invalid Submit Timestamp value.", "content", "error",
						messageAccessor.getSubmitTimestamp().getPath()));
			}

			// Originator
			if (!(messageAccessor.getSenderID() != null && messageAccessor.getSenderID().getValue().length() > 0)) {
				contentValidation = false;
				messageReport.addError(new Error("Invalid Sender ID value.", messageAccessor.getSenderID().getPath()));
				wctpreport.addContentEntry(new WCTPEntry("Invalid Sender ID value.", "content", "error",
						messageAccessor.getSenderID().getPath()));
			}
			/*
			 * if (!(messageAccessor.getSecurityCode() != null &&
			 * messageAccessor.getSecurityCode().getValue().length() > 0)) {
			 * contentValidation = false; messageReport.addError(new
			 * Error("Invalid Security Code value.",
			 * messageAccessor.getSecurityCode().getPath())); }
			 */

			// MessageControl
			if (!(messageAccessor.getMessageID() != null && messageAccessor.getMessageID().getValue().length() > 0)) {
				contentValidation = false;
				messageReport
						.addError(new Error("Invalid Message ID value.", messageAccessor.getMessageID().getPath()));
				wctpreport.addContentEntry(new WCTPEntry("Invalid Message ID value.", "content", "error",
						messageAccessor.getMessageID().getPath()));
			}
			if (!(messageAccessor.getTransactionID() != null
					&& messageAccessor.getTransactionID().getValue().length() > 0)) {
				contentValidation = false;
				messageReport.addError(
						new Error("Invalid Transaction ID value.", messageAccessor.getTransactionID().getPath()));
				wctpreport.addContentEntry(new WCTPEntry("Invalid Transaction ID value.", "content", "error",
						messageAccessor.getTransactionID().getPath()));
			}
			if (!(messageAccessor.getAllowResponse() != null
					&& (messageAccessor.getAllowResponse().getValue().toUpperCase().equals("TRUE")
							|| messageAccessor.getAllowResponse().getValue().toUpperCase().equals("FALSE")))) {
				contentValidation = false;
				messageReport.addError(
						new Error("Invalid Allow Response value.", messageAccessor.getAllowResponse().getPath()));
				wctpreport.addContentEntry(new WCTPEntry("Invalid Allow Response value.", "content", "error",
						messageAccessor.getAllowResponse().getPath()));
			}
			if (!(messageAccessor.getDeliveryPriority() != null
					&& (messageAccessor.getDeliveryPriority().getValue().toUpperCase().equals("HIGH")
							|| messageAccessor.getDeliveryPriority().getValue().toUpperCase().equals("NORMAL")
							|| messageAccessor.getDeliveryPriority().getValue().toUpperCase().equals("LOW")))) {
				contentValidation = false;
				messageReport.addError(
						new Error("Invalid Delivery Priority value.", messageAccessor.getDeliveryPriority().getPath()));
				wctpreport.addContentEntry(new WCTPEntry("Invalid Delivery Priority value.", "content", "error",
						messageAccessor.getDeliveryPriority().getPath()));
			}
			if (!(messageAccessor.getNotifyWhenDelivered() != null
					&& (messageAccessor.getNotifyWhenDelivered().getValue().toUpperCase().equals("TRUE")
							|| messageAccessor.getNotifyWhenDelivered().getValue().toUpperCase().equals("FALSE")))) {
				contentValidation = false;
				messageReport.addError(new Error("Invalid Notify When Delivered value.",
						messageAccessor.getNotifyWhenDelivered().getPath()));
				wctpreport.addContentEntry(new WCTPEntry("Invalid Notify When Delivered value.", "content", "error",
						messageAccessor.getNotifyWhenDelivered().getPath()));
			}
			if (!(messageAccessor.getPreformatted() != null
					&& (messageAccessor.getPreformatted().getValue().toUpperCase().equals("TRUE")
							|| messageAccessor.getPreformatted().getValue().toUpperCase().equals("FALSE")))) {
				contentValidation = false;
				messageReport.addError(
						new Error("Invalid/Missing Preformatted value.", messageAccessor.getPreformatted().getPath()));
				wctpreport.addContentEntry(new WCTPEntry("Invalid/Missing Preformatted value.", "content", "error",
						messageAccessor.getPreformatted().getPath()));
			}

			// Recipient
			if (!(messageAccessor.getRecipientID() != null
					&& messageAccessor.getRecipientID().getValue().length() > 0)) {
				contentValidation = false;
				messageReport
						.addError(new Error("Invalid Recipient ID value.", messageAccessor.getRecipientID().getPath()));
				wctpreport.addContentEntry(new WCTPEntry("Invalid Recipient ID value.", "content", "error",
						messageAccessor.getRecipientID().getPath()));
			}

			// No MCR Messages
			if (MCR == false) {
				// Alphanumeric
				if (!(messageAccessor.getAlphanumeric() != null
						&& messageAccessor.getAlphanumeric().getValue().length() > 0)) {
					contentValidation = false;
					messageReport.addError(
							new Error("Invalid Alphanumeric value.", messageAccessor.getAlphanumeric().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("Invalid Alphanumeric value.", "content", "error",
							messageAccessor.getAlphanumeric().getPath()));
				}
			}

			// MCR Messages
			if (MCR == true) {
				// MessageText
				if (!(messageAccessor.getMessageText() != null
						&& messageAccessor.getMessageText().getValue().length() > 0)) {
					contentValidation = false;
					messageReport.addError(
							new Error("Invalid Message Text value.", messageAccessor.getMessageText().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("Invalid Message Text value.", "content", "error",
							messageAccessor.getMessageText().getPath()));
				}

				// Unpaired MCR Messages
				if (unpairedMCR == true) {
					// Choices
					for (int i = 0; i < messageAccessor.getChoices().getValues().size(); i++) {
						int choiceNum = i + 1;
						Node n = messageAccessor.getChoices().getValues().get(i);
						if (!(n.getText() != null && n.getText().length() > 0)) {
							contentValidation = false;
							messageReport.addError(new Error("Invalid Choice " + choiceNum + " value.",
									messageAccessor.getChoices().getPath()));
							wctpreport.addContentEntry(new WCTPEntry("Invalid Choice " + choiceNum + " value.", "content", "error",
									messageAccessor.getChoices().getPath()));
						}
					}
				}

				// Paired MCR Messages
				if (pairedMCR == true) {
					// ChoicePairs
					for (int i = 0; i < messageAccessor.getChoicePairs().getValues().size(); i++) {
						int choicePairNum = i + 1;
						Node n = messageAccessor.getChoicePairs().getValues().get(i);
						Node nSendChoice = n.selectSingleNode("/wctp-SendChoice");
						if (!(nSendChoice != null && nSendChoice.getText().length() > 0)) {
							contentValidation = false;
							messageReport.addError(new Error("Invalid SendChoice " + choicePairNum + " value.",
									messageAccessor.getChoicePairs().getPath() + "/wctp-SendChoice"));
							wctpreport.addContentEntry(new WCTPEntry("Invalid SendChoice " + choicePairNum + " value.", "content", "error",
									messageAccessor.getChoicePairs().getPath()));
						}
						Node nReplyChoice = n.selectSingleNode("/wctp-ReplyChoice");
						if (!(nReplyChoice != null && nReplyChoice.getText().length() > 0)) {
							contentValidation = false;
							messageReport.addError(new Error("Invalid ReplyChoice " + choicePairNum + " value.",
									messageAccessor.getChoicePairs().getPath() + "/wctp-ReplyChoice"));
							wctpreport.addContentEntry(new WCTPEntry("Invalid ReplyChoice " + choicePairNum + " value.", "content", "error",
									messageAccessor.getChoicePairs().getPath()));
						}
					}
				}

				// Callback
				if (callback == true) {
					if (!(messageAccessor.getDialback() != null
							&& messageAccessor.getDialback().getValue().length() > 0)) {
						contentValidation = false;
						messageReport.addError(
								new Error("Invalid Dialback String value.", messageAccessor.getDialback().getPath()));
						wctpreport.addContentEntry(new WCTPEntry("Invalid Dialback String value.", "content", "error",
								messageAccessor.getDialback().getPath()));
					}
				}

				// WCM Images
				if (attachments == true) {
					for (int i = 0; i < messageAccessor.getWCMImages().getValues().size(); i++) {
						int imageNum = i + 1;
						Node n = messageAccessor.getWCMImages().getValues().get(i);
						if (!(n != null && (n.valueOf("@Format").toUpperCase().equals("SVG")
								|| n.valueOf("@Format").toUpperCase().equals("JPEG")
								|| n.valueOf("@Format").toUpperCase().equals("PNG")
								|| n.valueOf("@Format").toUpperCase().equals("BMP")))) {
							contentValidation = false;
							messageReport.addError(new Error("Invalid Format " + imageNum + " value.",
									messageAccessor.getWCMImages().getPath() + "/wctp-Image/@Format"));
							wctpreport.addContentEntry(new WCTPEntry("Invalid Format " + imageNum + " value.", "content", "error",
									messageAccessor.getWCMImages().getPath() + "/wctp-Image/@Format"));
						}
						if (!(n != null && n.valueOf("@Format").toUpperCase().equals("BASE64"))) {
							contentValidation = false;
							messageReport.addError(new Error("Invalid Encoding " + imageNum + " value.",
									messageAccessor.getWCMImages().getPath() + "/wctp-Image/@Encoding"));
							wctpreport.addContentEntry(new WCTPEntry("Invalid Encoding " + imageNum + " value.", "content", "error",
									messageAccessor.getWCMImages().getPath() + "/wctp-Image/@Encoding"));						
						}
					}
				}

				// Alert Information
				if (alertInformation == true) {
					if (!(messageAccessor.getIHEPCDACM().getValue_Node() != null)) {
						contentValidation = false;
						messageReport.addError(new Error("Invalid Alert Information value.", "Null"));
						wctpreport.addContentEntry(new WCTPEntry("Invalid Alert Information value.", "content", "error",
								"N/A"));	
					}
				}
			}
		}

		// PCD-07 messages
		if (PCD_07 == true) {
			// Synchronous Messages
			if (synchronous == true) {
				// Successful Synchronous Messages
				if (synchronous_pass == true) {
					if (!(messageAccessor.getSuccessCode() != null
							&& messageAccessor.getSuccessCode().getValue().length() > 0)) {
						contentValidation = false;
						messageReport.addError(
								new Error("Invalid Success Code value.", messageAccessor.getSuccessCode().getPath()));
						wctpreport.addContentEntry(new WCTPEntry("Invalid Success Code value.", "content", "error",
								messageAccessor.getSuccessCode().getPath()));	
					}
					if (!(messageAccessor.getSuccessText() != null
							&& messageAccessor.getSuccessText().getValue().length() > 0)) {
						contentValidation = false;
						messageReport.addError(
								new Error("Invalid Success Text value.", messageAccessor.getSuccessText().getPath()));
						wctpreport.addContentEntry(new WCTPEntry("Invalid Success Text value.", "content", "error",
								messageAccessor.getSuccessText().getPath()));	
					}
				}
				// Unsuccessful Synchronous Message
				if (synchronous_fail == true) {
					if (!(messageAccessor.getErrorCode() != null
							&& messageAccessor.getErrorCode().getValue().length() > 0)) {
						contentValidation = false;
						messageReport.addError(
								new Error("Invalid Error Code value.", messageAccessor.getErrorCode().getPath()));
						wctpreport.addContentEntry(new WCTPEntry("Invalid Error Code value.", "content", "error",
								messageAccessor.getErrorCode().getPath()));	
					}
					if (!(messageAccessor.getErrorText() != null
							&& messageAccessor.getErrorText().getValue().length() > 0)) {
						contentValidation = false;
						messageReport.addError(
								new Error("Invalid Error Text value.", messageAccessor.getErrorText().getPath()));
						wctpreport.addContentEntry(new WCTPEntry("Invalid Error Text value.", "content", "error",
								messageAccessor.getErrorText().getPath()));	
					}
				}
			}

			if (asynchronous == true) {
				// ResponseHeader
				if (messageAccessor.getResponseTimestamp() != null) {
					try {
						// Need to clarify about precision verification...
						LocalDateTime timestamp = LocalDateTime
								.parse(messageAccessor.getResponseTimestamp().getValue());
					} catch (DateTimeParseException e) {
						contentValidation = false;
						messageReport.addError(new Error("Invalid Response Timestamp value.",
								messageAccessor.getResponseTimestamp().getPath()));
						wctpreport.addContentEntry(new WCTPEntry("Invalid Response Timestamp value.", "content", "error",
								messageAccessor.getResponseTimestamp().getPath()));	
					}
				} else {
					contentValidation = false;
					messageReport.addError(new Error("Invalid Response Timestamp value.",
							messageAccessor.getResponseTimestamp().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("Invalid Response Timestamp value.", "content", "error",
							messageAccessor.getResponseTimestamp().getPath()));	
				}
				if (messageAccessor.getRespondingToTimestamp() != null) {
					try {
						// Need to clarify about precision verification...
						LocalDateTime timestamp = LocalDateTime
								.parse(messageAccessor.getRespondingToTimestamp().getValue());
					} catch (DateTimeParseException e) {
						contentValidation = false;
						messageReport.addError(new Error("Invalid Responding To Timestamp value.",
								messageAccessor.getRespondingToTimestamp().getPath()));
						wctpreport.addContentEntry(new WCTPEntry("Invalid Responding To Timestamp value.", "content", "error",
								messageAccessor.getRespondingToTimestamp().getPath()));	
					}
				} else {
					contentValidation = false;
					messageReport.addError(new Error("Invalid Responding To Timestamp value.",
							messageAccessor.getRespondingToTimestamp().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("Invalid Responding To Timestamp value.", "content", "error",
							messageAccessor.getRespondingToTimestamp().getPath()));	
				}
				if (!(messageAccessor.getOnBehalfOfRecipientID() != null
						&& messageAccessor.getOnBehalfOfRecipientID().getValue().length() > 0)) {
					contentValidation = false;
					messageReport.addError(new Error("Invalid On Behalf Of Recipient ID value.",
							messageAccessor.getOnBehalfOfRecipientID().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("Invalid On Behalf Of Recipient ID value.", "content", "error",
							messageAccessor.getOnBehalfOfRecipientID().getPath()));	
				}

				// Originator
				if (!(messageAccessor.getSenderID_Response() != null
						&& messageAccessor.getSenderID_Response().getValue().length() > 0)) {
					contentValidation = false;
					messageReport.addError(
							new Error("Invalid Sender ID value.", messageAccessor.getSenderID_Response().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("Invalid Sender ID value.", "content", "error",
							messageAccessor.getSenderID_Response().getPath()));	
				}

				// MessageControl
				if (!(messageAccessor.getMessageID_Response() != null
						&& messageAccessor.getMessageID_Response().getValue().length() > 0)) {
					contentValidation = false;
					messageReport.addError(
							new Error("Invalid Message ID value.", messageAccessor.getMessageID_Response().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("Invalid Message ID value.", "content", "error",
							messageAccessor.getMessageID_Response().getPath()));	
				}
				if (!(messageAccessor.getTransactionID_Response() != null
						&& messageAccessor.getTransactionID_Response().getValue().length() > 0)) {
					contentValidation = false;
					messageReport.addError(new Error("Invalid Transaction ID value.",
							messageAccessor.getTransactionID_Response().getPath()));
					wctpreport.addContentEntry(new WCTPEntry("Invalid Transaction ID value.", "content", "error",
							messageAccessor.getTransactionID_Response().getPath()));	
				}

				// Asynchronous Non-MCR Messages
				if (MCR == false) {
					if (!(messageAccessor.getNotificationType() != null
							&& (messageAccessor.getNotificationType().getValue().toUpperCase().equals("READ")
									|| messageAccessor.getNotificationType().getValue().toUpperCase()
											.equals("DELIVERED")
									|| messageAccessor.getNotificationType().getValue().toUpperCase().equals("QUEUED")
									|| messageAccessor.getNotificationType().getValue().toUpperCase()
											.equals("IHEPCDCALLBACKSTART")
									|| messageAccessor.getNotificationType().getValue().toUpperCase()
											.equals("IHEPCDCALLBACKEND")))) {
						contentValidation = false;
						messageReport.addError(new Error("Invalid Notification Type value.",
								messageAccessor.getNotificationType().getPath()));
						wctpreport.addContentEntry(new WCTPEntry("Invalid Notification Type value.", "content", "error",
								messageAccessor.getNotificationType().getPath()));
					}

					// Alert Information
					if (alertInformation == true) {
						if (!(messageAccessor.getIHEPCDACM_Status() != null)) {
							contentValidation = false;
							messageReport.addError(new Error("Invalid Alert Information value.", "Null"));
							wctpreport.addContentEntry(new WCTPEntry("Invalid Alert Information value.", "content", "error",
									"N/A"));
						}
					}
				}

				// Asynchronous MCR Messages
				if (MCR == true) {
					if (!(messageAccessor.getAlphanumeric_Response_MCR() != null
							&& messageAccessor.getAlphanumeric_Response_MCR().getValue().length() > 0)) {
						contentValidation = false;
						messageReport.addError(new Error("Invalid Alphanumeric value.",
								messageAccessor.getAlphanumeric_Response_MCR().getPath()));
						wctpreport.addContentEntry(new WCTPEntry("Invalid Alphanumeric value.", "content", "error",
								messageAccessor.getAlphanumeric_Response_MCR().getPath()));
					}

					// Alert Information
					if (alertInformation == true) {
						if (!(messageAccessor.getIHEPCDACM_Reply() != null)) {
							contentValidation = false;
							messageReport.addError(new Error("Invalid Alert Information value.", "Null"));
							wctpreport.addContentEntry(new WCTPEntry("Invalid Alert Information value.", "content", "error",
									"N/A"));
						}
					}
				}
			}
		}
		return contentValidation;
	}
}
