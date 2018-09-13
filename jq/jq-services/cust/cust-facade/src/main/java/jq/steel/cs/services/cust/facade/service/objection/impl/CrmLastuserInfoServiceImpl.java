package jq.steel.cs.services.cust.facade.service.objection.impl;

import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.CrmLastuserInfoVO;
import jq.steel.cs.services.cust.facade.dao.CrmLastuserInfoMapper;
import jq.steel.cs.services.cust.facade.model.CrmLastuserInfo;
import jq.steel.cs.services.cust.facade.service.objection.CrmLastuserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class CrmLastuserInfoServiceImpl implements CrmLastuserInfoService{

    @Autowired
    private CrmLastuserInfoMapper crmLastuserInfoMapper;
    //新增/修改
    @Override
    public Integer unitOfUseInsert(CrmLastuserInfoVO crmLastuserInfoVO) {
        CrmLastuserInfo crmLastuserInfo = BeanCopyUtil.copy(crmLastuserInfoVO, CrmLastuserInfo.class);
        String orgName = crmLastuserInfoVO.getOrgName();
        String orgCode = crmLastuserInfoVO.getOrgCode();
        if(crmLastuserInfo.getSid()== null || crmLastuserInfo.getSid().equals("")){
            crmLastuserInfo.setCreatedBy(orgCode);
            crmLastuserInfo.setCreatedDt(new Date());
            crmLastuserInfo.setCustomerId(orgCode);
            //新增
            if (crmLastuserInfo.getDefaultFlag().equals("Y")){
                CrmLastuserInfo crmLastuserInfo1 = new CrmLastuserInfo();
                crmLastuserInfo1.setUpdatedDt(new Date());
                crmLastuserInfo1.setUpdatedBy(orgCode);
                crmLastuserInfo1.setDefaultFlag("N");
                crmLastuserInfoMapper.updateAll(crmLastuserInfo1);
            }else {
                //如果是第一条就设置默认联系人
                List<CrmLastuserInfo> crmLastuserInfos = crmLastuserInfoMapper.isOne(crmLastuserInfo);
                if (crmLastuserInfos.size()>0){

                }else {
                    crmLastuserInfo.setDefaultFlag("Y");
                }
            }
            //获取使用单位id
            int maxId = crmLastuserInfoMapper.selectMaxId(crmLastuserInfo);
           /* String maxid = String.valueOf(maxId);
            String string = "";
            if (maxId==0){
                string = String.valueOf(maxid);
            }else {
                string = maxid.substring(maxid.indexOf("-"+1,maxid.length()));
            }*/

            int in = maxId + 1;
            String lastUserId = orgCode+"-"+String.valueOf(in);
            crmLastuserInfo.setLastUserId(lastUserId);
            Integer integer = crmLastuserInfoMapper.insertSelective(crmLastuserInfo);
            return integer;
        }else {
            //修改 SID
            crmLastuserInfo.setUpdatedBy(orgCode);
            crmLastuserInfo.setUpdatedDt(new Date());
            if (crmLastuserInfo.getDefaultFlag().equals("Y")){
                CrmLastuserInfo crmLastuserInfo1 = new CrmLastuserInfo();
                crmLastuserInfo1.setUpdatedDt(new Date());
                crmLastuserInfo1.setUpdatedBy(orgCode);
                crmLastuserInfo1.setCustomerId(orgCode);
                crmLastuserInfo1.setDefaultFlag("N");
                crmLastuserInfoMapper.updateAll(crmLastuserInfo1);
            }
            crmLastuserInfoMapper.updateByPrimaryKeySelective(crmLastuserInfo);

            return  1;
        }

    }

    @Override
    public PageDTO<CrmLastuserInfoVO> findByPage(CrmLastuserInfoVO crmLastuserInfoVO) {
        try {
            //转换mdel
            CrmLastuserInfo crmLastuserInfo = new CrmLastuserInfo();
            BeanCopyUtil.copy(crmLastuserInfoVO,crmLastuserInfo);
            PageDTOUtil.startPage(crmLastuserInfoVO);
            List<CrmLastuserInfo> crmLastuserInfos = crmLastuserInfoMapper.findByPage(crmLastuserInfo);
            //转换返回对象
            List<CrmLastuserInfoVO> crmLastuserInfoVOS = BeanCopyUtil.copyList(crmLastuserInfos, CrmLastuserInfoVO.class);
            // 分页对象
            PageDTO<CrmLastuserInfoVO> transform = PageDTOUtil.transform(crmLastuserInfoVOS);
            return transform;

        }finally {
            PageDTOUtil.endPage();
        }
    }

    @Override
    public Integer unitOfUseDelete(CrmLastuserInfoVO crmLastuserInfoVO) {
        CrmLastuserInfo crmLastuserInfo = BeanCopyUtil.copy(crmLastuserInfoVO, CrmLastuserInfo.class);
        Integer i = crmLastuserInfoMapper.deleteByPrimaryKey(crmLastuserInfo.getSid());
        return i;
    }

    @Override
    public List<CrmLastuserInfoVO> findDefault(CrmLastuserInfoVO crmLastuserInfoVO) {
        //转换mdel
        CrmLastuserInfo crmLastuserInfo = new CrmLastuserInfo();
        BeanCopyUtil.copy(crmLastuserInfoVO,crmLastuserInfo);
        List<CrmLastuserInfo> crmCustomerInfo1= crmLastuserInfoMapper.findDefault(crmLastuserInfo);
        List<CrmLastuserInfoVO> crmLastuserInfoVOS = BeanCopyUtil.copyList(crmCustomerInfo1,CrmLastuserInfoVO.class);
        return crmLastuserInfoVOS;
    }

    @Override
    public List<CrmLastuserInfoVO> findunitOfUseList(CrmLastuserInfoVO crmLastuserInfoVO) {
        //转换mdel
        CrmLastuserInfo crmLastuserInfo = new CrmLastuserInfo();
        BeanCopyUtil.copy(crmLastuserInfoVO,crmLastuserInfo);
        List<CrmLastuserInfo> crmLastuserInfos = crmLastuserInfoMapper.findByPage(crmLastuserInfo);
        //转换返回对象
        List<CrmLastuserInfoVO> crmLastuserInfoVOS = BeanCopyUtil.copyList(crmLastuserInfos, CrmLastuserInfoVO.class);

        return crmLastuserInfoVOS;
    }
}
