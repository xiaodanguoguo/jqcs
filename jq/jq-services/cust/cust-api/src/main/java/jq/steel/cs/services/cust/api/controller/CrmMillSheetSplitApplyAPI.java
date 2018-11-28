package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitApplyVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitDetailVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface CrmMillSheetSplitApplyAPI {

    /**
     *  拆分页面（强制拆分）提交按钮
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/split/splitInsert",method = RequestMethod.POST)
    ServiceResponse<CrmMillSheetSplitApplyVO> splitInsert(@RequestBody JsonRequest<List<CrmMillSheetSplitApplyVO>> jsonRequest);

    /**
     *  拆分历史数据
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/split/splitFindAll",method = RequestMethod.POST)
    ServiceResponse<List<CrmMillSheetSplitDetailVO>> splitFindAll(@RequestBody JsonRequest<CrmMillSheetSplitDetailVO> jsonRequest);



    /**
     *  批量拆分
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/split/splitInsertAll",method = RequestMethod.POST)
    ServiceResponse<CrmMillSheetSplitApplyVO> splitInsertAll(@RequestBody JsonRequest<List<CrmMillSheetSplitApplyVO>> jsonRequest);
}
