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
package org.eclipse.emf.eef.runtime.view.lock;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFLockManager extends EEFService<Object> {

	/**
	 * Initialize the view locks.
	 * @param view the view to process.
	 */
	void initView(Object view);
	
	/**
	 * Locks the given view.
	 * @param view the view to lock.
	 */
	void lockView(Object view);
	
	/**
	 * Locks an editor in the given view.
	 * @param view the view to process.
	 * @param editor the editor to lock.
	 */
	void lockEditor(Object view, Object editor);
	
	/**
	 * Removes the lock of the view.
	 * @param view the view to process.
	 */
	void clearViewLock(Object view);
	
	/**
	 * Removes the lock on a given editor.
	 * @param view the view to process.
	 * @param editor the editor to unlock
	 */
	void clearEditorLock(Object view, Object editor);
	
	/**
	 * Notifies the current manager of a lock change.
	 * @param editingComponent the {@link PropertiesEditingComponent} receiving the event.
	 * @param view the view to process.
	 * @param lockEvent the {@link EEFLockEvent} describing the lock.
	 */
	void fireLockChange(PropertiesEditingComponent editingComponent, Object view, EEFLockEvent lockEvent);
	
}
