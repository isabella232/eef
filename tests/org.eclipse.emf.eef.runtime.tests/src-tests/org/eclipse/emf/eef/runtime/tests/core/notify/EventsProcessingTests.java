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
package org.eclipse.emf.eef.runtime.tests.core.notify;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.notify.UIPropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder;
import org.eclipse.emf.eef.runtime.tests.views.EClassMockView;
import org.eclipse.emf.eef.runtime.tests.views.EClassMockView2;
import org.junit.Test;

/**
 * This class tests the EEF components bevahior for the events received from the view.
 * It ensure that commands are performed only if needed.
 * 
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class EventsProcessingTests extends NonUIEditingTestCase {

	private static final String NEW_CLASS_NAME = "NewClassName";
	private static final String DEFAULT_ECLASS_NAME = "SampleName";
	private BasicCommandStack commandStack;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase#initEnvironmentBuilder()
	 */
	@Override
	protected Builder initEnvironmentBuilder() {
		Builder builder = super.initEnvironmentBuilder();
		return builder.setEditingModel(new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID)
																		.bindClass(EcorePackage.Literals.ECLASS)
																		.withView(EClassMockView.class)
																		.withView(EClassMockView2.class)
																		.build())
											.setEditingContext(createEditingContext(builder));
	}

	private PropertiesEditingContext createEditingContext(Builder builder) {
		commandStack = new BasicCommandStack();
		return builder.getContextFactoryProvider().getEditingContextFactory(builder.getEditedObject()).createPropertiesEditingContext(new AdapterFactoryEditingDomain(builder.getAdapterFactory(), commandStack), builder.getEditedObject());
	}

	/**
	 * This test checks that if a event ask for the edited object update, the new value must be different from the old one. 
	 * Otherwise the EEF shouldn't execute commands.
	 */
	@Test
	public void testIdentityEventsNonProcessing() {
		commandStack.saveIsDone();
		((EClass)editedObject).setName(DEFAULT_ECLASS_NAME);
		EClassMockView view1 = (EClassMockView) views.get(0);
		view1.getSupport().firePropertyChange(new PropertyChangeEvent(view1, "name", null, DEFAULT_ECLASS_NAME));
		assertFalse("Command performed but not needed!", commandStack.isSaveNeeded());
		view1.getSupport().firePropertyChange(new PropertyChangeEvent(view1, "name", null, NEW_CLASS_NAME));
		assertTrue("Command not performed but needed!", commandStack.isSaveNeeded());
		commandStack.saveIsDone();
		view1.getSupport().firePropertyChange(new PropertyChangeEvent(view1, "name", null, NEW_CLASS_NAME));
		assertFalse("Command performed but not needed!", commandStack.isSaveNeeded());

	}

	/**
	 * This test checks that {@link UIPropertiesEditingEvent} never causes command executing form the EEF controller.
	 */
	@Test
	public void testUIEventsNonProcessing() {
		commandStack.saveIsDone();
		PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
		environmentBuilder.getBindingHandlerProvider().getBindingManager(editingComponent.getEObject()).firePropertiesChanged(editingComponent, new MyEvent());
		assertFalse("Command not performed but needed!", commandStack.isSaveNeeded());
	}

	private static final class MyEvent extends PropertiesEditingEventImpl implements UIPropertiesEditingEvent {

		public MyEvent() {
			super(null, null, 0, null, null, false);
		}
		
	}
	
}
