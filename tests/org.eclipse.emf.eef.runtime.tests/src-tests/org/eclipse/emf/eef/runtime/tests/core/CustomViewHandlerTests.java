/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.tests.handlers.CustomSWTViewHandler;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder;
import org.eclipse.emf.eef.runtime.tests.views.SampleCustomView;
import org.junit.Test;

/**
 * @author glefur
 *
 */
public class CustomViewHandlerTests extends UIEditingTestCase {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase#initEnvironmentBuilder()
	 */
	@Override
	protected Builder initEnvironmentBuilder() {
		return super.initEnvironmentBuilder().setEditingModel(new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID)
																		.bindClass(EcorePackage.Literals.ECLASS)
																		.withView(SampleCustomView.class).handler(new CustomSWTViewHandler(null, null, SampleCustomView.class))
																	.build());
	}
	
	@Test
	public void testCustomViewHandler() {
		SampleCustomView view = (SampleCustomView) views.get(0);
		assertTrue("The view isn't properly initialized", !view.isAbstract());
		((EClass) editedObject).setAbstract(true);
		assertTrue("The view isn't properly updated", view.isAbstract());
	}

}
