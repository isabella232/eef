/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.eef.runtime.tests.ui.cases.PropertiesEditingViewEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ContainerTests extends PropertiesEditingViewEditingTestCase {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase#initEnvironmentBuilder()
	 */
	@Override
	protected Builder initEnvironmentBuilder() {
		Builder builder = super.initEnvironmentBuilder();
		return builder.setEditingModel(builder.createEditingModelWithContainersPropertiesEditingViews());
	}

	@Test
	public void testContainerBuilding() {
		Composite view = (Composite) getViews().get(0);
		Object control = getControl(view, 0);
		assertTrue("Bad container building.", control instanceof Group);
		Group group1 = (Group) control;
		assertTrue("Bad container building.", group1.getChildren()[0] instanceof Group);
		Group group2 = (Group) group1.getChildren()[0];
		assertTrue("Bad container building.", group2.getChildren()[1] instanceof Text);		
	}
	
	
	
}
