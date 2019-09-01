package com.acgnu.origin.notes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {
	/**
	 * 将结果返回
	 * @param response
	 * @param str
	 */
	public static void writeString(HttpServletResponse response, String str){
		try {
			response.setCharacterEncoding(FileUtils.CHARSET_UTF_8);
			PrintWriter w = response.getWriter();
			w.print(str);
			w.flush();
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
