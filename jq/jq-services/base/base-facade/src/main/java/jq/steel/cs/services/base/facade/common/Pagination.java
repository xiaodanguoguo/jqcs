package jq.steel.cs.services.base.facade.common;

/**
 * 
 * @author MrLi
 *
 */
public enum Pagination {

	PAGENUM(1),
	PAGESIZE(10);
	
	private Integer value;
	private Pagination(Integer value) {
		this.value = value;
	}
	public Integer getValue() {
		return value;
	}
}
