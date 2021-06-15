package com.entity;

/**
 * 图书图片实体类
 * 2021/6/8
 * @author 庞海
 *
 */
public class Book_img {
	private int book_img;
	private String url;
	public int getBook_img() {
		return book_img;
	}
	public void setBook_img(int book_img) {
		this.book_img = book_img;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Book_img [book_img=" + book_img + ", url=" + url + "]";
	}
	public Book_img(int book_img, String url) {
		super();
		this.book_img = book_img;
		this.url = url;
	}
	public Book_img() {
		super();
	}
}
