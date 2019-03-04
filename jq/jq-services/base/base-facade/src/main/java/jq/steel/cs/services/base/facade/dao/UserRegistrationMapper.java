package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.facade.model.UserRegistration;

public interface UserRegistrationMapper {
    int insert(UserRegistration record);

    int insertSelective(UserRegistration record);

    UserRegistration getSale(UserRegistration record);


}