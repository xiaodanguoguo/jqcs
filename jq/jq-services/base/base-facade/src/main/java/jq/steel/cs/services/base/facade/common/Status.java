package jq.steel.cs.services.base.facade.common;

public enum Status {


    STOP("0","停用"),
    START("1","启用"),
    HOLD_AUDIT("2","待审核"),
    NOT_PASS("3","未通过"),
    ORG("4","组织待审核根目录"),
    PASS("5","审核通过");



    private String code;

    private String msg;
    Status(String code, String msg){

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
    
    public static Status getStatus(String code) {
    	for (Status status : Status.values())
    		if (status.getCode().equals(code))
    			return status;
    	return null;
    }
    
}
