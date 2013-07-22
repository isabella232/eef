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
package org.eclipse.emf.eef.runtime.ui.internal.view.propertyeditors;

import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkit;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFToolkitProviderImpl extends EEFServiceProviderImpl<PropertyEditorContext, EEFToolkit<?>> implements EEFToolkitProvider {
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider#getToolkit(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 * WARNING: a unchecked cast is done in this method, client must ensure that the result of this method is affected to the good type of element!
	 */
	@SuppressWarnings("unchecked")
	public <T> EEFToolkit<T> getToolkit(PropertyEditorContext editorContext) {
		return (EEFToolkit<T>) getService(editorContext);
	}

}
