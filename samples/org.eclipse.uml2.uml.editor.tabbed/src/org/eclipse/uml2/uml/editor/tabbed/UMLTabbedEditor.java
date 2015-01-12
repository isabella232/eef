/*******************************************************************************
 * Copyright (c) 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.uml2.uml.editor.tabbed;

import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.uml2.uml.editor.presentation.UMLEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class UMLTabbedEditor extends UMLEditor implements ITabbedPropertySheetPageContributor {

	private TabbedPropertySheetPage propertySheetPage;
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.uml2.uml.editor.presentation.UMLEditor#getPropertySheetPage()
	 */
	@Override
	public IPropertySheetPage getPropertySheetPage() {
		if (propertySheetPage == null) {
			propertySheetPage = new TabbedPropertySheetPage(this);
		}
		return propertySheetPage;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor#getContributorId()
	 */
	@Override
	public String getContributorId() {
		return "org.eclipse.uml2.uml.editor.UMLEditor.properties";
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.uml2.uml.editor.presentation.UMLEditor#dispose()
	 */
	@Override
	public void dispose() {
		if (propertySheetPage != null) {
			propertySheetPage.dispose();
		}
		super.dispose();
	}

	
	
}
