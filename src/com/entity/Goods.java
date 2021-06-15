package com.entity;

import java.math.BigDecimal;

public class Goods {
	private int id;
	private String gname;
	private double gprice;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public double getGprice() {
		return gprice;
	}
	public void setGprice(double gprice) {
		this.gprice = gprice;
	}
	@Override
	public String toString() {
		return "Goods [id=" + id + ", gname=" + gname + ", gprice=" + gprice + "]";
	}
	public Goods(int id, String gname, double gprice) {
		super();
		this.id = id;
		this.gname = gname;
		this.gprice = gprice;
	}
	public Goods() {
		super();
	}
	
}
