/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core.notify;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder;
import org.eclipse.emf.eef.runtime.tests.views.EClassMockView;
import org.junit.Test;

/**
 * This class tests the EEF components ability to delayed command execution.
 * 
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class PropertiesChangeDelayingTests extends NonUIEditingTestCase {

	private static final String NEW_CLASS_NAME = "NewClassName";
	private BasicCommandStack commandStack;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase#initEnvironmentBuilder()
	 */
	@Override
	protected Builder initEnvironmentBuilder() {
		Builder builder = super.initEnvironmentBuilder();
		return builder.setEditingModel(new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID)
													.bindClass(EcorePackage.Literals.ECLASS)
														.withView(EClassMockView.class)
												.build())
											.setEditingContext(createEditingContext(builder));
	}
	
	private PropertiesEditingContext createEditingContext(Builder builder) {
		commandStack = new BasicCommandStack();
		return builder.createPropertiesEditingContextFactory().createPropertiesEditingContext(new AdapterFactoryEditingDomain(builder.getAdapterFactory(), commandStack), builder.getEditedObject());
	}

	/**
	 * This test sends delayed events and check regularly the command stack state. 
	 */
	@Test
	public void testEventDelaying() {
		commandStack.saveIsDone();
		final EClassMockView view1 = (EClassMockView) views.get(0);
		PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
		
		// T0: We send an editingEvent
		long timing = 0;
		environmentBuilder.getBindingManagerProvider().getBindingManager(editingComponent.getEObject()).firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(view1, "name", PropertiesEditingEvent.SET, null, NEW_CLASS_NAME, true));
		assertFalse("Command performed too soon.", commandStack.isSaveNeeded());
		final long delay = 30;
		editingContext.getOptions().setDelayedFirePropertiesChangedDelay(delay);
		TestScheduler executor = new TestScheduler();
		
		// T1: 10ms before command execution, we send an event again
		timing = delay - 10;
		int i = 1;
		executor.schedule(new TextUpdater(editingComponent, view1, NEW_CLASS_NAME + i), timing, TimeUnit.MILLISECONDS);
		
		// T2: 10ms before command execution, we check that no command has been performed.
		CommandStackChecker checker1 = new CommandStackChecker(commandStack);
		timing = timing + delay - 10;
		executor.schedule(checker1, timing, TimeUnit.MILLISECONDS);
		
		// T3: 1ms after, we send a event again.
		timing = timing + 1;
		executor.schedule(new TextUpdater(editingComponent, view1, NEW_CLASS_NAME + ++i), timing, TimeUnit.MILLISECONDS);
		
		// T4: 10ms before command execution, we check that no command has been performed.
		CommandStackChecker checker2 = new CommandStackChecker(commandStack);
		timing = timing + delay - 10;
		executor.schedule(checker2, timing, TimeUnit.MILLISECONDS);
		
		// T5: We wait until command execution and we check that the command has been performed.
		CommandStackChecker checker3 = new CommandStackChecker(commandStack);
		timing = timing + 50;
		executor.schedule(checker3, timing, TimeUnit.MILLISECONDS);
		
		while (!executor.allJobsDone()) {}
		
		assertFalse("Command performed too soon.", checker1.getStackState());
		assertFalse("Command performed too soon.", checker2.getStackState());
		assertTrue("Command seems to not be performed.", checker3.getStackState());
		
		commandStack.saveIsDone();
	}

	private static class TestScheduler {

		private ScheduledExecutorService executor;
		private List<Future<?>> futures;

		public TestScheduler() {
			executor = Executors.newSingleThreadScheduledExecutor();
			futures = new ArrayList<Future<?>>();
		}

		/**
		 * @see java.util.concurrent.ScheduledExecutorService#schedule(java.lang.Runnable, long, java.util.concurrent.TimeUnit)
		 */
		public void schedule(Runnable arg0, long arg1, TimeUnit arg2) {
			futures.add(executor.schedule(arg0, arg1, arg2));
		}
		
		public boolean allJobsDone() {
			for (Future<?> future : futures) {
				if (!future.isDone()) {
					return false;
				}
			}
			return true;
		}
		
	}
	
	private class TextUpdater implements Runnable {
		
		private final PropertiesEditingComponent editingComponent;
		private final EClassMockView view;
		private final String newValue;
		
		
		public TextUpdater(PropertiesEditingComponent editingComponent, EClassMockView view, String newValue) {
			this.editingComponent = editingComponent;
			this.view = view;
			this.newValue = newValue;
		}


		/**
		 * {@inheritDoc}
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			environmentBuilder.getBindingManagerProvider().getBindingManager(editingComponent.getEObject()).firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(view, "name", PropertiesEditingEvent.SET, null, newValue, true));			
		}
		
	}
	
	private static class CommandStackChecker implements Runnable {
		
		private final BasicCommandStack commandStack;
		private boolean stackState;

		public CommandStackChecker(BasicCommandStack commandStack) {
			this.commandStack = commandStack;
		}

		/**
		 * {@inheritDoc}
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			stackState = commandStack.isSaveNeeded();
		}

		/**
		 * @return the stackState
		 */
		public boolean getStackState() {
			return stackState;
		}
		
	}
	
}
