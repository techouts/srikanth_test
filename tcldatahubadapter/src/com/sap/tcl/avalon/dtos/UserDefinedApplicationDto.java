package com.sap.tcl.avalon.select.api.rest.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
{ "user-defined-application" })
public class UserDefinedApplicationDto implements Serializable
{

	/**
		 *
		 */
	private static final long serialVersionUID = 2008126765008289025L;

	@JsonProperty("user-defined-application")
	private UserDefinedApplication userDefinedApplication;

	@JsonProperty("user-defined-application")
	public UserDefinedApplication getUserDefinedApplication()
	{
		return userDefinedApplication;
	}

	@JsonProperty("user-defined-application")
	public void setUserDefinedApplication(final UserDefinedApplication userDefinedApplication)
	{
		this.userDefinedApplication = userDefinedApplication;
	}

}