package jq.steel.cs.services.base.api.vo;

/**
 * 供应商
 * @author ZhangX
 *
 */
public class MyRecommendationVO {
	
	private Long id;
	
    /**
     * 企业客户
     * ENT_CLIENT_INFO 表
	 * 经营范围
	 */
    private Long businessScope;
    
    /**
     * 企业客户
     * ENT_CLIENT_INFO 表
	 * 采购组织
	 */
    private Long purchOrgan;
    
    /**
     * 企业客户
     * ENT_CLIENT_INFO 表
	 * 企业名称
	 */
    private String name;
    
    /**
     * 企业客户
     * ENT_CLIENT_INFO 表
   	 * 注册资金
   	 */
    private Long regCapital;
    
    /**
     * 企业客户
     * ENT_CLIENT_INFO 表
	 * 企业性质
	 */
    private Byte corpType;
    
    /**
     * 联系信息
     * CONTACT_INFO 表
     * 联系电话
     */
	private String mobileNumber;
	
	/**
	 * 联系信息
     * CONTACT_INFO 表
	 * 联系人
	 */
	private String linkman;
	
	/**
	 * 联系信息
     * CONTACT_INFO 表
	 * 联系人地址
	 */
	private String address;
	
	/**
	 * 供货范围表
     * SUPPLY_SCOPE 表
	 * 物料大类码
	 */
	private String hugeCode;
	
	/**
	 * 供货范围表
     * SUPPLY_SCOPE 表
	 * 物料大类名称
	 */
	private String hugeMaterialName;
	
	/**
	 * 供货范围表
     * SUPPLY_SCOPE 表
	 * 是否冻结
	 */
	private Byte freeze;
	
	/**
	 * 供货范围表
     * SUPPLY_SCOPE 表
	 * 物料代码
	 */
	private String materielCode;
	
	/**
	 * 供货范围表
     * SUPPLY_SCOPE 表
	 * 物料名称
	 */
	private String materialNameCn;
	
	/**
	 * 供货范围表
     * SUPPLY_SCOPE 表
	 * 备注
	 */
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(Long businessScope) {
		this.businessScope = businessScope;
	}

	public Long getPurchOrgan() {
		return purchOrgan;
	}

	public void setPurchOrgan(Long purchOrgan) {
		this.purchOrgan = purchOrgan;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getRegCapital() {
		return regCapital;
	}

	public void setRegCapital(Long regCapital) {
		this.regCapital = regCapital;
	}

	public Byte getCorpType() {
		return corpType;
	}

	public void setCorpType(Byte corpType) {
		this.corpType = corpType;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHugeCode() {
		return hugeCode;
	}

	public void setHugeCode(String hugeCode) {
		this.hugeCode = hugeCode;
	}

	public String getHugeMaterialName() {
		return hugeMaterialName;
	}

	public void setHugeMaterialName(String hugeMaterialName) {
		this.hugeMaterialName = hugeMaterialName;
	}

	public Byte getFreeze() {
		return freeze;
	}

	public void setFreeze(Byte freeze) {
		this.freeze = freeze;
	}

	public String getMaterielCode() {
		return materielCode;
	}

	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
	}

	public String getMaterialNameCn() {
		return materialNameCn;
	}

	public void setMaterialNameCn(String materialNameCn) {
		this.materialNameCn = materialNameCn;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
     

}
