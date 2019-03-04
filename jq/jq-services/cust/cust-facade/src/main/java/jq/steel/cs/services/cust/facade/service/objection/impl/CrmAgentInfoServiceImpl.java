package jq.steel.cs.services.cust.facade.service.objection.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.CrmAgentInfoVO;
import jq.steel.cs.services.cust.api.vo.CrmCustomerInfoVO;
import jq.steel.cs.services.cust.facade.dao.CrmAgentInfoMapper;
import jq.steel.cs.services.cust.facade.dao.CrmCustomerInfoMapper;
import jq.steel.cs.services.cust.facade.model.CrmAgentInfo;
import jq.steel.cs.services.cust.facade.model.CrmCustomerInfo;
import jq.steel.cs.services.cust.facade.service.objection.CrmAgentInfoService;
import jq.steel.cs.services.cust.facade.service.objection.CrmCustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CrmAgentInfoServiceImpl implements CrmAgentInfoService{

    @Autowired
    private CrmAgentInfoMapper crmAgentInfoMapper;

    //新增/修改
    @Override
    public CrmAgentInfoVO agentInfoInsert(CrmAgentInfoVO crmAgentInfoVO) {
        String orgName = crmAgentInfoVO.getOrgName();
        String orgCode = crmAgentInfoVO.getOrgCode();
        CrmAgentInfo crmAgentInfo = BeanCopyUtil.copy(crmAgentInfoVO, CrmAgentInfo.class);
        if( crmAgentInfo.getSid()== null ||crmAgentInfo.getSid().equals("")){
            crmAgentInfo.setCreatedBy(orgCode);
            crmAgentInfo.setCreatedDt(new Date());
            crmAgentInfo.setAgentId(orgCode);
            //新增
            if (crmAgentInfo.getDefaultFlag().equals("Y")){
                CrmAgentInfo crmAgentInfo1 = new CrmAgentInfo();
                crmAgentInfo1.setUpdatedDt(new Date());
                crmAgentInfo1.setUpdatedBy(orgCode);
                crmAgentInfo1.setAgentId(crmAgentInfo.getAgentId());
                crmAgentInfo1.setDefaultFlag("N");
                crmAgentInfoMapper.updateAll(crmAgentInfo1);
            }else {
                //如果是第一条就设置默认联系人
                List<CrmAgentInfo> crmAgentInfos = crmAgentInfoMapper.isOne(crmAgentInfo);
                if (crmAgentInfos.size()>0){

                }else {
                    crmAgentInfo.setDefaultFlag("Y");
                }
            }
            Integer integer = crmAgentInfoMapper.insertSelective(crmAgentInfo);
            CrmAgentInfoVO crmAgentInfoVOs = BeanCopyUtil.copy(crmAgentInfo, CrmAgentInfoVO.class);
            return crmAgentInfoVOs;
        }else {
            //修改 SID
            crmAgentInfo.setUpdatedBy(orgCode);
            crmAgentInfo.setUpdatedDt(new Date());
            if (crmAgentInfo.getDefaultFlag().equals("Y")){
                CrmAgentInfo crmAgentInfo1 = new CrmAgentInfo();
                crmAgentInfo1.setUpdatedDt(new Date());
                crmAgentInfo1.setUpdatedBy(orgCode);
                crmAgentInfo1.setAgentId(crmAgentInfo.getAgentId());
                crmAgentInfo1.setDefaultFlag("N");
                crmAgentInfoMapper.updateAll(crmAgentInfo1);
            }
            crmAgentInfoMapper.updateByPrimaryKeySelective(crmAgentInfo);

            CrmAgentInfoVO crmAgentInfoVOs = BeanCopyUtil.copy(crmAgentInfo, CrmAgentInfoVO.class);
            return crmAgentInfoVOs;
        }

    }
    //分页查询
    @Override
    public PageDTO<CrmAgentInfoVO> findByPage(CrmAgentInfoVO crmAgentInfoVO) {
        try {
            //转换mdel
            CrmAgentInfo crmAgentInfo = new CrmAgentInfo();
            BeanCopyUtil.copy(crmAgentInfoVO,crmAgentInfo);
            PageDTOUtil.startPage(crmAgentInfoVO);
            List<CrmAgentInfo> crmAgentInfos = crmAgentInfoMapper.findByPage(crmAgentInfo);
            //转换返回对象
            List<CrmAgentInfoVO> crmAgentInfoVOList = BeanCopyUtil.copyList(crmAgentInfos, CrmAgentInfoVO.class);
            // 分页对象
            PageDTO<CrmAgentInfoVO> transform = PageDTOUtil.transform(crmAgentInfoVOList);
            return transform;

        }finally {
            PageDTOUtil.endPage();
        }
    }

    @Override
    public Integer agentInfoDelete(CrmAgentInfoVO crmAgentInfoVO) {
        CrmAgentInfo crmAgentInfo = BeanCopyUtil.copy(crmAgentInfoVO, CrmAgentInfo.class);
        Integer i = crmAgentInfoMapper.deleteByPrimaryKey(crmAgentInfo.getSid());
        return i;
    }

    @Override
    public List<CrmAgentInfoVO> findDefault(CrmAgentInfoVO crmAgentInfoVO) {
        //转换mdel
        CrmAgentInfo crmAgentInfo = new CrmAgentInfo();
        BeanCopyUtil.copy(crmAgentInfoVO,crmAgentInfo);
        List<CrmAgentInfo> crmCustomerInfo1 = crmAgentInfoMapper.findDefault(crmAgentInfo);
        List<CrmAgentInfoVO> crmCustomerInfoVOS = BeanCopyUtil.copyList(crmCustomerInfo1,CrmAgentInfoVO.class);
        return crmCustomerInfoVOS;
    }

    @Override
    public List<CrmAgentInfoVO> findagentInfoList(CrmAgentInfoVO crmAgentInfoVO) {
        CrmAgentInfo crmAgentInfo = new CrmAgentInfo();
        BeanCopyUtil.copy(crmAgentInfoVO,crmAgentInfo);
        List<CrmAgentInfo> crmAgentInfos = crmAgentInfoMapper.findByPage(crmAgentInfo);
        //转换返回对象
        List<CrmAgentInfoVO> crmCustomerInfoVOS = BeanCopyUtil.copyList(crmAgentInfos, CrmAgentInfoVO.class);
        return crmCustomerInfoVOS;
    }

    @Override
    public CrmAgentInfoVO findagentInfo(CrmAgentInfoVO crmAgentInfoVO) {
        CrmAgentInfo crmAgentInfo = crmAgentInfoMapper.selectByPrimaryKey(crmAgentInfoVO.getSid());
        CrmAgentInfoVO crmAgentInfoVO1 = new CrmAgentInfoVO();
        BeanCopyUtil.copy(crmAgentInfo, crmAgentInfoVO1);

        return crmAgentInfoVO1;
    }
}
