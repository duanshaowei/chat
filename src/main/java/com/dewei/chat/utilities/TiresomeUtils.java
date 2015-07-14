package com.dewei.chat.utilities;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class TiresomeUtils {
	private static String serverPath;

	public synchronized static void setServerPath(ServletRequest request) {
		if (serverPath == null) {
			String host = request.getServerName();
			int port = request.getServerPort();
			serverPath = "http://" + host + ":" + port;
		}
	}

	public static String getServerPath() {
		if (serverPath == null) {
			throw new RuntimeException("serverPath 为空，什么情况。");
		}
		return serverPath;
	}

	/**
	 * 获取访问者的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public synchronized static String getClientIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
