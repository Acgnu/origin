package com.acgnu.origin.notes;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据名称查询过滤器
 * @author Administrator
 * @param <T> 需要返回的集合
 */
public class AssignByName<T> implements AssignFilter<T>{
	private String name;
	private List<Student> filteredStu = new ArrayList<Student>();
	public AssignByName(String name){
		this.name = name;
	}
	@Override
	@SuppressWarnings("unchecked")
	public T doFilter(List<Student> stuList ) {
		if(RequestUtil.isEmpty(name)){
			return (T) stuList;
		}
		
		for(int i = 0; i < stuList.size(); i++){
			if(stuList.get(i).getName().contains(name)){
				filteredStu.add(stuList.get(i));
			}
		}
		return (T) filteredStu;
	}
}
