package com.sap.tcl.avalon.select.api.rest.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "value" })
public class DestinationPort implements Serializable
{

	/**
		 *
		 */
	private static final long serialVersionUID = -7612193110198854288L;

	@JsonProperty("value")
	private String value;

	@JsonProperty("value")
	public String getValue()
	{
		return value;
	}

	@JsonProperty("value")
	public void setValue(final String value)
	{
		this.value = value;
	}
}