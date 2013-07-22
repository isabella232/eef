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
package org.eclipse.emf.eef.runtime.internal.context;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.services.PriorityCircularityException;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingContextFactoryProviderImpl extends EEFServiceProviderImpl<EObject, PropertiesEditingContextFactory> implements EditingContextFactoryProvider {
	
	public final void addContextFactory(PropertiesEditingContextFactory contextFactory, Map<String, ?> properties) throws PriorityCircularityException {
		addService(contextFactory, properties);
		contextFactory.setProvider(this);
	}
	
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider#getEditingContextFactory(org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContextFactory getEditingContextFactory(EObject source) {
		return getService(source);
	}

}
