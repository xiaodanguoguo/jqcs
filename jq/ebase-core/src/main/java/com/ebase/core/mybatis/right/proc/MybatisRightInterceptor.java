package com.ebase.core.mybatis.right.proc;


import com.ebase.core.threadlocal.RightThreadLocal;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * 鉴权 update失败，直接报错
 * @author a@panly.me
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class MybatisRightInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object returnValue = invocation.proceed();
		if (returnValue != null) {
			int changed = (Integer) returnValue;
			if (changed == 0 && RightThreadLocal.get() != null && RightThreadLocal.get().getRightId() != null
					&& RightThreadLocal.get().getRightId().size() > 0) {
				throw new RuntimeException(RightThreadLocal.get().getOperCode() + "操作失败，请查看权限配置");
			}
		}
		return returnValue;

	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
