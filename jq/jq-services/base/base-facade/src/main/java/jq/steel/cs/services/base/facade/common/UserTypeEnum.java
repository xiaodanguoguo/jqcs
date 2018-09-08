package jq.steel.cs.services.base.facade.common;

import java.util.HashMap;
import java.util.Map;

public enum UserTypeEnum {

    PERSONAL((byte)2,"个人"),
    ENTERPRISE((byte)1,"企业");

    private byte code;

    private String msg;

    UserTypeEnum(Byte code,String mas){
        this.code = code;
        this.msg = mas;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public Byte getCode() {
        return code;
    }


    private static Map<Byte,UserTypeEnum> tmpMap = new HashMap<>(UserTypeEnum.values().length);

    public static UserTypeEnum getUserTypeEnum(Byte lifeCycle) {

        UserTypeEnum userTypeEnum = tmpMap.get(lifeCycle);
        if(userTypeEnum == null){
            for (UserTypeEnum cycleEnum : UserTypeEnum.values()){
                tmpMap.put(cycleEnum.getCode(),cycleEnum);
                if (cycleEnum.getCode().equals(lifeCycle)){
                    userTypeEnum = cycleEnum;
                }
            }
        }
        return userTypeEnum;
    }

    public static Boolean isPersonal(Byte type){

        return PERSONAL.getCode().equals(type) ? true : false;
    }
}
