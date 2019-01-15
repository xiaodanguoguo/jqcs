package jq.steel.cs.services.cust.facade.model;

import java.math.BigDecimal;
import java.util.Date;

public class MillSheetHead {

    //厂线
    private String millLine;

    private Long sid;

    private String zchehao;     //车号

    private String zcharg;

    private String millSheetNo; //质证书编号

    private String zkdauf;

    private String zkunnr;

    private String zkunwe;  //送达方

    private String zdaozhan; //到站

    private String vbelnVl;

    private Date lfdat;     //发车时间

    private String zhth;

    private String zcpmc;   //产品名称

    private String zph;     //牌号

    private String zzxbz;   //标准

    private String zfjsm;   //附加说明

    private Long totalZjishu;

    private BigDecimal totalZlosmenge;

    private String zjishuUnit;

    private String zmeins;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    private String name;        //收货单位名称<crm_customer中的name1客户名称>

    private String sizeMark ;    //合同规格

    private String productName ; //产品名称


    public String getMillLine() {
        return millLine;
    }

    public void setMillLine(String millLine) {
        this.millLine = millLine;
    }

    public String getSizeMark() { return sizeMark; }

    public void setSizeMark(String sizeMark) { this.sizeMark = sizeMark; }

    public String getProductName() {  return productName; }

    public void setProductName(String productName) { this.productName = productName; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZjhzt() {
        return zjhzt;
    }

    public void setZjhzt(String zjhzt) {
        this.zjhzt = zjhzt;
    }

    private String zjhzt;   //交货状态

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getZchehao() {
        return zchehao;
    }

    public void setZchehao(String zchehao) {
        this.zchehao = zchehao == null ? null : zchehao.trim();
    }

    public String getZcharg() {
        return zcharg;
    }

    public void setZcharg(String zcharg) {
        this.zcharg = zcharg == null ? null : zcharg.trim();
    }

    public String getMillSheetNo() {
        return millSheetNo;
    }

    public void setMillSheetNo(String millSheetNo) {
        this.millSheetNo = millSheetNo == null ? null : millSheetNo.trim();
    }

    public String getZkdauf() {
        return zkdauf;
    }

    public void setZkdauf(String zkdauf) {
        this.zkdauf = zkdauf == null ? null : zkdauf.trim();
    }

    public String getZkunnr() {
        return zkunnr;
    }

    public void setZkunnr(String zkunnr) {
        this.zkunnr = zkunnr == null ? null : zkunnr.trim();
    }

    public String getZkunwe() {
        return zkunwe;
    }

    public void setZkunwe(String zkunwe) {
        this.zkunwe = zkunwe == null ? null : zkunwe.trim();
    }

    public String getZdaozhan() {
        return zdaozhan;
    }

    public void setZdaozhan(String zdaozhan) {
        this.zdaozhan = zdaozhan == null ? null : zdaozhan.trim();
    }

    public String getVbelnVl() {
        return vbelnVl;
    }

    public void setVbelnVl(String vbelnVl) {
        this.vbelnVl = vbelnVl == null ? null : vbelnVl.trim();
    }

    public Date getLfdat() {
        return lfdat;
    }

    public void setLfdat(Date lfdat) {
        this.lfdat = lfdat;
    }

    public String getZhth() {
        return zhth;
    }

    public void setZhth(String zhth) {
        this.zhth = zhth == null ? null : zhth.trim();
    }

    public String getZcpmc() {
        return zcpmc;
    }

    public void setZcpmc(String zcpmc) {
        this.zcpmc = zcpmc == null ? null : zcpmc.trim();
    }

    public String getZph() {
        return zph;
    }

    public void setZph(String zph) {
        this.zph = zph == null ? null : zph.trim();
    }

    public String getZzxbz() {
        return zzxbz;
    }

    public void setZzxbz(String zzxbz) {
        this.zzxbz = zzxbz == null ? null : zzxbz.trim();
    }

    public String getZfjsm() {
        return zfjsm;
    }

    public void setZfjsm(String zfjsm) {
        this.zfjsm = zfjsm == null ? null : zfjsm.trim();
    }

    public Long getTotalZjishu() {
        return totalZjishu;
    }

    public void setTotalZjishu(Long totalZjishu) {
        this.totalZjishu = totalZjishu;
    }

    public BigDecimal getTotalZlosmenge() {
        return totalZlosmenge;
    }

    public void setTotalZlosmenge(BigDecimal totalZlosmenge) {
        this.totalZlosmenge = totalZlosmenge;
    }

    public String getZjishuUnit() {
        return zjishuUnit;
    }

    public void setZjishuUnit(String zjishuUnit) {
        this.zjishuUnit = zjishuUnit == null ? null : zjishuUnit.trim();
    }

    public String getZmeins() {
        return zmeins;
    }

    public void setZmeins(String zmeins) {
        this.zmeins = zmeins == null ? null : zmeins.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


    //-------------------10.26,添加字段,lujiawei----

    private String millBigType;     //产品大类

    private String contractVolume;  //合同重量

    private String originalWeight;  //原卷重量

    private String originalCarNo;   //原车号

    public String getMillBigType() {
        return millBigType;
    }

    public void setMillBigType(String millBigType) {
        this.millBigType = millBigType;
    }

    public String getContractVolume() {
        return contractVolume;
    }

    public void setContractVolume(String contractVolume) {
        this.contractVolume = contractVolume;
    }

    public String getOriginalWeight() {
        return originalWeight;
    }

    public void setOriginalWeight(String originalWeight) {
        this.originalWeight = originalWeight;
    }

    public String getOriginalCarNo() {
        return originalCarNo;
    }

    public void setOriginalCarNo(String originalCarNo) {
        this.originalCarNo = originalCarNo;
    }
}