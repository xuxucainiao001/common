package cn.toseektech.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * IP相关工具包
 *
 * @author xuxu
 */
public class IpUtil {
	
	private static final String UNKNOWN="unknown";

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
	 *
	 * @return ip
	 */

	private IpUtil() {
	}

	public static final String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (null != ip && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if (ip.indexOf(",") != -1) {
				ip = ip.split(",")[0];
			}
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip != null && ip.split("\\.").length != 4) {
			ip = "127.0.0.1";
		}
		return ip;
	}
	
	public static final String getIpAddr0(ServerHttpRequest request) {
		String ip = request.getHeaders().getFirst("x-forwarded-for");
		if (null != ip && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if (ip.indexOf(",") != -1) {
				ip = ip.split(",")[0];
			}
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeaders().getFirst("Proxy-Client-IP");
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeaders().getFirst("HTTP_CLIENT_IP");
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeaders().getFirst("HTTP_X_FORWARDED_FOR");
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeaders().getFirst("X-Real-IP");
		}
		if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddress().getHostName();
		}
		if (ip != null && ip.split("\\.").length != 4) {
			ip = "127.0.0.1";
		}
		return ip;
	}
}
