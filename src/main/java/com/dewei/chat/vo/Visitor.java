package com.dewei.chat.vo;

import java.util.Map;

import org.springframework.web.context.request.async.DeferredResult;

public class Visitor {

	private String requestId ; 
	
	private String sessionId;
	
	private DeferredResult<Map<String ,Object>> deferredResult = new DeferredResult<Map<String ,Object>>();
	
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public DeferredResult<Map<String, Object>> getDeferredResult() {
		return deferredResult;
	}

	public void setDeferredResult(DeferredResult<Map<String, Object>> deferredResult) {
		this.deferredResult = deferredResult;
	}
}
