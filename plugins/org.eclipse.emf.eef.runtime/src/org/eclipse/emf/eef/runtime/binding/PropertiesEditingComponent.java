/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.internal.services.editingProvider.AbstractPropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.emf.eef.runtime.notify.ViewChangeNotifier;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
import org.osgi.service.event.EventHandler;

import com.google.common.base.Function;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingComponent extends EventHandler {

	/**
	 * @return the handled {@link EObject}.
	 */
	EObject getEObject();
	
    /**
	 * Returns the editing context of the component.
	 * @return the component's {@link PropertiesEditingContext}.
	 */
	PropertiesEditingContext getEditingContext();
	
	/**
	 * Sets the editing context for this component.
	 * @param editingContext {@link PropertiesEditingContext} to set.
	 */
	void setEditingContext(PropertiesEditingContext editingContext);
	
	/**
	 * @return the {@link EditingModelEnvironment} of the {@link AbstractPropertiesEditingProvider} that provided this component.
	 * 
	 */
	EditingModelEnvironment getEditingModelEnvironment();
	
	/**
	 * @return the {@link EClassBinding} describing the target {@link EObject} mapping.
	 */
	EClassBinding getBinding();
	
	/**
	 * @param editingContext the {@link SemanticPropertiesEditingContext} to process.
	 * @return a {@link PropertiesEditingPolicy} able to process the given {@link SemanticPropertiesEditingContext}.
	 */
	PropertiesEditingPolicy getEditingPolicy(PropertiesEditingContext editingContext);

	/**
	 * Returns the {@link ViewHandler} able to manage the given view.
	 * @param view the view to manage.
	 * @return a list of {@link ViewHandler} editing the model element.
	 */
	ViewHandler<?> createViewHandler(Object view);

	/**
	 * Returns all {@link ViewHandler}s able to manage the views for the edited model element.
	 * @return a list of {@link ViewHandler} editing the model element.
	 */
	Collection<ViewHandler<?>> createViewHandlers();
	
	/**
	 * @param handler register a handler in the component.
	 */
	void registerViewHandler(ViewHandler<?> handler);

	/**
	 * @param handler unregister a handler in the component.
	 */
	void unregisterViewHandler(ViewHandler<?> handler);
	
	/**
	 * Executes a {@link Function} on all the created {@link ViewHandler} by the current {@link PropertiesEditingComponent}.
	 * @param function the {@link Function} to execute. 
	 */
	void executeOnViewHandlers(Function<ViewHandler<?>, Void> function);
	
	/**
	 * @return the {@link ViewChangeNotifier} component.
	 */
	ViewChangeNotifier getViewChangeNotifier();
	
	/**
	 * Adds a listener to this component.
	 * @param listener the {@link PropertiesEditingListener} to add.
	 */
	void addEditingListener(PropertiesEditingListener listener);
	
	/**
	 * Removes a listener to this component.
	 * @param listener the {@link PropertiesEditingListener} to remove.
	 */
	void removeEditingListener(PropertiesEditingListener listener);
	
	/**
	 * Returns all the {@link EEFLockPolicy} available in the context of the current component.
	 * @return a collection of applicable policies.
	 */
	Collection<EEFLockPolicy> getLockPolicies();
	
	/**
	 * Notifies the current component of a model change.
	 * @param msg {@link Notification} describing the model change.
	 */
	void notifyChanged(Notification msg);
	
	/**
	 * Notifies the current component of a lock change.
	 * @param lockEvent {@link EEFLockEvent} describing the lock change.
	 */
	void fireLockChanged(EEFLockEvent lockEvent);

	/**
	 * Validates the element edited by the current component.
	 * @return a result {@link Diagnostic} for this validation. 
	 */
	Diagnostic validate();
	
	void propagateEvent(PropertiesEditingEvent event);
	
	/**
	 * Disposes this component. Main topic is to remove this adapter from its host.
	 */
	void dispose();

	List<ViewHandler<?>> getViewHandlers();

}
