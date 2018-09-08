package jq.steel.cs.services.cust.api.controller;

import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoCountVO;
import jq.steel.cs.services.cust.api.vo.ObjectionTiBaoVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "${ser.name.cust}") // 这个是服务名
public interface ObjectionTiBaoAPI {

    /**
     * 条件分页查询
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/objectionTiBao/findByPage", method = RequestMethod.POST)
    ServiceResponse<PageDTO<ObjectionTiBaoVO>> findByPage(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest);


    /**
     *  新增查询修改查询和详情查询和销售审核查询
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionTiBao/findDetails",method = RequestMethod.POST)
    ServiceResponse<ObjectionTiBaoVO> findDetails(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest);

    /**
     * 新增修改销售审核保存驳回通过  保存数据
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/objectionTiBao/update", method = RequestMethod.POST)
    ServiceResponse<Integer> update(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest);



    /**
     *  删除/提交
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/objectionTiBao/submit", method = RequestMethod.POST)
    ServiceResponse<Integer> submit(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest);

    /**
     * 导出
     *
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/objectionTiBao/export", method = RequestMethod.POST)
    ServiceResponse<List<ObjectionTiBaoVO>> export(@RequestBody JsonRequest<List<String>> jsonRequest);



    /**
     * 协议书下载
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionTiBao/downFile",method = RequestMethod.POST)
    ServiceResponse<ObjectionTiBaoVO> downFile(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest);


    /**
     * 详情页面回显润乾（下载+打印）
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionTiBao/down",method = RequestMethod.POST)
    ServiceResponse<ObjectionTiBaoVO> down(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest);

    /**
     * 报告附件查看
     * @param  jsonRequest
     * @return
     *
     * */
    @RequestMapping(value = "/objectionTiBao/lookPhoto",method = RequestMethod.POST)
    ServiceResponse<ObjectionTiBaoVO> lookPhoto(@RequestBody JsonRequest<ObjectionTiBaoVO> jsonRequest);

    /**
     * @param:
     * @return:
     * @description:  根据不同状态计数
     * @author: lirunze
     * @Date: 2018/9/7
     */
    @RequestMapping(value = "/objectionTiBao/count",method = RequestMethod.POST)
    ServiceResponse<ObjectionTiBaoCountVO> getCount();
}