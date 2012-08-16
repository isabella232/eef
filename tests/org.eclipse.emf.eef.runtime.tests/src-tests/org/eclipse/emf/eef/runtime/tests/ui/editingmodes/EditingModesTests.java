/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.editingmodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.DomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
import org.eclipse.emf.eef.runtime.tests.views.EClassListeningView;
import org.eclipse.emf.eef.runtime.ui.commands.AbstractBatchEditingCommand;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingModesTests {

	private static final String ORIGINAL_ECLASS_NAME = "Test";
	private static final boolean ORIGINAL_ECLASS_ABSTRACTION = true;
	private static final String NEW_ECLASS_NAME = "NewName";
	private static final boolean NEW_ECLASS_ABSTRACTION = false;
	
	private EClassListeningView view;
	private TrackingCommandStack commandStack;
	private EClass editedEClass;
	private PropertiesEditingContext editingContext;

	@Before
	public void setUp() {
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory.addAdapterFactory(new PropertiesEditingProvider() {
			
			@Override
			protected Collection<? extends PropertiesEditingModel> initSpecificEditingModel() {
				List<PropertiesEditingModel> result = new ArrayList<PropertiesEditingModel>();
				result.add(new EditingModelBuilder()
					.bindClass(EcorePackage.Literals.ECLASS)
						.withView(EClassListeningView.class).build());
				return result;
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingProvider#initViewHandlerProvider()
			 */
			@Override
			protected ViewHandlerProvider initViewHandlerProvider() {
				return new EEFTestStuffsBuilder().buildViewHandlerProvider();
			}
			
			
			
		});
		commandStack = new TrackingCommandStack();
		AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(adapterFactory , commandStack);
		editedEClass = EcoreFactory.eINSTANCE.createEClass();
		editedEClass.setName(ORIGINAL_ECLASS_NAME);
		editedEClass.setAbstract(ORIGINAL_ECLASS_ABSTRACTION);
		
		editingContext = new DomainPropertiesEditingContext(domain, editedEClass);
		PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
		assertNotNull("Unable to create a PropertiesEditingComponent", editingComponent);
		Collection<ViewHandler<?>> viewHandlers = editingComponent.createViewHandlers();
		assertEquals("Bad number of ViewHandlers created", 1, viewHandlers.size());
		ViewHandler<?> viewHandler = viewHandlers.iterator().next();
		try {
			Object createdView = viewHandler.createView();
			assertTrue("Bad view created", createdView instanceof EClassListeningView);
			view = (EClassListeningView)createdView;
			viewHandler.initView(editingComponent);
			
		} catch (ViewConstructionException e) {
			fail("Unable to create the view" + e);
		}
	}
	
	/**
	 * This test checks the live mode : two changes are made, two commands are sent to the commandstack and when we undo the last command,
	 * only one feature is restored.
	 * At end of the test all must be restored as at the beginning. 
	 */
	@Test
	public void testLiveEditingMode() {
		view.setName(NEW_ECLASS_NAME);
		assertEquals("Bad commands count", 1, commandStack.getExecutedCommandCount());
		view.setAbstract(NEW_ECLASS_ABSTRACTION);
		assertEquals("Bad commands count", 2, commandStack.getExecutedCommandCount());
		assertEquals("Bad dirty state", true, commandStack.isSaveNeeded());
		commandStack.undo();
		assertEquals("Bad undo operation", ORIGINAL_ECLASS_ABSTRACTION, editedEClass.isAbstract());
		assertSame("Undo revert to many commands.", NEW_ECLASS_NAME, editedEClass.getName());
		view.setName(ORIGINAL_ECLASS_NAME);
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
				view.setName(NEW_ECLASS_NAME);
				view.setAbstract(NEW_ECLASS_ABSTRACTION);
				return true;
			}
		});
		assertEquals("Bad editing command", NEW_ECLASS_ABSTRACTION, editedEClass.isAbstract());
		assertEquals("Bad editing command", NEW_ECLASS_NAME, editedEClass.getName());
		assertEquals("Bad commands count", 1, commandStack.getExecutedCommandCount());
		assertEquals("Bad dirty state", true, commandStack.isSaveNeeded());
		commandStack.undo();
		assertEquals("Bad undo operation", ORIGINAL_ECLASS_ABSTRACTION, editedEClass.isAbstract());
		assertEquals("Bad undo operation", ORIGINAL_ECLASS_NAME, editedEClass.getName());
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
				view.setName(NEW_ECLASS_NAME);
				view.setAbstract(NEW_ECLASS_ABSTRACTION);
				assertEquals("Editing operation not applied,", NEW_ECLASS_ABSTRACTION, editedEClass.isAbstract());
				assertEquals("Editing operation not applied", NEW_ECLASS_NAME, editedEClass.getName());
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
