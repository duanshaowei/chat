package com.dewei.chat.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.dewei.chat.constants.MsPushConstants.SysRetCodeConstants;
import com.dewei.chat.exception.AsyncRequestTimeOut;
import com.dewei.chat.utilities.MapUtils;
import com.dewei.chat.vo.NetMessage;
import com.dewei.chat.vo.NetMessageBody;
import com.dewei.chat.vo.Visitor;
/**
 * 
 * @author duansw
 */
@Component
public class MessageQueueService {
	
	public static final LinkedBlockingQueue<NetMessage> MESSAGE_QUEUE = new LinkedBlockingQueue<NetMessage>();
	
	public static final ConcurrentHashMap<String ,Visitor> CONTEXT_QUEUE = new ConcurrentHashMap<String ,Visitor>();
	
	private static final long OFFER_TIMEOUT = 5;
	
	public ConcurrentHashMap<String ,Visitor> getDefrredResultQueue(){
		return CONTEXT_QUEUE ; 
	}
	
	public BlockingQueue<NetMessage> getMessageQueue (){
		return MESSAGE_QUEUE ; 
	}
	
	public boolean addAsyncResult (Visitor visitor)  {
		if(getDefrredResultQueue().containsKey(visitor.getRequestId())){
			return getDefrredResultQueue().replace(visitor.getRequestId(), getDefrredResultQueue().get(visitor.getRequestId()), visitor);
		}
		
		getDefrredResultQueue().put(visitor.getRequestId(), visitor);
		
		return true;
	}
	
	public static void pushMes(Visitor visitor, NetMessage message ,NetMessageBody messageBody){
		Set<String> mesReceiverTarget = message.getMessageHeader().getMesReceiverTarget();
		messageBody.setMemo(SysRetCodeConstants.SUCCESS.getMessage());
		messageBody.setOperateCode(SysRetCodeConstants.SUCCESS.getCode());
		Map<String, Object> response =  new HashMap<String, Object>();
		try {
			response = MapUtils.toMapObject(messageBody);
		} catch (Exception e) {
			throw new RuntimeException(" NetMessageBody 转换Map出错  ",e);
		}
		boolean status = visitor.getDeferredResult().setResult(response);
		if(!status)throw new AsyncRequestTimeOut(" DeferredResult visitor:"+visitor+" 请求超时. ");
		mesReceiverTarget.remove(visitor.getRequestId());
		message.getMessageHeader().getMesReceipter().add(visitor.getSessionId());
	}
	
	public boolean removeAsyncResult (String key , Visitor value){
		return getDefrredResultQueue().remove(key , value);
	}
	
	public Visitor removeAsyncResult (String key){
		return getDefrredResultQueue().remove(key);
	}
	
	public synchronized boolean offerMessage (NetMessage netMessage){
		try {
			return getMessageQueue().offer(netMessage, OFFER_TIMEOUT, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(" 增加消息到队列线程中断 ;details:"+netMessage.toString(),e);
		}
	}
	
	public int size (){
		return getMessageQueue().size();
	}
}
