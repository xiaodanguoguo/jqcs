package jq.steel.cs.services.base.facade.service.sysbasics.impl;

import jq.steel.cs.services.base.facade.dao.AddressMapper;
import jq.steel.cs.services.base.facade.dao.UserRegistrationMapper;
import jq.steel.cs.services.base.facade.model.Address;
import jq.steel.cs.services.base.facade.model.UserRegistration;
import jq.steel.cs.services.base.facade.service.sysbasics.AddressService;
import jq.steel.cs.services.base.facade.service.sysbasics.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	@Autowired
	private UserRegistrationMapper userRegistrationMapper;


	/**
	 * 根据id，查询省市区
	 * @return
	 */
	@Override
	public UserRegistration getSale(UserRegistration userRegistration) {
		UserRegistration userRegistration1 = userRegistrationMapper.getSale(userRegistration);
		return userRegistration1;
	}
}


