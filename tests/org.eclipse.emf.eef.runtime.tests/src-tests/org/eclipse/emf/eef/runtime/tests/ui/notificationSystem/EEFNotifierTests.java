/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.notificationSystem;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.internal.services.viewhandler.ViewHandlerProviderRegistryImpl;
import org.eclipse.emf.eef.runtime.services.impl.PriorityCircularityException;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandlerProvider;
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
		ViewHandlerProviderRegistryImpl registry = (ViewHandlerProviderRegistryImpl) builder.createEmptyViewHandlerProviderRegistry();
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, EEFTestEnvironment.SWT_VIEW_HANDLER_PROVIDER_NAME);
		try {
			registry.addService(new SWTViewHandlerProvider() {

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

						/**
						 * {@inheritDoc}
						 * @see org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandler#getNotifier()
						 */
						@Override
						public EEFNotifier getNotifier() {
							testNotifier = new TestNotifier();
							return testNotifier;
						}
						
					};
				}
				
			}, properties);
		} catch (PriorityCircularityException e) {
			fail("Exception during viewhandler definition : " + e.getMessage());
		}
		builder.setViewHandlerProviderRegistry(registry);
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
		 * This test assumes that the decoration are correctly added and removed.
		 * 
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#notify(org.eclipse.emf.eef.runtime.view.notify.EEFNotification)
		 */
		public void notify(EEFNotification notification) {
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
		public void clearViewNotification() {
			viewNotified = false;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#clearEditorNotification(java.lang.Object)
		 */
		public void clearEditorNotification(Object editor) {
			editorsNotified.remove(editor);
		}
		
	}
	
}
