package com.acgnu.origin.notes;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * 查询学生信息
 * @author Administrator
 */
@WebServlet("/QueryStudentList")
public class QueryStudentList extends HttpServlet {
	private static final long serialVersionUID = 2L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyWord = request.getParameter("k");	//查询条件关键字
		//使用名称过滤器查询
		AssignByName<List<Student>> assignFilter = new AssignByName<>(keyWord);
		List<Student> stuLi = DataCache.getInstance().getAssign(assignFilter);
		ResponseUtil.writeString(response, JSON.toJSONString(stuLi));
	}
}
