package jq.steel.cs.services.cust.facade.controller.app;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.cust.api.vo.CrmAnnouncementVO;
import jq.steel.cs.services.cust.facade.service.app.AppCrmAnnouncementService;
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
 * controller :	CrmAnnouncement
 * @author 		lujiawei
 * @date 		2018-9-14 14:00
 */
 
@RestController
@RequestMapping("/app/crmAnnouncement")
public class AppCrmAnnouncementController {
	private static Logger logger = LoggerFactory.getLogger(AppCrmAnnouncementController.class);

	@Autowired
    private AppCrmAnnouncementService crmAnnouncementService;
    
    /**
	 * 保存
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping(value = "/save" , method = RequestMethod.POST)
	public ServiceResponse<Integer> save(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest) {
		ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
		try {
			logger.info("save 参数 = {}", JsonUtil.toJson(jsonRequest));
			CrmAnnouncementVO vo = jsonRequest.getReqBody();
			Integer result = crmAnnouncementService.insertSelective(vo);
			if (result > 0) {
				serviceResponse.setRetContent(result);
			}else{
				serviceResponse.setResponseCode("0800001");
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
	@RequestMapping(value = "/update" , method = RequestMethod.POST)
	public ServiceResponse<Integer> update(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest) {
		ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
		try {
			logger.info("update 参数 = {}", JsonUtil.toJson(jsonRequest));
			CrmAnnouncementVO vo = jsonRequest.getReqBody();
			Integer result = crmAnnouncementService.updateByPrimaryKeySelective(vo);
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
	@RequestMapping(value = "/delete" , method = RequestMethod.POST)
	public ServiceResponse<Integer> delete(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest) {
		ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
		try {
			logger.info("delete 参数 = {}", JsonUtil.toJson(jsonRequest));
			CrmAnnouncementVO vo = jsonRequest.getReqBody();
			Integer result = crmAnnouncementService.deleteByPrimaryKey(vo.getAid());
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
	@RequestMapping(value = "/querydetails" , method = RequestMethod.POST)
	public ServiceResponse<CrmAnnouncementVO> queryDetails(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest) {
		ServiceResponse<CrmAnnouncementVO> serviceResponse = new ServiceResponse<>();
		try {
			logger.info("queryDetails 参数 = {}", JsonUtil.toJson(jsonRequest));
			CrmAnnouncementVO vo = jsonRequest.getReqBody();
			CrmAnnouncementVO crmAnnouncementVO = crmAnnouncementService.selectByPrimaryKey(vo.getAid());
			serviceResponse.setRetContent(crmAnnouncementVO);
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
	public ServiceResponse<PageDTO<CrmAnnouncementVO>> queryPageResult(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest) {
		ServiceResponse<PageDTO<CrmAnnouncementVO>> serviceResponse = new ServiceResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
			CrmAnnouncementVO vo = jsonRequest.getReqBody();
			PageDTOUtil.startPage(vo.getPageNum(),vo.getPageSize());
			List<CrmAnnouncementVO> crmAnnouncementVOs = crmAnnouncementService.select(vo);
			PageDTO<CrmAnnouncementVO> pages = PageDTOUtil.transform(crmAnnouncementVOs);
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
	@RequestMapping(value = "/keep" , method = RequestMethod.POST)
	public ServiceResponse<Integer> keep(@RequestBody JsonRequest<List<CrmAnnouncementVO>> jsonRequest) {
		ServiceResponse<Integer> serviceResponse = new ServiceResponse<>();
		
		try {
			logger.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
			List<CrmAnnouncementVO> crmAnnouncementVOs = jsonRequest.getReqBody();
			if (CollectionUtils.isEmpty(crmAnnouncementVOs)) {
				serviceResponse.setRetCode("");
				return serviceResponse;
			}
			Integer result =  crmAnnouncementService.keep(crmAnnouncementVOs);
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
	 * 或取最新的公告
	 * 参数：opt insert、update、delete
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/getNewAnnouncement" , method = RequestMethod.POST)
	public ServiceResponse<CrmAnnouncementVO> getNewAnnouncement(@RequestBody JsonRequest<CrmAnnouncementVO> jsonRequest) {
		ServiceResponse<CrmAnnouncementVO> serviceResponse = new ServiceResponse<>();

		try {
			CrmAnnouncementVO vo  = crmAnnouncementService.getNewAnnouncement();
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