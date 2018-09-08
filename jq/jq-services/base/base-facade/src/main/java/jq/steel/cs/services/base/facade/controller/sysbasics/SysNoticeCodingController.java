package jq.steel.cs.services.base.facade.controller.sysbasics;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.vo.SysNoticeVO;
import jq.steel.cs.services.base.facade.common.SysPramType;
import jq.steel.cs.services.base.facade.model.SysNotice;
import jq.steel.cs.services.base.facade.service.sysbasics.SysNoticeCodingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by MrLi on 2018/7/19. Test
 */
@RestController
@RequestMapping("/SysNotice")
public class SysNoticeCodingController {
	private static Logger logger = LoggerFactory.getLogger(SysNoticeCodingController.class);

	@Autowired
	private SysNoticeCodingService sysNoticeCodingService;

	/**
	 * 获取所有数据
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/getSysNoticeAll")
	public ServiceResponse<Map<String, List<SysNoticeVO>>> getSysNoticeAll(
			@RequestBody JsonRequest<SysNoticeVO> jsonRequest) {
		ServiceResponse<Map<String, List<SysNoticeVO>>> jsonResponse = new ServiceResponse<Map<String, List<SysNoticeVO>>>();
		Map<String, List<SysNoticeVO>> map = new HashMap<String, List<SysNoticeVO>>();
		Map<String, Object> requestMap = new HashMap<>();
		try {
			logger.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
			SysNoticeVO vo = jsonRequest.getReqBody();
			requestMap.put("title", vo.getTitle());
			requestMap.put("publishObj", vo.getPublishObj());
			requestMap.put("status", vo.getStatus());
			requestMap.put("isDelete", vo.getIsDelete());
			List<SysNotice> sysNoticeList = sysNoticeCodingService.getSysNoticeAll(requestMap);
			List<SysNoticeVO> sysNoticeVoList = new ArrayList<>();
			BeanCopyUtil.copyPropertieses(sysNoticeList, sysNoticeVoList, SysNoticeVO.class);
			map.put("resultData", sysNoticeVoList);
			jsonResponse.setRetContent(map);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setException(new BusinessException("500", new Object[] {}));
			logger.error(e.getMessage());
		}
		return jsonResponse;
	}

	/**
	 * 获取数据详细信息
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/getSysNoticeByKey")
	public ServiceResponse<SysNoticeVO> getSysNoticeByKey(@RequestBody JsonRequest<SysNoticeVO> jsonRequest) {
		ServiceResponse<SysNoticeVO> jsonResponse = new ServiceResponse<SysNoticeVO>();
		try {
			logger.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
			SysNoticeVO vo = jsonRequest.getReqBody();
			SysNotice sysNotice = sysNoticeCodingService.getSysNoticeByKey(vo.getId());
			SysNoticeVO sysNoticeVO = BeanCopyUtil.copy(sysNotice, SysNoticeVO.class);
			jsonResponse.setRetContent(sysNoticeVO);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setException(new BusinessException("500", new Object[] {}));
			logger.error(e.getMessage());
		}
		return jsonResponse;
	}

	/**
	 * 系统参数 保存 或者删除 或者修改接口
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/sysNoticeKeep")
	public ServiceResponse<Integer> keepSysNotice(@RequestBody JsonRequest<List<SysNoticeVO>> jsonRequest) {
		ServiceResponse<Integer> jsonResponse = new ServiceResponse<Integer>();
		try {
			logger.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
			List<SysNoticeVO> sysNoticeVOList = jsonRequest.getReqBody();
			List<SysNotice> sysNoticeList = BeanCopyUtil.copyList(sysNoticeVOList, SysNotice.class);
			for (SysNotice sysNotice : sysNoticeList) {
				String opt = sysNotice.getOpt();
				if (StringUtils.isEmpty(opt)) {
					jsonResponse.setRetMessage("操作类型不正确！");
					break;
				}
				if (SysPramType.DELETE.getMsg().equals(opt)) {

					int i = sysNoticeCodingService.delSysNotice(sysNotice.getId());
					if (i > 0) {
						jsonResponse.setRetContent(i);
					}else{
						jsonResponse.setRetCode("");
					}
				} else if (SysPramType.UPDATE.getMsg().equals(opt)) {

					int i = sysNoticeCodingService.updSysNotice(sysNotice);
					if (i > 0) {
						jsonResponse.setRetContent(i);
					}else{
						jsonResponse.setRetCode("");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setException(new BusinessException("500", new Object[] {}));
			logger.error(e.getMessage());
		}

		return jsonResponse;
	}

	/**
	 * 添加
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/sysNoticeAdd")
	public ServiceResponse<SysNoticeVO> sysNoticeAdd(@RequestBody JsonRequest<SysNoticeVO> jsonRequest) {
		ServiceResponse<SysNoticeVO> jsonResponse = new ServiceResponse<SysNoticeVO>();
		SysNoticeVO vo = jsonRequest.getReqBody();
		try {
			logger.info("del 参数 = {}", JsonUtil.toJson(jsonRequest));
			SysNotice sysNotice = BeanCopyUtil.copy(vo, SysNotice.class);
			sysNoticeCodingService.addSysNotice(sysNotice);
			BeanCopyUtil.copy(sysNotice, vo);
			if (vo.getId() != null && vo.getId() > 0) {
				jsonResponse.setRetContent(vo);
			} else {
				jsonResponse.setRetCode("");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setException(new BusinessException("500", new Object[] {}));
			logger.error(e.getMessage());
		}

		return jsonResponse;

	}
	
	@RequestMapping("/deleteByPrimaryKeys")
	public ServiceResponse<Integer> deleteByPrimaryKeys(@RequestBody JsonRequest<Set<Long>> jsonRequest) {
		ServiceResponse<Integer> jsonResponse = new ServiceResponse<>();
		Set<Long> keys = jsonRequest.getReqBody();
		try {
			logger.info("del 参数 = {}", JsonUtil.toJson(jsonRequest));
			int i = sysNoticeCodingService.deleteByPrimaryKeys(keys);
			if (i > 0) {
				jsonResponse.setRetContent(i);
			} else {
				jsonResponse.setRetCode("");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setException(new BusinessException("500", new Object[] {}));
			logger.error(e.getMessage());
		}

		return jsonResponse;

	}
	
}
