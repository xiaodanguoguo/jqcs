package com.ebase.core.mybatis;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import com.ebase.core.log.SearchableLogHelper;
import com.ebase.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.MDC;

import com.ebase.core.log.SearchableLoggerFactory;

/**
 * 
 * <p> 打印sql执行语句 </p> 
 *
 * @project 	core
 * @class 		MybatisLogInterceptor
 * @author 		a@panly.me
 * @date 		2017年5月27日 下午4:04:17
 */
@Intercepts({ //
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class}) //
})
public class MybatisLogInterceptor implements Interceptor {
	
	/**默认300毫秒sql以上的语句都打印记录*/
    private static final long DEFAULT_TIMEOUT = 300L;
    
    private boolean ignore = false;
    
    private long timeout = DEFAULT_TIMEOUT;

    private  static final Logger logger = SearchableLoggerFactory.getExecutionCostLogger();

    public Object intercept(Invocation invocation) throws Throwable {
        if (ignore) {
            return invocation.proceed();
        } else {
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Object parameter = null;
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }
            String sqlId = mappedStatement.getId();
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            Configuration configuration = mappedStatement.getConfiguration();

            long start = System.currentTimeMillis();
            try {
                return invocation.proceed();
            } finally {
                long cost = System.currentTimeMillis() - start;
                if (cost > this.timeout) {
                    this.printTimeoutSql(sqlId, showSql(configuration, boundSql), cost);
                }
            }
        }
    }

    private void printTimeoutSql(String sqlId, String sql, long cost) {
        String resource = MDC.get(SearchableLogHelper.MDC_KEY_RESOURCE);
        try {
            MDC.put(SearchableLogHelper.MDC_KEY_RESOURCE, sqlId);
            logger.warn("timeout, type= sql, cost= {}ms, sql= {}", cost, sql);
        } finally {
            if (resource == null) {
                MDC.remove(SearchableLogHelper.MDC_KEY_RESOURCE);
            } else {
                MDC.put(SearchableLogHelper.MDC_KEY_RESOURCE, resource);
            }
        }
    }

    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

    private String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        String ignoreStr = properties.getProperty("ignore");
        if (StringUtil.equals(ignoreStr, "true")) {
            ignore = true;
        } else {
            ignore = false;
        }

        String timeoutStr = StringUtils.trimToNull(properties.getProperty("timeout"));
        try {
            this.timeout = timeoutStr == null ? DEFAULT_TIMEOUT : Long.valueOf(timeoutStr);
        } catch (Exception ex) {
            this.timeout = DEFAULT_TIMEOUT;
        }
    }

}