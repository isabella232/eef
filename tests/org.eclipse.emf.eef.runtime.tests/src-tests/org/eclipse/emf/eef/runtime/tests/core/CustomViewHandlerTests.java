/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelPackage;
import org.eclipse.emf.eef.eeftests.bindingmodel.Sample;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.handlers.CustomSWTViewHandler;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.views.SampleCustomView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
				.bindClass(BindingmodelPackage.Literals.SAMPLE)
					.withView(SampleCustomView.class).handler(new CustomSWTViewHandler(null, null, SampleCustomView.class))
				.build();
	}
	
	@Test
	public void testCustomViewHandler() {

		SampleCustomView view = (SampleCustomView) views.get(0);
		assertTrue("The view isn't properly initialized", !view.isActive());
		((Sample)((EObjectPropertiesEditingContext)context).getEObject()).setActive(true);
		assertTrue("The view isn't properly updated", view.isActive());
	}

}
