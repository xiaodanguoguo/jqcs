package jq.steel.cs.services.cust.facade.controller.app;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.facade.service.millsheet.impl.MillCoilInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName:      AppMillCoilInfoController
 * @Description:    根据当前用户查询出其拥有的所有钢卷;钢卷下的明细
 * @Author:         lujiawei
 * @CreateDate:     2018/9/15 12:20
 */
@RestController
@RequestMapping("/app/millCoil")
public class AppMillCoilInfoController {

    private static Logger logger = LoggerFactory.getLogger(AppMillCoilInfoController.class);

    @Autowired
    private MillCoilInfoServiceImpl millCoilInfoService;

    //-------------------------------根据当前用户查询出其拥有的所有钢卷,并进行分页-----------------------------------
    @RequestMapping(value = "/getCoilsByCurrentUser", method = RequestMethod.POST)
    public ServiceResponse<PageDTO<MillCoilInfoVO>> CoilsByCurrentUser(@RequestBody JsonRequest<MillCoilInfoVO> jsonRequest) {
        ServiceResponse<PageDTO<MillCoilInfoVO>> serviceResponse = new ServiceResponse<>();

        if (jsonRequest == null
                && jsonRequest.getReqBody() == null
                && jsonRequest.getReqBody().getPageNum() <= 0
                && jsonRequest.getReqBody().getPageSize() <= 0) {
            serviceResponse.setException(new BusinessException("非法参数"));
            return serviceResponse;
        }
        logger.info("保存 参数 = {}", JsonUtil.toJson(jsonRequest));
        //获取当前用户的orgCode
        String orgCode = AssertContext.getOrgCode();

        MillCoilInfoVO reqBody = jsonRequest.getReqBody();
        try {
            PageDTO<MillCoilInfoVO> coilInfoVOList = millCoilInfoService.getCoilsByCurrentUser(orgCode, reqBody);

            serviceResponse.setRetContent(coilInfoVOList);
        } catch (Exception e) {
            logger.error("获取钢卷分页出错", e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return serviceResponse;
    }


    //--------------------------------通过钢卷号获取钢卷明细<物理/化学数据>--------------------------------------
    @RequestMapping(value = "/getCoilDetailByCoil", method = RequestMethod.POST)
    public ServiceResponse<List<CrmMillCoilInfoVO>> getCoilDetail(@RequestBody JsonRequest<CrmMillCoilInfoVO> jsonRequest) {
        ServiceResponse<List<CrmMillCoilInfoVO>> serviceResponse = new ServiceResponse<>();

        if (jsonRequest == null
                && jsonRequest.getReqBody() == null
                && jsonRequest.getReqBody().getZcharg() == null) {
            serviceResponse.setException(new BusinessException("非法参数"));
            return serviceResponse;
        }
        logger.info("保存 参数 = {}", JsonUtil.toJson(jsonRequest));

        CrmMillCoilInfoVO reqBody = jsonRequest.getReqBody();
        try {
            List<CrmMillCoilInfoVO> coilDetail = millCoilInfoService.getCoilDetail(reqBody);
            serviceResponse.setRetContent(coilDetail);
        } catch (Exception e) {
            logger.error("获取钢卷物理、化学数据", e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return serviceResponse;
    }

}