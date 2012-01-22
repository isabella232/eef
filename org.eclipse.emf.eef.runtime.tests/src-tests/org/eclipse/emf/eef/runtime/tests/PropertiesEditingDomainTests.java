/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelFactory;
import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelPackage;
import org.eclipse.emf.eef.eeftests.bindingmodel.Sample;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.model.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.ui.view.handlers.ReflectViewHandler;
import org.eclipse.emf.eef.runtime.ui.view.handlers.ReflectViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.ComposedViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingDomainTests {

	private PropertiesEditingContext context;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		PropertiesEditingModel model = new PropertiesEditingModel.Builder()
						.bind(BindingmodelPackage.Literals.SAMPLE, SampleView.class)
				.build();
		ComposedViewHandlerProvider provider = 
				new ComposedViewHandlerProvider.Builder()
						.addHandler(new ReflectViewHandlerProvider())
				.build();
		Sample sample = BindingmodelFactory.eINSTANCE.createSample();
		context = new EObjectPropertiesEditingContext(sample);
		context.setEditingModel(model);
		context.setViewHandlerProvider(provider);
	}

	@Test
	public void testViewAssociation() {
		ViewHandler handler = context.getViewHandler();
		assertNotNull("Can't find a ViewHandler for the given EObject.", handler);
		assertTrue("The returned ViewHandler isn't valid.", handler instanceof ReflectViewHandler);
		openView(handler);
	}
	
	
	private void openView(ViewHandler handler) {
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setLayout (new FillLayout());
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FillLayout());
		try {
			handler.createView(composite);
		} catch (ViewConstructionException e) {
			fail("An error occured during view creation?");
		}
		shell.pack ();
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();

	}

}
