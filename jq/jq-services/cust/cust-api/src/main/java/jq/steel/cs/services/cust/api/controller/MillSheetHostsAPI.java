package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;;import java.nio.charset.MalformedInputException;
import java.util.List;

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface MillSheetHostsAPI {
    /**
     * 条件分页查询
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/millsheet/findMillSheetByPage", method = RequestMethod.POST)
    ServiceResponse<PageDTO<MillSheetHostsVO>> findMillSheetByPage(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest);


    /**
     * 条件分页查询（酒钢）
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/millsheet/findMillSheetByPage1", method = RequestMethod.POST)
    ServiceResponse<PageDTO<MillSheetHostsVO>> findMillSheetByPage1(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest);

    /**
     * 查询质证书地址
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/millsheet/preview", method = RequestMethod.POST)
    ServiceResponse<List<MillSheetHostsVO>> findUrl(@RequestBody JsonRequest<List<MillSheetHostsVO>> jsonRequest);

    /**
     * 打印
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/millsheet/preview1", method = RequestMethod.POST)
    ServiceResponse<List<MillSheetHostsVO>> findUrl1(@RequestBody JsonRequest<List<String>> jsonRequest);



    /**
     * 查询质证书地址
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value ="/millsheet/rollbackQuery", method = RequestMethod.POST)
    ServiceResponse<MillSheetHostsVO> rollbackQuery(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest);

    /**
     * 下载返回地址
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/millsheet/downFile",method = RequestMethod.POST)
    ServiceResponse<List<MillSheetHostsVO>> downFile(@RequestBody JsonRequest<List<String>> jsonRequest);
    //ServiceResponse<List<MillSheetHostsVO>> downFile(@RequestBody JsonRequest<List<String>> jsonRequest, @RequestParam("orgName")String orgName,@RequestParam("orgCode")String orgCode);

    /**
     * 诉赔校验输入质证书正确与否
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/millsheet/findIsTrue",method = RequestMethod.POST)
    ServiceResponse<MillSheetHostsVO> findIsTrue(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest);


    /**
     * 查询条件校验钢卷编号是否正确
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/millsheet/checkCoil",method = RequestMethod.POST)
    ServiceResponse<MillSheetHostsVO> checkCoil(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest);


    /**
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/millsheet/downloadForApp")
    public ServiceResponse<MillSheetHostsVO> downloadForApp(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest);


    /**
     * 打印次数/下载次数+1
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/millsheet/updateNumber",method = RequestMethod.POST)
    ServiceResponse<Integer> updateNumber(@RequestBody JsonRequest<List<MillSheetHostsVO>> jsonRequest);
}
