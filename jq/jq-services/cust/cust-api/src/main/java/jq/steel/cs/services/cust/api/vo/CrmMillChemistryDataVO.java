package jq.steel.cs.services.cust.api.vo;

import java.math.BigDecimal;

public class CrmMillChemistryDataVO {
    private BigDecimal sid;

    private String kurztext;        //化学特性描述

    private String original_input;  //化学特性描述对应的值

    //----------------------------------------------------

    public BigDecimal getSid() { return sid; }

    public void setSid(BigDecimal sid) { this.sid = sid; }

    public String getKurztext() { return kurztext; }

    public void setKurztext(String kurztext) { this.kurztext = kurztext; }

    public String getOriginal_input() { return original_input; }

    public void setOriginal_input(String original_input) { this.original_input = original_input; }

}
