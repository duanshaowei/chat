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
			MessageQueueService.pushMes(visitor,message, chat);
	  }
	  
}
