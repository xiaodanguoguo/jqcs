package jq.steel.cs.services.base.facade.service.sysbasics.impl;

import com.ebase.core.StringHelper;
import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.StringUtil;
import com.ebase.utils.math.MathHelper;
import jq.steel.cs.services.base.api.vo.MessageVO;
import jq.steel.cs.services.base.api.vo.OrgInfoVO;
import jq.steel.cs.services.base.facade.common.IsDelete;
import jq.steel.cs.services.base.facade.common.Status;
import jq.steel.cs.services.base.facade.dao.*;
import jq.steel.cs.services.base.facade.model.*;
import jq.steel.cs.services.base.facade.service.message.MessageService;
import jq.steel.cs.services.base.facade.service.sysbasics.AddressService;
import jq.steel.cs.services.base.facade.service.sysbasics.OrgInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressMapper addressMapper;


	/**
	 * 根据id，查询省市区
	 * @return
	 */
	@Override
	public List<Address> getList(Address address) {
		List<Address> addresses = addressMapper.findList(address);
		return addresses;
	}
}


