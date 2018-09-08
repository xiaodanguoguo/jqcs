package com.ebase.core.easymock.facade;

import java.util.List;

import com.ebase.core.easymock.dto.User;
import com.ebase.core.exception.BusinessException;

public interface UserFacade {

	boolean reg(String name,String password) throws BusinessException;

	User getUser(Integer userId)throws BusinessException;
	
	List<User> queryUsers(String name)throws BusinessException;
	
}
