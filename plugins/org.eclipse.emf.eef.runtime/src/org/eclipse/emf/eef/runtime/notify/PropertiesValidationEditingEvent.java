/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.notify;

import org.eclipse.emf.common.util.Diagnostic;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesValidationEditingEvent implements PropertiesEditingEvent {

	private PropertiesEditingEvent editingEvent;

	private Diagnostic diagnostic;

	/**
	 * @return the diagnostic
	 */
	public Diagnostic getDiagnostic() {
		return diagnostic;
	}

	public PropertiesValidationEditingEvent(PropertiesEditingEvent event, Diagnostic diag) {
		this.editingEvent = event;
		this.diagnostic = diag;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#getSource()
	 */
	public Object getSource() {
		return editingEvent.getSource();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#getAffectedEditor()
	 */
	public Object getAffectedEditor() {
		return editingEvent.getAffectedEditor();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#getEventType()
	 */
	public int getEventType() {
		return editingEvent.getEventType();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#getNewValue()
	 */
	public Object getNewValue() {
		return editingEvent.getNewValue();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#getOldValue()
	 */
	public Object getOldValue() {
		return editingEvent.getOldValue();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#addHolder(java.lang.Object)
	 */
	public void addHolder(Object holder) {
		editingEvent.addHolder(holder);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#hold(org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener)
	 */
	public boolean hold(PropertiesEditingListener toTest) {
		return editingEvent.hold(toTest);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#delayedChanges()
	 */
	public boolean delayedChanges() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#setDelayed(boolean)
	 */
	public void setDelayed(boolean delayed) {
		// Do nothing
	}

}
