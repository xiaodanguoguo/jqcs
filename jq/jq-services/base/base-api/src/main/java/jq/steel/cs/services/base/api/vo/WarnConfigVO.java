package jq.steel.cs.services.base.api.vo;

//供应商预警
public class WarnConfigVO {

    private Long id;

    private Long supplierId;        //供应商ID

    private Integer redWarnDays;    //红色预警时间

    private Integer yellowWarnDays; //黄色预警时间

    private String opt;             //insert update delete

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getRedWarnDays() {
        return redWarnDays;
    }

    public void setRedWarnDays(Integer redWarnDays) {
        this.redWarnDays = redWarnDays;
    }

    public Integer getYellowWarnDays() {
        return yellowWarnDays;
    }

    public void setYellowWarnDays(Integer yellowWarnDays) {
        this.yellowWarnDays = yellowWarnDays;
    }
}