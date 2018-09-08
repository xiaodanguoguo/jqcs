package com.ebase.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class FieldValidatorUtil {

    private static String REGEX_CHINESE_CHARACTER = "^[\\u4e00-\\u9fa5]*$";

    /**
     * 对象非空验证
     *
     * @param obj        需要验证的类
     * @param properties 需要验证的字段名
     * @return ResultObj 返回固定格式结果:1 对象字段是否非空 2 首次出现的[非法空值]字段名
     */
    public static ResultObj validateFields(Object obj, String... properties) {

        boolean flag = true;
        StringBuilder msg = new StringBuilder("");
        if (null == obj || null == properties) {
            flag = false;
        } else {

            Field[] fields = {};
            for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    Field[] field = clazz.getDeclaredFields();
                    fields = ArrayUtils.addAll(fields, field);
                } catch (Exception ignored) {

                }
            }
            String[] propertiesArray = properties.clone();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                for (String property : propertiesArray) {
                    if (property.equals(fieldName)) {
                        Object val = null;
                        try {
                            val = field.get(obj);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        if (null == val) {
                            flag = false;
                            msg.append(fieldName);
                            break;
                        }
                    }
                }
                if (!flag) {
                    break;
                }
            }
        }
        return new ResultObj(flag, msg.toString());
    }

    /**
     * 不能输入空格和中文，特殊字符，
     * 长度限制为2-15位，
     * 可以输入英文（不限制大小写）和0-9数字
     *
     * @param code 编码
     * @return ResultObj
     */
    public static ResultObj validateCode(String code) {
        String regexCode = "^[0-9A-Za-z]{2,15}$";
        boolean flag = null != code && Pattern.compile(regexCode).matcher(code).find();
        String msg = flag ? "" : "编码为2-15位的字母或数字";
        return new ResultObj(flag, msg);
    }

    /**
     * 验证字段是否为数字
     *
     * @param obj        需要验证的类
     * @param properties 需要验证的字段名
     * @return 返回固定格式结果:1 对象字段是否非空 2 首次出现的[非法空值]字段名
     */
    public static ResultObj isNumValidate(Object obj, String... properties) {

        boolean flag = true;
        StringBuilder msg = new StringBuilder("");
        if (null == obj || null == properties) {
            flag = false;
        } else {

            Field[] fields = {};
            for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    Field[] field = clazz.getDeclaredFields();
                    fields = ArrayUtils.addAll(fields, field);
                } catch (Exception ignored) {

                }
            }
            String[] propertiesArray = properties.clone();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                for (String property : propertiesArray) {
                    if (property.equals(fieldName)) {
                        Object val = null;
                        try {
                            val = field.get(obj);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        String valStr = String.valueOf(val);
                        if (!valStr.matches("^[0-9]*$") || valStr.length() > 3) {
                            flag = false;
                            msg.append(fieldName);
                            break;
                        }
                    }
                }
                if (!flag) {
                    break;
                }
            }
        }
        return new ResultObj(flag, msg.toString());
    }

    /**
     * 验证返回结果
     */
    public static class ResultObj {

        private boolean flag;
        private String msg;

        ResultObj(boolean flag, String msg) {
            this.flag = flag;
            this.msg = msg;
        }

        public boolean isFlag() {
            return flag;
        }

        public String getMsg() {
            return msg;
        }

        @Override
        public String toString() {
            return "ResultObj{" + "flag=" + flag + ", msg='" + msg + '\'' + '}';
        }
    }
}
