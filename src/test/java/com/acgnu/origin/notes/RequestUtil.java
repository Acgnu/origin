package com.acgnu.origin.notes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

public class RequestUtil {
	/**
	 * 判断对象是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj){
		return null == obj || obj.toString().isEmpty();
	}
	
	/**
	 * 转换数据类型为int
	 * @param obj
	 * @return
	 */
	public static int toInt(Object obj){
		int i = 0; 
		try{
			i = Integer.valueOf(obj.toString());
		}catch(Exception e){
			//do nothing
		}
		return i;
	}
	
	//转换数据类型为short
	public static short toShort(Object obj){
		short i = 0; 
		try{
			i = Short.valueOf(obj.toString());
		}catch(Exception e){
			//do nothing
		}
		return i;
	}
	
	/**
	 * 将请求参数封装到Model中并返回model
	 * @param classType 需要封装的model class
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static <T> T getRequestModel(Class<T> classType, HttpServletRequest request) throws Exception{
		T instance = classType.newInstance();
		Enumeration<String> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()){
			String name = paramNames.nextElement();
			String value = request.getParameter(name);
			String firstAlphaOfNameUpper = name.substring(0, 1).toUpperCase();
			String leftAlphaOfName = name.substring(1, name.length());
			Field field = classType.getDeclaredField(name);
			Method method = classType.getMethod("set" + firstAlphaOfNameUpper + leftAlphaOfName, field.getType());
			castAndSetValue(field.getType().getName(), instance, method, value);
		}
		return instance;
	}
	
	private static void castAndSetValue(String typeName, Object instance, Method method, String value) throws Exception{
		if (typeName.equals("java.lang.String")) {
			method.invoke(instance, value);
		} else if (typeName.equals("java.lang.Long")) {
			method.invoke(instance, Long.parseLong(value));
		} else if (typeName.equals("long")) {
			method.invoke(instance, Long.parseLong(value));
		} else if (typeName.equals("java.util.Date")) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			method.invoke(instance, format.parse(value));
		} else if (typeName.equals("java.math.BigDecimal")) {
			method.invoke(instance, BigDecimal.valueOf(Long.parseLong(value)));
		} else if (typeName.equals("java.lang.Double")) {
			method.invoke(instance, Double.parseDouble(value));
		} else if (typeName.equals("double")) {
			method.invoke(instance, Double.parseDouble(value));
		} else if (typeName.equals("java.lang.Integer")) {
			method.invoke(instance, toInt(value));
		} else if (typeName.equals("int")) {
			method.invoke(instance, toInt(value));
		} else if (typeName.equals("java.lang.Short")) {
			method.invoke(instance, toShort(value));
		} else if (typeName.equals("int")) {
			method.invoke(instance, toShort(value));
		}
	}
}
