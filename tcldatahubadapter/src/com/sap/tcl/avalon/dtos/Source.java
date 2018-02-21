package com.sap.tcl.avalon.select.api.rest.dtos;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
/*
 * @JsonPropertyOrder({ "region", "zone" })
 */
public class Source
{

	@JsonProperty("region")
	private List<String> region = null;
	@JsonProperty("zone")
	private Zone zone;
	@JsonProperty("user")
	private User user;

	@JsonProperty("region")
	public List<String> getRegion()
	{
		return region;
	}

	@JsonProperty("user")
	public User getUser()
	{
		return user;
	}

	@JsonProperty("user")
	public void setUser(final User user)
	{
		this.user = user;
	}

	@JsonProperty("region")
	public void setRegion(final List<String> region)
	{
		this.region = region;
	}

	@JsonProperty("zone")
	public Zone getZone()
	{
		return zone;
	}

	@JsonProperty("zone")
	public void setZone(final Zone zone)
	{
		this.zone = zone;
	}



}
