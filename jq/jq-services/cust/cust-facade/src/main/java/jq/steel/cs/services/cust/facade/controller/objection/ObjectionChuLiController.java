package jq.steel.cs.services.cust.facade.controller.objection;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.ObjectionChuLiVO;
import jq.steel.cs.services.cust.api.vo.ObjectionDiaoChaVO;
import jq.steel.cs.services.cust.facade.service.objection.ObjectionChuLiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/objectionChuLi")
public class ObjectionChuLiController {


    private static Logger logger = LoggerFactory.getLogger(ObjectionChuLiController.class);

    @Autowired
    private ObjectionChuLiService objectionChuLiService;

    /**
     *  条件分页查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findByPage",method = RequestMethod.POST)
    public ServiceResponse<PageDTO<ObjectionChuLiVO>> findByPage(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<ObjectionChuLiVO>> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionChuLiVO objectionChuLiVO = jsonRequest.getReqBody();
            PageDTO<ObjectionChuLiVO> pageDTO = objectionChuLiService.findByPage(objectionChuLiVO);
            serviceResponse.setRetContent(pageDTO);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }


    /**
     *  公共信息查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    public ServiceResponse<ObjectionChuLiVO> findAll(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest){
        logger.info("参数", JsonUtil.toJson(jsonRequest));
        ServiceResponse<ObjectionChuLiVO> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionChuLiVO objectionChuLiVO = jsonRequest.getReqBody();
            ObjectionChuLiVO objectionChuLiVO1 = objectionChuLiService.findAll(objectionChuLiVO);
            serviceResponse.setRetContent(objectionChuLiVO1);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    /**
     *  协议书保存/提交/审核
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/agreementUpdate",method = RequestMethod.POST)
    public ServiceResponse<Integer> agreementUpdate(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest){
        logger.info("参数", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionChuLiVO objectionChuLiVO = jsonRequest.getReqBody();
            Integer integer= objectionChuLiService.agreementUpdate(objectionChuLiVO);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }


    /**
     *  异议处理导出
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/export",method = RequestMethod.POST)
    public ServiceResponse<List<ObjectionChuLiVO>> export(@RequestBody JsonRequest<List<String>> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<ObjectionChuLiVO>> serviceResponse = new ServiceResponse<>();
        try {
            List<String> liVOS = jsonRequest.getReqBody();
            List<ObjectionChuLiVO> integer = objectionChuLiService.export(liVOS);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    /**
     * 打印/预览 实时生成pdf并且返回url地址
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/preview",method = RequestMethod.POST)
    public ServiceResponse preview(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest){
        logger.info("参数 = {}",JsonUtil.toJson(jsonRequest));
        ServiceResponse<ObjectionChuLiVO> serviceResponse = new ServiceResponse<>();
        ObjectionChuLiVO reqBody = jsonRequest.getReqBody();
        try{
            ObjectionChuLiVO downUrl = objectionChuLiService.preview(reqBody);
            serviceResponse.setRetContent(downUrl);
            return serviceResponse;
        }catch (Exception e){
            logger.error("保存 参数 失败 error = {}",e);

            throw new BusinessException("0000001");
        }
    }

    /**
     *  下载 返回文件流
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/download",method = RequestMethod.POST)
    public ServiceResponse<ObjectionChuLiVO> download(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest){
        logger.info("参数 = {}",JsonUtil.toJson(jsonRequest));
        ServiceResponse<ObjectionChuLiVO> serviceResponse = new ServiceResponse<>();
        ObjectionChuLiVO reqBody = jsonRequest.getReqBody();
        try{
            ObjectionChuLiVO downUrl = objectionChuLiService.download(reqBody);
            serviceResponse.setRetContent(downUrl);
            return serviceResponse;
        }catch (Exception e){
            logger.error("保存 参数 失败 error = {}",e);

            throw new BusinessException("0000001");
        }
    }

    /**
     *  强制结案
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/compulsorySettlement",method = RequestMethod.POST)
    public ServiceResponse<Integer> compulsorySettlement(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionChuLiVO reqBody = jsonRequest.getReqBody();
            Integer integer = objectionChuLiService.compulsorySettlement(reqBody);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }
}
