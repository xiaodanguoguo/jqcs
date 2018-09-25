package com.ebase.core;

import com.ebase.core.session.UserSession;

import java.util.List;
import java.util.Map;

/**
 * @Auther: wangyu
 */
public class AssertContext {


    private static ThreadLocal<UserSession> allContext = new ThreadLocal<UserSession>();

    public static void init(UserSession userSession) {
        allContext.set(userSession);
    }

    public static UserSession get() {
        return allContext.get();
    }

    /**
     * 账号id
     * @return
     */
    public static String getAcctId() {
        UserSession userSession = get();
        if (userSession != null) {
            return userSession.getUser().getAcctId();
        }
        return null;
    }

    /**
     * 账号名称
     * @return
     */
    public static String getAcctName() {
        UserSession userSession = get();
        if (userSession != null) {
            return userSession.getUser().getAcctName();
        }
        return null;
    }

    /**
     * 账号类型
     * @return
     */
    public static String getAcctType() {
        UserSession userSession = get();
        if (userSession != null) {
            return userSession.getUser().getAcctType();
        }
        return null;
    }

    /**
     * 用户名称
     * @return
     */
    public static String getUserName(){
        UserSession userSession = get();
        if (userSession != null) {
            return userSession.getUser().getUserName();
        }
        return null;
    }

    /**
     * 组织id
     * @return
     */
    public static String getOrgId(){
        UserSession userSession = get();
        if (userSession != null) {
            return userSession.getUser().getOrgId();
        }
        return null;
    }

    /**
     * 组织编码
     * @return
     */
    public static String getOrgCode(){
        UserSession userSession = get();
        if (userSession != null) {
            return userSession.getUser().getOrgCode();
        }
        return null;
    }

    /**
     * 组织名称
     * @return
     */
    public static String getOrgName(){
        UserSession userSession = get();
        if (userSession != null) {
            return userSession.getUser().getOrgName();
        }
        return null;
    }

    /**
     * 权限路径
     * @return
     */
    public static Map<String, String> getAuthMapPath() {
        UserSession userSession = get();
        if (userSession != null) {
            return userSession.getUser().getAuthMap() ;
        }
        return null;
    }

    /**
     * 权限code
     * @return
     */
    public static Map<String, String> getAuthMap() {
        UserSession userSession = get();
        if (userSession != null) {
            return userSession.getUser().getAuthMap();
        }
        return null;
    }

    /**
     * 权限code
     * @return
     */
    public static List<String> getLimitCode() {
        UserSession userSession = get();
        if (userSession != null) {
            return userSession.getUser().getLimitCode();
        }
        return null;
    }

    /**
     * 组织类型
     * @return
     */
    public static String getOrgType(){
        UserSession userSession = get();
        if (userSession != null) {
            return userSession.getUser().getOrgType();
        }
        return null;
    }

    public static String getSessionId() {
        UserSession userSession = get();
        if (userSession != null) {
            return userSession.getSessionId();
        }
        return null;
    }

    public final static void destroy() {
        allContext.remove();
    }
}
