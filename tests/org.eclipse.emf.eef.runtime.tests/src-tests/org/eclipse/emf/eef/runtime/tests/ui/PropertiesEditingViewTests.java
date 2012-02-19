/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui;

import org.eclipse.emf.eef.eeftests.bindingmodel.Sample;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.tests.ui.cases.PropertiesEditingViewEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
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

	private static final boolean NEW_SAMPLE_ACTIVE_FOR_TEST = !EEFTestStuffsBuilder.SAMPLE_ACTIVATION_INITIALIZATION_EDITING_VIEWS_CONTEXT;
	private static final String NEW_SAMPLE_NAME_FOR_TEST = "New Sample name for Test";

	
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.ui.cases.PropertiesEditingViewEditingTestCase#buildEditingContext()
	 */
	@Override
	protected PropertiesEditingContext buildEditingContext() {
		return new EEFTestStuffsBuilder().buildEditingContextWithPropertiesEditingViews();
	}

	/**
	 * Test the good building of the {@link PropertiesEditingView}.
	 */
	@Test
	public void testPropertiesEditingViewBuild() {
		assertEquals("Bad count of views.", 1, getViews().size());
		Composite view = getViews().get(0);
		assertEquals("Bad view selection.", 5, view.getChildren().length);
		Text nameText = getControl(view, 1);
		assertEquals("Bad view initialization", EEFTestStuffsBuilder.SAMPLE_NAME_INITIALIZATION_EDITING_VIEWS_CONTEXT, nameText.getText());
		Button activeCheck = getControl(view, 3);
		assertEquals("Bad view initialization", EEFTestStuffsBuilder.SAMPLE_ACTIVATION_INITIALIZATION_EDITING_VIEWS_CONTEXT, activeCheck.getSelection());
	}

	/**
	 * Test the {@link PropertiesEditingView} update.
	 */
	@Test
	public void testViewUpdate() {
		Sample sample = getElementToEdit();
		sample.setName(NEW_SAMPLE_NAME_FOR_TEST);
		Composite view = getViews().get(0);
		Text nameText = getControl(view, 1);
		assertEquals("bad view refresh", NEW_SAMPLE_NAME_FOR_TEST, nameText.getText());
		sample.setActive(NEW_SAMPLE_ACTIVE_FOR_TEST);
		Button activeCheck = getControl(view, 3);
		assertEquals("bad view refresh", NEW_SAMPLE_ACTIVE_FOR_TEST, activeCheck.getSelection());
	}
	
}
