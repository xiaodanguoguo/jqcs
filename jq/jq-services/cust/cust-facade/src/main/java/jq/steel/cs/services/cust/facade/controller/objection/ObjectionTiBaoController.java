package jq.steel.cs.services.cust.facade.controller.objection;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoCountVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;
import jq.steel.cs.services.cust.facade.model.CrmClaimApply;
import jq.steel.cs.services.cust.facade.service.objection.ObjectionTiBaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/objectionTiBao")
public class ObjectionTiBaoController {

    private static Logger logger = LoggerFactory.getLogger(ObjectionTiBaoController.class);


    @Autowired
    private ObjectionTiBaoService objectionTiBaoService;

    /**
     * 条件分页查询
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    public ServiceResponse<PageDTO<ObjectionTiBaoVO>> findByPage(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<ObjectionTiBaoVO>> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionTiBaoVO objectionTiBaoVO = jsonRequest.getReqBody();
            PageDTO<ObjectionTiBaoVO> pageDTO = objectionTiBaoService.findByPage(objectionTiBaoVO);
            serviceResponse.setRetContent(pageDTO);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    /**
     *  新增查询修改查询和详情查询和销售审核查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findDetails",method = RequestMethod.POST)
    public ServiceResponse<ObjectionTiBaoVO> findDetails(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<ObjectionTiBaoVO> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionTiBaoVO objectionTiBaoVO = jsonRequest.getReqBody();
            ObjectionTiBaoVO objectionTiBaoVO1 = objectionTiBaoService.findDetails(objectionTiBaoVO);
            if (objectionTiBaoVO1.getExplain()!=null){
                serviceResponse.setRetCode("1");
                serviceResponse.setRetMessage(objectionTiBaoVO1.getExplain());
            }
            serviceResponse.setRetContent(objectionTiBaoVO1);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }


    /**
     * 新增保存修改销售审核保存驳回通过
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ServiceResponse<ObjectionTiBaoVO> update(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("参数", JsonUtil.toJson(jsonRequest));
        ServiceResponse<ObjectionTiBaoVO> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionTiBaoVO objectionTiBaoVO = jsonRequest.getReqBody();
            ObjectionTiBaoVO objectionTiBaoVO1= objectionTiBaoService.update(objectionTiBaoVO);
            if (objectionTiBaoVO1.getCheckCode()!=null){
            if(-100 == objectionTiBaoVO1.getCheckCode().intValue()){
                serviceResponse.setRetCode(ServiceResponse.FAIL_CODE);
                serviceResponse.setRetMessage("质证书编号不存在");
            }else if(-101 == objectionTiBaoVO1.getCheckCode().intValue()){
                serviceResponse.setRetCode(ServiceResponse.FAIL_CODE);
                serviceResponse.setRetMessage("质证书管理单位不存在");
            }
            }
            serviceResponse.setRetContent(objectionTiBaoVO1);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }


    /**
     * 提交/删除
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ServiceResponse<Integer> submit(@RequestBody JsonRequest<List<ObjectionTiBaoVO>> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
        try {
            List<ObjectionTiBaoVO> objectionTiBaoVO = jsonRequest.getReqBody();
            Integer integer = objectionTiBaoService.submit(objectionTiBaoVO);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    /**
     * 导出
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public ServiceResponse<List<ObjectionTiBaoVO>> export(@RequestBody JsonRequest<List<String>> jsonRequest){
        logger.info("分页", JsonUtil.toJson(jsonRequest));
        ServiceResponse<List<ObjectionTiBaoVO>> serviceResponse = new ServiceResponse<>();
        try {
            List<String> objectionTiBaoVO = jsonRequest.getReqBody();
            List<ObjectionTiBaoVO> integer = objectionTiBaoService.export(objectionTiBaoVO);
            serviceResponse.setRetContent(integer);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }




    /**
     * 协议书下载
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping("/downFile")
    public ServiceResponse<ObjectionTiBaoVO> downFile(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("参数 = {}",JsonUtil.toJson(jsonRequest));
        ServiceResponse<ObjectionTiBaoVO> serviceResponse = new ServiceResponse<>();
        ObjectionTiBaoVO reqBody = jsonRequest.getReqBody();
        try{
            ObjectionTiBaoVO downUrl = objectionTiBaoService.findDownUrl(reqBody);
            serviceResponse.setRetContent(downUrl);
            return serviceResponse;
        }catch (Exception e){
            logger.error("保存 参数 失败 error = {}",e);

            throw new BusinessException("0000001");
        }
    }

    /**
     * 详情页面回显润乾（下载+打印）
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping("/down")
    public ServiceResponse<ObjectionTiBaoVO> down(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("参数 = {}",JsonUtil.toJson(jsonRequest));
        ServiceResponse<ObjectionTiBaoVO> serviceResponse = new ServiceResponse<>();
        ObjectionTiBaoVO reqBody = jsonRequest.getReqBody();
        try{
            ObjectionTiBaoVO downUrl = objectionTiBaoService.findDownUrl(reqBody);
            serviceResponse.setRetContent(downUrl);
            return serviceResponse;
        }catch (Exception e){
            logger.error("保存 参数 失败 error = {}",e);

            throw new BusinessException("0000001");
        }
    }

    /**
     * 报告附件查看
     * @param  jsonRequest
     * @return
     *
     * */

    @RequestMapping(value = "/lookPhoto")
    public ServiceResponse<ObjectionTiBaoVO> lookPhoto(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest){
        logger.info("参数 = {}",JsonUtil.toJson(jsonRequest));
        ServiceResponse<ObjectionTiBaoVO> serviceResponse = new ServiceResponse<>();
        ObjectionTiBaoVO reqBody = jsonRequest.getReqBody();
        try{
            ObjectionTiBaoVO downUrl = objectionTiBaoService.lookPhoto(reqBody);
            serviceResponse.setRetContent(downUrl);
            return serviceResponse;
        }catch (Exception e){
            logger.error("保存 参数 失败 error = {}",e);

            throw new BusinessException("0000001");
        }
    }



    /**
     * @param:
     * @return:
     * @description:  根据不同状态计数
     * @author: lirunze
     * @Date: 2018/9/7
     */
    @RequestMapping(value = "/count",method = RequestMethod.POST)
    ServiceResponse<ObjectionTiBaoCountVO> getCount(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest) {
        logger.info("根据不同状态计数");
        ServiceResponse<ObjectionTiBaoCountVO> serviceResponse = new ServiceResponse<>();

        try {
            CrmClaimApply crmClaimApply = new CrmClaimApply();
            crmClaimApply.setCreatedBy(jsonRequest.getReqBody().getCreatedBy());

            ObjectionTiBaoCountVO vo = objectionTiBaoService.getCount(crmClaimApply);
            serviceResponse.setRetContent(vo);
        } catch (Exception e) {
            logger.error("错误 = {}", e);
            serviceResponse.setException(new BusinessException("500"));
        }

        return serviceResponse;
    }



    //app

    /**
     * 异议提报列表
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/findTiBaoByPage", method = RequestMethod.POST)
    ServiceResponse<PageDTO<ObjectionTiBaoVO>> findTiBaoByPage(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest) {
        logger.info("条件分页查询 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<ObjectionTiBaoVO>> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionTiBaoVO objectionTiBaoVO = jsonRequest.getReqBody();
            PageDTO<ObjectionTiBaoVO> pageDTO = objectionTiBaoService.findTiBaoByPage(objectionTiBaoVO);
            serviceResponse.setRetContent(pageDTO);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

    /**
     * 异议跟踪列表
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/findgenzongByPage", method = RequestMethod.POST)
    ServiceResponse<PageDTO<ObjectionTiBaoVO>> findgenzongByPage(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest) {
        logger.info("条件分页查询 = {}", JsonUtil.toJson(jsonRequest));
        ServiceResponse<PageDTO<ObjectionTiBaoVO>> serviceResponse = new ServiceResponse<>();
        try {
            ObjectionTiBaoVO objectionTiBaoVO = jsonRequest.getReqBody();
            PageDTO<ObjectionTiBaoVO> pageDTO = objectionTiBaoService.findgenzongByPage(objectionTiBaoVO);
            serviceResponse.setRetContent(pageDTO);
        }catch (BusinessException e){
            logger.error("获取分页出错",e);
            serviceResponse.setException(new BusinessException("500"));
        }
        return  serviceResponse;
    }

}
