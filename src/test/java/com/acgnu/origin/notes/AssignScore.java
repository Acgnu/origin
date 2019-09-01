package com.acgnu.origin.notes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 更新学分信息后台
 * @author Administrator
 */
@WebServlet("/AssignScore")
public class AssignScore extends HttpServlet {
	private static final long serialVersionUID = 4L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Result result = new Result();
		try {
			String id = request.getParameter("id");
			short score = RequestUtil.toShort(request.getParameter("score"));
			if(RequestUtil.isEmpty(id)){
				result.setCode(Result.PARAM_ERROR);
				return;
			}
			
			AssignByStuid<Student> assignByStuid = new AssignByStuid<>(RequestUtil.toInt(id));
			Student stu = DataCache.getInstance().getAssign(assignByStuid);
			if(null == stu){
				result.setCode(Result.PARAM_ERROR);
				return;
			}
			stu.setScore(score);
			DataCache.getInstance().saveAll();
			result.setSUccess();
		} catch (Exception e) {
			result.setError(Result.EXCEPTION, e.getMessage());
		} finally {
			ResponseUtil.writeString(response, result.toString());
		}
	}
}
