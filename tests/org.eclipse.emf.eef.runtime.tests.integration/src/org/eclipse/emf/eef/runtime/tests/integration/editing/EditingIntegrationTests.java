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
package org.eclipse.emf.eef.runtime.tests.integration.editing;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.tests.integration.AbstractIntegrationTest;
import org.junit.Test;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingIntegrationTests extends AbstractIntegrationTest {

	private static final String ECLASS_NEWNAME = "Test";

	@Test
	public void testDirectEditInLiveMode() {
		EClass demo = EcoreFactory.eINSTANCE.createEClass();
		EditingContextFactoryProvider editingContextFactoryProvider = getEditingContextFactoryProvider();
		PropertiesEditingContext editingContext = editingContextFactoryProvider.getEditingContextFactory(demo).createPropertiesEditingContext(getAdapterFactory(), demo);
		PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
		EAttribute feature = EcorePackage.Literals.ENAMED_ELEMENT__NAME;
		EEFBindingSettings<EObject> bindingSettings = editingContext.getEditingComponent().getBindingSettings();
		System.err.println("Binding Settings : " + bindingSettings);
		PropertiesEditingModel eefDescription = (PropertiesEditingModel) bindingSettings.getEEFDescription(demo.eClass());
		Object propertyEditor = eefDescription.binding(demo).propertyEditor(demo, feature, editingContext.getOptions().autowire());
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, propertyEditor, PropertiesEditingEvent.SET, null, ECLASS_NEWNAME));
		assertEquals("The EObject hasn't been correctly updated.", ECLASS_NEWNAME, demo.getName());
	}

	@Test
	public void testDirectEditInBatchMode() {
		EClass demo = EcoreFactory.eINSTANCE.createEClass();
		EditingContextFactoryProvider editingContextFactoryProvider = getEditingContextFactoryProvider();
		PropertiesEditingContext editingContext = editingContextFactoryProvider.getEditingContextFactory(demo).createPropertiesEditingContext(getAdapterFactory(), demo);
		editingContext.getOptions().setBatchMode(true);
		PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
		EAttribute feature = EcorePackage.Literals.ENAMED_ELEMENT__NAME;
		PropertiesEditingModel eefDescription = (PropertiesEditingModel) editingContext.getEditingComponent().getBindingSettings().getEEFDescription(demo.eClass());
		Object propertyEditor = eefDescription.binding(demo).propertyEditor(demo, feature, editingContext.getOptions().autowire());
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, propertyEditor, PropertiesEditingEvent.SET, null, ECLASS_NEWNAME));
		assertEquals("The EObject hasn't been correctly updated.", ECLASS_NEWNAME, demo.getName());
	}

	@Test
	public void testDomainEditInLiveMode() {
		EClass demo = EcoreFactory.eINSTANCE.createEClass();
		EditingContextFactoryProvider editingContextFactoryProvider = getEditingContextFactoryProvider();
		PropertiesEditingContext editingContext = editingContextFactoryProvider.getEditingContextFactory(demo).createPropertiesEditingContext(getEditingDomain(), getAdapterFactory(), demo);
		PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
		EAttribute feature = EcorePackage.Literals.ENAMED_ELEMENT__NAME;
		PropertiesEditingModel eefDescription = (PropertiesEditingModel) editingContext.getEditingComponent().getBindingSettings().getEEFDescription(demo.eClass());
		Object propertyEditor = eefDescription.binding(demo).propertyEditor(demo, feature, editingContext.getOptions().autowire());
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, propertyEditor, PropertiesEditingEvent.SET, null, ECLASS_NEWNAME));
		assertEquals("The EObject hasn't been correctly updated.", ECLASS_NEWNAME, demo.getName());
	}

	@Test
	public void testDomainEditInBatchMode() {
		EClass demo = EcoreFactory.eINSTANCE.createEClass();
		EditingContextFactoryProvider editingContextFactoryProvider = getEditingContextFactoryProvider();
		PropertiesEditingContext editingContext = editingContextFactoryProvider.getEditingContextFactory(demo).createPropertiesEditingContext(getEditingDomain(), getAdapterFactory(), demo);
		editingContext.getOptions().setBatchMode(true);
		PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
		EAttribute feature = EcorePackage.Literals.ENAMED_ELEMENT__NAME;
		PropertiesEditingModel eefDescription = (PropertiesEditingModel) editingContext.getEditingComponent().getBindingSettings().getEEFDescription(demo.eClass());
		Object propertyEditor = eefDescription.binding(demo).propertyEditor(demo, feature, editingContext.getOptions().autowire());
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, propertyEditor, PropertiesEditingEvent.SET, null, ECLASS_NEWNAME));
		assertEquals("The EObject hasn't been correctly updated.", ECLASS_NEWNAME, demo.getName());
	}

}
