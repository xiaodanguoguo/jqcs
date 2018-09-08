package com.ebase.core.page;

import com.ebase.core.page.constant.SortEnums;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @Description : 分页基类
 */
public class PageBasic implements Serializable {

    /**
     * 删除状态
     * 1 正常 0删除
     */
    private Integer recordStatus;
    /**
     * 查询页码
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 起始行
     */
    private Integer startRow;

    /**
     * 列表排序字段
     */
    private String sortField;

    /**
     * 列表排序方式
     */
    private String sortType;

    public PageBasic() {
        recordStatus = 1;
    }

    /**
     * 初始化页码/每页条数
     */
    public PageBasic init() {
        if (null == pageNum) {
            //默认从第一页开始
            this.pageNum = 1;
        }
        if (null == pageSize) {
            //默认一页显示20条
            this.pageSize = 20;
        }
        return initSort();
    }

    /**
     * 初始化页码/每页条数
     */
    public PageBasic init(int total) {
        if (null == pageNum) {
            //默认从第一页开始
            this.pageNum = 1;
        }
        if (null == pageSize) {
            //默认一页显示20条
            this.pageSize = 20;
        }
        //总页数
        int pages = total / pageSize + (total % pageSize == 0 ? 0 : 1);
        if (pageNum > pages) {
            pageNum = pages;
        }
        this.startRow = this.pageNum > 0 ? (this.pageNum - 1) * this.pageSize : 0;
        return initSort();
    }

    /**
     * 初始化排序方式
     *
     * @return PageBasic
     */
    public PageBasic initSort() {
        if (StringUtils.isNotBlank(sortField)) {
            this.sortField = SortEnums.SortFieldEnum.SORT_FIELD_MAP.get(sortField);
        } else {
            this.sortField = "`CREATE_TIME`";
        }
        if (StringUtils.isNotBlank(sortType)) {
            this.sortType = SortEnums.SortTypeEnum.SORT_TYPE_MAP.get(sortType);
        } else {
            this.sortType = "DESC";
        }
        return this;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    @Override
    public String toString() {
        return "PageBasic{" + "recordStatus=" + recordStatus + ", pageNum=" + pageNum + ", pageSize=" + pageSize
                + ", startRow=" + startRow + ", sortField='" + sortField + '\'' + ", sortType='" + sortType + '\''
                + '}';
    }
}
