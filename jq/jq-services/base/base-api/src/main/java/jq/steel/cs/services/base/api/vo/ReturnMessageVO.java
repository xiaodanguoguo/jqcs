package jq.steel.cs.services.base.api.vo;

import java.io.Serializable;

public class ReturnMessageVO<T> implements Serializable {

    private Boolean flag;

    private String message;

    private String errorCode;

    private T retContent;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public T getRetContent() {
        return retContent;
    }

    public void setRetContent(T retContent) {
        this.retContent = retContent;
    }
}
