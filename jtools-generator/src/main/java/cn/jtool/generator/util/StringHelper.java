package cn.jtool.generator.util;

import cn.jtool.generator.provider.db.table.model.Column;
import cn.jtool.generator.provider.db.table.model.Column.EnumMetaDada;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author badqiu
 * @email badqiu(a)gmail.com
 */
public class StringHelper {

	public final static Map<String, String> REPLACE_VALUE=new HashMap<>();
	public volatile static boolean USE_DEFAULT=true;

	public static String removeCrlf(String str) {
		if(str == null) return null;
		return StringHelper.join(StringHelper.tokenizeToStringArray(str,"\t\n\r\f")," ");
	}
	
	private static final Map<String, String> XML = new HashMap<String, String>();
	static{
		XML.put("apos", "'");
		XML.put("quot", "\"");
		XML.put("amp", "&");
		XML.put("lt", "<");
		XML.put("gt", ">");
	}
	
	public static String unescapeXml(String str) {
		if(str == null) return null;
		for(String key : XML.keySet()) {
			String value = XML.get(key);
			str = StringHelper.replace(str, "&"+key+";", value);
		}
		return str;
	}
		 

	public static String removePrefix(String str, String prefix) {
		return removePrefix(str,prefix,false);
	}

	public static String removePrefix(String str, String prefix, boolean ignoreCase) {
		if(str == null) return null;
		if(prefix == null) return str;
		if(ignoreCase) {
			if(str.toLowerCase().startsWith(prefix.toLowerCase())) {
				return str.substring(prefix.length());
			}
		}else {
			if(str.startsWith(prefix)) {
				return str.substring(prefix.length());
			}
		}
		return str;
	}
	
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
    
    public static String getExtension(String str) {
    	if(str == null) return null;
    	int i = str.lastIndexOf('.');
    	if(i >= 0) {
    		return str.substring(i+1);
    	}
    	return null;
    }

	/**
	 * Count the occurrences of the substring in string s.
	 * @param str string to search in. Return 0 if this is null.
	 * @param sub string to search for. Return 0 if this is null.
	 */
	public static int countOccurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
			return 0;
		}
		int count = 0;
		int pos = 0;
		int idx;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}
	
	public static boolean contains(String str, String... keywords) {
		if(str == null) return false;
		if(keywords == null) throw new IllegalArgumentException("'keywords' must be not null");
		
		for(String keyword : keywords) {
			if(str.contains(keyword.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

   public static String defaultString(Object value) {
        if(value == null) {
            return "";
        }
        return value.toString();
    }
	   
	public static String defaultIfEmpty(Object value, String defaultValue) {
		if(value == null || "".equals(value)) {
			return defaultValue;
		}
		return value.toString();
	}
	
	public static String makeAllWordFirstLetterUpperCase(String sqlName) {
		String[] strs = sqlName.toLowerCase().split("_");
		String result = "";
		String preStr = "";
		for(int i = 0; i < strs.length; i++) {
			if(preStr.length() == 1) {
				result += strs[i];
			}else {
				result += capitalize(strs[i]);
			}
			preStr = strs[i];
		}
		for (Map.Entry<String, String> funcEntry: REPLACE_VALUE.entrySet()){
			String key=funcEntry.getKey();
			String value= funcEntry.getValue();
			if((key==null || key.length()==0) || value==null){
				continue;
			}
			if(!result.startsWith(key)){
				continue;
			}
			result=value+ result.substring(key.length());
		}
		return result;
	}
	
	public static int indexOfByRegex(String input, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		if(m.find()) {
			return m.start();
		}
		return -1;
	}
	
	public static String toJavaVariableName(String str) {
		return uncapitalize(toJavaClassName(str));
	}

	public static String toJavaClassName(String str) {
		return makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(str));
	}

	public static String removeMany(String inString, String... keywords) {
		if (inString == null) {
			return null;
		}
		for(String k : keywords) {
			inString = replace(inString, k, "");
		}
		return inString;
	}
	
	public static String replace(String inString, String oldPattern, String newPattern) {
		if (inString == null) {
			return null;
		}
		if (oldPattern == null || newPattern == null) {
			return inString;
		}

		StringBuffer sbuf = new StringBuffer();
		// output StringBuffer we'll build up
		int pos = 0; // our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sbuf.append(inString.substring(pos, index));
			sbuf.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sbuf.append(inString.substring(pos));

		// remember to append any characters to the right of a match
		return sbuf.toString();
	}
	/**����ĸ��copy from spring*/
	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}
	
	/**����ĸСдcopy from spring*/
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}
	/**copy from spring*/
	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str.length());
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		}
		else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}
	
	private static final Random RANDOM = new Random();
	public static String randomNumeric(int count) {
        return random(count, false, true);
    }
	
	public static String random(int count, boolean letters, boolean numbers) {
	    return random(count, 0, 0, letters, numbers);
	}
	
    public static String random(int count, int start, int end, boolean letters, boolean numbers) {
        return random(count, start, end, letters, numbers, null, RANDOM);
    }
	 
	public static String random(int count, int start, int end, boolean letters,
                                boolean numbers, char[] chars, Random random) {
		if (count == 0) {
			return "";
		} else if (count < 0) {
			throw new IllegalArgumentException(
					"Requested random string length " + count
							+ " is less than 0.");
		}
		if ((start == 0) && (end == 0)) {
			end = 'z' + 1;
			start = ' ';
			if (!letters && !numbers) {
				start = 0;
				end = Integer.MAX_VALUE;
			}
		}

		char[] buffer = new char[count];
		int gap = end - start;

		while (count-- != 0) {
			char ch;
			if (chars == null) {
				ch = (char) (random.nextInt(gap) + start);
			} else {
				ch = chars[random.nextInt(gap) + start];
			}
			if ((letters && Character.isLetter(ch))
					|| (numbers && Character.isDigit(ch))
					|| (!letters && !numbers)) {
				if (ch >= 56320 && ch <= 57343) {
					if (count == 0) {
						count++;
					} else {
						// low surrogate, insert high surrogate after putting it
						// in
						buffer[count] = ch;
						count--;
						buffer[count] = (char) (55296 + random.nextInt(128));
					}
				} else if (ch >= 55296 && ch <= 56191) {
					if (count == 0) {
						count++;
					} else {
						// high surrogate, insert low surrogate before putting
						// it in
						buffer[count] = (char) (56320 + random.nextInt(128));
						count--;
						buffer[count] = ch;
					}
				} else if (ch >= 56192 && ch <= 56319) {
					// private high surrogate, no effing clue, so skip it
					count++;
				} else {
					buffer[count] = ch;
				}
			} else {
				count++;
			}
		}
		return new String(buffer);
	}
	
	/**
	 * Convert a name in camelCase to an underscored name in lower case.
	 * Any upper case letters are converted to lower case with a preceding underscore.
	 * @param name the string containing original name
	 * @return the converted name
	 */
	public static String toUnderscoreName(String name) {
		if(name == null) return null;
		
		String filteredName = name;
		if(filteredName.indexOf("_") >= 0 && filteredName.equals(filteredName.toUpperCase())) {
			filteredName = filteredName.toLowerCase();
		}
		if(filteredName.indexOf("_") == -1 && filteredName.equals(filteredName.toUpperCase())) {
			filteredName = filteredName.toLowerCase();
		}
		
		StringBuffer result = new StringBuffer();
		if (filteredName != null && filteredName.length() > 0) {
			result.append(filteredName.substring(0, 1).toLowerCase());
			for (int i = 1; i < filteredName.length(); i++) {
				String preChart = filteredName.substring(i - 1, i);
				String c = filteredName.substring(i, i + 1);
				if(c.equals("_")) {
					result.append("_");
					continue;
				}
				if(preChart.equals("_")){
					result.append(c.toLowerCase());
					continue;
				}
				if(c.matches("\\d")) {
					result.append(c);
				}else if (c.equals(c.toUpperCase())) {
					result.append("_");
					result.append(c.toLowerCase());
				}
				else {
					result.append(c);
				}
			}
		}
		return result.toString();
	}
	
	public static String removeEndWiths(String inputString, String... endWiths) {
	    for(String endWith : endWiths) {
    	    if(inputString.endsWith(endWith)) {
                return inputString.substring(0,inputString.length() - endWith.length());
            }
	    }
	    return inputString;
	}
	
	/**
	 * 将string转换为List<ColumnEnum> 格式为: "enumAlias(enumKey,enumDesc)"
	 */
	static Pattern three = Pattern.compile("(.*)\\((.*),(.*)\\)");
	static Pattern two = Pattern.compile("(.*)\\((.*)\\)");
	public static List<EnumMetaDada> string2EnumMetaData(String data) {
		if(data == null || data.trim().length() == 0) return new ArrayList();
		//enumAlias(enumKey,enumDesc),enumAlias(enumDesc)
		
		List<Column.EnumMetaDada> list = new ArrayList();
		Pattern p = Pattern.compile("\\w+\\(.*?\\)");
		Matcher m = p.matcher(data);
		while (m.find()) {
			String str = m.group();
            Matcher three_m = three.matcher(str);
			if(three_m.find()) {
				list.add(new EnumMetaDada(three_m.group(1),three_m.group(2),three_m.group(3)));
				continue;
			}
			Matcher two_m = two.matcher(str);
			if(two_m.find()) {
				list.add(new EnumMetaDada(two_m.group(1),two_m.group(1),two_m.group(2)));
				continue;
			}			
			throw new IllegalArgumentException("error enumString format:"+data+" expected format:F(1,Female);M(0,Male) or F(Female);M(Male)");
		}
		return list;
	}
	
	/**
	 * Test whether the given string matches the given substring
	 * at the given index.
	 * @param str the original string (or StringBuilder)
	 * @param index the index in the original string to start matching against
	 * @param substring the substring to match at the given index
	 */
	public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
		for (int j = 0; j < substring.length(); j++) {
			int i = index + j;
			if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
				return false;
			}
		}
		return true;
	}
	
	public static String[] tokenizeToStringArray(String str, String seperators) {
		if(str == null) return new String[0];
		StringTokenizer tokenlizer = new StringTokenizer(str,seperators);
		List result = new ArrayList();
		
		while(tokenlizer.hasMoreElements()) {
			Object s = tokenlizer.nextElement();
			result.add(s);
		}
		return (String[])result.toArray(new String[result.size()]);
	}
    public static String join(List list, String seperator) {
        return join(list.toArray(new Object[0]),seperator);
    }
    
    public static String replace(int start, int end, String str, String replacement) {
        String before = str.substring(0,start);
        String after = str.substring(end);
        return before + replacement + after;
    }
    
	public static String join(Object[] array, String seperator) {
		if(array == null) return null;
		StringBuffer result = new StringBuffer();
		for(int i = 0; i < array.length; i++) {
			result.append(array[i]);
			if(i != array.length - 1)  {
				result.append(seperator);
			}
		}
		return result.toString();
	}
	public static int containsCount(String string, String keyword) {
		if(string == null) return 0;
		int count = 0;
		for(int i = 0; i < string.length(); i++ ) {
			int indexOf = string.indexOf(keyword,i);
			if(indexOf < 0) {
				break;
			}
			count ++;
			i = indexOf;
		}
		return count;
	}

    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->HELLO_WORLD
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：HELLO_WORLD->HelloWorld
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割

        String camels[] = name.split("_");
        for (String camel :  camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

	public static String firstUperCamelName(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将首字母小写
			return name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel :  camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 其他的驼峰片段，首字母大写
			result.append(camel.substring(0, 1).toUpperCase());
			result.append(camel.substring(1).toLowerCase());
		}
		return result.toString();
	}
}
