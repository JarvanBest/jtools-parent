package cn.jtool.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Vector;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 英文字母A的 ASCII值
     **/
    private static short ASCII_A = 65;

    /**
     * 英文字母A的 ASCII值
     **/
    private static short ASCII_a = 97;

    private static final char zero_char = '0';

    private static final String ZERO = "0";

    //利用正则表达式判断字符串是否是数字带小数
    private static final Pattern isDecimal = Pattern.compile("[0-9]*.?[0-9]*");

    //判断是否为数字
    private static final Pattern isNumeric = Pattern.compile("[0-9]*");

    public static String trim(String input) {
        if (isEmpty(input)) {
            return BLANK;
        }
        return input.trim();
    }

    public static String valueOf(Object obj) {
        if (obj == null) {
            return BLANK;
        }
        return String.valueOf(obj);
    }

    public static boolean equal(String source, String target) {
        if (source == null && target == null) {
            return true;
        } else if (source == null || target == null) {
            return false;
        }
        return source.equals(target);
    }

    public static String toUpperCase(String source) {
        if (source == null) {
            return BLANK;
        }
        return source.toUpperCase();
    }

    public static String getUTF8(String input) {
        if (isEmpty(input)) {
            return input;
        }
        return new String(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    /**
     * @param sValue 0099.9900
     * @return String 99.99
     * @Description : 格式化Double格式字符串数据
     */
    public static String trimDouble(String sValue) {
        if (isEmpty(sValue)) {
            return ZERO;
        }
        char[] cs = sValue.toCharArray();
        //前向后遍历去掉字符'0'
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] == zero_char) {
                cs[i] = ' ';
            } else {
                break;
            }
        }
        //后向前遍历去掉字符'0'
        for (int j = cs.length - 1; j >= 0; j--) {
            if (cs[j] == zero_char) {
                cs[j] = ' ';
            } else {
                break;
            }
        }
        return new String(cs).trim();
    }

    /**
     * @param s null or ""
     * @return boolean true
     * @Description: 判断字符串是否为空
     */
    public static boolean isEmpty(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 描述:比较数据是否超出范围
     *
     * @param s
     * @return boolean
     * @author Jarvan
     * @created 2017年4月26日 上午11:31:46
     * @since v1.0.0
     */
    public static boolean isOut(String s, int i) {
        if (s.length() > i) {
            return true;
        }
        return false;
    }


    /**
     * @param s null or ""
     * @return boolean true
     * @Description: 判断字符串是否为空
     */
    public static boolean isBlank(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * @param s null or ""
     * @return boolean true
     * @Description: 判断字符串是否不为空
     */
    public static boolean isNotBlank(String s) {
        return (s != null && s.trim().length() > 0 && !BLANK.equals(s));
    }

    /**
     * 首字母大写
     *
     * @param s
     * @return
     */
    public static String firstUpper(String s) {
        if (isEmpty(s)) return null;
        char[] cs = s.toCharArray();
        if (isLowwer(cs[0])) {
            cs[0] = Character.toUpperCase(cs[0]);
            return new String(cs);
        }
        return s;
    }

    /**
     * 将传入的字符串的首字母转化为小写字母
     *
     * @param s
     * @return
     */
    public static String firstLower(String s) {
        if (isEmpty(s)) return s;
        char[] cs = s.toCharArray();
        if (isUpper(cs[0])) {
            cs[0] = Character.toLowerCase(cs[0]);
            return new String(cs);
        }
        return s;
    }

    /**
     * 转换成java属性格式字符。首字母小写，下划线后第一个字母大写并去掉下划线，其余转为小写
     * 如:输入table_column，返回tableColumn
     *
     * @param table_column
     * @return
     */
    public static String toJavaString(String table_column) {
        StringBuffer sbf = new StringBuffer();
        char[] charArr = table_column.toLowerCase().toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            if (charArr[i] == '_') {
                if ((i < charArr.length - 1) && (charArr[i + 1] != '_')) {
                    charArr[i + 1] = Character.toUpperCase(charArr[i + 1]);
                }
            } else {
                sbf.append(charArr[i]);
            }
        }
        String str = "".equals(sbf.toString()) ? table_column : sbf.toString();
        return firstLower(str);
    }

    /**
     * 在非首大写字符前增加下划线。
     * 如：输入tableColumn,返回 table_Column
     *
     * @return
     */
    public static String addUnderlineBeforeUpper(String str) {
        if (str == null) {
            return str;
        }
        char[] cArr = str.toCharArray();
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(cArr[0]);
        for (int i = 1; i < cArr.length; i++) {
            if (Character.isUpperCase(cArr[i])) {
                strBuf.append('_');
            }
            strBuf.append(cArr[i]);
        }
        return strBuf.toString();
    }

    /**
     * 将字符串按给定分割符分割，返回字符串数组
     *
     * @param str
     * @param sgn
     * @return
     */
    public static final String[] split(String str, String sgn) {
        //TBD
        String[] returnValue = null;
        if (str != null) {
            Vector vectors = new Vector();
            int i = str.indexOf(sgn);
            String tempStr = "";
            for (; i >= 0; i = str.indexOf(sgn)) {
                tempStr = str.substring(0, i);
                str = str.substring(i + sgn.length());
                vectors.addElement(tempStr);
            }
            if (!"".equalsIgnoreCase(str)) vectors.addElement(str);
            returnValue = new String[vectors.size()];
            for (i = 0; i < vectors.size(); i++) {
                returnValue[i] = (String) vectors.get(i);
                returnValue[i] = returnValue[i].trim();
            }
        }
        return returnValue;
    }

    /**
     * 查找某一字符串中str，特定子串s的出现次数
     *
     * @param str  String
     * @param sign String
     * @return int
     */
    public static int getHasCount(String str, String sign) {
        int iret = 0;
        int signLen = sign.length();
        int strLen = str.length();
        String temp = str;
        if (strLen == 0 || strLen < signLen) iret = 0;
        else {
            for (int i = 0; i <= strLen - signLen && str.length() >= signLen; i++) {
                temp = str.substring(0, signLen);
                if (sign.equals(temp)) {
                    str = str.substring(signLen);
                    iret++;
                } else {
                    str = str.substring(1);
                }
            }
        }
        return iret;
    }

    /**
     * 将字符串中的 子字符串 替换为新 字符串  (不区分大小写)
     *
     * @param line      被操作字符串        "abcedfg"
     * @param oldString 被替换子字符串    "Ce"
     * @param newString 新字符串          "11"
     * @return 替换后字符串              "ab11dfg"
     */

    public static final String replaceIgnoreCase(String line, String oldString, String newString) {
        if (line == null) return null;
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
            char line2[] = line.toCharArray();
            char newString2[] = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j;
            for (j = i; (i = lcLine.indexOf(lcOldString, i)) > 0; j = i) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
            }

            buf.append(line2, j, line2.length - j);
            return buf.toString();
        } else {
            return line;
        }
    }

    /**
     * 将字符串补足为指定长度 ，在前面加'0'
     *
     * @param string "111"  指定的字符串
     * @param length 8        转换后的长度
     * @return "00000111"
     */
    public static final String fillBefore(String string, int length) {
        return fillBefore(string, length, zero_char);
    }

    /**
     * 将字符串补足为指定长度 ，在前面加指定字符fill
     *
     * @param string "111"  指定的字符串
     * @param length 8        转换后的长度
     * @return "00000111"
     */
    public static final String fillBefore(String string, int length, char fill) {
        if (isEmpty(string)) {
            return string;
        } else if (string.length() > length) {
            return string;
        } else {
            char[] cs = new char[length];
            for (int i = 0; i < cs.length; i++) {
                cs[i] = fill;
            }
            char[] data = string.toCharArray();
            System.arraycopy(data, 0, cs, cs.length - data.length, data.length);
            return new String(cs);
        }
    }

    /**
     * 将字符串补足为指定长度 ，在后面加'0'
     *
     * @param string "23433"  指定的字符串
     * @param length 8        转换后的长度
     * @return "23433000"
     */
    public static final String fillAfter(String string, int length) {
        return fillAfter(string, length, zero_char);
    }

    /**
     * 将字符串补足为指定长度 ，在后面加fill
     *
     * @param string "23433"  指定的字符串
     * @param length 8        转换后的长度
     * @return "23433000"
     */
    public static final String fillAfter(String string, int length, char fill) {
        if (isEmpty(string)) {
            return string;
        } else if (string.length() > length) {
            return string;
        } else {
            char[] cs = new char[length];
            for (int i = 0; i < cs.length; i++) {
                cs[i] = fill;
            }
            char[] data = string.toCharArray();
            System.arraycopy(data, 0, cs, 0, data.length);
            return new String(cs);
        }
    }

    /**
     * 将String型数组转换为Long型数组
     *
     * @param str 转换前的String型数组
     * @return 转换后的Long型数组
     */
    public static final Long[] parseStrToLong(String[] str) {
        Long[] lon = null;
        if (null != str && str.length > 0) {
            lon = new Long[str.length];
            for (int i = 0; i < lon.length; i++) {
                lon[i] = Long.parseLong(str[i]);
            }
        }

        return lon;
    }

    public static final String convertHTML(String content) {
        String ret = null;
        if (content != null) {
            ret = content.replaceAll("\\r\\n", "<br/>").replaceAll("\\r", "<br/>").replaceAll("\\n", "<br/>");
        }
        return ret;
    }

    public static final String decode(String str) {
        String result = str;
        if (str != null) {
            try {
                result = URLDecoder.decode(str, "UTF-8");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static final String BLANK = "";

    public static final String FALSE = "false";

    public static final String TRUE = "true";

    public static final String getStringFormat(String get) {
        if (isEmpty(get)) {
            return get;
        }
        try {
            return new String(get.getBytes("ISO-8859-1"), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return get;
    }

    /**
     * 字符是否为字母
     *
     * @param c
     * @return
     */
    public static boolean isAlph(char c) {
        return isUpper(c) || isLowwer(c);
    }

    /**
     * 是否大写字母
     *
     * @param c
     * @return
     */
    public static boolean isUpper(char c) {
        return c >= ASCII_A && c <= ASCII_A + 26;
    }

    /**
     * 是否小写字母
     *
     * @param c
     * @return
     */
    public static boolean isLowwer(char c) {
        return c >= ASCII_a && c <= ASCII_a + 26;
    }

    public static String stringTimes(String source, int times) {
        if (isEmpty(source) || times == 0) {
            return StringUtil.BLANK;
        }
        StringBuffer sb = new StringBuffer(source);
        for (int i = 1; i < times; i++) {
            sb.append(source);
        }
        return sb.toString();
    }

    public static String getDefault(String input, String def) {
        return isEmpty(input) ? def : input;
    }

    /**
     * 描述:是否以startStr开头
     *
     * @param str
     * @param startStr
     * @return boolean
     * @created 2017年3月8日 下午5:10:05
     * @since v1.0.0
     */
    public static boolean startWith(String str, String startStr) {
        if (!isEmpty(str) && !isEmpty(startStr)) {
            return str.startsWith(startStr);
        }
        return false;
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        return isNumeric.matcher(str).matches();
    }

    /**
     * 利用正则表达式判断字符串是否是数字带小数
     *
     * @param str
     * @return
     */
    public static boolean isDecimal(String str) {
        return isDecimal.matcher(str).matches();
    }

    public static boolean isNumberic(String input) {
        try {
            if (StringUtil.isEmpty(input)) {
                return false;
            }
            Double.parseDouble(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 描述:str是否包含subStr字符串
     *
     * @param str
     * @param subStr
     * @return boolean
     * @created 2017年5月10日 下午5:06:16
     * @since v1.0.0
     */
    public static boolean contains(String str, String subStr) {
        if (!isEmpty(str) && !isEmpty(subStr)) {
            return str.contains(subStr);
        }
        return false;
    }

    /**
     * 描述: 判断多个字符串是否为空
     *
     * @param values
     * @return boolean
     * @author Jarvan
     * @created 2017年5月11日 下午2:48:09
     * @since v1.0.0
     */
    public static boolean exitEmpty(String... values) {
        boolean result = false;
        if (values == null || values.length == 0) {
            result = true;
        } else {
            for (String value : values) {
                result |= isEmpty(value);
            }
        }
        return result;
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
            if (item == null || StringUtil.isEmpty(item)) {
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
}
