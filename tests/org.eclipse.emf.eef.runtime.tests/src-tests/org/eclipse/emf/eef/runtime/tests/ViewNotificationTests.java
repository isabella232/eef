/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests;

import static org.junit.Assert.assertEquals;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.eef.runtime.notify.TypedPropertyChangedEvent;
import org.eclipse.emf.eef.runtime.tests.ui.cases.NonUIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.views.EClassMockView;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewNotificationTests extends NonUIEditingTestCase {

	@Test
	public void testSetViewNotification() {
		EClassMockView view = (EClassMockView) views.get(0);
		String newValue = "new EClass Name";
		view.getSupport().firePropertyChange(new PropertyChangeEvent(view, "name", null, newValue));
		assertEquals("The _SET_ view event haven't be correctly propagated.", newValue, ((EClass)editedObject).getName());
	}

	@Test
	public void testUnsetViewNotification() {
		EClassMockView view = (EClassMockView) views.get(0);
		view.getSupport().firePropertyChange(new TypedPropertyChangedEvent(view, "name", TypedPropertyChangedEvent.UNSET, null, null));
		assertEquals("The _UNSET_ view event haven't be correctly propagated.", null, ((EClass)editedObject).getName());
	}

	@Test
	public void testAddViewNotification() {
		EClassMockView view = (EClassMockView) views.get(0);
		int oldSize = ((EClass)editedObject).getESuperTypes().size();
		view.getSupport().firePropertyChange(new TypedPropertyChangedEvent(view, "eSuperTypes", TypedPropertyChangedEvent.ADD, null, EcoreFactory.eINSTANCE.createEClass()));
		assertEquals("The _ADD_ view event haven't be correctly propagated.", oldSize + 1, ((EClass)editedObject).getESuperTypes().size());
	}

	@Test
	public void testAddManyViewNotification() {
		EClassMockView view = (EClassMockView) views.get(0);
		int oldSize = ((EClass)editedObject).getESuperTypes().size();
		List<EClass> newClasses = new ArrayList<EClass>();
		newClasses.add(EcoreFactory.eINSTANCE.createEClass());
		newClasses.add(EcoreFactory.eINSTANCE.createEClass());
		view.getSupport().firePropertyChange(new TypedPropertyChangedEvent(view, "eSuperTypes", TypedPropertyChangedEvent.ADD_MANY, null, newClasses));
		assertEquals("The _ADD_MANY_ view event haven't be correctly propagated.", oldSize + newClasses.size(), ((EClass)editedObject).getESuperTypes().size());
	}

	@Test
	public void testRemoveViewNotification() {
		EClassMockView view = (EClassMockView) views.get(0);
		int oldSize = ((EClass)editedObject).getESuperTypes().size();
		view.getSupport().firePropertyChange(new TypedPropertyChangedEvent(view, "eSuperTypes", TypedPropertyChangedEvent.REMOVE, ((EClass) editedObject).getESuperTypes().get(0), null));
		assertEquals("The _REMOVE_ view event haven't be correctly propagated.", oldSize - 1, ((EClass)editedObject).getESuperTypes().size());
	}

	@Test
	public void testRemoveManyViewNotification() {
		EClassMockView view = (EClassMockView) views.get(0);
		view.getSupport().firePropertyChange(new TypedPropertyChangedEvent(view, "eSuperTypes", TypedPropertyChangedEvent.REMOVE_MANY, ((EClass) editedObject).getESuperTypes(), null));
		assertEquals("The _REMOVE_MANY_ view event haven't be correctly propagated.", 0, ((EClass)editedObject).getESuperTypes().size());
	}

}
