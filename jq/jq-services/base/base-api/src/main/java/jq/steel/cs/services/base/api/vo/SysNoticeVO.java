package jq.steel.cs.services.base.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by MrLi on 2018/7/19.
 */
public class SysNoticeVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3661555625638166397L;

	private Long id;

    private String title;

    private String noticeType;

    private String publishObj;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String content;

    private byte status;

    private byte isDelete;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date stTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date enTime;

    private String opt; //标记是什么数据

    private int pageSize = 10;

    private int pageNum = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType == null ? null : noticeType.trim();
    }

    public String getPublishObj() {
        return publishObj;
    }

    public void setPublishObj(String publishObj) {
        this.publishObj = publishObj == null ? null : publishObj.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public byte getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(byte isDelete) {
		this.isDelete = isDelete;
	}

	public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
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
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getStTime() {
        return stTime;
    }

    public void setStTime(Date stTime) {
        this.stTime = stTime;
    }

    public Date getEnTime() {
        return enTime;
    }

    public void setEnTime(Date enTime) {
        this.enTime = enTime;
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
