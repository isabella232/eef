/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.ui.swt.util;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.ui.swt.commands.WizardEditingCommand;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.util.OSGiHelper;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * Util class to edit EObject in batch mode.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class EEFBatchEditingUtil {

	/**
	 * Edit EObject in batch mode.
	 * 
	 * @param eObject
	 *            EObject
	 * @param domain
	 * @param adapterFactory
	 */
	public void editObjectInBatchMode(EditingDomain domain, AdapterFactory adapterFactory, EObject eObject) {
		// init EEF services.
		BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		EditingContextFactoryProvider contextFactoryProvider = OSGiHelper.getService(bundleContext, EditingContextFactoryProvider.class);
		EMFServiceProvider emfServiceProvider = OSGiHelper.getService(bundleContext, EMFServiceProvider.class);
		EEFEditingServiceProvider eefEditingServiceProvider = OSGiHelper.getService(bundleContext, EEFEditingServiceProvider.class);
		EditUIProvidersFactory editUIProvidersFactory = OSGiHelper.getService(bundleContext, EditUIProvidersFactory.class);

		// open wizard
		PropertiesEditingContextFactory editingContextFactory = contextFactoryProvider.getEditingContextFactory(eObject);
		PropertiesEditingContext context = editingContextFactory.createPropertiesEditingContext(domain, adapterFactory, eObject);
		context.getOptions().setBatchMode(true);
		PropertiesEditingEvent editingEvent = new PropertiesEditingEventImpl(null, null, PropertiesEditingEvent.EDIT, null, eObject);
		SemanticPropertiesEditingContext semanticContext = (SemanticPropertiesEditingContext) editingContextFactory.createSemanticPropertiesEditingContext(context, editingEvent);
		WizardEditingCommand wizardEditingCommand = new WizardEditingCommand(contextFactoryProvider, emfServiceProvider, eefEditingServiceProvider, editUIProvidersFactory, semanticContext);
		domain.getCommandStack().execute(wizardEditingCommand);
		context.dispose();
	}
}
