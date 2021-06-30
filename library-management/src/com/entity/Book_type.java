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
	private long create_time;
	private String create_by;
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
	public long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	@Override
	public String toString() {
		return "Book_type [type_id=" + type_id + ", name=" + name + ", create_time=" + create_time + ", create_by="
				+ create_by + "]";
	}
	public Book_type(int type_id, String name, long create_time, String create_by) {
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
