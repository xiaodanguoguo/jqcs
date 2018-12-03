package jq.steel.cs.services.cust.api.vo;
//用于储存app端质证书下载路径
public class MillSheetDownloadVO  {
    private String millSheetPath;

    public String getMillSheetPath() {
        return millSheetPath;
    }

    private Integer downableNum; //可下载次数

    public void setMillSheetPath(String millSheetPath) {
        this.millSheetPath = millSheetPath;
    }

    public Integer getDownableNum() {
        return downableNum;
    }

    public void setDownableNum(Integer downableNum) {
        this.downableNum = downableNum;
    }
}
