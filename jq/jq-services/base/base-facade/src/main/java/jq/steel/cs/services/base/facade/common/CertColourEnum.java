package jq.steel.csservices.base.facade.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: wangyu
 */
public enum CertColourEnum {

    COLOURLESS((byte)0,"无色"),
    YELLOW((byte)2,"黄色"),
    RED((byte)1,"红色");


    private byte code;

    private String msg;

    CertColourEnum(Byte code,String mas){
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


    private static Map<Byte,CertColourEnum> tmpMap = new HashMap<>(CertColourEnum.values().length);

    public static CertColourEnum getCertColourEnum(Byte lifeCycle) {

        CertColourEnum certColourEnum = tmpMap.get(lifeCycle);
        if(certColourEnum == null){
            for (CertColourEnum cycleEnum : CertColourEnum.values()){
                tmpMap.put(cycleEnum.getCode(),cycleEnum);
                if (cycleEnum.getCode().equals(lifeCycle)){
                    certColourEnum = cycleEnum;
                }
            }
        }
        return certColourEnum;
    }
}
