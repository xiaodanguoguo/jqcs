package jq.steel.cs.services.cust.facade.model;

import java.util.ArrayList;
import java.util.List;

//钢卷信息
public class CrmMillCoilInfo {
    private Long sid;
    private String zcharg;      //钢卷编号

    private String specs;       //钢卷规格

    private String zlosmenge;   //钢卷重量

    private int zJiShu;// 件数

    //用于存储钢卷对应的化学数据
    private List<CrmMillChemistryData> listForMillChemistryData = new ArrayList<>();

    //用于存储钢卷对应的物理数据
    private List<CrmMillPhysicsData> listForMillPhysicsData = new ArrayList<>();

    private int showFlag;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getZcharg() { return zcharg; }

    public void setZcharg(String zcharg) { this.zcharg = zcharg; }

    public String getSpecs() { return specs; }

    public void setSpecs(String specs) { this.specs = specs; }

    public String getZlosmenge() { return zlosmenge; }

    public void setZlosmenge(String zlosmenge) { this.zlosmenge = zlosmenge; }

    public List<CrmMillChemistryData> getListForMillChemistryData() {
        return listForMillChemistryData;
    }

    public void setListForMillChemistryData(List<CrmMillChemistryData> listForMillChemistryData) {
        this.listForMillChemistryData = listForMillChemistryData;
    }

    public List<CrmMillPhysicsData> getListForMillPhysicsData() {
        return listForMillPhysicsData;
    }

    public void setListForMillPhysicsData(List<CrmMillPhysicsData> listForMillPhysicsData) {
        this.listForMillPhysicsData = listForMillPhysicsData;
    }

    public int getzJiShu() {
        return zJiShu;
    }

    public void setzJiShu(int zJiShu) {
        this.zJiShu = zJiShu;
    }

    public int getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(int showFlag) {
        this.showFlag = showFlag;
    }
}
