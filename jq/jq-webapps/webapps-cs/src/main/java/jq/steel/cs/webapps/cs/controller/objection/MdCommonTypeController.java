package jq.steel.cs.webapps.cs.controller.objection;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.cust.api.controller.MdCommonTypeAPI;
import jq.steel.cs.services.cust.api.vo.MdCommonTypeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/md")
public class MdCommonTypeController {

	private final static Logger logger = LoggerFactory.getLogger(MdCommonTypeController.class);
	
	@Autowired
	private MdCommonTypeAPI mdCommonTypeAPI;
	

	//诉赔提报界面校验质证书编号是否正确
	@RequestMapping(value = "/findItemsByTypeId",method = RequestMethod.POST)
	public JsonResponse<List<MdCommonTypeVO>> findItemsByTypeId(@RequestBody JsonRequest<MdCommonTypeVO> jsonRequest){
		JsonResponse<List<MdCommonTypeVO>> jsonResponse = new JsonResponse<>();

		try {
			ServiceResponse<List<MdCommonTypeVO>> serviceResponse = mdCommonTypeAPI.findItemsByTypeId(jsonRequest);
			jsonResponse.setRspBody(serviceResponse.getRetContent());
		} catch (BusinessException e) {
			logger.error("获取分页列表错误 = {}", e);
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		}
		return jsonResponse;
	}
}
