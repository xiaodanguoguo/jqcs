package jq.steel.cs.services.cust.facade.common;

public enum ProductCategoryStatus {


    SAVE("1","保存"),
    SUBMIT("2","提交");


    private String code;

    private String msg;
    ProductCategoryStatus(String code, String msg){

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
    
    public static ProductCategoryStatus getStatus(String code) {
    	for (ProductCategoryStatus status : ProductCategoryStatus.values())
    		if (status.getCode().equals(code))
    			return status;
    	return null;
    }
    
}
