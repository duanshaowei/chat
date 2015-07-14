package com.dewei.chat.thread;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
/**
 * 
 * @author duansw
 */
public class PushMessageExecutor extends ThreadPoolTaskExecutor{
	private static final long serialVersionUID = 5149811698443869694L;

	private List<Runnable> runnable = new ArrayList<Runnable>();
	
	public List<Runnable> getRunnable() {
		return runnable;
	}

	public void setRunnable(List<Runnable> runnable) {
		this.runnable = runnable;
	}

	public void start () {
		for (Runnable run : runnable) {
			super.execute(run);
		}
	}
}
