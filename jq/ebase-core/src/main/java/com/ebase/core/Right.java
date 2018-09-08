package com.ebase.core;

import java.util.List;

/**
 * 用户和操作的关系，以及对应的权限数据集
 * @author lipan
 *
 */
public class Right {
	
	//对应用户
	private Long acctId;
	
	//操作
	private Long operId;
	
	private String operCode;
	
	private String operName;
	
	//关联的数据权限rightId
	private List<Long> rightId;
	
	public Long getAcctId() {
		return acctId;
	}

	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}
	

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public List<Long> getRightId() {
		return rightId;
	}

	public void setRightId(List<Long> rightId) {
		this.rightId = rightId;
	}

}
