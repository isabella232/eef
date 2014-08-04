package org.eclipse.emf.eef.runtime.tests.integration.suite;

import org.eclipse.emf.eef.runtime.tests.integration.editing.EditingIntegrationTests;
import org.eclipse.emf.eef.runtime.tests.integration.editing.EditingValidationWithBindCustomizerTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EditingIntegrationTests.class, EditingValidationWithBindCustomizerTests.class })
public class AllTests {

}
