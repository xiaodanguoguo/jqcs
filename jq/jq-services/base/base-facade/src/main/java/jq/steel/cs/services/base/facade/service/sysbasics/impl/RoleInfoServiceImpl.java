package jq.steel.cs.services.base.facade.service.sysbasics.impl;


import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.base.api.vo.RoleInfoVO;
import jq.steel.cs.services.base.facade.common.IsDelete;
import jq.steel.cs.services.base.facade.common.Status;
import jq.steel.cs.services.base.facade.common.SysPramType;
import jq.steel.cs.services.base.facade.dao.AcctOperPrivRelaMapper;
import jq.steel.cs.services.base.facade.dao.AcctRoleGroupRoleMapper;
import jq.steel.cs.services.base.facade.dao.AcctRoleRealMapper;
import jq.steel.cs.services.base.facade.dao.RoleInfoMapper;
import jq.steel.cs.services.base.facade.model.AcctRoleGroupRole;
import jq.steel.cs.services.base.facade.model.RoleInfo;
import jq.steel.cs.services.base.facade.service.sysbasics.RoleInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: zhaoyuhang
 */
@Service
public class RoleInfoServiceImpl implements RoleInfoService {

    private static Logger LOG = LoggerFactory.getLogger(RoleInfoServiceImpl.class);

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Autowired
    private AcctRoleGroupRoleMapper acctRoleGroupRoleMapper;

    @Autowired
    private AcctOperPrivRelaMapper acctOperPrivRelaMapper;

    @Autowired
    private AcctRoleRealMapper acctRoleRealMapper;

    @Override
    public List<RoleInfoVO> roleInfoTree(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        List<String> idall = new ArrayList<>();
        BeanCopyUtil.copy(jsonRequest,roleInfo);
        if(StringUtils.isEmpty(roleInfo.getOrgIdAll())){

        }else{
            String[]  orgid=roleInfo.getOrgIdAll().split(",");

            for(int i=0;i<orgid.length;i++){
                idall.add(orgid[i]);
            }
            roleInfo.setOrgIdAlls(idall);
        }

        List<RoleInfo> list = roleInfoMapper.roleGroupTree(roleInfo);
        List<RoleInfo> listRo=new ArrayList<>();
        for(int j=0;j<list.size();j++){
            listRo.add(list.get(j));
            roleInfo.setParentApplicationId(list.get(j).getRoleGroupId());
            roleInfo.setRoleGroupId(list.get(j).getRoleGroupId());
            List<RoleInfo> listRoleInfo =roleInfoMapper.findRoleDetailed(roleInfo);
            listRo.get(j).setChildren(menuChild(roleInfo,listRoleInfo,roleInfo.getRoleGroupTitle()));
        }
        roleInfo.setRoleGroupId(null);
        List<RoleInfo> listRoleInfo = roleInfoMapper.roleInfoTree(roleInfo);

        listRo.addAll(listRoleInfo);

        List<RoleInfoVO> result = BeanCopyUtil.copyList(listRo, RoleInfoVO.class);
        return result;
    }

    public List<RoleInfo> menuChild(RoleInfo roleInfo,List<RoleInfo> listRoleInfo,String roleGroupTitle){
        String parentApplicationName=roleGroupTitle;
        //根据父Id查询数据
        List<RoleInfo> list = roleInfoMapper.roleGroupParentTree(roleInfo);

        for(int i=0;i<list.size();i++){
            List<RoleInfo> listTreeNull=new ArrayList<>();
            list.get(i).setChildren(listTreeNull);
            roleInfo.setParentApplicationId(list.get(i).getRoleGroupId());
            list.get(i).setParentApplicationName(parentApplicationName);
            //查询当前级别的下一级是否有数据
            List<RoleInfo> listTree = roleInfoMapper.roleGroupParentTree(roleInfo);
            RoleInfo role=new RoleInfo();
            role.setRoleGroupId(list.get(i).getRoleGroupId());
            //roleInfo.setOrgIdAlls(list.get(i).getOrgIdAlls());
            List<RoleInfo> ListRole =roleInfoMapper.findRoleDetailed(role);
            if(listTree.size()>0){
                list.get(i).setChildren(menuChild(roleInfo,ListRole,list.get(i).getRoleGroupTitle()));
            }else{
                if(ListRole.size()>0){
                    for(int z=0;z<ListRole.size();z++){
                        listTreeNull.add(ListRole.get(z));
                    }
                }
                list.get(i).setChildren(listTreeNull);
            }
        }
        for(int z=0;z<listRoleInfo.size();z++){
            list.add(listRoleInfo.get(z));
        }
        return list;
    }

    @Override
    public List<RoleInfoVO> roleInfoAll(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,roleInfo);
        try {
            if(StringUtils.isEmpty(roleInfo.getOrgIdAll())){

            }else{
                String[]  strs=roleInfo.getOrgIdAll().split(",");
                List<String> idall = new ArrayList<>();
                for(int i=0;i<strs.length;i++){
                    idall.add(strs[i]);
                }

                roleInfo.setOrgIdAlls(idall);
            }


            roleInfo.setIsDelete(IsDelete.NO.getCode());
            roleInfo.setStatus(Status.START.getCode());

            //查询所有数据
            List<RoleInfo> list = roleInfoMapper.findAll(roleInfo);
            List<RoleInfoVO> result = BeanCopyUtil.copyList(list, RoleInfoVO.class);
            return result;
        } finally {
            PageDTOUtil.endPage();
        }
    }



    @Override
    public List<RoleInfoVO> roleInfoAllLike(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,roleInfo);
        try {

            roleInfo.setIsDelete(IsDelete.NO.getCode());
            roleInfo.setStatus(Status.START.getCode());

            //查询所有数据
            List<RoleInfo> list = roleInfoMapper.findAllLike(roleInfo);
            List<RoleInfoVO> result = BeanCopyUtil.copyList(list, RoleInfoVO.class);
            return result;
        } finally {
            PageDTOUtil.endPage();
        }
    }

    @Override
    public List<RoleInfoVO> roleRoleAcctInfo(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,roleInfo);

        //查询所有数据
        List<RoleInfo> list = roleInfoMapper.roleRoleAcctInfo(roleInfo);
        List<RoleInfoVO> result = BeanCopyUtil.copyList(list, RoleInfoVO.class);
        return result;
    }


    @Override
    public PageDTO<RoleInfoVO> roleInfoList(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,roleInfo);
        try {
            PageDTOUtil.startPage(jsonRequest);

            roleInfo.setRoleTitle(roleInfo.getRoleCode());
            roleInfo.setIsDelete(IsDelete.NO.getCode());
            //查询分页数据
            List<RoleInfo> list = roleInfoMapper.find(roleInfo);

            PageDTO<RoleInfo> page = PageDTOUtil.transform(list);

            //转换
            PageDTO<RoleInfoVO> pageVo = new PageDTO(page.getPageNum(),page.getPageSize());
            pageVo.setTotal(page.getTotal());
            List<RoleInfo> resultData = page.getResultData();

            List<RoleInfoVO> result = BeanCopyUtil.copyList(resultData, RoleInfoVO.class);
            pageVo.setResultData(result);
            return pageVo;
        } finally {
            PageDTOUtil.endPage();
        }
    }


    @Override
    public String verificationDeleteRoelInfo(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,roleInfo);
        List<RoleInfo> list=roleInfoMapper.verificationDeleteRoelInfo(roleInfo);
        if(CollectionUtils.isEmpty(list)){
            return "是否删除";
        }else{
            return "角色与用户有关联，请先解除关联再删除";
        }
    }

    @Override
    public RoleInfoVO keepRoleInfo(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,roleInfo);
        //int i = roleInfoMapper.insertSelective(roleInfo);
        String opt = roleInfo.getOpt();

        if(SysPramType.DELETE.getMsg().equals(opt)){
            //删除
            //roleInfoMapper.deleteByPrimaryKey(roleInfo.get(i).getRoleId());
            /*RoleInfo roleInfo1IsDelete=new RoleInfo();
            roleInfo1IsDelete.setRoleId(roleInfo.get(i).getRoleId());
            roleInfo1IsDelete.setIsDelete(IsDelete.YES.getCode());*/
            roleInfo.setIsDelete(IsDelete.YES.getCode());
            RoleInfo roleInfo1=roleInfoMapper.findGroupId(roleInfo);
            if(StringUtils.isEmpty(roleInfo1)){

            }else{
                roleInfo.setRoleGroupId(roleInfo1.getRoleGroupId());
            }
            roleInfoMapper.updateByPrimaryKeySelective(roleInfo);

            //删除角色与用户关联表
            acctRoleRealMapper.deleteByPrimaryKey2(roleInfo.getRoleId());
            acctOperPrivRelaMapper.deleteRoleId(roleInfo.getRoleId());
        }else if(SysPramType.UPDATE.getMsg().equals(opt)){
            roleInfo.setUpdatedTime(new Date());
            //修改
            AcctRoleGroupRole acctRoleGroupRole = new AcctRoleGroupRole();
            if(StringUtils.isEmpty(roleInfo.getIsStatus())){
                if(StringUtils.isEmpty(roleInfo.getRoleGroupId())){
                    //删除角色关联的角色组关系
                    acctRoleGroupRoleMapper.deleteRoleId(acctRoleGroupRole.getRoleId());
                }else{
                    acctRoleGroupRole.setRoleId(roleInfo.getRoleId());
                    acctRoleGroupRole.setRoleGroupId(roleInfo.getRoleGroupId());
                    acctRoleGroupRoleMapper.deleteRoleId(acctRoleGroupRole.getRoleId());
                    //添加角色和角色组的关联关系
                    acctRoleGroupRoleMapper.insertSelective(acctRoleGroupRole);
                }
                roleInfoMapper.updateByPrimaryKeySelective(roleInfo);
            }else{
                roleInfoMapper.updateByPrimaryKeySelective(roleInfo);
            }
        }else if(SysPramType.INSERT.getMsg().equals(opt)){
            roleInfo.setRoleId(null);

            roleInfo.setIsDelete(IsDelete.NO.getCode());
            roleInfo.setCreatedTime(new Date());
            roleInfoMapper.insertSelective(roleInfo);
            //添加
            if(StringUtils.isEmpty(roleInfo.getRoleGroupId())){

            }else{
                //添加角色和角色组的关联关系
                AcctRoleGroupRole acctRoleGroupRole = new AcctRoleGroupRole();
                acctRoleGroupRole.setRoleId(roleInfo.getRoleId());
                acctRoleGroupRole.setRoleGroupId(roleInfo.getRoleGroupId());
                acctRoleGroupRoleMapper.insertSelective(acctRoleGroupRole);
            }
//            }else{
//                jsonResponse.setRetCode("0000002");
//                jsonResponse.setRetDesc("角色名称重复!");
//            }
        }
        roleInfo.setOrRole("角色");
        RoleInfoVO roleInfoVO= BeanCopyUtil.copy(roleInfo, RoleInfoVO.class);
        return roleInfoVO;
    }

    @Override
    public List<RoleInfoVO> verificationIsTtitleRole(RoleInfoVO jsonRequest) {

        RoleInfo reqBody = new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,reqBody);
        List<RoleInfo> list=roleInfoMapper.verificationIsTtitle(reqBody);
        List<RoleInfoVO> result = BeanCopyUtil.copyList(list, RoleInfoVO.class);
        return result;
    }

    @Override
    public List<RoleInfoVO> getRoleCodeByAcctId(String acctId) {
        List<RoleInfo>  list = roleInfoMapper.getRoleCodeByAcctId(acctId);
        List<RoleInfoVO> result = BeanCopyUtil.copyList(list, RoleInfoVO.class);
        return result;
    }
}
