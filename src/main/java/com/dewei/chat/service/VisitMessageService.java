package com.dewei.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dewei.chat.vo.Visitor;
/**
 * 
 * @author duansw
 */
@Service
public class VisitMessageService {

	  @Autowired
	  private MessageQueueService queueService;
	  
	  public void addAsyncResult(Visitor visitor){
		  boolean status =  queueService.addAsyncResult(visitor);
		  
		  if(!status){
			  throw new RuntimeException(" 添加订阅者或非订阅者到队列失败 ");
		  }
	  }
	  
	  public boolean removeAsyncResult(Visitor visitor){
		 if(visitor.getRequestId().equals("4100"))
			try {
				Thread.sleep(5000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 Visitor v = queueService.removeAsyncResult(visitor.getRequestId());
		 return v !=null ? true : false;
	  }
}
