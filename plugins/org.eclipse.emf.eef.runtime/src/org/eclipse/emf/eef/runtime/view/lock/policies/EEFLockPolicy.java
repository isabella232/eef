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
package org.eclipse.emf.eef.runtime.view.lock.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFLockPolicy {
	
	/**
	 * Defines if an {@link EObject} is locked.
	 * @param object the object to check.
	 * @return <code>true</code> if the given object is locked.
	 */
	boolean isLocked(PropertiesEditingContext editingContext, EObject object);

	/**
	 * Defines if an {@link EStructuralFeature} of an object is locked.
	 * @param object the object to check.
	 * @param propertyBinding the concerning binding.
	 * @return <code>true</code> if the given feature is locked.
	 */
	boolean isLocked(PropertiesEditingContext editingContext, EObject object, PropertyBinding propertyBinding);

	/**
	 * Disposes the current policy.
	 */
	void dispose();
	
}
