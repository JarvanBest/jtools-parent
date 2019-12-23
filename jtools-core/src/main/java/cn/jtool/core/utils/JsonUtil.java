package cn.jtool.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * Created by zhujiawen on 2018-03-23 10:50
 */
public final class JsonUtil {

    static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * 将一个对象转换为Json字符串
     *
     * @param values
     * @return
     */
    public static String toJson(Object values) {
        if (values == null){
            return "";
        }
        return JSON.toJSONString(values,SerializerFeature.DisableCircularReferenceDetect);
    }

    public static String toJSONString(Object vlaues) {
        return toJson(vlaues);
    }

    /**
     * 解析为一个类实例
     *
     * @param content
     * @param classz
     * @param <T>
     * @return
     */
    public static <T> T parseToObj(String content, Class<T> classz) {
        if (content == null || content.length() == 0){
            return null;
        }
        try {
            return JSON.parseObject(content, classz);
        } catch (Exception e) {
            logger.error(e.getMessage()+content, e);
        }
        return null;
    }


    /**
     * 解析为一个类实例
     *
     * @param content
     * @param parametrized
     * @param parametrized
     * @param <T>
     * @return
     */
    public static <T> T parseToObj(String content, TypeReference<T> parametrized) {

        try {
            return JSON.parseObject(content, parametrized);
        } catch (Exception e) {
            logger.error(e.getMessage()+content, e);
        }
        return null;
    }

    /**
     * 解析为一个hashmap，键值对
     *
     * @return
     * @throws Exception
     */
    public static HashMap parseToHashmap(String jsonData) {
        return parseToObj(jsonData, HashMap.class);
    }

    public static Map<String, String> parseToMap(String jsonData) {
        Map<String, String> map = null;
        try {
            map = parseToObj(jsonData, new TypeReference<HashMap<String, String>>() {
            });
        } catch (Exception e) {
            logger.error("json解析失败"+jsonData, e);
        }
        if (map == null){
            map = new HashMap<>();
        }
        return map;
    }

}
