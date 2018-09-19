package jq.steel.cs.services.base.facade.service.user.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.ebase.core.MD5Util;
import com.ebase.core.StringHelper;
import com.ebase.core.cache.CacheService;
import com.ebase.core.service.ServiceResponse;
import com.ebase.core.session.Acct;
import com.ebase.core.session.AcctLogin;
import com.ebase.core.session.AcctSession;
import com.ebase.core.session.CacheKeyConstant;
import com.ebase.utils.BeanCopyUtil;
import com.ebase.utils.StringUtil;
import com.ebase.utils.math.MathHelper;
import com.ebase.utils.secret.base64.Base64Util;
import jq.steel.cs.services.base.api.vo.AcctInfoVO;
import jq.steel.cs.services.base.api.vo.FunctionManageVO;
import jq.steel.cs.services.base.api.vo.MessageVO;
import jq.steel.cs.services.base.facade.common.IsDelete;
import jq.steel.cs.services.base.facade.common.Status;
import jq.steel.cs.services.base.facade.dao.AcctInfoMapper;
import jq.steel.cs.services.base.facade.dao.OrgInfoMapper;
import jq.steel.cs.services.base.facade.model.AcctInfo;
import jq.steel.cs.services.base.facade.model.OrgInfo;
import jq.steel.cs.services.base.facade.service.message.MessageService;
import jq.steel.cs.services.base.facade.service.sysbasics.FunctionManageService;
import jq.steel.cs.services.base.facade.service.user.AcctService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wangyu
 */
@Service
public class AcctServiceImpl implements AcctService {

    private static Logger LOG = LoggerFactory.getLogger(AcctServiceImpl.class);

    private static String AUDIT_CUST = "100";

    private static String CODE = "jgzc";

    private static String TITLE = "酒钢客服系统注册成功通知";

    private static String VERSION = "1";


    private static Integer TIME_EXPIRE = 60 * 60; //30分钟

    @Autowired
    private AcctInfoMapper acctInfoMapper;

    @Autowired
    private OrgInfoMapper orgInfoMapper;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private FunctionManageService functionManageService;

//    @Autowired
//    private OrgInfoTypeMapper orgInfoTypeMapper;

    @Override
    @Transactional
    public ServiceResponse<String> userRegister(AcctInfoVO acctInfoVO) {
        ServiceResponse<String> serviceResponse = new ServiceResponse<>();

        AcctInfoVO vo = new AcctInfoVO();
        vo.setAcctTitle(acctInfoVO.getAcctTitle());
        vo.setEmail(acctInfoVO.getEmail());
        vo.setMobilePhone(acctInfoVO.getMobilePhone());
        List<AcctInfo> acctInfos = acctInfoMapper.find(vo);

        OrgInfo orgInfo = new OrgInfo();
        orgInfo.setOrgName(acctInfoVO.getCompanyName());
        orgInfo.setTel(acctInfoVO.getMobilePhone());
        List<OrgInfo> orgInfos = orgInfoMapper.queryExistsOrgInfo(orgInfo);

        for (AcctInfo a : acctInfos) {

            if (acctInfoVO.getAcctTitle().equals(a.getEmail()) ||
                    acctInfoVO.getAcctTitle().equals(a.getAcctTitle()) ||
                        acctInfoVO.getAcctTitle().equals(a.getMobilePhone())) {
                serviceResponse.setRetCode("500");
                serviceResponse.setRetMessage("账号已存在");
                return serviceResponse;
            }

            if (acctInfoVO.getMobilePhone().equals(a.getMobilePhone())) {
                serviceResponse.setRetCode("500");
                serviceResponse.setRetMessage("电话号已被使用");
                return serviceResponse;
            }

            if (acctInfoVO.getEmail().equals(a.getEmail())) {
                serviceResponse.setRetCode("500");
                serviceResponse.setRetMessage("邮箱已存在");
                return serviceResponse;
            }

        }

        for (OrgInfo o : orgInfos) {
            if (acctInfoVO.getCompanyName().equals(o.getOrgName())) {
                serviceResponse.setRetCode("500");
                serviceResponse.setRetMessage("公司名称已存在");
                return serviceResponse;
            }

            if (acctInfoVO.getMobilePhone().equals(o.getTel())) {
                serviceResponse.setRetCode("500");
                serviceResponse.setRetMessage("电话号已被使用");
                return serviceResponse;
            }
        }

        //加密一下
        acctInfoVO.setAcctPassword(MD5Util.encrypByMd5(acctInfoVO.getAcctPassword()));

        //保存用户表的信息 和 客户表
        AcctInfo acctInfo = BeanCopyUtil.copy(acctInfoVO,AcctInfo.class);

        Long code = orgInfoMapper.getCode();
        String orgCode = code.toString();
        int zeroLength = 0;
        if(orgCode.length() < 9) {
            zeroLength = 9 - orgCode.length();
        }
        while (zeroLength > 0) {
            orgCode = "0" + orgCode;
            zeroLength--;
        }

        orgInfo = new OrgInfo();
        orgInfo.setOrgCode("C100" + orgCode);
        orgInfo.setId(AUDIT_CUST + orgCode);
        orgInfo.setOrgName(acctInfoVO.getCompanyName());
        orgInfo.setParentId(AUDIT_CUST);
        orgInfo.setStatus(Status.HOLD_AUDIT.getCode());
        orgInfo.setEmail(acctInfoVO.getEmail());
        orgInfo.setTel(acctInfoVO.getMobilePhone());
        orgInfo.setAddr(acctInfoVO.getAddress());
        Long i = orgInfoMapper.insertOrgInfo(orgInfo);

        acctInfo.setoInfoId(AUDIT_CUST + orgCode);
        acctInfo.setAcctType(1L);//  管理员
        acctInfo.setStatus(Byte.valueOf(Status.HOLD_AUDIT.getCode()));
        acctInfo.setName(acctInfoVO.getCompanyName());
        int count =  acctInfoMapper.insertSelective(acctInfo);

        // 发邮件
        MessageVO messageVO = new MessageVO();
        messageVO.setDestination(acctInfoVO.getEmail());
        Map<String, Object> map = new HashMap<>();
        map.put("username", acctInfoVO.getAcctTitle());
        messageVO.setVariables(map);

        String content = messageService.getRegisterContent(CODE, TITLE, messageVO);
        serviceResponse.setResponseCode(ServiceResponse.SUCCESS_CODE);

        return serviceResponse;
    }

    /**
     * 用户登录
     * @param acctLogin
     * @return
     */
    @Override
    public ServiceResponse<AcctSession> userLogin(AcctLogin acctLogin) {

        ServiceResponse<AcctSession> response = new ServiceResponse<AcctSession>();

        try{
            //校验用户信息
            if(StringUtils.isEmpty(acctLogin.getAcctId())){
                response.setResponseCode("0701009");
                return response;
            }
            if(StringUtils.isEmpty(acctLogin.getPassword())){
                response.setResponseCode("0702000");
                return response;
            }
            //根据账号名查询账号对象
            AcctInfo acctInfo = acctInfoMapper.selectByLogin(acctLogin.getAcctId());

            if (null == acctInfo) {
                response.setResponseCode("0701001");
                response.setRetMessage("该账号不存在");
                return response;
            }

            if (acctInfo.getStatus().toString().equals(Status.HOLD_AUDIT.getCode())) {
                response.setResponseCode("0701002");
                response.setRetMessage("账号待审核失败，该账户不可用");
                return response;
            }

            if (acctInfo.getStatus().toString().equals(Status.NOT_PASS.getCode())) {
                response.setResponseCode("0701003");
                response.setRetMessage("账号审核失败，该账户不可用");
                return response;
            }

            if (acctInfo.getIsDelete().toString().equals(IsDelete.YES.getCode())) {
                response.setResponseCode("0701004");
                response.setRetMessage("账号已被禁用，该账户不可用");
                return response;
            }

            if (acctInfo.getStatus().toString().equals(Status.STOP.getCode())) {
                response.setResponseCode("0701005");
                response.setRetMessage("账号未激活，该账户不可用");
                return response;
            }

            OrgInfo record = new OrgInfo();
            record.setId(acctInfo.getoInfoId());
            OrgInfo orgInfo = orgInfoMapper.selectOrgInfo(record);
            //验证两次密码是否一致
            String password = MD5Util.encrypByMd5(acctLogin.getPassword());
            if(acctInfo.getAcctPassword().equals(password)){
                //登录成功
                Acct acct = BeanCopyUtil.copy(acctInfo, Acct.class);
                acct.setOrgId(orgInfo.getId());
                acct.setoInfoName(orgInfo.getOrgName());
                acct.setOrgType(orgInfo.getOrgType());
                acct.setOrgCode(orgInfo.getOrgCode());

                if (StringUtil.isNotEmpty(orgInfo.getSapCode()) && StringUtil.isNotEmpty(acct.getOrgCode())) {
                    acct.setOrgCode(orgInfo.getSapCode());
                }

                AcctSession acctSession = loginSuccess(acct,acctLogin);

                response.setRetContent(acctSession);
            }else{
                response.setResponseCode("0702004");
                return response;
            }


        }catch (Exception e){
            LOG.error("error = {}",e);
            e.printStackTrace();
        }
        return response;

    }

    @Override
    public AcctInfoVO getAcct(String acctName) {
        AcctInfo acctInfo = acctInfoMapper.selectByLogin(acctName);


        return BeanCopyUtil.copy(acctInfo,AcctInfoVO.class);
    }

//    public OrgInfoType menuChildRole(OrgInfoType orgInfoType){
//        orgInfoType.setOrgId(orgInfoType.getParentId());
//        orgInfoType=orgInfoTypeMapper.selectOrgFather(orgInfoType);
//        if (!org.springframework.util.StringUtils.isEmpty(orgInfoType.getOrgType())) {
//            if(orgInfoType.getOrgType().equals(Long.parseLong("1"))){
//                return orgInfoType;
//            }else{
//                orgInfoType=menuChildRole(orgInfoType);
//            }
//        }else{
//            orgInfoType=menuChildRole(orgInfoType);
//        }
//
//        return orgInfoType;
//    }

    @Override
    public Map<String, String> getAcctAuth(String acctId){
        String authKey = CacheKeyConstant.ACCT_AUTH_ID+acctId;

        Map<String, String> map =  cacheService.getObject(authKey, Map.class);
        if(null == map){
            System.err.println("----------------授权id缓存无效-------------------");
            FunctionManageVO manageVO = new FunctionManageVO();
            manageVO.setAcctId(acctId);

            List<FunctionManageVO> list = functionManageService.getUserfunctionList(manageVO);
            map = new HashMap<>();
            for(FunctionManageVO manage : list){
                map.put(String.valueOf(manage.getFunctionId()),String.valueOf(manage.getFunctionId()));
            }
            cacheService.set(authKey, map, TIME_EXPIRE);
        }

        return map;
    }

    @Override
    public Map<String, String> getAcctAuthPath(String acctId){
        String authKey = CacheKeyConstant.ACCT_AUTH_PATH+acctId;

        Map<String, String> map =  cacheService.getObject(authKey, Map.class);
        if(null == map){
            System.err.println("----------------授权路径缓存无效-------------------");
            FunctionManageVO manageVO = new FunctionManageVO();
            manageVO.setAcctId(acctId);

            List<FunctionManageVO> list = functionManageService.getUserfunctionList(manageVO);
            map = new HashMap<>();
            for(FunctionManageVO manage : list){
                map.put(manage.getFunctionPath(), manage.getFunctionPath());
            }
            cacheService.set(authKey, map, TIME_EXPIRE);
        }

        return map;
    }


    private AcctSession loginSuccess(Acct acct, AcctLogin acctLogin) {

        AcctSession acctSession = new AcctSession();
        acctSession.setAcct(acct);

        //生成key
        String key = CacheKeyConstant.ACCT_SESSION + acctLogin.getSessionId() + acctLogin.getClientType();
//        String key = CacheKeyConstant.ACCT_SESSION + acctLogin.getSessionId();
        System.err.println("--------redis login cache key --------"+key+"--------------");


        // 用户权限
        FunctionManageVO functionManageVO = new FunctionManageVO();
        functionManageVO.setAcctId(String.valueOf(acct.getAcctId()));
        List<FunctionManageVO> authList = functionManageService.getUserfunctionList(functionManageVO);
        String authKey = CacheKeyConstant.ACCT_AUTH_ID+acct.getAcctId();

        if (CollectionUtils.isNotEmpty(authList)) {
            Map<String, String> authMap = new HashMap<>();
            Map<String, String> authMapPath = new HashMap<>();
            List<String> limitCode = new ArrayList<>();
            for(FunctionManageVO manageVO : authList){
                authMap.put(manageVO.getFunctionCode(), manageVO.getFunctionCode());
                authMapPath.put(manageVO.getFunctionPath(), manageVO.getFunctionPath());
                limitCode.add(manageVO.getFunctionCode());
            }

            acctSession.getAcct().setLimitCode(limitCode);
            acctSession.getAcct().setAuthMap(authMap);
            acctSession.getAcct().setAuthMapPath(authMapPath);
            acctSession.getAcct().setLimitCode(limitCode);
        } else {
            acctSession.getAcct().setLimitCode(new ArrayList<>());
        }


        //保存到 session 中 60 分钟
        cacheService.set(key,acctSession,TIME_EXPIRE);

        acctSession.setSessionId(Base64Util.encode(acctLogin.getSessionId() + acctLogin.getClientType()));

        return acctSession;

    }

    /**
     * 查询指定长度的id最大值
     * 如果存在加一，不存在就将父节点的parentId后面拼上101
     * 最高节点parentId为0
     * @return
     */
    private String getOrgInfoId(String orgCode) {
        StringBuilder result = new StringBuilder(orgCode);
        String orgInfoId = orgInfoMapper.getOrgInfoId(orgCode);
        if (StringHelper.isBlank(orgInfoId))
            orgInfoId = result.append("101").toString();
        else
            orgInfoId = MathHelper.add(orgInfoId);
        return orgInfoId;
    }
}
