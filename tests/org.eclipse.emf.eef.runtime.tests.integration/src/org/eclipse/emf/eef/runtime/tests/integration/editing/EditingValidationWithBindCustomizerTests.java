package org.eclipse.emf.eef.runtime.tests.integration.editing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectEditor;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.tests.integration.AbstractIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleException;

import com.google.common.collect.Lists;

public class EditingValidationWithBindCustomizerTests extends AbstractIntegrationTest {

	/**
	 * Constants.
	 */
	protected static final String NEW_VALUE = "newValue";

	/**
	 * Ecore instances
	 */
	private EClass eClassTest;

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
		initEEFBindingSettings("platform:/plugin/org.eclipse.emf.eef.runtime.tests.integration/ressources/bindingCustomizer/TestBindingCustomizer.editingmodel");
	}

	/**
	 * init Ecore model example
	 */
	protected void initEcoreModel() {
		eClassTest = EcoreFactory.eINSTANCE.createEClass();
	}

	/**
	 * Set eclass name with binding customizer
	 */
	@Test
	public void testSetUnsetString() {
		assertNull(eClassTest.getName());

		// init component
		PropertiesEditingComponent editingComponent = initPropertiesEditingComponent(eClassTest);

		// fire event
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, getNameBindingCustomizerEditor(editingComponent.getBinding()), PropertiesEditingEvent.SET, null, NEW_VALUE));
		assertEquals(NEW_VALUE, eClassTest.getName());

		// undo
		getEditingDomain().getCommandStack().undo();
		assertNull(eClassTest.getName());

		// redo
		getEditingDomain().getCommandStack().redo();
		assertEquals(NEW_VALUE, eClassTest.getName());

		// fire event
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, getNameBindingCustomizerEditor(editingComponent.getBinding()), PropertiesEditingEvent.UNSET, null, null));
		assertEquals("", eClassTest.getName());

		// undo
		getEditingDomain().getCommandStack().undo();
		assertEquals(NEW_VALUE, eClassTest.getName());

		// redo
		getEditingDomain().getCommandStack().redo();
		assertEquals("", eClassTest.getName());

	}

	/**
	 * Add eclass eattributes with binding customizer
	 */
	@Test
	public void testAdd() {
		assertTrue(eClassTest.getEAttributes().isEmpty());
		EAttribute eAttribute1 = EcoreFactory.eINSTANCE.createEAttribute();
		EAttribute eAttribute2 = EcoreFactory.eINSTANCE.createEAttribute();

		// init component
		PropertiesEditingComponent editingComponent = initPropertiesEditingComponent(eClassTest);

		// fire event
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, getEAttributeBindingCustomizerEditor(editingComponent.getBinding()), PropertiesEditingEvent.ADD, null, eAttribute1));
		assertEquals(1, eClassTest.getEAttributes().size());
		assertEquals(eAttribute1, eClassTest.getEAttributes().get(0));

		// undo
		getEditingDomain().getCommandStack().undo();
		assertTrue(eClassTest.getEAttributes().isEmpty());

		// redo
		getEditingDomain().getCommandStack().redo();
		assertEquals(1, eClassTest.getEAttributes().size());
		assertEquals(eAttribute1, eClassTest.getEAttributes().get(0));

		// fire event
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, getEAttributeBindingCustomizerEditor(editingComponent.getBinding()), PropertiesEditingEvent.ADD, null, eAttribute2));
		assertEquals(2, eClassTest.getEAttributes().size());
		assertEquals(eAttribute1, eClassTest.getEAttributes().get(0));
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(1));

		// undo
		getEditingDomain().getCommandStack().undo();
		assertEquals(1, eClassTest.getEAttributes().size());
		assertEquals(eAttribute1, eClassTest.getEAttributes().get(0));

		// redo
		getEditingDomain().getCommandStack().redo();
		assertEquals(2, eClassTest.getEAttributes().size());
		assertEquals(eAttribute1, eClassTest.getEAttributes().get(0));
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(1));

	}

	/**
	 * Add eclass eattributes with binding customizer
	 */
	@Test
	public void testAddAll() {
		assertTrue(eClassTest.getEAttributes().isEmpty());
		EAttribute eAttribute1 = EcoreFactory.eINSTANCE.createEAttribute();
		EAttribute eAttribute2 = EcoreFactory.eINSTANCE.createEAttribute();
		EAttribute eAttribute3 = EcoreFactory.eINSTANCE.createEAttribute();
		EAttribute eAttribute4 = EcoreFactory.eINSTANCE.createEAttribute();

		// init component
		PropertiesEditingComponent editingComponent = initPropertiesEditingComponent(eClassTest);

		// fire event
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, getEAttributeBindingCustomizerEditor(editingComponent.getBinding()), PropertiesEditingEvent.ADD_MANY, null, Lists.newArrayList(eAttribute1, eAttribute2)));
		assertEquals(2, eClassTest.getEAttributes().size());
		assertEquals(eAttribute1, eClassTest.getEAttributes().get(0));
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(1));

		// undo
		getEditingDomain().getCommandStack().undo();
		assertTrue(eClassTest.getEAttributes().isEmpty());

		// redo
		getEditingDomain().getCommandStack().redo();
		assertEquals(2, eClassTest.getEAttributes().size());
		assertEquals(eAttribute1, eClassTest.getEAttributes().get(0));
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(1));

		// fire event
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, getEAttributeBindingCustomizerEditor(editingComponent.getBinding()), PropertiesEditingEvent.ADD_MANY, null, Lists.newArrayList(eAttribute3, eAttribute4)));
		assertEquals(4, eClassTest.getEAttributes().size());
		assertEquals(eAttribute1, eClassTest.getEAttributes().get(0));
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(1));
		assertEquals(eAttribute3, eClassTest.getEAttributes().get(2));
		assertEquals(eAttribute4, eClassTest.getEAttributes().get(3));

		// undo
		getEditingDomain().getCommandStack().undo();
		assertEquals(2, eClassTest.getEAttributes().size());
		assertEquals(eAttribute1, eClassTest.getEAttributes().get(0));
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(1));

		// redo
		getEditingDomain().getCommandStack().redo();
		assertEquals(4, eClassTest.getEAttributes().size());
		assertEquals(eAttribute1, eClassTest.getEAttributes().get(0));
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(1));
		assertEquals(eAttribute3, eClassTest.getEAttributes().get(2));
		assertEquals(eAttribute4, eClassTest.getEAttributes().get(3));

	}

	/**
	 * Remove eclass eattributes with binding customizer
	 */
	@Test
	public void testRemove() {
		assertTrue(eClassTest.getEAttributes().isEmpty());
		EAttribute eAttribute1 = EcoreFactory.eINSTANCE.createEAttribute();
		EAttribute eAttribute2 = EcoreFactory.eINSTANCE.createEAttribute();
		eClassTest.getEStructuralFeatures().add(eAttribute1);
		eClassTest.getEStructuralFeatures().add(eAttribute2);

		assertEquals(2, eClassTest.getEAttributes().size());
		assertEquals(eAttribute1, eClassTest.getEAttributes().get(0));
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(1));

		// init component
		PropertiesEditingComponent editingComponent = initPropertiesEditingComponent(eClassTest);

		// fire event
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, getEAttributeBindingCustomizerEditor(editingComponent.getBinding()), PropertiesEditingEvent.REMOVE, eAttribute1, null));
		assertEquals(1, eClassTest.getEAttributes().size());
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(0));

		// undo
		getEditingDomain().getCommandStack().undo();
		assertEquals(2, eClassTest.getEAttributes().size());
		assertEquals(eAttribute1, eClassTest.getEAttributes().get(0));
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(1));

		// redo
		getEditingDomain().getCommandStack().redo();
		assertEquals(1, eClassTest.getEAttributes().size());
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(0));

		// fire event
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, getEAttributeBindingCustomizerEditor(editingComponent.getBinding()), PropertiesEditingEvent.REMOVE, eAttribute2, null));
		assertEquals(0, eClassTest.getEAttributes().size());

		// undo
		getEditingDomain().getCommandStack().undo();
		assertEquals(1, eClassTest.getEAttributes().size());
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(0));

		// redo
		getEditingDomain().getCommandStack().redo();
		assertEquals(0, eClassTest.getEAttributes().size());

	}

	/**
	 * Remove eclass eattributes with binding customizer
	 */
	@Test
	public void testRemoveAll() {
		assertTrue(eClassTest.getEAttributes().isEmpty());
		EAttribute eAttribute1 = EcoreFactory.eINSTANCE.createEAttribute();
		EAttribute eAttribute2 = EcoreFactory.eINSTANCE.createEAttribute();
		EAttribute eAttribute3 = EcoreFactory.eINSTANCE.createEAttribute();
		EAttribute eAttribute4 = EcoreFactory.eINSTANCE.createEAttribute();
		eClassTest.getEStructuralFeatures().add(eAttribute1);
		eClassTest.getEStructuralFeatures().add(eAttribute2);
		eClassTest.getEStructuralFeatures().add(eAttribute3);
		eClassTest.getEStructuralFeatures().add(eAttribute4);

		assertEquals(4, eClassTest.getEAttributes().size());
		assertEquals(eAttribute1, eClassTest.getEAttributes().get(0));
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(1));
		assertEquals(eAttribute3, eClassTest.getEAttributes().get(2));
		assertEquals(eAttribute4, eClassTest.getEAttributes().get(3));

		// init component
		PropertiesEditingComponent editingComponent = initPropertiesEditingComponent(eClassTest);

		// fire event
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, getEAttributeBindingCustomizerEditor(editingComponent.getBinding()), PropertiesEditingEvent.REMOVE_MANY, Lists.newArrayList(eAttribute1, eAttribute3), null));
		assertEquals(2, eClassTest.getEAttributes().size());
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(0));
		assertEquals(eAttribute4, eClassTest.getEAttributes().get(1));

		// undo
		getEditingDomain().getCommandStack().undo();
		assertEquals(4, eClassTest.getEAttributes().size());
		assertEquals(eAttribute1, eClassTest.getEAttributes().get(0));
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(1));
		assertEquals(eAttribute3, eClassTest.getEAttributes().get(2));
		assertEquals(eAttribute4, eClassTest.getEAttributes().get(3));

		// redo
		getEditingDomain().getCommandStack().redo();
		assertEquals(2, eClassTest.getEAttributes().size());
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(0));
		assertEquals(eAttribute4, eClassTest.getEAttributes().get(1));

		// fire event
		getPropertiesBindingHandler().firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(this, getEAttributeBindingCustomizerEditor(editingComponent.getBinding()), PropertiesEditingEvent.REMOVE_MANY, Lists.newArrayList(eAttribute2, eAttribute4), null));
		assertEquals(0, eClassTest.getEAttributes().size());

		// undo
		getEditingDomain().getCommandStack().undo();
		assertEquals(2, eClassTest.getEAttributes().size());
		assertEquals(eAttribute2, eClassTest.getEAttributes().get(0));
		assertEquals(eAttribute4, eClassTest.getEAttributes().get(1));

		// redo
		getEditingDomain().getCommandStack().redo();
		assertEquals(0, eClassTest.getEAttributes().size());

	}

	/**
	 * @param binding
	 *            EClassBinding
	 * @return binding customizer with name feature
	 */
	protected Object getNameBindingCustomizerEditor(EClassBinding binding) {
		return ((EObjectEditor) binding.getPropertyBindings().get(1).getEditor()).getDefinition();
	}

	/**
	 * @param binding
	 *            EClassBinding
	 * @return binding customizer with eattribute feature
	 */
	protected Object getEAttributeBindingCustomizerEditor(EClassBinding binding) {
		return ((EObjectEditor) binding.getPropertyBindings().get(2).getEditor()).getDefinition();
	}

}
