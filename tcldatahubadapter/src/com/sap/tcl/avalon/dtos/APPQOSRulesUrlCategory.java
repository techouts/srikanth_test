package com.sap.tcl.avalon.select.api.rest.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class APPQOSRulesUrlCategory implements Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("predefined")
	private List<String> predefined = null;
	@JsonProperty("user-defined")
	private List<String> userDefined = null;

	@JsonProperty("predefined")
	public List<String> getPredefined()
	{
		return predefined;
	}

	@JsonProperty("predefined")
	public void setPredefined(final List<String> predefined)
	{
		this.predefined = predefined;
	}

	@JsonProperty("user-defined")
	public List<String> getUserDefined()
	{
		return userDefined;
	}

	@JsonProperty("user-defined")
	public void setUserDefined(final List<String> userDefined)
	{
		this.userDefined = userDefined;
	}


}
