package com.dewei.chat.abstracts.subscribers;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dewei.chat.abstracts.subscribers.handler.SubscriberMesBaseHandler;
import com.dewei.chat.vo.NetMessage;

/**
 * 
 * @author duansw
 */
public abstract class SubscriberMesBase implements SubscriberInterface {

	@Autowired
	private List<SubscriberMesBaseHandler> subscriber;

	public List<SubscriberMesBaseHandler> getSubscriber() {
		
		return subscriber == null ? new ArrayList<SubscriberMesBaseHandler>() : subscriber;
	}

	@Override
	public void entrance(NetMessage message) throws Exception {
		for (SubscriberMesBaseHandler subscriberMesHandler : getSubscriber()) {

			if (!subscriberMesHandler.verifySubscriberMesHandler(message)) {
				throw new RuntimeException("此消息未找到消息订阅处理器!;messag:"
						+ message.toString());
			} else {
				
					subscriberMesHandler.process(message);
				
			}
		}
		
	}
}
