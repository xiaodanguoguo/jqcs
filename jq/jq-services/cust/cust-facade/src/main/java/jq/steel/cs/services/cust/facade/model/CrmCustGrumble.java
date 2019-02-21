package jq.steel.cs.services.cust.facade.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class CrmCustGrumble {
    private String factory;

    //厂家code
    private List<String> factorys;

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

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createDt;

    private Long updateByid;

    private Date updateDt;

    //菜单类别1抱怨2表扬
    private String cType;
    //公司
    private String customer;
    //产品名称
    private String productName;
    //类别  服务/产品
    private String tType;
    //反馈内容
    private String feedbackContent;


    //大类名称
    private String categoryName;
    //创建人名称
    private String createByName;
    //联系电话
    private String  phone;


    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDt;
    private String endDtStr;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDt;

    private String startDtStr;

    private  String orgType;

    private  String orgName;

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public List<String> getFactorys() {
        return factorys;
    }

    public void setFactorys(List<String> factorys) {
        this.factorys = factorys;
    }

    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    public String getEndDtStr() {
        return endDtStr;
    }

    public void setEndDtStr(String endDtStr) {
        this.endDtStr = endDtStr;
    }

    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public String getStartDtStr() {
        return startDtStr;
    }

    public void setStartDtStr(String startDtStr) {
        this.startDtStr = startDtStr;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String gettType() {
        return tType;
    }

    public void settType(String tType) {
        this.tType = tType;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

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