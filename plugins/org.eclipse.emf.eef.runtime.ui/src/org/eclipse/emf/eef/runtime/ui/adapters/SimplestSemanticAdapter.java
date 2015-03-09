/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.ui.adapters;

import org.eclipse.emf.ecore.EObject;

/**
 * Simplest semantic adapter.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 */
public class SimplestSemanticAdapter implements SemanticAdapter {

	private EObject eObject;

	public SimplestSemanticAdapter(EObject eObject) {
		super();
		this.eObject = eObject;
	}

	/**
	 * @return the semantic element to edit
	 */
	public EObject getEObject() {
		return this.eObject;
	}
}
