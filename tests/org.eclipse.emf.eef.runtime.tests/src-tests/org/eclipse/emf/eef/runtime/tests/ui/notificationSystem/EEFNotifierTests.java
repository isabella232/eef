/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.notificationSystem;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.impl.EEFServiceRegistryImpl;
import org.eclipse.emf.eef.runtime.services.impl.PriorityCircularityException;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotification;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifier;
import org.eclipse.emf.eef.runtime.view.notify.EEFPropertyNotification;
import org.eclipse.swt.widgets.Composite;
import org.junit.Test;

/**
 * This case tests the EEF UI notification system.
 * 
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class EEFNotifierTests extends UIEditingTestCase {

	private TestNotifier testNotifier;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase#initEnvironmentBuilder()
	 */
	@Override
	protected Builder initEnvironmentBuilder() {
		Builder builder = super.initEnvironmentBuilder();
		builder.setComponentRegistry(createComponentRegistry(builder));
		builder.setEditingModel(new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID)
									.bindClass(EcorePackage.Literals.EREFERENCE)
										.withView(SampleView.class)
										.bindProperty(EcorePackage.Literals.ETYPED_ELEMENT__LOWER_BOUND)
											.withEditor("name")
									.bindClass(EcorePackage.Literals.ECLASS)
										.withView(SampleView.class)
									.build());
		return builder;
	}

	public EEFServiceRegistry createComponentRegistry(EEFTestEnvironment.Builder builder) {
		EEFServiceRegistry componentRegistry = new EEFServiceRegistryImpl();
		try {
			for (EMFService emfService : builder.getEMFServices()) {
				Map<String, String> properties = new HashMap<String, String>();
				properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, emfService.getClass().getName());
				componentRegistry.addService(emfService, properties);
			}
			for (ViewService service : builder.getViewServices()) {
				Map<String, String> properties = new HashMap<String, String>();
				properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, service.getClass().getName());
				componentRegistry.addService(service, properties);			
			}
			for (ToolkitPropertyEditorProvider provider : builder.getEditorProviders()) {
				Map<String, String> properties = new HashMap<String, String>();
				properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, provider.getClass().getName());
				componentRegistry.addService(provider, properties);
			}
			for (PropertiesEditingProvider provider : builder.getEditingProviders()) {
				Map<String, String> properties = new HashMap<String, String>();
				properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, provider.getClass().getName());
				componentRegistry.addService(provider, properties);
			}
			Map<String, String> properties = new HashMap<String, String>();
			properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, TestNotifier.class.getName());
			testNotifier = new TestNotifier();
			componentRegistry.addService(testNotifier, properties);
			properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, EEFTestEnvironment.REFLECT_VIEW_HANDLER_PROVIDER_NAME);
			componentRegistry.addService(new ReflectViewHandlerProvider() {

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService#providedServices()
				 */
				@Override
				public Collection<String> providedServices() {
					List<String> result = new ArrayList<String>();
					result.add(ViewHandlerProvider.class.getName());
					return result;
				}
				
			}, properties);
			properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, EEFTestEnvironment.SWT_VIEW_HANDLER_PROVIDER_NAME);
			properties.put(EEFTestEnvironment.PRIORITY_OVER_KEY, EEFTestEnvironment.REFLECT_VIEW_HANDLER_PROVIDER_NAME);
			componentRegistry.addService(new SWTViewHandlerProvider() {

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService#providedServices()
				 */
				@Override
				public Collection<String> providedServices() {
					List<String> result = new ArrayList<String>();
					result.add(ViewHandlerProvider.class.getName());
					return result;
				}

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandlerProvider#getHandler(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
				 */
				@SuppressWarnings("unchecked")
				@Override
				public ViewHandler<? extends Composite> getHandler(PropertiesEditingComponent editingComponent, Object view) {
					return new SWTViewHandler(this, editingComponent, (Class<? extends Composite>) view) {
						
						/**
						 * {@inheritDoc}
						 * @see org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandler#canHandle(java.lang.Object)
						 */
						@Override
						public boolean canHandle(Object editor) {
							return "name".equals(editor);
						}

					};
				}
				
			}, properties);
			properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, EEFTestEnvironment.PROPERTIES_EDITING_VIEW_HANDLER_PROVIDER_NAME);
			properties.put(EEFTestEnvironment.PRIORITY_OVER_KEY, EEFTestEnvironment.SWT_VIEW_HANDLER_PROVIDER_NAME);
			PropertiesEditingViewHandlerProvider handler = new PropertiesEditingViewHandlerProvider() {

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService#providedServices()
				 */
				@Override
				public Collection<String> providedServices() {
					List<String> result = new ArrayList<String>();
					result.add(ViewHandlerProvider.class.getName());
					return result;
				}
				
			};
			componentRegistry.addService(handler, properties);
		} catch (PriorityCircularityException e) {
			//TODO: can't happen!
		}
		return componentRegistry;
	}

	
	/**
	 * This test sets a inconvertible string in int value for the lower editor and checks that the {@link TestNotifier} has notified the editor.
	 * Then, it sets a valid value and check that the editor hasn't notification anymore.
	 */
	@Test
	public void testEditorNotifying() {
		environmentBuilder.setEditedObject(EcoreFactory.eINSTANCE.createEReference());
		environmentBuilder.setEditingContext(null);
		initEditingContext();
		disposeUI();
		initUI();
		SampleView view = (SampleView) views.get(0);
		view.notifiedSetName("Invalid int value ##%%");
		assertTrue("Bad notification broadcast.", testNotifier.isEditorNotified("name"));
		view.notifiedSetName("1");
		assertFalse("Bad notification broadcast.", testNotifier.isEditorNotified("name"));
	}
	
	@Test
	public void testViewNotifying() {
		environmentBuilder.setEditedObject(EcoreFactory.eINSTANCE.createEClass());
		environmentBuilder.setEditingContext(null);
		initEditingContext();
		disposeUI();
		initUI();
		SampleView view = (SampleView) views.get(0);
		view.notifiedSetName("Invalid EClass name ##%%");
		assertTrue("Bad notification broadcast.", testNotifier.isViewNotified());
		view.notifiedSetName("ValidEClassNames");
		assertFalse("Bad notification broadcast.", testNotifier.isViewNotified());
	}
	
	
	private static final class TestNotifier implements EEFNotifier {

		private Collection<Object> editorsNotified;
		private boolean viewNotified;
		
		public TestNotifier() {
			editorsNotified = new ArrayList<Object>();
			viewNotified = false;
		}

		/**
		 * @return the editorsNotified
		 */
		public boolean isEditorNotified(Object editor) {
			return editorsNotified.contains(editor);
		}

		/**
		 * @return the viewNotified
		 */
		public boolean isViewNotified() {
			return viewNotified;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.services.EEFComponent#getComponentRegistry()
		 */
		public EEFServiceRegistry getComponentRegistry() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.services.EEFComponent#providedServices()
		 */
		public Collection<String> providedServices() {
			List<String> result = new ArrayList<String>();
			result.add(EEFNotifier.class.getName());
			return result;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
		 */
		public boolean serviceFor(Object element) {
			return true;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.services.EEFComponent#setComponentRegistry(org.eclipse.emf.eef.runtime.services.EEFServiceRegistry)
		 */
		public void setComponentRegistry(EEFServiceRegistry componentRegistry) {
			
		}

		/**
		 * This test assumes that the decoration are correctly added and removed.
		 * 
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#notify(org.eclipse.emf.eef.runtime.view.notify.EEFNotification)
		 */
		public void notify(Object view, EEFNotification notification) {
			if (notification instanceof EEFPropertyNotification) {
				editorsNotified.add(((EEFPropertyNotification) notification).getEditor());
			} else {
				viewNotified = true;
			}
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#clearViewNotification()
		 */
		public void clearViewNotification(Object view) {
			viewNotified = false;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#clearEditorNotification(java.lang.Object)
		 */
		public void clearEditorNotification(Object view, Object editor) {
			editorsNotified.remove(editor);
		}
		
	}
	
}
