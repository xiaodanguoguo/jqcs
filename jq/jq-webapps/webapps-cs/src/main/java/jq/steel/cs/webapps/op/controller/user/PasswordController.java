package jq.steel.cs.webapps.op.controller.user;

import com.ebase.core.StringHelper;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.core.web.json.JsonResponse;
import feign.FeignException;
import jq.steel.cs.services.base.api.controller.PasswordAPI;
import jq.steel.cs.services.base.api.vo.UserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ChenJie 密码找回模块
 */
@RestController
@RequestMapping("/password")
public class PasswordController {

	private final static Logger logger = LoggerFactory.getLogger(PasswordController.class);

	@Autowired
	private PasswordAPI passwordAPI;

	@Autowired
	private HttpServletResponse httpServletResponse;

	@Value("${pass.url}")
	private String passUrl;

	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public JsonResponse<UserInfoVO> account(@RequestBody JsonRequest<UserInfoVO> jsonRequest) {
		JsonResponse<UserInfoVO> result = new JsonResponse<>();
		try {
			if (StringHelper.isNotEmpty(jsonRequest.getReqBody().getUserName())) {
				ServiceResponse<UserInfoVO> response = passwordAPI.account(jsonRequest.getReqBody());
				if (response.getRetContent() != null) {
					result.setRspBody(response.getRetContent());
				} else {
					result.setRetCode(response.getRetCode());
					result.setRetDesc(response.getRetMessage());
				}
			} else {
				result.setRetCode("0200001");
				result.setRetDesc("参数不合法");
			}
		} catch (FeignException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setRetCode(JsonResponse.SYS_EXCEPTION);
		}
		return result;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public JsonResponse<String> send(@RequestBody JsonRequest<UserInfoVO> jsonRequest) {
		JsonResponse<String> result = new JsonResponse<>();
		UserInfoVO userInfoVO = jsonRequest.getReqBody();
		if (userInfoVO != null) {
			if ((StringHelper.isNotEmpty(userInfoVO.getUserId().toString()))
					&& (StringHelper.isNotEmpty(userInfoVO.getStatus().toString()))) {
				try {
					ServiceResponse<String> response = passwordAPI.send(jsonRequest.getReqBody());
					if ("SUCCESS".equals(response.getRetContent())) {
						result.setRspBody(response.getRetContent());
						result.setRetDesc(response.getRetMessage());
					} else {
						response.setRetCode("0702007");
						response.setRetMessage("发送失败");
					}
				} catch (FeignException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
					result.setRetCode(JsonResponse.SYS_EXCEPTION);
					result.setRetDesc("系统异常");
				}
			} else {
				result.setRetCode("0200001");
				result.setRetDesc("参数不合法");
			}
		} else {
			result.setRetCode("0200001");
			result.setRetDesc("参数不合法");
		}
		return result;
	}

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public JsonResponse<String> validate(String valiURL) {
		JsonResponse<String> result = new JsonResponse<>();
		try {
			if (StringHelper.isNotBlank(valiURL)) {
				UserInfoVO userInfoVO = new UserInfoVO();
				userInfoVO.setValiURL(valiURL);
				ServiceResponse<String> valiResponse = passwordAPI.validate(userInfoVO);
				if (!"ERROR".equals(valiResponse.getRetContent())) {
					result.setRspBody(valiResponse.getRetContent());
					result.setRetDesc(valiResponse.getRetMessage());
					String encrypt = valiResponse.getRetContent();
					System.out.println(encrypt);
					String address = passUrl
							+ "/project/login/html-gulp-www/forgetEdit.html?skipHtml=1&encrypt=" + encrypt;
					httpServletResponse.sendRedirect(address);
				} else {
					String address = passUrl + "/project/login/html-gulp-www/forgetPast.html";
					httpServletResponse.sendRedirect(address);
					result.setRspBody(valiResponse.getRetContent());
					result.setRetCode(valiResponse.getRetCode());
					result.setRetDesc(valiResponse.getRetMessage());
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setRetCode(JsonResponse.SYS_EXCEPTION);
		}
		return result;
	}

	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	public JsonResponse<String> modifyPassword(@RequestBody JsonRequest<UserInfoVO> jsonRequest) {
		JsonResponse<String> result = new JsonResponse<>();
		UserInfoVO userInfoVO = jsonRequest.getReqBody();
		try {
			String userName = userInfoVO.getUserName();
			String oldPassword = userInfoVO.getOldPassword();
			String password = userInfoVO.getPassword();
			if ((StringHelper.isNotEmpty(userName)) && (StringHelper.isNotEmpty(oldPassword))
					&& ((StringHelper.isNotEmpty(password)))) {
				ServiceResponse<String> modifyResult = passwordAPI.modifyPassword(userInfoVO);
				if ("SUCCESS".equals(modifyResult.getRetContent())) {
					result.setRspBody(modifyResult.getRetContent());
					result.setRetDesc(modifyResult.getRetMessage());
				} else {
					result.setRspBody(modifyResult.getRetContent());
					result.setRetCode(modifyResult.getRetCode());
					result.setRetDesc(modifyResult.getRetMessage());
				}
			} else {
				result.setRetCode("0200001");
				result.setRetDesc("参数不合法");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setRetCode(JsonResponse.SYS_EXCEPTION);
		}
		return result;
	}

	@RequestMapping(value = "/restPassword", method = RequestMethod.POST)
	public JsonResponse<String> restPassword(@RequestBody JsonRequest<UserInfoVO> jsonRequest) {
		JsonResponse<String> result = new JsonResponse<>();

		UserInfoVO userInfoVO = jsonRequest.getReqBody();
		try {
			String encrypt = userInfoVO.getEncrypt();
			String password = userInfoVO.getPassword();

			if ((StringHelper.isNotEmpty(encrypt)) && (StringHelper.isNotEmpty(password))) {
				ServiceResponse<String> restPassword = passwordAPI.restPassword(userInfoVO);
				if (restPassword.getRetContent().equals("SUCCESS")) {
					result.setRspBody(restPassword.getRetContent());
					result.setRetDesc(restPassword.getRetMessage());
				} else {
					result.setRspBody(restPassword.getRetContent());
					result.setRetCode(restPassword.getRetCode());
					result.setRetDesc(restPassword.getRetMessage());
				}
			} else {
				result.setRspBody("ERROR");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/validateCode", method = RequestMethod.POST)
	public JsonResponse<String> validateCode(@RequestBody JsonRequest<UserInfoVO> jsonRequest) {
		JsonResponse<String> result = new JsonResponse<>();
		UserInfoVO userInfoVO = jsonRequest.getReqBody();
		try {
			String userId = userInfoVO.getUserId().toString();
			String valiCode = userInfoVO.getValiCode();
			if (StringHelper.isNotEmpty(userId) && StringHelper.isNotEmpty(valiCode)) {
				ServiceResponse<String> validateResponse = passwordAPI.validateCode(userInfoVO);
				if (!"ERROR".equals(validateResponse.getRetContent())) {
					result.setRspBody(validateResponse.getRetContent());
					result.setRetDesc(validateResponse.getRetMessage());
				} else {
					result.setRspBody(validateResponse.getRetContent());
					result.setRetCode(validateResponse.getRetCode());
					result.setRetDesc(validateResponse.getRetMessage());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

}
