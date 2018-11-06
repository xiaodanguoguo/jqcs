package jq.steel.cs.services.cust.facade.controller.app;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetDetailVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHostsVO;
import jq.steel.cs.services.cust.facade.service.app.AppCrmCoilService;
import jq.steel.cs.services.cust.facade.service.app.AppMillSheetHeadService;
import jq.steel.cs.services.cust.facade.service.millsheet.MillSheetHostsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @ClassName:      AppMillSheetHostsDetailController
 * @Description:    查询质证书明细
 * @Author:         lujiawei
 * @CreateDate:     2018/9/13 9:26
 */
@RestController
@RequestMapping("/app/millsheet")
public class AppMillSheetHostsDetailController {
    private final static Logger logger = LoggerFactory.getLogger(AppMillSheetHostsDetailController.class);
    @Autowired
    private AppCrmCoilService appCrmCoilService;
    @Autowired
    private AppMillSheetHeadService millSheetHeadService;
    @Autowired
    private MillSheetHostsService millSheetHostsService;

    /**
     * 通过质证书信息,查询对应的钢卷信息以及对应的钢卷物理,化学信息
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/findMillSheetDeatilByMillSheet", method = RequestMethod.POST)
    public ServiceResponse<List<CrmMillCoilInfoVO>> getCoilDetailByMillSheet(@RequestBody JsonRequest<CrmMillSheetDetailVO> jsonRequest) {
        ServiceResponse<List<CrmMillCoilInfoVO>> serviceResponse = new ServiceResponse<>();
        if (jsonRequest == null) {
            serviceResponse.setException(new BusinessException("非法操作"));
            return serviceResponse;
        }
        try {
            CrmMillSheetDetailVO cmsdVO = jsonRequest.getReqBody();
            List<CrmMillCoilInfoVO> listAll = appCrmCoilService.getListByMillSheet(cmsdVO.getMillSheetNo(),1);
            serviceResponse.setRetContent(listAll);
        } catch (Exception e) {
            serviceResponse.setException(new BusinessException("500"));
            logger.error("错误 = {}", e);
        }
        return serviceResponse;
    }

    /**
     * 扫描二维码,查询质证书信息和质证书下的钢卷信息以及对应的钢卷物理,化学信息
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/findMillSheetDeatilByQrCode", method = RequestMethod.POST)
    public ServiceResponse<List<CrmMillCoilInfoVO>> getCoilDetailByQrCode(@RequestBody JsonRequest<CrmMillSheetDetailVO> jsonRequest) {
        ServiceResponse<List<CrmMillCoilInfoVO>> serviceResponse = new ServiceResponse<>();
        if (jsonRequest == null) {
            serviceResponse.setException(new BusinessException("非法操作"));
            return serviceResponse;
        }
        try {
            CrmMillSheetDetailVO cmsdVO = jsonRequest.getReqBody();
            List<CrmMillCoilInfoVO> listAll = appCrmCoilService.getListByByQrCode(cmsdVO.getMillSheetNo(),1);
            /*PageDTO<CrmMillCoilInfoVO> pageDTO = new PageDTO<>();
            pageDTO.setResultData(listAll);*/
            serviceResponse.setRetContent(listAll);
        } catch (Exception e) {
            serviceResponse.setException(new BusinessException("500"));
            logger.error("错误 = {}", e);
        }
        return serviceResponse;
    }

    /**
     * 通过车号/批板卷号/发车时间,进行条件查询
     */
    @RequestMapping(value = "/getSheetMsgs", method = RequestMethod.POST)
    public ServiceResponse<List<MillSheetHostsVO>> getSheetMsg(@RequestBody JsonRequest<MillCoilInfoVO> jsonRequest) {
        ServiceResponse<List<MillSheetHostsVO>> serviceResponse = new ServiceResponse<>();
        if (jsonRequest == null) {
            serviceResponse.setException(new BusinessException("非法操作"));
            return serviceResponse;
        }
        try {
            MillCoilInfoVO vo = jsonRequest.getReqBody();
            List<MillSheetHostsVO> listAll = millSheetHeadService.getSheetHostsMsg(vo);
            /*PageDTO<CrmMillCoilInfoVO> pageDTO = new PageDTO<>();
            pageDTO.setResultData(listAll);*/
            serviceResponse.setRetContent(listAll);
        } catch (Exception e) {
            serviceResponse.setException(new BusinessException("500"));
            logger.error("错误 = {}", e);
        }
        return serviceResponse;
    }

    /**
     *改变质证书为已下载状态,同时减少可下载次数
     * @param jsonRequest
     */
    @RequestMapping(value = "/updateMillSheetHostsState",method = RequestMethod.POST)
    public void updateMillSheetHostsState(@RequestBody JsonRequest<MillSheetHostsVO> jsonRequest){
         millSheetHostsService.updateStateAndPrintNum(jsonRequest.getReqBody().getMillSheetNo());
    }

}
