package com.acgnu.origin.notes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

public class RequestUtil {
	/**
	 * 转换数据类型为int
	 * @param obj
	 * @return
	 */
	public static int toInt(Object obj){
		try{
			return Integer.parseInt(obj.toString());
		}catch(Exception ignored){
		}
		return 0;
	}
	
	//转换数据类型为short
	public static short toShort(Object obj){
		try{
			return Short.parseShort(obj.toString());
		}catch(Exception ignored){
		}
		return 0;
	}
	
	/**
	 * 将请求参数封装到Model中并返回model
	 * @param classType 需要封装的model class
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static <T> T getRequestModel(Class<T> classType, HttpServletRequest request) throws Exception{
		Constructor<T> declaredConstructor = classType.getDeclaredConstructor();
		T instance = declaredConstructor.newInstance();
		Enumeration<String> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()){
			String name = paramNames.nextElement();
			String value = request.getParameter(name);
			String firstAlphaOfNameUpper = name.substring(0, 1).toUpperCase();
			String leftAlphaOfName = name.substring(1);
			Field field = classType.getDeclaredField(name);
			Method method = classType.getMethod("set" + firstAlphaOfNameUpper + leftAlphaOfName, field.getType());
			castAndSetValue(field.getType().getName(), instance, method, value);
		}
		return instance;
	}
	
	private static void castAndSetValue(String typeName, Object instance, Method method, String value) throws Exception{
		switch (typeName) {
			case "java.lang.String":
				method.invoke(instance, value);
				break;
			case "java.lang.Long":
			case "long":
				method.invoke(instance, Long.parseLong(value));
				break;
			case "java.util.Date":
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				method.invoke(instance, format.parse(value));
				break;
			case "java.math.BigDecimal":
				method.invoke(instance, BigDecimal.valueOf(Long.parseLong(value)));
				break;
			case "java.lang.Double":
			case "double":
				method.invoke(instance, Double.parseDouble(value));
				break;
			case "java.lang.Integer":
			case "int":
				method.invoke(instance, toInt(value));
				break;
			case "java.lang.Short":
				method.invoke(instance, toShort(value));
				break;
		}
	}
}
