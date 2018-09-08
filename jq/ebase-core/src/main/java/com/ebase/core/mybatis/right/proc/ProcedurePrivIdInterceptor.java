package com.ebase.core.mybatis.right.proc;

import com.ebase.core.threadlocal.PageThreadLocal;
import com.ebase.core.threadlocal.RightThreadLocal;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("all")
@Intercepts(value = {
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }) })
public class ProcedurePrivIdInterceptor implements Interceptor {

	Map<String, MappedStatement> callMsMap = new ConcurrentHashMap<>();

	public static final String CALLABLE = "_CALLABLE";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 进入 权限拦截 sql 模式
		if (RightThreadLocal.get()!=null&& RightThreadLocal.get().getRightId()!=null&& RightThreadLocal.get().getRightId().size()>0) {
			Object[] args = invocation.getArgs();
			MappedStatement ms = (MappedStatement) args[0];
			Object srcParameter = args[1];
			ResultHandler resultHandler = null;
			if(args.length > 3){
				resultHandler = (ResultHandler) args[3];
			}
			BoundSql srcBoundSql = ms.getBoundSql(srcParameter);
			Executor executor = (Executor) invocation.getTarget();
			
			// 获取原始sql
			String srcSqlContent = getSql(srcBoundSql, ms.getConfiguration());
			
			String callMsId = ms.getId()+CALLABLE;
			
			MappedStatement callMs = null;
			// 创建 callable的 MappedStatement
			if(callMsMap.containsKey(callMsId)){
				callMs = callMsMap.get(callMsId);
			}else{
				callMs = newCallMs(ms,callMsId);
				callMsMap.put(callMsId, callMs);
			}
			
			// 获取callable传入参数
			Map<String, Object> callMapParameter = getCallParameter(srcSqlContent);
			
			Object returnObject = null;
			
			if(invocation.getMethod().getName().equals("query")){
				// 执行查询操作
				returnObject = executor.query(callMs, callMapParameter, RowBounds.DEFAULT, resultHandler);
			}else{
				returnObject = executor.update(callMs, callMapParameter);
			}
			//若是分页模式
			if (PageThreadLocal.get() != null) {
				Long total = (Long) callMapParameter.get("total");
				if(total==null){total=new Long(0);};
				PageThreadLocal.get().setTotal(total);
			}
			return returnObject;
		} else {
			return invocation.proceed();
		}
	}

	private MappedStatement newCallMs(MappedStatement srcMs,String callMsId) {
		// 获取call输入输出配置
		List<ParameterMapping> callParameterMappings = getCallParameterMappings(srcMs.getConfiguration());

		// 获取call存储过程内容
		String callSql = getCallSql();

		StaticSqlSource sqlSource = new StaticSqlSource(srcMs.getConfiguration(), callSql, callParameterMappings);

		MappedStatement.Builder builder = new MappedStatement.Builder(srcMs.getConfiguration(),callMsId, sqlSource, srcMs.getSqlCommandType());

		builder.resource(srcMs.getResource());
		builder.fetchSize(srcMs.getFetchSize());
		builder.statementType(StatementType.CALLABLE);
		builder.keyGenerator(srcMs.getKeyGenerator());
		if (srcMs.getKeyProperties() != null && srcMs.getKeyProperties().length != 0) {
			StringBuilder keyProperties = new StringBuilder();
			for (String keyProperty : srcMs.getKeyProperties()) {
				keyProperties.append(keyProperty).append(",");
			}
			keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
			builder.keyProperty(keyProperties.toString());
		}
		builder.timeout(srcMs.getTimeout());
		builder.parameterMap(srcMs.getParameterMap());
		builder.resultMaps(srcMs.getResultMaps());
		builder.resultSetType(srcMs.getResultSetType());
		builder.cache(srcMs.getCache());
		builder.flushCacheRequired(srcMs.isFlushCacheRequired());
		builder.useCache(srcMs.isUseCache());
		MappedStatement callMs = builder.build();
		return callMs;
	}


	private Map<String, Object> getCallParameter(String srcSqlContent) {
		Map<String, Object> params = new HashMap<>();
		params.put("execSql", srcSqlContent);
		List<Long> rightId = RightThreadLocal.get().getRightId();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rightId.size(); i++) {
			sb.append(rightId.get(i));
			if(i!=rightId.size()-1){
				sb.append(",");
			}
		}
		params.put("rightId",sb.toString());
		if (PageThreadLocal.get() != null) {
			String dataRange = PageThreadLocal.get().getStartRow()+","+PageThreadLocal.get().getPageSize();
			params.put("dataRange", dataRange);
			params.put("total", 0);
		}else{
			params.put("dataRange", "0,0");
			params.put("total", 0);
		}
		return params;
	}

	/**
	 * <parameter property="execSql" jdbcType="VARCHAR" javaType="java.lang.String" mode="IN"/> 
	 * <parameter property="dataRange" jdbcType="VARCHAR" javaType="java.lang.String" mode="IN"/> 
	 * <parameter property="rightId" jdbcType="VARCHAR" javaType="java.lang.String" mode="IN"/>
	 * <parameter property="total" jdbcType="NUMERIC" javaType="java.lang.Long" mode="OUT"/>
	 * 
	 * @return
	 */
	private List<ParameterMapping> getCallParameterMappings(Configuration configuration) {
		List<ParameterMapping> list = new ArrayList<>();
		//3个in参数，
		//1个out参数
		ParameterMapping sqlcontent = new ParameterMapping.Builder(configuration, "execSql", String.class)
				.mode(ParameterMode.IN).jdbcType(JdbcType.VARCHAR).build();
		ParameterMapping dataRange = new ParameterMapping.Builder(configuration, "dataRange", String.class)
				.mode(ParameterMode.IN).jdbcType(JdbcType.VARCHAR).build();
		ParameterMapping rightId = new ParameterMapping.Builder(configuration, "rightId", String.class)
				.mode(ParameterMode.IN).jdbcType(JdbcType.VARCHAR).build();
		ParameterMapping total = new ParameterMapping.Builder(configuration, "total", Long.class)
				.mode(ParameterMode.OUT).jdbcType(JdbcType.INTEGER).build();
		list.add(sqlcontent);
		list.add(dataRange);
		list.add(rightId);
		list.add(total);
		return list;
	}

	/**
	 * 获取call sql 方式
	 * 
	 * @return
	 */
	private String getCallSql() {
		return "call PR_GetDataPermission_PriID(?,?,?,?)";
	}

	private String getSql(BoundSql boundSql, Configuration configuration) {
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
		return sql.toUpperCase();
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

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
