package jq.steel.cs.services.base.facade.dao;

import jq.steel.cs.services.base.facade.model.UserRegistration;

import java.util.List;

public interface UserRegistrationMapper {
    int insert(UserRegistration record);

    int insertSelective(UserRegistration record);

    List<UserRegistration> getSale(UserRegistration record);

    List<UserRegistration> getList(UserRegistration record);

}