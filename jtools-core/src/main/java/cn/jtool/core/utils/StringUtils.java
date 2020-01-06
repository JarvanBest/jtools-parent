package cn.jtool.core.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017-03-10.
 */
public class StringUtils {

    private static final Logger
            LOG = LoggerFactory.getLogger(StringUtils.class);

    public static String trim(String s) {
        if (isNotEmpty(s))
            s = s.trim();
        return s;
    }

    /**
     * 截断后缀
     * @param s
     * @param code
     * @return
     */
    public static String trim(String s, final String code) {
        if (s == null) {
            return null;
        }
        s = s.trim();
        if (StringUtils.isNullOrEmpty(code)) {
            return s;
        }
        while (s.endsWith(code)) {
            s = s.substring(0, s.length() - 1).trim();
        }
        return s;
    }

    public static void append(StringBuilder builder, String s, Object... args) {
        builder.append(String.format(s, args));
    }

    public static String getIP() {
        InetAddress ia = null;
        try {
            ia = ia.getLocalHost();
            String localip = ia.getHostAddress();
            return localip;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }

    /**
     * 隐藏中间四位手机号码
     *
     * @param phone
     * @return
     */
    public static String hideMiddleFourPhone(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static boolean equalsIgnoreCase(String first, String sencord) {
        if (first == null && sencord == null) {
            return true;
        }
        return first != null && first.equalsIgnoreCase(sencord);
    }

    public static boolean equalsSuccess(String value) {
        return "SUCCESS".equalsIgnoreCase(value);
    }

    public static boolean equalsFail(String value) {
        return "FAIL".equalsIgnoreCase(value);
    }

    /**
     * 函数功能说明 ： 判断字符串是否为空 . 修改者名字： 修改日期： 修改内容：
     *
     * @return boolean
     * @throws
     * @参数： @param str
     * @参数： @return
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0) return true;
        for (int var2 = 0; var2 < str.length(); ++var2) {
            char var3 = str.charAt(var2);
            //存在不为空的字符串
            //10:换行键 12:换页键,13:回车键 9:水平制表符 32:空格
            if (var3 != 10 && var3 != 12 && var3 != 13 && var3 != 9 && var3 != 32) {
                return false;
            }
        }
        return true;
    }


    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 函数功能说明 ： 判断字符串是否为空 . 修改者名字： 修改日期： 修改内容：
     * null字符串，空字符串，空格均为空
     *
     * @return boolean
     * @throws
     * @参数： @param str
     * @参数： @return
     */
    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        if ("null".equalsIgnoreCase(str)) {
            return true;
        }
        for (int var2 = 0; var2 < str.length(); ++var2) {
            char var3 = str.charAt(var2);
            //存在不为空的字符串
            //10:换行键 12:换页键,13:回车键 9:水平制表符 32:空格
            if (var3 != 10 && var3 != 12 && var3 != 13 && var3 != 9 && var3 != 32) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotNullOrEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    /**
     * 过滤数据库字符串，屏蔽注入
     *
     * @param value
     * @return
     */
    public static String sqlStringFilter(String value) {
        if (value == null || value.length() == 0) return value;
        // SQL过滤，防止注入
        String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
                + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
        Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        if (sqlPattern.matcher(value).find()) {
            value = "";
        }
        return value;
    }


    public static boolean isTrueString(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof Integer) {
            return (Integer) value == 1;
        }
        if (value instanceof Long) {
            return (Long) value == 1;
        }
        if (value instanceof Short) {
            return (Short) value == 1;
        }
        return isTrueString(value.toString());
    }


    public static String getFirstChar(String str) {
        String result = str;
        if (isNullOrEmpty(result)
                || result.length() <= 1) return result;
        return result.substring(0, 1);
    }

    public static boolean isTrueString(String str) {
        return isNotNullOrEmpty(str) &&
                (
                        str.equals("1")
                                || str.equalsIgnoreCase("true")
                                || str.equalsIgnoreCase("真")
                                || str.equalsIgnoreCase("是")
                                || str.equalsIgnoreCase("对")
                                || str.equalsIgnoreCase("开")
                                || str.equalsIgnoreCase("on")
                                || str.equalsIgnoreCase("open")
                );
    }

    /**
     * 函数功能说明 ： 判断对象数组是否为空. 修改者名字： 修改日期： 修改内容：
     *
     * @return boolean
     * @throws
     * @参数： @param obj
     * @参数： @return
     */
    public static boolean isEmpty(Object[] obj) {
        return null == obj || 0 == obj.length;
    }

    /**
     * 函数功能说明 ： 判断对象是否为空. 修改者名字： 修改日期： 修改内容：
     *
     * @return boolean
     * @throws
     * @参数： @param obj
     * @参数： @return
     */
    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).trim().isEmpty();
        }
        return !(obj instanceof Number) ? false : false;
    }

    /**
     * 函数功能说明 ： 判断集合是否为空. 修改者名字： 修改日期： 修改内容：
     *
     * @return boolean
     * @throws
     * @参数： @param obj
     * @参数： @return
     */
    public static boolean isEmpty(List<?> obj) {
        return null == obj || obj.isEmpty();
    }

    /**
     * 函数功能说明 ： 判断Map集合是否为空. 修改者名字： 修改日期： 修改内容：
     *
     * @return boolean
     * @throws
     * @参数： @param obj
     * @参数： @return
     */
    public static boolean isEmpty(Map<?, ?> obj) {
        return null == obj || obj.isEmpty();
    }

    /**
     * 函数功能说明 ： 获得文件名的后缀名. 修改者名字： 修改日期： 修改内容：
     *
     * @return String
     * @throws
     * @参数： @param fileName
     * @参数： @return
     */
    public static String getExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取去掉横线的长度为32的UUID串.
     *
     * @return uuid.
     * @author WuShuicheng.
     */
    public static String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取带横线的长度为36的UUID串.
     *
     * @return uuid.
     * @author WuShuicheng.
     */
    public static String get36UUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 验证一个字符串是否完全由纯数字组成的字符串，当字符串为空时也返回false.
     *
     * @param str 要判断的字符串 .
     * @return true or false .
     * @author WuShuicheng .
     */
    public static boolean isNumeric(String str) {
        if (StringUtils.isNullOrEmpty(str)) {
            return false;
        } else {
            return str.matches("\\d*");
        }
    }

    /**
     * 计算采用utf-8编码方式时字符串所占字节数
     *
     * @param content
     * @return
     */
    public static int getByteSize(String content) {
        int size = 0;
        if (null != content) {
            try {
                // 汉字采用utf-8编码时占3个字节
                size = content.getBytes("utf-8").length;
            } catch (UnsupportedEncodingException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return size;
    }

    /**
     * 函数功能说明 ： 截取字符串拼接in查询参数. 修改者名字： 修改日期： 修改内容：
     *
     * @return String
     * @throws
     * @参数： @param ids
     * @参数： @return
     */
    public static List<String> getInParam(String param) {
        boolean flag = param.contains(",");
        List<String> list = new ArrayList<String>();
        if (flag) {
            list = Arrays.asList(param.split(","));
        } else {
            list.add(param);
        }
        return list;
    }

    public static boolean hasText(String str) {
        if (!hasLength(str)) {
            return false;
        } else {
            int strLen = str.length();

            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean hasLength(String str) {
        return str != null && str.length() > 0;
    }

    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (str != null && prefix != null) {
            if (str.startsWith(prefix)) {
                return true;
            } else if (str.length() < prefix.length()) {
                return false;
            } else {
                String lcStr = str.substring(0, prefix.length()).toLowerCase();
                String lcPrefix = prefix.toLowerCase();
                return lcStr.equals(lcPrefix);
            }
        } else {
            return false;
        }
    }

    public static String clean(String in) {
        String out = in;
        if (in != null) {
            out = in.trim();
            if (out.equals("")) {
                out = null;
            }
        }

        return out;
    }


    /**
     * 右补位，左对齐
     *
     * @param oriStr 原字符串
     * @param len    目标字符串长度
     * @param alexin 补位字符
     * @return 目标字符串
     */
    public static String padLeft(String oriStr, int len, char alexin) {
        String str = "";
        int strlen = oriStr.length();
        if (strlen < len) {
            for (int i = 0; i < len - strlen; i++) {
                str = str + alexin;
            }
        }
        str = str + oriStr;
        return str;
    }

    /**
     * 左补位，右对齐
     *
     * @param oriStr 原字符串
     * @param len    目标字符串长度
     * @param alexin 补位字符
     * @return 目标字符串
     */
    public static String padRight(String oriStr, int len, char alexin) {
        String str = "";
        int strlen = oriStr.length();
        if (strlen < len) {
            for (int i = 0; i < len - strlen; i++) {
                str = str + alexin;
            }
        }
        str = oriStr + str;
        return str;
    }


    // Contains
    //-----------------------------------------------------------------------

    /**
     * <p>Checks if CharSequence contains a search character, handling {@code null}.
     * This method uses {@link String#indexOf(int)} if possible.</p>
     * <p>
     * <p>A {@code null} or empty ("") CharSequence will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.contains(null, *)    = false
     * StringUtils.contains("", *)      = false
     * StringUtils.contains("abc", 'a') = true
     * StringUtils.contains("abc", 'z') = false
     * </pre>
     *
     * @param seq        the CharSequence to check, may be null
     * @param searchChar the character to find
     * @return true if the CharSequence contains the search character,
     * false if not or {@code null} string input
     * @since 3.0 Changed signature from contains(String, int) to contains(CharSequence, int)
     */
    public static boolean contains(final CharSequence seq, final int searchChar) {
        if (isEmpty(seq)) {
            return false;
        }
        return CharSequenceUtils.indexOf(seq, searchChar, 0) >= 0;
    }

    /**
     * <p>Checks if CharSequence contains a search CharSequence, handling {@code null}.
     * This method uses {@link String#indexOf(String)} if possible.</p>
     * <p>
     * <p>A {@code null} CharSequence will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.contains(null, *)     = false
     * StringUtils.contains(*, null)     = false
     * StringUtils.contains("", "")      = true
     * StringUtils.contains("abc", "")   = true
     * StringUtils.contains("abc", "a")  = true
     * StringUtils.contains("abc", "z")  = false
     * </pre>
     *
     * @param seq       the CharSequence to check, may be null
     * @param searchSeq the CharSequence to find, may be null
     * @return true if the CharSequence contains the search CharSequence,
     * false if not or {@code null} string input
     * @since 3.0 Changed signature from contains(String, String) to contains(CharSequence, CharSequence)
     */
    public static boolean contains(final CharSequence seq, final CharSequence searchSeq) {
        if (seq == null || searchSeq == null) {
            return false;
        }
        return CharSequenceUtils.indexOf(seq, searchSeq, 0) >= 0;
    }

    /**
     * <p>Checks if CharSequence contains a search CharSequence irrespective of case,
     * handling {@code null}. Case-insensitivity is defined as by
     * {@link String#equalsIgnoreCase(String)}.
     * <p>
     * <p>A {@code null} CharSequence will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.contains(null, *) = false
     * StringUtils.contains(*, null) = false
     * StringUtils.contains("", "") = true
     * StringUtils.contains("abc", "") = true
     * StringUtils.contains("abc", "a") = true
     * StringUtils.contains("abc", "z") = false
     * StringUtils.contains("abc", "A") = true
     * StringUtils.contains("abc", "Z") = false
     * </pre>
     *
     * @param str       the CharSequence to check, may be null
     * @param searchStr the CharSequence to find, may be null
     * @return true if the CharSequence contains the search CharSequence irrespective of
     * case or false if not or {@code null} string input
     * @since 3.0 Changed signature from containsIgnoreCase(String, String) to containsIgnoreCase(CharSequence, CharSequence)
     */
    public static boolean containsIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        final int len = searchStr.length();
        final int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given CharSequence contains any whitespace characters.
     *
     * @param seq the CharSequence to check (may be {@code null})
     * @return {@code true} if the CharSequence is not empty and
     * contains at least 1 whitespace character
     * @see Character#isWhitespace
     * @since 3.0
     */
    // From org.springframework.util.StringUtils, under Apache License 2.0
    public static boolean containsWhitespace(final CharSequence seq) {
        if (isEmpty(seq)) {
            return false;
        }
        final int strLen = seq.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(seq.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    // IndexOfAny chars
    //-----------------------------------------------------------------------

    /**
     * <p>Search a CharSequence to find the first index of any
     * character in the given set of characters.</p>
     * <p>
     * <p>A {@code null} String will return {@code -1}.
     * A {@code null} or zero length search array will return {@code -1}.</p>
     *
     * <pre>
     * StringUtils.indexOfAny(null, *)                = -1
     * StringUtils.indexOfAny("", *)                  = -1
     * StringUtils.indexOfAny(*, null)                = -1
     * StringUtils.indexOfAny(*, [])                  = -1
     * StringUtils.indexOfAny("zzabyycdxx",['z','a']) = 0
     * StringUtils.indexOfAny("zzabyycdxx",['b','y']) = 3
     * StringUtils.indexOfAny("aba", ['z'])           = -1
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null
     * @param searchChars the chars to search for, may be null
     * @return the index of any of the chars, -1 if no match or null input
     * @since 3.0 Changed signature from indexOfAny(String, char[]) to indexOfAny(CharSequence, char...)
     */
    public static int indexOfAny(final CharSequence cs, final char... searchChars) {
        if (isEmpty(cs) || searchChars == null || searchChars.length == 0) {
            return -1;
        }
        final int csLen = cs.length();
        final int csLast = csLen - 1;
        final int searchLen = searchChars.length;
        final int searchLast = searchLen - 1;
        for (int i = 0; i < csLen; i++) {
            final char ch = cs.charAt(i);
            for (int j = 0; j < searchLen; j++) {
                if (searchChars[j] == ch) {
                    if (i < csLast && j < searchLast && Character.isHighSurrogate(ch)) {
                        // ch is a supplementary character
                        if (searchChars[j + 1] == cs.charAt(i + 1)) {
                            return i;
                        }
                    } else {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * <p>Search a CharSequence to find the first index of any
     * character in the given set of characters.</p>
     * <p>
     * <p>A {@code null} String will return {@code -1}.
     * A {@code null} search string will return {@code -1}.</p>
     *
     * <pre>
     * StringUtils.indexOfAny(null, *)            = -1
     * StringUtils.indexOfAny("", *)              = -1
     * StringUtils.indexOfAny(*, null)            = -1
     * StringUtils.indexOfAny(*, "")              = -1
     * StringUtils.indexOfAny("zzabyycdxx", "za") = 0
     * StringUtils.indexOfAny("zzabyycdxx", "by") = 3
     * StringUtils.indexOfAny("aba","z")          = -1
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null
     * @param searchChars the chars to search for, may be null
     * @return the index of any of the chars, -1 if no match or null input
     * @since 3.0 Changed signature from indexOfAny(String, String) to indexOfAny(CharSequence, String)
     */
    public static int indexOfAny(final CharSequence cs, final String searchChars) {
        if (isEmpty(cs) || isEmpty(searchChars)) {
            return -1;
        }
        return indexOfAny(cs, searchChars.toCharArray());
    }

    // ContainsAny
    //-----------------------------------------------------------------------

    /**
     * <p>Checks if the CharSequence contains any character in the given
     * set of characters.</p>
     * <p>
     * <p>A {@code null} CharSequence will return {@code false}.
     * A {@code null} or zero length search array will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.containsAny(null, *)                = false
     * StringUtils.containsAny("", *)                  = false
     * StringUtils.containsAny(*, null)                = false
     * StringUtils.containsAny(*, [])                  = false
     * StringUtils.containsAny("zzabyycdxx",['z','a']) = true
     * StringUtils.containsAny("zzabyycdxx",['b','y']) = true
     * StringUtils.containsAny("aba", ['z'])           = false
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null
     * @param searchChars the chars to search for, may be null
     * @return the {@code true} if any of the chars are found,
     * {@code false} if no match or null input
     * @since 3.0 Changed signature from containsAny(String, char[]) to containsAny(CharSequence, char...)
     */
    public static boolean containsAny(final CharSequence cs, final char... searchChars) {
        if (isEmpty(cs) || searchChars == null || searchChars.length == 0) {
            return false;
        }
        final int csLength = cs.length();
        final int searchLength = searchChars.length;
        final int csLast = csLength - 1;
        final int searchLast = searchLength - 1;
        for (int i = 0; i < csLength; i++) {
            final char ch = cs.charAt(i);
            for (int j = 0; j < searchLength; j++) {
                if (searchChars[j] == ch) {
                    if (Character.isHighSurrogate(ch)) {
                        if (j == searchLast) {
                            // missing low surrogate, fine, like String.indexOf(String)
                            return true;
                        }
                        if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
                            return true;
                        }
                    } else {
                        // ch is in the Basic Multilingual Plane
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * <p>
     * Checks if the CharSequence contains any character in the given set of characters.
     * </p>
     * <p>
     * <p>
     * A {@code null} CharSequence will return {@code false}. A {@code null} search CharSequence will return
     * {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.containsAny(null, *)            = false
     * StringUtils.containsAny("", *)              = false
     * StringUtils.containsAny(*, null)            = false
     * StringUtils.containsAny(*, "")              = false
     * StringUtils.containsAny("zzabyycdxx", "za") = true
     * StringUtils.containsAny("zzabyycdxx", "by") = true
     * StringUtils.containsAny("aba","z")          = false
     * </pre>
     *
     * @param cs          the CharSequence to check, may be null
     * @param searchChars the chars to search for, may be null
     * @return the {@code true} if any of the chars are found, {@code false} if no match or null input
     * @since 3.0 Changed signature from containsAny(String, String) to containsAny(CharSequence, CharSequence)
     */
    public static boolean containsAny(final CharSequence cs, final CharSequence searchChars) {
        if (searchChars == null) {
            return false;
        }
        return containsAny(cs, CharSequenceUtils.toCharArray(searchChars));
    }

    public static List<String> parseStringToStringList(String source, String token) {
        if (!isNullOrEmpty(source) && !isNullOrEmpty(token)) {
            List<String> result = new ArrayList();
            String[] units = source.split(token);
            String[] arr$ = units;
            int len$ = units.length;
            for (int i$ = 0; i$ < len$; ++i$) {
                String unit = arr$[i$];
                result.add(unit);
            }
            return result;
        } else {
            return null;
        }
    }

    /**
     * <p>Operations on {@link CharSequence} that are
     * {@code null} safe.</p>
     *
     * @version $Id: CharSequenceUtils.java 1469220 2013-04-18 08:15:47Z bayard $
     * @see CharSequence
     * @since 3.0
     */
    static class CharSequenceUtils {

        /**
         * <p>{@code CharSequenceUtils} instances should NOT be constructed in
         * standard programming. </p>
         * <p>
         * <p>This constructor is public to permit tools that require a JavaBean
         * instance to operate.</p>
         */
        public CharSequenceUtils() {
            super();
        }

        //-----------------------------------------------------------------------

        /**
         * <p>Returns a new {@code CharSequence} that is a subsequence of this
         * sequence starting with the {@code char} value at the specified index.</p>
         * <p>
         * <p>This provides the {@code CharSequence} equivalent to {@link String#substring(int)}.
         * The length (in {@code char}) of the returned sequence is {@code length() - start},
         * so if {@code start == end} then an empty sequence is returned.</p>
         *
         * @param cs    the specified subsequence, null returns null
         * @param start the start index, inclusive, valid
         * @return a new subsequence, may be null
         * @throws IndexOutOfBoundsException if {@code start} is negative or if
         *                                   {@code start} is greater than {@code length()}
         */
        public static CharSequence subSequence(final CharSequence cs, final int start) {
            return cs == null ? null : cs.subSequence(start, cs.length());
        }

        //-----------------------------------------------------------------------

        /**
         * <p>Finds the first index in the {@code CharSequence} that matches the
         * specified character.</p>
         *
         * @param cs         the {@code CharSequence} to be processed, not null
         * @param searchChar the char to be searched for
         * @param start      the start index, negative starts at the string start
         * @return the index where the search char was found, -1 if not found
         */
        static int indexOf(final CharSequence cs, final int searchChar, int start) {
            if (cs instanceof String) {
                return ((String) cs).indexOf(searchChar, start);
            } else {
                final int sz = cs.length();
                if (start < 0) {
                    start = 0;
                }
                for (int i = start; i < sz; i++) {
                    if (cs.charAt(i) == searchChar) {
                        return i;
                    }
                }
                return -1;
            }
        }

        /**
         * Used by the indexOf(CharSequence methods) as a green implementation of indexOf.
         *
         * @param cs         the {@code CharSequence} to be processed
         * @param searchChar the {@code CharSequence} to be searched for
         * @param start      the start index
         * @return the index where the search sequence was found
         */
        static int indexOf(final CharSequence cs, final CharSequence searchChar, final int start) {
            return cs.toString().indexOf(searchChar.toString(), start);
//        if (cs instanceof String && searchChar instanceof String) {
//            // TODO: Do we assume searchChar is usually relatively small;
//            //       If so then calling toString() on it is better than reverting to
//            //       the green implementation in the else block
//            return ((String) cs).indexOf((String) searchChar, start);
//        } else {
//            // TODO: Implement rather than convert to String
//            return cs.toString().indexOf(searchChar.toString(), start);
//        }
        }

        /**
         * <p>Finds the last index in the {@code CharSequence} that matches the
         * specified character.</p>
         *
         * @param cs         the {@code CharSequence} to be processed
         * @param searchChar the char to be searched for
         * @param start      the start index, negative returns -1, beyond length starts at end
         * @return the index where the search char was found, -1 if not found
         */
        static int lastIndexOf(final CharSequence cs, final int searchChar, int start) {
            if (cs instanceof String) {
                return ((String) cs).lastIndexOf(searchChar, start);
            } else {
                final int sz = cs.length();
                if (start < 0) {
                    return -1;
                }
                if (start >= sz) {
                    start = sz - 1;
                }
                for (int i = start; i >= 0; --i) {
                    if (cs.charAt(i) == searchChar) {
                        return i;
                    }
                }
                return -1;
            }
        }

        /**
         * Used by the lastIndexOf(CharSequence methods) as a green implementation of lastIndexOf
         *
         * @param cs         the {@code CharSequence} to be processed
         * @param searchChar the {@code CharSequence} to be searched for
         * @param start      the start index
         * @return the index where the search sequence was found
         */
        static int lastIndexOf(final CharSequence cs, final CharSequence searchChar, final int start) {
            return cs.toString().lastIndexOf(searchChar.toString(), start);
//        if (cs instanceof String && searchChar instanceof String) {
//            // TODO: Do we assume searchChar is usually relatively small;
//            //       If so then calling toString() on it is better than reverting to
//            //       the green implementation in the else block
//            return ((String) cs).lastIndexOf((String) searchChar, start);
//        } else {
//            // TODO: Implement rather than convert to String
//            return cs.toString().lastIndexOf(searchChar.toString(), start);
//        }
        }

        /**
         * Green implementation of toCharArray.
         *
         * @param cs the {@code CharSequence} to be processed
         * @return the resulting char array
         */
        static char[] toCharArray(final CharSequence cs) {
            if (cs instanceof String) {
                return ((String) cs).toCharArray();
            } else {
                final int sz = cs.length();
                final char[] array = new char[cs.length()];
                for (int i = 0; i < sz; i++) {
                    array[i] = cs.charAt(i);
                }
                return array;
            }
        }

        /**
         * Green implementation of regionMatches.
         *
         * @param cs         the {@code CharSequence} to be processed
         * @param ignoreCase whether or not to be case insensitive
         * @param thisStart  the index to start on the {@code cs} CharSequence
         * @param substring  the {@code CharSequence} to be looked for
         * @param start      the index to start on the {@code substring} CharSequence
         * @param length     character length of the region
         * @return whether the region matched
         */
        static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
                                     final CharSequence substring, final int start, final int length) {
            if (cs instanceof String && substring instanceof String) {
                return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
            } else {
                int index1 = thisStart;
                int index2 = start;
                int tmpLen = length;

                while (tmpLen-- > 0) {
                    char c1 = cs.charAt(index1++);
                    char c2 = substring.charAt(index2++);

                    if (c1 == c2) {
                        continue;
                    }

                    if (!ignoreCase) {
                        return false;
                    }

                    // The same check as in String.regionMatches():
                    if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
                            && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                        return false;
                    }
                }

                return true;
            }
        }
    }




    public static String joinDefault(String... args) {
        return join("$", "$", args);
    }

    /**
     * 使用连接字符连接字符串
     *
     * @param joinChar
     * @param args
     * @return
     */
    public static String join(String joinChar, String nullStr, String... args) {
        if (args == null || args.length == 0) {
            return "";
        }
        if (args.length == 1) {
            return args[0] == null ? "" : args[0].trim();
        }
        if (joinChar == null) {
            joinChar = "_";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(args[0] == null ? "" : args[0].trim());
        for (int i = 1; i < args.length; i++) {
            String item = args[i];
            if (item == null || isNullOrEmpty(item)) {
                if (nullStr == null) {
                    continue;
                }
                builder.append(joinChar).append(nullStr);
            } else {
                builder.append(joinChar).append(item.trim());
            }
        }
        return builder.toString();
    }


    /**
     * 检查是否存在非法字符，防止SQL注入
     *
     * @param str 被检查的字符串
     * @return ture-字符串中存在非法字符，false-不存在非法字符
     */
    public static boolean checkSQLInject(String str) {
        // 判断黑名单
        String[] inj_stra = {"script", "mid", "master", "truncate", "insert", "select", "delete", "update", "declare",
                "iframe", "'", "onreadystatechange", "alert", "atestu", "xss", ";", "'", "\"", "<", ">", "(", ")", "\\",
                "svg", "confirm", "prompt", "onload", "onmouseover", "onfocus", "onerror"};
        str = str.toLowerCase(); // sql不区分大小写
        for (int i = 0; i < inj_stra.length; i++) {
            if (str.contains(inj_stra[i])) {
                LoggerFactory.getLogger(StringUtils.class).info("传入str=" + str + ",包含特殊字符：" + inj_stra[i]);
                return true;
            }
        }
        return false;
    }


    public static String xssEncode(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '>':
                    sb.append('＞');// 全角大于号
                    break;
                case '<':
                    sb.append('＜');// 全角小于号
                    break;
                case '\'':
                    sb.append('‘');// 全角单引号
                    break;
                case '\"':
                    sb.append('“');// 全角双引号
                    break;
                case '&':
                    sb.append('＆');// 全角
                    break;
                case '\\':
                    sb.append('＼');// 全角斜线
                    break;
                case '#':
                    sb.append('＃');// 全角井号
                    break;
                case '(':
                    sb.append('（');//
                    break;
                case ')':
                    sb.append('）');//
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 不够位数的在前面补0，保留code的长度位数字
     *
     * @param code
     * @return
     */
    public static String autoGenericCode(String code) {
        // 保留code的位数
        return String.format("%0" + code.length() + "d", Integer.parseInt(code) + 1);
    }



    public static String autoFillCodeByStr(String code, int length) {
        if (StringUtils.isNullOrEmpty(code)) {
            return "";
        }
        int zeroNum = length - code.length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < zeroNum; i++) {
            sb.append(0);
        }
        return sb.append(code).toString();
    }

    public static String substrFixStr(String code, int length, boolean pre) {
        if (StringUtils.isNullOrEmpty(code)) {
            return "";
        }
        if (code.length() < length) {
            code = autoFillCodeByStr(code, length);
        }
        if (pre) {
            return code.substring(0, length);
        }
        return code.substring(code.length() - length, code.length());
    }


    public static void main(String[] args) {
//        String avg = autoGenericCode("A0111");
//        String avg = autoFillCode("11",9);
        String avg = substrFixStr("E2", 9, false);
        System.out.println("格式化字符串：" + avg);
    }

}
