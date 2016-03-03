/*******************************************************************************
 * Copyright (c) 2015, 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.eef.tests.internal.controllers;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.eclipse.eef.EEFGroupDescription;
import org.eclipse.eef.EEFPageDescription;
import org.eclipse.eef.EEFTextDescription;
import org.eclipse.eef.EEFViewDescription;
import org.eclipse.eef.EEFWidgetDescription;
import org.eclipse.eef.EefPackage;
import org.eclipse.eef.core.api.EEFExpressionUtils;
import org.eclipse.eef.core.api.controllers.IEEFTextController;
import org.eclipse.eef.core.internal.controllers.EEFTextController;
import org.eclipse.eef.tests.internal.AQLInterpreter;
import org.eclipse.eef.tests.internal.EEFDataTests;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.transaction.impl.TransactionalEditingDomainImpl;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;
import org.eclipse.sirius.common.interpreter.api.VariableManagerFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the {@link IEEFTextController}.
 *
 * @author sbegaudeau
 */
@SuppressWarnings({ "checkstyle:javadocmethod" })
public class EEFTextControllerTests {
	/**
	 * The path of the dart.ecore file.
	 */
	private static final String DART_ECORE = "/data/dart.ecore"; //$NON-NLS-1$

	/**
	 * The name of the Project EClass.
	 */
	private static final String PROJECT_ECLASS_NAME = "Project"; //$NON-NLS-1$

	/**
	 * The editing domain.
	 */
	private TransactionalEditingDomainImpl editingDomain;

	/**
	 * The resource set.
	 */
	private ResourceSetImpl resourceSet;

	/**
	 * The interpreter.
	 */
	private IInterpreter interpreter;

	@Before
	public void setUp() {
		this.resourceSet = new ResourceSetImpl();
		this.resourceSet.getPackageRegistry().put(EefPackage.eNS_URI, EefPackage.eINSTANCE);
		this.resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl()); //$NON-NLS-1$
		this.resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl()); //$NON-NLS-1$

		AdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		this.editingDomain = new TransactionalEditingDomainImpl(adapterFactory, this.resourceSet);

		this.interpreter = new AQLInterpreter();
	}

	private EEFViewDescription view(String modelPath) {
		Resource resource = EEFDataTests.loadResource(this.resourceSet, URI.createFileURI(EEFDataTests.CURRENTDIR + modelPath));
		assertThat(resource, notNullValue());
		EObject eObject = resource.getContents().get(0);
		if (eObject instanceof EEFViewDescription) {
			EEFViewDescription eefViewDescription = (EEFViewDescription) eObject;
			return eefViewDescription;
		}
		throw new IllegalStateException("The root of the model is not a view description"); //$NON-NLS-1$
	}

	private EEFPageDescription page(String modelPath, int pageIndex) {
		return this.view(modelPath).getPages().get(pageIndex);
	}

	private EEFGroupDescription group(EEFPageDescription eefPageDescription, int groupIndex) {
		return eefPageDescription.getGroups().get(groupIndex);
	}

	private <T extends EEFWidgetDescription> T widget(EEFGroupDescription eefGroupDescription, Class<T> clazz, int widgetIndex) {
		List<T> list = eefGroupDescription.getContainer().getWidgets().stream().filter(w -> clazz.isAssignableFrom(w.getClass()))
				.map(w -> clazz.cast(w)).collect(Collectors.toList());
		return list.get(widgetIndex);
	}

	private EPackage ePackage(String modelPath, int ePackageIndex) {
		Resource resource = EEFDataTests.loadResource(this.resourceSet, URI.createFileURI(EEFDataTests.CURRENTDIR + modelPath));
		assertThat(resource, notNullValue());
		EObject eObject = resource.getContents().get(0);
		if (eObject instanceof EPackage) {
			return (EPackage) eObject;
		}
		throw new IllegalStateException("The root of the model is not an EPackage"); //$NON-NLS-1$
	}

	private IEEFTextController textController(String modelPath) {
		EClassifier eClassifier = this.ePackage(DART_ECORE, 0).getEClassifier(PROJECT_ECLASS_NAME);

		IVariableManager variableManager = new VariableManagerFactory().createVariableManager();
		variableManager.put(EEFExpressionUtils.SELF, eClassifier);

		EEFTextDescription description = widget(group(page(modelPath, 0), 0), EEFTextDescription.class, 0);
		return new EEFTextController(description, variableManager, this.interpreter, this.editingDomain);
	}

	@Test
	public void testLabel() {
		AtomicBoolean atomicBoolean = new AtomicBoolean(false);
		IEEFTextController controller = this.textController(EEFDataTests.EEFTEXTCONTROLLERTESTS_LABEL);
		controller.onNewLabel(label -> {
			assertThat(label, is("Project:")); //$NON-NLS-1$
			atomicBoolean.set(true);
		});
		controller.refresh();
		assertTrue(atomicBoolean.get());
	}

	@Test
	public void testValue() {
		AtomicBoolean atomicBoolean = new AtomicBoolean(false);
		IEEFTextController controller = this.textController(EEFDataTests.EEFTEXTCONTROLLERTESTS_VALUE);
		controller.onNewValue(value -> {
			assertThat(value, is(PROJECT_ECLASS_NAME));
			atomicBoolean.set(true);
		});
		controller.refresh();
		assertTrue(atomicBoolean.get());
	}

	@Test
	public void testHelp() {
		AtomicBoolean atomicBoolean = new AtomicBoolean(false);
		IEEFTextController controller = this.textController(EEFDataTests.EEFTEXTCONTROLLERTESTS_HELP);
		controller.onNewHelp(help -> {
			assertThat(help, is("Project Help")); //$NON-NLS-1$
			atomicBoolean.set(true);
		});
		controller.refresh();
		assertTrue(atomicBoolean.get());
	}
}
