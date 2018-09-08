package jq.steel.cs.services.base.facade.controller.sysbasics;


import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.vo.RoleGroupVO;
import jq.steel.cs.services.base.facade.common.SysPramType;
import jq.steel.cs.services.base.facade.service.sysbasics.RoleGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统基础模块-  系统角色定义
 * @Auther: zhaoyuhang
 */
@RestController
public class RoleGroupController {

    private static Logger LOG = LoggerFactory.getLogger(RoleGroupController.class);

    private static Logger logger = LoggerFactory.getLogger(RoleGroupController.class);

    @Autowired
    private RoleGroupService roleGroupService;


    /**
     * 系统参数 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/roleGroupList")
    public ServiceResponse<PageDTO<RoleGroupVO>> roleGroupList(@RequestBody RoleGroupVO jsonRequest){
        ServiceResponse<PageDTO<RoleGroupVO>> jsonResponse = new ServiceResponse<>();
       try {
            LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
            PageDTO<RoleGroupVO> page = roleGroupService.roleGroupList(jsonRequest);
            jsonResponse.setRetContent(page);
       } catch (Exception e) {
           jsonResponse.setException(new BusinessException("0104001", new Object[]{jsonResponse}));
           logger.error(e.getMessage());
       }
        return jsonResponse;
    }

    /**
     * 系统参数 所有可用角色组 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/roleGroupAll")
    public ServiceResponse<List<RoleGroupVO>> roleGroupAll(@RequestBody RoleGroupVO jsonRequest){
        ServiceResponse<List<RoleGroupVO>> jsonResponse = new ServiceResponse();
        try {

            if(StringUtils.isEmpty(jsonRequest.getOrgIdAll())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }
            LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
            List<RoleGroupVO> roleGroup = roleGroupService.findAll(jsonRequest);
            jsonResponse.setRetContent(roleGroup);
        } catch (Exception e) {
            jsonResponse.setException(new BusinessException("0102003", new Object[]{jsonResponse}));
            logger.error(e.getMessage());
        }
        return jsonResponse;
    }


    @RequestMapping("/verificationDeleteRoleGroup")
    public ServiceResponse<String> verificationDeleteRoleGroup(@RequestBody RoleGroupVO jsonRequest){
        ServiceResponse<String> jsonResponse = new ServiceResponse();
        try {
            if(StringUtils.isEmpty(jsonRequest.getRoleGroupId())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }
            LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
            String ver = roleGroupService.verificationDeleteRoleGroup(jsonRequest);
            jsonResponse.setRetContent(ver);
        } catch (Exception e) {
            jsonResponse.setException(new BusinessException("0102003", new Object[]{jsonResponse}));
            logger.error(e.getMessage());
        }
        return jsonResponse;
    }

    @RequestMapping("/keepRoleGroup")
    public ServiceResponse<RoleGroupVO> keepRoleGroup(@RequestBody RoleGroupVO jsonRequest){
        ServiceResponse<RoleGroupVO> jsonResponse = new ServiceResponse();
        try {
            //opt update
            if(StringUtils.isEmpty(jsonRequest.getOpt())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }else{
                if(SysPramType.DELETE.getMsg().equals(jsonRequest.getOpt())) {
                    //分类ID
                    if(StringUtils.isEmpty(jsonRequest.getRoleGroupId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                }
                if(SysPramType.UPDATE.getMsg().equals(jsonRequest.getOpt())) {
                    //分类ID
                    if(StringUtils.isEmpty(jsonRequest.getRoleGroupId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    //分类名称
                    if(StringUtils.isEmpty(jsonRequest.getRoleGroupTitle())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    //组织ID
                    if(StringUtils.isEmpty(jsonRequest.getOrgId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                }
                if(SysPramType.INSERT.getMsg().equals(jsonRequest.getOpt())) {
                    //分类名称
                    if(StringUtils.isEmpty(jsonRequest.getRoleGroupTitle())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    //组织ID
                    if(StringUtils.isEmpty(jsonRequest.getOrgId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                }
            }
            LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
            RoleGroupVO roleGroupVO = roleGroupService.keepRoleGroup(jsonRequest);
            jsonResponse.setRetContent(roleGroupVO);
        } catch (Exception e) {
            jsonResponse.setException(new BusinessException("0102003", new Object[]{jsonResponse}));
            logger.error(e.getMessage());
        }
        return jsonResponse;
    }
}
