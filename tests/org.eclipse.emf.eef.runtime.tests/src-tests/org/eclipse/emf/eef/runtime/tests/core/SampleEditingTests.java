/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

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
		Collection<ViewHandler<?>> viewHandlers = editingContext.getEditingComponent().createViewHandlers();
		assertEquals("ViewHandler not initialized", viewHandlers.size(), 1);
		assertTrue("Bad ViewHandler selection", viewHandlers.iterator().next() instanceof SWTViewHandler);
		for (ViewHandler<?> viewHandler : viewHandlers) {
			viewHandler.dispose();
		}
	}
	
	@Test
	public void testViewAssociation() {
		assertEquals("Too many children in the composite owning the view", views.size(), 1);
		assertEquals("Error in view association, the SampleView isn't created for a Sample EObject", views.get(0).getClass(), SampleView.class);
	}	
}