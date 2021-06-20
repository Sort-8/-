package com.entity;

/**
 * 图书记录实体类
 * 2021/6/8
 * @author 庞海
 *
 */
public class Book_record {
	private int record_id;
	private int card_id;
	private int record_type;
	private String book_name;
	private int user_id;
	private long record_time;
	private int out_time;
	public int getRecord_id() {
		return record_id;
	}
	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}
	public int getCard_id() {
		return card_id;
	}
	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}
	public int getRecord_type() {
		return record_type;
	}
	public void setRecord_type(int record_type) {
		this.record_type = record_type;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public long getRecord_time() {
		return record_time;
	}
	public void setRecord_time(long record_time) {
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
		return "Book_record [record_id=" + record_id + ", card_id=" + card_id + ", record_type=" + record_type
				+ ", book_name=" + book_name + ", user_id=" + user_id + ", record_time=" + record_time + ", out_time="
				+ out_time + "]";
	}
	public Book_record(int record_id, int card_id, int record_type, String book_name, int user_id, long record_time,
			int out_time) {
		super();
		this.record_id = record_id;
		this.card_id = card_id;
		this.record_type = record_type;
		this.book_name = book_name;
		this.user_id = user_id;
		this.record_time = record_time;
		this.out_time = out_time;
	}
	public Book_record() {
		super();
	}
}
