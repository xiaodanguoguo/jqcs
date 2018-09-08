package com.ebase.core.log.filter;

import java.util.Map;

import com.ebase.core.log.SearchableLoggerFactory;
import com.ebase.core.log.SearchableLogHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.MDC;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * 用于为dubbo调用打印执行记录.
 */
public class DubboServiceLogFilter implements Filter {
    static Logger costingLogger = SearchableLoggerFactory.getExecutionCostLogger();
    static Logger tracingLogger = SearchableLoggerFactory.getResourceTraceLogger();

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (RpcContext.getContext().isProviderSide()) {
            return this.invokeOnProviderSide(invoker, invocation);
        } else if (RpcContext.getContext().isConsumerSide()) {
            return this.invokeOnConsumerSide(invoker, invocation);
        } else {
            return invoker.invoke(invocation);
        }
    }

    /**
     * dubbo服务提供方的处理逻辑.
     *
     * @param invoker    Invoker
     * @param invocation Invocation
     * @return 返回结果
     * @throws com.alibaba.dubbo.rpc.RpcException RPC异常
     */
    public Result invokeOnProviderSide(Invoker<?> invoker, Invocation invocation) throws RpcException {

        Map<String, String> attachments = invocation.getAttachments();

        // 从attachment中获取传播过来的requestId
        String propagatedRid = StringUtils.trimToNull(attachments.get(SearchableLogHelper.KEY_PROPAGATED_REQUEST_ID));
        // 若传播过来的requestId为空, 则生成新的requestId
        String requestId = StringUtils.isNotBlank(propagatedRid) ? propagatedRid : SearchableLogHelper.getRandomId();

        // 从attachment中获取传播过来的userId
        String propagatedUid = StringUtils.trimToNull(attachments.get(SearchableLogHelper.KEY_PROPAGATED_USER_ID));
        // 若传播过来的userId为空, 则使用anonymous
        String userId = StringUtils.isNotBlank(propagatedUid) ? propagatedUid : "anonymous";

        // 计算当前调用请求的资源
        String resource = SearchableLogHelper.getResourceName(invoker.getInterface(), invocation.getMethodName(),
                invocation.getParameterTypes());

        // 获取消费方应用名称
        String consumer = attachments.get(SearchableLogHelper.KEY_PROPAGATED_APP);

        long start = System.currentTimeMillis();
        try {
            // 在MDC中设置userId, application, ip, requestId, resource等信息
            MDC.put(SearchableLogHelper.MDC_KEY_USER_ID, userId);
            MDC.put(SearchableLogHelper.MDC_KEY_APP, SearchableLogHelper.getApplication());
            MDC.put(SearchableLogHelper.MDC_KEY_IP, SearchableLogHelper.getInetAddr());
            MDC.put(SearchableLogHelper.MDC_KEY_REQUEST_ID, requestId);
            MDC.put(SearchableLogHelper.MDC_KEY_RESOURCE, resource);

            // 打印service执行开始的跟踪日志
            tracingLogger.info("started, site= provider, consumer= {}, args= {}", consumer,
                    SearchableLogHelper.toJSONString(invocation.getArguments()));

            // 执行dubbo调用
            Result result = invoker.invoke(invocation);

            // 获取dubbo服务提供方的应用名称
            result.getAttachments().put(SearchableLogHelper.KEY_PROPAGATED_APP, SearchableLogHelper.getApplication());

            // 根据返回结果打印service执行完成的跟踪日志
            if (result.hasException()) {
                tracingLogger.error("completed, cost= {}ms", System.currentTimeMillis() - start, result.getException());
            } else {
                String content = SearchableLogHelper.toJSONString(result.getValue());
                tracingLogger.info("completed, cost= {}ms, result= {}", System.currentTimeMillis() - start, content);
            }

            return result;
        } catch (RpcException rex) {
            // 打印service执行完成的跟踪日志
            tracingLogger.error("completed, cost= {}ms", System.currentTimeMillis() - start, rex);
            throw rex;
        } catch (RuntimeException rex) {
            // 打印service执行完成的跟踪日志
            tracingLogger.error("completed, cost= {}ms", System.currentTimeMillis() - start, rex);
            throw rex;
        } finally {
            // 若执行超时, 则打印超时日志
            long cost = System.currentTimeMillis() - start;
            if (cost > SearchableLogHelper.getInstance().getTimeout()) {
                costingLogger.warn("timeout, type= dubbo:provider, cost= {}ms", cost);
            }

            // 清空MDC中设置的userId, application, ip, requestId, resource等信息
            MDC.remove(SearchableLogHelper.MDC_KEY_RESOURCE);
            MDC.remove(SearchableLogHelper.MDC_KEY_REQUEST_ID);
            MDC.remove(SearchableLogHelper.MDC_KEY_IP);
            MDC.remove(SearchableLogHelper.MDC_KEY_APP);
            MDC.remove(SearchableLogHelper.MDC_KEY_USER_ID);
        }

    }

    /**
     * dubbo服务消费方的处理逻辑.
     *
     * @param invoker    Invoker
     * @param invocation Invocation
     * @return 返回结果
     * @throws com.alibaba.dubbo.rpc.RpcException RPC异常
     */
    public Result invokeOnConsumerSide(Invoker<?> invoker, Invocation invocation) throws RpcException {

        // 计算当前调用请求的资源
        String resource = SearchableLogHelper.getResourceName(invoker.getInterface(), invocation.getMethodName(),
                invocation.getParameterTypes());

        // 获取之前的资源
        String originRes = MDC.get(SearchableLogHelper.MDC_KEY_RESOURCE);

        long start = System.currentTimeMillis();
        try {
            // 在MDC中设置本次调用的资源
            MDC.put(SearchableLogHelper.MDC_KEY_RESOURCE, resource);

            // 为dubbo远程调用设置隐式传递的参数: requestId, userId, app
            invocation.getAttachments().put(SearchableLogHelper.KEY_PROPAGATED_REQUEST_ID,
                    (String) MDC.get(SearchableLogHelper.MDC_KEY_REQUEST_ID));
            invocation.getAttachments().put(SearchableLogHelper.KEY_PROPAGATED_USER_ID,
                    (String) MDC.get(SearchableLogHelper.MDC_KEY_USER_ID));

            invocation.getAttachments().put(SearchableLogHelper.KEY_PROPAGATED_APP, SearchableLogHelper.getApplication());

            // 打印service执行开始的跟踪日志
            tracingLogger.info("started, site= consumer, args= {}",
                    SearchableLogHelper.toJSONString(invocation.getArguments()));

            // 执行dubbo调用
            Result result = invoker.invoke(invocation);

            // 获取服务提供方的应用名称
            String provider = result.getAttachments().get(SearchableLogHelper.KEY_PROPAGATED_APP);

            // 根据返回结果打印service执行完成的跟踪日志
            if (result.hasException()) {
                tracingLogger.error("completed, cost= {}ms, provider= {}",
                        new Object[]{System.currentTimeMillis() - start, provider, result.getException()});
            } else {
                String content = SearchableLogHelper.toJSONString(result.getValue());
                tracingLogger.info("completed, cost= {}ms, provider= {}, result= {}",
                        new Object[]{System.currentTimeMillis() - start, provider, content});
            }

            return result;
        } catch (RpcException rex) {
            // 打印service执行完成的跟踪日志
            tracingLogger.error("completed, cost= {}ms", System.currentTimeMillis() - start, rex);
            throw rex;
        } catch (RuntimeException rex) {
            // 打印service执行完成的跟踪日志
            tracingLogger.error("completed, cost= {}ms", System.currentTimeMillis() - start, rex);
            throw rex;
        } finally {
            // 若执行超时, 则打印超时日志
            long cost = System.currentTimeMillis() - start;
            if (cost > SearchableLogHelper.getInstance().getTimeout()) {
                costingLogger.warn("timeout, type= dubbo:consumer, cost= {}ms", cost);
            }

            // 若之前设置过资源名称, 则恢复之前的资源名称; 若之前没有设置过, 则删除当前的资源名称.
            if (originRes == null) {
                MDC.remove(SearchableLogHelper.MDC_KEY_RESOURCE);
            } else {
                MDC.put(SearchableLogHelper.MDC_KEY_RESOURCE, originRes);
            }

        }

    }

}
