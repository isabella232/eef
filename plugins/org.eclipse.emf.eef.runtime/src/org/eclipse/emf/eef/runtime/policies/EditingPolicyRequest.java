/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.internal.policies.request.EditingPolicyRequestImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditingPolicyRequest {
	
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

	/**
	 * Returns the edited Object by the current request.
	 * @return the edited {@link EObject}.
	 */
	EObject getTarget();

	/**
	 * Returns the edited feature by the current request.
	 * @return the edited {@link EStructuralFeature}.
	 */
	EStructuralFeature getFeature();

	/**
	 * Returns the new value for the feature.
	 * @return the new value.
	 */
	Object getValue();

	/**
	 * Returns the type of current requests 
	 * @return the {@link ProcessingKind} for this request.
	 */
	ProcessingKind getProcessingKind();

	/**
	 * Returns the new index stored in the request.
	 * @return the new index of the current request.
	 */
	int getNewIndex();
	
	/**
	 * Returns the old index stored in the request.
	 * @return the old index of the current request.
	 */
	int getOldIndex();
	
	public static class Builder  {
		
		private ProcessingKind processingKind;
		private EObject target;
		private EStructuralFeature feature;
		private Object value;
		private Integer newIndex;
		private Integer oldIndex;
		
		/**
		 * @param processingKind the processingKind to set
		 * @return the current {@link Builder}
		 */
		public Builder setProcessingKind(ProcessingKind processingKind) {
			this.processingKind = processingKind;
			return this;
		}
		
		/**
		 * @param target the target to set
		 * @return the current {@link Builder}
		 */
		public Builder setTarget(EObject target) {
			this.target = target;
			return this;
		}
		
		/**
		 * @param feature the feature to set
		 * @return the current {@link Builder}
		 */
		public Builder setFeature(EStructuralFeature feature) {
			this.feature = feature;
			return this;
		}
		
		/**
		 * @param value the value to set
		 * @return the current {@link Builder}
		 */
		public Builder setValue(Object value) {
			this.value = value;
			return this;
		}
		
		/**
		 * @param newIndex the newIndex to set
		 * @return the current {@link Builder}
		 */
		public Builder setNewIndex(Integer newIndex) {
			this.newIndex = newIndex;
			return this;
		}
		
		/**
		 * @param oldIndex the oldIndex to set
		 * @return the current {@link Builder}
		 */
		public Builder setOldIndex(Integer oldIndex) {
			this.oldIndex = oldIndex;
			return this;
		}
		
		/**
		 * Creates a new {@link EditingPolicyRequest}.
		 * @return the built {@link EditingPolicyRequest}.
		 */
		public EditingPolicyRequest build() {
			if (processingKind != null && target != null && feature != null) {
				if (value != null) {
					return new EditingPolicyRequestImpl(processingKind, target, feature, value);
				} if (oldIndex != null && newIndex != null) {
					return new EditingPolicyRequestImpl(processingKind, target, feature, oldIndex, newIndex);
				} else {
					return new EditingPolicyRequestImpl(processingKind, target, feature);
				}
			}
			throw new IllegalArgumentException("Unable to build a EditPolicyRequest with the given arguments.");
		}
		
	}

}
