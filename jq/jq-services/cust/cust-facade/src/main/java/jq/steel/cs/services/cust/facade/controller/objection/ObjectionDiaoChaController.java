package jq.steel.cs.services.cust.facade.controller.objection;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.ObjectionDiaoChaVO;
import jq.steel.cs.services.cust.facade.service.objection.ObjectionDiaoChaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/objectionDiaoCha")
public class ObjectionDiaoChaController {

    private static Logger logger = LoggerFactory.getLogger(ObjectionDiaoChaController.class);

    @Autowired
    private  ObjectionDiaoChaService objectionDiaoChaService;

    /**
     *  条件分页查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findByPage",method = RequestMethod.POST)
    public ServiceResponse<PageDTO<ObjectionDiaoChaVO>> findByPage(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<ObjectionDiaoChaVO>> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionDiaoChaVO objectionDiaoChaVO = jsonRequest.getReqBody();
            jsonRequest.getReqBody().setAcctId(AssertContext.getAcctId());

            PageDTO<ObjectionDiaoChaVO> pageDTO = objectionDiaoChaService.findByPage(objectionDiaoChaVO);
            serviceResponse.setRetContent(pageDTO);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }


    /**
     *  异议调查外部调查内部调查回显数据
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findDetails",method = RequestMethod.POST)
    public ServiceResponse<ObjectionDiaoChaVO> findDetails(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest){
        logger.info("参数", JsonUtil.toJson(jsonRequest));
        ServiceResponse<ObjectionDiaoChaVO> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionDiaoChaVO objectionDiaoChaVO = jsonRequest.getReqBody();
            ObjectionDiaoChaVO objectionDiaoChaVO1 = objectionDiaoChaService.findDetails(objectionDiaoChaVO);
           /* if (objectionDiaoChaVO1.getExplain()!=null){
                serviceResponse.setRetCode("1");
                serviceResponse.setRetMessage(objectionTiBaoVO1.getExplain());
            }*/
            serviceResponse.setRetContent(objectionDiaoChaVO1);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    /**
     *  内外部调查报告（保存，跟踪，提交）异议处理确认书（通过 ，驳回）
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ServiceResponse<Integer> update(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest){
        logger.info("参数", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionDiaoChaVO objectionDiaoChaVO = jsonRequest.getReqBody();
            Integer integer= objectionDiaoChaService.update(objectionDiaoChaVO);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }


    /**
     *  内部调查报告（保存，提交）
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/updateInside",method = RequestMethod.POST)
    public ServiceResponse<Integer> updateInside(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionDiaoChaVO objectionDiaoChaVO = jsonRequest.getReqBody();
            Integer integer = objectionDiaoChaService.updateInside(objectionDiaoChaVO);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }


    /**
     *  异议调查导出
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/export",method = RequestMethod.POST)
    public ServiceResponse<List<ObjectionDiaoChaVO>> export(@RequestBody JsonRequest<List<String>> jsonRequest){
        ServiceResponse<List<ObjectionDiaoChaVO>> serviceResponse = new ServiceResponse<>();
        try {
            List<String> objectionDiaoChaVOS = jsonRequest.getReqBody();
            List<ObjectionDiaoChaVO> integer = objectionDiaoChaService.export(objectionDiaoChaVOS);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    /**
     *  异议调查打印受理单
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/print",method = RequestMethod.POST)
    public ServiceResponse print(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest){
        logger.info("参数 = {}",JsonUtil.toJson(jsonRequest));
        ServiceResponse<ObjectionDiaoChaVO> serviceResponse = new ServiceResponse<>();
        ObjectionDiaoChaVO reqBody = jsonRequest.getReqBody();
        try{
            ObjectionDiaoChaVO downUrl = objectionDiaoChaService.print(reqBody);
            serviceResponse.setRetContent(downUrl);
            return serviceResponse;
        }catch (Exception e){
            logger.error("保存 参数 失败 error = {}",e);

            throw new BusinessException("0000001");
        }
    }

    /**
     *  调查报告下载pdf
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/downPdf",method = RequestMethod.POST)
    public ServiceResponse<ObjectionDiaoChaVO> downPdf(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest){
        logger.info("参数 = {}",JsonUtil.toJson(jsonRequest));
        ServiceResponse<ObjectionDiaoChaVO> serviceResponse = new ServiceResponse<>();
        ObjectionDiaoChaVO reqBody = jsonRequest.getReqBody();
        try{
            ObjectionDiaoChaVO downUrl = objectionDiaoChaService.print(reqBody);
            serviceResponse.setRetContent(downUrl);
            return serviceResponse;
        }catch (Exception e){
            logger.error("保存 参数 失败 error = {}",e);

            throw new BusinessException("0000001");
        }
    }

    /**
     *  调查报告驳回
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/reject",method = RequestMethod.POST)
    public ServiceResponse<Integer> reject(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionDiaoChaVO objectionDiaoChaVO = jsonRequest.getReqBody();
            Integer integer = objectionDiaoChaService.reject(objectionDiaoChaVO);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    /**
     *  内部调查/外部调查开始结束状态修改
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/updateState",method = RequestMethod.POST)
    public ServiceResponse<Integer> updateState(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionDiaoChaVO objectionDiaoChaVO = jsonRequest.getReqBody();
            Integer integer = objectionDiaoChaService.updateState(objectionDiaoChaVO);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }
}
