/**
 * 
 */
package org.eclipse.emf.eef.runtime.context;

import org.eclipse.emf.ecore.EObject;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditingRecorder {

	/**
	 * Initialize the modifications recording.
	 * @param src the source {@link EObject}.
	 */
	void initRecording(EObject src);
	
	/**
	 * Stop the recording of editing operations in this context.
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
	
	
	/**
	 * Disposes this recorder.
	 */
	void dispose();

	
}
