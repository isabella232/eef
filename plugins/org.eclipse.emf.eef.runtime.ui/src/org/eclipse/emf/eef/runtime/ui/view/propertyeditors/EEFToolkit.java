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

import java.util.Collection;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.Widget;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public interface EEFToolkit<T> extends EEFService<PropertyEditorContext>, ModelPropertyEditorFactory<Toolkit, T> {

	/**
	 * @param eStructuralFeature
	 *            EStructuralFeature
	 * @return all toolkits widgets enable for {@link EStructuralFeature}
	 */
	public Collection<Widget> getAllWidgetsFor(EStructuralFeature eStructuralFeature);

	/**
	 * @param name
	 *            String
	 * @return the first toolkits widget named "name".
	 */
	public Widget getWidgetByName(String name);

}
