package com.ebase.core.easymock.weblogic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.easymock.dto.User;
import com.ebase.core.easymock.facade.UserFacade;
import com.ebase.core.easymock.facade.VerificationFacade;

@Service
public class UserWeblogic {

	@Autowired
	private UserFacade userFacade;
	
	@Autowired
	private VerificationFacade verificationFacade;
	
	public boolean regUser(String name,String password,String code){
		boolean su =  verificationFacade.verify(name, code);
		System.out.println(su);
		if(!su){
			throw new BusinessException("验证码不正确");
		}
		return userFacade.reg(name, password);
	}
	
	public List<User> queryUser(String name){
		return userFacade.queryUsers(name);
	}

	public User getUser(Integer userId){
		return userFacade.getUser(userId);
	}
	
	public UserFacade getUserFacade() {
		return userFacade;
	}

	public void setUserFacade(UserFacade userFacade) {
		this.userFacade = userFacade;
	}

	public VerificationFacade getVerificationFacade() {
		return verificationFacade;
	}

	public void setVerificationFacade(VerificationFacade verificationFacade) {
		this.verificationFacade = verificationFacade;
	}
	
	
}
