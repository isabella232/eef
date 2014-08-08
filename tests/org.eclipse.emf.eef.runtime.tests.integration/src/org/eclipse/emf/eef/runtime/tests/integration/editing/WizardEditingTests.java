package org.eclipse.emf.eef.runtime.tests.integration.editing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectEditor;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.tests.integration.AbstractWizardIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleException;

public class WizardEditingTests extends AbstractWizardIntegrationTest {

	/**
	 * Constants.
	 */
	protected static final String NEW_VALUE = "newValue";

	/**
	 * Ecore instances
	 */
	private EClass eClassTest;
	private EPackage ePackageTest;
	private EAttribute eAttributeTest;

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.tests.integration.AbstractIntegrationTest#setUp()
	 */
	@Override
	@Before
	public void setUp() throws BundleException {
		super.setUp();

		// init Ecore model example
		initEcoreModel();

		// init EEF Binding settings
		initEEFBindingSettings("platform:/plugin/org.eclipse.emf.eef.runtime.tests.integration/ressources/wizard/TestWizard.editingmodel");
		// init editing policy processor
		initEditingPolicyProcessor();
	}

	/**
	 * init Ecore model example
	 */
	protected void initEcoreModel() {
		ePackageTest = EcoreFactory.eINSTANCE.createEPackage();
		eClassTest = EcoreFactory.eINSTANCE.createEClass();
		ePackageTest.getEClassifiers().add(eClassTest);
		eAttributeTest = EcoreFactory.eINSTANCE.createEAttribute();
		eClassTest.getEStructuralFeatures().add(eAttributeTest);
	}

	/**
	 * On EPackage, open wizard on 0..* classifiers reference, set name and
	 * cancel, set name and finish, unset name and cancel, unset name and finish
	 * 
	 */
	@Test
	public void testSetClassNameUnsetClassNameWizard() {
		assertNull(eClassTest.getName());

		// init component
		PropertiesEditingComponent editingComponent = initPropertiesEditingComponent(ePackageTest);

		// fire event : open class wizard and update it : set name and cancel
		setWizardProperties(new PropertiesEditingEventImpl(this, getClassNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE), CANCEL);
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, getClassesPackageBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.EDIT, null, eClassTest));
		assertEquals(null, eClassTest.getName());

		// undo
		assertFalse(getEditingDomain().getCommandStack().canUndo());
		assertNull(eClassTest.getName());

		// redo
		assertFalse(getEditingDomain().getCommandStack().canRedo());
		assertNull(eClassTest.getName());

		// fire event : open class wizard and update it : set name
		setWizardProperties(new PropertiesEditingEventImpl(this, getClassNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE), OK);
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, getClassesPackageBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.EDIT, null, eClassTest));
		assertEquals(NEW_VALUE, eClassTest.getName());

		// undo
		getEditingDomain().getCommandStack().undo();
		assertNull(eClassTest.getName());

		// redo
		getEditingDomain().getCommandStack().redo();
		assertEquals(NEW_VALUE, eClassTest.getName());

		Command mostRecentCommand = getEditingDomain().getCommandStack().getMostRecentCommand();

		// fire event : open class wizard and update it : unset name and cancel
		setWizardProperties(new PropertiesEditingEventImpl(this, getClassNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.UNSET, null, null), CANCEL);
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, getClassesPackageBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.EDIT, null, eClassTest));
		assertEquals(NEW_VALUE, eClassTest.getName());

		// undo/redo not possible on unset command
		assertEquals(mostRecentCommand, getEditingDomain().getCommandStack().getMostRecentCommand());

		// fire event : open class wizard and update it : unset name
		setWizardProperties(new PropertiesEditingEventImpl(this, getClassNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.UNSET, null, null), OK);
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, getClassesPackageBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.EDIT, null, eClassTest));
		assertEquals(null, eClassTest.getName());

		// undo
		getEditingDomain().getCommandStack().undo();
		assertEquals(NEW_VALUE, eClassTest.getName());

		// redo
		getEditingDomain().getCommandStack().redo();
		assertEquals(null, eClassTest.getName());
	}

	/**
	 * On EPackage, open wizard on 0..* classifiers reference, On EClass, open
	 * wizard on 0..* eSF, set name and cancel, set name and finish, unset name
	 * and cancel, unset name and finish
	 * 
	 */
	@Test
	public void testSetFeatureNameUnsetFeatureNameWizard() {
		assertNull(eAttributeTest.getName());

		// init component
		PropertiesEditingComponent editingComponent = initPropertiesEditingComponent(ePackageTest);

		// fire event : open class wizard
		// setWizardProperties(new PropertiesEditingEventImpl(this,
		// getNameBindingCustomizerEditor(editingComponent.getBinding()),
		// PropertiesEditingEvent.SET, null, NEW_VALUE), CANCEL);
		setWizardProperties(null, OK);
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, getClassesPackageBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.EDIT, null, eClassTest));

		// fire event : open ESF wizard and update it : set name and cancel
		setWizardProperties(new PropertiesEditingEventImpl(this, getESFNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE), OK);
		getPropertiesBindingHandler().firePropertiesChanged(getPropertiesEditingContext().getEditingComponent(), new PropertiesEditingEventImpl(this, getEStructuralFeaturesClasseBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.EDIT, null, eAttributeTest));
		assertEquals(NEW_VALUE, eAttributeTest.getName());

		// undo
		getEditingDomain().getCommandStack().undo();
		assertNull(eAttributeTest.getName());

		// redo
		getEditingDomain().getCommandStack().redo();
		assertEquals(NEW_VALUE, eAttributeTest.getName());

		// // undo
		// assertFalse(getEditingDomain().getCommandStack().canUndo());
		// assertNull(eClassTest.getName());
		//
		// // redo
		// assertFalse(getEditingDomain().getCommandStack().canRedo());
		// assertNull(eClassTest.getName());
		//
		// // fire event : open class wizard and update it : set name
		// setWizardProperties(new PropertiesEditingEventImpl(this,
		// getClassNameBindingEditor(editingComponent.getBinding()),
		// PropertiesEditingEvent.SET, null, NEW_VALUE), OK);
		// getPropertiesBindingHandler().firePropertiesChanged(editingComponent,
		// new PropertiesEditingEventImpl(this,
		// getClassesPackageBindingEditor(editingComponent.getBinding()),
		// PropertiesEditingEvent.EDIT, null, eClassTest));
		// assertEquals(NEW_VALUE, eClassTest.getName());
		//
		// // undo
		// getEditingDomain().getCommandStack().undo();
		// assertNull(eClassTest.getName());
		//
		// // redo
		// getEditingDomain().getCommandStack().redo();
		// assertEquals(NEW_VALUE, eClassTest.getName());
		//
		// Command mostRecentCommand =
		// getEditingDomain().getCommandStack().getMostRecentCommand();
		//
		// // fire event : open class wizard and update it : unset name and
		// cancel
		// setWizardProperties(new PropertiesEditingEventImpl(this,
		// getClassNameBindingEditor(editingComponent.getBinding()),
		// PropertiesEditingEvent.UNSET, null, null), CANCEL);
		// getPropertiesBindingHandler().firePropertiesChanged(editingComponent,
		// new PropertiesEditingEventImpl(this,
		// getClassesPackageBindingEditor(editingComponent.getBinding()),
		// PropertiesEditingEvent.EDIT, null, eClassTest));
		// assertEquals(NEW_VALUE, eClassTest.getName());
		//
		// // undo/redo not possible on unset command
		// assertEquals(mostRecentCommand,
		// getEditingDomain().getCommandStack().getMostRecentCommand());
		//
		// // fire event : open class wizard and update it : unset name
		// setWizardProperties(new PropertiesEditingEventImpl(this,
		// getClassNameBindingEditor(editingComponent.getBinding()),
		// PropertiesEditingEvent.UNSET, null, null), OK);
		// getPropertiesBindingHandler().firePropertiesChanged(editingComponent,
		// new PropertiesEditingEventImpl(this,
		// getClassesPackageBindingEditor(editingComponent.getBinding()),
		// PropertiesEditingEvent.EDIT, null, eClassTest));
		// assertEquals(null, eClassTest.getName());
		//
		// // undo
		// getEditingDomain().getCommandStack().undo();
		// assertEquals(NEW_VALUE, eClassTest.getName());
		//
		// // redo
		// getEditingDomain().getCommandStack().redo();
		// assertEquals(null, eClassTest.getName());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.tests.integration.AbstractWizardIntegrationTest#updateOnWizard(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	@Override
	protected boolean updateOnWizard(PropertiesEditingContext context) {
		if (getPropertyEvent() != null) {
			getPropertiesBindingHandler().firePropertiesChanged(context.getEditingComponent(), getPropertyEvent());
		}
		return isFinished();
	}

	/**
	 * @param binding
	 *            EClassBinding
	 * @return binding customizer with name feature
	 */
	protected Object getClassesPackageBindingEditor(EClassBinding binding) {
		return ((EObjectEditor) binding.getPropertyBindings().get(4).getEditor()).getDefinition();
	}

	/**
	 * @param binding
	 *            EClassBinding
	 * @return binding customizer with name feature
	 */
	protected Object getEStructuralFeaturesClasseBindingEditor(EClassBinding binding) {
		binding = ((PropertiesEditingModel) binding.eContainer()).binding(eClassTest);
		return ((EObjectEditor) binding.getPropertyBindings().get(7).getEditor()).getDefinition();
	}

	/**
	 * @param binding
	 *            EClassBinding
	 * @return binding customizer with eattribute feature
	 */
	protected Object getEAttributeBindingEditor(EClassBinding binding) {
		return ((EObjectEditor) binding.getPropertyBindings().get(2).getEditor()).getDefinition();
	}

	/**
	 * @param binding
	 *            EClassBinding
	 * @return binding customizer with name feature
	 */
	protected Object getClassNameBindingEditor(EClassBinding binding) {
		binding = ((PropertiesEditingModel) binding.eContainer()).binding(eClassTest);
		return ((EObjectEditor) binding.getPropertyBindings().get(0).getEditor()).getDefinition();
	}

	/**
	 * @param binding
	 *            EClassBinding
	 * @return binding customizer with name feature
	 */
	protected Object getESFNameBindingEditor(EClassBinding binding) {
		binding = ((PropertiesEditingModel) binding.eContainer()).binding(eAttributeTest);
		return ((EObjectEditor) binding.getPropertyBindings().get(0).getEditor()).getDefinition();
	}

}
