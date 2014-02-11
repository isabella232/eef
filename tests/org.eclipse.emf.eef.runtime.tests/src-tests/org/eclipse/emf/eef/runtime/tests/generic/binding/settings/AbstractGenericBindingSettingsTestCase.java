/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.tests.generic.binding.settings;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eef.runtime.internal.util.EMFServiceImpl;
import org.eclipse.emf.eef.runtime.internal.util.EMFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.services.PriorityCircularityException;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment;
import org.eclipse.emf.eef.runtime.ui.internal.view.propertyeditors.EEFToolkitProviderImpl;
import org.eclipse.emf.eef.runtime.ui.swt.internal.binding.settings.GenericBindingSettings;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.SWTToolkit;

import com.google.common.collect.Lists;

/**
 * Test for Generic Binding Settings.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class AbstractGenericBindingSettingsTestCase {

	/**
	 * Init GenericBindingSettings
	 * 
	 * @param eClass
	 *            EClass
	 * 
	 * @return GenericBindingSettings
	 * @throws PriorityCircularityException
	 */
	public GenericBindingSettings initGenericBindingSettings(Resource... resource) throws PriorityCircularityException {
		GenericBindingSettings genericBindingSettings = new GenericBindingSettings();
		EEFToolkitProviderImpl eefToolkitProvider = new EEFToolkitProviderImpl();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("component.name", "SWTToolkit");
		eefToolkitProvider.addService(new SWTToolkit(), options);
		options.put("component.name", "EMFPropertiesToolkit");
		options.put("priority.over", "SWTToolkit");
		eefToolkitProvider.addService(new EMFPropertiesToolkit(), options);
		genericBindingSettings.setEEFToolkitProvider(eefToolkitProvider);
		EMFServiceProviderImpl emfServiceProvider = new EMFServiceProviderImpl();
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, EMFServiceImpl.class.getName());
		emfServiceProvider.addService(new EMFServiceImpl(), properties);
		genericBindingSettings.setEMFServiceProvider(emfServiceProvider);
		ResourceSet resourceSet = genericBindingSettings.getEditingModelEnvironment().getResourceSet();
		resourceSet.getResources().addAll(Lists.newArrayList(resource));
		return genericBindingSettings;
	}
}
