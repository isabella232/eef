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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFBindingSettingsProvider {

	/**
	 * Returns the most appropriate binding settings for the given package.
	 * @param ePackage the {@link EPackage} to process.
	 * @return the most appropriate {@link EEFBindingSettings} for the given package. 
	 */
	<T extends EObject> EEFBindingSettings<T> getBindingSettings(EPackage ePackage);
	
}
