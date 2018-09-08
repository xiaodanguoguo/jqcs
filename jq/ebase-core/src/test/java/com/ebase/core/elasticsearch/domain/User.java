package com.ebase.core.elasticsearch.domain;

import java.util.Date;

import com.ebase.utils.ReflectUtil;

import io.searchbox.annotations.JestId;

public class User {

	@JestId
	private String id;
	
	private String name;
	private int age;
	private Date birth;
	private String intru;
	
	public User() {
		super();
	}
	public User(String id, String name, int age, Date birth, String intru) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.birth = birth;
		this.intru = intru;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getIntru() {
		return intru;
	}
	public void setIntru(String intru) {
		this.intru = intru;
	}
	
	@Override
	public String toString() {
		return ReflectUtil.fieldsToString(this);
	}
}
