package jq.steel.cs.services.base.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author ZhangX
 *
 */
public class EntClientInfoVO {

    private Long id;

    //供应商分类ID
    private Long supTypeId;

    //客户标识
    private Long custId;

    //名称
    private String name;

    //简称
    private String shortName;

    //中文名称
    private String nameCn;

    //英文名称
    private String nameEn;

    //客商码
    private String merchantCode;

    //税号
    private String taxNumber;

    //企业客户类型
    private Byte entCustType;

    //供应商编码
    private String supplierCode;

    //头像路径
    private String photoSrc;

    //境内外关系
    private Byte relation;

    //D-U-N-S
    private String dUNS;

    //特准供应商
    private Byte speSupplier;

    //统一社会信用代码
    private String creditCode;

    //境外经营活动证明号码
    private String certificateNumber;

    //境外经营活动证原名
    private String certificateName;

    //组织机构代码
    private String organCode;

    //组织机构原名
    private String organName;

    //组织机构代码生效日期
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date organEffeDate;

    //组织机构代码失效日期
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date organExpiryDate;

    //营业执照原名
    private String bussLiceName;

    //营业执照代码
    private String bussLiceCode;

    //营业执照生效日期
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date bussLiceEffeDate;

    //营业执照失效日期
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date bussLiceExpiryDate;

    //税务登记证原名
    private String taxRegName;

    //税务登记证生效日期
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date taxTegEffeDate;

    //税务登记证失效日期
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date taxTegExpiryDate;

    //企业属性
    private Byte corpAttr;

    //企业类型
    private Byte corpType;

    //所属行业代码
    private String industryCode;

    //法人代表
    private String corpRepre;

    //注册资本
    private BigDecimal regCapital;

    //公司成立时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date entBuildDate;

    //公司注册地址省份
    private String entProAddr;

    //公司注册地址省份代码
    private String entProCode;

    //公司注册城市地址
    private String entCityAddr;

    //公司注册地址城市代码
    private String entCityCode;

    //公司注册详细地址
    private String entDetailAddr;

    //黑名单供应商
    private Byte blackSupplier;

    //经营范围
    private Long businessScope;

    //采购组织
    private Long purchOrgan;

    //创建人
    private String createdBy;

    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date createdTime;

    //修改人
    private String updatedBy;

    //修改时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date updatedTime;

    private Byte lifeCycle; //注册状态

    private List<BankInfoVO> bankInfoVOS;//银行信息

    private List<CertInfoVO> certInfoVOS; //资质信息

    private List<ContactInfoVO> contactInfoVOS; //联系信息

    private List<SupplyScopeVO> supplyScopeVOS; //供货范围表
    
    private Integer pageSize = 10;
    
    private Integer pageNum =5;
	
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

//    private List<BankInfoVO> bankInfos;//银行信息
//
//    private List<CertInfoVO> certInfos; //资质信息
//
//    private List<ContactInfoVO> contactInfos; //联系信息
//
//    private List<SupplyScopeVO> supplyScopes; //供货范围表

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupTypeId() {
        return supTypeId;
    }

    public void setSupTypeId(Long supTypeId) {
        this.supTypeId = supTypeId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn == null ? null : nameCn.trim();
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber == null ? null : taxNumber.trim();
    }

    public Byte getEntCustType() {
        return entCustType;
    }

    public void setEntCustType(Byte entCustType) {
        this.entCustType = entCustType;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getPhotoSrc() {
        return photoSrc;
    }

    public void setPhotoSrc(String photoSrc) {
        this.photoSrc = photoSrc == null ? null : photoSrc.trim();
    }

    public Byte getRelation() {
        return relation;
    }

    public void setRelation(Byte relation) {
        this.relation = relation;
    }

    public String getdUNS() {
        return dUNS;
    }

    public void setdUNS(String dUNS) {
        this.dUNS = dUNS == null ? null : dUNS.trim();
    }

    public Byte getSpeSupplier() {
        return speSupplier;
    }

    public void setSpeSupplier(Byte speSupplier) {
        this.speSupplier = speSupplier;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? null : creditCode.trim();
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber == null ? null : certificateNumber.trim();
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName == null ? null : certificateName.trim();
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode == null ? null : organCode.trim();
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName == null ? null : organName.trim();
    }

    public Date getOrganEffeDate() {
        return organEffeDate;
    }

    public void setOrganEffeDate(Date organEffeDate) {
        this.organEffeDate = organEffeDate;
    }

    public Date getOrganExpiryDate() {
        return organExpiryDate;
    }

    public void setOrganExpiryDate(Date organExpiryDate) {
        this.organExpiryDate = organExpiryDate;
    }

    public String getBussLiceName() {
        return bussLiceName;
    }

    public void setBussLiceName(String bussLiceName) {
        this.bussLiceName = bussLiceName == null ? null : bussLiceName.trim();
    }

    public String getBussLiceCode() {
        return bussLiceCode;
    }

    public void setBussLiceCode(String bussLiceCode) {
        this.bussLiceCode = bussLiceCode == null ? null : bussLiceCode.trim();
    }

    public Date getBussLiceEffeDate() {
        return bussLiceEffeDate;
    }

    public void setBussLiceEffeDate(Date bussLiceEffeDate) {
        this.bussLiceEffeDate = bussLiceEffeDate;
    }

    public Date getBussLiceExpiryDate() {
        return bussLiceExpiryDate;
    }

    public void setBussLiceExpiryDate(Date bussLiceExpiryDate) {
        this.bussLiceExpiryDate = bussLiceExpiryDate;
    }

    public String getTaxRegName() {
        return taxRegName;
    }

    public void setTaxRegName(String taxRegName) {
        this.taxRegName = taxRegName == null ? null : taxRegName.trim();
    }

    public Date getTaxTegEffeDate() {
        return taxTegEffeDate;
    }

    public void setTaxTegEffeDate(Date taxTegEffeDate) {
        this.taxTegEffeDate = taxTegEffeDate;
    }

    public Date getTaxTegExpiryDate() {
        return taxTegExpiryDate;
    }

    public void setTaxTegExpiryDate(Date taxTegExpiryDate) {
        this.taxTegExpiryDate = taxTegExpiryDate;
    }

    public Byte getCorpAttr() {
        return corpAttr;
    }

    public void setCorpAttr(Byte corpAttr) {
        this.corpAttr = corpAttr;
    }

    public Byte getCorpType() {
        return corpType;
    }

    public void setCorpType(Byte corpType) {
        this.corpType = corpType;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode == null ? null : industryCode.trim();
    }

    public String getCorpRepre() {
        return corpRepre;
    }

    public void setCorpRepre(String corpRepre) {
        this.corpRepre = corpRepre == null ? null : corpRepre.trim();
    }

    public BigDecimal getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(BigDecimal regCapital) {
        this.regCapital = regCapital;
    }

    public Date getEntBuildDate() {
        return entBuildDate;
    }

    public void setEntBuildDate(Date entBuildDate) {
        this.entBuildDate = entBuildDate;
    }

    public String getEntProAddr() {
        return entProAddr;
    }

    public void setEntProAddr(String entProAddr) {
        this.entProAddr = entProAddr == null ? null : entProAddr.trim();
    }

    public String getEntProCode() {
        return entProCode;
    }

    public void setEntProCode(String entProCode) {
        this.entProCode = entProCode == null ? null : entProCode.trim();
    }

    public String getEntCityAddr() {
        return entCityAddr;
    }

    public void setEntCityAddr(String entCityAddr) {
        this.entCityAddr = entCityAddr == null ? null : entCityAddr.trim();
    }

    public String getEntCityCode() {
        return entCityCode;
    }

    public void setEntCityCode(String entCityCode) {
        this.entCityCode = entCityCode == null ? null : entCityCode.trim();
    }

    public String getEntDetailAddr() {
        return entDetailAddr;
    }

    public void setEntDetailAddr(String entDetailAddr) {
        this.entDetailAddr = entDetailAddr == null ? null : entDetailAddr.trim();
    }

    public Byte getBlackSupplier() {
        return blackSupplier;
    }

    public void setBlackSupplier(Byte blackSupplier) {
        this.blackSupplier = blackSupplier;
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

    public Byte getLifeCycle() {
        return lifeCycle;
    }

   public void setLifeCycle(Byte lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public List<BankInfoVO> getBankInfoVOS() {
        return bankInfoVOS;
    }

    public void setBankInfoVOS(List<BankInfoVO> bankInfoVOS) {
        this.bankInfoVOS = bankInfoVOS;
    }

    public List<CertInfoVO> getCertInfoVOS() {
        return certInfoVOS;
    }

    public void setCertInfoVOS(List<CertInfoVO> certInfoVOS) {
        this.certInfoVOS = certInfoVOS;
    }

    public List<ContactInfoVO> getContactInfoVOS() {
        return contactInfoVOS;
    }

    public void setContactInfoVOS(List<ContactInfoVO> contactInfoVOS) {
        this.contactInfoVOS = contactInfoVOS;
    }

    public List<SupplyScopeVO> getSupplyScopeVOS() {
        return supplyScopeVOS;
    }

    public void setSupplyScopeVOS(List<SupplyScopeVO> supplyScopeVOS) {
        this.supplyScopeVOS = supplyScopeVOS;
    }
}