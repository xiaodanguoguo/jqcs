package jq.steel.cs.services.base.facade.model;


import jq.steel.cs.services.base.api.enumvo.Status;

import java.util.Date;

public class DictMeasUnitInfo {
    private Long id;

    private String code;            //代码

    private String unitTypeCode;    //计量单位代码

    private Byte isBase;            //是否基本单位

    private Status status;          //状态

    private String createdBy;       //创建人

    private Date createdTime;       //创建时间

    private String updatedBy;       //修改人

    private Date updatedTime;       //修改时间

    private String opt; //标记是什么数据

    private int pageSize =10;

    private int pageNum = 1;

    //一对一
    private DictUnitType dictUnitType;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

    public DictUnitType getDictUnitType() {
        return dictUnitType;
    }

    public void setDictUnitType(DictUnitType dictUnitType) {
        this.dictUnitType = dictUnitType;
    }
}