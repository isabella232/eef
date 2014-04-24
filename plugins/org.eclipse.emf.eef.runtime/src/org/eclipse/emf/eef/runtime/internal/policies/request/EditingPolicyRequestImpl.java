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
package org.eclipse.emf.eef.runtime.internal.policies.request;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class EditingPolicyRequestImpl implements EditingPolicyRequest {

	private ProcessingKind processingKind;

	private SemanticPropertiesEditingContext editingContext;
	private EObject target;
	private Object value;
	private int newIndex;
	private int oldIndex;

	/**
	 * @param processingKind
	 * @param target
	 * @param value
	 */
	public EditingPolicyRequestImpl(SemanticPropertiesEditingContext editingContext, ProcessingKind processingKind, EObject target, Object value) {
		this(editingContext, processingKind, target);
		this.value = value;
	}

	/**
	 * @param editingContext
	 * @param processingKind
	 * @param target
	 */
	public EditingPolicyRequestImpl(SemanticPropertiesEditingContext editingContext, ProcessingKind processingKind, EObject target) {
		this.editingContext = editingContext;
		this.processingKind = processingKind;
		this.target = target;
	}

	/**
	 * @param processingKind
	 * @param target
	 * @param newIndex
	 * @param oldIndex
	 */
	public EditingPolicyRequestImpl(SemanticPropertiesEditingContext editingContext, ProcessingKind processingKind, EObject target, int newIndex, int oldIndex) {
		this(editingContext, processingKind, target);
		this.newIndex = newIndex;
		this.oldIndex = oldIndex;
	}

	/**
	 * @return the processingKind
	 */
	public ProcessingKind getProcessingKind() {
		return processingKind;
	}

	/**
	 * @return the target
	 */
	public EObject getTarget() {
		return target;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @return the newIndex
	 */
	public int getNewIndex() {
		return newIndex;
	}

	/**
	 * @return the oldIndex
	 */
	public int getOldIndex() {
		return oldIndex;
	}

}
