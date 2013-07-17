/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.cases;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.editingview.PropertiesEditingViewHandler;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handle.exceptions.ViewConstructionException;
import org.eclipse.swt.widgets.Composite;
import org.junit.Before;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class NonUIEditingTestCase {

	protected PropertiesEditingContext editingContext;
	protected EObject editedObject;

	protected List<Object> views;
	protected Builder environmentBuilder;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		environmentBuilder = initEnvironmentBuilder();
		initEditingContext();
		offScreenViewsInitialization();
	}

	protected EEFTestEnvironment.Builder initEnvironmentBuilder() {
		return new EEFTestEnvironment.Builder();
	}
	
	protected final void initEditingContext() {
		EEFTestEnvironment environment = environmentBuilder.build();
		editingContext =  environment.getEditingContext();
		editedObject = environment.getEditedObject();
	}

	@SuppressWarnings("unchecked")
	protected void offScreenViewsInitialization() throws ViewConstructionException {
		List<View> viewDescriptors = editingContext.getEditingComponent().getViewDescriptors();
		views = new ArrayList<Object>();
		for (View viewDescriptor : viewDescriptors) {
			PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
			ViewHandler<?> viewHandler = editingContext.getViewHandlerProvider().getViewHandler(viewDescriptor);
			Object view = viewHandler.createView(viewDescriptor, editingComponent);
			if (viewHandler instanceof PropertiesEditingViewHandler && view instanceof PropertiesEditingView) {
				((PropertiesEditingViewHandler)viewHandler).initView(editingComponent, (PropertiesEditingView<Composite>) view);
			} else if (viewHandler instanceof SWTViewHandler && view instanceof Composite) {
				((SWTViewHandler)viewHandler).initView(editingComponent, (Composite) view);
			}
			views.add(view);
		}
	}
}
