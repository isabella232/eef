/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core.notify;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.emf.eef.runtime.notify.PropertiesValidationEditingEvent;
import org.eclipse.emf.eef.runtime.tests.ui.cases.NonUIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.views.EClassMockView;
import org.eclipse.emf.eef.runtime.tests.views.EClassMockView2;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingListenerTests extends NonUIEditingTestCase {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.ui.cases.NonUIEditingTestCase#buildEditingModel()
	 */
	protected PropertiesEditingModel buildEditingModel() {
		return new EditingModelBuilder()
		.bindClass(EcorePackage.Literals.ECLASS)
			.withView(EClassMockView.class)
			.withView(EClassMockView2.class)
		.build();
	}

	@Test
	public void testNotificationsInterViews() {
		EClassMockView view1 = (EClassMockView) views.get(0);
		EClassMockView2 view2 = (EClassMockView2) views.get(1);
		view1.getSupport().firePropertyChange(new PropertyChangeEvent(view1, "name", null, "newEClassName"));
		Collection<PropertiesEditingEvent> allEvents = view2.getEvents();
		Collection<PropertiesEditingEvent> editingEvents = new ArrayList<PropertiesEditingEvent>();
		for (PropertiesEditingEvent propertiesEditingEvent : allEvents) {
			if (propertiesEditingEvent instanceof PropertiesEditingEventImpl) {
				editingEvents.add(propertiesEditingEvent);
			}
		}
		assertTrue("Bad view communication.", allEvents.size() > 0);
	}
	
	@Test
	public void testValidationNotifications() {
		EClassMockView view1 = (EClassMockView) views.get(0);
		ValidationEditingListener validationListener = new ValidationEditingListener();
		context.getEditingComponent().addEditingListener(validationListener);
		view1.getSupport().firePropertyChange(new PropertyChangeEvent(view1, "name", null, "new EClass Name"));
		assertEquals("Validation Event not propagated.", 1, validationListener.getEvents().size());
		assertTrue("Bad validation diagnostic.", validationListener.getEvents().get(0).getDiagnostic().getSeverity() != Diagnostic.OK);
	}
	
	private final class ValidationEditingListener implements PropertiesEditingListener {
		
		private List<PropertiesValidationEditingEvent> events;
		
		/**
		 * 
		 */
		public ValidationEditingListener() {
			events = new ArrayList<PropertiesValidationEditingEvent>();
		}

		public void firePropertiesChanged(PropertiesEditingEvent event) {
			if (event instanceof PropertiesValidationEditingEvent) {
				events.add((PropertiesValidationEditingEvent) event);
			}
		}

		/**
		 * @return the events
		 */
		public List<PropertiesValidationEditingEvent> getEvents() {
			return events;
		}
	}

}
