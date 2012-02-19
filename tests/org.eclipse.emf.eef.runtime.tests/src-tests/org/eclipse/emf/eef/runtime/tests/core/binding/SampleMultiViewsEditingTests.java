/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core.binding;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelPackage;
import org.eclipse.emf.eef.eeftests.bindingmodel.Sample;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.views.SampleActiveView;
import org.eclipse.emf.eef.runtime.tests.views.SampleNameView;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SampleMultiViewsEditingTests extends UIEditingTestCase {

	private static final String NEW_NAME = "New Name";

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase#buildEditingModel()
	 */
	@Override
	protected PropertiesEditingModel buildEditingModel() {
		return new EditingModelBuilder()
					.bindClass(BindingmodelPackage.Literals.SAMPLE)
						.withView(SampleNameView.class)
						.withView(SampleActiveView.class)
					.build();
	}

	@Test
	public void testMultiViewsEditing() {
		((Sample)((EObjectPropertiesEditingContext)context).getEObject()).setName(NEW_NAME);
		assertTrue("The view isn't properly updated", NEW_NAME.equals(((SampleNameView)views.get(0)).getName()));
		((Sample)((EObjectPropertiesEditingContext)context).getEObject()).setActive(true);
		assertTrue("The view isn't properly updated", ((SampleActiveView)views.get(1)).isActive());
		
	}
	
}
