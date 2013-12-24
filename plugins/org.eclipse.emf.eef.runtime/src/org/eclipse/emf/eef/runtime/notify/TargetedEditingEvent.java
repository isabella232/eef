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

import org.eclipse.emf.ecore.EObject;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TargetedEditingEvent extends PropertiesEditingEventImpl {

	private EObject target;

	public TargetedEditingEvent(Object source, Object affectedEditor, EObject target, int eventType, Object oldValue, Object newValue) {
		super(source, affectedEditor, eventType, oldValue, newValue);
		this.target = target;
	}

	public TargetedEditingEvent(Object source, Object affectedEditor, EObject target, int eventType, Object oldValue, Object newValue, boolean delayed) {
		super(source, affectedEditor, eventType, oldValue, newValue, delayed);
		this.target = target;
	}

	/**
	 * @return the target of this event.
	 */
	public EObject getTarget() {
		return target;
	}
	
}
