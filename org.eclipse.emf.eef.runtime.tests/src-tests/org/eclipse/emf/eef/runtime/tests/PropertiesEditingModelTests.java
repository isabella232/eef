/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelProvider;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingModelTests {
	
	private EEFTestStuffsBuilder builder;

	@Before
	public void setUp() {
		this.builder = new EEFTestStuffsBuilder();
	}

	@Test
	public void testBuilder() {
		PropertiesEditingModel model = builder.buildEditingModel();
		assertNotNull(model);
	}
	
	@Test
	public void testBinding() {
		EditingModelProvider model = new EditingModelProvider(builder.buildEditingModel());
		PropertiesEditingComponent component = (PropertiesEditingComponent) model.adapt(BindingmodelFactory.eINSTANCE.createSample(), PropertiesEditingComponent.class);
		assertNotNull("Binding not initialized", component);
	}
}
