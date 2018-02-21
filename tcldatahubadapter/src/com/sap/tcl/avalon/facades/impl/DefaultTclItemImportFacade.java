package com.sap.tcl.avalon.facades.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.DuplicatedItemIdentifier;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.jalo.SyncItemCronJob;
import de.hybris.platform.catalog.jalo.SyncItemJob;
import de.hybris.platform.catalog.jalo.synchronization.CatalogVersionSyncJob;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.catalog.model.SyncItemJobModel;
import de.hybris.platform.catalog.model.synchronization.CatalogVersionSyncJobModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.impex.jalo.ImpExException;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.impex.ImportConfig;
import de.hybris.platform.servicelayer.impex.ImportResult;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.type.TypeService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.hybris.datahub.core.config.ImportConfigStrategy;
import com.hybris.datahub.core.dto.ItemImportTaskData;
import com.hybris.datahub.core.event.DatahubAdapterImportEvent;
import com.hybris.datahub.core.facades.ItemImportResult;
import com.hybris.datahub.core.facades.ItemImportResult.DatahubAdapterEventStatus;
import com.hybris.datahub.core.facades.impl.DefaultItemImportFacade;
import com.hybris.datahub.core.facades.impl.ImportResultConverter;
import com.hybris.datahub.core.facades.impl.converter.ErrorLimitExceededException;
import com.hybris.datahub.core.services.impl.DataHubFacade;
import com.sap.tcl.avalon.core.model.CircuitPriorityModel;
import com.sap.tcl.avalon.core.model.DeviceGroupModel;
import com.sap.tcl.avalon.core.model.DeviceRouteModel;
import com.sap.tcl.avalon.core.model.ForwardingProfileModel;
import com.sap.tcl.avalon.core.model.ManagedCpeServiceModel;
import com.sap.tcl.avalon.core.model.PolicyGroupModel;
import com.sap.tcl.avalon.core.model.PolicyRuleModel;
import com.sap.tcl.avalon.core.model.PrimeAppQosPolicyModel;
import com.sap.tcl.avalon.core.model.PrimeAppRuleModel;
import com.sap.tcl.avalon.core.model.PrimeApplicationModel;
import com.sap.tcl.avalon.core.model.PrimeDeviceModel;
import com.sap.tcl.avalon.core.model.PrimeDeviceQosPolicyModel;
import com.sap.tcl.avalon.core.model.QosProfileModel;
import com.sap.tcl.avalon.core.model.SdwanSelectTemplateModel;
import com.sap.tcl.avalon.core.model.SelectAppRuleModel;
import com.sap.tcl.avalon.core.model.SelectApplicationModel;
import com.sap.tcl.avalon.core.model.SelectDeviceModel;
import com.sap.tcl.avalon.core.model.SlaProfileModel;
import com.sap.tcl.avalon.core.model.UrlCategoryModel;


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

/**
 *
 */
public class DefaultTclItemImportFacade extends DefaultItemImportFacade
{
	private static final Logger LOG = LoggerFactory.getLogger(DefaultTclItemImportFacade.class);

	private ImportService tclImportService;
	private ImportResultConverter tclResultConverter;
	private DataHubFacade tclDataHubFacade;
	private EventService tclEventService;
	private ImportConfigStrategy tclImportConfigStrategy;
	private int tclErrorLimit;
	private CatalogVersionService catalogVersionService;
	private ModelService modelService;
	private TypeService typeService;
	private static final String TCLPRODUCTCATALOG = "tclavalonProductCatalog";

	@Override
	public ItemImportResult importItems(final ItemImportTaskData ctx) throws IOException
	{
		final ItemImportResult result = runTclImportAndHandleErrors(ctx);
		addTclHeaderErrorsToResult(ctx, result);

		fireTclDatahubAdapterImportEvent(ctx.getPoolName(), result.getStatus());
		callbackToTclDataHub(ctx, result);

		if (ctx.getPoolName().contains("VERSA_DIR") || ctx.getPoolName().contains("ANUTA_DIR"))
		{
			executesdwanSyncJob();
		}
		return result;
	}

	/**
	 *
	 */
	private void executesdwanSyncJob()
	{
		final CatalogVersionModel catalogVersionModel = getCatalogVersionService().getCatalogVersion(TCLPRODUCTCATALOG,
				CatalogManager.OFFLINE_VERSION);
		final List<SyncItemJobModel> syncJobList = catalogVersionModel.getSynchronizations();
		if (CollectionUtils.isNotEmpty(syncJobList))
		{
			final List filteredJobList = syncJobList.stream()
					.filter(syncItemJobModel -> syncItemJobModel.getCode().contains("sdwan")).collect(Collectors.toList());
			CatalogVersionSyncJobModel sdwanSyncJob = null;
			if (filteredJobList.isEmpty())
			{
				sdwanSyncJob = createSdwanSyncJob();
			}
			else if (filteredJobList.get(0) instanceof CatalogVersionSyncJobModel)
			{
				sdwanSyncJob = (CatalogVersionSyncJobModel) filteredJobList.get(0);
			}
			runsdwanSyncJob(sdwanSyncJob);
		}
	}

	/**
	 *
	 */
	@SuppressWarnings("deprecation")
	private void runsdwanSyncJob(final CatalogVersionSyncJobModel sdwanSyncJob)
	{
		final List<CronJobModel> cronjobs = sdwanSyncJob.getCronJobs().stream().collect(Collectors.toList());
		SyncItemCronJob newSyncJob = null;
		final CatalogVersionSyncJob catalogSyncJob = modelService.getSource(sdwanSyncJob);


		if (!cronjobs.isEmpty())
		{
			Collections.sort(cronjobs, new Comparator<CronJobModel>()
			{
				@Override
				public int compare(final CronJobModel cronJob1, final CronJobModel cronJob2)
				{

					if (cronJob1 == null || cronJob1.getEndTime() == null || cronJob2 == null || cronJob2.getEndTime() == null)
					{
						return 0;
					}
					else
					{
						return cronJob1.getEndTime().compareTo(cronJob2.getEndTime());
					}
				}
			});
			final CronJobModel latestCronJob = cronjobs.get(cronjobs.size() - 1);
			if (CronJobStatus.FINISHED.equals(latestCronJob.getStatus()) && !CronJobResult.SUCCESS.equals(latestCronJob.getResult()))
			{
				newSyncJob = modelService.getSource(latestCronJob);
			}

		}
		if (newSyncJob == null)
		{
			newSyncJob = catalogSyncJob.newExecution();
		}
		newSyncJob.setLogToDatabase(false);
		newSyncJob.setLogToFile(false);
		newSyncJob.setForceUpdate(false);

		newSyncJob.setConfigurator(new FullSyncConfigurator(catalogSyncJob));
		catalogSyncJob.perform(newSyncJob, true);

		if (!CronJobResult.SUCCESS.equals(newSyncJob.getResult()))
		{
			final Collection<DuplicatedItemIdentifier> duplicateIdentifiers = catalogVersionService.findDuplicatedIds(sdwanSyncJob
					.getSourceVersion());
			duplicateIdentifiers.stream().forEach(
					identifier -> LOG.info(identifier.getComposedType(), Integer.valueOf(identifier.getCount())));

			//prepare event and publish to generate email.
		}
	}

	/**
	 *
	 */
	private CatalogVersionSyncJobModel createSdwanSyncJob()
	{
		final CatalogVersionSyncJobModel cvsj = new CatalogVersionSyncJobModel();
		cvsj.setActive(Boolean.TRUE);
		cvsj.setCode("tclsdwanCatalogSyncJob staged -> online");
		final ComposedTypeModel[] composedItems = new ComposedTypeModel[]
		{ typeService.getComposedTypeForClass(SdwanSelectTemplateModel.class),
				typeService.getComposedTypeForClass(SelectDeviceModel.class),
				typeService.getComposedTypeForClass(DeviceGroupModel.class),
				typeService.getComposedTypeForClass(PolicyGroupModel.class),
				typeService.getComposedTypeForClass(PolicyRuleModel.class),
				typeService.getComposedTypeForClass(SelectApplicationModel.class),
				typeService.getComposedTypeForClass(UrlCategoryModel.class),
				typeService.getComposedTypeForClass(SlaProfileModel.class),
				typeService.getComposedTypeForClass(QosProfileModel.class),
				typeService.getComposedTypeForClass(ForwardingProfileModel.class),
				typeService.getComposedTypeForClass(SelectAppRuleModel.class),
				typeService.getComposedTypeForClass(CircuitPriorityModel.class),
				typeService.getComposedTypeForClass(ManagedCpeServiceModel.class),
				typeService.getComposedTypeForClass(PrimeDeviceModel.class),
				typeService.getComposedTypeForClass(DeviceRouteModel.class),
				typeService.getComposedTypeForClass(PrimeAppQosPolicyModel.class),
				typeService.getComposedTypeForClass(PrimeApplicationModel.class),
				typeService.getComposedTypeForClass(PrimeAppRuleModel.class),
				typeService.getComposedTypeForClass(PrimeDeviceQosPolicyModel.class) };

		final List<ComposedTypeModel> list = Arrays.asList(composedItems);
		final CatalogVersionModel source = getCatalogVersionService().getCatalogVersion(TCLPRODUCTCATALOG,
				CatalogManager.OFFLINE_VERSION);
		final CatalogVersionModel target = getCatalogVersionService().getCatalogVersion(TCLPRODUCTCATALOG,
				CatalogManager.ONLINE_VERSION);
		cvsj.setSourceVersion(source);
		cvsj.setTargetVersion(target);
		cvsj.setRootTypes(list);
		cvsj.setCreateNewItems(Boolean.TRUE);
		modelService.save(cvsj);
		return cvsj;
	}


	protected class FullSyncConfigurator implements SyncItemCronJob.Configurator
	{
		protected final SyncItemJob job;

		public FullSyncConfigurator(final SyncItemJob paramSyncItemJob)
		{
			this.job = paramSyncItemJob;
		}

		@Override
		public void configureCronjob(final SyncItemCronJob sicj, final SyncItemJob.SyncItemCopyContext sicc)
		{
			this.job.configureFullVersionSync(sicj);
		}

		@Override
		public SyncItemJob.CompletionInfo getCompletionInfo()
		{
			return new SyncItemJob.CompletionInfo("configuring full version sync", 0, 0, 0, 0);
		}
	}

	private void addTclHeaderErrorsToResult(final ItemImportTaskData ctx, final ItemImportResult result)
	{
		if (ctx != null && !CollectionUtils.isEmpty(ctx.getHeaderErrors()))
		{
			result.addErrors(ctx.getHeaderErrors());
		}
	}



	private ItemImportResult runTclImportAndHandleErrors(final ItemImportTaskData ctx) throws IOException
	{
		try
		{
			return runTclImport(ctx);

		}
		catch (final ErrorLimitExceededException arg2)
		{
			final String e = "Target System Publication " + ctx.getPublicationId()
					+ " has failed because the number of errors exceeds the configured threshold of " + tclErrorLimit + " errors.";


			LOG.error(e);
			return new ItemImportResult(new ErrorLimitExceededException(e
					+ " For more information on the errors, please see the Hybris Commerce log."));

		}
		catch (RuntimeException | ImpExException arg3)
		{
			return new ItemImportResult(arg3);
		}
	}

	private void fireTclDatahubAdapterImportEvent(final String poolName, final DatahubAdapterEventStatus status)
	{
		tclEventService.publishEvent(new DatahubAdapterImportEvent(poolName, status));
	}


	private void callbackToTclDataHub(final ItemImportTaskData ctx, final ItemImportResult result)
	{
		this.tclDataHubFacade.returnImportResult(ctx.getResultCallbackUrl(), result);
	}


	private ItemImportResult runTclImport(final ItemImportTaskData ctx) throws ImpExException
	{
		final ImportConfig config = tclImportConfigStrategy.createImportConfig(ctx);
		final ImportResult result = tclImportService.importData(config);
		return tclResultConverter.convert(result);
	}

	/**
	 * @return the tclImportService
	 */
	public ImportService getTclImportService()
	{
		return tclImportService;
	}

	/**
	 * @param tclImportService
	 *           the tclImportService to set
	 */
	@Required
	public void setTclImportService(final ImportService tclImportService)
	{
		this.tclImportService = tclImportService;
	}

	/**
	 * @return the tclResultConverter
	 */
	public ImportResultConverter getTclResultConverter()
	{
		return tclResultConverter;
	}

	/**
	 * @param tclResultConverter
	 *           the tclResultConverter to set
	 */
	@Required
	public void setTclResultConverter(final ImportResultConverter tclResultConverter)
	{
		this.tclResultConverter = tclResultConverter;
	}

	/**
	 * @return the tclDataHubFacade
	 */
	public DataHubFacade getTclDataHubFacade()
	{
		return tclDataHubFacade;
	}

	/**
	 * @param tclDataHubFacade
	 *           the tclDataHubFacade to set
	 */
	@Required
	public void setTclDataHubFacade(final DataHubFacade tclDataHubFacade)
	{
		this.tclDataHubFacade = tclDataHubFacade;
	}

	/**
	 * @return the tclEventService
	 */
	public EventService getTclEventService()
	{
		return tclEventService;
	}

	/**
	 * @param tclEventService
	 *           the tclEventService to set
	 */
	@Required
	public void setTclEventService(final EventService tclEventService)
	{
		this.tclEventService = tclEventService;
	}

	/**
	 * @return the tclImportConfigStrategy
	 */
	public ImportConfigStrategy getTclImportConfigStrategy()
	{
		return tclImportConfigStrategy;
	}

	/**
	 * @param tclImportConfigStrategy
	 *           the tclImportConfigStrategy to set
	 */
	@Required
	public void setTclImportConfigStrategy(final ImportConfigStrategy tclImportConfigStrategy)
	{
		this.tclImportConfigStrategy = tclImportConfigStrategy;
	}

	/**
	 * @return the tclErrorLimit
	 */
	public int getTclErrorLimit()
	{
		return tclErrorLimit;
	}

	/**
	 * @param tclErrorLimit
	 *           the tclErrorLimit to set
	 */

	public void setTclErrorLimit(final int tclErrorLimit)
	{
		this.tclErrorLimit = tclErrorLimit;
	}

	/**
	 * @return the catalogVersionService
	 */
	public CatalogVersionService getCatalogVersionService()
	{
		return catalogVersionService;
	}

	/**
	 * @param catalogVersionService
	 *           the catalogVersionService to set
	 */
	@Required
	public void setCatalogVersionService(final CatalogVersionService catalogVersionService)
	{
		this.catalogVersionService = catalogVersionService;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the typeService
	 */
	public TypeService getTypeService()
	{
		return typeService;
	}

	/**
	 * @param typeService
	 *           the typeService to set
	 */
	@Required
	public void setTypeService(final TypeService typeService)
	{
		this.typeService = typeService;
	}

}
