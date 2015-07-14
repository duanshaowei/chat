package com.dewei.chat.vo.message;

import com.dewei.chat.vo.NetMessageBody;


/**
 * 
 * @author duansw
 */
public class NetChatMessage extends NetMessageBody {
	private static final long serialVersionUID = 6429878753535797882L;
	
	private String senderName;
	
	private String senderID;
	
	private String senderimgMogid;
	
	private String receiverID;
	
	private String receiverName;

	public String getSenderimgMogid() {
		return senderimgMogid;
	}

	public void setSenderimgMogid(String senderimgMogid) {
		this.senderimgMogid = senderimgMogid;
	}
	
	public String getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(String receiverID) {
		this.receiverID = receiverID;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
}
