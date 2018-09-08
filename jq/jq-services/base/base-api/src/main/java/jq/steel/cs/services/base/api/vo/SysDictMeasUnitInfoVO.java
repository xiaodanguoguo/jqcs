package jq.steel.cs.services.base.api.vo;

import java.util.Date;

/**
 * @Auther: zhaoyichen
 */
public class SysDictMeasUnitInfoVO {
    private Long id;

    private String code;

    private String unitTypeCode;

    private Byte isBase;

    private Byte status;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    private String opt; //标记是什么数据 "update" "delete"

    private int pageSize =10;

    private int pageNum = 1;

    // 一对一
    private DictUnitTypeVO dictUnitTypeVO;

    public DictUnitTypeVO getDictUnitTypeVO() {
        return dictUnitTypeVO;
    }

    public void setDictUnitTypeVO(DictUnitTypeVO dictUnitTypeVO) {
        this.dictUnitTypeVO = dictUnitTypeVO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUnitTypeCode() {
        return unitTypeCode;
    }

    public void setUnitTypeCode(String unitTypeCode) {
        this.unitTypeCode = unitTypeCode;
    }

    public Byte getIsBase() {
        return isBase;
    }

    public void setIsBase(Byte isBase) {
        this.isBase = isBase;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
