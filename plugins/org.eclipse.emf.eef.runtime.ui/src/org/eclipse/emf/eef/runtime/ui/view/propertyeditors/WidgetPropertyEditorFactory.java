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
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.EEFToolkitImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public interface WidgetPropertyEditorFactory<T> extends PropertyEditorFactory<T> {

	/**
	 * Defines if the current provider is able to handle the given
	 * {@link PropertyEditorContext}.
	 * 
	 * @param editorContext
	 *            context to process.
	 * @return <code>true</code> if the current provider can handle this
	 *         context.
	 */
	boolean serviceFor(PropertyEditorContext editorContext);

	/**
	 * @param toolkit
	 *            the {@link EEFToolkitImpl} owning the current property editor
	 */
	void setToolkit(EEFToolkitImpl<T> toolkit);

	/**
	 * @param feature
	 *            EStructuralFeature
	 * @return if the feature can be handled by the widget
	 */
	boolean canHandle(EStructuralFeature feature);

}
