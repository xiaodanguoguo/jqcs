package jq.steel.cs.services.base.facade.service.sysbasics.impl;

import jq.steel.cs.services.base.facade.dao.AddressMapper;
import jq.steel.cs.services.base.facade.dao.UserRegistrationMapper;
import jq.steel.cs.services.base.facade.model.Address;
import jq.steel.cs.services.base.facade.model.UserRegistration;
import jq.steel.cs.services.base.facade.service.sysbasics.AddressService;
import jq.steel.cs.services.base.facade.service.sysbasics.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	@Autowired
	private UserRegistrationMapper userRegistrationMapper;


	/**
	 * 根据产品类别、公司所在区域对照表对应关系进行筛选。
	 	例如：用户选择了用户选了线棒材+碳钢+不锈钢，那就以线棒材+所在区域去选，户选了线碳钢+不锈钢，那就以碳钢+所在区域去选。
	 * @return
	 */
	@Override
	public UserRegistration getSale(UserRegistration userRegistration) {
		//产品类别
		if (userRegistration.getProductClassification().indexOf(",")!=-1){
			String a []  = userRegistration.getProductClassification().split(",");
			userRegistration.setProductClassification(a[0]);
		}
		//省市
		if (userRegistration.getArea().indexOf(",")!=-1){
			String a []  = userRegistration.getArea().split(",");
			userRegistration.setProvince(a[0]);
			userRegistration.setCity(a[1]);
		}
		//产品分类 +省份 查询 的集合  判断是否在其中 不在改为‘其他’
		List<UserRegistration> userRegistrations= userRegistrationMapper.getList(userRegistration);
		if(userRegistrations.size()>0){
			String a="";
			for (UserRegistration userRegistration22:userRegistrations){
				if (userRegistration22.getCity().equals(userRegistration.getCity())){
					a="a";
				}
			}
			if(a.equals("")){
				userRegistration.setCity("其他");
			}
		}
		List<UserRegistration> userRegistration1 = userRegistrationMapper.getSale(userRegistration);
		if(userRegistration1.size()>0){
			return userRegistration1.get(0);
		}else{
			UserRegistration userRegistration2 = new UserRegistration();
			userRegistration2.setSalesCompany("111");
			return userRegistration2;
		}

	}
}


