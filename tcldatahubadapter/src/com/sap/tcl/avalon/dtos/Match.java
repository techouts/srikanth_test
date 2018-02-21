package com.sap.tcl.avalon.select.api.rest.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
/*
 * @JsonPropertyOrder({ "url-category", "ip-version", "ip-flags", "application", "source", "destination" })
 */
public class Match
{

	@JsonProperty("url-category")
	private UrlCategory urlCategory;
	@JsonProperty("ip-version")
	private String ipVersion;
	@JsonProperty("ip-flags")
	private String ipFlags;
	@JsonProperty("application")
	private Application application;
	@JsonProperty("source")
	private Source source;
	@JsonProperty("destination")
	private Destination destination;

	@JsonProperty("url-category")
	public UrlCategory getUrlCategory()
	{
		return urlCategory;
	}

	@JsonProperty("url-category")
	public void setUrlCategory(final UrlCategory urlCategory)
	{
		this.urlCategory = urlCategory;
	}

	@JsonProperty("ip-version")
	public String getIpVersion()
	{
		return ipVersion;
	}

	@JsonProperty("ip-version")
	public void setIpVersion(final String ipVersion)
	{
		this.ipVersion = ipVersion;
	}

	@JsonProperty("ip-flags")
	public String getIpFlags()
	{
		return ipFlags;
	}

	@JsonProperty("ip-flags")
	public void setIpFlags(final String ipFlags)
	{
		this.ipFlags = ipFlags;
	}

	@JsonProperty("application")
	public Application getApplication()
	{
		return application;
	}

	@JsonProperty("application")
	public void setApplication(final Application application)
	{
		this.application = application;
	}

	@JsonProperty("source")
	public Source getSource()
	{
		return source;
	}

	@JsonProperty("source")
	public void setSource(final Source source)
	{
		this.source = source;
	}

	@JsonProperty("destination")
	public Destination getDestination()
	{
		return destination;
	}

	@JsonProperty("destination")
	public void setDestination(final Destination destination)
	{
		this.destination = destination;
	}



}
