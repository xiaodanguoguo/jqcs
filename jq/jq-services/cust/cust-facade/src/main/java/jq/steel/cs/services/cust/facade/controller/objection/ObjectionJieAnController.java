package jq.steel.cs.services.cust.facade.controller.objection;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.ObjectionChuLiVO;
import jq.steel.cs.services.cust.api.vo.ObjectionJieAnVO;
import jq.steel.cs.services.cust.facade.service.objection.ObjectionJieAnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/objectionJieAn")
public class ObjectionJieAnController {

    private static Logger logger = LoggerFactory.getLogger(ObjectionJieAnController.class);

    @Autowired
    private ObjectionJieAnService objectionJieAnService;

    /**
     *  上传协议书文件
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public ServiceResponse<Integer> upload(@RequestBody JsonRequest<ObjectionJieAnVO> jsonRequest){
        logger.info("参数", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionJieAnVO objectionJieAnVO = jsonRequest.getReqBody();
            Integer integer= objectionJieAnService.upload(objectionJieAnVO);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    /**
     *  过期原因
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/expiren",method = RequestMethod.POST)
    public ServiceResponse<Integer> expiren(@RequestBody JsonRequest<ObjectionJieAnVO> jsonRequest){
        logger.info("参数", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionJieAnVO objectionJieAnVO = jsonRequest.getReqBody();
            Integer integer= objectionJieAnService.expiren(objectionJieAnVO);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    /**
     *  异议结案撤销
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/revoke",method = RequestMethod.POST)
    public ServiceResponse<Integer> revoke(@RequestBody JsonRequest<ObjectionJieAnVO> jsonRequest){
        logger.info("参数", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionJieAnVO objectionJieAnVO = jsonRequest.getReqBody();
            Integer integer= objectionJieAnService.revoke(objectionJieAnVO);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    /**
     *  打印通知单
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/print",method = RequestMethod.POST)
    public ServiceResponse<ObjectionJieAnVO> print(@RequestBody JsonRequest<ObjectionJieAnVO> jsonRequest){
        logger.info("参数 = {}",JsonUtil.toJson(jsonRequest));
        ServiceResponse<ObjectionJieAnVO> serviceResponse = new ServiceResponse<>();
        ObjectionJieAnVO reqBody = jsonRequest.getReqBody();
        try{
            ObjectionJieAnVO downUrl = objectionJieAnService.print(reqBody);
            serviceResponse.setRetContent(downUrl);
            return serviceResponse;
        }catch (Exception e){
            logger.error("保存 参数 失败 error = {}",e);

            throw new BusinessException("0000001");
        }
    }


    /**
     * 查看文件
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/look",method = RequestMethod.POST)
    public ServiceResponse<ObjectionJieAnVO> look(@RequestBody JsonRequest<ObjectionJieAnVO> jsonRequest){
        logger.info("参数 = {}",JsonUtil.toJson(jsonRequest));
        ServiceResponse<ObjectionJieAnVO> serviceResponse = new ServiceResponse<>();
        ObjectionJieAnVO reqBody = jsonRequest.getReqBody();
        try{
            ObjectionJieAnVO downUrl = objectionJieAnService.look(reqBody);
            serviceResponse.setRetContent(downUrl);
            return serviceResponse;
        }catch (Exception e){
            logger.error("保存 参数 失败 error = {}",e);

            throw new BusinessException("0000001");
        }
    }
}
