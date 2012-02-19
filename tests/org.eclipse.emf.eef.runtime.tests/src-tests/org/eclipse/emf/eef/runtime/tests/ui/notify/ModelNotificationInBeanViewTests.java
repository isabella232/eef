/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.notify;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelFactory;
import org.eclipse.emf.eef.eeftests.bindingmodel.Root;
import org.eclipse.emf.eef.eeftests.bindingmodel.Sample;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
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
		return new EEFTestStuffsBuilder().buildEditingModelWithSWTViews();
	}

	@Test
	public void testSetRefresh() {
		disposeUI();
		elementToEdit = elementToEdit();
		initUI();
		Sample sample = (Sample) elementToEdit;
		SampleView sampleView = (SampleView)views.get(0);

		sample.setName("New name");
		assertEquals("Bad view refresh", sample.getName(), sampleView.getName());

		sample.setActive(true);
		assertEquals("Bad view refresh", sample.isActive(), sampleView.isActive());		
	}
	
	@Test
	public void testAddRemoveRefreshs() {
		disposeUI();
		elementToEdit = BindingmodelFactory.eINSTANCE.createRoot();
		initUI();
		
		Root root = (Root) elementToEdit;
		RootView rootView = (RootView)views.get(0);

		root.getSamples().add(BindingmodelFactory.eINSTANCE.createSample());
		assertEquals("Bad view refresh", 1, rootView.samplesSize());

		root.getSamples().remove(0);
		assertEquals("Bad view refresh", 0, rootView.samplesSize());
	}
}
