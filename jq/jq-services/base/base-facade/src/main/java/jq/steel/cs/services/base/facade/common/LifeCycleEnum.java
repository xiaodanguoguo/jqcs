package jq.steel.csservices.base.facade.common;

import java.util.HashMap;
import java.util.Map;

//生命周期
public enum LifeCycleEnum {

    REGISTER((byte)1,"注册"),
    RECOMMEND((byte)2,"推荐"),
    POTENTIAL((byte)3,"潜在"),
    QUALIFIED((byte)4,"合格"),
    ELIMINATE((byte)5,"淘汰");

    private byte code;

    private String msg;

    LifeCycleEnum(Byte code,String mas){
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


    private static Map<Byte,LifeCycleEnum> tmpMap = new HashMap<>(LifeCycleEnum.values().length);

    public static LifeCycleEnum getLifeCycleEnum(Byte lifeCycle) {

        LifeCycleEnum lifeCycleEnum = tmpMap.get(lifeCycle);
        if(lifeCycleEnum == null){
            for (LifeCycleEnum cycleEnum : LifeCycleEnum.values()){
                tmpMap.put(cycleEnum.getCode(),cycleEnum);
                if (cycleEnum.getCode().equals(lifeCycle)){
                    lifeCycleEnum = cycleEnum;
                }
            }
        }
        return lifeCycleEnum;
    }

    public static Boolean isRegister(Byte code){
        if(REGISTER.getCode().equals(code) || RECOMMEND.getCode().equals(code) || POTENTIAL.getCode().equals(code))
            return true;
        return false;
    }

    public static Boolean isQualified(Byte code){
        return QUALIFIED.getCode().equals(code) ? true : false;
    }

    public static Boolean isEliminate(Byte code){
        return ELIMINATE.getCode().equals(code) ? true : false;
    }
}
