package jq.steel.cs.services.cust.facade.common;

public enum ObjectionTiBaoStatus {

    NEW("NEW","请选择"),
    PRESENT("PRESENT","已提报"),
    ACCEPTANCE("ACCEPTANCE","已受理"),
    REJECT("REJECT","已驳回"),
    INVESTIGATION("INVESTIGATION","调查中"),
    HANDLE("HANDLE","处理中"),
    END("END","已结案"),
    EVALUATE("EVALUATE","已评价");


    private String code;

    private String msg;
    ObjectionTiBaoStatus(String code, String msg){

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
    
    public static ObjectionTiBaoStatus getStatus(String code) {
    	for (ObjectionTiBaoStatus status : ObjectionTiBaoStatus.values())
    		if (status.getCode().equals(code))
    			return status;
    	return null;
    }
    
}
