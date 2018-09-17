package jq.steel.cs.services.base.facade.model;


import jq.steel.cs.services.base.facade.common.NoticeIsDelete;
import jq.steel.cs.services.base.facade.common.NoticeStatus;

import java.util.Date;

public class SysNotice {

    //主键
    private Long id;

    //标题
    private String title;

    //公告类型
    private String noticeType;

    //发布对象
    private String publishObj;

    //开始时间
    private Date startTime;

    //结束时间
    private Date endTime;

    //内容
    private String content;

    //状态
    private NoticeStatus status;

    //    是否删除
    private NoticeIsDelete isDelete;

    //    创建人
    private String createdBy;

    //    创建时间
    private Date createdTime;

    //    修改人
    private String updatedBy;

    //    修改时间
    private Date updatedTime;

    private String opt; //标记是什么数据

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
        if (status != null)
            return status.getCode();
        return 0;
    }

    public void setStatus(byte code) {
        this.status = NoticeStatus.getNoticeStatus(code);
    }

    public byte getIsDelete() {
        if (isDelete != null)
            return isDelete.getCode();
        return 0;
    }

    public void setIsDelete(byte isDelete) {
        this.isDelete = NoticeIsDelete.getIsDelete(isDelete);
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

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }
}