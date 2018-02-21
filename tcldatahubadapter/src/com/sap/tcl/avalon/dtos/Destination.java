package com.sap.tcl.avalon.select.api.rest.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
/*
 * @JsonPropertyOrder({ "region" })
 */
public class Destination
{

	@JsonProperty("region")
	private List<String> region = null;


	@JsonProperty("region")
	public List<String> getRegion()
	{
		return region;
	}

	@JsonProperty("region")
	public void setRegion(final List<String> region)
	{
		this.region = region;
	}
}