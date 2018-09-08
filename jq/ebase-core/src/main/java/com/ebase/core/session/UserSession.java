package com.ebase.core.session;

/**
 * @Auther: wangyu
 */
public class UserSession {

    private String sessionId;

    private User user;


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}