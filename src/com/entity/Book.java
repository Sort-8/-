package com.entity;

/**
 * 图书实体类
 * 2021/6/8
 * @author 庞海
 *
 */
public class Book {
	private int book_id;
	private String code;
	private String name;
	private String author;
	private String press;
	private int lend_num;
	private int type_id;
	private String create_by;
	private int number;
	private int lend_stu;
	private long create_time;
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public int getLend_num() {
		return lend_num;
	}
	public void setLend_num(int lend_num) {
		this.lend_num = lend_num;
	}
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getLend_stu() {
		return lend_stu;
	}
	public void setLend_stu(int lend_stu) {
		this.lend_stu = lend_stu;
	}
	public long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "Book [book_id=" + book_id + ", code=" + code + ", name=" + name + ", author=" + author + ", press="
				+ press + ", lend_num=" + lend_num + ", type_id=" + type_id + ", create_by=" + create_by + ", number="
				+ number + ", lend_stu=" + lend_stu + ", create_time=" + create_time + "]";
	}
	public Book(int book_id, String code, String name, String author, String press, int lend_num, int type_id,
			String create_by, int number, int lend_stu, long create_time) {
		super();
		this.book_id = book_id;
		this.code = code;
		this.name = name;
		this.author = author;
		this.press = press;
		this.lend_num = lend_num;
		this.type_id = type_id;
		this.create_by = create_by;
		this.number = number;
		this.lend_stu = lend_stu;
		this.create_time = create_time;
	}
	public Book() {
		super();
	}
}
