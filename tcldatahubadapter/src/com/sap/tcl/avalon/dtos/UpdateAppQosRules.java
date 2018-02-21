/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2018 SAP SE
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * Hybris ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with SAP Hybris.
 */
package com.sap.tcl.avalon.select.api.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *
 */
public class UpdateAppQosRules
{

	@JsonProperty("app-qos-policy")
	private APPQOSPolicy appQosRule;

	/**
	 * @return the appQosRule
	 */
	@JsonProperty("app-qos-policy")
	public APPQOSPolicy getAppQosRule()
	{
		return appQosRule;
	}

	/**
	 * @param appQosRule
	 *           the appQosRule to set
	 */
	@JsonProperty("app-qos-policy")
	public void setAppQosRule(final APPQOSPolicy appQosRule)
	{
		this.appQosRule = appQosRule;
	}






}
