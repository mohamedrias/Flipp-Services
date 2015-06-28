package com.wiki.children.dao;

import java.io.Serializable;
import java.util.List;

import com.wiki.children.domain.Query;

public interface QueryDAO extends Serializable {
	
	public List<Query> getQueriesByAge(int age);
}
