package jq.steel.cs.webapps.cs.controller.user;

import com.ebase.core.AssertContext;
import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.base.api.controller.FunctionManageAPI;
import jq.steel.cs.services.base.api.vo.FunctionManageVO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final static Logger LOG = SearchableLoggerFactory.getDefaultLogger();

    @Autowired
    private FunctionManageAPI functionManageAPI;

    // 用户权限接口
    @RequestMapping("/list")
    public JsonResponse<List<String>> getAcctRes() {
        JsonResponse<List<String>> jsonResponse = new JsonResponse<List<String>>(null);
        try {
            FunctionManageVO functionManageVO = new FunctionManageVO();
            functionManageVO.setAcctId(AssertContext.getAcctId());
            functionManageVO.setAcctId("16");

            ServiceResponse<List<FunctionManageVO>> serviceResponse = functionManageAPI.getUserfunctionList(functionManageVO);

            List<String> list = new ArrayList<>();
            for(FunctionManageVO functionVo : serviceResponse.getRetContent()){
                list.add(String.valueOf(functionVo.getFunctionId()));
            }

            jsonResponse.setRspBody(list);
        } catch (Exception e) {
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }
        return jsonResponse;
    }

}
