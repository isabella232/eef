/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelFactory;
import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelPackage;
import org.eclipse.emf.eef.eeftests.bindingmodel.Sample;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
import org.eclipse.emf.eef.runtime.tests.views.SampleActiveView;
import org.eclipse.emf.eef.runtime.tests.views.SampleNameView;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SampleMultiViewsEditingTests {

	private static final String NEW_NAME = "New Name";
	private EObjectPropertiesEditingContext context;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		context = new EObjectPropertiesEditingContext(BindingmodelFactory.eINSTANCE.createSample());
		context.setEditingModel(new EditingModelBuilder()
									.bindClass(BindingmodelPackage.Literals.SAMPLE)
										.withView(SampleNameView.class)
										.withView(SampleActiveView.class)
									.build());
		context.setViewHandlerProvider(new EEFTestStuffsBuilder().buildViewHandlerProvider());
	}
	
	@Test
	public void testMultiViewsEditing() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout (new FillLayout());
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FillLayout());
		List<ViewHandler<?>> viewHandlers = context.getComponent().getViewHandlers();
		List<Composite> views = new ArrayList<Composite>();
		for (int i = 0; i < viewHandlers.size(); i++) {
			ViewHandler<?> handler = viewHandlers.get(i);			
			if (handler instanceof SWTViewHandler) {
				views.add(buildView(composite, (SWTViewHandler) handler, i));
			}
		}
		shell.pack();
		shell.open();

		((Sample)context.getEObject()).setName(NEW_NAME);
		assertTrue("The view isn't properly updated", NEW_NAME.equals(((SampleNameView)views.get(0)).getName()));
		((Sample)context.getEObject()).setActive(true);
		assertTrue("The view isn't properly updated", ((SampleActiveView)views.get(1)).isActive());
		
		display.dispose();
	}

	/**
	 * @param composite
	 * @param handler
	 * @param index 
	 * @return 
	 */
	private Composite buildView(Composite composite, SWTViewHandler handler, int index) {
		Composite view = null;
		Group group = new Group(composite, SWT.NONE);
		group.setText("View " + index);
		group.setLayout(new FillLayout());
		Composite container = new Composite(group, SWT.NONE);
		container.setLayout(new FillLayout());
		try {
			view = handler.createView(container);
		} catch (ViewConstructionException e) {
			fail("An error occured during view creation");
		}
		return view;
	}

}
