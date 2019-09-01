package com.acgnu.origin.notes;

public class Student {
	private Integer id;
	private Short sex;	//性别 0 未知 1 男 2 女 3 人妖
	private Short age;	//1 - 150
	private String name;
	private Short score;	//0 - 100
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Short getSex() {
		return sex;
	}
	public void setSex(Short sex) {
		if(sex < 0 || sex > 3){
			sex = 0;
		}
		this.sex = sex;
	}
	public Short getAge() {
		return age;
	}
	public void setAge(Short age) {
		if(age < 0){
			age = 0;
		}else if(age > 150){
			age = 150;
		}
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Short getScore() {
		return score;
	}
	public void setScore(Short score) {
		if(score < 0){
			score = 0;
		}else if(score > 100){
			score = 100;
		}
		this.score = score;
	}
	
	
	
}
