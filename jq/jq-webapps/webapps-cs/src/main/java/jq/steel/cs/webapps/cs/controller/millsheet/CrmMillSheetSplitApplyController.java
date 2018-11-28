package jq.steel.cs.webapps.cs.controller.millsheet;

import com.ebase.core.AssertContext;
import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import com.ebase.utils.JsonUtil;
import com.ebase.utils.excel.ImportExcelUtils;
import com.ebase.utils.file.FileUtil;
import com.raqsoft.expression.function.Iterate;
import jq.steel.cs.services.cust.api.controller.CrmMillSheetSplitApplyAPI;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitApplyVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitDetailVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.webapps.cs.controller.file.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/split")
public class CrmMillSheetSplitApplyController {
    private final static Logger logger = LoggerFactory.getLogger(CrmMillSheetSplitApplyController.class);

    @Autowired
    private CrmMillSheetSplitApplyAPI crmMillSheetSplitApplyAPI;

    /**
     *  拆分页面（强制拆分）提交按钮
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/splitInsert",method = RequestMethod.POST)
    public JsonResponse<CrmMillSheetSplitApplyVO> splitInsert(@RequestBody JsonRequest<List<CrmMillSheetSplitApplyVO>> jsonRequest){
        logger.info("{}",JsonUtil.toJson(jsonRequest));
        JsonResponse<CrmMillSheetSplitApplyVO>  jsonResponse = new JsonResponse<>();
        for (CrmMillSheetSplitApplyVO crmMillSheetSplitApplyVO: jsonRequest.getReqBody()){
            crmMillSheetSplitApplyVO.setAcctName(AssertContext.getAcctName());
        }
        try {
            ServiceResponse<CrmMillSheetSplitApplyVO> serviceResponse = crmMillSheetSplitApplyAPI.splitInsert(jsonRequest);
            jsonResponse.setRspBody(serviceResponse.getRetContent());

        } catch (BusinessException e) {
            logger.error("提交报错", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }
    /**
     *  拆分历史数据
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/splitFindAll",method = RequestMethod.POST)
    public  JsonResponse<List<CrmMillSheetSplitDetailVO>> splitFindAll (@RequestBody JsonRequest<CrmMillSheetSplitDetailVO> jsonRequest){
        logger.info("{}", JsonUtil.toJson(jsonRequest));
        JsonResponse<List<CrmMillSheetSplitDetailVO>> jsonResponse = new JsonResponse<>();
        try {
            ServiceResponse<List<CrmMillSheetSplitDetailVO>> serviceResponse = crmMillSheetSplitApplyAPI.splitFindAll(jsonRequest);
            jsonResponse.setRspBody(serviceResponse.getRetContent());
        }catch (BusinessException e){
            logger.error("查询报错", e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;
    }


    @PostMapping("/upload")
    public JsonResponse<CrmMillSheetSplitApplyVO> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        JsonRequest<List<CrmMillSheetSplitApplyVO>> jsonRequest = new JsonRequest<>();
        JsonResponse<CrmMillSheetSplitApplyVO> jsonResponse = new JsonResponse<CrmMillSheetSplitApplyVO>();

        if (null != file) {
            try {
                Map<Integer, Map<Integer, Object>> map = new HashMap<>();
                try {
                    if (map.size() > 0) {
                        List<CrmMillSheetSplitApplyVO> applyVOS = new ArrayList<>();
                        map = ImportExcelUtils.readExcelContentz(file);
                        for (int i = 0; i < map.size(); i++) {
                            CrmMillSheetSplitApplyVO crmMillSheetSplitApplyVO = new CrmMillSheetSplitApplyVO();
                            List<Object> arrayList = new ArrayList<>();
                            Map<Integer, Object> mapItem = map.get(i);
                            if (mapItem.size() == 6) {
                                for (int j = 0; j < mapItem.size(); j++) {
                                    arrayList.add(map.get(i).get(j));
                                }
                                crmMillSheetSplitApplyVO.setAcctName(AssertContext.getAcctName());
                                crmMillSheetSplitApplyVO.setMillsheetNo((String) arrayList.get(0));
                                crmMillSheetSplitApplyVO.setZchehao((String) arrayList.get(1));
                                crmMillSheetSplitApplyVO.setZjishu(Long.valueOf((String) arrayList.get(2)));
                                crmMillSheetSplitApplyVO.setZcharg((String) arrayList.get(3));
                                crmMillSheetSplitApplyVO.setSpecs((String) arrayList.get(4));
                                crmMillSheetSplitApplyVO.setSpiltCustomer((String) arrayList.get(5));
                            } else {
                                jsonResponse.setRetCode("0000001");
                                jsonResponse.setRetDesc("excel中数据不完善");
                                return jsonResponse;
                            }
                            applyVOS.add(crmMillSheetSplitApplyVO);
                        }
                        jsonRequest.setReqBody(applyVOS);
                        ServiceResponse<CrmMillSheetSplitApplyVO> serviceResponse = crmMillSheetSplitApplyAPI.splitInsertAll(jsonRequest);
                        if (serviceResponse.getRetContent().getCode()>0){
                            jsonResponse.setRspBody(serviceResponse.getRetContent());
                        }else {
                            jsonResponse.setRetCode("0000001");
                            jsonResponse.setRetDesc("请在模板中输入有效数据");
                        }

                    } else {
                        jsonResponse.setRetCode("0000001");
                        jsonResponse.setRetDesc("请在模板中输入有效数据");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                logger.error("异常：", e);
                jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            }
        } else {
            logger.info("上传文件不能为空");
        }

        return jsonResponse;
    }
}
