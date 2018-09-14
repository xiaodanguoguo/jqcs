package jq.steel.cs.services.cust.facade.model;

import java.math.BigDecimal;

//钢卷对应的物理信息
public class CrmMillPhysicsData {

    private BigDecimal sid;

    private String kurztext ;   //物理信息值


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

}
