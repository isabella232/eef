/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests;

import static org.junit.Assert.*;

import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelPackage;

import org.eclipse.emf.eef.runtime.model.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingModelTests {

	@Test
	public void testBuilder() {
		PropertiesEditingModel.Builder builder = new PropertiesEditingModel.Builder();
		builder.bind(BindingmodelPackage.Literals.SAMPLE, SampleView.class);
		PropertiesEditingModel model = builder.build();
		assertNotNull(model);
	}
	

}
