package com.sap.tcl.avalon.select.api.rest.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "zone-list" })
public class Zone
{

	@JsonProperty("zone-list")
	private List<String> zoneList = null;


	@JsonProperty("zone-list")
	public List<String> getZoneList()
	{
		return zoneList;
	}

	@JsonProperty("zone-list")
	public void setZoneList(final List<String> zoneList)
	{
		this.zoneList = zoneList;
	}

	/*
 * @added by srikanthh
 */
}
