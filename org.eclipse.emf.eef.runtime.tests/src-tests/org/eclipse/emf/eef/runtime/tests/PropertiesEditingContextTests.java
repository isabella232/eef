/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelFactory;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingContextTests {

	private PropertiesEditingContext context;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		EEFTestStuffsBuilder builder = new EEFTestStuffsBuilder();
		context = new EObjectPropertiesEditingContext(BindingmodelFactory.eINSTANCE.createSample());
		context.setEditingModel(builder.buildEditingModel());
		context.setViewHandlerProvider(builder.buildViewHandlerProvider());
	}

	@Test
	public void testViewHandling() {
		List<ViewHandler<?>> viewHandlers = context.getComponent().getViewHandlers();
		assertEquals("ViewHandler not initialized", viewHandlers.size(), 1);
		assertTrue("Bad ViewHandler selection", viewHandlers.get(0) instanceof SWTViewHandler);
	}
	
	@Test
	public void testViewAssociation() {
		SWTViewHandler handler = (SWTViewHandler) context.getComponent().getViewHandlers().get(0);
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setLayout (new FillLayout());
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FillLayout());
		try {
			handler.createView(composite);
		} catch (ViewConstructionException e) {
			fail("An error occured during view creation");
		}
		shell.pack ();
		shell.open ();

		assertEquals("Too many children in the composite owning the view", composite.getChildren().length, 1);
		assertEquals("Error in view association, the SampleView isn't created for a Sample EObject", composite.getChildren()[0].getClass(), SampleView.class);
		
		display.dispose ();

	}

	
}

