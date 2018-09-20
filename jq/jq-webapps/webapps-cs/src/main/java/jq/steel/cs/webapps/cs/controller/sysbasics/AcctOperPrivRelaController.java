package jq.steel.cs.webapps.cs.controller.sysbasics;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import feign.FeignException;
import jq.steel.cs.services.base.api.controller.AcctOperPrivRelaAPI;
import jq.steel.cs.services.base.api.vo.AcctOperPrivRelaVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统基础模块-  系统功能管理  -  系统角色定义
 * @Auther: zhaoyuhang
 */

@RestController
@RequestMapping("/acctOperPrivRela")
public class AcctOperPrivRelaController {

    private final static Logger logger = LoggerFactory.getLogger(AcctOperPrivRelaController.class);

    @Autowired
    private AcctOperPrivRelaAPI acctOperPrivRelaAPI;


    /**
     *  系统角色功能关联添加
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/addAcctOperPrivRela",method = RequestMethod.POST)
    public JsonResponse<Integer> addAcctOperPrivRela(@RequestBody JsonRequest<AcctOperPrivRelaVO> jsonRequest){
        JsonResponse<Integer> result = new JsonResponse<>();
        try {
//            //功能ID
//            if(StringUtils.isEmpty(jsonRequest.getReqBody().getFunctionIds())){
//                result.setRetCode("0102005");
//                return result;
//            }
//            //角色ID
//            if(StringUtils.isEmpty(jsonRequest.getReqBody().getRoleId())){
//                result.setRetCode("0102005");
//                return result;
//            }
            //根据service层返回的编码做不同的操作
            ServiceResponse<Integer> response=acctOperPrivRelaAPI.addAcctOperPrivRela(jsonRequest.getReqBody());
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode())) {
                result.setRspBody(response.getRetContent());
                //如果需要异常信息
            } else {
                if (response.isHasError()) {
                    //系统异常
                    result.setRetCode(JsonResponse.SYS_EXCEPTION);
                    //如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
                } else {
                    //根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
                    result.setRetCode(response.getRetCode());
                }
            }
        } catch (FeignException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            result.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return result;
    }

    /**
     *  系统角色功能关联删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/delAcctOperPrivRela",method = RequestMethod.POST)
    public JsonResponse<Integer> delAcctOperPrivRela(@RequestBody JsonRequest<AcctOperPrivRelaVO> jsonRequest){

        JsonResponse<Integer> result = new JsonResponse<>();
        try {
            //角色ID
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getRoleId())){
                result.setRetCode("0102005");
                return result;
            }
            //根据service层返回的编码做不同的操作
            ServiceResponse<Integer> response=acctOperPrivRelaAPI.delAcctOperPrivRela(jsonRequest.getReqBody());
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode())) {
                result.setRspBody(response.getRetContent());
                //如果需要异常信息
            } else {
                if (response.isHasError()) {
                    //系统异常
                    result.setRetCode(JsonResponse.SYS_EXCEPTION);
                    //如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
                } else {
                    //根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
                    result.setRetCode(response.getRetCode());
                }
            }
        } catch (FeignException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            result.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return result;
    }



}

