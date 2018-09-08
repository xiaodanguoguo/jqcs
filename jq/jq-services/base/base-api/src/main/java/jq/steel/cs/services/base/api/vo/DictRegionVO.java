package jq.steel.cs.services.base.api.vo;

import java.util.List;

/**
 * @Auther: zhaoyichen
 */
public class DictRegionVO {
    //id
    private Long id;
    //上级地区
    private String regionCode;
    //地区名称
    private String regionName;
    //上级id
    private Integer parentId;
    //状态
    private Byte status;

    // 子菜单
    private List<DictRegionVO> childMenus;

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
        this.regionCode = regionCode == null ? null : regionCode.trim();
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
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

    public List<DictRegionVO> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<DictRegionVO> childMenus) {
        this.childMenus = childMenus;
    }

}
