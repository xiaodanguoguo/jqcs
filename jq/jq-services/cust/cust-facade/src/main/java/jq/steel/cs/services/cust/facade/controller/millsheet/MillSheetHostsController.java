package jq.steel.cs.services.cust.facade.controller.millsheet;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.services.cust.facade.service.millsheet.MillSheetHostsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/millsheet")
public class MillSheetHostsController {
    private static Logger logger = LoggerFactory.getLogger(MillSheetHostsController.class);

    @Autowired
    private MillSheetHostsService millSheetHostsService;
    //条件分页查询
    @RequestMapping(value = "/findMillSheetByPage",method = RequestMethod.POST)
    public ServiceResponse<PageDTO<MillSheetHostsVO>> findMillSheetByPage(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest){
        logger.info("分页 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<MillSheetHostsVO>> serviceResponse = new ServiceResponse<>();
        try {
            MillSheetHostsVO millSheetHostsVO = jsonRequest.getReqBody();
            PageDTO<MillSheetHostsVO> pageDTO = millSheetHostsService.findMillSheetByPage(millSheetHostsVO);
            serviceResponse.setRetContent(pageDTO);
        }catch (BusinessException e){
                logger.error("获取分页出错",e);
                serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    //查询文件地址
    @RequestMapping(value = "/preview",method = RequestMethod.POST)
    public ServiceResponse<List<MillSheetHostsVO>> findUrl(@RequestBody JsonRequest<List<MillSheetHostsVO>> jsonRequest){
        logger.info("参数 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<MillSheetHostsVO>> serviceResponse = new ServiceResponse<>();
        try {
            List<MillSheetHostsVO> millSheetHostsVO = jsonRequest.getReqBody();
            List<MillSheetHostsVO> list = millSheetHostsService.findUrl(millSheetHostsVO);
            serviceResponse.setRetContent(list);
        }catch (BusinessException e){
            logger.error("出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    //查询文件地址
    @RequestMapping(value = "/rollbackQuery",method = RequestMethod.POST)
    public ServiceResponse<MillSheetHostsVO> rollbackQuery(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest){
        logger.info("参数 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<MillSheetHostsVO> serviceResponse = new ServiceResponse<>();
        try {
            MillSheetHostsVO millSheetHostsVO = jsonRequest.getReqBody();
            MillSheetHostsVO list = millSheetHostsService.rollbackQuery(millSheetHostsVO);
            serviceResponse.setRetContent(list);
        }catch (BusinessException e){
            logger.error("出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    /**
     * 下载返回地址
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/downFile", method = RequestMethod.POST)
    public ServiceResponse<List<MillSheetHostsVO>> downFile(@RequestBody JsonRequest<List<String>> jsonRequest){
        logger.info("保存 参数 = {}",JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<MillSheetHostsVO>> serviceResponse = new ServiceResponse<>();
        List<String> reqBody = jsonRequest.getReqBody();
        try{
            List<MillSheetHostsVO> downUrl = millSheetHostsService.findDownUrl(reqBody);
            serviceResponse.setRetContent(downUrl);
            return serviceResponse;
        }catch (Exception e){
            logger.error("保存 参数 失败 error = {}",e);

            throw new BusinessException("500");
        }
    }


    /**
     * 诉赔界面校验质证书输入正确与否
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/findIsTrue")
    public ServiceResponse<MillSheetHostsVO> findIsTrue(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest){
        logger.info("参数 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<MillSheetHostsVO> serviceResponse = new ServiceResponse<>();
        try {
            MillSheetHostsVO millSheetHostsVO = jsonRequest.getReqBody();
            MillSheetHostsVO list = millSheetHostsService.findIsTrue(millSheetHostsVO);
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
