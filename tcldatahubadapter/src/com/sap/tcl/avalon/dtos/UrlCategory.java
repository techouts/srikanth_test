package com.sap.tcl.avalon.select.api.rest.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "category-name" })
public class UrlCategory
{

	@JsonProperty("category-name")
	private String categoryName;


	@JsonProperty("predefined")
	private List<String> predefined = null;
	@JsonProperty("user-defined")
	private List<String> userDefined = null;

	@JsonProperty("category-name")
	public String getCategoryName()
	{
		return categoryName;
	}

	@JsonProperty("category-name")
	public void setCategoryName(final String categoryName)
	{
		this.categoryName = categoryName;
	}

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