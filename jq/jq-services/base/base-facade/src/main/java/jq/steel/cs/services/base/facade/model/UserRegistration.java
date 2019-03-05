package jq.steel.cs.services.base.facade.model;

import java.math.BigDecimal;

public class UserRegistration {

    private String area;

    private String salesCompany;

    private BigDecimal sid;

    private String productClassification;

    private String province;

    private String city;

    private BigDecimal oId;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSalesCompany() {
        return salesCompany;
    }

    public void setSalesCompany(String salesCompany) {
        this.salesCompany = salesCompany;
    }

    public BigDecimal getSid() {
        return sid;
    }

    public void setSid(BigDecimal sid) {
        this.sid = sid;
    }

    public String getProductClassification() {
        return productClassification;
    }

    public void setProductClassification(String productClassification) {
        this.productClassification = productClassification == null ? null : productClassification.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public BigDecimal getoId() {
        return oId;
    }

    public void setoId(BigDecimal oId) {
        this.oId = oId;
    }
}