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
package org.eclipse.emf.eef.runtime.tests.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SampleEditingTests extends UIEditingTestCase {
	
	@Test
	public void testViewHandling() {
		List<View> viewDescriptors = editingContext.getEditingComponent().getViewDescriptors();
		ViewHandler<?> viewHandler = editingContext.getViewHandlerProvider().getViewHandler(viewDescriptors.get(0));
		assertEquals("ViewHandler not initialized", viewDescriptors.size(), 1);
		assertTrue("Bad ViewHandler selection", viewHandler instanceof SWTViewHandler);
		PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
		List<Object> views = editingComponent.getViews();
		for (Object view : views) {
			ViewHandler<?> handler = editingContext.getViewHandlerProvider().getViewHandler(editingComponent.getDescriptorForView(view));
			handler.dispose(editingComponent, view);
		}
	}
	
	@Test
	public void testViewAssociation() {
		assertEquals("Too many children in the composite owning the view", views.size(), 1);
		assertEquals("Error in view association, the SampleView isn't created for a Sample EObject", views.get(0).getClass(), SampleView.class);
	}	
}