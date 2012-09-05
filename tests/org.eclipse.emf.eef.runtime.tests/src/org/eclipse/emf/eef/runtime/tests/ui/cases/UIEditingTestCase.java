/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.cases;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandler;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandler;
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
		
		viewHandlers = editingContext.getEditingComponent().createViewHandlers();
		views = new ArrayList<Object>();
		
		int i = 0;
		for (ViewHandler<?> viewHandler : viewHandlers) {
			views.add(buildView(composite, viewHandler, i++));
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
	protected Object buildView(Composite composite, ViewHandler<?> handler, int index) {
		Object view = null;
		Group group = new Group(composite, SWT.NONE);
		group.setText("View " + index);
		group.setLayout(new FillLayout());
		Composite container = new Composite(group, SWT.NONE);
		container.setLayout(new FillLayout());
		if (handler instanceof SWTViewHandler) {
			try {
				view = ((SWTViewHandler)handler).createView(container);
				handler.initView(editingContext.getEditingComponent());
			} catch (ViewConstructionException e) {
				fail("An error occured during view creation");
			}
		} else if (handler instanceof PropertiesEditingViewHandler) {
			try {
				view = ((PropertiesEditingViewHandler)handler).createView(editingContext.getEditingComponent(), container);
				handler.initView(editingContext.getEditingComponent());
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
		for (ViewHandler<?> handler : viewHandlers) {
			handler.dispose();
		}
		shell.dispose();
		views.clear();
	}

}
