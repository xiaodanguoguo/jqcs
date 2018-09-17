package jq.steel.cs.services.base.facade.common;

public enum NoticeIsDelete {


    NO((byte)0,"否"),
    YES((byte)1,"是");



    private byte code;

    private String msg;
    NoticeIsDelete(byte code, String msg){

        this.code = code;
        this.msg = msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
    
    public byte getCode() {
		return code;
	}

	public void setCode(byte code) {
		this.code = code;
	}

	public static NoticeIsDelete getIsDelete(byte code) {
    	for (NoticeIsDelete isDelete : NoticeIsDelete.values())
    		if (isDelete.getCode() == code)
    			return isDelete;
    	return null;
    }
    
}
