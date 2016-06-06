package com.hp.dao;

import java.util.List;

import com.hp.bean.UActiveList;

public interface UActiveDao {
	List<UActiveList.Data> loadActive(String jsonString);
}
