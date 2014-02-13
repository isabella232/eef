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
package org.eclipse.emf.eef.runtime.ui.swt.internal.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.ui.swt.util.EEFViewerFilterInvocationParameters;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFViewerFilterInvocationParametersImpl implements EEFViewerFilterInvocationParameters {
	
	private final PropertiesEditingContext editingContext;
	private final PropertiesEditingView<?> editingView;
	private final Object element;
	

	/**
	 * @param editingContext
	 * @param editingView
	 * @param element
	 */
	public EEFViewerFilterInvocationParametersImpl(PropertiesEditingContext editingContext, PropertiesEditingView<?> editingView, Object element) {
		this.editingContext = editingContext;
		this.editingView = editingView;
		this.element = element;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EEFInvocationParameters#getEditingContext()
	 */
	public PropertiesEditingContext getEditingContext() {
		return editingContext;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EEFInvocationParameters#getEditedObject()
	 */
	public EObject getEditedObject() {
		return editingContext.getEditingComponent().getEObject();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.util.EEFViewerFilterInvocationParameters#getEditingView()
	 */
	public PropertiesEditingView<?> getEditingView() {
		return editingView;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.util.EEFViewerFilterInvocationParameters#getElement()
	 */
	public Object getElement() {
		return element;
	}

}
