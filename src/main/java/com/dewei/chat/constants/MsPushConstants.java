package com.dewei.chat.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author duansw
 */
public class MsPushConstants {

	// 发送标志为成功
	public static final String SEND_MESSAGE_SUCCEED = "1";
	// 发送标志为失败
	public static final String SEND_MESSAGE_FAIL = "0";
	// 消息删除标志
	public static final String SEND_MESSAGE_DEL = "1";
	// 消息未删除标志
	public static final String SEND_MESSAGE_UNDEL = "0";
	// 消息已读标志
	public static final String MESSAGE_READ_TAG = "1";
	// 消息未读标志
	public static final String MESSAGE_UN_READ_TAG = "0";
	// 消息访问唯一标示符
	public static final String VISITOR_TAG = "requestId";
	// 异步超时时间
	public static final long ASYNCTIMEOUT = 3 * 1000;
	// 系统编码格式
	public static final String SYSTEM_ENCODE = "UTF-8";

	public static long getAsynctimeout() {
		return ASYNCTIMEOUT;
	}

	public enum SysRetCodeConstants {
		SUCCESS("000000", "成功"), 
		MESSAGE_CONNECTION_TIMEOUT("027011","消息连接超时,请重新连接"), 
		REQUEST_FORMAT_ILLEGAL("027060", "请求数据格式非法"), 
		REQUEST_CHECK_FAILURE("027061", "请求数据校验失败"), 
		SYSTEM_ERROR("999999", "系统异常");

		private String code;
		private String message;

		private SysRetCodeConstants(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		static Map<String, SysRetCodeConstants> lookup = new HashMap<String, SysRetCodeConstants>();

		static public SysRetCodeConstants byCode(final String code) {
			return lookup.get(code);
		}

		static {
			for (final SysRetCodeConstants d : SysRetCodeConstants.values()) {
				lookup.put(d.code, d);
			}
		}
	}
}
