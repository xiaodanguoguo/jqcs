package jq.steel.cs.services.base.facade.common;

import java.util.HashMap;
import java.util.Map;

public enum UserStatusEnum {

    NORMAL((byte)1,"正常"),
    INVALID((byte)0,"失效"),
    LOCKING((byte)2,"锁定");


    private byte code;

    private String msg;

    UserStatusEnum(Byte code,String mas){
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


    private static Map<Byte,UserStatusEnum> tmpMap = new HashMap<>(UserStatusEnum.values().length);

    public static UserStatusEnum getUserStatusEnum(Byte lifeCycle) {

        UserStatusEnum userStatusEnum = tmpMap.get(lifeCycle);
        if(userStatusEnum == null){
            for (UserStatusEnum cycleEnum : UserStatusEnum.values()){
                tmpMap.put(cycleEnum.getCode(),cycleEnum);
                if (cycleEnum.getCode().equals(lifeCycle)){
                    userStatusEnum = cycleEnum;
                }
            }
        }
        return userStatusEnum;
    }

}
