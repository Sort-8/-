package com.entity;

/**
 * 图书记录实体类
 * 2021/6/8
 * @author 庞海
 *
 */
public class Book_record {
	private int record_id;
	private int book_id;
	private int user_id;
	private int record_time;
	private int out_time;
	public int getRecord_id() {
		return record_id;
	}
	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getRecord_time() {
		return record_time;
	}
	public void setRecord_time(int record_time) {
		this.record_time = record_time;
	}
	public int getOut_time() {
		return out_time;
	}
	public void setOut_time(int out_time) {
		this.out_time = out_time;
	}
	@Override
	public String toString() {
		return "Book_record [record_id=" + record_id + ", book_id=" + book_id + ", user_id=" + user_id
				+ ", record_time=" + record_time + ", out_time=" + out_time + "]";
	}
	public Book_record(int record_id, int book_id, int user_id, int record_time, int out_time) {
		super();
		this.record_id = record_id;
		this.book_id = book_id;
		this.user_id = user_id;
		this.record_time = record_time;
		this.out_time = out_time;
	}
	public Book_record() {
		super();
	}
}
