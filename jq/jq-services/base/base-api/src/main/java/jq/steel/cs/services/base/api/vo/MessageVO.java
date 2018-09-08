package jq.steel.cs.services.base.api.vo;

import java.io.Serializable;
import java.util.Map;

public class MessageVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private transient String id;
	// 接收人信息
	private String destination;
	// 动态值
	private Map<String, Object> variables;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

}
