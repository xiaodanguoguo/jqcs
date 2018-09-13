package jq.steel.cs.services.cust.api.vo;

import java.math.BigDecimal;

public class CrmMillPhysicsDataVO {

    private BigDecimal sid;

    private String kurztext ;   //物理信息值

    private String millSheetNo;

    private String zcharg ;


    public BigDecimal getSid() {
        return sid;
    }

    public void setSid(BigDecimal sid) {
        this.sid = sid;
    }

    public String getKurztext() {
        return kurztext;
    }

    public void setKurztext(String kurztext) {
        this.kurztext = kurztext;
    }

    public String getMillSheetNo() {
        return millSheetNo;
    }

    public void setMillSheetNo(String millSheetNo) {
        this.millSheetNo = millSheetNo;
    }

    public String getZcharg() {
        return zcharg;
    }

    public void setZcharg(String zcharg) {
        this.zcharg = zcharg;
    }

}
