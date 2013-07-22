/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.tests.core.binding;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder;
import org.eclipse.emf.eef.runtime.tests.views.SampleTitleView;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertyBindingTests extends UIEditingTestCase {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase#initEnvironmentBuilder()
	 */
	@Override
	protected Builder initEnvironmentBuilder() {
		return super.initEnvironmentBuilder()
						.setEditingModel(new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID).bindClass(EcorePackage.Literals.ECLASS)
								.withView(SampleTitleView.class)
								.bindProperty(EcorePackage.Literals.ENAMED_ELEMENT__NAME)
									.withEditor("title")
							.build());
	}

	@Test
	public void testPropertyBindingEditing() {
		EClass sample = (EClass) editedObject;
		sample.setName("New name");
		SampleTitleView sampleView = (SampleTitleView)views.get(0);
		assertEquals("Bad view refresh", sample.getName(), sampleView.getTitle());

		sampleView.notifiedSetTitle("Other Name");
		assertEquals("Bad model refresh", sample.getName(), sampleView.getTitle());
	}

}
