package gov.nist.hit.wctp;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;

public class Accessor {
	
	private Document messageDocument;
	
	public Accessor(Document messageDocument) {
		this.messageDocument = messageDocument;
	}
	
	public NodeInfo getVersion() {
		Node operation = messageDocument.selectSingleNode("//wctp-Operation");
		if (operation == null) {
			return null;
		}
		return new NodeInfo(operation.valueOf("@wctpVersion"), operation.getPath() + "/@wctpVersion");
	}
	
	public NodeInfo getSubmitTimestamp() {
		Node header = messageDocument.selectSingleNode("//wctp-Operation/wctp-SubmitRequest/wctp-SubmitHeader");
		if (header == null) {
			return null;
		}
		return new NodeInfo(header.valueOf("@submitTimestamp"), header.getPath() + "/@submitTimestamp");
	}
	
	public NodeInfo getSenderID() {
		Node originator = messageDocument.selectSingleNode("//wctp-Operation/wctp-SubmitRequest/wctp-SubmitHeader/wctp-Originator");		
		if (originator == null ) {
			return null;
		}
		return new NodeInfo(originator.valueOf("@senderID"), originator.getPath() + "/@senderID");
	}
	
	public NodeInfo getSecurityCode() {
		Node originator = messageDocument.selectSingleNode("//wctp-Operation/wctp-SubmitRequest/wctp-SubmitHeader/wctp-Originator");
		if (originator == null ) {
			return null;
		}
		return new NodeInfo(originator.valueOf("@securityCode"), originator.getPath() + "/@securityCode");
	}
	
	public NodeInfo getMessageID() {
		Node messageControl = messageDocument.selectSingleNode("//wctp-Operation/wctp-SubmitRequest/wctp-SubmitHeader/wctp-MessageControl");
		if (messageControl == null ) {
			return null;
		}
		return new NodeInfo(messageControl.valueOf("@messageID"), messageControl.getPath() + "/@messageID");
	}
	
	public NodeInfo getTransactionID() {
		Node messageControl = messageDocument.selectSingleNode("//wctp-Operation/wctp-SubmitRequest/wctp-SubmitHeader/wctp-MessageControl");
		if (messageControl == null ) {
			return null;
		}
		return new NodeInfo(messageControl.valueOf("@transactionID"), messageControl.getPath() + "/@transactionID");
	}
	
	public NodeInfo getAllowResponse() {
		Node messageControl = messageDocument.selectSingleNode("//wctp-Operation/wctp-SubmitRequest/wctp-SubmitHeader/wctp-MessageControl");
		if (messageControl == null ) {
			return null;
		}
		return new NodeInfo(messageControl.valueOf("@allowResponse"), messageControl.getPath() + "/@allowResponse");
	}
	
	public NodeInfo getDeliveryPriority() {
		Node messageControl = messageDocument.selectSingleNode("//wctp-Operation/wctp-SubmitRequest/wctp-SubmitHeader/wctp-MessageControl");
		if (messageControl == null ) {
			return null;
		}
		return new NodeInfo(messageControl.valueOf("@deliveryPriority"), messageControl.getPath() + "/@deliveryPriority");
	}
	
	public NodeInfo getNotifyWhenDelivered() {
		Node messageControl = messageDocument.selectSingleNode("//wctp-Operation/wctp-SubmitRequest/wctp-SubmitHeader/wctp-MessageControl");
		if (messageControl == null ) {
			return null;
		}
		return new NodeInfo(messageControl.valueOf("@notifyWhenDelivered"), messageControl.getPath() + "/@notifyWhenDelivered");
	}
	
	public NodeInfo getPreformatted() {
		Node messageControl = messageDocument.selectSingleNode("//wctp-Operation/wctp-SubmitRequest/wctp-SubmitHeader/wctp-MessageControl");
		if (messageControl == null ) {
			return null;
		}
		return new NodeInfo(messageControl.valueOf("@preformatted"), messageControl.getPath() + "/@preformatted");
	}
	
	public NodeInfo getRecipientID() {
		Node recipient = messageDocument.selectSingleNode("//wctp-Operation/wctp-SubmitRequest/wctp-SubmitHeader/wctp-Recipient");
		if (recipient == null ) {
			return null;
		}
		return new NodeInfo(recipient.valueOf("@recipientID"), recipient.getPath() + "/@recipientID");
	}
	
	public NodeInfo getAlphanumeric() {
		Node alphanumeric = messageDocument.selectSingleNode("//wctp-Operation/wctp-SubmitRequest/wctp-Payload/wctp-Alphanumeric");
		if (alphanumeric == null ) {
			return null;
		}
		return new NodeInfo(alphanumeric.getText(), alphanumeric.getPath());
	}
	
	public NodeInfo getMessageText() {
		Node messageText = messageDocument.selectSingleNode("//wctp-Operation/wctp-SubmitRequest/wctp-Payload/wctp-MCR/wctp-MessageText");
		if (messageText == null ) {
			return null;
		}
		return new NodeInfo(messageText.getText(), messageText.getPath());
	}
	
	public NodeInfo getChoices() {
		List<Node> choices = messageDocument.selectNodes("//wctp-Operation/wctp-SubmitRequest/wctp-Payload/wctp-MCR/wctp-Choice");
		if (choices.size() == 0 ) {
			return null;
		}
		return new NodeInfo(choices, choices.get(0).getPath());
	}
	
	public NodeInfo getChoicePairs() {
		List<Node> choicePairs = messageDocument.selectNodes("//wctp-Operation/wctp-SubmitRequest/wctp-Payload/wctp-MCR/wctp-ChoicePair");
		if (choicePairs.size() == 0 ) {
			return null;
		}
		return new NodeInfo(choicePairs, choicePairs.get(0).getPath());
	}
	
	public NodeInfo getDialback() {
		Node dialback = messageDocument.selectSingleNode("//wctp-Operation/wctp-SubmitRequest/wctp-Payload/wctp-IHEPCDDialback");
		if (dialback == null ) {
			return null;
		}
		return new NodeInfo(dialback.valueOf("@String"), dialback.getPath() + "/@String");
	}
	
	public NodeInfo getSuccessCode() {
		Node success = messageDocument.selectSingleNode("//wctp-Operation/wctp-Confirmation/wctp-Success");
		if (success == null ) {
			return null;
		}
		return new NodeInfo(success.valueOf("@successCode"), success.getPath() + "/@successCode");
	}
	
	public NodeInfo getSuccessText() {
		Node success = messageDocument.selectSingleNode("//wctp-Operation/wctp-Confirmation/wctp-Success");
		if (success == null ) {
			return null;
		}
		return new NodeInfo(success.valueOf("@successText"), success.getPath() + "/@successText");
	}
	
	public NodeInfo getSuccessComment() {
		Node success = messageDocument.selectSingleNode("//wctp-Operation/wctp-Confirmation/wctp-Success");
		if (success == null ) {
			return null;
		}
		return new NodeInfo(success.getText(), success.getPath());
	}
	
	public NodeInfo getErrorCode() {
		Node failure = messageDocument.selectSingleNode("//wctp-Operation/wctp-Confirmation/wctp-Failure");
		if (failure == null ) {
			return null;
		}
		return new NodeInfo(failure.valueOf("@errorCode"), failure.getPath() + "/@errorCode");
	}
	
	public NodeInfo getErrorText() {
		Node failure = messageDocument.selectSingleNode("//wctp-Operation/wctp-Confirmation/wctp-Failure");
		if (failure == null ) {
			return null;
		}
		return new NodeInfo(failure.valueOf("@errorText"), failure.getPath() + "/@errorText");
	}
	
	public NodeInfo getErrorComment() {
		Node failure = messageDocument.selectSingleNode("//wctp-Operation/wctp-Confirmation/wctp-Failure");
		if (failure == null ) {
			return null;
		}
		return new NodeInfo(failure.getText(), failure.getPath());
	}
	
	public NodeInfo getResponseTimestamp() {
		Node responseHeader = messageDocument.selectSingleNode("//wctp-Operation/wctp-StatusInfo/wctp-ResponseHeader");
		if (responseHeader == null ) {
			return null;
		}
		return new NodeInfo(responseHeader.valueOf("@responseTimestamp"), responseHeader.getPath() + "/@responseTimestamp");
	}
	
	public NodeInfo getRespondingToTimestamp() {
		Node responseHeader = messageDocument.selectSingleNode("//wctp-Operation/wctp-StatusInfo/wctp-ResponseHeader");
		if (responseHeader == null ) {
			return null;
		}
		return new NodeInfo(responseHeader.valueOf("@respondingToTimestamp"), responseHeader.getPath() + "/@respondingToTimestamp");
	}
	
	public NodeInfo getOnBehalfOfRecipientID() {
		Node responseHeader = messageDocument.selectSingleNode("//wctp-Operation/wctp-StatusInfo/wctp-ResponseHeader");
		if (responseHeader == null ) {
			return null;
		}
		return new NodeInfo(responseHeader.valueOf("@onBehalfOfRecipientID"), responseHeader.getPath() + "/@onBehalfOfRecipientID");
	}
	
	public NodeInfo getSenderID_Response() {
		Node originator = messageDocument.selectSingleNode("//wctp-Operation/wctp-StatusInfo/wctp-ResponseHeader/wctp-Originator");
		if (originator == null ) {
			return null;
		}
		return new NodeInfo(originator.valueOf("@senderID"), originator.getPath() + "/@senderID");
	}
	
	public NodeInfo getMessageID_Response() {
		Node messageControl = messageDocument.selectSingleNode("//wctp-Operation/wctp-StatusInfo/wctp-ResponseHeader/wctp-MessageControl");
		if (messageControl == null ) {
			return null;
		}
		return new NodeInfo(messageControl.valueOf("@messageID"), messageControl.getPath() + "/@messageID");
	}
	
	public NodeInfo getTransactionID_Response() {
		Node messageControl = messageDocument.selectSingleNode("//wctp-Operation/wctp-StatusInfo/wctp-ResponseHeader/wctp-MessageControl");
		if (messageControl == null ) {
			return null;
		}
		return new NodeInfo(messageControl.valueOf("@transactionID"), messageControl.getPath() + "/@transactionID");
	}
	
	public NodeInfo getAuthorizationCode() {
		Node recipient = messageDocument.selectSingleNode("//wctp-Operation/wctp-StatusInfo/wctp-ResponseHeader/wctp-Recipient");
		if (recipient == null ) {
			return null;
		}
		return new NodeInfo(recipient.valueOf("@authorizationCode"), recipient.getPath() + "/@authorizationCode");
	}
	
	public NodeInfo getNotificationType() {
		Node notification = messageDocument.selectSingleNode("//wctp-Operation/wctp-StatusInfo/wctp-Notification");
		if (notification == null ) {
			return null;
		}
		return new NodeInfo(notification.valueOf("@type"), notification.getPath() + "/@type");
	}
	
	public NodeInfo getMCRMessageReply() {
		Node messageReply = messageDocument.selectSingleNode("//wctp-Operation/wctp-MessageReply");
		if (messageReply == null ) {
			return null;
		}
		return new NodeInfo(messageReply.valueOf("@MCRMessageReply"), messageReply.getPath() + "/@MCRMessageReply");
	}
	
	public NodeInfo getResponeToMessageID() {
		Node responseHeader = messageDocument.selectSingleNode("//wctp-Operation/wctp-MessageReply/wctp-ResponseHeader");
		if (responseHeader == null ) {
			return null;
		}
		return new NodeInfo(responseHeader.valueOf("@responseToMessageID"), responseHeader.getPath() + "/@responseToMessageID");
	}
	
	public NodeInfo getResponseTimestamp_MCR() {
		Node responseHeader = messageDocument.selectSingleNode("//wctp-Operation/wctp-MessageReply/wctp-ResponseHeader");
		if (responseHeader == null ) {
			return null;
		}
		return new NodeInfo(responseHeader.valueOf("@responseTimestamp"), responseHeader.getPath() + "/@responseTimestamp");
	}
	
	public NodeInfo getRespondingToTimestamp_MCR() {
		Node responseHeader = messageDocument.selectSingleNode("//wctp-Operation/wctp-MessageReply/wctp-ResponseHeader");
		if (responseHeader == null ) {
			return null;
		}
		return new NodeInfo(responseHeader.valueOf("@respondingToTimestamp"), responseHeader.getPath() + "/@respondingToTimestamp");
	}
	
	public NodeInfo getOnBehalfOfRecipientID_MCR() {
		Node responseHeader = messageDocument.selectSingleNode("//wctp-Operation/wctp-MessageReply/wctp-ResponseHeader");
		if (responseHeader == null ) {
			return null;
		}
		return new NodeInfo(responseHeader.valueOf("@onBehalfOfRecipientID"), responseHeader.getPath() + "/@onBehalfOfRecipientID");
	}
	
	public NodeInfo getSenderID_Response_MCR() {
		Node originator = messageDocument.selectSingleNode("//wctp-Operation/wctp-MessageReply/wctp-ResponseHeader/wctp-Originator");
		if (originator == null ) {
			return null;
		}
		return new NodeInfo(originator.valueOf("@senderID"), originator.getPath() + "/@senderID");
	}
	
	public NodeInfo getMiscInfo() {
		Node originator = messageDocument.selectSingleNode("//wctp-Operation/wctp-MessageReply/wctp-ResponseHeader/wctp-Originator");
		if (originator == null ) {
			return null;
		}
		return new NodeInfo(originator.valueOf("@miscInfo"), originator.getPath() + "/@miscInfo");
	}
	
	public NodeInfo getMessageID_Response_MCR() {
		Node messageControl = messageDocument.selectSingleNode("//wctp-Operation/wctp-MessageReply/wctp-ResponseHeader/wctp-MessageControl");
		if (messageControl == null ) {
			return null;
		}
		return new NodeInfo(messageControl.valueOf("@messageID"), messageControl.getPath() + "/@messageID");
	}
	
	public NodeInfo getTransactionID_Response_MCR() {
		Node messageControl = messageDocument.selectSingleNode("//wctp-Operation/wctp-MessageReply/wctp-ResponseHeader/wctp-MessageControl");
		if (messageControl == null ) {
			return null;
		}
		return new NodeInfo(messageControl.valueOf("@transactionID"), messageControl.getPath() + "/@transactionID");
	}
	
	public NodeInfo getRecipientID_Response_MCR() {
		Node recipient = messageDocument.selectSingleNode("//wctp-Operation/wctp-MessageReply/wctp-ResponseHeader/wctp-Recipient");
		if (recipient == null ) {
			return null;
		}
		return new NodeInfo(recipient.valueOf("@recipientID"), recipient.getPath() + "/@recipientID");
	}
	
	public NodeInfo getAlphanumeric_Response_MCR() {
		Node alphanumeric = messageDocument.selectSingleNode("//wctp-Operation/wctp-MessageReply/wctp-Payload/wctp-Alphanumeric");
		if (alphanumeric == null ) {
			return null;
		}
		return new NodeInfo(alphanumeric.getText(), alphanumeric.getPath());
	}
	
	public NodeInfo getPCD04Message() {
		Node pcd04Message = messageDocument.selectSingleNode("//wctp-Operation/wctp-SubmitRequest/wctp-Payload/wctp-MCR/wctp-IHEPCD04");
		if (pcd04Message == null ) {
			return null;
		}
		return new NodeInfo(pcd04Message.getText(), pcd04Message.getPath());
	}
	
	public NodeInfo getWCMImages() {
		List<Node> images = messageDocument.selectNodes("//wctp-Operation/wctp-SubmitRequest/wctp-Payload/wctp-MCR/wctp-IHEPCDACMImages/wctp-IHEWCMImage");
		if (images == null ) {
			return null;
		}
		return new NodeInfo(images, images.get(0).getPath());
	}
	
	public NodeInfo getIHEPCDACM() {
		Node IHEPCDACM = messageDocument.selectSingleNode("//wctp-Operation/wctp-SubmitRequest/wctp-Payload/wctp-MCR/wctp-IHEPCDACM");
		if (IHEPCDACM == null ) {
			return null;
		}
			return new NodeInfo(IHEPCDACM, IHEPCDACM.getPath());
	}
	
	public NodeInfo getIHEPCDACM_Status() {
		Node IHEPCDACM = messageDocument.selectSingleNode("//wctp-Operation/wctp-StatusInfo/wctp-IHEPCDACM");
		if (IHEPCDACM == null ) {
			return null;
		}
			return new NodeInfo(IHEPCDACM, IHEPCDACM.getPath());
	}
	
	public NodeInfo getIHEPCDACM_Reply() {
		Node IHEPCDACM = messageDocument.selectSingleNode("//wctp-Operation/wctp-MessageReply/wctp-IHEPCDACM");
		if (IHEPCDACM == null ) {
			return null;
		}
			return new NodeInfo(IHEPCDACM, IHEPCDACM.getPath());
	}
	
}