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
package org.eclipse.emf.eef.runtime.editingModel;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditingModelEnvironment {

	/**
	 * @return the {@link ResourceSet} used in this environment.
	 */
	ResourceSet getResourceSet();

	/**
	 * Returns the {@link GenFeature} decorating the given feature.
	 * @param feature {@link EStructuralFeature} to process.
	 * @return the {@link GenFeature} associated to the feature if accessible, <code>null</code> otherwise.
	 */
	EObject genFeature(EStructuralFeature feature);

}
