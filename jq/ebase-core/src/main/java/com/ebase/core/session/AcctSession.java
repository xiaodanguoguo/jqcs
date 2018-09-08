package com.ebase.core.session;

/**
 * 管理员session
 * @Auther: wangyu
 */
public class AcctSession {


    private String sessionId;

    private Acct acct;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Acct getAcct() {
        return acct;
    }

    public void setAcct(Acct acct) {
        this.acct = acct;
    }
}
