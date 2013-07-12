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
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.internal.view.notify.EEFNotifierProviderImpl;
import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.services.impl.PriorityCircularityException;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.EEFServiceDescriptor;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotification;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifier;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifierProvider;
import org.eclipse.emf.eef.runtime.view.notify.EEFPropertyNotification;
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
		Collection<EEFServiceDescriptor<? extends EEFService<Object>>> notifiers = new ArrayList<EEFTestEnvironment.EEFServiceDescriptor<? extends EEFService<Object>>>();
		testNotifier = new TestNotifier();
		notifiers.add(new EEFServiceDescriptor<EEFNotifier>("testNotifier", testNotifier));
		builder.setEditingModel(new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID)
									.bindClass(EcorePackage.Literals.EREFERENCE)
										.withView(SampleView.class)
										.bindProperty(EcorePackage.Literals.ETYPED_ELEMENT__LOWER_BOUND)
											.withEditor("name")
									.bindClass(EcorePackage.Literals.ECLASS)
										.withView(SampleView.class)
									.build());
		builder.setEEFNotifierProvider(createEEFNotifierProvider());
		return builder;
	}
	
	public EEFNotifierProvider createEEFNotifierProvider() {
		EEFNotifierProviderImpl result = new EEFNotifierProviderImpl();
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, "testNotifier");
		try {
			result.addService(testNotifier, properties);
		} catch (PriorityCircularityException e) {
			e.printStackTrace();
		}
		return result;
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
