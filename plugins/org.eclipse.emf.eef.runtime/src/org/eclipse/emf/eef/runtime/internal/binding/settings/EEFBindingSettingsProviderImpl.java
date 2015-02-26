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
package org.eclipse.emf.eef.runtime.internal.binding.settings;

import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFBindingSettingsProviderImpl extends EEFServiceProviderImpl<EPackage, EEFBindingSettings<PropertiesEditingModel>> implements EEFBindingSettingsProvider {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider#getBindingSettings(org.eclipse.emf.ecore.EPackage)
	 */
	@SuppressWarnings("unchecked")
	public EEFBindingSettings<PropertiesEditingModel> getBindingSettings(EPackage ePackage) {
		return getService(ePackage);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider#getAllBindingSettings(org.eclipse.emf.ecore.EPackage)
	 */
	@SuppressWarnings("unchecked")
	public List<EEFBindingSettings<PropertiesEditingModel>> getAllBindingSettings(EPackage ePackage) {
		return getServicesAndDefaultFor(ePackage);
	}

}
