package com.hp.bean;

import java.util.ArrayList;
import java.util.List;

public class XMLCity {
	private List<UCities> mCities = new ArrayList<UCities>();
	private int code;
	private String name;
	public List<UCities> getmCities() {
		return mCities;
	}
	public void setmCities(List<UCities> mCities) {
		this.mCities = mCities;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
