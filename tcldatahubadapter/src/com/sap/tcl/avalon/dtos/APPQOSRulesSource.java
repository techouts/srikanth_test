package com.sap.tcl.avalon.select.api.rest.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class APPQOSRulesSource implements Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("user")
	private APPQOSRulesUser user;

	@JsonProperty("user")
	public APPQOSRulesUser getUser()
	{
		return user;
	}

	@JsonProperty("user")
	public void setUser(final APPQOSRulesUser user)
	{
		this.user = user;
	}


}
