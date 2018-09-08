package com.ebase.core.easymock.facade;

import com.ebase.core.exception.BusinessException;

public interface VerificationFacade {
	
	public boolean verify(String name,String code) throws BusinessException;

}
