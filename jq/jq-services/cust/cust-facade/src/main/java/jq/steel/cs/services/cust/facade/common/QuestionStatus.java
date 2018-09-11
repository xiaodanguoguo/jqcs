package jq.steel.cs.services.cust.facade.common;

public enum QuestionStatus {


    NEW_CREATE("1","新建"),
    ISSUE("2","已发布"),
    FINISH("3","已结束");


    private String code;

    private String msg;
    QuestionStatus(String code, String msg){

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
    
    public static QuestionStatus getStatus(String code) {
    	for (QuestionStatus status : QuestionStatus.values())
    		if (status.getCode().equals(code))
    			return status;
    	return null;
    }
    
}
