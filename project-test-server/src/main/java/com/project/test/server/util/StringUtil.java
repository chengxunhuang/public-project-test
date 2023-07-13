package com.project.test.server.util;

import com.project.test.server.constant.Constants;

import static com.project.test.server.constant.Constants.*;

/**
 * @author rean
 * @version 1.0.0
 * @description: StringUtil 本次重写主要是加空指针判断
 * @date 2021/12/30 10:07
 */

public class StringUtil {


//    -------------去空格 start-------------------------------

    /**
     * 给定字符串数组全部做去首尾空格
     *
     * @param strs 字符串数组
     */
    public static void trim(String[] strs) {
        if (null == strs) {
            return;
        }
        String str;
        for (int i = 0; i < strs.length; i++) {
            str = strs[i];
            if (null != str) {
                strs[i] = trim(str);
            }
        }
    }

    public static String trim(CharSequence str) {
        return (null == str) ? null : trim(str, 0);
    }

    public static String trim(CharSequence str, int mode) {
        String result;
        if (str == null) {
            result = null;
        } else {
            int length = str.length();
            int start = 0;
            int end = length;// 扫描字符串头部
            if (mode <= 0) {
                while ((start < end) && (CharacterUtil.isBlankChar(str.charAt(start)))) {
                    start++;
                }
            }// 扫描字符串尾部
            if (mode >= 0) {
                while ((start < end) && (CharacterUtil.isBlankChar(str.charAt(end - 1)))) {
                    end--;
                }
            }
            if ((start > 0) || (end < length)) {
                result = str.toString().substring(start, end);
            } else {
                result = str.toString();
            }
        }

        return result;
    }


    /**
     * 重复某个字符
     *
     * @param c     被重复的字符
     * @param count 重复的数目，如果小于等于0则返回""
     * @return 重复字符字符串
     */
    public static String repeat(char c, int count) {
        if (count <= 0) {
            return STR_EMPTY;
        }

        char[] result = new char[count];
        for (int i = 0; i < count; i++) {
            result[i] = c;
        }
        return new String(result);
    }

    /**
     * 重复某个字符串
     *
     * @param str   被重复的字符
     * @param count 重复的数目
     * @return 重复字符字符串
     */
    public static String repeat(CharSequence str, int count) {
        if (null == str) {
            return null;
        }
        if (count <= 0 || str.length() == 0) {
            return STR_EMPTY;
        }
        if (count == 1) {
            return str.toString();
        }

        // 检查
        final int len = str.length();
        final long longSize = (long) len * (long) count;
        final int size = (int) longSize;
        if (size != longSize) {
            throw new ArrayIndexOutOfBoundsException("Required String length is too large: " + longSize);
        }

        final char[] array = new char[size];
        str.toString().getChars(0, len, array, 0);
        int n;
        for (n = len; n < size - n; n <<= 1) {// n <<= 1相当于n *2
            System.arraycopy(array, 0, array, n, n);
        }
        System.arraycopy(array, 0, array, n, size - n);
        return new String(array);
    }
    //    -------------去空格 end-------------------------------

    /**
     * 重复某个字符串并通过分界符连接
     *
     * @param str         被重复的字符串
     * @param count       数量
     * @param conjunction 分界符
     * @return 连接后的字符串
     * @since 4.0.1
     */
    public static String repeatAndJoin(CharSequence str, int count, CharSequence conjunction) {
        if (count <= 0) {
            return STR_EMPTY;
        }
        StringBuffer builder = new StringBuffer();
        boolean isFirst = true;
        while (count-- > 0) {
            if (isFirst) {
                isFirst = false;
            } else if (CharacterUtil.isNotEmpty(conjunction)) {
                builder.append(conjunction);
            }
            builder.append(str);
        }
        return builder.toString();
    }


    public static String format(CharSequence template, Object... params) {
        if (null == template) {
            return STR_NULL;
        }
        if ((params == null || params.length == 0)) {
            return template.toString();
        }
        return format(template.toString(), params);
    }

    private static String format(final String strPattern, final Object... argArray) {
        final int strPatternLength = strPattern.length();

        // 初始化定义好的长度以获得更好的性能
        StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

        int handledPosition = 0;// 记录已经处理到的位置
        int delimIndex;// 占位符所在位置
        int length = argArray.length;
        for (int argIndex = 0; argIndex < length; argIndex++) {
            delimIndex = strPattern.indexOf(Constants.STR_EMPTY_JSON, handledPosition);
            if (delimIndex == -1) {// 剩余部分无占位符
                if (handledPosition == 0) { // 不带占位符的模板直接返回
                    return strPattern;
                }
                // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                sbuf.append(strPattern, handledPosition, strPatternLength);
                return sbuf.toString();
            }

            // 转义符
            if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == Constants.C_BACKSLASH) {// 转义符
                if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == Constants.C_BACKSLASH) {// 双转义符
                    // 转义符之前还有一个转义符，占位符依旧有效
                    sbuf.append(strPattern, handledPosition, delimIndex - 1);
                    sbuf.append(argArray[argIndex]);
                    handledPosition = delimIndex + 2;
                } else {
                    // 占位符被转义
                    argIndex--;
                    sbuf.append(strPattern, handledPosition, delimIndex - 1);
                    sbuf.append(Constants.C_DELIM_START);
                    handledPosition = delimIndex + 1;
                }
            } else {// 正常占位符
                sbuf.append(strPattern, handledPosition, delimIndex);
                sbuf.append(argArray[argIndex]);
                handledPosition = delimIndex + 2;
            }
        }

        // append the characters following the last {} pair.
        // 加入最后一个占位符后所有的字符
        sbuf.append(strPattern, handledPosition, strPattern.length());

        return sbuf.toString();
    }

    /**
     * 是否包含，同String.container
     *
     * @return boolean
     * @param: searchStr
     * @author
     * @date 2022/1/4 11:31
     */
    public static boolean contains(CharSequence str, CharSequence searchStr) {
        if (null == str || null == searchStr) {
            return false;
        }
        return str.toString().contains(searchStr);
    }

    /**
     * strUnderToCamel-> 大写转驼峰
     *
     * @return String
     * @param: camel
     * @author
     * @date 2022/1/4 11:31
     */
    public static String toCamel(String camel) {
        char[] chars = camel.toLowerCase().toCharArray();
        StringBuilder sb = chars[0] == C_UNDER ? new StringBuilder() : new StringBuilder().append(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == C_UNDER) {
                continue;
            } else if (chars[i - 1] == C_UNDER) {
                sb.append(Character.toUpperCase(chars[i]));
            } else {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @return String
     * @param: under
     * @author
     * @date 2022/1/4 11:31
     */
    public static String toUnder(String under) {
        char[] chars = under.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length - 1; i++) {

            sb.append(chars[i]);
            if (Character.isUpperCase(chars[i + 1])) {
                sb.append(C_UNDER);
            }
        }
        return sb.append(chars[chars.length - 1]).toString().toUpperCase() ;
    }

}
