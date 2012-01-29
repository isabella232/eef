/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.eef.eeftests.bindingmodel.Sample;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewNotificationTests extends UIEditingTestCase {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase#buildEditingModel()
	 */
	protected PropertiesEditingModel buildEditingModel() {
		return new EEFTestStuffsBuilder().buildEditingModel();
	}

	@Test
	public void testModelRefresh() {
		Sample sample = (Sample) elementToEdit;
		SampleView sampleView = (SampleView)views.get(0);

		sampleView.notifiedSetName("New Name");
		assertEquals("Bad model refresh", sample.getName(), sampleView.getName());

	}

}
