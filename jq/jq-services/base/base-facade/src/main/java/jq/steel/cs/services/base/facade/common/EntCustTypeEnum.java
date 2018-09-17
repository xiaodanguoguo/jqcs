package jq.steel.cs.services.base.facade.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 企业客户类型
 */
public enum EntCustTypeEnum {

    Supplier((byte)1,"供应商"),
    buyer((byte)2,"采购商");

    private byte code;

    private String msg;

    EntCustTypeEnum(Byte code,String mas){
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


    private static Map<Byte,EntCustTypeEnum> tmpMap = new HashMap<>(EntCustTypeEnum.values().length);

    public static EntCustTypeEnum getEntCustTypeEnum(Byte lifeCycle) {

        EntCustTypeEnum entCustTypeEnum = tmpMap.get(lifeCycle);
        if(entCustTypeEnum == null){
            for (EntCustTypeEnum cycleEnum : EntCustTypeEnum.values()){
                tmpMap.put(cycleEnum.getCode(),cycleEnum);
                if (cycleEnum.getCode().equals(lifeCycle)){
                    entCustTypeEnum = cycleEnum;
                }
            }
        }
        return entCustTypeEnum;
    }
}
