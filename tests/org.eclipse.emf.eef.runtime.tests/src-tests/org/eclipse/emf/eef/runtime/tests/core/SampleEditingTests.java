/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.swt.SWTViewHandlerFactory;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SampleEditingTests extends UIEditingTestCase {
	
	@Test
	public void testViewHandling() {
		List<View> viewDescriptors = editingContext.getEditingComponent().getViewDescriptors();
		ViewHandlerFactory<?> handlerFactory = editingContext.getViewHandlerFactoryProvider().getHandlerFactory(viewDescriptors.get(1));
		assertEquals("ViewHandler not initialized", viewDescriptors.size(), 1);
		assertTrue("Bad ViewHandler selection", handlerFactory instanceof SWTViewHandlerFactory);
		PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
		List<Object> views = editingComponent.getViews();
		for (Object view : views) {
			ViewHandlerFactory<?> handlerFactory2 = editingContext.getViewHandlerFactoryProvider().getHandlerFactory(editingComponent.getDescriptorForView(view));
			handlerFactory2.dispose(editingComponent, view);
		}
	}
	
	@Test
	public void testViewAssociation() {
		assertEquals("Too many children in the composite owning the view", views.size(), 1);
		assertEquals("Error in view association, the SampleView isn't created for a Sample EObject", views.get(0).getClass(), SampleView.class);
	}	
}