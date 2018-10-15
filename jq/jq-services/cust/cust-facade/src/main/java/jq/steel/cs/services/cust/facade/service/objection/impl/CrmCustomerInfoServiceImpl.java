package jq.steel.cs.services.cust.facade.service.objection.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.CrmCustomerInfoVO;
import jq.steel.cs.services.cust.facade.dao.CrmCustomerInfoMapper;
import jq.steel.cs.services.cust.facade.model.CrmCustomerInfo;
import jq.steel.cs.services.cust.facade.service.objection.CrmCustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CrmCustomerInfoServiceImpl implements CrmCustomerInfoService{

    @Autowired
    private CrmCustomerInfoMapper crmCustomerInfoMapper;

    //新增/修改
    @Override
    public CrmCustomerInfoVO orderUnitInsert(CrmCustomerInfoVO crmCustomerInfoVO) {
        String orgName = crmCustomerInfoVO.getOrgName();
        String orgCode = crmCustomerInfoVO.getOrgCode();
        CrmCustomerInfo crmCustomerInfo = BeanCopyUtil.copy(crmCustomerInfoVO, CrmCustomerInfo.class);
        if( crmCustomerInfo.getSid()== null ||crmCustomerInfo.getSid().equals("")){
            crmCustomerInfo.setCreatedBy(orgCode);
            crmCustomerInfo.setCreatedDt(new Date());
            crmCustomerInfo.setCustomerId(orgCode);
            //新增
            if (crmCustomerInfo.getDefaultFlag().equals("Y")){
                CrmCustomerInfo crmCustomerInfo1 = new CrmCustomerInfo();
                crmCustomerInfo1.setUpdatedDt(new Date());
                crmCustomerInfo1.setUpdatedBy(orgCode);
                crmCustomerInfo1.setCustomerId(crmCustomerInfo.getCustomerId());
                crmCustomerInfo1.setDefaultFlag("N");
                crmCustomerInfoMapper.updateAll(crmCustomerInfo1);
            }else {
                //如果是第一条就设置默认联系人
                List<CrmCustomerInfo> crmCustomerInfos = crmCustomerInfoMapper.isOne(crmCustomerInfo);
                if (crmCustomerInfos.size()>0){

                }else {
                    crmCustomerInfo.setDefaultFlag("Y");
                }
            }
            Integer integer = crmCustomerInfoMapper.insertSelective(crmCustomerInfo);
            CrmCustomerInfoVO crmCustomerInfoVOs = BeanCopyUtil.copy(crmCustomerInfo, CrmCustomerInfoVO.class);
            return crmCustomerInfoVOs;
        }else {
            //修改 SID
            crmCustomerInfo.setUpdatedBy(orgCode);
            crmCustomerInfo.setUpdatedDt(new Date());
            if (crmCustomerInfo.getDefaultFlag().equals("Y")){
                CrmCustomerInfo crmCustomerInfo1 = new CrmCustomerInfo();
                crmCustomerInfo1.setUpdatedDt(new Date());
                crmCustomerInfo1.setUpdatedBy(orgCode);
                crmCustomerInfo1.setCustomerId(crmCustomerInfo.getCustomerId());
                crmCustomerInfo1.setDefaultFlag("N");
                crmCustomerInfoMapper.updateAll(crmCustomerInfo1);
            }
            crmCustomerInfoMapper.updateByPrimaryKeySelective(crmCustomerInfo);

            CrmCustomerInfoVO crmCustomerInfoVOs = BeanCopyUtil.copy(crmCustomerInfo, CrmCustomerInfoVO.class);
            return crmCustomerInfoVOs;
        }

    }
    //分页查询
    @Override
    public PageDTO<CrmCustomerInfoVO> findByPage(CrmCustomerInfoVO crmCustomerInfoVO) {
        try {
            //转换mdel
            CrmCustomerInfo crmCustomerInfo = new CrmCustomerInfo();
            BeanCopyUtil.copy(crmCustomerInfoVO,crmCustomerInfo);
            PageDTOUtil.startPage(crmCustomerInfoVO);
            List<CrmCustomerInfo> crmCustomerInfos = crmCustomerInfoMapper.findByPage(crmCustomerInfo);
            //转换返回对象
            List<CrmCustomerInfoVO> crmCustomerInfoVOS = BeanCopyUtil.copyList(crmCustomerInfos, CrmCustomerInfoVO.class);
            // 分页对象
            PageDTO<CrmCustomerInfoVO> transform = PageDTOUtil.transform(crmCustomerInfoVOS);
            return transform;

        }finally {
            PageDTOUtil.endPage();
        }
    }

    @Override
    public Integer orderUnitDelete(CrmCustomerInfoVO crmCustomerInfoVO) {
        CrmCustomerInfo crmCustomerInfo = BeanCopyUtil.copy(crmCustomerInfoVO, CrmCustomerInfo.class);
        Integer i = crmCustomerInfoMapper.deleteByPrimaryKey(crmCustomerInfo.getSid());
        return i;
    }

    @Override
    public List<CrmCustomerInfoVO> findDefault(CrmCustomerInfoVO crmCustomerInfoVO) {
        //转换mdel
        CrmCustomerInfo crmCustomerInfo = new CrmCustomerInfo();
        BeanCopyUtil.copy(crmCustomerInfoVO,crmCustomerInfo);
        //crmCustomerInfo.setDefaultFlag("Y");
        List<CrmCustomerInfo> crmCustomerInfo1 = crmCustomerInfoMapper.findDefault(crmCustomerInfo);
        List<CrmCustomerInfoVO> crmCustomerInfoVOS = BeanCopyUtil.copyList(crmCustomerInfo1,CrmCustomerInfoVO.class);
        return crmCustomerInfoVOS;
    }

    @Override
    public List<CrmCustomerInfoVO> findorderUnitList(CrmCustomerInfoVO crmCustomerInfoVO) {
        CrmCustomerInfo crmCustomerInfo = new CrmCustomerInfo();
        BeanCopyUtil.copy(crmCustomerInfoVO,crmCustomerInfo);
        List<CrmCustomerInfo> crmCustomerInfos = crmCustomerInfoMapper.findByPage(crmCustomerInfo);
        //转换返回对象
        List<CrmCustomerInfoVO> crmCustomerInfoVOS = BeanCopyUtil.copyList(crmCustomerInfos, CrmCustomerInfoVO.class);
        return crmCustomerInfoVOS;
    }

    @Override
    public CrmCustomerInfoVO findorderUnitInfo(CrmCustomerInfoVO crmCustomerInfoVO) {
        CrmCustomerInfo crmCustomerInfo = crmCustomerInfoMapper.selectByPrimaryKey(crmCustomerInfoVO.getSid());
        CrmCustomerInfoVO crmCustomerInfoVO1 = new CrmCustomerInfoVO();
        BeanCopyUtil.copy(crmCustomerInfo, crmCustomerInfoVO1);

        return crmCustomerInfoVO1;
    }
}
