package com.dewei.chat.vo;

import java.io.Serializable;
/**
 * 
 * @author duansw
 */
public class NetMessage implements Serializable{

	private static final long serialVersionUID = 8518751037065141147L;

	private String netMessageID ; 
	
	private NetMessageHeader messageHeader = new NetMessageHeader();
	
	private NetMessageBody messageBody ;

	public String getNetMessageID() {
		return netMessageID;
	}

	public void setNetMessageID(String netMessageID) {
		this.netMessageID = netMessageID;
	}
	
	public NetMessageHeader getMessageHeader() {
		return messageHeader;
	}

	public void setMessageHeader(NetMessageHeader messageHeader) {
		this.messageHeader = messageHeader;
	}

	public NetMessageBody getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(NetMessageBody messageBody) {
		this.messageBody = messageBody;
	}

	@Override
	public String toString() {
		return "Message [messageHeader=" + messageHeader.toString() + ", messageBody="
				+ messageBody.toString() + "]";
	}
	
	
}
