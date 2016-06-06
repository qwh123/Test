package com.hp.dao;

import java.util.List;

import com.hp.bean.UPublish;

public interface UPublishDao {
	List<UPublish.Data> loadUPublish(String json);
}
