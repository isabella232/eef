/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core.binding;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelPackage;
import org.eclipse.emf.eef.eeftests.bindingmodel.Sample;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.views.SampleTitleView;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertyBindingTests extends UIEditingTestCase {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase#buildEditingModel()
	 */
	protected PropertiesEditingModel buildEditingModel() {
		return new EditingModelBuilder().bindClass(BindingmodelPackage.Literals.SAMPLE)
					.withView(SampleTitleView.class)
					.bindProperty(BindingmodelPackage.Literals.ABSTRACT_SAMPLE__NAME)
						.withEditor("title")
				.build();
	}

	@Test
	public void testPropertyBindingEditing() {
		Sample sample = (Sample) elementToEdit;
		sample.setName("New name");
		SampleTitleView sampleView = (SampleTitleView)views.get(0);
		assertEquals("Bad view refresh", sample.getName(), sampleView.getTitle());

		sampleView.notifiedSetTitle("Other Name");
		assertEquals("Bad model refresh", sample.getName(), sampleView.getTitle());
	}

}
