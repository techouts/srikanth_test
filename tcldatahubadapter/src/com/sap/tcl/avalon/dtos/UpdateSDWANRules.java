package com.sap.tcl.avalon.select.api.rest.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "rule" })
public class UpdateSDWANRules
{

	@JsonProperty("rule")
	private Rule rule;


	@JsonProperty("rule")
	public Rule getRule()
	{
		return rule;
	}

	@JsonProperty("rule")
	public void setRule(final Rule rule)
	{
		this.rule = rule;
	}


}