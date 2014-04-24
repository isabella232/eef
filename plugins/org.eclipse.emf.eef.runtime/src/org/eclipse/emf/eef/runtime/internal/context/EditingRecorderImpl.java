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
package org.eclipse.emf.eef.runtime.internal.context;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.eef.runtime.context.EditingRecorder;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class EditingRecorderImpl implements EditingRecorder {

	private ChangeRecorder changeRecorder;
	private ChangeDescription modificationsRecording;

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.EditingRecorder#getChangeRecorder()
	 */
	public ChangeRecorder getChangeRecorder() {
		return changeRecorder;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.EditingRecorder#initRecording(org.eclipse.emf.ecore.EObject)
	 */
	public void initRecording(EObject src) {
		Resource resource = src.eResource();
		if (resource != null) {
			if (resource.getResourceSet() != null) {
				this.changeRecorder = new ChangeRecorder(resource.getResourceSet());
			} else {
				this.changeRecorder = new ChangeRecorder(resource);
			}
		} else {
			this.changeRecorder = new ChangeRecorder(src);
		}
	}

	// Stop the modifications recording.
	private void endRecording() {
		modificationsRecording = changeRecorder.endRecording();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.EditingRecorder#stopEditing()
	 */
	public void stopEditing() {
		endRecording();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.EditingRecorder#cancelEditing()
	 */
	public void cancelEditing() {
		undoEditing();
		if (modificationsRecording != null) {
			modificationsRecording = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.EditingRecorder#undoEditing()
	 */
	public void undoEditing() {
		if (changeRecorder != null) {
			if (changeRecorder.isRecording()) {
				endRecording();
			}
			if (modificationsRecording != null) {
				modificationsRecording.applyAndReverse();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.EditingRecorder#dispose()
	 */
	public void dispose() {
		if (changeRecorder != null) {
			changeRecorder.dispose();
		}
	}

}
