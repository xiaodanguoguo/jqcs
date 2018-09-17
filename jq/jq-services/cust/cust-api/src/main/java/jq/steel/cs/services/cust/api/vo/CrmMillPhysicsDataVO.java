package jq.steel.cs.services.cust.api.vo;

import java.math.BigDecimal;

public class CrmMillPhysicsDataVO {

    private BigDecimal sid;

    private String kurztext ;   //物理信息值

    private String originalInput;


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

    public String getOriginalInput() {
        return originalInput;
    }

    public void setOriginalInput(String originalInput) {
        this.originalInput = originalInput;
    }
}
