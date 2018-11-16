package jq.steel.cs.services.cust.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MillLabelVO implements Serializable {

    private int state;

    private Long sid;

    private String zcpmc;

    private String zcharg;              //批次

    private String zph;                 //牌号

    private String specs;               //规格

    private String zzxbz;

    private BigDecimal zlosmenge;

    private BigDecimal inputZlosmenge;

    private String coilId;

    private Short supportNumber;

    private String operatorId;          //作业员ID

    private Date operationTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date productionTime;        //生产时间

    private String productionTimeStr;

    private String id;

    private String makeUp;

    private String inputSign;

    private String remark;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getProductionTimeStr() {
        return productionTimeStr;
    }

    public void setProductionTimeStr(String productionTimeStr) {
        this.productionTimeStr = productionTimeStr;
    }

    private Integer version;

    private static final long serialVersionUID = 1L;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getZcpmc() {
        return zcpmc;
    }

    public void setZcpmc(String zcpmc) {
        this.zcpmc = zcpmc == null ? null : zcpmc.trim();
    }

    public String getZcharg() {
        return zcharg;
    }

    public void setZcharg(String zcharg) {
        this.zcharg = zcharg == null ? null : zcharg.trim();
    }

    public String getZph() {
        return zph;
    }

    public void setZph(String zph) {
        this.zph = zph == null ? null : zph.trim();
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs == null ? null : specs.trim();
    }

    public String getZzxbz() {
        return zzxbz;
    }

    public void setZzxbz(String zzxbz) {
        this.zzxbz = zzxbz == null ? null : zzxbz.trim();
    }

    public BigDecimal getZlosmenge() {
        return zlosmenge;
    }

    public void setZlosmenge(BigDecimal zlosmenge) {
        this.zlosmenge = zlosmenge;
    }

    public BigDecimal getInputZlosmenge() {
        return inputZlosmenge;
    }

    public void setInputZlosmenge(BigDecimal inputZlosmenge) {
        this.inputZlosmenge = inputZlosmenge;
    }

    public String getCoilId() {
        return coilId;
    }

    public void setCoilId(String coilId) {
        this.coilId = coilId == null ? null : coilId.trim();
    }

    public Short getSupportNumber() {
        return supportNumber;
    }

    public void setSupportNumber(Short supportNumber) {
        this.supportNumber = supportNumber;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Date getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(Date productionTime) {
        this.productionTime = productionTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMakeUp() {
        return makeUp;
    }

    public void setMakeUp(String makeUp) {
        this.makeUp = makeUp == null ? null : makeUp.trim();
    }

    public String getInputSign() {
        return inputSign;
    }

    public void setInputSign(String inputSign) {
        this.inputSign = inputSign == null ? null : inputSign.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sid=").append(sid);
        sb.append(", zcpmc=").append(zcpmc);
        sb.append(", zcharg=").append(zcharg);
        sb.append(", zph=").append(zph);
        sb.append(", specs=").append(specs);
        sb.append(", zzxbz=").append(zzxbz);
        sb.append(", zlosmenge=").append(zlosmenge);
        sb.append(", inputZlosmenge=").append(inputZlosmenge);
        sb.append(", coilId=").append(coilId);
        sb.append(", supportNumber=").append(supportNumber);
        sb.append(", operatorId=").append(operatorId);
        sb.append(", operationTime=").append(operationTime);
        sb.append(", productionTime=").append(productionTime);
        sb.append(", id=").append(id);
        sb.append(", makeUp=").append(makeUp);
        sb.append(", inputSign=").append(inputSign);
        sb.append(", remark=").append(remark);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdDt=").append(createdDt);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedDt=").append(updatedDt);
        sb.append(", version=").append(version);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}