package jq.steel.cs.services.base.facade.controller.sysbasics;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.page.PageDTO;
import com.ebase.core.page.PageDTOUtil;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.JsonUtil;
import jq.steel.cs.services.base.api.vo.SysDescriptionVO;
import jq.steel.cs.services.base.facade.common.SysPramType;
import jq.steel.cs.services.base.facade.model.SysDescription;
import jq.steel.cs.services.base.facade.service.sysbasics.SysDescriptionCodingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by MrLi on 2018/7/23.
 */
@RestController
@RequestMapping("/SysDescription")
public class SysDescriptionCodingController {
	private static Logger logger = LoggerFactory.getLogger(SysDescriptionCodingController.class);

	@Autowired
	private SysDescriptionCodingService sysDescriptionCodingService;

	/**
	 * 系统参数 list 接口
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/sysDescriptionList")
	public ServiceResponse<PageDTO<SysDescriptionVO>> listSysDescription(
			@RequestBody JsonRequest<SysDescriptionVO> jsonRequest) {
		ServiceResponse<PageDTO<SysDescriptionVO>> jsonResponse = new ServiceResponse<>();
		try {
			logger.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
			SysDescriptionVO vo = jsonRequest.getReqBody();
			PageDTOUtil.startPage(vo.getPageNum(),vo.getPageSize());
			List<SysDescription> sysDescriptions = sysDescriptionCodingService.listSysDescription(vo.getKeyword(),
					Long.parseLong(vo.getStatus()));
			List<SysDescriptionVO> sysDescriptionVOs = BeanCopyUtil.copyList(sysDescriptions, SysDescriptionVO.class);
			
			PageDTO<SysDescriptionVO> pages = PageDTOUtil.transform(sysDescriptionVOs);
			jsonResponse.setRetContent(pages);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setException(new BusinessException("500", new Object[] {}));
			logger.error(e.getMessage());
		} finally {
			PageDTOUtil.endPage();
		}
		return jsonResponse;
	}

	/**
	 * 系统参数 保存 或者删除 或者修改接口
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/sysDescriptionKeep")
	public ServiceResponse<Integer> keepSysDescription(@RequestBody JsonRequest<List<SysDescriptionVO>> jsonRequest) {
		ServiceResponse<Integer> jsonResponse = new ServiceResponse<>();
		List<SysDescriptionVO> vos = jsonRequest.getReqBody();
		List<SysDescription> sysDescriptions = BeanCopyUtil.copyList(vos, SysDescription.class);
		try {
			logger.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
			if (CollectionUtils.isEmpty(sysDescriptions)) {
				jsonResponse.setRetCode("");
				return jsonResponse;
			}
			for (SysDescription sysDescription : sysDescriptions) {

				String opt = sysDescription.getOpt();

				if (StringUtils.isEmpty(opt)) {
					jsonResponse.setRetCode("");
					break;
				}
				// System.out.print(SysPramType.INSERT.getMsg());
				if (SysPramType.DELETE.getMsg().equals(opt)) {

					int i = sysDescriptionCodingService.delSysDescription(sysDescription.getId());
					if (i > 0) {
						jsonResponse.setRetContent(i);
					}else{
						jsonResponse.setRetCode("");
					}
				} else if (SysPramType.UPDATE.getMsg().equals(opt)) {

					int i = sysDescriptionCodingService.updSysDescription(sysDescription);
					if (i > 0) {
						jsonResponse.setRetContent(i);
					}else{
						jsonResponse.setRetCode("");
					}
				} else if (SysPramType.INSERT.getMsg().equals(opt)) {
					sysDescription.setCreatedTime(new Date());
					sysDescription.setUpdatedTime(new Date());
					int i = sysDescriptionCodingService.addSysDescription(sysDescription);
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
}
