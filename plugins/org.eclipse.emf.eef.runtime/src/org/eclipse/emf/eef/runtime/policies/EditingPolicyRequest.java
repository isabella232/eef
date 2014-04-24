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
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.request.EditingPolicyRequestImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public interface EditingPolicyRequest {

	public enum ProcessingKind {
		SET, UNSET, EDIT, ADD, ADD_MANY, REMOVE, REMOVE_MANY, MOVE
	}

	/**
	 * Returns the edited Object by the current request.
	 * 
	 * @return the edited {@link EObject}.
	 */
	EObject getTarget();

	/**
	 * Returns the new value for the feature.
	 * 
	 * @return the new value.
	 */
	Object getValue();

	/**
	 * Returns the type of current requests
	 * 
	 * @return the {@link ProcessingKind} for this request.
	 */
	ProcessingKind getProcessingKind();

	/**
	 * Returns the new index stored in the request.
	 * 
	 * @return the new index of the current request.
	 */
	int getNewIndex();

	/**
	 * Returns the old index stored in the request.
	 * 
	 * @return the old index of the current request.
	 */
	int getOldIndex();

	public static class Builder {

		private SemanticPropertiesEditingContext editingContext;
		private ProcessingKind processingKind;
		private EObject target;
		private Object value;
		private Integer newIndex;
		private Integer oldIndex;

		/**
		 * @param editingContext
		 *            the editingContext to set
		 * @return the current {@link Builder}
		 */
		public Builder setEditingContext(SemanticPropertiesEditingContext editingContext) {
			this.editingContext = editingContext;
			return this;
		}

		/**
		 * @param processingKind
		 *            the processingKind to set
		 * @return the current {@link Builder}
		 */
		public Builder setProcessingKind(ProcessingKind processingKind) {
			this.processingKind = processingKind;
			return this;
		}

		/**
		 * @param target
		 *            the target to set
		 * @return the current {@link Builder}
		 */
		public Builder setTarget(EObject target) {
			this.target = target;
			return this;
		}

		/**
		 * @param value
		 *            the value to set
		 * @return the current {@link Builder}
		 */
		public Builder setValue(Object value) {
			this.value = value;
			return this;
		}

		/**
		 * @param newIndex
		 *            the newIndex to set
		 * @return the current {@link Builder}
		 */
		public Builder setNewIndex(Integer newIndex) {
			this.newIndex = newIndex;
			return this;
		}

		/**
		 * @param oldIndex
		 *            the oldIndex to set
		 * @return the current {@link Builder}
		 */
		public Builder setOldIndex(Integer oldIndex) {
			this.oldIndex = oldIndex;
			return this;
		}

		/**
		 * Creates a new {@link EditingPolicyRequest}.
		 * 
		 * @return the built {@link EditingPolicyRequest}.
		 */
		public EditingPolicyRequest build() {
			if (processingKind != null && target != null) {
				if (value != null) {
					return new EditingPolicyRequestImpl(editingContext, processingKind, target, value);
				}
				if (oldIndex != null && newIndex != null) {
					return new EditingPolicyRequestImpl(editingContext, processingKind, target, newIndex, oldIndex);
				} else {
					return new EditingPolicyRequestImpl(editingContext, processingKind, target);
				}
			}
			throw new IllegalArgumentException("Unable to build a EditPolicyRequest with the given arguments.");
		}

	}

}
