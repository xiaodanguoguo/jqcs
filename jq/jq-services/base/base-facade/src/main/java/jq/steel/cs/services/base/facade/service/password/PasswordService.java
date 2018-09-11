package jq.steel.cs.services.base.facade.service.password;


import jq.steel.cs.services.base.api.vo.UserInfoVO;

/**
 * @author ChenJie
 */
public interface PasswordService {
	UserInfoVO account(String userName);

	String send(Long userId, Byte status);

	String validate(String valiURL);

	String validateCode(Long userId, String valiCode);

	String modifyPassword(String userName, String oldPassword, String password);

	String restPassword(String encrypt, String password);

}
