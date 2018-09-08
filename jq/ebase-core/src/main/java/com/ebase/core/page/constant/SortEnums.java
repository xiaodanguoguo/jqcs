/*
 * Copyright: Copyright (c) 2017-2020
 * Company: ygego
 *
 */

package com.ebase.core.page.constant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: SrotEnums
 * Description: 页面排序枚举
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public interface SortEnums extends Serializable {

    /**
     * 接口模块排序字段
     */
    enum SortFieldEnum implements SortEnums {

        /**
         * 公共
         */

        /*创建时间*/
        CREATE_TIME("createTime","`CREATE_TIME`"),

        /*修改时间*/
        UPDATE_TIME("updateTime","`UPDATE_TIME`"),

        /*依赖统计*/
        BE_USED_COUNT("beUsedCount","beUsedCount"),

        /*引用统计*/
        RELY_COUNT("relyCount","relyCount"),

        /**
         * 方法模块
         */
        // 方法编号
        TYPE_METHOD_CODE("methodCode", "METHOD_CODE"),
        // 方法名
        TYPE_METHOD_NAME("methodName", "convert(`METHOD_NAME` using gbk)"),
        // 服务名
        TYPE_SERVICE_NAME("serviceName", "SERVICE_NAME"),
        // 接口名
        TYPE_INTERFACE_NAME("interfaceName", "INTERFACE_NAME"),
        // 版本号
        TYPE_INTERFACE_VERSION_NAME("interfaceVersionName", "INTERFACE_VERSION_NAME"),
        //参数描述
        TYPE_PARA_DESC("paraDesc", "PARA_DESC"),
        //方法描述
        TYPE_METHOD_DESC("methodDesc", "METHOD_DESC"),
        // 方法状态
        TYPE_METHOD_STATUS("methodStatus", "METHOD_STATUS"),
        // 引用统计
        TYPE_QUO_COUNT("quoCount", "QUO_COUNT"),

        TYPE_QUOTE_STAT("quoteStat","QUOTE_STAT"),
        TYPE_CALL_STAT("callStat","CALL_STAT"),

        /**
         * 服务模块
         */
        /*服务编码*/
        SERVICE_CODE("serviceCode","`SERVICE_CODE`"),

        /*服务名称*/
        SERVICE_NAME("serviceName","convert(`SERVICE_NAME` using gbk)"),

        /*应用名称*/
        APP_NAME("appName","convert(`APP_NAME` using gbk)"),

        /*服务来源*/
        SERVICE_SOURCE("serviceSource","`SERVICE_SOURCE`"),

        /*服务状态*/
        SERVICE_STATUS("serviceStatus","`SERVICE_STATUS`"),
        // 服务类型
        SERVICE_TYPE("serviceType","`SERVICE_TYPE`"),

        /**
         * 接口模块
         */
        /*接口编码*/
        INTERFACE_CODE("interfaceCode", "`INTERFACE_CODE`"),
        /*接口类型*/
        INTERFACE_TYPE("interfaceType","INTERFACE_TYPE"),
        /*接口协议*/
        INTERFACE_PROTOCOL("interfaceProtocol","INTERFACE_PROTOCOL"),
        /*接口状态*/
        INTERFACE_STATUS("interfaceStatus","INTERFACE_STATUS"),
        /*版本统计*/
        INTERFACE_VERSION_COUNT("versionsCount","`versionsCount`"),

        /**
         * 版本模块
         */
        /*版本编码*/
        INTERFACE_VERSION_CODE("interfaceVersionCode","INTERFACE_VERSION_CODE"),
        /*版本名称*/
        INTERFACE_VERSION_NAME("interfaceVersionName","INTERFACE_VERSION_NAME"),
        /*版本状态*/
        INTERFACE_VERSION_STATUS("interfaceVersionStatus","INTERFACE_VERSION_STATUS"),

        /**
         * 接口名称
         */
        TYPE_SOAP("interfaceName", "`INTERFACE_NAME`"),
        /**
         * 人员编号
         */
        ACCT_CODE("acctCode", "`ACCT_CODE`"),
        /**
         * 人员名称
         */
        NAME("name", "convert(`NAME` using gbk)"),
        /**
         * 账号名称
         */
        ACCT_TITLE("acctTitle", "convert(`ACCT_TITLE` using gbk)"),
        /**
         * 人员状态
         */
        ACCT_STATE("acctState", "`ACCT_STATE`"),
        /**
         * 角色编号
         */
        ROLE_CODE("roleCode", "`ROLE_CODE`"),
        /**
         * 角色名称
         */
        ROLE_TITLE("roleTitle", "convert(`ROLE_TITLE` using gbk)"),
        /**
         * 角色描述
         */
        ROLE_DESC("roleDesc", "convert(`ROLE_DESC` using gbk)"),
        /**
         * 成员统计
         */
        ACCT_NUM("acctNum", "`acctNum`"),
        /**
         * 角色状态
         */
        ROLE_STATE("roleState", "`ROLE_STATE`"),
        /**
         * 应用编号
         */
        APP_CODE("appCode", "`APP_CODE`"),
        /**
         * 应用描述
         */
        APP_DESC("appDesc", "`convert(`APP_DESC` using gbk)"),
        /**
         * 依赖统计
         */
        METHODSSET_NUM("methodsSetNum", "`methodsSetNum`"),
        /**
         * 应用状态
         */
        APP_STATUS("appStatus", "`APP_STATUS`"),
        /**
         * 应用来源
         */
        APP_SOURCE("appSource", "`APP_SOURCE`"),
        /**
         * 应用类型
         */
        APP_TYPE("appType", "`APP_TYPE`"),
    	/**
    	 * 注册时间
    	 */
    	REGISTER_TIME("registerTime", "`REGISTER_TIME`"),
    	/**
    	 * 加入时间
    	 */
    	JOIN_TIME("joinTime", "`joinTime`"),

        /**
         * 功能管理
         */

        //功能编码
        FUNCTION_CODE("functionCode", "`FUNCTION_CODE`"),
        //功能名称
        FUNCTION_TITLE("functionTitle", "convert(`FUNCTION_TITLE` using gbk)"),
        //功能全路径
        TITLE_FULL_PATH("titleFullPath", "convert(`TITLE_FULL_PATH` using gbk)"),
        //父级功能
        TITLE_PARENT_PATH("titleParentPath", "convert(`TITLE_PARENT_PATH` using gbk)"),
        //操作统计
        OPER_STATISTICS("operStatistics", "`OPER_STATISTICS`"),
        //功能状态
        FUNCTION_STATE("functionState", "`FUNCTION_STATE`"),
        //成员编号
        MEMBER_CODE("memberCode", "`MEMBER_CODE`"),
        //成员类型
        MEMBER_TYPE("memberType", "`MEMBER_TYPE`"),
        //成员名称
        MEMBER_TITLE("memberTitle", "convert(`MEMBER_TITLE` using gbk)"),
        //成员状态
        MEMBER_STATE("memberState", "`MEMBER_STATE`"),

        /**
         *  操作管理
         */

        //操作编号
        OPER_CODE("operCode", "`OPER_CODE`"),
        //操作名称
        OPER_TITLE("operTitle", "convert(`OPER_TITLE` using gbk)"),
        //授权统计
        OPER_PRIV_COUNT("operPrivCount", "`OPER_PRIV_COUNT`"),
        //操作状态
        OPER_STATE("operState", "`OPER_STATE`"),
        //授权编号
        PRIV_OBJ_CODE("privObjCode", "`PRIV_OBJ_CODE`"),
        //授权类型
        PRIV_TYPE("privType", "`PRIV_TYPE`"),
        //授权对象
        PRIV_OBJ_NAME("privObjName", "convert(`PRIV_OBJ_NAME` using gbk)"),
        //授权来源
        OPER_PERSON_NAME("operPersonName", "convert(`OPER_PERSON_NAME` using gbk)"),
        //授权状态
        RELA_STATE("relaState", "`RELA_STATE`");


        private String key;
        private String field;

        public static Map<String, String> SORT_FIELD_MAP = new HashMap<>();

        static {
            for (SortFieldEnum em : SortFieldEnum.values()) {
                SORT_FIELD_MAP.put(em.getKey(), em.getField());
            }
        }

        SortFieldEnum(String key, String field) {
            this.key = key;
            this.field = field;
        }

        public String getKey() {
            return key;
        }

        public String getField() {
            return field;
        }
    }

    /**
     * 排序方式
     */
    enum SortTypeEnum implements SortEnums {
        /**
         * 正序排列
         */
        TYPE_ASC("asc", "ASC"),

        /**
         * 倒序排列
         */
        TYPE_DESC("desc", "DESC");

        public static Map<String, String> SORT_TYPE_MAP = new HashMap<>();

        static {
            for (SortTypeEnum em : SortTypeEnum.values()) {
                SORT_TYPE_MAP.put(em.getKey(), em.getType());
            }
        }

        private String key;

        private String type;

        SortTypeEnum(String key, String type) {
            this.key = key;
            this.type = type;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }



    /**
     * 日志枚举
     */
    enum LogSortFieldEnum implements SortEnums {

        logId("logId", "LOG_ID"),
        recordTime("recordTime", "RECORD_TIME"),
        appName("appName", "convert(`APP_NAME` using gbk)"),
        operTitle("operTitle", "convert(`OPER_TITLE` using gbk)"),
        ipAddr("ipAddr", "IP_ADDR"),
        operResult1("operResult1", "OPER_RESULT"),
        operResult2("operResult2", "OPER_RESULT"),
        operPersonName("operPersonName", "convert(`OPER_PERSON_NAME` using gbk)"),
        operatorName("operatorName", "convert(`OPERATOR_NAME` using gbk)"),
        operateResult1("operateResult1", "OPERATE_RESULT"),
        logContent("logContent", "LOG_CONTENT"),
        operateTime("operateTime", "OPERATE_TIME"),
        logType3("logType3", "LOG_TYPE"),
        affiModule("affiModule", "AFFI_MODULE"),
        recordType("recordType", "RECORD_TYPE"),
        callTitle("callTitle", "convert(`CALL_TITLE` using gbk)"),
        serviceName("serviceName", "convert(`SERVICE_NAME` using gbk)"),
        interfaceName("interfaceName", "convert(`INTERFACE_NAME` using gbk)"),
        interfaceVersionNum("interfaceVersionNum", "INTERFACE_VERSION_NUM"),
        methodName("methodName", "convert(`METHOD_NAME` using gbk)"),
        callTime("callTime", "CALL_TIME"),
        authenticationResult1("authenticationResult1", "AUTHENTICATION_RESULT"),
        callResult1("callResult1", "CALL_RESULT"),
        responseTime("responseTime", "RESPONSE_TIME"),
        createTime("createTime", "CREATE_TIME"),
        dialTestingTime("dialTestingTime", "DIAL_TESTING_TIME"),
        dialTestingResult("dialTestingResult", "DIAL_TESTING_RESULT"),
        operateDetai2("operateDetai2", "LOG_CONTENT");
        private String key;
        private String field;

        public static Map<String, String> LOG_SORT_FIELD_MAP = new HashMap<>();

        static {
            for (LogSortFieldEnum em : LogSortFieldEnum.values()) {
                LOG_SORT_FIELD_MAP.put(em.getKey(), em.getField());
            }
        }

        LogSortFieldEnum(String key, String field) {
            this.key = key;
            this.field = field;
        }

        public String getKey() {
            return key;
        }

        public String getField() {
            return field;
        }
    }

}
