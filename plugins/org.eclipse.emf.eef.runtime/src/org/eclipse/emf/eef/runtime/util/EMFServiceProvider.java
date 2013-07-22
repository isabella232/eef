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
package org.eclipse.emf.eef.runtime.util;

import org.eclipse.emf.ecore.EPackage;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EMFServiceProvider {
	
	/**
	 * Returns the most appropriate EMFService for the given {@link EPackage}.
	 * @param ePackage {@link EPackage} to process.
	 * @return an appropriate {@link EMFService}.
	 */
	EMFService getEMFService(EPackage ePackage);
	
}
