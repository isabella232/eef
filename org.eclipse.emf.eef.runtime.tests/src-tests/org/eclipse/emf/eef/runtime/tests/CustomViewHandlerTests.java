/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests;

import static org.junit.Assert.*;

import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelFactory;
import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelPackage;
import org.eclipse.emf.eef.eeftests.bindingmodel.Sample;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.handlers.CustomSWTViewHandler;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
import org.eclipse.emf.eef.runtime.tests.views.SampleCustomView;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Test;

/**
 * @author glefur
 *
 */
public class CustomViewHandlerTests {

	private EObjectPropertiesEditingContext context;

	@Before
	public void setUp() {
		PropertiesEditingModel editingModel = new EditingModelBuilder()
			.bindClass(BindingmodelPackage.Literals.SAMPLE)
				.withView(SampleCustomView.class).handler(new CustomSWTViewHandler(SampleCustomView.class))
			.build();
		EEFTestStuffsBuilder builder = new EEFTestStuffsBuilder();
		context = new EObjectPropertiesEditingContext(BindingmodelFactory.eINSTANCE.createSample());
		context.setEditingModel(editingModel);
		context.setViewHandlerProvider(builder.buildViewHandlerProvider());
	}
	
	@Test
	public void testCustomViewHandler() {
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

		SampleCustomView view = (SampleCustomView) composite.getChildren()[0];
		assertTrue("The view isn't properly initialized", !view.isActive());
		((Sample)context.getEObject()).setActive(true);
		assertTrue("The view isn't properly updated", view.isActive());
		
		display.dispose ();
	}

}
