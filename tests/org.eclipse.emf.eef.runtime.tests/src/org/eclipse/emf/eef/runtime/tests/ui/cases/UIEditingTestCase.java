/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.cases;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.editingview.PropertiesEditingViewHandler;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handle.exceptions.ViewConstructionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class UIEditingTestCase extends NonUIEditingTestCase {

	protected Shell shell;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		initUI();
	}

	@After
	public void tearDown() {
		disposeUI();
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase#initEnvironmentBuilder()
	 */
	@Override
	protected Builder initEnvironmentBuilder() {
		Builder builder = super.initEnvironmentBuilder();
		return builder.setEditingModel(builder.createEditingModelWithSWTViews());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase#offScreenViewsInitialization()
	 */
	@Override
	protected void offScreenViewsInitialization() throws ViewConstructionException {
		//Note: we prevent off screen initialization
	}

	/**
	 * 
	 */
	protected void initUI() {
		shell = new Shell();
		shell.setLayout (new FillLayout());
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FillLayout());
		
		List<View> viewdescriptors = editingContext.getEditingComponent().getViewDescriptors();
		views = new ArrayList<Object>();
		
		int i = 0;
		for (View viewDescriptor : viewdescriptors) {
			views.add(buildView(composite, viewDescriptor, i++));
		}
		
		shell.pack();
		shell.open();
	}

	/**
	 * @param composite
	 * @param view
	 * @param index 
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	protected Object buildView(Composite composite, View viewDescriptor, int index) {
		Object view = null;
		Group group = new Group(composite, SWT.NONE);
		group.setText("View " + index);
		group.setLayout(new FillLayout());
		Composite container = new Composite(group, SWT.NONE);
		container.setLayout(new FillLayout());
		ViewHandler<?> viewHandler = editingContext.getViewHandlerProvider().getViewHandler(viewDescriptor);
		if (viewHandler instanceof SWTViewHandler) {
			try {
				view = ((SWTViewHandler)viewHandler).createView(viewDescriptor, container);
				((SWTViewHandler)viewHandler).initView(editingContext.getEditingComponent(), (Composite)view);
			} catch (ViewConstructionException e) {
				fail("An error occured during view creation");
			}
		} else if (viewHandler instanceof PropertiesEditingViewHandler) {
			try {
				view = ((PropertiesEditingViewHandler)viewHandler).createView(viewDescriptor, editingContext.getEditingComponent(), container);
				((PropertiesEditingViewHandler)viewHandler).initView(editingContext.getEditingComponent(), (PropertiesEditingView<Composite>) view);
			} catch (ViewConstructionException e) {
				fail("An error occured during view creation");
			}
		} else {
			fail("Unable to build the view.");
		}
		return view;
	}

	/**
	 * 
	 */
	protected void disposeUI() {
		PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
		List<Object> views = editingComponent.getViews();
		for (Object view : views) {
			ViewHandler<?> viewHandler = editingContext.getViewHandlerProvider().getViewHandler(editingComponent.getDescriptorForView(view));
			viewHandler.dispose(editingComponent, view);
		}
		shell.dispose();
		this.views.clear();
	}

}
