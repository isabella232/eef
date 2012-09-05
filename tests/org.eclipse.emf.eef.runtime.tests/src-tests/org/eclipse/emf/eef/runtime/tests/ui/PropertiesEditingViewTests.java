/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.eef.runtime.tests.ui.cases.PropertiesEditingViewEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * Test the {@link PropertiesEditingView}s.
 */
public class PropertiesEditingViewTests extends PropertiesEditingViewEditingTestCase {

	private static final boolean NEW_SAMPLE_ACTIVE_FOR_TEST = !EEFTestEnvironment.FIRST_ECLASS_SAMPLE_ABSTRACTNESS;
	private static final String NEW_SAMPLE_NAME_FOR_TEST = "NewEClass1";

	/**
	 * Test the good building of the {@link PropertiesEditingView}.
	 */
	@Test
	public void testPropertiesEditingViewBuild() {
		assertEquals("Bad count of views.", 2, getViews().size());
		Composite view = getViews().get(0);
		assertEquals("Bad view selection.", 8, view.getChildren().length);
		Text nameText = getControl(view, 1);
		assertEquals("Bad view initialization", EEFTestEnvironment.FIRST_ECLASS_SAMPLE_NAME, nameText.getText());
		Button activeCheck = getControl(view, 3);
		assertEquals("Bad view initialization", EEFTestEnvironment.FIRST_ECLASS_SAMPLE_ABSTRACTNESS, activeCheck.getSelection());
	}

	/**
	 * Test the {@link PropertiesEditingView} update.
	 */
	@Test
	public void testViewUpdate() {
		EClass sample = (EClass) editedObject;
		sample.setName(NEW_SAMPLE_NAME_FOR_TEST);
		Composite view = getViews().get(0);
		Text nameText = getControl(view, 1);
		assertEquals("bad view refresh", NEW_SAMPLE_NAME_FOR_TEST, nameText.getText());
		sample.setAbstract(NEW_SAMPLE_ACTIVE_FOR_TEST);
		Button activeCheck = getControl(view, 3);
		assertEquals("bad view refresh", NEW_SAMPLE_ACTIVE_FOR_TEST, activeCheck.getSelection());
	}
	
}
