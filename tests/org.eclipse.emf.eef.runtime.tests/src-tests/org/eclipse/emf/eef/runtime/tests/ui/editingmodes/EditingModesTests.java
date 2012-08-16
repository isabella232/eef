/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.editingmodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.eef.runtime.context.impl.DomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
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
public class EditingModesTests extends NonUIEditingTestCase {

	private static final String ORIGINAL_ECLASS_NAME = "Test";
	private static final boolean ORIGINAL_ECLASS_ABSTRACTION = true;
	private static final String NEW_ECLASS_NAME = "NewName";
	private static final boolean NEW_ECLASS_ABSTRACTION = false;

	private TrackingCommandStack commandStack;

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
		editedEClass.setAbstract(ORIGINAL_ECLASS_ABSTRACTION);
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
	protected EObjectPropertiesEditingContext createEditingContext() {
		commandStack = new TrackingCommandStack();
		AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(adapterFactory , commandStack);
		return new DomainPropertiesEditingContext(domain, editedObject);
	}


	/**
	 * This test checks the live mode : two changes are made, two commands are sent to the commandstack and when we undo the last command,
	 * only one feature is restored.
	 * At end of the test all must be restored as at the beginning. 
	 */
	@Test
	public void testLiveEditingMode() {
		view().setName(NEW_ECLASS_NAME);
		assertEquals("Bad commands count", 1, commandStack.getExecutedCommandCount());
		view().setAbstract(NEW_ECLASS_ABSTRACTION);
		assertEquals("Bad commands count", 2, commandStack.getExecutedCommandCount());
		assertEquals("Bad dirty state", true, commandStack.isSaveNeeded());
		commandStack.undo();
		assertEquals("Bad undo operation", ORIGINAL_ECLASS_ABSTRACTION, editedEClass().isAbstract());
		assertSame("Undo revert to many commands.", NEW_ECLASS_NAME, editedEClass().getName());
		view().setName(ORIGINAL_ECLASS_NAME);
		commandStack.flush();
	}
	
	/**
	 * This test checks the batch mode : two changes are made in only one commands and when we undo this command,
	 * the original state is restored.
	 */
	@Test
	public void testBatchEditingMode() {
		editingContext.getOptions().setBatchMode(true);
		commandStack.execute(new AbstractBatchEditingCommand(editingContext) {
			
			@Override
			protected boolean prepareBatchEditing() {
				view().setName(NEW_ECLASS_NAME);
				view().setAbstract(NEW_ECLASS_ABSTRACTION);
				return true;
			}
		});
		assertEquals("Bad editing command", NEW_ECLASS_ABSTRACTION, editedEClass().isAbstract());
		assertEquals("Bad editing command", NEW_ECLASS_NAME, editedEClass().getName());
		assertEquals("Bad commands count", 1, commandStack.getExecutedCommandCount());
		assertEquals("Bad dirty state", true, commandStack.isSaveNeeded());
		commandStack.undo();
		assertEquals("Bad undo operation", ORIGINAL_ECLASS_ABSTRACTION, editedEClass().isAbstract());
		assertEquals("Bad undo operation", ORIGINAL_ECLASS_NAME, editedEClass().getName());
		assertEquals("Bad dirty state", false, commandStack.isSaveNeeded());
		commandStack.flush();
		editingContext.getOptions().setBatchMode(false);
	}

	/**
	 * This test checks the batch mode : two changes are made in only one commands and when we undo this command,
	 * the original state is restored.
	 */
	@Test
	public void testCancelBatchEditing() {
		editingContext.getOptions().setBatchMode(true);
		commandStack.execute(new AbstractBatchEditingCommand(editingContext) {
			
			@Override
			protected boolean prepareBatchEditing() {
				view().setName(NEW_ECLASS_NAME);
				view().setAbstract(NEW_ECLASS_ABSTRACTION);
				assertEquals("Editing operation not applied,", NEW_ECLASS_ABSTRACTION, editedEClass().isAbstract());
				assertEquals("Editing operation not applied", NEW_ECLASS_NAME, editedEClass().getName());
				return false;
			}
		});
		assertEquals("Bad dirty state", false, commandStack.isSaveNeeded());
		assertEquals("Bad CommandStack state", false, commandStack.canUndo());
		commandStack.flush();
		editingContext.getOptions().setBatchMode(false);
	}

	private static class TrackingCommandStack extends BasicCommandStack {
		
		private int executedCommandCount = 0;
		
		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.common.command.BasicCommandStack#execute(org.eclipse.emf.common.command.Command)
		 */
		@Override
		public void execute(Command command) {
			super.execute(command);
			executedCommandCount++;
		}

		/**
		 * @return the executedCommandCount
		 */
		public int getExecutedCommandCount() {
			return executedCommandCount;
		}

	}
}
