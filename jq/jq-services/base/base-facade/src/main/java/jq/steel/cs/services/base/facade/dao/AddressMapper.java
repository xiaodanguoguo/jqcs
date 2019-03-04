package jq.steel.cs.services.base.facade.dao;


import jq.steel.cs.services.base.facade.model.Address;

import java.util.List;

public interface AddressMapper {
    List<Address> findList(Address record);
}