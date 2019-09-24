package com.acgnu.origin.notes;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Class: RandomUtil.java Description: 随机数工具类
 
 * @date 2012-12-14
 */
@SuppressWarnings("all")
public class RandomUtil {
	private final static Random rm = new Random();
	private final static String[] stringNumber = { "01", "02", "03", "04",
			"05", "06", "07", "08", "09" };
	
	 /**
     * 保留2位小数点
     * @param str
     * @return
     */
	public static Double convertStringToDouble2(String str) {
		Double  price=0.0d;
		  try {
			BigDecimal bd = new BigDecimal(""+str);  
			  price=bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e) {
		   e.printStackTrace();
		}  
		  return price;
	}
	
	

	/**
	 * 随机生成一个整数
	 * 
	 * @param max
	 * @return
	 */
	public static int randomOneInt(int max) {
		return rm.nextInt(max);
	}

	/**
	 * 随机生成一个小于10的整数字符
	 * 
	 * @param max
	 * @return
	 */
	public static String randomOneString(int max) {
		return (new Integer(rm.nextInt(max))).toString();
	}

	/** 计算出现次数 */
	public static int frequency(String str) {
		int count = 0;
		String[] val = str.split(",");
		for (int i = 0; i < val.length; i++) {
			for (int j = 0; j < val.length; j++) {
				if (val[i].equals(val[j]))
					count++;
			}
		}
		return count;
	}

	/** 以长度来分割字符串 */
	public static String splitStr(String str, int length) {
		String restr = null;
		int count = str.length() / length;
		for (int i = 0; i < count; i++) {
			if (i == 0)
				restr = str.substring(0, length);
			else
				restr += "," + str.substring(0, length);
			str = str.substring(length, str.length());
		}
		return restr;
	}

	/**
	 * 返回长度为length的字符串随机数
	 * @param length
	 *            长度
	 */
	public static String randomForMedian(int length) {
		String str = "";
		for (int i = 0; i < length; i++) {
			int tempInt = randomOneInt(10);
			if (i == 0 && 0 == tempInt)
				tempInt = randomOneInt(9) + 1;
			str += tempInt;
		}
		return str;
	}

	/**
	 * @date 2012-12-14 上午11:19:17
	 * @describe 随机生成任意长度的数字组合字符串
	 * @return String
	 */
	public static String randStringLength(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String num = "";
		
		for (int i = 0; i < length; i++) {
			num +=chars.charAt((int)(Math.random() * 52));
		}
		return num;
	}

	/**
	 * @date 2012-4-20 上午10:49:12
	 * @describe 随机生成MD5加密后的N位纯数字组合序列号，格式如(1111 2222 3333 4444)
	 * @return String
	 * @exception
	 * @version 1.0
	 */
	public static String getSerialNumberByMD5(String id, int n) {
		String guid = "";
		String str = id + RandomUtil.randomForMedian((n - id.length()));
		if (null != str && !"".equals(str)) {
			String[] num = new String[str.length() / 4];
			for (int i = 0; i < num.length; i++) {
				num[i] = str.substring(0, 4);
				str = str.substring(4);
			}
			if (null != num && num.length > 0) {
				for (int i = 0; i < num.length; i++) {
					guid += num[i] + " ";
				}
				guid = guid.substring(0, guid.length() - 1);
			}
		}
		return guid;
	}

	/**
	 * @date 2012-4-20 上午10:49:12
	 * @describe 随机生成N位纯数字组合序列号，格式如(11112222333344445555)
	 * @return String
	 * @exception
	 * @version 1.0
	 */
	public static String getSerialNumber(String id, int n) {
		String guid = "";
		if (null != id && !"".equals(id)) {
			guid = id + RandomUtil.randomForMedian((n - id.length()));
		}
		return guid;
	}
}
