package com.example.food_map.constants;

public enum RtnCode {
	SUCCESSFUL("200", "完成!"), 
	CANNOT_EMTPY("400", "欄位不可空白"), // 前面名字可自己定義
	DATA_ERROR("400", "輸入格式有誤"), 
	INT_ERROR("400", "輸入數字或數量有誤!"), 
	NOT_FOUND("404", "查無店家資料"), 
	ALREADY_PRESENT("409", "重複新增"),
	NO_CHANGE("409", "資料未修改");

	private String code;
	private String message;

	private RtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
