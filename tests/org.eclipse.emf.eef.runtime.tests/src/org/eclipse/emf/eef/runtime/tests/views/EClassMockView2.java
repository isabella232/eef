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
package org.eclipse.emf.eef.runtime.tests.views;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * It's a shame but I don't see for the moment the way to use a Java
 * class twice as view for a given EClass
 */
public class EClassMockView2 extends EClassMockView {

	public EClassMockView2(PropertiesEditingComponent editingComponent) {
		super(editingComponent);
	}
}
