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
package org.eclipse.emf.eef.runtime.tests.views;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.TypedPropertyChangedEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EClassListeningView {

	private PropertyChangeSupport support;
	private String name;
	private boolean abstract_;

	/**
	 * @param editingComponent
	 */
	public EClassListeningView(PropertiesEditingComponent editingComponent) {
		this.support = new PropertyChangeSupport(this);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		if (name != null && !name.equals(this.name)) {
			System.out.println("EClassListeningView.setName()");
			this.name = name;
			support.firePropertyChange(new TypedPropertyChangedEvent(this, "name", TypedPropertyChangedEvent.SET, null, name));
		}
	}

	/**
	 * @return the abstract_
	 */
	public boolean isAbstract() {
		return abstract_;
	}

	/**
	 * @param abstract_ the abstract_ to set
	 */
	public void setAbstract(boolean abstract_) {
		if (this.abstract_ != abstract_) {
			this.abstract_ = abstract_;
			support.firePropertyChange(new TypedPropertyChangedEvent(this, "abstract", TypedPropertyChangedEvent.SET, null, abstract_));
		}
	}

	/**
	 * @return the support
	 */
	public PropertyChangeSupport getSupport() {
		return support;
	}


	/**
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

}
