package cn.jtool.core.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie操作工具
 * 
 * @author Joe
 */
public class CookieUtil {

	private CookieUtil() {
	}

	/**
	 * 按名称获取cookie
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || StringUtil.isEmpty(name)) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}

		return null;
	}

	/**
	 * 清除cookie
	 * 
	 * @param response
	 */
	public static void removeCookie(HttpServletResponse response, String name, String path, String domain) {

		Cookie cookie = new Cookie(name, null);


		// 删除Cookie时，只设置maxAge=0将不能够从浏览器中删除cookie,
		// 因为一个Cookie应当属于一个path与domain，所以删除时，Cookie的这两个属性也必须设置。
		if (path != null) {
			cookie.setPath(path);
		}
		if (domain != null) {
			cookie.setDomain(domain);
		}
		//0 不记录cookie 立即失效 1 会话级cookie，关闭浏览器失效
		cookie.setMaxAge(-1000);
		response.addCookie(cookie);
	}
}
