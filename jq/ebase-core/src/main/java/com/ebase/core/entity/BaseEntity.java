package com.ebase.core.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.ebase.utils.JsonUtil;

/**
 * <p>
 * BaseEntity   pojo 类的基础类 
 * </p>
 *
 * @project core
 * @class BaseDTO
 */
public class BaseEntity implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	public String toJson(){
		return JsonUtil.toJson(this);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, true);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, true);
	}

}
