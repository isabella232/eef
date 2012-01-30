/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SampleEditingTests extends UIEditingTestCase {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase#buildEditingModel()
	 */
	protected PropertiesEditingModel buildEditingModel() {
		return new EEFTestStuffsBuilder().buildEditingModel();
	}

	@Test
	public void testViewHandling() {
		List<ViewHandler<?>> viewHandlers = context.getEditingComponent().getViewHandlers();
		assertEquals("ViewHandler not initialized", viewHandlers.size(), 1);
		assertTrue("Bad ViewHandler selection", viewHandlers.get(0) instanceof SWTViewHandler);
	}
	
	@Test
	public void testViewAssociation() {
		assertEquals("Too many children in the composite owning the view", views.size(), 1);
		assertEquals("Error in view association, the SampleView isn't created for a Sample EObject", views.get(0).getClass(), SampleView.class);
	}	
}