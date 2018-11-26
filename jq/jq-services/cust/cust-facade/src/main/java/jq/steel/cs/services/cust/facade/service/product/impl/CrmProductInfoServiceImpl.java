package jq.steel.cs.services.cust.facade.service.product.impl;

import com.ebase.core.AssertContext;
import com.ebase.core.cache.CacheService;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.core.service.ServiceResponse;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmProductInfoVO;
import jq.steel.cs.services.cust.facade.common.ProductInfoStatus;
import jq.steel.cs.services.cust.facade.dao.CrmProductInfoMapper;
import jq.steel.cs.services.cust.facade.model.CrmProductInfo;
import jq.steel.cs.services.cust.facade.service.product.CrmProductInfoService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: CrmProductInfoServiceImpl
 * @Description:
 * @Author: lirunze
 * @CreateDate: 2018/8/25 14:33
 */
@Service("crmProductInfoService")
public class CrmProductInfoServiceImpl implements CrmProductInfoService {

    private final static Logger logger = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private CrmProductInfoMapper crmProductInfoMapper;

    @Autowired
    private CacheService cacheService;

    @Override
    public PageDTO<CrmProductInfoVO> getPage(CrmProductInfoVO crmProductInfoVO) {
        try {
            CrmProductInfo crmProductInfo = new CrmProductInfo();
            BeanCopyUtil.copy(crmProductInfoVO, crmProductInfo);

            PageDTOUtil.startPage(crmProductInfoVO);

            List<CrmProductInfo> list = crmProductInfoMapper.getList(crmProductInfo);
            PageDTO<CrmProductInfoVO> page = PageDTOUtil.transform(list, CrmProductInfoVO.class);

            return page;
        } finally {
            PageDTOUtil.endPage();
        }
    }

    @Override
    public CrmProductInfoVO getDetail(CrmProductInfoVO crmProductInfoVO) {
        CrmProductInfo crmProductInfo = new CrmProductInfo();
        BeanCopyUtil.copy(crmProductInfoVO, crmProductInfo);
        crmProductInfo = crmProductInfoMapper.selectByPrimaryKey(crmProductInfoVO.getPid());
        BeanCopyUtil.copy(crmProductInfo, crmProductInfoVO);
        return crmProductInfoVO;
    }

    @Override
    public ServiceResponse<Boolean> deletePruduct(CrmProductInfoVO crmProductInfoVO) {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        Boolean flag = false;
        List<Long> pids = crmProductInfoVO.getPids();

        for (Long pid : pids) {
            CrmProductInfo crmProductInfo = crmProductInfoMapper.selectByPrimaryKey(pid);
            if (ProductInfoStatus.ISSUE.getCode().equals(crmProductInfo.getStatus())) {
//                serviceResponse.setRetCode("0903001",
//                        new Object[]{crmProductInfo.getProductName() + "_" + crmProductInfo.getDesignation() + "_" + crmProductInfo.getStandard()});
//                return serviceResponse;
            } else {
                crmProductInfoMapper.deleteByPrimaryKey(pid);
            }
        }

        flag = true;
        serviceResponse.setRetContent(flag);

        return serviceResponse;
    }

    @Override
    public ServiceResponse<Boolean> issuePruduct(CrmProductInfoVO crmProductInfoVO) {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        Boolean flag = false;
        List<Long> pids = crmProductInfoVO.getPids();

        for (Long pid : pids) {
            CrmProductInfo crmProductInfo = new CrmProductInfo();
            crmProductInfo.setPid(pid);
            crmProductInfo.setStatus(ProductInfoStatus.ISSUE.getCode());
            crmProductInfo.setUpdateDt(new Date());
//            crmProductInfo.setUpdateByid(Long.valueOf(AssertContext.getAcctId()));
            crmProductInfo.setUpdateBy(AssertContext.getAcctName());
            crmProductInfoMapper.updateByPrimaryKeySelective(crmProductInfo);
        }

        flag = true;
        serviceResponse.setRetContent(flag);

        return serviceResponse;
    }

    @Override
    public ServiceResponse<Boolean> insertPruduct(CrmProductInfoVO crmProductInfoVO) {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        Boolean flag = false;


        CrmProductInfo crmProductInfo = new CrmProductInfo();
        BeanCopyUtil.copy(crmProductInfoVO, crmProductInfo);

        CrmProductInfo productInfo = crmProductInfoMapper.getOne(crmProductInfo);

        if (null != productInfo) {
            serviceResponse.setRetCode("0903002",
                    new Object[]{crmProductInfo.getCategoryName() + "_" +crmProductInfo.getProductName() + "_" + crmProductInfo.getDesignation() +
                            "_" + crmProductInfo.getStandard() + "_" +crmProductInfo.getProductArea()});
            return serviceResponse;
        }

        crmProductInfo.setThumbnail(JsonUtil.toJson(crmProductInfoVO.getThumbnailList()));
        Long maxSortNumber = crmProductInfoMapper.getMaxSortNumber();
        if (maxSortNumber == null) {
            maxSortNumber = 1L;
        }
        crmProductInfo.setSortNumber(maxSortNumber);
        crmProductInfo.setCreateDt(new Date());
//        crmProductInfo.setCreateByid(Long.valueOf(AssertContext.getAcctId()));
//        crmProductInfo.setCreateBy(AssertContext.getAcctName());
        crmProductInfo.setStatus(ProductInfoStatus.NEW_CREATE.getCode());
        crmProductInfoMapper.insertSelective(crmProductInfo);

        flag = true;
        serviceResponse.setRetContent(flag);

        return serviceResponse;
    }

    @Override
    public ServiceResponse<Boolean> updatePruduct(CrmProductInfoVO crmProductInfoVO) {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        Boolean flag = false;


        CrmProductInfo crmProductInfo = new CrmProductInfo();
        BeanCopyUtil.copy(crmProductInfoVO, crmProductInfo);

        CrmProductInfo productInfo = crmProductInfoMapper.getOne(crmProductInfo);

        if (null != productInfo && !productInfo.getPid().equals(crmProductInfoVO.getPid())) {
            serviceResponse.setRetCode("0903002",
                    new Object[]{crmProductInfo.getCategoryName() + "_" +crmProductInfo.getProductName() + "_" + crmProductInfo.getDesignation() +
                            "_" + crmProductInfo.getStandard() + "_" +crmProductInfo.getProductArea()});
            return serviceResponse;
        }

        crmProductInfo.setThumbnail(JsonUtil.toJson(crmProductInfoVO.getThumbnailList()));
        crmProductInfo.setUpdateDt(new Date());
//        crmProductInfo.setUpdateByid(Long.valueOf(AssertContext.getAcctId()));
//        crmProductInfo.setUpdateBy(AssertContext.getAcctName());
        crmProductInfoMapper.updateByPrimaryKeySelective(crmProductInfo);

        flag = true;
        serviceResponse.setRetContent(flag);

        return serviceResponse;
    }

    @Override
    public ServiceResponse<Boolean> movePruduct(CrmProductInfoVO crmProductInfoVO) {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        Boolean flag = false;
        List<Long> pids = crmProductInfoVO.getPids();

        CrmProductInfo crmProductInfo1 = crmProductInfoMapper.selectByPrimaryKey(crmProductInfoVO.getPids().get(0));
        CrmProductInfo crmProductInfo2 = crmProductInfoMapper.selectByPrimaryKey(crmProductInfoVO.getPids().get(1));

        Long median = crmProductInfo1.getSortNumber();

        crmProductInfo1.setSortNumber(crmProductInfo2.getSortNumber());
        crmProductInfo1.setUpdateDt(new Date());
//        crmProductInfo1.setUpdateByid(Long.valueOf(AssertContext.getAcctId()));
//        crmProductInfo1.setUpdateBy(AssertContext.getAcctName());
        crmProductInfoMapper.updateByPrimaryKeySelective(crmProductInfo1);

        crmProductInfo2.setSortNumber(median);
        crmProductInfo2.setUpdateDt(new Date());
//        crmProductInfo2.setUpdateByid(Long.valueOf(AssertContext.getAcctId()));
//        crmProductInfo2.setUpdateBy(AssertContext.getAcctName());
        crmProductInfoMapper.updateByPrimaryKeySelective(crmProductInfo2);

        flag = true;
        serviceResponse.setRetContent(flag);

        return serviceResponse;
    }

    @Override
    public PageDTO<CrmProductInfoVO> getIntroductPage(CrmProductInfoVO crmProductInfoVO) {
        try {
            CrmProductInfo crmProductInfo = new CrmProductInfo();
            BeanCopyUtil.copy(crmProductInfoVO, crmProductInfo);
            crmProductInfoVO.setStatus(ProductInfoStatus.ISSUE.getCode());
            PageDTOUtil.startPage(crmProductInfoVO);

            List<CrmProductInfo> list = crmProductInfoMapper.getIntroductList(crmProductInfo);
            PageDTO<CrmProductInfoVO> page = PageDTOUtil.transform(list, CrmProductInfoVO.class);

            return page;
        } finally {
            PageDTOUtil.endPage();
        }
    }


}
