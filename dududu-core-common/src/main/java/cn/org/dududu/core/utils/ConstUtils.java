package cn.org.dududu.core.utils;

import cn.org.dududu.core.annotation.Const;
import cn.org.dududu.core.consts.Codes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常量工具类
 */
public class ConstUtils {
    public static final String SCAN_PACKAGE_NAME = "cn.org.dududu.core.consts";

    public static Map<Integer, String> CODES_MAP = null;

    /**
     * 初始化代码表
     */
    private static void initCodesMap() {
        CODES_MAP = new HashMap<Integer, String>();
        List<Class<?>> constClassList = null;

        constClassList = ClassUtils.getClassList(SCAN_PACKAGE_NAME, true);
        for (Class<?> c : constClassList) {
            if (Codes.class.isAssignableFrom(c)) {
                List<KV<Integer, String>> kvList = getKvList(c);
                for (KV<Integer, String> kv : kvList) {
                    CODES_MAP.put(kv.getK(), kv.getV());
                }
            }
        }
    }

    public static List<KV<Integer, String>> getKvList(Class<?> clazz) {
        Field fields[] = clazz.getDeclaredFields();
        List<KV<Integer, String>> constList = new ArrayList<KV<Integer, String>>();
        for (Field f : fields) {
            try {
                Integer k = new Integer(f.getInt(null));
                Const cn = f.getAnnotation(Const.class);
                if (null != cn) {
                    String value = cn.value();
                    KV<Integer, String> kv = new KV<Integer, String>(k, value);
                    constList.add(kv);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return constList;
    }

    /**
     * 读取指定消息代码的消息文本
     *
     * @param code 消息代码
     * @return 返回消息文本
     */
    public static String getMsgConstName(int code) {
        if (null == CODES_MAP) ConstUtils.initCodesMap();
        return CODES_MAP.get(code);
    }

    /**
     * 获取业务常量文本
     *
     * @param clazz 常量接口/类
     * @param code  代码
     * @return 常量文本
     */
    public static String getBisConstName(Class<?> clazz, int code) {
        Field fields[] = clazz.getDeclaredFields();
        for (Field f : fields) {
            try {
                Integer k = new Integer(f.getInt(null));
                Const cn = f.getAnnotation(Const.class);
                if (null != cn) {
                    String value = cn.value();
                    if (k.intValue() == code) return value;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
