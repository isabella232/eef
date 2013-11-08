/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.binding;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.emf.eef.runtime.notify.ViewChangeNotifier;
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
	 * @return the associated {@link EEFBindingSettings}.
	 */
	<T extends EObject> EEFBindingSettings<T> getBindingSettings();
	
	/**
	 * @return the {@link EClassBinding} describing the target {@link EObject} mapping.
	 */
	EClassBinding getBinding();
	
	/**
	 * TODO
	 */
	List<View> getViewDescriptors();
	
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

	/**
	 * Associates a view for the given descriptor.
	 * @param descriptor the referenced descriptor.
	 * @param view the associated view.
	 */
	void setViewForDescriptor(View descriptor, Object view);
	
	/**
	 * Returns the descriptor associated to the given view.
	 * @param view the view to process.
	 * @return the descriptor of the view.
	 */
	View getDescriptorForView(Object view);
	
	/**
	 * @return all the views of the current {@link PropertiesEditingComponent}.
	 */
	List<Object> getViews();
	
	/**
	 * Removes a view from this component.
	 * @param view the view to remove.
	 */
	void removeView(Object view);

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
