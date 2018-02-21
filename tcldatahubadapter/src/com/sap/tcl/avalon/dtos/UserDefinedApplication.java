package com.sap.tcl.avalon.select.api.rest.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "app-name", "description", "family", "subfamily", "productivity", "risk", "tag", "precedence", "app-timeout", "app-match-rules" })
public class UserDefinedApplication implements Serializable
{

	/**
		 *
		 */
	private static final long serialVersionUID = 4954340879756059688L;

	@JsonProperty("app-name")
	private String appName;
	@JsonProperty("description")
	private String description;
	@JsonProperty("family")
	private String family;
	@JsonProperty("subfamily")
	private String subfamily;
	@JsonProperty("productivity")
	private String productivity;
	@JsonProperty("risk")
	private String risk;
	@JsonProperty("tag")
	private List<String> tag = null;
	@JsonProperty("precedence")
	private String precedence;
	@JsonProperty("app-timeout")
	private String appTimeout;
	@JsonProperty("app-match-rules")
	private List<AppMatchRule> appMatchRules = null;

	@JsonProperty("app-name")
	public String getAppName()
	{
		return appName;
	}

	@JsonProperty("app-name")
	public void setAppName(final String appName)
	{
		this.appName = appName;
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

	@JsonProperty("family")
	public String getFamily()
	{
		return family;
	}

	@JsonProperty("family")
	public void setFamily(final String family)
	{
		this.family = family;
	}

	@JsonProperty("subfamily")
	public String getSubfamily()
	{
		return subfamily;
	}

	@JsonProperty("subfamily")
	public void setSubfamily(final String subfamily)
	{
		this.subfamily = subfamily;
	}

	@JsonProperty("productivity")
	public String getProductivity()
	{
		return productivity;
	}

	@JsonProperty("productivity")
	public void setProductivity(final String productivity)
	{
		this.productivity = productivity;
	}

	@JsonProperty("risk")
	public String getRisk()
	{
		return risk;
	}

	@JsonProperty("risk")
	public void setRisk(final String risk)
	{
		this.risk = risk;
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

	@JsonProperty("precedence")
	public String getPrecedence()
	{
		return precedence;
	}

	@JsonProperty("precedence")
	public void setPrecedence(final String precedence)
	{
		this.precedence = precedence;
	}

	@JsonProperty("app-timeout")
	public String getAppTimeout()
	{
		return appTimeout;
	}

	@JsonProperty("app-timeout")
	public void setAppTimeout(final String appTimeout)
	{
		this.appTimeout = appTimeout;
	}

	@JsonProperty("app-match-rules")
	public List<AppMatchRule> getAppMatchRules()
	{
		return appMatchRules;
	}

	@JsonProperty("app-match-rules")
	public void setAppMatchRules(final List<AppMatchRule> appMatchRules)
	{
		this.appMatchRules = appMatchRules;
	}

}