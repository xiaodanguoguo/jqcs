package jq.steel.cs.services.base.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jq.steel.cs.services.base.api.enumvo.Status;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MaterielInfoVO {
	private Long id;

    private String parentId;

	private Long typeId;

	private Long materialTypeId;

	private Long custId;

	private String materialCode;

	private String shortDesc;

	private String longDesc;

	private String materialNameCn;

	private String materialNameEn;

	private String modelSpec;

	private BigDecimal buggetPrice;

	private Byte baseUnit;

	private Byte substance;

	private String adviseManufacturerInfo;

	private String oidMaterialCode;

	private String materialAddInfo;

	private String texture;

	private Status status;

	private Byte purchaseTexture;

	private Long sourceSearch;

	private Long execute;

	private String createCode;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	private Byte carding;

	private Byte isAppointment;

	private Byte isNotTender;

	private String manufacturer;

	private String manufacturerCargoCode;

	private String supplierCargoCode;

	private String createdBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createdTime;

	private String updatedBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updatedTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    public Long getMaterialTypeId() {
		return materialTypeId;
	}

	public void setMaterialTypeId(Long materialTypeId) {
		this.materialTypeId = materialTypeId;
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

	public String getHugeCode() {
		return hugeCode;
	}

	public void setHugeCode(String hugeCode) {
		this.hugeCode = hugeCode;
	}

	public String getMiddleCode() {
		return middleCode;
	}

	public void setMiddleCode(String middleCode) {
		this.middleCode = middleCode;
	}

	public String getLittleCode() {
		return littleCode;
	}

	public void setLittleCode(String littleCode) {
		this.littleCode = littleCode;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;

    private String hugeCode;

    private String middleCode;

    private String littleCode;

    private String brandName;

    public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	private List<MaterialPictureVO> pictures;

	//物料采购模式
	private List<MaterialPurchaseModeVO> materialPurchaseModes;

	//品牌制造商
	private List<BrandManufacturerVO> brandManufacturers;

	//物料制造商关联
	private List<SupplierMaterialVO> supplierMaterials;

	//物料供应商关联
	private List<SupplierMaterialVO> supplierMaterialTwo;

	//物料类型查询
	private List<MaterielTypeVO> materielTypes;
	//叶类
	private String leafClass;
	//中类
	private String middleClass;
	//大类
	private String largeClass;

	public String getLeafClass() {
		return leafClass;
	}

	public void setLeafClass(String leafClass) {
		this.leafClass = leafClass;
	}

	public String getMiddleClass() {
		return middleClass;
	}

	public void setMiddleClass(String middleClass) {
		this.middleClass = middleClass;
	}

	public String getLargeClass() {
		return largeClass;
	}

	public void setLargeClass(String largeClass) {
		this.largeClass = largeClass;
	}

	public List<MaterielTypeVO> getMaterielTypes() {
		return materielTypes;
	}

	public void setMaterielTypes(List<MaterielTypeVO> materielTypes) {
		this.materielTypes = materielTypes;
	}

	public List<MaterialPurchaseModeVO> getMaterialPurchaseModes() {
		return materialPurchaseModes;
	}

	public void setMaterialPurchaseModes(List<MaterialPurchaseModeVO> materialPurchaseModes) {
		this.materialPurchaseModes = materialPurchaseModes;
	}

	public List<BrandManufacturerVO> getBrandManufacturers() {
		return brandManufacturers;
	}

	public void setBrandManufacturers(List<BrandManufacturerVO> brandManufacturers) {
		this.brandManufacturers = brandManufacturers;
	}

	public List<SupplierMaterialVO> getSupplierMaterials() {
		return supplierMaterials;
	}

	public void setSupplierMaterials(List<SupplierMaterialVO> supplierMaterials) {
		this.supplierMaterials = supplierMaterials;
	}

	public List<SupplierMaterialVO> getSupplierMaterialTwo() {
		return supplierMaterialTwo;
	}

	public void setSupplierMaterialTwo(List<SupplierMaterialVO> supplierMaterialTwo) {
		this.supplierMaterialTwo = supplierMaterialTwo;
	}

	public List<MaterialPictureVO> getPictures() {
		return pictures;
	}

	public void setPictures(List<MaterialPictureVO> pictures) {
		this.pictures = pictures;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode == null ? null : materialCode.trim();
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc == null ? null : shortDesc.trim();
	}

	public String getLongDesc() {
		return longDesc;
	}

	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc == null ? null : longDesc.trim();
	}

	public String getMaterialNameCn() {
		return materialNameCn;
	}

	public void setMaterialNameCn(String materialNameCn) {
		this.materialNameCn = materialNameCn == null ? null : materialNameCn.trim();
	}

	public String getMaterialNameEn() {
		return materialNameEn;
	}

	public void setMaterialNameEn(String materialNameEn) {
		this.materialNameEn = materialNameEn == null ? null : materialNameEn.trim();
	}

	public String getModelSpec() {
		return modelSpec;
	}

	public void setModelSpec(String modelSpec) {
		this.modelSpec = modelSpec == null ? null : modelSpec.trim();
	}

	public BigDecimal getBuggetPrice() {
		return buggetPrice;
	}

	public void setBuggetPrice(BigDecimal buggetPrice) {
		this.buggetPrice = buggetPrice;
	}

	public Byte getBaseUnit() {
		return baseUnit;
	}

	public void setBaseUnit(Byte baseUnit) {
		this.baseUnit = baseUnit;
	}

	public Byte getSubstance() {
		return substance;
	}

	public void setSubstance(Byte substance) {
		this.substance = substance;
	}

	public String getAdviseManufacturerInfo() {
		return adviseManufacturerInfo;
	}

	public void setAdviseManufacturerInfo(String adviseManufacturerInfo) {
		this.adviseManufacturerInfo = adviseManufacturerInfo == null ? null : adviseManufacturerInfo.trim();
	}

	public String getOidMaterialCode() {
		return oidMaterialCode;
	}

	public void setOidMaterialCode(String oidMaterialCode) {
		this.oidMaterialCode = oidMaterialCode == null ? null : oidMaterialCode.trim();
	}

	public String getMaterialAddInfo() {
		return materialAddInfo;
	}

	public void setMaterialAddInfo(String materialAddInfo) {
		this.materialAddInfo = materialAddInfo == null ? null : materialAddInfo.trim();
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture == null ? null : texture.trim();
	}

	public String getStatus() {
		if (this.status != null)
			return status.getCode();
		return "";
	}

	public void setStatus(String code) {
		this.status = Status.getStatus(code);
	}

	public Byte getPurchaseTexture() {
		return purchaseTexture;
	}

	public void setPurchaseTexture(Byte purchaseTexture) {
		this.purchaseTexture = purchaseTexture;
	}

	public Long getSourceSearch() {
		return sourceSearch;
	}

	public void setSourceSearch(Long sourceSearch) {
		this.sourceSearch = sourceSearch;
	}

	public Long getExecute() {
		return execute;
	}

	public void setExecute(Long execute) {
		this.execute = execute;
	}

	public String getCreateCode() {
		return createCode;
	}

	public void setCreateCode(String createCode) {
		this.createCode = createCode == null ? null : createCode.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Byte getCarding() {
		return carding;
	}

	public void setCarding(Byte carding) {
		this.carding = carding;
	}

	public Byte getIsAppointment() {
		return isAppointment;
	}

	public void setIsAppointment(Byte isAppointment) {
		this.isAppointment = isAppointment;
	}

	public Byte getIsNotTender() {
		return isNotTender;
	}

	public void setIsNotTender(Byte isNotTender) {
		this.isNotTender = isNotTender;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer == null ? null : manufacturer.trim();
	}

	public String getManufacturerCargoCode() {
		return manufacturerCargoCode;
	}

	public void setManufacturerCargoCode(String manufacturerCargoCode) {
		this.manufacturerCargoCode = manufacturerCargoCode == null ? null : manufacturerCargoCode.trim();
	}

	public String getSupplierCargoCode() {
		return supplierCargoCode;
	}

	public void setSupplierCargoCode(String supplierCargoCode) {
		this.supplierCargoCode = supplierCargoCode == null ? null : supplierCargoCode.trim();
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