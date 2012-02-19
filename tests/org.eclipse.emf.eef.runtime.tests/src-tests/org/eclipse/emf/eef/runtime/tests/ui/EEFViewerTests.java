/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui;

import org.eclipse.emf.eef.runtime.tests.ui.cases.PropertiesEditingViewEditingTestCase;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFViewerTests extends PropertiesEditingViewEditingTestCase {

	/**
	 * 
	 */
	@Test
	public void testViewsCreation() {
		assertEquals("Bad count of created views", 2, getViews().size());
	}
}
