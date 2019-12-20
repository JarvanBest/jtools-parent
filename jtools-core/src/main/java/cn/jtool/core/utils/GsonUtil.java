package cn.jtool.core.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class GsonUtil {
    private static Gson gson = null;

    static {
        // gson = new Gson();
        if (gson == null) {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        }
    }

    private GsonUtil() {
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String parameter(Object object) {
        String parameter = null;
        if (gson != null) {
            parameter = gson.toJson(object);
        }
        return parameter;
    }

    /**
     * 转成bean
     *
     * @param parameter
     * @param cls
     * @return
     */
    public static <T> T GsonString(String parameter, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(parameter, cls);
        }
        return t;
    }

    /**
     * 转成list
     *
     * @param parameter
     * @param cls
     * @return
     */
    public static <T> List<T> GsonToList(String parameter, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(parameter, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param parameter
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String parameter) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(parameter,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param parameter
     * @return
     */
    public static <T> Map<String, T> GsonToMaps(String parameter) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(parameter, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

}
