package jq.steel.cs.services.base.api.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.base.api.vo.AcctInfoVO;
import jq.steel.cs.services.base.api.vo.OrgInfoVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * 
 * @author zhangx
 *
 */
@FeignClient(value = "${ser.name.base}")
public interface OrgInfoServiceAPI {
	
	/**
	 * 添加组织机构
	 * @param jsonRequest 参数（orgCode:机构代码,orgName:机构名称,parentId:上级机构,remark:备注,status:0:停用;1:启用,createdBy:创建人）
	 * @return 失败0 或 成功1
	 */
	@RequestMapping(value = "/addOrgInfo",method = RequestMethod.POST)
    ServiceResponse<String> addOrgInfo(JsonRequest<OrgInfoVO> jsonRequest);
	
	/**
	 * 删除组织机构
	 * @param jsonRequest 参数（id：主键）将会级联删除父类下的所有子类
	 * @return 可以删除返回删除个数（如果绑定用户返回不可删除）
	 */
	@RequestMapping(value = "/removeOrgInfo",method = RequestMethod.POST)
    ServiceResponse<Integer> removeOrgInfo(JsonRequest<OrgInfoVO> jsonRequest);
	
	/**
	 * 修改组织机构
	 * @param jsonRequest 根据主键id 参数（orgCode:机构代码,orgName:机构名称,parentId:上级机构,remark:备注,status:0:停用;1:启用,createdBy:创建人,updatedBy:修改人）
	 * @return 失败0 或 成功1
	 */
	@RequestMapping(value = "/saveOrgInfo",method = RequestMethod.POST)
    ServiceResponse<Integer> saveOrgInfo(JsonRequest<OrgInfoVO> jsonRequest);
	
	/**
	 * 查询组织机构
	 * @param jsonRequest 根据主键id
	 * @return 参数（id:主键,orgCode:机构代码,orgName:机构名称,parentId:上级机构,remark:备注,status:0:停用;1:启用,createdBy:创建人,createdTime:创建时间,updatedBy:修改人,updatedTime:修改时间）
	 */
	@RequestMapping(value = "/getOrgInfo",method = RequestMethod.POST)
    ServiceResponse<OrgInfoVO> getOrgInfo(JsonRequest<OrgInfoVO> jsonRequest);
	
	/**
	 * 查询组织机构分页
	 * @param jsonRequest
	 * @return 参数（id:主键,orgCode:机构代码,orgName:机构名称,parentId:上级机构,remark:备注,status:0:停用;1:启用,createdBy:创建人,createdTime:创建时间,updatedBy:修改人,updatedTime:修改时间）
	 */
	@RequestMapping(value = "/getListOrgInfo",method = RequestMethod.POST)
    JsonResponse<PageDTO<OrgInfoVO>> getListOrgInfo(JsonRequest<OrgInfoVO> jsonRequest);
	
	/**
	 * 组织表查询（父类查出子类信息）
	 * @param jsonRequest 父类id
	 * @return 子类（id:主键,orgCode:机构代码,orgName:机构名称,parentId:上级机构,remark:备注,status:0:停用;1:启用,createdBy:创建人,createdTime:创建时间,updatedBy:修改人,updatedTime:修改时间）
	 */
	@RequestMapping(value = "/getListTreeOrgInfo",method = RequestMethod.POST)
    ServiceResponse<List<OrgInfoVO>> getListTreeOrgInfo(JsonRequest<OrgInfoVO> jsonRequest);
	
	/**
	 * 组织表递归查询结构树
	 * @param jsonRequest 传入上级机构 parentId 
	 * @return 返回结构树
	 */
	@RequestMapping(value = "/getChildTreeOrgInfo",method = RequestMethod.POST)
    ServiceResponse<OrgInfoVO> getChildTreeOrgInfo(JsonRequest<OrgInfoVO> jsonRequest);
	
	 /**
	  * 根据当前用户的组织id，查询出当前用户及当前用户一下的组织
	  * 物料综合查询用
	  * @return
	  */
	@RequestMapping(value = "/getMaterielOrginfo",method = RequestMethod.POST)
	public ServiceResponse<List<OrgInfoVO>> getMaterielOrginfo(@RequestBody JsonRequest<AcctInfoVO> jsonRequest);

	/**
	 * @param:
	 * @return:
	 * @description: 审核列表
	 * @author: lirunze
	 * @Date: 2018/8/31
	 */
	@RequestMapping(value = "/audit/list", method = RequestMethod.POST)
    JsonResponse<PageDTO<OrgInfoVO>> getAuditOrgList(@RequestBody JsonRequest<OrgInfoVO> jsonRequest);

	/**
	 * @param:
	 * @return:
	 * @description: 审核
	 * @author: lirunze
	 * @Date: 2018/8/31
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	ServiceResponse<Integer> getAuditOrg(@RequestBody JsonRequest<OrgInfoVO> jsonRequest);


	/**
	 * 组织表查询结构树（内存拼接）
	 * @param jsonRequest
	 * @return 返回结构树
	 *//*
	@RequestMapping(value = "/getListRecursionOrgInfo",method = RequestMethod.POST)
	JsonResponse getListRecursionOrgInfo(JsonRequest<OrgInfoVO> jsonRequest);*/


	/**
	 * @param:
	 * @return:
	 * @description: 验证是否有客户名称
	 * @author: wushibin
	 * @Date: 2018/8/31
	 */
	@RequestMapping(value = "/selectOrgName", method = RequestMethod.POST)
	ServiceResponse<OrgInfoVO> selectOrgName(@RequestBody JsonRequest<OrgInfoVO> jsonRequest);


	/**
	 * @param:
	 * @return:
	 * @description: 通过sap编码获取客户名称
	 * @author: lirunze
	 * @Date: 2018/9/3
	 */
	@RequestMapping(value = "/getOrgName", method = RequestMethod.POST)
	ServiceResponse<OrgInfoVO> getOrgName(@RequestBody JsonRequest<OrgInfoVO> jsonRequest);
}
