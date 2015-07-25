package com.dewei.chat.handle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dewei.chat.abstracts.subscribers.handler.SubscriberMesBaseHandler;
import com.dewei.chat.service.chat.ChatMessageService;
import com.dewei.chat.vo.NetMessage;
import com.dewei.chat.vo.Visitor;
import com.dewei.chat.vo.message.NetChatMessage;
/**
 * 
 * @author duansw
 *
 */
@Component
public class ChatSubscriberMes extends SubscriberMesBaseHandler{
	@Autowired
	private ChatMessageService chatService ;
	
	@Override
	public Boolean verifySubscriberMesHandler(NetMessage message) {
		if (message == null)
			return false;
		if (message.getMessageBody() instanceof NetChatMessage)
			return true;
		return false;
	}

	@Override
	public void subscribersProc(Visitor visitor,
			NetMessage message) {
		
		 chatService.subsProcess(visitor , message);
	}

	@Override
	public void noSubscribersaProc(NetMessage message) {
		
	}
}
