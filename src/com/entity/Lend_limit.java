package com.entity;

/**
 * 借阅限制实体类
 * 2021/6/8
 * @author 庞海
 *
 */
public class Lend_limit {
	private int limit_id;
	private int role_id;
	private int max_number;
	private int max_time;
	public int getLimit_id() {
		return limit_id;
	}
	public void setLimit_id(int limit_id) {
		this.limit_id = limit_id;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public int getMax_number() {
		return max_number;
	}
	public void setMax_number(int max_number) {
		this.max_number = max_number;
	}
	public int getMax_time() {
		return max_time;
	}
	public void setMax_time(int max_time) {
		this.max_time = max_time;
	}
	@Override
	public String toString() {
		return "Lend_limit [limit_id=" + limit_id + ", role_id=" + role_id + ", max_number=" + max_number
				+ ", max_time=" + max_time + "]";
	}
	public Lend_limit(int limit_id, int role_id, int max_number, int max_time) {
		super();
		this.limit_id = limit_id;
		this.role_id = role_id;
		this.max_number = max_number;
		this.max_time = max_time;
	}
	public Lend_limit() {
		super();
	}
}
