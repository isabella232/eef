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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class DomainPropertiesEditingContext extends EObjectPropertiesEditingContext implements DomainAwarePropertiesEditingContext {
	
	private EditingDomain editingDomain;
	
	/**
	 * @param editingDomain {@link EditingDomain} editing the given {@link EObject}.
	 * @param adapterFactory {@link AdapterFactory} to use in the current context.
	 * @param eObject {@link EObject} to edit.
	 */
	DomainPropertiesEditingContext(EditingDomain editingDomain, AdapterFactory adapterFactory, EObject eObject) {
		super(adapterFactory, eObject);
		this.editingDomain = editingDomain;
	}

	/**
	 * @param parentContext the parent {@link PropertiesEditingContext}.
	 * @param editingDomain {@link EditingDomain} editing the given {@link EObject}.
	 * @param eObject {@link EObject} to edit.
	 */
	DomainPropertiesEditingContext(PropertiesEditingContext parentContext, EObject eObject) {
		super(parentContext, eObject);
	}

	/**
	 * @param editingDomain {@link AdapterFactoryEditingDomain} editing the given {@link EObject}.
	 * @param eObject {@link EObject} to edit.
	 */
	DomainPropertiesEditingContext(AdapterFactoryEditingDomain editingDomain, EObject eObject) {
		this(editingDomain, editingDomain.getAdapterFactory(), eObject);
	}

	/**
	 * @return the editingDomain
	 */
	public EditingDomain getEditingDomain() {
		if (editingDomain != null) {
			return editingDomain;
		} else {
			if (parentContext instanceof DomainAwarePropertiesEditingContext) {
				return ((DomainAwarePropertiesEditingContext) parentContext).getEditingDomain();
			}
		}
		return null;
	}

}
