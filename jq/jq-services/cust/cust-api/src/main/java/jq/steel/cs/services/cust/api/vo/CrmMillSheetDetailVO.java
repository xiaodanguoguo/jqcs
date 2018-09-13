package jq.steel.cs.services.cust.api.vo;

import java.io.Serializable;
import java.math.BigDecimal;

//钢卷明细VO
public class CrmMillSheetDetailVO implements Serializable {

    private String millSheetNo;     //质保书编号

    private Integer showFlag;        //元素是否显示的状态

    private String zcharg;          //钢卷编号

    private String specs;           //钢卷规格

    private String zlosmenge;       //钢卷重量

    //----------------------物理数据----------------------------
    private BigDecimal PSid;

    private String pKurztext ;   //物理信息值
    //--------------------------------------------------
    private BigDecimal CSid;

    private String cKurztext;        //化学特性描述

    private String original_input;  //化学特性描述对应的值
    //----------------------化学数据----------------------------

    public BigDecimal getPSid() { return PSid; }

    public void setPSid(BigDecimal PSid) { this.PSid = PSid; }

    public BigDecimal getCSid() { return CSid; }

    public void setCSid(BigDecimal CSid) { this.CSid = CSid; }

    public String getMillSheetNo() { return millSheetNo; }

    public void setMillSheetNo(String millSheetNo) { this.millSheetNo = millSheetNo; }

    public Integer getShowFlag() { return showFlag; }

    public void setShowFlag(Integer showFlag) { this.showFlag = showFlag; }

    public String getpKurztext() { return pKurztext; }

    public void setpKurztext(String pKurztext) { this.pKurztext = pKurztext; }

    public String getcKurztext() { return cKurztext; }

    public void setcKurztext(String cKurztext) { this.cKurztext = cKurztext; }

    public String getZcharg() { return zcharg; }

    public void setZcharg(String zcharg) {
        this.zcharg = zcharg;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getZlosmenge() {
        return zlosmenge;
    }

    public void setZlosmenge(String zlosmenge) {
        this.zlosmenge = zlosmenge;
    }

    public String getOriginal_input() {
        return original_input;
    }

    public void setOriginal_input(String original_input) {
        this.original_input = original_input;
    }

    public CrmMillSheetDetailVO(String millSheetNo, Integer showFlag) {
        this.millSheetNo = millSheetNo;
        this.showFlag = showFlag;
    }

    public CrmMillSheetDetailVO() { }
}
