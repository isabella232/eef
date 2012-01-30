/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.cases;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class UIEditingTestCase {

	protected Display display;

	protected EObject elementToEdit;
	protected EObjectPropertiesEditingContext context;
	protected PropertiesEditingComponent component;
	protected List<Composite> views;



	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		elementToEdit = elementToEdit();
		initUI();
	}

	/**
	 * @return
	 */
	protected EObject elementToEdit() {
		return BindingmodelFactory.eINSTANCE.createSample();
	}

	/**
	 * @return the {@link PropertiesEditingModel} for the test case
	 */
	protected abstract PropertiesEditingModel buildEditingModel();
	
	@After
	public void tearDown() {
		disposeUI();
	}

	/**
	 * 
	 */
	protected void initUI() {
		context = new EObjectPropertiesEditingContext(elementToEdit);
		context.setEditingModel(buildEditingModel());
		context.setViewHandlerProvider(new EEFTestStuffsBuilder().buildViewHandlerProvider());
		display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout (new FillLayout());
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FillLayout());
		component = context.getEditingComponent();
		List<ViewHandler<?>> viewHandlers = component.getViewHandlers();
		views = new ArrayList<Composite>();
		for (int i = 0; i < viewHandlers.size(); i++) {
			ViewHandler<?> handler = viewHandlers.get(i);			
			if (handler instanceof SWTViewHandler) {
				views.add(buildView(composite, (SWTViewHandler) handler, i));
			}
		}
		shell.pack();
		shell.open();
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
			handler.initView(component);
		} catch (ViewConstructionException e) {
			fail("An error occured during view creation");
		}
		return view;
	}

	/**
	 * 
	 */
	protected void disposeUI() {
		display.dispose();
		views.clear();
	}

}
