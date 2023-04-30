package com.yanzhen.util;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 工具类
 */
public class ReflectionUtils {
    /**
     * 通过反射，获得Class定义中声明的父类的泛型参数类型
     *
     * @param clazz 类
     * @return
     */
    public static <T> Class<T> getSuperGenericType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得定义 Class 时声明的父类的泛型参数的类型
     *
     * @param clazz 类
     * @param index 索引
     * @return 类
     */
    public static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     *
     * @param object    类
     * @param fieldName 属性
     * @return 属性
     */
    public static Field getDeclaredField(Object object, String fieldName) {
        Field field;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 设置参数
     *
     * @param object    对象
     * @param fieldName 属性
     * @param value     值
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        Field field = getDeclaredField(object, fieldName);
        assert field != null;
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取参数
     *
     * @param object    对象
     * @param fieldName 属性
     * @return 值
     */
    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getDeclaredField(object, fieldName);
        assert field != null;
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

