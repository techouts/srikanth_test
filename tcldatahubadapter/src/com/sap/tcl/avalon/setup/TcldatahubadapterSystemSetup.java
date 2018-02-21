/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.sap.tcl.avalon.setup;

import static com.sap.tcl.avalon.constants.TcldatahubadapterConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.sap.tcl.avalon.constants.TcldatahubadapterConstants;
import com.sap.tcl.avalon.service.TcldatahubadapterService;


@SystemSetup(extension = TcldatahubadapterConstants.EXTENSIONNAME)
public class TcldatahubadapterSystemSetup
{
	private final TcldatahubadapterService tcldatahubadapterService;

	public TcldatahubadapterSystemSetup(final TcldatahubadapterService tcldatahubadapterService)
	{
		this.tcldatahubadapterService = tcldatahubadapterService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		tcldatahubadapterService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return TcldatahubadapterSystemSetup.class.getResourceAsStream("/tcldatahubadapter/sap-hybris-platform.png");
	}
}
