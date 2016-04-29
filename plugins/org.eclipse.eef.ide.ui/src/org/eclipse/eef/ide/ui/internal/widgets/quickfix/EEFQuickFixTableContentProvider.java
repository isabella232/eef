/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.eef.ide.ui.internal.widgets.quickfix;

import org.eclipse.eef.EEFValidationFixDescription;
import org.eclipse.eef.EEFValidationRuleDescription;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * The content provider of the table showing all the quick fixes.
 *
 * @author sbegaudeau
 */
public class EEFQuickFixTableContentProvider implements IStructuredContentProvider {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof EEFValidationRuleDescription) {
			EEFValidationRuleDescription validationRule = (EEFValidationRuleDescription) inputElement;
			return validationRule.getFixes().toArray(new EEFValidationFixDescription[validationRule.getFixes().size()]);
		}
		return new Object[0];
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		// Do nothing
	}

}
