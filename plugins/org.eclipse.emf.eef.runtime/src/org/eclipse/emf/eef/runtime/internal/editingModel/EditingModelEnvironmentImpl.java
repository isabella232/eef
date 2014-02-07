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
package org.eclipse.emf.eef.runtime.internal.editingModel;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.notify.SettingsChangesNotifierImpl;
import org.osgi.service.event.EventAdmin;

/**
 * This class is intended to provide information about the environment where the
 * {@link PropertiesEditingModel}s are loaded.
 * 
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public final class EditingModelEnvironmentImpl implements EditingModelEnvironment {

	private ResourceSet resourceSet;
	private ECrossReferenceAdapter crossReferenceAdapter;
	private EventAdmin eventAdmin;

	public EditingModelEnvironmentImpl(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

	/**
	 * @return the {@link ResourceSet} of this provider.
	 */
	public ResourceSet getResourceSet() {
		if (resourceSet == null) {
			resourceSet = new ResourceSetImpl();
			crossReferenceAdapter = new ECrossReferenceAdapter();
			resourceSet.eAdapters().add(crossReferenceAdapter);
			if (eventAdmin != null) {
				resourceSet.eAdapters().add(new SettingsChangesNotifierImpl(eventAdmin));
			}
		}
		return resourceSet;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment#genFeature(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public EObject genFeature(EStructuralFeature feature) {
		if (crossReferenceAdapter != null) {
			Collection<Setting> inverseReferences = crossReferenceAdapter.getInverseReferences(feature);
			for (Setting setting : inverseReferences) {
				if ("GenFeature".equals(setting.getEObject().eClass().getName())) {
					return setting.getEObject();
				}
			}
		}
		return null;
	}

}
