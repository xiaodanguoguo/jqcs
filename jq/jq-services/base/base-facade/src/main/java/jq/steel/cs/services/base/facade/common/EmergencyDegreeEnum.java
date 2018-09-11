package jq.steel.csservices.base.facade.common;

import java.util.HashMap;
import java.util.Map;

public enum EmergencyDegreeEnum {

    COMMONLY((byte)1,"一般"),
    URGENT((byte)2,"加急");


    private byte code;

    private String msg;

    EmergencyDegreeEnum(Byte code,String mas){
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


    private static Map<Byte,EmergencyDegreeEnum> tmpMap = new HashMap<>(EmergencyDegreeEnum.values().length);

    public static EmergencyDegreeEnum getEmergencyDegreeEnum(Byte lifeCycle) {

        EmergencyDegreeEnum emergencyDegreeEnum = tmpMap.get(lifeCycle);
        if(emergencyDegreeEnum == null){
            for (EmergencyDegreeEnum cycleEnum : EmergencyDegreeEnum.values()){
                tmpMap.put(cycleEnum.getCode(),cycleEnum);
                if (cycleEnum.getCode().equals(lifeCycle)){
                    emergencyDegreeEnum = cycleEnum;
                }
            }
        }
        return emergencyDegreeEnum;
    }

}
