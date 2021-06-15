package com.entity;

/**
 * 角色实体类
 * 2021/6/8
 * @author 庞海
 *
 */
public class Role {
	private int role_id;
	private String name;
	private int is_default;
	private int create_time;
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIs_default() {
		return is_default;
	}
	public void setIs_default(int is_default) {
		this.is_default = is_default;
	}
	public int getCreate_time() {
		return create_time;
	}
	public void setCreate_time(int create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "Role [role_id=" + role_id + ", name=" + name + ", is_default=" + is_default + ", create_time="
				+ create_time + "]";
	}
	public Role(int role_id, String name, int is_default, int create_time) {
		super();
		this.role_id = role_id;
		this.name = name;
		this.is_default = is_default;
		this.create_time = create_time;
	}
	public Role() {
		super();
	}
}
