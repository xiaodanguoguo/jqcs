package jq.steel.cs.services.cust.facade.common;

public enum ProductInfoStatus {


    NEW_CREATE("1","新建"),
    ISSUE("2","已发布");


    private String code;

    private String msg;
    ProductInfoStatus(String code, String msg){

        this.code = code;
        this.msg = msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }
    
    public static ProductInfoStatus getStatus(String code) {
    	for (ProductInfoStatus status : ProductInfoStatus.values())
    		if (status.getCode().equals(code))
    			return status;
    	return null;
    }
    
}
