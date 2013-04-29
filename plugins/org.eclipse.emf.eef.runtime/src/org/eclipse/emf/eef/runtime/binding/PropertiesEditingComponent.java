/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.internal.services.editingProvider.AbstractPropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.emf.eef.runtime.notify.ViewChangeNotifier;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingComponent {

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
	 * Defines the valid {@link EEFLockPolicy} to use in the current component.
	 * @param lockPolicies a {@link Collection} of {@link EEFLockPolicy} to use.
	 */
	void setLockPolicies(Collection<EEFLockPolicy> lockPolicies);
	
	void propagateEvent(PropertiesEditingEvent event);
	
	/**
	 * Disposes this component. Main topic is to remove this adapter from its host.
	 */
	void dispose();

	List<ViewHandler<?>> getViewHandlers();

	/**
	 * @return the {@link PropertiesEditingModel} describing the Editing Forms for the given {@link EObject}.
	 * @processing
	 */
	PropertiesEditingModel getEditingModel();

	/**
	 * Defines if the given notification has impact on the current component.
	 * @param notification the {@link Notification} to check.
	 * @return <code>true</code> if the current component must be notified.
	 */
	boolean isAffectingEvent(Notification notification);

	/**
	 * Defines if the given lockPolicy should be enabled for the current component. 
	 * @param lockPolicy the {@link EEFLockPolicy} to check.
	 * @return <code>true</code> if the given lockPolicy should be enable.
	 */
	boolean enableLockPolicy(EEFLockPolicy lockPolicy);

}
