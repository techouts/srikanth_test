package com.sap.tcl.avalon.select.api.rest.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class APPQOSRulesSet implements Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("qos-profile")
	private String qosProfile;


	@JsonProperty("qos-profile")
	public String getQosProfile()
	{
		return qosProfile;
	}

	@JsonProperty("qos-profile")
	public void setQosProfile(final String qosProfile)
	{
		this.qosProfile = qosProfile;
	}


}
