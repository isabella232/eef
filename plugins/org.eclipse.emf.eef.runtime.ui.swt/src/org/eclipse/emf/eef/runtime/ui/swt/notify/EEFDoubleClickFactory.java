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
package org.eclipse.emf.eef.runtime.ui.swt.notify;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFDoubleClickFactory {

	/**
	 * Defines the {@link EditingContextFactoryProvider} to use in the created EEF DoubleClick Listeners.
	 * @param contextFactoryProvider the {@link EditingContextFactoryProvider} to use.
	 */
	void setContextFactoryProvider(EditingContextFactoryProvider contextFactoryProvider);
	
	/**
	 * Creates a {@link IDoubleClickListener} that open an EEF wizard to 
	 * @param domain {@link EditingDomain} to use to edit the obkects.
	 * @param adapterFactory {@link AdapterFactory} to use in the EEF context.
	 * @return an {@link IDoubleClickListener} to edit {@link EObject} with EEF.
	 */
	IDoubleClickListener createListener(EditingDomain domain, AdapterFactory adapterFactory);

}
