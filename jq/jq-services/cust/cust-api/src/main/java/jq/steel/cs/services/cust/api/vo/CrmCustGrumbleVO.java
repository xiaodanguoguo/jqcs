package jq.steel.cs.services.cust.api.vo;

import java.util.Date;

public class CrmCustGrumbleVO {
    private Long cid;

    // 大类id
    private Long categoryId;

    // 产品id
    private Long productId;

    // 创建人
    private String submitBy;

    // 抱怨内容
    private String grumbleContent;

    private Long createByid;

    private Date createDt;

    private Long updateByid;

    private Date updateDt;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSubmitBy() {
        return submitBy;
    }

    public void setSubmitBy(String submitBy) {
        this.submitBy = submitBy == null ? null : submitBy.trim();
    }

    public String getGrumbleContent() {
        return grumbleContent;
    }

    public void setGrumbleContent(String grumbleContent) {
        this.grumbleContent = grumbleContent == null ? null : grumbleContent.trim();
    }

    public Long getCreateByid() {
        return createByid;
    }

    public void setCreateByid(Long createByid) {
        this.createByid = createByid;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Long getUpdateByid() {
        return updateByid;
    }

    public void setUpdateByid(Long updateByid) {
        this.updateByid = updateByid;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }
}