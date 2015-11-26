package com.timbreanalyse.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
/**
 * 常用工具方法小集合，旨在弥补Java语法的不�?
 * 建议import static使用，并修改IDE配置中的import合并规则为：2条import static就合并为.*
 * Eclipse�?"Java / Code Style / Organize Imports / Number of static imports needed for .*" = 2
 * IntelliJ�?"Code Style / Java / Imports / Names count to use static import with *" = 2
 *
 *
 * 后续想法：如果此类的方法进一步膨�?，可以�?�虑用这种方式降低维护成本：
 * _ extends _string extends _collection extends _log extends _misc
 * (这样import static仍生效，不过ide的提示会很奇�?)
 * 后续想法2：参考Java8引入Closure的细节，添加相关工具方法
 */
public final class CommonUtil {

	
	/* ------------------------- string ------------------------- */

	// format
	public static String f(String format, Object... args) {
		return String.format(format, args);
	}

	// with empty
	public static String trimToEmpty(String s) {
		return s == null ? "" : s.trim();
	}
	public static String trimToNull(String s) {
		String trimed = trimToEmpty(s);
		return trimed.isEmpty() ? null : trimed;
	}
	public static boolean isNotEmpty(String s) {
		return s != null && s.length() > 0;
	}
	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

	/* ------------------------- arrays ------------------------- */

	public static boolean isEmpty(long[] arr) {
		return arr == null || arr.length == 0;
	}
	public static boolean isNotEmpty(long[] arr) {
		return arr != null && arr.length > 0;
	}
	public static boolean isEmpty(byte[] arr) {
		return arr == null || arr.length == 0;
	}
	public static boolean isNotEmpty(byte[] arr) {
		return arr != null && arr.length > 0;
	}
	public static boolean isEmpty(char[] arr) {
		return arr == null || arr.length == 0;
	}
	public static boolean isNotEmpty(char[] arr) {
		return arr != null && arr.length > 0;
	}

	/* ------------------------- collection ------------------------- */

	// builder
	public static <T> List<T> asList(T... a) {
		return Arrays.asList(a);
	}
	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> asMap(Object... args) {
		if (args.length % 2 != 0)
			throw new IllegalArgumentException("args.length = " + args.length);

		Map<String, T> map = new HashMap<String, T>();
		for (int i = 0; i < args.length - 1; i += 2)
			map.put(String.valueOf(args[i]), (T) args[i + 1]);
		return map;
	}
	public static Map<String, String> asStringMap(Object... args) {
		if (args.length % 2 != 0)
			throw new IllegalArgumentException("args.length = " + args.length);

		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < args.length - 1; i += 2)
			map.put(String.valueOf(args[i]), args[i + 1] == null ? null : String.valueOf(args[i + 1]));
		return map;
	}

	// with empty
	public static int size(List<?> list) {
		return list != null ? list.size() : 0;
	}
	public static boolean isEmpty(Collection<?> coll) {
		return coll == null || coll.isEmpty();
	}
	public static boolean isNotEmpty(Collection<?> coll) {
		return coll != null && !coll.isEmpty();
	}
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}
	public static boolean isNotEmpty(Map<?, ?> map) {
		return map != null && !map.isEmpty();
	}
	@SuppressWarnings("unchecked")
	public static <T> List<T> emptyList() {
		return Collections.EMPTY_LIST;
	}
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> emptyMap() {
		return Collections.EMPTY_MAP;
	}
	public static <T> List<T> nonEmpty(List<T> list) {
		return list != null ? list : Collections.<T>emptyList();
	}



	/* ------------------------- math ------------------------- */

	public static int max(int a, int b) {
		return Math.max(a, b);
	}
	public static int min(int a, int b) {
		return Math.min(a, b);
	}



	/* ------------------------- misc ------------------------- */

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
	public static double random() {
		return Math.random();
	}
	public static int randInt(int n) {
		return rand.nextInt(n);
	}
	static final Random rand = new Random();


}
