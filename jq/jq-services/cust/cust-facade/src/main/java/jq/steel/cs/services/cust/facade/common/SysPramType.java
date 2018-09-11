package jq.steel.cs.services.cust.facade.common;

public enum SysPramType {


    DELETE("delete",1),
    UPDATE("update",2),
    INSERT("insert",3);

    private String msg;

    private Integer code;


    SysPramType(String mas, Integer code){
        this.msg = mas;
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
}
