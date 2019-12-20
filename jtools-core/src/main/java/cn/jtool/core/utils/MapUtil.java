package cn.jtool.core.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 描述：map工具类
 * Created by 002267 on 2017/9/26.
 */
public class MapUtil {

	/**
	 * 描述：去除map中value为null和""的键值对
	 * Created 002267 by on 2017-09-26 14:34:06
	 *
	 * @param
	 * @return
	 */
	public static Map<String, Object> mapRemoveEmpty(Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		map.keySet().stream().filter(m -> !StringUtil.isEmpty((String) map.get(m))).forEach(m -> result.put(m, map.get(m)));
		return result;
	}

    /**
     * 描述：拼接map参数到指定url
     * Created by zhujiawen on 2018-10-26 12:05:03 
     * 
     * @param url,params
     * @return String
     */
	private static String map2Url(String url, Map<String, Object> params) {
		StringBuilder sbd = new StringBuilder();
        params.keySet().forEach((k) ->(CommonUtil.isEmpty(sbd) ? sbd.append("?") : sbd.append("&")).append(k).append("=").append(params.get(k)));
		return url + sbd.toString();
	}




	public static String getUrl(String url, HashMap<String, String> params) {
		// 添加url参数
		if (params != null) {
			Iterator<String> it = params.keySet().iterator();
			StringBuffer sb = null;
			while (it.hasNext()) {
				String key = it.next();
				String value = params.get(key);
				if (sb == null) {
					sb = new StringBuffer();
					sb.append("?");
				} else {
					sb.append("&");
				}
				sb.append(key);
				sb.append("=");
				sb.append(value);
			}
			url += !CommonUtil.isEmpty(sb) ? sb.toString() : "";
		}
		return url;
	}

    /**
     * Object转Map
     * @param
     */
    private static Map<String, Object> ObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> cls = obj.getClass();
        System.out.println(cls);
        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

	//去除下划线并让他下个字母大写
	public static String replaceUnderlineAndfirstToUpper(String srcStr, String flag) {
		StringBuilder newString = new StringBuilder();
		int first = 0;
		while (srcStr.contains(flag)) {
			first = srcStr.indexOf(flag);
			if (first != srcStr.length()) {
				newString.append(srcStr, 0, first);
				srcStr = srcStr.substring(first + flag.length());
				srcStr = firstCharacterToCapital(srcStr);
			}
		}
		newString.append(srcStr);
		return newString.toString();
	}


	// 首字母大写
	static String firstCharacterToCapital(String str) {
		char[] chrs = str.toCharArray();
		if (chrs[0] >= 'a' && chrs[0] <= 'z')
			chrs[0] -= 32;
		return String.valueOf(chrs);
	}

}
