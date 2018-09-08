package jq.steel.cs.services.base.facade.common;

/**
 * 常量code 码
 */
public class CommonConstant {

	// 返回值状态
	public static final String SUCCESS = "SUCCESS";

	public static final String ERROR = "ERROR";
	
	// -----------王宇添加的----------------
	// 企业名称不能为空
	public static final String ENT_NAME_NOT_NULL = "0701007";

	// 密码不能为空
	public static final String PASSADE_NOT_NULL = "0702000";

	// 统一社会信用代码不能为空
	public static final String SOCIAL_CREDIT_CODE_NOT_NULL = "0701008";

	// 用户名不能为空
	public static final String USER_NAME_NOT_NULL = "0701009";

	// 邮箱不能为空
	public static final String EMAIL_NOT_NULL = "0701010";

	// 企业名称已被占用
	public static final String ENT_NAME_OCCUPY = "0701001";

	// 统一社会信用代码已被占用
	public static final String SOCIAL_CREDIT_CODE_OCCUPY = "0701002";

	// 用户名已被占用
	public static final String USER_NAME_OCCUPY = "0701003";

	// 邮箱已被占用
	public static final String EMAIL_OCCUPY = "0701004";

	// 身份证号不能为空
	public static final String ID_NUMBER_NOT_NULL = "0701011";

	// 电话号不能为空
	public static final String TELEPHONE_NUMBER_NOT_NULL = "0701012";

	// 电话号已被占用
	public static final String TELEPHONE_NUMBER_OCCUPY = "0701006";

	// 身份证号已被占用
	public static final String ID_NUMBER_OCCUPY = "0701005";

	// 当前账号不存在
	public static final String ACCOUNT_IS_NULL = "0702001";

	// 当前账号已失效
	public static final String ACCOUNT_INVALID = "0702002";

	// 当前账号锁定中
	public static final String ACCOUNT_LOCK_IN = "0702003";

	// 密码输入错误!
	public static final String PASSWORD_ENTRY_ERROR = "0702004";

	// -----------陈杰添加的----------------
	// 发送失败
	public static final String SEND_FAIL = "0702007";

	// 验证失败
	public static final String VALIDATE_FAIL = "0702008";

	// 密码修改失败
	public static final String PASSWORD_MODIFY_FAIL = "0702009";

	// 密码重置失败
	public static final String PASSWORD_REST_FAIL = "0702010";

}
