package jq.steel.cs.services.cust.api.vo;

public class ObjectionJieAnVO {

    private  String orgCode;

    private  String orgName;

    private String claimNo;

    //协议书文件地址
    private  String  claimNoUrl;

    //润乾地址
    private  String report;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getClaimNoUrl() {
        return claimNoUrl;
    }

    public void setClaimNoUrl(String claimNoUrl) {
        this.claimNoUrl = claimNoUrl;
    }

    public String getClaimNo() {
        return claimNo;
    }

    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo;
    }
}
