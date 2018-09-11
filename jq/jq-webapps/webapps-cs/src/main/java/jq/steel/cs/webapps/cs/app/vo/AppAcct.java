package jq.steel.cs.webapps.cs.app.vo;

import java.io.Serializable;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class AppAcct implements Serializable {
    private String sessionId;
    private String acctId;// 登录账号
    private String name;// 用户名
    private String oInfoName;//客户名称
    private String acctType;// 0普通用户、1管理员（有审核权限）

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getoInfoName() {
        return oInfoName;
    }

    public void setoInfoName(String oInfoName) {
        this.oInfoName = oInfoName;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }
}
