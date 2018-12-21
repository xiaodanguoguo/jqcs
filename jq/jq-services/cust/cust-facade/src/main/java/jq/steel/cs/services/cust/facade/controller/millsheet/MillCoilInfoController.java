package jq.steel.cs.services.cust.facade.controller.millsheet;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.facade.service.millsheet.MillCoilInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coilinfo")
public class MillCoilInfoController {
    private static Logger logger = LoggerFactory.getLogger(MillCoilInfoController.class);

    @Autowired
    private MillCoilInfoService millCoilInfoService;
    //拆分申请（强制拆分）数据查询
    @RequestMapping(value = "/splitFind",method = RequestMethod.POST)
    public ServiceResponse<PageDTO<MillCoilInfoVO>> splitFind(@RequestBody JsonRequest<MillCoilInfoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<MillCoilInfoVO>> serviceResponse = new ServiceResponse<>();
        try {
            MillCoilInfoVO millCoilInfoVO = jsonRequest.getReqBody();
            PageDTO<MillCoilInfoVO> pageDTO = millCoilInfoService.splitFind(millCoilInfoVO);
            serviceResponse.setRetContent(pageDTO);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }


    /**
     * 诉赔界面校验批板卷号输入正确与否
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/findIsTrue")
    public ServiceResponse<MillCoilInfoVO> findIsTrue(@RequestBody JsonRequest<MillCoilInfoVO> jsonRequest){
        logger.info("参数 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<MillCoilInfoVO> serviceResponse = new ServiceResponse<>();
        try {
            MillCoilInfoVO millCoilInfoVO = jsonRequest.getReqBody();
            MillCoilInfoVO list = millCoilInfoService.findIsTrue(millCoilInfoVO);
            if (list.getTrue()){
                serviceResponse.setRetContent(list);
            }else {
                serviceResponse.setRetContent(list);
                serviceResponse.setRetCode("00");
                serviceResponse.setRetMessage(list.getCheckInstructions());
            }

        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }
}
