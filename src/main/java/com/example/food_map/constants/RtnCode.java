package com.example.food_map.constants;

public enum RtnCode {
	SUCCESSFUL("200", "����!"), 
	CANNOT_EMTPY("400", "��줣�i�ť�"), // �e���W�r�i�ۤv�w�q
	DATA_ERROR("400", "��J�榡���~"), 
	INT_ERROR("400", "��J�Ʀr�μƶq���~!"), 
	NOT_FOUND("404", "�d�L���a���"), 
	ALREADY_PRESENT("409", "���Ʒs�W"),
	NO_CHANGE("409", "��ƥ��ק�");

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
