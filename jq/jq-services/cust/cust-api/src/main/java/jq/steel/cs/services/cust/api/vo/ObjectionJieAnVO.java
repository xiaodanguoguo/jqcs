package jq.steel.cs.services.cust.api.vo;

public class ObjectionJieAnVO {
    private String claimState1;

    public String getClaimState1() {
        return claimState1;
    }

    public void setClaimState1(String claimState1) {
        this.claimState1 = claimState1;
    }

    //上传文件图片字段
    private String viewUrl;
    private String originalName;
    private String filePath;

    private  String orgCode;

    private  String orgName;

    private String claimNo;

    //协议书文件地址
    private  String  claimNoUrl;



    //润乾地址
    private  String report;

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

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
