/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.handlers.CustomSWTViewHandler;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.views.SampleCustomView;
import org.junit.Test;

/**
 * @author glefur
 *
 */
public class CustomViewHandlerTests extends UIEditingTestCase {


	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase#buildEditingModel()
	 */
	protected PropertiesEditingModel buildEditingModel() {
		return new EditingModelBuilder()
				.bindClass(EcorePackage.Literals.ECLASS)
					.withView(SampleCustomView.class).handler(new CustomSWTViewHandler(null, null, SampleCustomView.class))
				.build();
	}
	
	@Test
	public void testCustomViewHandler() {
		SampleCustomView view = (SampleCustomView) views.get(0);
		assertTrue("The view isn't properly initialized", !view.isAbstract());
		((EClass) editedObject).setAbstract(true);
		assertTrue("The view isn't properly updated", view.isAbstract());
	}

}
