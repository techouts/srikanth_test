package com.sap.tcl.avalon.select.api.rest.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
/*
 * @JsonPropertyOrder({ "forwarding-profile", "action" })
 */
public class Set
{

	@JsonProperty("forwarding-profile")
	private String forwardingProfile;
	@JsonProperty("action")
	private String action;

	@JsonProperty("forwarding-profile")
	public String getForwardingProfile()
	{
		return forwardingProfile;
	}

	@JsonProperty("forwarding-profile")
	public void setForwardingProfile(final String forwardingProfile)
	{
		this.forwardingProfile = forwardingProfile;
	}

	@JsonProperty("action")
	public String getAction()
	{
		return action;
	}

	@JsonProperty("action")
	public void setAction(final String action)
	{
		this.action = action;
	}


}