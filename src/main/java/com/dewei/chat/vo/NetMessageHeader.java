package com.dewei.chat.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * 
 * @author duansw
 */
public class NetMessageHeader implements Serializable {
	private static final long serialVersionUID = -5608822062632060506L;
	// 消息创建者
	@NotNull(message="消息创建者不能为空")
	private Long producUserID;
	// 消息创建时间
	private Date messageCreateDate = new Date();
	// 消息存活时间
	private Date messageDestroyDate;
	// 消息失效时间(单位/秒)
	private int messageDurviveTime = 60;
	// 消息接收者
	private Set<String> mesReceiverTarget = new HashSet<String>();
	// 消息已收者
	private Set<String> mesReceipter = new HashSet<String>(); 
	// 消息类型
	@NotEmpty(message="消息类型不能为空")
	private String messageCode;

	public Set<String> getMesReceipter() {
		return mesReceipter;
	}

	public void setMesReceipter(Set<String> mesReceipter) {
		this.mesReceipter = mesReceipter;
	}
	
	/**
	 * 消息是销毁的
	 * 
	 * @return
	 */
	public Boolean isMesDestroy() {
		Boolean valid = true;

		valid = getMessageDestroyDate().getTime() > new Date().getTime()  ;
        //消息订阅者是否全部已订阅消息
		if (valid && mesReceiverTarget != null) {
			valid = mesReceiverTarget.size() > 0;
		}

		return !valid;
	}
	
	public Date getMessageDestroyDate() {
		this.messageDestroyDate = DateUtils.addSeconds(getMessageCreateDate(),
				getMessageDurviveTime());

		return messageDestroyDate;
	}

	public int getMessageDurviveTime() {
		return messageDurviveTime;
	}

	public void setMessageDurviveTime(int messageDurviveTime) {
		this.messageDurviveTime = messageDurviveTime;
	}

	public Set<String> getMesReceiverTarget() {
		return mesReceiverTarget;
	}

	public void setMesReceiverTarget(Set<String> mesReceiverTarget) {
		this.mesReceiverTarget = mesReceiverTarget;
	}

	public Long getProducUserID() {
		return producUserID;
	}

	public void setProducUserID(Long producUserID) {
		this.producUserID = producUserID;
	}

	public Date getMessageCreateDate() {
		return messageCreateDate;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	@Override
	public String toString() {
		return "MessageHeader [producUserID=" + producUserID
				+ ", messageCreateDate=" + messageCreateDate
				+ ", messageDestroyDate=" + messageDestroyDate
				+ ", messageDurviveTime=" + messageDurviveTime
				+ ", mesReceiverTarget=" + StringUtils.join(mesReceiverTarget.toArray(),",")
				+ ", mesReceipter=" + StringUtils.join(mesReceipter.toArray(),",")
				+ ", messageCode=" + messageCode + " , isMesDestroy = " +isMesDestroy()+ "]";
	}

}
