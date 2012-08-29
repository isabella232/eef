/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.suite;

import org.eclipse.emf.eef.runtime.tests.core.binding.ViewsComponentsLinkingTests;
import org.eclipse.emf.eef.runtime.tests.core.compatibility.EMFEditCompatibilityTests;
import org.eclipse.emf.eef.runtime.tests.core.editingmodes.EditingModesTests;
import org.eclipse.emf.eef.runtime.tests.core.notify.EditingListenerTests;
import org.eclipse.emf.eef.runtime.tests.core.notify.ViewNotificationTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
//	CustomViewHandlerTests.class,
//	SampleEditingTests.class,
//	EClassBindingResolverTests.class,
//	EditingModelBuilderTests.class,
//	PlatformResourceRegistryResourceBinding.class,
//	PropertyBindingTests.class,
//	SampleMultiViewsEditingTests.class,
	ViewsComponentsLinkingTests.class,
	EditingListenerTests.class,
	ViewNotificationTests.class,
//	EMFServiceProvidingTests.class,
//	EEFViewerTests.class,
//	PropertiesEditingViewTests.class,
//	ModelNotificationInBeanViewTests.class,
//	ModelNotificationInPropertiesEditingViewTests.class,
	EditingModesTests.class,
	EMFEditCompatibilityTests.class,
//	FeatureDocumentationTests.class
})
public class AllTests {

}
