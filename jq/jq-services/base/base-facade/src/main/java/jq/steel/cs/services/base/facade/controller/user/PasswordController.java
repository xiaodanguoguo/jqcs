package jq.steel.cs.services.base.facade.controller.user;

import com.ebase.core.exception.BusinessException;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonResponse;
import jq.steel.cs.services.base.api.vo.UserInfoVO;
import jq.steel.cs.services.base.facade.common.CommonConstant;
import jq.steel.cs.services.base.facade.service.password.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @authorChenjie
 * @密码找回模块
 */

@RestController
@RequestMapping("/password")
public class PasswordController {

	private static Logger logger = LoggerFactory.getLogger(PasswordController.class);

	@Autowired
	private PasswordService passwordService;

	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public ServiceResponse<UserInfoVO> account(@RequestBody UserInfoVO userInfoVO) {
		ServiceResponse<UserInfoVO> response = new ServiceResponse<>();
		try {
			UserInfoVO User = passwordService.account(userInfoVO.getUserName());
			if (User != null) {
				response.setRetContent(User);
			} else {
				response.setRetCode(CommonConstant.ACCOUNT_IS_NULL);
				response.setRetMessage("账号不存在");
			}
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			response.setRetCode("500");
			response.setRetMessage("系统异常");
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	ServiceResponse<String> send(@RequestBody UserInfoVO userInfoVO) {
		ServiceResponse<String> response = new ServiceResponse<>();
		try {
			String sendStatus = passwordService.send(userInfoVO.getUserId(), userInfoVO.getStatus());
			if (CommonConstant.SUCCESS.equals(sendStatus)) {
				response.setRetContent(sendStatus);
				response.setRetMessage("发送成功");
			} else {
				response.setRetCode(CommonConstant.SEND_FAIL);
				response.setRetMessage("发送失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			response.setRetCode(JsonResponse.SYS_EXCEPTION);
		}
		return response;
	}

	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	ServiceResponse<String> validate(@RequestBody UserInfoVO userInfoVO) {
		ServiceResponse<String> response = new ServiceResponse<>();

		try {
			String valiResult = passwordService.validate(userInfoVO.getValiURL());
			if (!CommonConstant.ERROR.equals(valiResult)) {
				response.setRetContent(valiResult);
				response.setRetMessage("验证成功");
			} else {
				response.setRetCode(CommonConstant.VALIDATE_FAIL);
				response.setRetMessage("验证失败");
			}
			response.setRetContent(valiResult);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			response.setRetCode(JsonResponse.SYS_EXCEPTION);
		}
		return response;
	}

	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	ServiceResponse<String> modifyPassword(@RequestBody UserInfoVO userInfoVO) {
		ServiceResponse<String> response = new ServiceResponse<>();

		try {
			String modifyResult = passwordService.modifyPassword(userInfoVO.getUserName(), userInfoVO.getOldPassword(),
					userInfoVO.getPassword());
			if (CommonConstant.SUCCESS.equals(modifyResult)) {
				response.setRetContent(modifyResult);
				response.setRetMessage("修改成功");
			} else {
				response.setRetContent(modifyResult);
				response.setRetCode(CommonConstant.PASSWORD_MODIFY_FAIL);
				response.setRetMessage("修改失败");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			response.setRetCode(JsonResponse.SYS_EXCEPTION);
		}
		return response;
	}

	@RequestMapping(value = "/restPassword", method = RequestMethod.POST)
	ServiceResponse<String> restPassword(@RequestBody UserInfoVO userInfoVO) {
		ServiceResponse<String> response = new ServiceResponse<>();
		try {
			String restPassStatus = passwordService.restPassword(userInfoVO.getEncrypt(), userInfoVO.getPassword());
			if (CommonConstant.SUCCESS.equals(restPassStatus)) {
				response.setRetContent(restPassStatus);
				response.setRetMessage("重置密码成功");
			} else {
				response.setRetContent(restPassStatus);
				response.setRetCode(CommonConstant.PASSWORD_REST_FAIL);
				response.setRetMessage("重置密码失败");
			}
			response.setRetContent(restPassStatus);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			response.setRetCode(JsonResponse.SYS_EXCEPTION);
		}
		return response;
	}

	@RequestMapping(value = "/validateCode", method = RequestMethod.POST)
	ServiceResponse<String> validateCode(@RequestBody UserInfoVO userInfoVO) {
		ServiceResponse<String> response = new ServiceResponse<>();
		try {
			String valiResult = passwordService.validateCode(userInfoVO.getUserId(), userInfoVO.getValiCode());
			if (!CommonConstant.ERROR.equals(valiResult)) {
				response.setRetContent(valiResult);
				response.setRetMessage("验证成功");
			} else {
				response.setRetCode(CommonConstant.VALIDATE_FAIL);
				response.setRetMessage("验证失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
