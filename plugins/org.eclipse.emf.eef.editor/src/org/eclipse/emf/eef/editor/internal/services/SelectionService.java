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
package org.eclipse.emf.eef.editor.internal.services;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SelectionService {

	/**
	 * Unwrapping utility for JFace selection.
	 * @param selection selection to unwrap.
	 * @return Expected value.
	 */
	public <T> T unwrapSelection(ISelection selection) {
		if (selection instanceof StructuredSelection) {
			StructuredSelection sSel = (StructuredSelection)selection;
			if (sSel.size() > 1) {
				return (T) sSel.toList();
			} else {
				return (T) sSel.getFirstElement();
			}
		}
		return null;
	}
	

}
