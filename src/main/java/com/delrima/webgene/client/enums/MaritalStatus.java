package com.delrima.webgene.client.enums;

public enum MaritalStatus {

	MARRIED("Married", "Y"), SINGLE("Single", "N"), UNKNOWN("Unknown", "U");

	private String description;
	private String code;

	private MaritalStatus(String description, String code) {
		this.description = description;
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public final String getCode() {
		return code;
	}

	public final void setCode(String code) {
		this.code = code;
	}
}
