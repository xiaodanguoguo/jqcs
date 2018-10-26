package jq.steel.cs.services.cust.facade.controller.millsheet;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.services.cust.api.vo.MillSheetNeedsVO;
import jq.steel.cs.services.cust.facade.service.millsheet.MillSheetNeedsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/millsheetNeed")
public class MillSheetNeedsController {

    private static Logger logger = LoggerFactory.getLogger(MillSheetNeedsController.class);


    @Autowired
    private MillSheetNeedsService millSheetNeedsService;

    //查询特殊需求文件地址
    @RequestMapping(value = "/findByType",method = RequestMethod.POST)
    public ServiceResponse<List<MillSheetNeedsVO>> findByType(@RequestBody JsonRequest<MillSheetNeedsVO> jsonRequest){
        logger.info("分页 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<MillSheetNeedsVO>> serviceResponse = new ServiceResponse<>();
        try {
            MillSheetNeedsVO millSheetNeedsVO = jsonRequest.getReqBody();
            List<MillSheetNeedsVO> millSheetNeedsVOS = millSheetNeedsService.findByType(millSheetNeedsVO);
            serviceResponse.setRetContent(millSheetNeedsVOS);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }
}
