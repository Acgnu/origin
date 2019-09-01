package com.acgnu.origin.notes;

import java.util.List;

/**
 * 过滤器接口, 实现该接口来过滤查询
 * @author Administrator
 * @param <T>
 */
public interface AssignFilter<T> {
	T doFilter(List<Student> stuList);
}
