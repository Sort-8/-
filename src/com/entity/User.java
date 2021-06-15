package com.entity;

/**
 * 用户实体类
 * 2021/6/8
 * @author 庞海
 *
 */
public class User {
	private int user_id;
	private int role_id;
	private String usr;
	private String pwd;
	private String name;
	private int auth;
	private String email;
	private String sex;
	private long create_time;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getUsr() {
		return usr;
	}
	public void setUsr(String usr) {
		this.usr = usr;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAuth() {
		return auth;
	}
	public void setAuth(int auth) {
		this.auth = auth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", role_id=" + role_id + ", usr=" + usr + ", pwd=" + pwd + ", name=" + name
				+ ", auth=" + auth + ", email=" + email + ", sex=" + sex + ", create_time="
				+ create_time + "]";
	}
	public User(int role_id, String usr, String pwd, String name, String email,
			String sex, long create_time) {
		super();
		this.role_id = role_id;
		this.usr = usr;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
		this.sex = sex;
		this.create_time = create_time;
	}
	public User() {
		super();
	}
}
