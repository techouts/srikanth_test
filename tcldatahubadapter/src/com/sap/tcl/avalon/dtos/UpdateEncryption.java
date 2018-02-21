package com.sap.tcl.avalon.select.api.rest.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateEncryption {

	@JsonProperty("encryption")
	private String encryption;

	@JsonProperty("encryption")
	public String getEncryption() {
		return encryption;
	}

	@JsonProperty("encryption")
	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

}