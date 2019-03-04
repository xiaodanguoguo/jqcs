package jq.steel.cs.services.base.facade.service.sysbasics;


import com.ebase.core.page.PageDTO;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.base.api.vo.OrgInfoVO;
import jq.steel.cs.services.base.facade.model.AcctInfo;
import jq.steel.cs.services.base.facade.model.Address;
import jq.steel.cs.services.base.facade.model.OrgInfo;

import java.util.List;

/**
 * 
 * @author zhangx
 *
 */
public interface AddressService {

	List<Address> getList(Address address);
}
