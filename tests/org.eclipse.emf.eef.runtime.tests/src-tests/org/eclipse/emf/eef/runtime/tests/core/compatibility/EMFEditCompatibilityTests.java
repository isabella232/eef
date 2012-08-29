/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core.compatibility;

import static org.junit.Assert.assertSame;

import java.util.Map;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.DomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.views.EClassListeningView;
import org.eclipse.emf.eef.runtime.ui.commands.AbstractBatchEditingCommand;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EMFEditCompatibilityTests extends NonUIEditingTestCase {

	private static final String ORIGINAL_ECLASS_NAME = "Test";
	private static final String NEW_ECLASS_NAME = "NewName";
	private static final String MARKER = "Mark";

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase#buildEditingModel()
	 */
	@Override
	protected PropertiesEditingModel buildEditingModel() {
		return new EditingModelBuilder()
						.bindClass(EcorePackage.Literals.ECLASS)
							.withView(EClassListeningView.class).build();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase#createEditedObject()
	 */
	@Override
	protected EObject createEditedObject() {
		EClass editedEClass = EcoreFactory.eINSTANCE.createEClass();
		editedEClass.setName(ORIGINAL_ECLASS_NAME);
		return editedEClass;
	}


	private EClass editedEClass() {
		return (EClass)editedObject;
	}
	
	protected EClassListeningView view() {
		return (EClassListeningView)views.get(0);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase#createEditingContext()
	 */
	@Override
	protected PropertiesEditingContext createEditingContext() {
		return new DomainPropertiesEditingContext(new TestEditingDomain(adapterFactory, new BasicCommandStack()), editedObject);
	}


	/**
	 * This test checks the live mode : The context use the 'custom command' editing domain. When we change the EClass name
	 * the new value must be {@link EMFEditCompatibilityTests#MARKER}. 
	 */
	@Test
	public void testCompatibilityInLiveMode() {
		assertSame("Bad initiale state for this test.", ORIGINAL_ECLASS_NAME, editedEClass().getName());
		view().setName(NEW_ECLASS_NAME);
		assertSame("Bad EMF.edit command override use.",  MARKER, editedEClass().getName());
		editedEClass().setName(ORIGINAL_ECLASS_NAME);
	}
	
	/**
	 * This test checks the batch mode : two changes are made in only one commands and when we undo this command,
	 * the original state is restored.
	 */
	@Test
	public void testBatchEditingMode() {
		assertSame("Bad initiale state for this test.", ORIGINAL_ECLASS_NAME, editedEClass().getName());
		editingContext.getOptions().setBatchMode(true);
		((DomainPropertiesEditingContext)editingContext).getEditingDomain().getCommandStack().execute(new AbstractBatchEditingCommand(editingContext) {
			
			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.commands.AbstractBatchEditingCommand#prepareBatchEditing()
			 */
			@Override
			protected boolean prepareBatchEditing() {
				view().setName(NEW_ECLASS_NAME);
				return true;
			}
		});
		assertSame("Bad EMF.edit command override use.",  MARKER, editedEClass().getName());
		editingContext.getOptions().setBatchMode(false);
		editedEClass().setName(ORIGINAL_ECLASS_NAME);
	}

	private static class TestEditingDomain extends AdapterFactoryEditingDomain {

		public TestEditingDomain(AdapterFactory adapterFactory, CommandStack commandStack, Map<Resource, Boolean> resourceToReadOnlyMap) {
			super(adapterFactory, commandStack, resourceToReadOnlyMap);
		}

		public TestEditingDomain(AdapterFactory adapterFactory, CommandStack commandStack, ResourceSet resourceSet) {
			super(adapterFactory, commandStack, resourceSet);
		}

		public TestEditingDomain(AdapterFactory adapterFactory, CommandStack commandStack) {
			super(adapterFactory, commandStack);
		}
		
		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain#createCommand(java.lang.Class, org.eclipse.emf.edit.command.CommandParameter)
		 */
		@Override
		public Command createCommand(Class<? extends Command> commandClass, CommandParameter commandParameter) {
			if (commandClass == SetCommand.class && commandParameter.getFeature() == EcorePackage.Literals.ENAMED_ELEMENT__NAME) {
				return new SetCommand(TestEditingDomain.this, commandParameter.getEOwner(), (EStructuralFeature)commandParameter.getFeature(), MARKER);
			}
			return super.createCommand(commandClass, commandParameter);
		}
	}

}
