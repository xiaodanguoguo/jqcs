package jq.steel.cs.services.cust.facade.controller.millsheet;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.MillSheetDownloadVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.services.cust.facade.model.MillSheetHosts;
import jq.steel.cs.services.cust.facade.service.millsheet.MillSheetHostsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    //条件分页查询（酒钢）
    @RequestMapping(value = "/findMillSheetByPage1",method = RequestMethod.POST)
    public ServiceResponse<PageDTO<MillSheetHostsVO>> findMillSheetByPage1(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest){
        logger.info("分页 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<MillSheetHostsVO>> serviceResponse = new ServiceResponse<>();
        try {
            MillSheetHostsVO millSheetHostsVO = jsonRequest.getReqBody();
            PageDTO<MillSheetHostsVO> pageDTO = millSheetHostsService.findMillSheetByPage1(millSheetHostsVO);
            serviceResponse.setRetContent(pageDTO);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    //查询文件地址
    @RequestMapping(value = "/preview",method = RequestMethod.POST)
    public ServiceResponse<List<MillSheetHostsVO>> findUrl(@RequestBody JsonRequest<List<MillSheetHostsVO>> jsonRequest,HttpServletRequest request){
        logger.info("参数 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<MillSheetHostsVO>> serviceResponse = new ServiceResponse<>();
        try {
            List<MillSheetHostsVO> millSheetHostsVO = jsonRequest.getReqBody();
            List<MillSheetHostsVO> list = millSheetHostsService.findUrl(millSheetHostsVO,request);
            serviceResponse.setRetContent(list);
        }catch (BusinessException e){
            logger.error("出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    //查询文件地址
    @RequestMapping(value = "/preview1",method = RequestMethod.POST)
    public ServiceResponse<List<MillSheetHostsVO>> findUrl1(@RequestBody JsonRequest<List<String>> jsonRequest,HttpServletRequest request){
        logger.info("参数 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<MillSheetHostsVO>> serviceResponse = new ServiceResponse<>();
        List<String> reqBody = jsonRequest.getReqBody();
        try {
            List<MillSheetHostsVO> list = millSheetHostsService.findUrl1(reqBody,request);
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


    /**
     * 查询条件校验钢卷编号是否正确
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/checkCoil")
    public ServiceResponse<MillSheetHostsVO> checkCoil(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest){
        logger.info("参数 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<MillSheetHostsVO> serviceResponse = new ServiceResponse<>();
        try {
            MillSheetHostsVO millSheetHostsVO = jsonRequest.getReqBody();
            MillSheetHostsVO list = millSheetHostsService.checkCoil(millSheetHostsVO);
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



    //返回app端质证书下载路径
    @RequestMapping(value = "/downloadForApp" , method = RequestMethod.POST)
    public ServiceResponse<MillSheetHostsVO> downloadForApp(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest){
        MillSheetHostsVO vo = millSheetHostsService.getUrlForApp(jsonRequest);
        ServiceResponse<MillSheetHostsVO> srp = new ServiceResponse<>();
        srp.setRetContent(vo);
        return srp;
    }

    /**
     * 打印次数/下载次数+1
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/updateNumber",method = RequestMethod.POST)
    public ServiceResponse<Integer>  updateNumber(@RequestBody JsonRequest<List<MillSheetHostsVO>> jsonRequest,HttpServletRequest request){
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            Integer integer = millSheetHostsService.updateNumber(jsonRequest.getReqBody(),request);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }



    /**
     * 拆分撤销
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/revoke",method = RequestMethod.POST)
    public ServiceResponse<Integer>  revoke(@RequestBody JsonRequest<List<MillSheetHostsVO>> jsonRequest,HttpServletRequest request){
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            Integer integer = millSheetHostsService.revoke(jsonRequest.getReqBody(),request);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

}
