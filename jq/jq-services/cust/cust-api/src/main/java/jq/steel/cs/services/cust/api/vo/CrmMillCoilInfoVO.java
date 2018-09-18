package jq.steel.cs.services.cust.api.vo;

import java.util.ArrayList;
import java.util.List;

public class CrmMillCoilInfoVO {
    private String zcharg;      //钢卷编号

    private String specs;       //钢卷规格

    private String zlosmenge;   //钢卷重量

    private int zJiShu;// 件数

    //用于存储钢卷对应的化学数据
    private List<CrmMillChemistryDataVO> listForMillChemistryData = new ArrayList<>();

    //用于存储钢卷对应的物理数据
    private List<CrmMillPhysicsDataVO> listForMillPhysicsData = new ArrayList<>();



    public String getZcharg() { return zcharg; }

    public void setZcharg(String zcharg) { this.zcharg = zcharg; }

    public String getSpecs() { return specs; }

    public void setSpecs(String specs) { this.specs = specs; }

    public String getZlosmenge() { return zlosmenge; }

    public void setZlosmenge(String zlosmenge) { this.zlosmenge = zlosmenge; }

    public List<CrmMillChemistryDataVO> getListForMillChemistryData() {
        return listForMillChemistryData;
    }

    public void setListForMillChemistryData(List<CrmMillChemistryDataVO> listForMillChemistryData) {
        this.listForMillChemistryData = listForMillChemistryData;
    }

    public List<CrmMillPhysicsDataVO> getListForMillPhysicsData() {
        return listForMillPhysicsData;
    }

    public void setListForMillPhysicsData(List<CrmMillPhysicsDataVO> listForMillPhysicsData) {
        this.listForMillPhysicsData = listForMillPhysicsData;
    }

    public int getzJiShu() {
        return zJiShu;
    }

    public void setzJiShu(int zJiShu) {
        this.zJiShu = zJiShu;
    }
}
