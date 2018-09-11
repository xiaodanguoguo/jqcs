package jq.steel.cs.services.base.facade.common;

public enum NoticeStatus {

	PUBLIS((byte)1, "已发布"), DRAFT((byte)2, "草稿");

	private NoticeStatus(byte code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private byte code;
	private String msg;

	public byte getCode() {
		return code;
	}

	public void setCode(byte code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static NoticeStatus getNoticeStatus(byte code) {
		for (NoticeStatus noticeStatus : NoticeStatus.values())
			if (noticeStatus.getCode() == code)
				return noticeStatus;
		return null;
	}

}
