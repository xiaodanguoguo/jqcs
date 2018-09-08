package jq.steel.cs.services.base.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

//供应商分类
public class SupplierTypeVO {
    private Long id;

    private String supCateCode;
    
    private String supCateName;

    private Byte hierarchy;

    private Long parentId;

    private Byte isScore;

    private Byte isRate;

    private Byte isExamine;

    private String createdBy;

//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    private String updatedBy;

//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
    
    private List<SupplierTypeVO> supplierTypeVOs;
    
    private String opt;
    
    private String keyword;
    
    
    public List<SupplierTypeVO> getSupplierTypeVOs() {
		return supplierTypeVOs;
	}

	public void setSupplierTypeVOs(List<SupplierTypeVO> supplierTypeVOs) {
		this.supplierTypeVOs = supplierTypeVOs;
	}

	public String getSupCateCode() {
		return supCateCode;
	}

	public void setSupCateCode(String supCateCode) {
		this.supCateCode = supCateCode;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupCateName() {
        return supCateName;
    }

    public void setSupCateName(String supCateName) {
        this.supCateName = supCateName == null ? null : supCateName.trim();
    }

    public Byte getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(Byte hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Byte getIsScore() {
        return isScore;
    }

    public void setIsScore(Byte isScore) {
        this.isScore = isScore;
    }

    public Byte getIsRate() {
        return isRate;
    }

    public void setIsRate(Byte isRate) {
        this.isRate = isRate;
    }

    public Byte getIsExamine() {
        return isExamine;
    }

    public void setIsExamine(Byte isExamine) {
        this.isExamine = isExamine;
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
}