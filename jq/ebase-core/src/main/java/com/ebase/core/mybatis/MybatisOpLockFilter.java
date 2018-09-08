package com.ebase.core.mybatis;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

/**
 * 
 * <p>
 * 拦截dao中 insert update delete 方法，若是返回值为0 ，则报错
 * </p>
 *
 * @project core
 * @class MybatisOpLockFilter
 * @author a@panly.me
 * @date 2017年5月27日 下午4:06:29
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class MybatisOpLockFilter implements Interceptor {

	private boolean ignore;

	public Object intercept(Invocation invocation) throws Throwable {
		if (ignore) {
			return invocation.proceed();
		} else {
			MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
			String sqlId = mappedStatement.getId();
			Object parameter = null;
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }
			Object returnValue = invocation.proceed();
			int rows = 0;
			/*if (returnValue != null) {
				rows = (Integer) returnValue;
				if (rows == 0) {
					//获取传入的数据
					throw new SQLException(sqlId + "操作不成功 ,参数是:"+JsonUtil.toJson(parameter));
				}
			}*/
			return returnValue;
		}
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		String ignoreStr = properties.getProperty("ignore");

		if (StringUtils.equals(ignoreStr, "true")) {
			ignore = true;
		} else {
			ignore = false;
		}
	}

}
