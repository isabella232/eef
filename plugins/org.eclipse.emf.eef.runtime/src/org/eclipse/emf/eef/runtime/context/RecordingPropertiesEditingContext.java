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

package org.eclipse.emf.eef.runtime.context;

import org.eclipse.emf.ecore.change.util.ChangeRecorder;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public interface RecordingPropertiesEditingContext extends PropertiesEditingContext {

	/**
	 * @return the {@link ChangeRecorder} recording the changes implies by the
	 *         current context.
	 */
	ChangeRecorder getChangeRecorder();

	/**
	 * start the recording of editing operations in this context.
	 */
	void startEditing();

	/**
	 * stop the recording of editing operations in this context.
	 */
	void stopEditing();

	/**
	 * Cancel all the editing operations in this context.
	 */
	void cancelEditing();

	/**
	 * Undo all the editing operations in this context.
	 */
	void undoEditing();

}
