package jq.steel.cs.services.cust.api.vo;

import java.io.Serializable;
import java.util.List;

public class CrmMillSheetSplitDetailVO implements Serializable{

    private  String orgCode;

    private  String orgName;

    private String millSheetNo;
    //钢卷信息表
    private List<MillCoilInfoVO> millCoilInfoVOS;
    //拆分申请表
    private List<CrmMillSheetSplitApplyVO> crmMillSheetSplitApplyVOS;

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

    public String getMillSheetNo() {
        return millSheetNo;
    }

    public void setMillSheetNo(String millSheetNo) {
        this.millSheetNo = millSheetNo;
    }

    public List<MillCoilInfoVO> getMillCoilInfoVOS() {
        return millCoilInfoVOS;
    }

    public void setMillCoilInfoVOS(List<MillCoilInfoVO> millCoilInfoVOS) {
        this.millCoilInfoVOS = millCoilInfoVOS;
    }

    public List<CrmMillSheetSplitApplyVO> getCrmMillSheetSplitApplyVOS() {
        return crmMillSheetSplitApplyVOS;
    }

    public void setCrmMillSheetSplitApplyVOS(List<CrmMillSheetSplitApplyVO> crmMillSheetSplitApplyVOS) {
        this.crmMillSheetSplitApplyVOS = crmMillSheetSplitApplyVOS;
    }
}
