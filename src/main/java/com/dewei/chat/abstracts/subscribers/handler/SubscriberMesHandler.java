package com.dewei.chat.abstracts.subscribers.handler;

import com.dewei.chat.vo.NetMessage;
import com.dewei.chat.vo.Visitor;

/**
 * 消息订阅处理器
 * @author duansw
 */
public interface SubscriberMesHandler {
	/**
	 * 查证信息与消息订阅匹配规则
	 * @param message
	 * @return
	 */
	Boolean verifySubscriberMesHandler(NetMessage message);
	/**
	 * 找到消息订阅者处理
	 * 
	 * @param result
	 * @param message
	 */
    void subscribersProc(Visitor visitor, NetMessage message);
    /**
	 * 没有找到消息订阅者处理
	 * 
	 * @param message
	 */
    void noSubscribersaProc(NetMessage message) ;
}
