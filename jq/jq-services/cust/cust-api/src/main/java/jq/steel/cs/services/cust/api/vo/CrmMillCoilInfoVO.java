package jq.steel.cs.services.cust.api.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CrmMillCoilInfoVO implements Serializable {
    private Long sid;

    private String state;

    private String zcharg;      //钢卷编号

    private String specs;       //钢卷规格

    private String zlosmenge;   //钢卷重量

    private int zJiShu;         // 件数,数量

    private String zlph;        //炉(批)号

    private MillSheetHeadVO millSheetHead;

    //用于存储钢卷对应的化学数据
    private List<CrmMillChemistryDataVO> listForMillChemistryData = new ArrayList<>();

    //用于存储钢卷对应的物理数据
    private List<CrmMillPhysicsDataVO> listForMillPhysicsData = new ArrayList<>();


    private int showFlag = 1;


    public MillSheetHeadVO getMillSheetHead() { return millSheetHead; }

    public void setMillSheetHead(MillSheetHeadVO millSheetHead) { this.millSheetHead = millSheetHead; }

    public String getZlph() { return zlph; }

    public void setZlph(String zlph) { this.zlph = zlph; }



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

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

    public int getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(int showFlag) {
        this.showFlag = showFlag;
    }
}
