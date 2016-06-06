package com.hp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XMLCountry implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<XMLProvine> mProvines=new ArrayList<XMLProvine>();
	private int  code;
	private String name;
	private int ID;
	public List<XMLProvine> getmProvines() {
		return mProvines;
	}
	public void setmProvines(List<XMLProvine> mProvines) {
		this.mProvines = mProvines;
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
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	

}
