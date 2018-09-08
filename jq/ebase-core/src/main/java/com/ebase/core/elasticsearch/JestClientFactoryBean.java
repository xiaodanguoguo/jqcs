package com.ebase.core.elasticsearch;

import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.google.gson.GsonBuilder;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

/**
 * <p>
 * elasticsearch jest 客户端的配置
 * </p>
 *
 * @project core
 * @class JestClientFactoryBean
 */
public class JestClientFactoryBean implements FactoryBean<JestClient>,InitializingBean{

	private JestClient jestClient;
	
	private List<String> serverUris; 
	
	private int maxTotal = 20;
	
	private int readTimeout = 6000;
	
	private int connTimeout = 6000;
	
	@Override
	public JestClient getObject() throws Exception {
		return jestClient;
	}

	@Override
	public Class<?> getObjectType() {
		return JestClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notEmpty(serverUris, "地址必须配置");
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder(serverUris)
				.gson(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create())
				.connTimeout(1500)
				.readTimeout(readTimeout)
				.connTimeout(connTimeout)
				.maxTotalConnection(maxTotal)
				.multiThreaded(true)
				.build());
		jestClient = factory.getObject();
	}

	public List<String> getServerUris() {
		return serverUris;
	}

	public void setServerUris(List<String> serverUris) {
		this.serverUris = serverUris;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getConnTimeout() {
		return connTimeout;
	}

	public void setConnTimeout(int connTimeout) {
		this.connTimeout = connTimeout;
	}
	
}
