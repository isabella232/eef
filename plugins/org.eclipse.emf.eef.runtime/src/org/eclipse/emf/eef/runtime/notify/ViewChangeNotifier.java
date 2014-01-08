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
package org.eclipse.emf.eef.runtime.notify;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.emf.eef.runtime.binding.BindingHandlerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewChangeNotifier implements PropertyChangeListener {

	private BindingHandlerProvider bindingHandlerProvider;
	private PropertiesEditingComponent component;
	
	/**
	 * @param bindingHandlerProvider
	 * @param component
	 */
	public ViewChangeNotifier(BindingHandlerProvider bindingHandlerProvider, PropertiesEditingComponent component) {
		this.bindingHandlerProvider = bindingHandlerProvider;
		this.component = component;
	}

	/**
	 * {@inheritDoc}
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("ViewChangeNotifier.propertyChange()");
		bindingHandlerProvider.getBindingManager(component.getEObject()).firePropertiesChanged(component, new PropertiesEditingEventImpl(
				evt.getSource(), evt.getPropertyName(), 
				((evt instanceof TypedPropertyChangedEvent)?((TypedPropertyChangedEvent)evt).getEventType():PropertiesEditingEvent.SET), 
				evt.getOldValue(), evt.getNewValue()));
	}

}
