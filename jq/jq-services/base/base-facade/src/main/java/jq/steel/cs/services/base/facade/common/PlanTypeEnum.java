package jq.steel.cs.services.base.facade.common;

import java.util.HashMap;
import java.util.Map;

public enum PlanTypeEnum {


    MONTHLY((byte)1,"月度计划"),
    QUARTER((byte)2,"季度计划"),
    YEAR((byte)3,"年度计划");


    private byte code;

    private String msg;

    PlanTypeEnum(Byte code,String mas){
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


    private static Map<Byte,PlanTypeEnum> tmpMap = new HashMap<>(PlanTypeEnum.values().length);

    public static PlanTypeEnum getPlanTypeEnum(Byte lifeCycle) {

        PlanTypeEnum planTypeEnum = tmpMap.get(lifeCycle);
        if(planTypeEnum == null){
            for (PlanTypeEnum cycleEnum : PlanTypeEnum.values()){
                tmpMap.put(cycleEnum.getCode(),cycleEnum);
                if (cycleEnum.getCode().equals(lifeCycle)){
                    planTypeEnum = cycleEnum;
                }
            }
        }
        return planTypeEnum;
    }

}
