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
package org.eclipse.emf.eef.runtime.tests.ui;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.eef.runtime.tests.ui.cases.PropertiesEditingViewEditingTestCase;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFViewerTests extends PropertiesEditingViewEditingTestCase {

	/**
	 * 
	 */
	@Test
	public void testViewsCreation() {
		assertEquals("Bad count of created views", 2, getViews().size());
	}
}
