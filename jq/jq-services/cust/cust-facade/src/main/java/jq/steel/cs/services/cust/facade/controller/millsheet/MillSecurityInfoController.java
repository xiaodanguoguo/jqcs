package jq.steel.cs.services.cust.facade.controller.millsheet;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.MillSecurityInfoVO;
import jq.steel.cs.services.cust.facade.service.millsheet.MillSecurityInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/millsheetcheck")
public class MillSecurityInfoController {

    private static Logger logger = LoggerFactory.getLogger(MillSheetHostsController.class);

    @Autowired
    private MillSecurityInfoService millSecurityInfoService;


    //防伪码验真
    @RequestMapping(value = "/fangWeiMa",method = RequestMethod.POST)
    public ServiceResponse<MillSecurityInfoVO> fangWeiMa(@RequestBody JsonRequest<MillSecurityInfoVO> jsonRequest,HttpServletRequest request){
        logger.info("防伪码", JsonUtil.toJson(jsonRequest));
        ServiceResponse<MillSecurityInfoVO> serviceResponse = new ServiceResponse<>();
        try {
            MillSecurityInfoVO millSecurityInfoVO = jsonRequest.getReqBody();
            MillSecurityInfoVO millSecurityInfoVO1 = millSecurityInfoService.fangWeiMa(millSecurityInfoVO,request);
            serviceResponse.setRetContent(millSecurityInfoVO1);
        }catch (BusinessException e){
            logger.error("防伪码验真出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    //附件验真
    //防伪码验真
    @RequestMapping(value = "/fuJian",method = RequestMethod.POST)
    public ServiceResponse<MillSecurityInfoVO> fuJian(@RequestBody JsonRequest<MillSecurityInfoVO> jsonRequest,HttpServletRequest request){
        logger.info("文件地址", JsonUtil.toJson(jsonRequest));
        ServiceResponse<MillSecurityInfoVO> serviceResponse = new ServiceResponse<>();
        try {
            MillSecurityInfoVO MillSecurityInfoVO = jsonRequest.getReqBody();
            MillSecurityInfoVO list = millSecurityInfoService.fuJian(MillSecurityInfoVO,request);
            serviceResponse.setRetContent(list);
        }catch (BusinessException e){
            logger.error("防伪码验真出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }
}
