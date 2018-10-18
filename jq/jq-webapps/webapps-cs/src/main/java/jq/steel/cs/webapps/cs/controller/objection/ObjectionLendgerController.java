package jq.steel.cs.webapps.cs.controller.objection;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import com.ebase.utils.excel.ExportExcelUtils;
import feign.FeignException;
import jq.steel.cs.services.cust.api.controller.ObjectionLendgerAPI;
import jq.steel.cs.services.cust.api.vo.ObjectionLedgerVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/objectionLendger")
public class ObjectionLendgerController {


    private final static Logger logger = LoggerFactory.getLogger(ObjectionLendgerController.class);

    @Autowired
    private ObjectionLendgerAPI objectionLendgerAPI;



    /**
     *  质量异议统计台账
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/findLedgerByPage",method = RequestMethod.POST)
    public JsonResponse<PageDTO<ObjectionLedgerVO>> findLedgerByPage(@RequestBody JsonRequest<ObjectionLedgerVO> jsonRequest){
        logger.info("参数={}", JsonUtil.toJson(jsonRequest));
        JsonResponse<PageDTO<ObjectionLedgerVO>> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<PageDTO<ObjectionLedgerVO>> serviceResponse = objectionLendgerAPI.findLedgerByPage(jsonRequest);
            if (ServiceResponse.SUCCESS_CODE.equals(serviceResponse.getRetCode())) {
                jsonResponse.setRspBody(serviceResponse.getRetContent());
            } else {
                if (serviceResponse.isHasError()) {
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                }else {
                    jsonResponse.setRetCode(serviceResponse.getRetCode());
                    jsonResponse.setRetDesc(serviceResponse.getRetMessage());
                    return jsonResponse;
                }
            }
        } catch (BusinessException e) {
            logger.error("获取分页列表错误 = {}", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

    /**
     * 导出
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/export",method = RequestMethod.POST)
    public JsonResponse<List<ObjectionLedgerVO>> export(@RequestParam("name") String jsonRequest) {
        JsonResponse<List<ObjectionLedgerVO>> jsonResponse = new JsonResponse<>();
        try {
            ObjectionLedgerVO list = JsonUtil.fromJson(jsonRequest,ObjectionLedgerVO.class);
            JsonRequest<ObjectionLedgerVO> jsonRequest1 = new JsonRequest();
            jsonRequest1.setReqBody(list);
            ServiceResponse<List<ObjectionLedgerVO>> response = objectionLendgerAPI.export(jsonRequest1);
            // 根据service层返回的编码做不同的操作
            if (ServiceResponse.SUCCESS_CODE.equals(response.getRetCode())) {
                jsonResponse.setRspBody(response.getRetContent());
                //转正报表
                List<String> headers = getParam();
                List<ObjectionLedgerVO> arr =  jsonResponse.getRspBody();
                try {
                    ExportExcelUtils.createExcelDownload("质量异议统计台账", "质量异议统计台账", "质量异议统计台账" +
                            System.currentTimeMillis(), headers.toArray(new String[headers.size()]), arr);

                } catch (Exception e) {
                    logger.error("error = {}",e);
                }
            }   // 如果需要异常信息
            else if (response.isHasError())
                // 系统异常
                jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                // 如果需要的话, 这个方法可以获取异常信息 response.getErrorMessage()
            else {
                // 根据业务的不同确定返回的业务信息是否正常,是否需要执行下一步操作
                jsonResponse.setRetCode(response.getRetCode());
                jsonResponse.setRetDesc(response.getRetMessage());
            }
        } catch (FeignException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return jsonResponse;

    }

    private List<String> getParam() {
        List<String> headers = new ArrayList<>();
        headers.add("质量投诉编号@claimNo@4000");
        headers.add("异议状态@claimState@4000");
        headers.add("使用客户@lastUser@8000");
        headers.add("联系人@createEmpNo@4000");
        headers.add("联系电话@lastUserTel@4000");
        headers.add("代理公司（或酒钢驻外公司）@customerName@8000");
        headers.add("联系人@custEmpNo@8000");
        headers.add("联系电话@custTel@4000");
        headers.add("物流过程@logisticsProcess@8000");
        headers.add("货物存放地@lastUserAddr@8000");
        headers.add("加工工艺@endProcessingTech@8000");
        headers.add("用途@used@4000");
        headers.add("车号@originalCarNo@4000");
        headers.add("质证书编号@millSheetNo@4000");
        headers.add("牌号(mm)@designation@4000");
        headers.add("提出异议量(t)@objectionNum@4000");
        headers.add("确认量(t)@objectionConfirmation@4000");
        headers.add("赔偿金额（元）@agreementAmount@4000");
        headers.add("投诉原因@claimReason@8000");
        headers.add("确认的产品质量问题@proProblem@8000");
        headers.add("异议类别@claimType@4000");
        headers.add("生产日期@pd@4000");
        headers.add("生产工序@millLine@4000");
        headers.add("处理依据及处理方式@handingSuggestion@8000");
        headers.add("受理日期@at@4000");
        headers.add("到达时间@at1@4000");
        headers.add("结案日期@ct@4000");
        headers.add("结案人@closingUser@4000");
        headers.add("处理周期@cycle@4000");
        headers.add("备注@memo@4000");
        headers.add("客户评价@evaluate@4000");
        return headers;
    }
}
