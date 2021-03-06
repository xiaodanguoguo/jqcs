package jq.steel.cs.services.cust.facade.service.category.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.ebase.core.service.ServiceResponse;
import com.ebase.utils.BeanCopyUtil;
import jq.steel.cs.services.cust.api.vo.CrmProductCategoryVO;
import jq.steel.cs.services.cust.facade.common.ProductCategoryStatus;
import jq.steel.cs.services.cust.facade.common.SysPramType;
import jq.steel.cs.services.cust.facade.dao.CrmProductCategoryMapper;
import jq.steel.cs.services.cust.facade.model.CrmProductCategory;
import jq.steel.cs.services.cust.facade.service.category.CrmProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: CrmProductCategoryServiceImpl
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/8/25 10:05
 */
@Service("crmProductCategoryService")
public class CrmProductCategoryServiceImpl implements CrmProductCategoryService {

    @Autowired
    private CrmProductCategoryMapper crmProductCategoryMapper;

    @Override
    public List<CrmProductCategoryVO> getPage(CrmProductCategoryVO crmProductCategoryVO) {
        CrmProductCategory crmProductCategory = new CrmProductCategory();
        BeanCopyUtil.copy(crmProductCategoryVO, crmProductCategory);

        List<CrmProductCategory> list = crmProductCategoryMapper.getList(crmProductCategory);

        List<CrmProductCategoryVO> listvo = BeanCopyUtil.copyList(list,CrmProductCategoryVO.class);
        return listvo;

    }

    @Override
    public ServiceResponse<Boolean> insertCrmProductCategory(List<CrmProductCategoryVO> list) {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        boolean flag = false;
        serviceResponse.setRetContent(flag);
        if (CollectionUtils.isEmpty(list)) {
            serviceResponse.setResponseCode("0000001");
            return serviceResponse;
        }

        CrmProductCategory crmProductCategory = null;

        for (CrmProductCategoryVO crmProductCategoryVO : list) {
            // 删除
            if (SysPramType.DELETE.getMsg().equals(crmProductCategoryVO.getOpt())) {
                crmProductCategory = crmProductCategoryMapper.selectByPrimaryKey(crmProductCategoryVO.getCid());
                if (ProductCategoryStatus.SUBMIT.getCode().equals(crmProductCategory.getStatus())) {
                    serviceResponse.setRetCode("0902002",new Object[]{crmProductCategoryVO.getCategoryName()});
                    return serviceResponse;
                }
                crmProductCategoryMapper.deleteByPrimaryKey(crmProductCategoryVO.getCid());

            }
            // 修改
            if (SysPramType.UPDATE.getMsg().equals(crmProductCategoryVO.getOpt())) {
                crmProductCategory = crmProductCategoryMapper.selectByPrimaryKey(crmProductCategoryVO.getCid());
                BeanCopyUtil.copy(crmProductCategoryVO, crmProductCategory);
                crmProductCategory.setUpdateDt(new Date());
                crmProductCategory.setCreateBy(null);
                crmProductCategory.setCreateByid(null);
                crmProductCategoryMapper.updateByPrimaryKeySelective(crmProductCategory);

            }
            // 添加
            if (SysPramType.INSERT.getMsg().equals(crmProductCategoryVO.getOpt())) {
                crmProductCategory = new CrmProductCategory();
                crmProductCategory.setCategoryName(crmProductCategoryVO.getCategoryName());
                crmProductCategory = crmProductCategoryMapper.getOne(crmProductCategory);

                if (null != crmProductCategory) {
                    serviceResponse.setRetCode("0902001",new Object[]{crmProductCategoryVO.getCategoryName()});
                    return serviceResponse;
                } else {
                    crmProductCategory = new CrmProductCategory();
                    BeanCopyUtil.copy(crmProductCategoryVO, crmProductCategory);
                    crmProductCategory.setCreateDt(new Date());
                    crmProductCategory.setUpdateBy(null);
                    crmProductCategory.setUpdateByid(null);
                    crmProductCategoryMapper.insertSelective(crmProductCategory);
                }
            }
        }

        flag = true;
        serviceResponse.setRetContent(flag);
        return serviceResponse;
    }

    @Override
    public List<CrmProductCategoryVO> getList(List<String> codes) {
        CrmProductCategory crmProductCategory = new CrmProductCategory();
        crmProductCategory.setStatus(ProductCategoryStatus.SUBMIT.getCode());
        crmProductCategory.setFactoryCodes(codes);
        List<CrmProductCategory> list = crmProductCategoryMapper.getList(crmProductCategory);

        List<CrmProductCategoryVO> listvo = BeanCopyUtil.copyList(list, CrmProductCategoryVO.class);
        return listvo;
    }

    @Override
    public List<CrmProductCategoryVO> getIntroductList(CrmProductCategoryVO crmProductCategoryVO) {
        CrmProductCategory crmProductCategory = new CrmProductCategory();
        BeanCopyUtil.copy(crmProductCategoryVO, crmProductCategory);
        crmProductCategory.setStatus(ProductCategoryStatus.SUBMIT.getCode());
        List<CrmProductCategory> list = crmProductCategoryMapper.getIntroductList(crmProductCategory);

        List<CrmProductCategoryVO> listvo = BeanCopyUtil.copyList(list, CrmProductCategoryVO.class);
        return listvo;
    }
}
