/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingPolicyProcessing {

	public ProcessingKind processingKind;
	
	public EObject target;
	
	public EStructuralFeature feature;
	
	public Object value;
	
	public Integer newIndex;

	public Integer oldIndex;

	public enum ProcessingKind {
		SET,
		UNSET,
		EDIT,
		ADD,
		ADD_MANY,
		REMOVE,
		REMOVE_MANY,
		MOVE
	}
	
}
