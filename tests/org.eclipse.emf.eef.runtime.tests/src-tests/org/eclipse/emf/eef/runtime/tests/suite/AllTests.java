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
package org.eclipse.emf.eef.runtime.tests.suite;

import org.eclipse.emf.eef.runtime.tests.core.SampleEditingTests;
import org.eclipse.emf.eef.runtime.tests.core.binding.EClassBindingResolverTests;
import org.eclipse.emf.eef.runtime.tests.core.binding.EditingModelBuilderTests;
import org.eclipse.emf.eef.runtime.tests.core.binding.PlatformResourceRegistryResourceBinding;
import org.eclipse.emf.eef.runtime.tests.core.binding.PropertyBindingTests;
import org.eclipse.emf.eef.runtime.tests.core.binding.SampleMultiViewsEditingTests;
import org.eclipse.emf.eef.runtime.tests.core.compatibility.EMFEditCompatibilityTests;
import org.eclipse.emf.eef.runtime.tests.core.editingmodes.EditingModesTests;
import org.eclipse.emf.eef.runtime.tests.core.notify.EditingListenerTests;
import org.eclipse.emf.eef.runtime.tests.core.notify.EventsProcessingTests;
import org.eclipse.emf.eef.runtime.tests.core.notify.PropertiesChangeDelayingTests;
import org.eclipse.emf.eef.runtime.tests.core.notify.ViewNotificationTests;
import org.eclipse.emf.eef.runtime.tests.generic.binding.settings.GenericBindingSettings2TestCase;
import org.eclipse.emf.eef.runtime.tests.generic.binding.settings.GenericBindingSettingsTestCase;
import org.eclipse.emf.eef.runtime.tests.ui.ContainerTests;
import org.eclipse.emf.eef.runtime.tests.ui.EEFViewerTests;
import org.eclipse.emf.eef.runtime.tests.ui.FeatureDocumentationTests;
import org.eclipse.emf.eef.runtime.tests.ui.PropertiesEditingViewTests;
import org.eclipse.emf.eef.runtime.tests.ui.lock.EEFLockTests;
import org.eclipse.emf.eef.runtime.tests.ui.notificationSystem.EEFNotifierTests;
import org.eclipse.emf.eef.runtime.tests.ui.notify.ModelNotificationInBeanViewTests;
import org.eclipse.emf.eef.runtime.tests.ui.notify.ModelNotificationInPropertiesEditingViewTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	
	// NonUIEditingTests
	EditingListenerTests.class,
	EventsProcessingTests.class,
	EditingModesTests.class,
	EMFEditCompatibilityTests.class,
	ViewNotificationTests.class,
	PropertiesChangeDelayingTests.class,
	
	// UIEditingTests
	ModelNotificationInBeanViewTests.class,
	ModelNotificationInPropertiesEditingViewTests.class,
	PropertyBindingTests.class,
	SampleEditingTests.class,
	SampleMultiViewsEditingTests.class,
	EEFNotifierTests.class,
	EEFLockTests.class,
	
	//PropertiesEditingViewEditingTestCase
	EEFViewerTests.class,
	PropertiesEditingViewTests.class,
	ContainerTests.class,
	
	EditingModelBuilderTests.class,
	EClassBindingResolverTests.class,
	PlatformResourceRegistryResourceBinding.class,
	FeatureDocumentationTests.class,
	
	//Other case
	GenericBindingSettingsTestCase.class,
	GenericBindingSettings2TestCase.class
})
public class AllTests {

}
