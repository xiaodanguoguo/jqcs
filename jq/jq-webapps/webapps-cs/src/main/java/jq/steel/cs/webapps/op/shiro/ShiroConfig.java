package jq.steel.cs.webapps.op.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.ebase.core.service.ServiceResponse;
import jq.steel.cs.services.base.api.controller.FunctionManageAPI;
import jq.steel.cs.services.base.api.vo.FunctionManageVO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * Created by zhengpeng on 2018/9/6.
 * 权限配置类
 */
@Configuration
public class ShiroConfig {


    @Autowired
    private FunctionManageAPI functionManageAPI;



//    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager){
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        //登录接口
        shiroFilterFactoryBean.setLoginUrl("/acct/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/usersPage");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/font-awesome/**","anon");
        //filterChainDefinitionMap.put("/acct/login","anon");


        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        //自定义加载权限资源关系
//        List<Resources> resourcesList = resourcesService.queryAll();
//        for(Resources resources:resourcesList){
//
//            if (StringUtil.isNotEmpty(resources.getResurl())) {
//                String permission = "perms[" + resources.getResurl()+ "]";
//                filterChainDefinitionMap.put(resources.getResurl(),permission);
//            }
//        }

        //List allFunctionManager = allFunctionManageList();

        filterChainDefinitionMap.put("/**", "authc");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

        //return shiroFilterFactoryBean;
    }


//    @Bean
    public SecurityManager securityManager(){
        System.out.println("ShiroConfiguration.securityManager()");
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setRealm(myShiroRealm());
        // 自定义缓存实现 使用redis
        //securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        //securityManager.setSessionManager(sessionManager());
        return securityManager;
    }


//    @Bean
    public JqShiroRealm myShiroRealm(){
        System.out.println("ShiroConfiguration.myShiroRealm()");
        JqShiroRealm myShiroRealm = new JqShiroRealm();
        //myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

//    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }


//    private List allFunctionManageList(){
//        List dataList = new ArrayList<>();
//
//        FunctionManageVO manageVO = new FunctionManageVO();
//        ServiceResponse<HashMap> response =  functionManageAPI.functionManageList(manageVO);
//
//        return dataList;
//    }

}
