package jq.steel.cs.services.base.facade.service.password.impl;

import com.ebase.core.StringHelper;
import com.ebase.core.cache.CacheService;
import com.ebase.utils.secret.AesUtil;
import com.ebase.utils.secret.Md5Util;
import jq.steel.cs.services.base.api.vo.MessageVO;
import jq.steel.cs.services.base.api.vo.UserInfoVO;
import jq.steel.cs.services.base.facade.common.CommonConstant;
import jq.steel.cs.services.base.facade.dao.AcctInfoMapper;
import jq.steel.cs.services.base.facade.model.AcctInfo;
import jq.steel.cs.services.base.facade.service.message.MessageService;
import jq.steel.cs.services.base.facade.service.password.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ChenJie
 */
@Service
public class PasswordServiceImpl implements PasswordService {

	@Autowired
	private AcctInfoMapper acctInfoMapper;

	@Autowired
	private MessageService messageService;

	@Autowired
	private CacheService cacheService;

	@Value("${mail.url}")
	private String mailUrl;

	@Override
	public UserInfoVO account(String userName) {
		UserInfoVO userInfoVO = new UserInfoVO();
		AcctInfo acctInfo = acctInfoMapper.selectByLogin(userName);
		if (acctInfo != null) {
			userInfoVO.setUserId(acctInfo.getAcctId());
			userInfoVO.setUserName(acctInfo.getAcctTitle());
			userInfoVO.setEmail(acctInfo.getEmail());
			return userInfoVO;
		}
		return null;
	}

	@Override
	public String send(Long userId, Byte status) {
		AcctInfo acctInfo = acctInfoMapper.selectByPrimaryKey(userId);

		MessageVO messageVO = new MessageVO();
		String code = CommonConstant.ERROR;
		try {
			if (acctInfo != null) {
				if ("0".equals(status.toString())) {
					messageVO.setDestination(acctInfo.getEmail());
					Map<String, Object> map = new HashMap<>();
					String uuid = UUID.randomUUID().toString().replace("-", "");
					String ids = acctInfo.getAcctId() + "&" + uuid;
					String encrypt = URLEncoder.encode(AesUtil.encrypt(ids, "good"), "UTF-8");
					String address = mailUrl + "/password/validate?valiURL=" + encrypt;
					map.put("address", address);
					messageVO.setVariables(map);
					String valUrl = cacheService.get(acctInfo.getAcctId().toString());
					if (valUrl == null) {
						cacheService.set(acctInfo.getAcctId().toString(), ids, 86400);
					} else {
						cacheService.delete(acctInfo.getAcctId().toString());
						cacheService.set(acctInfo.getAcctId().toString(), ids, 86400);
					}
					code = messageService.sendEmailWithAttachment("findPass","邮箱验证", messageVO, null, null);
				}
			} else {
				return CommonConstant.ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return CommonConstant.ERROR;
		}

		return code;
	}

	// 邮箱 链接验证
	@Override
	public String validate(String valiURL) {
		String decrypt = AesUtil.decrypt(valiURL, "good");
		String[] split = decrypt.split("&");
		String userId = "";
		if (split.length > 0) {
			userId = split[0];
		}
		String rediscode = cacheService.get(userId);
		try {
			if (rediscode != null && rediscode != "") {
				if (decrypt.equals(rediscode)) {
					cacheService.delete(userId);
					String uuid = UUID.randomUUID().toString().replace("-", "");
					String ids = userId + "&" + uuid;
					String encrypt = AesUtil.encrypt(ids, "good");
					cacheService.set(userId, ids);
					return encrypt;
				} else {
					return CommonConstant.ERROR;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return CommonConstant.ERROR;
		}
		return CommonConstant.ERROR;
	}

	@Override
	public String modifyPassword(String userName, String oldPassword, String password) {
		AcctInfo acctInfo = acctInfoMapper.selectByLogin(userName);
		if (acctInfo != null) {
			String findOldPass = acctInfo.getAcctPassword();
			if (findOldPass.equals(Md5Util.encrpt(oldPassword))) {
				acctInfo.setAcctPassword(Md5Util.encrpt(password));
				try {
					int updateStatus = acctInfoMapper.updateByPrimaryKeySelective(acctInfo);
					if (updateStatus == 1) {
						return CommonConstant.SUCCESS;
					}
					return CommonConstant.ERROR;
				} catch (Exception e) {
					e.printStackTrace();
					return CommonConstant.ERROR;
				}
			} else {
				return CommonConstant.ERROR;
			}
		} else {
			return CommonConstant.ERROR;
		}
	}

	@Override
	public String validateCode(Long userId, String valiCode) {
//		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
//		if (userInfo != null) {
//			String phoneNo = userInfo.getPhoneNo();
//			String phoneCode = cacheService.get(phoneNo);
//			if (StringHelper.isNotEmpty(phoneCode)) {
//				if (phoneCode.equals(valiCode)) {
//					cacheService.delete(phoneNo);
//					String uid = userInfo.getUserId().toString();
//					String uuid = UUID.randomUUID().toString().replace("-", "");
//					String ids = uid + "&" + uuid;
//					String encrypt = AesUtil.encrypt(ids, "good");
//					String value = cacheService.get(uid);
//					if (StringHelper.isNotEmpty(value)) {
//						cacheService.delete(uid);
//						cacheService.set(uid, ids);
//					} else {
//						cacheService.set(uid, ids);
//					}
//					return encrypt;
//				} else {
//					return CommonConstant.ERROR;
//				}
//			} else {
//				return CommonConstant.ERROR;
//			}
//		}
		return CommonConstant.ERROR;
	}

	@Override
	public String restPassword(String encrypt, String password) {
		try {
			String decrypt = AesUtil.decrypt(encrypt, "good");
			String[] split = decrypt.split("&");
			String userId = "";
			if (split.length > 0) {
				userId = split[0];
			}
			if (StringHelper.isNotEmpty(userId)) {
				String value = cacheService.get(userId);
				if (StringHelper.isNotEmpty(value) && value.equals(decrypt)) {
					AcctInfo acctInfo = acctInfoMapper.selectByPrimaryKey(Long.parseLong(userId));
					if (acctInfo != null) {
						acctInfo.setAcctPassword(Md5Util.encrpt(password));
						int updateStatus = acctInfoMapper.updateByPrimaryKeySelective(acctInfo);
						if (updateStatus == 1) {
							cacheService.delete(userId);
							return CommonConstant.SUCCESS;
						}
					}
				} else {
					return CommonConstant.ERROR;
				}
			} else {
				return CommonConstant.ERROR;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			return CommonConstant.ERROR;
		}
		return CommonConstant.ERROR;
	}

}
