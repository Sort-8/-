package com.entity;

/**
 * 图书类型实体类
 * 2021/6/8
 * @author 庞海
 *
 */
public class Book_type {
	private int type_id;
	private String name;
	private int create_time;
	private int create_by;
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCreate_time() {
		return create_time;
	}
	public void setCreate_time(int create_time) {
		this.create_time = create_time;
	}
	public int getCreate_by() {
		return create_by;
	}
	public void setCreate_by(int create_by) {
		this.create_by = create_by;
	}
	@Override
	public String toString() {
		return "Book_type [type_id=" + type_id + ", name=" + name + ", create_time=" + create_time + ", create_by="
				+ create_by + "]";
	}
	public Book_type(int type_id, String name, int create_time, int create_by) {
		super();
		this.type_id = type_id;
		this.name = name;
		this.create_time = create_time;
		this.create_by = create_by;
	}
	public Book_type() {
		super();
	}
}
