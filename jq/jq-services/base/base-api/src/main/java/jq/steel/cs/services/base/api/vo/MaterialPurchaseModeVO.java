package jq.steel.cs.services.base.api.vo;

import java.util.Date;

/**
 * 资质信息
 * @Auther: wangyu
 */
public class MaterialPurchaseModeVO {
    private Long id;

    private Long materialId;

    private Byte purchaseModeType;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    private String opt;

    private DictSysVO dictSys;

    public Byte getPurchaseModeType() {
        return purchaseModeType;
    }

    public void setPurchaseModeType(Byte purchaseModeType) {
        this.purchaseModeType = purchaseModeType;
    }

    public DictSysVO getDictSys() {
        return dictSys;
    }

    public void setDictSys(DictSysVO dictSys) {
        this.dictSys = dictSys;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
