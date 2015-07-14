package com.dewei.chat.exception;

public class AsyncRequestTimeOut extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AsyncRequestTimeOut(Throwable e) {
		super(e);
	}

	public AsyncRequestTimeOut(String msg) {
		super(msg);
	}
	
	public AsyncRequestTimeOut(String msg,Throwable e) {
		super(msg,e);
	}
}
