/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.cases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder;
import org.junit.Before;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class NonUIEditingTestCase {

	protected PropertiesEditingContext editingContext;
	protected EObject editedObject;

	protected List<Object> views;
	protected Collection<ViewHandler<?>> viewHandlers;
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

	protected void offScreenViewsInitialization() throws ViewConstructionException {
		viewHandlers = editingContext.getEditingComponent().createViewHandlers();
		views = new ArrayList<Object>();
		for (ViewHandler<?> viewHandler : viewHandlers) {
			PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
			Object view = viewHandler.createView(editingComponent);
			viewHandler.initView(editingComponent);
			views.add(view);
		}
	}
}
