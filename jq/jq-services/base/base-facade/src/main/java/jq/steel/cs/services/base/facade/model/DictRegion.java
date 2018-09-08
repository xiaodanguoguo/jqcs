package jq.steel.cs.services.base.facade.model;

import java.util.List;

public class DictRegion {
    private Long id;

    private String regionCode;

    private String regionName;

    private Integer parentId;

    private Byte status;

    private List<DictRegion> childMenus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public List<DictRegion> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<DictRegion> childMenus) {
        this.childMenus = childMenus;
    }
}