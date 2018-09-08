package com.ebase.core.elasticsearch.repository;

import com.ebase.core.elasticsearch.domain.User;
import org.springframework.stereotype.Repository;

import com.ebase.core.elasticsearch.EsBaseRepository;
import com.ebase.core.elasticsearch.EsRepository;

@Repository
public class UserRepository extends EsBaseRepository<User> implements EsRepository<User> {

	@Override
	public Class<User> getEntityClass() {
		return User.class;
	}

	@Override
	public String getType() {
		return "usertest";
	}

	@Override
	public String getIndex() {
		return "panly";
	}

}
