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
package org.eclipse.emf.eef.runtime.query;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.services.EEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ModelRequestor extends EEFService<EPackage> {

	/**
	 * Filters a collection of EObject given a {@link Filter}.
	 * @param elements the collection to filter.
	 * @param filter the filter to apply.
	 * @return the filtered collection.
	 */
	<ANY_EOBJECT extends EObject> Collection<ANY_EOBJECT> filter(Collection<ANY_EOBJECT> elements, Filter filter);
	
}
