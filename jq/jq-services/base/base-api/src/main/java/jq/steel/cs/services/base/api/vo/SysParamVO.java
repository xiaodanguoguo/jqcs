package jq.steel.cs.services.base.api.vo;

import java.util.Date;

/**
 * @Auther: wangyu
 */
public class SysParamVO {

    //主键id
    private Long id;

    //参数代码
    private String paramCode;

    //参数名称
    private String paramName;

    //参数值
    private String paramValue;

    //扩展字段1
    private String ex1;

    //扩展字段2
    private String ex2;

    //状态
    private Byte status;

    //创建人
    private String createdBy;

    //创建时间
    private Date createdTime;

    //修改人
    private String updatedBy;

    //修改时间
    private Date updatedTime;

    private String opt; //标记是什么数据 "update" "delete"

    private int pageSize =10;

    private int pageNum = 1;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getEx1() {
        return ex1;
    }

    public void setEx1(String ex1) {
        this.ex1 = ex1;
    }

    public String getEx2() {
        return ex2;
    }

    public void setEx2(String ex2) {
        this.ex2 = ex2;
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
