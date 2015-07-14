package com.dewei.chat.abstracts.subscribers;

import com.dewei.chat.vo.NetMessage;

/**
 * 
 * @author duansw
 */
public interface SubscriberInterface {

	void entrance(NetMessage message) throws Exception;
}
