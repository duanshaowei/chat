package com.dewei.chat.service.chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dewei.chat.service.MessageQueueService;
import com.dewei.chat.vo.NetMessage;
import com.dewei.chat.vo.Visitor;
import com.dewei.chat.vo.message.NetChatMessage;
/**
 * 
 * @author duansw
 */
@Service
public class ChatMessageService {
	  @Autowired
	  private MessageQueueService queueService;
	  
	  public void addChatMessage (NetMessage netMessage){
		  boolean addStatus = queueService.offerMessage(netMessage);
		  if(!addStatus){
			  throw new RuntimeException(" 增加消息到队列失败 ; 队列已使用:"+queueService.size());
		  }
	  }


	  public void subsProcess (Visitor visitor,
				NetMessage message) {
		    NetChatMessage chat = (NetChatMessage) message.getMessageBody();
		    
/*			MessageRelation messageRelation = this
					.findMessageRelationByMessageIdAndReceiverId(
							new Long(message.getNetMessageID()),
							new Long(visitor.getRequestId()));
			messageRelation.setReadStatus(MsPushConstants.MESSAGE_READ_TAG);
			this.saveOrUpdate(messageRelation);

			Message dissociate = messageRelation.getMessage();
			chat.setSenderName(dissociate.getSenderName());
			chat.setSenderID(dissociate.getSender().getId().toString());
			chat.setReceiverID(messageRelation.getReceiver().getId().toString());
			chat.setReceiverName(messageRelation.getReceiver().getName());
			chat.setSenderimgMogid(dissociate.getSender().getImgMogid());*/
			
			MessageQueueService.pushMes(visitor,message, chat);
	  }
	  
}
