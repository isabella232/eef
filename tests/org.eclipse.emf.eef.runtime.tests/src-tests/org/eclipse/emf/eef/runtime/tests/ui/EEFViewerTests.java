/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.ui.cases.PropertiesEditingViewEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironmentBuilder;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFViewerTests extends PropertiesEditingViewEditingTestCase {

	
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.ui.cases.PropertiesEditingViewEditingTestCase#buildEditingModel()
	 */
	@Override
	protected PropertiesEditingModel buildEditingModel() {
		return new EEFTestEnvironmentBuilder().buildEditingModelWithPropertiesEditingViews();
	}

	/**
	 * 
	 */
	@Test
	public void testViewsCreation() {
		assertEquals("Bad count of created views", 2, getViews().size());
	}
}
