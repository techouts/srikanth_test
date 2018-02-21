package com.sap.tcl.avalon.select.api.rest.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class APPQOSPolicy implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("name")
	private String name;
	@JsonProperty("match")
	private APPQOSRulesMatch match;
	@JsonProperty("set")
	private APPQOSRulesSet set;

	@JsonProperty("name")
	public String getName()
	{
		return name;
	}

	@JsonProperty("name")
	public void setName(final String name)
	{
		this.name = name;
	}


	@JsonProperty("match")
	public APPQOSRulesMatch getMatch()
	{
		return match;
	}

	@JsonProperty("match")
	public void setMatch(final APPQOSRulesMatch match)
	{
		this.match = match;
	}

	@JsonProperty("set")
	public APPQOSRulesSet getSet()
	{
		return set;
	}

	@JsonProperty("set")
	public void setSet(final APPQOSRulesSet set)
	{
		this.set = set;
	}
}
