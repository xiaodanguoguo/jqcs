package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.ObjectionDiaoChaVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface ObjectionDiaoChaAPI {

    /**
     *  条件分页查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionDiaoCha/findByPage",method = RequestMethod.POST)
    ServiceResponse<PageDTO<ObjectionDiaoChaVO>> findByPage(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest);


    /**
     *  异议调查外部调查内部调查回显数据
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionDiaoCha/findDetails",method = RequestMethod.POST)
    ServiceResponse<ObjectionDiaoChaVO> findDetails(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest);

    /**
     *  内外部调查报告（保存，跟踪，提交）异议处理确认书（通过 ，驳回）
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionDiaoCha/update",method = RequestMethod.POST)
    ServiceResponse<Integer> update(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest);


    /**
     *  内部调查报告（保存，提交）
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionDiaoCha/updateInside",method = RequestMethod.POST)
    ServiceResponse<Integer> updateInside(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest);


    /**
     *  异议调查导出
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionDiaoCha/export",method = RequestMethod.POST)
    ServiceResponse<List<ObjectionDiaoChaVO>> export(@RequestBody JsonRequest<List<String>> jsonRequest);

    /**
     *  异议调查打印受理单
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionDiaoCha/print",method = RequestMethod.POST)
    ServiceResponse print(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest);

    /**
     *  调查报告下载pdf
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionDiaoCha/downPdf",method = RequestMethod.POST)
    ServiceResponse<ObjectionDiaoChaVO> downPdf(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest);

    /**
     *  调查报告驳回
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionDiaoCha/reject",method = RequestMethod.POST)
    ServiceResponse<Integer> reject(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest);

    /**
     *  内部调查/外部调查开始结束状态修改
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionDiaoCha/updateState",method = RequestMethod.POST)
    ServiceResponse<Integer> updateState(@RequestBody JsonRequest<ObjectionDiaoChaVO> jsonRequest) ;
}
