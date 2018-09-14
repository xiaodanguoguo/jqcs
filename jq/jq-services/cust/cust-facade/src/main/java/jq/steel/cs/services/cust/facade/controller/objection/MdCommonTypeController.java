package jq.steel.cs.services.cust.facade.controller.objection;

import java.util.List;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.MdCommonTypeVO;
import jq.steel.cs.services.cust.facade.service.objection.MdCommonTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/md")
public class MdCommonTypeController {
	private static Logger logger = LoggerFactory.getLogger(MdCommonTypeController.class);

	@Autowired
	private MdCommonTypeService mdCommonTypeService;

	//查询文件地址
	@RequestMapping(value = "/findItemsByTypeId",method = RequestMethod.POST)
	public ServiceResponse<List<MdCommonTypeVO>> findItemsByTypeId(@RequestBody JsonRequest<MdCommonTypeVO> jsonRequest){
		logger.info("参数 = {}", JsonUtil.toJson(jsonRequest));
		ServiceResponse<List<MdCommonTypeVO>> serviceResponse = new ServiceResponse<>();
		try {
			MdCommonTypeVO mdCommonTypeVO = jsonRequest.getReqBody();
			List<MdCommonTypeVO> list = mdCommonTypeService.findItemsByTypeId(mdCommonTypeVO);
			serviceResponse.setRetContent(list);
		}catch (BusinessException e){
			logger.error("出错",e);
			serviceResponse.setException(new BusinessException("500"));
		}
		return  serviceResponse;
	}
}
