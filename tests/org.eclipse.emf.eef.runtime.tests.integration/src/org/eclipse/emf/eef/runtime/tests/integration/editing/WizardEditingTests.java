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
import org.eclipse.emf.eef.runtime.tests.integration.AbstractWizardIntegrationTest;
import org.eclipse.emf.eef.runtime.tests.integration.EmbedingEditingEvent;
import org.eclipse.emf.eef.runtime.tests.integration.EmbedingEditingEvent.Builder;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleException;

public class WizardEditingTests extends AbstractWizardIntegrationTest {

	/**
	 * Constants.
	 */
	protected static final String NEW_VALUE = "newValue";

	public WizardEditingTests() {
		super();
	}

	/**
	 * Ecore instances
	 */
	private EClass eClassTest;
	private EPackage ePackageTest;
	private EAttribute eAttributeTest;

	private EClass createdEClass;
	private EAttribute createdEAttribute;

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
		initEEFBindingSettings("platform:/plugin/org.eclipse.emf.eef.runtime.tests.integration/resources/wizard/TestWizard.editingmodel");
		// init editing policy processor
		initEditingPolicyProcessors();
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

		// init processor
		Processor processor = new Processor(editingComponent);

		// fire event : open class wizard and update it : set name and cancel
		Builder builder = new EmbedingEditingEvent.Builder(getClassesPackageBindingEditor(editingComponent.getBinding()), eClassTest) {
			@Override
			public void test(PropertiesEditingContext editingContext) {
				assertEquals(null, eClassTest.getName());
			}
		};
		processor.execute(builder.addEvent(this, getClassNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE).cancel());

		// undo
		assertFalse(processor.canUndo());
		assertNull(eClassTest.getName());

		// redo
		assertFalse(processor.canRedo());
		assertNull(eClassTest.getName());

		// fire event : open class wizard and update it : set name and finish
		builder = new EmbedingEditingEvent.Builder(getClassesPackageBindingEditor(editingComponent.getBinding()), eClassTest) {
			@Override
			public void test(PropertiesEditingContext editingContext) {
				assertEquals(NEW_VALUE, eClassTest.getName());
			}
		};
		processor.execute(builder.addEvent(this, getClassNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE).ok());

		// undo
		processor.undo();
		assertNull(eClassTest.getName());

		// redo
		processor.redo();
		assertEquals(NEW_VALUE, eClassTest.getName());

		Command mostRecentCommand = getEditingDomain().getCommandStack().getMostRecentCommand();

		// fire event : open class wizard and update it : unset name and cancel
		builder = new EmbedingEditingEvent.Builder(getClassesPackageBindingEditor(editingComponent.getBinding()), eClassTest) {
			@Override
			public void test(PropertiesEditingContext editingContext) {
				assertEquals(NEW_VALUE, eClassTest.getName());
			}
		};
		processor.execute(builder.addEvent(this, getClassNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.UNSET, null, null).cancel());

		// undo/redo not possible on unset command
		assertEquals(mostRecentCommand, getEditingDomain().getCommandStack().getMostRecentCommand());

		// fire event : open class wizard and update it : unset name
		builder = new EmbedingEditingEvent.Builder(getClassesPackageBindingEditor(editingComponent.getBinding()), eClassTest) {
			@Override
			public void test(PropertiesEditingContext editingContext) {
				assertEquals(null, eClassTest.getName());
			}
		};
		processor.execute(builder.addEvent(this, getClassNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.UNSET, null, null).ok());

		// undo
		processor.undo();
		assertEquals(NEW_VALUE, eClassTest.getName());

		// redo
		processor.redo();
		assertEquals(null, eClassTest.getName());
	}

	/**
	 * On EPackage, open wizard on 0..* classifiers reference, set name, On
	 * EClass, open wizard on 0..* eSF, set name and finish, finish
	 * 
	 */
	@Test
	public void test2WizardsSetFeatureNameOkOk() {
		assertNull(eAttributeTest.getName());

		// init component
		PropertiesEditingComponent editingComponent = initPropertiesEditingComponent(ePackageTest);

		// init processor
		Processor processor = new Processor(editingComponent);

		// fire event : open class wizard and update it : set name and ok
		Builder builder = new EmbedingEditingEvent.Builder(getClassesPackageBindingEditor(editingComponent.getBinding()), eClassTest) {
			@Override
			public void test(PropertiesEditingContext editingContext) {
				assertEquals(NEW_VALUE, eClassTest.getName());
			}
		};
		builder.addEvent(this, getClassNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE);

		// fire event : open attribute wizard and update it : set name and ok
		EmbedingEditingEvent editingEvent = builder.addEvent(new Builder(getEStructuralFeaturesClasseBindingEditor(editingComponent.getBinding()), eAttributeTest) {

			@Override
			public void test(PropertiesEditingContext editingContext) {
				assertEquals(NEW_VALUE, eAttributeTest.getName());

			}
		}.addEvent(this, getESFNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE).ok()).ok();
		processor.execute(editingEvent);

		// undo
		processor.undo();
		assertNull(eAttributeTest.getName());
		assertNull(eClassTest.getName());

		// redo
		processor.redo();
		assertEquals(NEW_VALUE, eAttributeTest.getName());
		assertEquals(NEW_VALUE, eClassTest.getName());

	}

	/**
	 * On EPackage, open wizard on 0..* classifiers reference, set name, On
	 * EClass, open wizard on 0..* eSF, set name and cancel, finish
	 * 
	 */
	@Test
	public void test2WizardsSetFeatureNameCancelOk() {
		assertNull(eAttributeTest.getName());

		// init component
		PropertiesEditingComponent editingComponent = initPropertiesEditingComponent(ePackageTest);

		// init processor
		Processor processor = new Processor(editingComponent);

		// fire event : open class wizard and update it : set name and ok
		Builder builder = new EmbedingEditingEvent.Builder(getClassesPackageBindingEditor(editingComponent.getBinding()), eClassTest) {
			@Override
			public void test(PropertiesEditingContext editingContext) {
				assertEquals(NEW_VALUE, eClassTest.getName());
			}
		};
		builder.addEvent(this, getClassNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE);
		// fire event : open attribte wizard and update it : set name and cancel
		EmbedingEditingEvent editingEvent = builder.addEvent(new Builder(getEStructuralFeaturesClasseBindingEditor(editingComponent.getBinding()), eAttributeTest) {

			@Override
			public void test(PropertiesEditingContext editingContext) {
				assertEquals(null, eAttributeTest.getName());

			}
		}.addEvent(this, getESFNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE).cancel()).ok();
		processor.execute(editingEvent);

		// undo
		processor.undo();
		assertNull(eAttributeTest.getName());
		assertNull(eClassTest.getName());

		// redo
		processor.redo();
		assertEquals(null, eAttributeTest.getName());
		assertEquals(NEW_VALUE, eClassTest.getName());

	}

	/**
	 * On EPackage, open wizard on 0..* classifiers reference, set name, On
	 * EClass, open wizard on 0..* eSF, set name and finish, cancel
	 * 
	 */
	@Test
	public void test2WizardsSetFeatureNameOkCancel() {
		assertNull(eAttributeTest.getName());

		// init component
		PropertiesEditingComponent editingComponent = initPropertiesEditingComponent(ePackageTest);

		// init processor
		Processor processor = new Processor(editingComponent);

		// fire event : open class wizard and update it : set name and cancel
		Builder builder = new EmbedingEditingEvent.Builder(getClassesPackageBindingEditor(editingComponent.getBinding()), eClassTest) {
			@Override
			public void test(PropertiesEditingContext editingContext) {
				assertEquals(null, eClassTest.getName());
			}
		};
		builder.addEvent(this, getClassNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE);

		// fire event : open attribute wizard and update it : set name and ok
		EmbedingEditingEvent editingEvent = builder.addEvent(new Builder(getEStructuralFeaturesClasseBindingEditor(editingComponent.getBinding()), eAttributeTest) {

			@Override
			public void test(PropertiesEditingContext editingContext) {
				assertEquals(NEW_VALUE, eAttributeTest.getName());

			}
		}.addEvent(this, getESFNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE).ok()).cancel();
		processor.execute(editingEvent);

		// undo
		assertFalse(processor.canUndo());
		assertNull(eAttributeTest.getName());
		assertNull(eClassTest.getName());

		// redo
		assertFalse(processor.canRedo());
		assertNull(eAttributeTest.getName());
		assertNull(eClassTest.getName());

	}

	/**
	 * On EPackage, open wizard on 0..* classifiers reference, set name, On
	 * EClass, open wizard on 0..* eSF, set name and cancel, cancel
	 * 
	 */
	@Test
	public void test2WizardsSetFeatureNameCancelCancel() {
		assertNull(eAttributeTest.getName());

		// init component
		PropertiesEditingComponent editingComponent = initPropertiesEditingComponent(ePackageTest);

		// init processor
		Processor processor = new Processor(editingComponent);

		// fire event : open class wizard and update it : set name and cancel
		Builder builder = new EmbedingEditingEvent.Builder(getClassesPackageBindingEditor(editingComponent.getBinding()), eClassTest) {
			@Override
			public void test(PropertiesEditingContext editingContext) {
				assertEquals(null, eClassTest.getName());
			}
		};
		builder.addEvent(this, getClassNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE);

		// fire event : open attribute wizard and update it : set name and ok
		EmbedingEditingEvent editingEvent = builder.addEvent(new Builder(getEStructuralFeaturesClasseBindingEditor(editingComponent.getBinding()), eAttributeTest) {

			@Override
			public void test(PropertiesEditingContext editingContext) {
				assertEquals(null, eAttributeTest.getName());

			}
		}.addEvent(this, getESFNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE).cancel()).cancel();
		processor.execute(editingEvent);

		// undo
		assertFalse(processor.canUndo());
		assertNull(eAttributeTest.getName());
		assertNull(eClassTest.getName());

		// redo
		assertFalse(processor.canRedo());
		assertNull(eAttributeTest.getName());
		assertNull(eClassTest.getName());

	}

	/**
	 * On EPackage, add wizard on 0..* classifiers reference, set name, On
	 * EClass, add wizard on 0..* eSF, set name and ok, ok
	 * 
	 */
	@Test
	public void test2WizardsCreateFeatureNameOkOk() {
		assertNull(eAttributeTest.getName());

		// init component
		PropertiesEditingComponent editingComponent = initPropertiesEditingComponent(ePackageTest);

		// init processor
		Processor processor = new Processor(editingComponent);

		// fire event : create class wizard and update it : set name and ok
		Builder builder = new EmbedingEditingEvent.Builder(getClassesPackageBindingEditor(editingComponent.getBinding()), null) {

			@Override
			public void test(PropertiesEditingContext editingContext) {
				createdEClass = (EClass) ePackageTest.getEClassifiers().get(1);
				assertEquals(NEW_VALUE, createdEClass.getName());
			}
		};
		builder.addEvent(this, getClassNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE);

		// fire event : create eattribute wizard and update it : set name and ok
		EmbedingEditingEvent editingEvent = builder.addEvent(new Builder(getEStructuralFeaturesClasseBindingEditor(editingComponent.getBinding()), null) {

			@Override
			public void test(PropertiesEditingContext editingContext) {
				createdEAttribute = ((EClass) editingContext.getEditingComponent().getEObject()).getEAttributes().get(0);
				assertEquals(NEW_VALUE, createdEAttribute.getName());

			}
		}.addEvent(this, getESFNameBindingEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE).ok()).ok();
		processor.execute(editingEvent);

		// undo
		processor.undo();
		assertEquals(1, ePackageTest.getEClassifiers().size());

		// redo
		processor.redo();
		assertEquals(2, ePackageTest.getEClassifiers().size());
		assertEquals(1, ((EClass) ePackageTest.getEClassifiers().get(1)).getEAttributes().size());
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
