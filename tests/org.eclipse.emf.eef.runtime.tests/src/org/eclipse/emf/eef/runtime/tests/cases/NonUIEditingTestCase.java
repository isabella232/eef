/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.cases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.services.PriorityCircularityException;
import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderImpl;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironmentBuilder;
import org.eclipse.emf.eef.runtime.tests.views.EClassMockView;
import org.junit.Before;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class NonUIEditingTestCase {

	protected AdapterFactory adapterFactory;

	protected PropertiesEditingContext editingContext;
	protected EObject editedObject;

	protected List<Object> views;
	protected Collection<ViewHandler<?>> viewHandlers;

	private PropertiesEditingProvider editingProvider;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		editedObject = createEditedObject();
		adapterFactory = buildAdapterFactory();
		editingProvider = createEditingProvider(buildEditingModel());
		initEditingContext();
		offScreenViewsInitialization();
	}

	protected void initEditingContext() throws PriorityCircularityException {
		EMFServiceProvider emfServiceProvider = createEMFServiceProvider();
		editingContext =  createEditingContext(editingProvider, emfServiceProvider);
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

	protected PropertiesEditingProvider createEditingProvider(final PropertiesEditingModel editingModel) {
		PropertiesEditingProvider editingProvider = new PropertiesEditingProviderImpl() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderImpl#getEditingModel()
			 */
			@Override
			protected PropertiesEditingModel getEditingModel() {
				return editingModel;
			}
			
			
		};
		return editingProvider;
	}


	protected EMFServiceProvider createEMFServiceProvider() {
		EEFTestEnvironmentBuilder eefTestEnvironmentBuilder = new EEFTestEnvironmentBuilder();
		return eefTestEnvironmentBuilder.createEMFServiceProvider(eefTestEnvironmentBuilder.createEMFServices());
	}


	protected PropertiesEditingContext createEditingContext(PropertiesEditingProvider editingProvider, EMFServiceProvider emfServiceProvider) throws PriorityCircularityException {
		return new EEFTestEnvironmentBuilder().buildEditingContext(adapterFactory, editingProvider, emfServiceProvider, editedObject);
	}


	protected AdapterFactory buildAdapterFactory() {
		return new EEFTestEnvironmentBuilder().buildAdapterFactory();
	}

	
	protected EObject createEditedObject() {
		EPackage ecoreModel = new EEFTestEnvironmentBuilder().buildEcoreSampleModel();
		return ecoreModel.getEClassifiers().get(0);
	}
	
	
	/**
	 * @return
	 */
	protected PropertiesEditingModel buildEditingModel() {
		return new EditingModelBuilder()
							.bindClass(EcorePackage.Literals.ECLASS)
								.withView(EClassMockView.class)
							.build();
	}
}
