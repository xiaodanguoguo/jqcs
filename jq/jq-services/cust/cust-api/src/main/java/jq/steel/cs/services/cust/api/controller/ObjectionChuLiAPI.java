package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.ObjectionChuLiVO;
import jq.steel.cs.services.cust.api.vo.ObjectionDiaoChaVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface ObjectionChuLiAPI {

    /**
     *  条件分页查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionChuLi/findByPage",method = RequestMethod.POST)
    ServiceResponse<PageDTO<ObjectionChuLiVO>> findByPage(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest);


    /**
     *  公共信息查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionChuLi/findAll",method = RequestMethod.POST)
    ServiceResponse<ObjectionChuLiVO> findAll(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest);

    /**
     *  协议书保存/提交/审核
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionChuLi/agreementUpdate",method = RequestMethod.POST)
    ServiceResponse<Integer> agreementUpdate(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest);



    /**
     * 异议处理导出
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionChuLi/export",method = RequestMethod.POST)
    ServiceResponse<List<ObjectionChuLiVO>> export(@RequestBody JsonRequest<List<String>> jsonRequest);

    /**
     *  协议书模板查看
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionChuLi/agreementLook",method = RequestMethod.POST)
    ServiceResponse agreementLook(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest);

    /**
     *  协议书模板下载pdf
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionChuLi/agreementModel",method = RequestMethod.POST)
    ServiceResponse<ObjectionChuLiVO> agreementModel(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest);

    /**
     *  强制结案
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionChuLi/compulsorySettlement",method = RequestMethod.POST)
    ServiceResponse<Integer> compulsorySettlement(@RequestBody JsonRequest<ObjectionChuLiVO> jsonRequest);


}
