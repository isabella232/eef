/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EventTimer {

	private PropertiesBindingHandler bindingManager;
	
	private static Future<?> currentFuture;
	private final ScheduledExecutorService executor;
	
	public EventTimer(PropertiesBindingHandler bindingManager) {
		this.bindingManager = bindingManager;
		executor = Executors.newSingleThreadScheduledExecutor();
	}

	public void schedule(final PropertiesEditingComponent editingComponent, final PropertiesEditingEvent editingEvent) {
		if (currentFuture != null && !currentFuture.isDone()) {
			currentFuture.cancel(true);
		}
		final ScheduledFuture<?> future = executor.schedule(new DelayFirePropertiesChange(bindingManager, editingComponent, editingEvent), editingComponent.getEditingContext().getOptions().delayedFirePropertiesChangedDelay(), TimeUnit.MILLISECONDS);
		setCurrentExecutingFuture(future);
	}
	
	
	private void setCurrentExecutingFuture(Future<?> f) {
		currentFuture = f;
	}

	private static class DelayFirePropertiesChange implements Runnable {

		private PropertiesBindingHandler bindingManager;
		private PropertiesEditingComponent editingComponent;
		private PropertiesEditingEvent editingEvent;
		
		/**
		 * @param bindingManager
		 * @param editingComponent
		 * @param editingEvent
		 */
		public DelayFirePropertiesChange(PropertiesBindingHandler bindingManager, PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
			this.bindingManager = bindingManager;
			this.editingComponent = editingComponent;
			this.editingEvent = editingEvent;
		}


		/**
		 * {@inheritDoc}
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			editingEvent.setDelayed(false);
			bindingManager.firePropertiesChanged(editingComponent, editingEvent);
		}
		
		
		
	}

}
