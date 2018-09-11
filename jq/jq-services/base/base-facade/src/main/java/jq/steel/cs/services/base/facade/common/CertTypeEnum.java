package jq.steel.csservices.base.facade.common;

import java.util.HashMap;
import java.util.Map;

//证件类型
public enum CertTypeEnum {

    ID_NUMBER((byte)1,"身份证号");

    private byte code;

    private String msg;

    CertTypeEnum(Byte code,String mas){
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


    private static Map<Byte,CertTypeEnum> tmpMap = new HashMap<>(CertTypeEnum.values().length);

    public static CertTypeEnum getUserTypeEnum(Byte lifeCycle) {

        CertTypeEnum userTypeEnum = tmpMap.get(lifeCycle);
        if(userTypeEnum == null){
            for (CertTypeEnum cycleEnum : CertTypeEnum.values()){
                tmpMap.put(cycleEnum.getCode(),cycleEnum);
                if (cycleEnum.getCode().equals(lifeCycle)){
                    userTypeEnum = cycleEnum;
                }
            }
        }
        return userTypeEnum;
    }

}
