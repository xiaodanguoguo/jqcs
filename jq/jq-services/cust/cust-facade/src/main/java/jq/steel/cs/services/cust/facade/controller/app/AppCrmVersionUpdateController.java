package jq.steel.cs.services.cust.facade.controller.app;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmVersionUpdateVO;
import jq.steel.cs.services.cust.facade.service.app.AppCrmVersionUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller :	CrmVersionUpdate
 * @author		lujiawei
 * @date 		2018-9-13
 */
 
@RestController
@RequestMapping("/app/crmVersionUpdate")
public class AppCrmVersionUpdateController {
	private static Logger logger = LoggerFactory.getLogger(AppCrmVersionUpdateController.class);

	@Autowired
    private AppCrmVersionUpdateService crmVersionUpdateService;
    
    /**
	 * 保存
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping(value = "/save" , method = RequestMethod.POST)
	public ServiceResponse<Integer> save(@RequestBody JsonRequest<CrmVersionUpdateVO> jsonRequest) {
		ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
		try {
			logger.info("save 参数 = {}", JsonUtil.toJson(jsonRequest));
			CrmVersionUpdateVO vo = jsonRequest.getReqBody();
			Integer result = crmVersionUpdateService.insertSelective(vo);
			if (result > 0) {
				serviceResponse.setRetContent(result);
			}else{
				serviceResponse.setRetCode("");
			}
		} catch (Exception e) {
			e.printStackTrace();
			serviceResponse.setException(new BusinessException("500", new Object[] {}));
			serviceResponse.setRetCode("500");
			logger.error(e.getMessage());
			return serviceResponse;
		}

		return serviceResponse;

	}
	
	/**
	 * 更新
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping(value = "/update" , method = RequestMethod.PUT)
	public ServiceResponse<Integer> update(@RequestBody JsonRequest<CrmVersionUpdateVO> jsonRequest) {
		ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
		try {
			logger.info("update 参数 = {}", JsonUtil.toJson(jsonRequest));
			CrmVersionUpdateVO vo = jsonRequest.getReqBody();
			Integer result = crmVersionUpdateService.updateByPrimaryKeySelective(vo);
			if (result > 0) {
				serviceResponse.setRetContent(result);
			}else{
				serviceResponse.setRetCode("");
			}
		} catch (Exception e) {
			e.printStackTrace();
			serviceResponse.setException(new BusinessException("500", new Object[] {}));
			serviceResponse.setRetCode("500");
			logger.error(e.getMessage());
			return serviceResponse;
		}

		return serviceResponse;

	}
	
	/**
	 * 删除
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping(value = "/delete" , method = RequestMethod.DELETE)
	public ServiceResponse<Integer> delete(@RequestBody JsonRequest<CrmVersionUpdateVO> jsonRequest) {
		ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
		try {
			logger.info("delete 参数 = {}", JsonUtil.toJson(jsonRequest));
			CrmVersionUpdateVO vo = jsonRequest.getReqBody();
			Integer result = crmVersionUpdateService.deleteByPrimaryKey(vo.getVid());
			if (result > 0) {
				serviceResponse.setRetContent(result);
			}else{
				serviceResponse.setRetCode("");
			}
		} catch (Exception e) {
			e.printStackTrace();
			serviceResponse.setException(new BusinessException("500", new Object[] {}));
			serviceResponse.setRetCode("500");
			logger.error(e.getMessage());
			return serviceResponse;
		}

		return serviceResponse;

	}
	
	/**
	 * 查询单条记录
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping(value = "/querydetails" , method = RequestMethod.GET)
	public ServiceResponse<CrmVersionUpdateVO> queryDetails(@RequestBody JsonRequest<CrmVersionUpdateVO> jsonRequest) {
		ServiceResponse<CrmVersionUpdateVO> serviceResponse = new ServiceResponse<>();
		try {
			logger.info("queryDetails 参数 = {}", JsonUtil.toJson(jsonRequest));
			CrmVersionUpdateVO vo = jsonRequest.getReqBody();
			CrmVersionUpdateVO crmVersionUpdateVO = crmVersionUpdateService.selectByPrimaryKey(vo.getVid());
			serviceResponse.setRetContent(crmVersionUpdateVO);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResponse.setException(new BusinessException("500", new Object[] {}));
			serviceResponse.setRetCode("500");
			logger.error(e.getMessage());
			return serviceResponse;
		}

		return serviceResponse;

	}
	
	/**
	 * 分页查询
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping(value = "/querypageresult" , method = RequestMethod.POST)
	public ServiceResponse<PageDTO<CrmVersionUpdateVO>> queryPageResult(@RequestBody JsonRequest<CrmVersionUpdateVO> jsonRequest) {
		ServiceResponse<PageDTO<CrmVersionUpdateVO>> serviceResponse = new ServiceResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
			CrmVersionUpdateVO vo = jsonRequest.getReqBody();
			PageDTOUtil.startPage(1,10);
			List<CrmVersionUpdateVO> crmVersionUpdateVOs = crmVersionUpdateService.select(vo);
			PageDTO<CrmVersionUpdateVO> pages = PageDTOUtil.transform(crmVersionUpdateVOs);
			serviceResponse.setRetContent(pages);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResponse.setException(new BusinessException("500", new Object[] {}));
			serviceResponse.setRetCode("500");
			logger.error(e.getMessage());
			return serviceResponse;
		} finally {
			PageDTOUtil.endPage();
		}
		return serviceResponse;
	}
	
	/**
	 * 批量 保存、修改、删除
	 * 参数：opt insert、update、delete
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/keep")
	public ServiceResponse<Integer> keep(@RequestBody JsonRequest<List<CrmVersionUpdateVO>> jsonRequest) {
		ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
		
		try {
			logger.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
			List<CrmVersionUpdateVO> crmVersionUpdateVOs = jsonRequest.getReqBody();
			if (CollectionUtils.isEmpty(crmVersionUpdateVOs)) {
				serviceResponse.setRetCode("");
				return serviceResponse;
			}
			Integer result =  crmVersionUpdateService.keep(crmVersionUpdateVOs);
			if (result > 0) {
				serviceResponse.setRetContent(result);
			}else{
				serviceResponse.setRetCode("");
			}
		} catch (Exception e) {
			e.printStackTrace();
			serviceResponse.setException(new BusinessException("500", new Object[] {}));
			serviceResponse.setRetCode("500");
			logger.error(e.getMessage());
			return serviceResponse;
		}
		return serviceResponse;
	}

	/**
	 * 返回最新的版本信息
	 * @return
	 */
	@RequestMapping(value = "/getNewVersion" , method = RequestMethod.GET)
	public ServiceResponse<CrmVersionUpdateVO> newVersion() {
		ServiceResponse<CrmVersionUpdateVO> serviceResponse = new ServiceResponse<>();
		try {
			CrmVersionUpdateVO vo = crmVersionUpdateService.getNewVerson();
			serviceResponse.setRetContent(vo);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResponse.setException(new BusinessException("500", new Object[] {}));
			serviceResponse.setRetCode("500");
			logger.error(e.getMessage());
			return serviceResponse;
		}
		return serviceResponse;
	}
    
}