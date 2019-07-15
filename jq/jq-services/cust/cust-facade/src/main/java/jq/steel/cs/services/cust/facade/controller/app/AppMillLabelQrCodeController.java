package jq.steel.cs.services.cust.facade.controller.app;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import jq.steel.cs.services.cust.api.vo.CrmMillCoilInfoVO;
import jq.steel.cs.services.cust.facade.service.app.AppMillLabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName:      AppMillLabelQrCodeController
 * @Description:    实物标签-->通过二维码查询标签信息
 * @Author:         lujiawei
 * @CreateDate:     2018/9/19 13:30
 */

@RestController
@RequestMapping("/app/millLabel")
public class AppMillLabelQrCodeController {

    private final static Logger logger = LoggerFactory.getLogger(AppMillSheetHostsDetailController.class);

    @Autowired
    private AppMillLabelService appMillLabelService;

    //通过二维码扫描出来的信息进行查询

    /**
     * @param jsonRequest " 榆中县 04 甲\n2017-07-15 13:17\nHRB400E\nф14\n170708101  46\n123支"
     * @return
     */
    @RequestMapping(value = "/queyByQrCode", method = RequestMethod.POST)
    public ServiceResponse<List<CrmMillCoilInfoVO>> queryByQrCode(@RequestBody JsonRequest<String> jsonRequest){

        ServiceResponse<List<CrmMillCoilInfoVO>> serviceResponse = new ServiceResponse<>();
        if (jsonRequest == null
                && jsonRequest.getReqBody() == null
                ) {
            serviceResponse.setException(new BusinessException("非法操作"));
            return serviceResponse;
        }
        try {
            List<CrmMillCoilInfoVO> list = appMillLabelService.queryByQrCode(jsonRequest);
            serviceResponse.setRetContent(list);
        } catch (BusinessException e) {
            serviceResponse.setRetCode(e.getErrorCode());
            serviceResponse.setRetMessage(e.getMessage());
            logger.error("错误 = {}", e);
        } catch (Exception e) {
            serviceResponse.setException(new BusinessException("500"));
            logger.error("错误 = {}", e);
        }

        return serviceResponse;

    }
}
