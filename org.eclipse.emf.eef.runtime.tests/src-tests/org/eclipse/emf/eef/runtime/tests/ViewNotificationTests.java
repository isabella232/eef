/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelFactory;
import org.eclipse.emf.eef.eeftests.bindingmodel.Sample;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
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
public class ViewNotificationTests {

	private PropertiesEditingContext context;
	private Sample modelElement;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		EEFTestStuffsBuilder builder = new EEFTestStuffsBuilder();
		modelElement = BindingmodelFactory.eINSTANCE.createSample();
		context = new EObjectPropertiesEditingContext(modelElement);
		context.setEditingModel(builder.buildEditingModel());
		context.setViewHandlerProvider(builder.buildViewHandlerProvider());
	}

	@Test
	public void testSingleSet() {
		List<ViewHandler<?>> handlers = context.getComponent().getViewHandlers();
		if (handlers.size() == 1) {
			ViewHandler<?> handler = handlers.get(0);
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

			modelElement.setName("New name");
			assertEquals("Bad view refresh", modelElement.getName(), ((SampleView)handler.getView()).getName());

			modelElement.setActive(true);
			assertEquals("Bad view refresh", modelElement.isActive(), ((SampleView)handler.getView()).isActive());

			display.dispose ();
		}

	}

}
