package jq.steel.cs.webapps.cs.shiro;

import java.util.*;

import jq.steel.cs.services.base.api.controller.AcctAPI;
import jq.steel.cs.services.base.api.vo.AcctInfoVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebase.core.StringHelper;

/**
 * Created by yangqj on 2017/4/21.
 */
public class JqShiroRealm extends AuthorizingRealm {

	public static final String USER_SESSION_KEY = "userSessionKey";
    @Autowired
    private AcctAPI acctAPI;

    /**
     * 默认premission字符串 
     */  
    //public static final String PREMISSION_STRING = "perms[\"{0}\"]";
    
    /**
     * 授权
     * 通过当前登录用户获取
     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        AcctInfoVO user = (AcctInfoVO) SecurityUtils.getSubject().getPrincipal();//User{id=1, username='admin', password='3ef7164d1f6167cb9f2658c07d3c2f0a', enable=1}
////        Map<String,Object> map = new HashMap<String,Object>();
////        map.put("userid",user.getId());
////        List<Resources> resourcesList = resourcesService.loadUserResources(map);
////        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
////
////        for(Resources resources: resourcesList){
////            info.addStringPermission(resources.getResurl());
////        }
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        return info;
//    }


    //url授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AcctInfoVO acct = (AcctInfoVO) SecurityUtils.getSubject().getPrincipal();//User{id=1, username='admin', password='3ef7164d1f6167cb9f2658c07d3c2f0a', enable=1}
        Map<String,Object> map = new HashMap<String,Object>();
        //map.put("userid",user.getId());
        //List<Resources> resourcesList = resourcesService.loadUserResources(map);
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //for(Resources resources: resourcesList){
        //    info.addStringPermission(resources.getResurl());
        // }
        return info;
    }

    //权限认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        String acctName = (String) token.getPrincipal();
        if (StringHelper.isBlank(acctName)){
			//TODO 抛账号错误异常
			throw new UnknownAccountException();
		}
        AcctInfoVO acct = acctAPI.getAcct(acctName).getRetContent();
		if (acct == null){
			throw new UnknownAccountException();
		}

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		acct, //用户
        		acct.getAcctPassword(),
                ByteSource.Util.bytes(acctName),
                getName()  //realm name
        );
        //登录成功，信息放入shiro session
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(USER_SESSION_KEY, acct);

        return authenticationInfo;
    }




}
