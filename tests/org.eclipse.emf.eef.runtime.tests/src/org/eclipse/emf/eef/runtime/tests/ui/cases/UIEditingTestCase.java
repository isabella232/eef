/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.cases;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.AbstractPropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandler;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
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
public abstract class UIEditingTestCase {


	protected EObject elementToEdit;
	protected PropertiesEditingContext context;
	protected PropertiesEditingComponent component;
	protected List<Object> views;

	protected Shell shell;
	private Collection<ViewHandler<?>> viewHandlers;

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
	 * @return the {@link PropertiesEditingModel} for the test case.
	 */
	protected PropertiesEditingModel buildEditingModel() {
		return new EEFTestStuffsBuilder().buildEditingModelWithSWTViews();
	}

	/**
	 * @return {@link PropertiesEditingContext} for the test case.
	 */
	protected PropertiesEditingContext buildEditingContext() {
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory.addAdapterFactory(new AbstractPropertiesEditingProvider() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.editingModel.AbstractPropertiesEditingProvider#initSpecificEditingModel()
			 */
			protected Collection<? extends PropertiesEditingModel> initSpecificEditingModel() {
				List<PropertiesEditingModel> result = new ArrayList<PropertiesEditingModel>();
				result.add(buildEditingModel());
				return result;
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.editingModel.AbstractPropertiesEditingProvider#initViewHandlerProvider()
			 */
			protected ViewHandlerProvider initViewHandlerProvider() {
				return new EEFTestStuffsBuilder().buildViewHandlerProvider();
			}
			
		});
		PropertiesEditingContext context = 
				new EObjectPropertiesEditingContext(adapterFactory, elementToEdit);
		return context;
	}
	
	/**
	 * @return the element to edit.
	 */
	@SuppressWarnings("unchecked")
	public <T extends EObject> T getElementToEdit() {
		if (context instanceof EObjectPropertiesEditingContext) {
			return (T)((EObjectPropertiesEditingContext) context).getEObject();
		}
		return null;
	}

	@After
	public void tearDown() {
		disposeUI();
	}

	/**
	 * 
	 */
	protected void initUI() {
		context = buildEditingContext();
		shell = new Shell();
		shell.setLayout (new FillLayout());
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FillLayout());
		component = context.getEditingComponent();
		viewHandlers = component.createViewHandlers();
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
				handler.initView(component);
			} catch (ViewConstructionException e) {
				fail("An error occured during view creation");
			}
		} else if (handler instanceof PropertiesEditingViewHandler) {
			try {
				view = ((PropertiesEditingViewHandler)handler).createView(component, container);
				handler.initView(component);
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
