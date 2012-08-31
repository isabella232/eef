/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.notify;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.internal.services.viewhandler.PriorityCircularityException;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironmentBuilder;
import org.eclipse.emf.eef.runtime.tests.views.RootView;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * NOTE: I don't know how to cause the 'Unset Event'
 */
public class ModelNotificationInBeanViewTests extends UIEditingTestCase {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase#buildEditingModel()
	 */
	protected PropertiesEditingModel buildEditingModel() {
		return new EEFTestEnvironmentBuilder().buildEditingModelWithSWTViews();
	}

	@Test
	public void testSetRefresh() throws PriorityCircularityException {
		disposeUI();
		editedObject = createEditedObject();
		initEditingContext();
		initUI();
		EClass sample = (EClass) editedObject;
		SampleView sampleView = (SampleView)views.get(0);

		sample.setName("New name");
		assertEquals("Bad view refresh", sample.getName(), sampleView.getName());

		sample.setAbstract(true);
		assertEquals("Bad view refresh", sample.isAbstract(), sampleView.isAbstract());		
	}
	
	@Test
	public void testAddRemoveRefreshs() throws PriorityCircularityException {
		disposeUI();
		editedObject = EcoreFactory.eINSTANCE.createEPackage();
		initEditingContext();
		initUI();
		
		EPackage root = (EPackage) editedObject;
		RootView rootView = (RootView)views.get(0);

		root.getEClassifiers().add(EcoreFactory.eINSTANCE.createEClass());
		assertEquals("Bad view refresh", 1, rootView.eClassifiersSize());

		root.getEClassifiers().remove(0);
		assertEquals("Bad view refresh", 0, rootView.eClassifiersSize());
	}
}
