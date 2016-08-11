package com.nettysocketio.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public static boolean isNotBlank(String str) {
		if (null != str && !"".equals(str)) {
			return true;
		}
		return false;
	}

	public static boolean isBlank(String str) {
		return !isNotBlank(str);
	}


	public static String filterEmoji(String source,String replaceStr) {
		if (source != null) {
			Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
					Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			Matcher emojiMatcher = emoji.matcher(source);
			if (emojiMatcher.find()) {
				source = emojiMatcher.replaceAll(replaceStr);
				return source;
			}
			return source;
		}
		return source;
	}
	public static void main(String[] args) {
		String s="sdfsdf12321⊙▂⊙⊙0⊙⊙︿⊙⊙ω⊙⊙";
		System.out.println(filterEmoji(s,""));
	}
}
