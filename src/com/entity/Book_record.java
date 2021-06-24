package com.entity;

/**
 * 图书记录实体类
 * 2021/6/8
 * @author 庞海
 *
 */
public class Book_record {
	private int record_id;
	private int user_id;
	private int record_type;
	private int book_id;
	private String book_name;
	private long record_time;
	public int getRecord_id() {
		return record_id;
	}
	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getRecord_type() {
		return record_type;
	}
	public void setRecord_type(int record_type) {
		this.record_type = record_type;
	}
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public long getRecord_time() {
		return record_time;
	}
	public void setRecord_time(long record_time) {
		this.record_time = record_time;
	}
	public Book_record(int record_id, int user_id, int record_type, int book_id, String book_name, long record_time) {
		super();
		this.record_id = record_id;
		this.user_id = user_id;
		this.record_type = record_type;
		this.book_id = book_id;
		this.book_name = book_name;
		this.record_time = record_time;
	}
	public Book_record() {
		super();
	}
	@Override
	public String toString() {
		return "Book_record [record_id=" + record_id + ", user_id=" + user_id + ", record_type=" + record_type
				+ ", book_id=" + book_id + ", book_name=" + book_name + ", record_time=" + record_time + "]";
	}
}
