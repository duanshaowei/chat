package com.dewei.chat.thread.task;
import java.util.Date;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dewei.chat.abstracts.subscribers.SubscriberMesBase;
import com.dewei.chat.exception.AsyncRequestTimeOut;
import com.dewei.chat.service.MessageQueueService;
import com.dewei.chat.vo.NetMessage;
import com.dewei.chat.vo.NetMessageHeader;
/**
 * 基于发布与订阅的消息线程推送类 
 * @author duansw
 *
 */
@Component
public class PushMessageRunTask extends SubscriberMesBase implements Runnable {
	private final static Logger logger = LoggerFactory.getLogger(PushMessageRunTask.class);

	@Autowired
	private MessageQueueService queueService ; 
    
	/**
	 *  消息失效策略
	 * @param message
	 */
	private void messageDestroyStrategy(NetMessage message ,Iterator<NetMessage> it) {
		long start = System.currentTimeMillis();
		logger.warn(" 开始 删除过期消息 【 "+message.toString()+" 】 "+new Date(start).toString()+"");
		
		it.remove();
		
		long cost = System.currentTimeMillis() - start;
		logger.warn(" 结束 删除过期消息 操作状态【 "+ flag+" 】 运行【 "+cost+" 】毫秒");
	}

	private void entrance(NetMessage message , Iterator<NetMessage> it) throws Exception {
		try {
			super.entrance(message);
		}catch (AsyncRequestTimeOut e){ 
		    logger.info(e.getMessage());
		}catch (Exception e) {
			logger.error( "执行出错消息 "+message.toString() ,e);
			it.remove();
		}
	}
	
	@Override
	public void run() {
		logger.debug(" 开始执行 【基于发布与订阅的消息线程推送任务】 "+new Date(System.currentTimeMillis()).toString());
		while (flag) {
			try {
				Iterator<NetMessage> it = queueService.getMessageQueue().iterator();
				for (;it.hasNext();) {
					NetMessage message = it.next();message.toString();
					NetMessageHeader header = message.getMessageHeader();
					if (header.isMesDestroy()) {
						messageDestroyStrategy (message , it);
					} else{
						entrance(message ,it);
					}
				}
			} catch (Throwable e) {
				logger.error(" >>>>>> 系统出错 " + e.getMessage() , e);
				e.printStackTrace();stop();
			}
		}
		logger.debug(" 结束执行 【基于发布与订阅的消息线程推送任务】 "+new Date(System.currentTimeMillis()).toString());
	}
	
	public static Boolean flag = true;

	public void stop (){
		flag = false;
	}
}
