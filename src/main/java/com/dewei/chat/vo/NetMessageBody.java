package com.dewei.chat.vo;

import java.io.Serializable;
import java.util.Date;

import org.joda.time.DateTime;

/**
 * 
 * @author duansw
 */
public abstract class NetMessageBody implements Serializable {

	private static final long serialVersionUID = 1075725825412052109L;

	private Date operateTime = new Date();

	private String operateCode;

	private String memo;

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOperateTime() {
		DateTime dateTiem = new DateTime(operateTime);
		return dateTiem.toString("yyyy年MM月dd日 HH:mm:ss");
	}

	public Date getOperateTimeOriginal(){
		return operateTime;
	}
	
	public String getOperateCode() {
		return operateCode;
	}

	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public static NetMessageBody emptyBody() {
		return new NetMessageBody() {
			private static final long serialVersionUID = 7172240521303282415L;
		};
	}

	@Override
	public String toString() {

		return "MessageBody [ operateCode = " + operateCode + " , memo = "
				+ memo + " operateTime = " + getOperateTime() + " , content ="+content+"]";
	}

}
