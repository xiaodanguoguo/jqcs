package jq.steel.cs.services.cust.facade.controller.app;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.session.AcctSession;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmMillSheetSplitInfoVO;
import jq.steel.cs.services.cust.api.vo.MillCoilInfoVO;
import jq.steel.cs.services.cust.api.vo.MillSheetHeadVO;
import jq.steel.cs.services.cust.facade.model.CrmMillSheetSplitInfo;
import jq.steel.cs.services.cust.facade.service.app.AppMillSheetHeadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/millsheetHead")
public class AppMillSheetHeadController {

    private final static Logger logger = LoggerFactory.getLogger(AppMillSheetHostsDetailController.class);
    @Autowired
    private AppMillSheetHeadService millSheetHeadService;
    /**
     * 前台通过输入质证书编号后自动带出需要的信息
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/findMillSheetHeadByMillSheetNo", method = RequestMethod.POST)
    public ServiceResponse<MillSheetHeadVO> getMillSheetHeadByMillSheetNo(@RequestBody JsonRequest<MillSheetHeadVO> jsonRequest) {
        ServiceResponse<MillSheetHeadVO> serviceResponse = new ServiceResponse<>();
        if (jsonRequest == null || jsonRequest.getReqBody().getMillSheetNo() == null) {
            serviceResponse.setException(new BusinessException("质证书编号不能为空!"));
            return serviceResponse;
        }
        try {
            MillSheetHeadVO mshVO = jsonRequest.getReqBody();
            //MillSheetHeadVO vo = millSheetHeadService.getSheetHeadByMillSheetNo(mshVO.getMillSheetNo());
            MillSheetHeadVO vo = millSheetHeadService.getByMillSheetNOWithCreateOrUpdate(mshVO.getMillSheetNo());
            serviceResponse.setRetContent(vo);
        } catch (Exception e) {
            serviceResponse.setException(new BusinessException("500"));
            logger.error("错误 = {}", e);
        }
        return serviceResponse;
    }

    @RequestMapping(value = "/findCoilByZcharg", method = RequestMethod.POST)
    public ServiceResponse<MillCoilInfoVO> getCoilByZcharg(@RequestBody JsonRequest<MillCoilInfoVO> jsonRequest) {
        ServiceResponse<MillCoilInfoVO> serviceResponse = new ServiceResponse<>();
        if (jsonRequest == null
                || jsonRequest.getReqBody().getZcharg() == null
                || jsonRequest.getReqBody().getMillSheetNo() == null
        ) {
            serviceResponse.setException(new BusinessException(" 批/板/卷号或者质证书编号不能为空!"));
            return serviceResponse;
        }
        try {
            MillCoilInfoVO mciVO = jsonRequest.getReqBody();
            MillCoilInfoVO vo = millSheetHeadService.getCoilByZcharg(mciVO);
            serviceResponse.setRetContent(vo);
        } catch (Exception e) {
            serviceResponse.setException(new BusinessException("500"));
            logger.error("错误 = {}", e);
        }
        return serviceResponse;
    }


    @RequestMapping(value = "/findMillSheetBySaleCompany", method = RequestMethod.POST)
    public ServiceResponse<CrmMillSheetSplitInfoVO> findMillSheetForSaleCompany(@RequestBody JsonRequest<CrmMillSheetSplitInfoVO> jsonRequest) {
        ServiceResponse<CrmMillSheetSplitInfoVO> serviceResponse = new ServiceResponse<>();
        try {

            CrmMillSheetSplitInfoVO vo = jsonRequest.getReqBody();
            List<CrmMillSheetSplitInfoVO> vos = millSheetHeadService.getMillSheetForSaleCompany(vo);
            serviceResponse.setRetContent(vo);
        } catch (Exception e) {
            serviceResponse.setException(new BusinessException("500"));
            logger.error("错误 = {}", e);
        }
        return serviceResponse;
    }


}
