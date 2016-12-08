package com.spring.utils;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Map工具包，支持路径.
 *
 * @author chylg
 */
public class MapUtil {

    private static final Logger log = LoggerFactory.getLogger(MapUtil.class);

    public static String asStr(Map<String, Object> map, String key) {
        if (StringUtils.isBlank(key) || isEmpty(map)) {
            return "";
        }
        Object obj = map.get(key);
        return obj == null ? "" : obj.toString();
    }

    public static Object val(Map<String, Object> map, String key) {
        if (StringUtils.isBlank(key) || isEmpty(map)) {
            return null;
        }
        return map.get(key);
    }

    @SuppressWarnings("unchecked")
    public static Map map(Map<String, Object> map, String key) {
        Object obj = val(map, key);
        if (obj instanceof Map) {
            return (Map) obj;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static List list(Map map, String key) {
        Object obj = val(map, key);
        if (obj instanceof List) {
            return (List) obj;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Integer asInt(Map map, String key) {
        if (StringUtils.isBlank(key) || isEmpty(map)) {
            return null;
        }
        Object obj = map.get(key);
        try {
            return new Integer(obj.toString()).intValue();
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static void copy(Map dest, Map src, String key) {
        if (StringUtils.isBlank(key) || dest != null || isEmpty(src)) {
            return;
        }
        dest.put(key, val(src, key));
    }

    public static void copyNotBlank(Map dest, Map src, String key) {
        copyNotBlank(src, key, dest, key);
    }

    public static void copyNotBlank(Map src, String srcKey, Map dest, String destKey) {
        if (dest == null || StringUtils.isBlank(destKey)) {
            return;
        }
        Object obj = val(src, srcKey);
        if (obj != null && StringUtils.isNotBlank(obj.toString())) {
            dest.put(destKey, obj);
        }
    }

    /**
     * 复制Map中指定类型的对象,
     * 过滤条件为：键名类型在keyCls中与键值类型在valCls中的对象返回
     *
     * @param srcMap
     * @param keyCls
     * @param valCls
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<T, Object> filter(Map<T, Object> srcMap, Class<T> keyCls, Class<?>[] valCls) {
        Map<T, Object> map = new HashMap<T, Object>();
        if (MapUtils.isEmpty(srcMap))
            return map;
        Set<?> key = srcMap.keySet();
        for (Iterator<?> it = key.iterator(); it.hasNext(); ) {
            Object k = it.next();
            Object v = srcMap.get(k);
            if (keyCls == k.getClass()) {
                for (Class<?> clsV : valCls) {
                    if (clsV == v.getClass()) {
                        map.put((T) k, v);
                    }
                }
            }
        }
        return map;
    }

    /**
     * eg: key1/key2[i][j]/key3.
     *
     * @param map
     * @param path
     * @return
     * @author chylg
     */
    @SuppressWarnings("unchecked")
    public static Object path(Map map, String path) {
        if (StringUtils.isBlank(path) || isEmpty(map)) {
            return null;
        }
        if (path.startsWith("/")) {
            path = path.substring(1, path.length());
        }
        String[] keys = path.split("/");
        Object obj = map;
        int len = keys.length;
        String key = "";
        for (int i = 0; i < len; i++) {
            if (map == null || map.isEmpty()) {
                return null;
            }
            key = keys[i].trim();
            if (isArrayKey(key)) {
                obj = array((Map) obj, key);
            } else {
                if (obj instanceof Map) {
                    obj = val((Map) obj, key);
                }

            }
        }
        return obj;
    }

    @SuppressWarnings("unchecked")
    public static Map getMap(Map map, String path) {
        if (StringUtils.isBlank(path) || isEmpty(map)) {
            return null;
        }
        if (path.startsWith("/")) {
            path = path.substring(1, path.length());
        }
        String[] keys = path.split("/");
        Object obj = map;
        Map objMap = null;
        int len = keys.length;
        String key = "";
        for (int i = 0; i < len; i++) {
            if (map == null || map.isEmpty()) {
                return null;
            }
            key = keys[i].trim();
            if (isArrayKey(key)) {
                obj = array((Map) obj, key);
            } else {
                if (obj instanceof Map) {
                    obj = val((Map) obj, key);
                }

            }
        }
        if (obj instanceof Map) {
            objMap = (Map) obj;
        }
        return objMap;
    }

    public static boolean isBlankValue(Map map, String key) {
        Object obj = val(map, key);
        if (obj == null || StringUtils.isBlank(obj.toString())) {
            return true;
        }
        return false;
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return map != null && !map.isEmpty();
    }

    private static boolean isArrayKey(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        if (key.endsWith("]")) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private static Object array(Map map, String key) {
        if (StringUtils.isBlank(key) || isEmpty(map)) {
            return null;
        }
        String[] keys = StringUtils.split(key, "][");
        List lst = list(map, keys[0]);
        Object obj = null;
        int index = -1;
        int len = keys.length;
        for (int i = 1; i < len; i++) {
            index = Integer.parseInt(keys[i]);
            obj = lst.get(index);
            if (obj instanceof List) {
                lst = (List) obj;
            } else if (i == len - 1) {
                return obj;
            } else {
                return null;
            }
        }
        return obj;
    }

}
