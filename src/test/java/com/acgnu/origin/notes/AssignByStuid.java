package com.acgnu.origin.notes;

import java.util.List;

/**
 * 根据学生id查询的过滤器
 * @author Administrator
 * @param <T> 单个查询对象
 */
public class AssignByStuid<T> implements AssignFilter<T>{
	private Integer id;
	public AssignByStuid(Integer id){
		this.id = id;
	}
	@Override
	@SuppressWarnings("unchecked")
	public T doFilter(List<Student> stuList) {
		for(int i = 0; i < stuList.size(); i++){
			if(stuList.get(i).getId() == id){
				return (T) stuList.get(i);
			}
		}
		return null;
	}
}
