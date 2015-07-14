package com.dewei.chat.abstracts.subscribers.handler;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.dewei.chat.service.MessageQueueService;
import com.dewei.chat.vo.NetMessage;
import com.dewei.chat.vo.Visitor;
/**
 * 
 * @author duansw
 */
public abstract class SubscriberMesBaseHandler implements SubscriberMesHandler {
	
	@Autowired
	private MessageQueueService queueService ;

	/**
	 * 根据消息类型查找消息订阅上下文
	 * @param message 消息
	 * @return DeferredResult
	 */
	private Visitor findSubscriberContext(NetMessage message) {
		Visitor entity = null;
		Set<String> mesReceiverTarget = message.getMessageHeader().getMesReceiverTarget();
		Set<String> receipters = message.getMessageHeader().getMesReceipter();
		ConcurrentHashMap<String ,Visitor> visitors = queueService.getDefrredResultQueue();
		String key = (String)mesReceiverTarget.toArray()[0];
		Visitor visitor =visitors.get(key);
		if( visitor !=null && !receipters.contains(visitor.getSessionId())){
			entity = visitor ;
		}
		return entity;
	}

	public void process(NetMessage message) {
		Visitor visitor = findSubscriberContext(message);
		if (visitor == null)
			noSubscribersaProc(message);
		else{
			subscribersProc(visitor, message);
		}
			
	}
}
