package com.platon.browser.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.platon.browser.bean.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 公共工具类
 *
 * @date 2021/4/17
 */
@Slf4j
public class CommonUtil {

    /**
     * 支持lamda的链式判空
     * 用法：ofNullable(() -> obj.getObj1().getObj2().getObj3()).ifPresent(res -> System.out.println(res));
     * 解释：会自动判断obj、getObj1()、getObj2()、getObj3()是否为空，如果getObj3()的值不为空，则打印。例如getObj2()为空，结果返回null，而不是报空指针。
     *
     * @param resolver
     * @return java.util.Optional<T>
     * @date 2021/4/19
     */
    public static <T> Optional<T> ofNullable(Supplier<T> resolver) {
        try {
            T result = resolver.get();
            return Optional.ofNullable(result);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    /**
     * 创建trace-id
     *
     * @param
     * @return java.lang.String
     * @date 2021/4/17
     */
    public static String createTraceId() {
        return StrUtil.removeAll(UUID.randomUUID().toString(), "-");
    }

    /**
     * 添加全局链路ID--默认生成的链路ID
     *
     * @param
     * @return void
     * @date 2021/4/22
     */
    public static void putTraceId() {
        try {
            MDC.put(CommonConstant.TRACE_ID, createTraceId());
        } catch (Exception e) {
            log.error("添加链路ID异常", e);
        }
    }

    /**
     * 添加全局链路ID--默认生成的链路ID
     *
     * @param customKey 自定义key
     * @return void
     * @date 2021/5/31
     */
    public static void putCustomTraceId(String customKey) {
        try {
            MDC.put(customKey, createTraceId());
        } catch (Exception e) {
            log.error("添加链路ID异常", e);
        }
    }

    /**
     * 添加全局链路ID
     *
     * @param traceId 链路ID
     * @return void
     * @date 2021/4/22
     */
    public static void putTraceId(String traceId) {
        try {
            if (StrUtil.isNotBlank(traceId)) {
                MDC.put(CommonConstant.TRACE_ID, traceId);
            } else {
                log.error("请输入链路ID");
            }
        } catch (Exception e) {
            log.error("添加链路ID异常", e);
        }
    }

    /**
     * 获取全局链路ID
     *
     * @param
     * @return java.lang.String
     * @date 2021/4/23
     */
    public static String getTraceId() {
        String traceId = "";
        try {
            traceId = MDC.get(CommonConstant.TRACE_ID);
        } catch (Exception e) {
            log.error("获取全局链路ID异常", e);
        }
        return traceId;
    }

    /**
     * 删除全局链路ID
     *
     * @param
     * @return void
     * @date 2021/4/22
     */
    public static void removeTraceId() {
        try {
            MDC.remove(CommonConstant.TRACE_ID);
        } catch (Exception e) {
            log.error("删除链路ID异常", e);
        }
    }

    /**
     * 删除全局链路ID
     *
     * @param customKey 自定义key
     * @return void
     * @date 2021/5/31
     */
    public static void removeCustomTraceId(String customKey) {
        try {
            MDC.remove(customKey);
        } catch (Exception e) {
            log.error("删除链路ID异常", e);
        }
    }


    public static <T> T map2Bean(Map<String,Object> map, Class<T> beanClass) {
        try {
            T object = beanClass.newInstance();
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }

                field.setAccessible(true);
                if (map.containsKey(field.getName())) {

                    Object value = map.get(field.getName());

                    if (field.getType() == BigInteger.class ){
                        if (value instanceof BigInteger) {
                            field.set(object, map.get(field.getName()));
                        }else if (value instanceof Integer || value instanceof Long) {
                            field.set(object, new BigInteger(String.valueOf(map.get(field.getName()))));
                        }else if (value instanceof String) {
                            if (((String) value).startsWith("0x")){
                                field.set(object, new BigInteger(((String)map.get(field.getName())).substring(2),16));
                            }else{
                                field.set(object, new BigInteger((String)map.get(field.getName()),10));
                            }
                        }
                    }else{
                        field.set(object, map.get(field.getName()));
                    }


                }
            }
            return object;
        }catch (Exception e){
            log.error("map转成" + beanClass.getSimpleName() + "对象出错", e);
        }
        return null;
    }

}
