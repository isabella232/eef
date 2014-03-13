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
package org.eclipse.emf.eef.runtime.binding.settings;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public interface EEFBindingSettings<T extends EObject> extends EEFService<EPackage> {

	/**
	 * @return the editingModelEnvironment of this provider.
	 */
	EditingModelEnvironment getEditingModelEnvironment();

	/**
	 * Returns an element describing the editing forms to edit the given object.
	 * 
	 * @param eObject
	 *            the {@link EObject} to edit.
	 * @return the EEF description.
	 */
	T getEEFDescription(EObject eObject);

	/**
	 * Returns an element describing the editing forms to edit the given object.
	 * 
	 * @param eClass
	 *            the {@link EClass} to edit.
	 * @return the EEF description.
	 */
	T getEEFDescription(EClass eClass);

	/**
	 * Returns an ed to edit the given object.
	 * 
	 * @param ePackage
	 *            the {@link EPackage} to edit.
	 * @return the properties editing model.
	 */
	PropertiesEditingModel getEditingModel(EPackage ePackage);

	/**
	 * Method allowing users to filter the {@link EEFLockPolicy}s applied on a
	 * {@link PropertiesEditingComponent}.
	 * 
	 * @param lockPolicy
	 *            {@link EEFLockPolicy} to process.
	 * @return <code>true</code> if the given policy must be applied on the
	 *         edited elements.
	 */
	boolean enableLockPolicy(EEFLockPolicy lockPolicy);

}
