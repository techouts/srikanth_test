package com.sap.tcl.avalon.select.api.rest.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
/*
 * @JsonPropertyOrder({ "name" })
 */
public class Rule
{

	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("set")
	private Set set;
	@JsonProperty("match")
	private Match match;
	@JsonProperty("tag")
	private List<String> tag = null;

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

	@JsonProperty("description")
	public String getDescription()
	{
		return description;
	}

	@JsonProperty("description")
	public void setDescription(final String description)
	{
		this.description = description;
	}

	@JsonProperty("set")
	public Set getSet()
	{
		return set;
	}

	@JsonProperty("set")
	public void setSet(final Set set)
	{
		this.set = set;
	}

	@JsonProperty("match")
	public Match getMatch()
	{
		return match;
	}

	@JsonProperty("match")
	public void setMatch(final Match match)
	{
		this.match = match;
	}

	@JsonProperty("tag")
	public List<String> getTag()
	{
		return tag;
	}

	@JsonProperty("tag")
	public void setTag(final List<String> tag)
	{
		this.tag = tag;
	}


}