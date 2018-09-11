package jq.steel.cs.services.base.facade.common;

public enum RecordStatusCode {

	NOT_START(0,"未发送"),
	SUCCESS(1,"发送成功"),
	SENDING(2,"发送中"),
	FAILURE(3,"发送失败"),
	;
	
	private Integer code;
	
	private String desc;
	
	private RecordStatusCode(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static RecordStatusCode parse(Integer code){
		RecordStatusCode[] codes = RecordStatusCode.values();
		for (RecordStatusCode RecordStatusCode : codes) {
			if(RecordStatusCode.getCode().equals(code)){
				return RecordStatusCode;
			}
		}
		return null;
	}
	
	public static String getDescByCode(Integer code){
		RecordStatusCode  t = parse(code);
		if(t!=null){
			return t.getDesc();
		}else{
			return null;
		}
	}
	
}
