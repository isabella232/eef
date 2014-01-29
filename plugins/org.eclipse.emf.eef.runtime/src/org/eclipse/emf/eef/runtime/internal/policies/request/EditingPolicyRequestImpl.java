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
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingPolicyRequestImpl implements EditingPolicyRequest {

	private ProcessingKind processingKind;
	
	private EObject target;
	private PropertyBinding propertyBinding;
	private Object value;
	private int newIndex;
	private int oldIndex;
	
	/**
	 * @param processingKind
	 * @param target
	 * @param feature
	 * @param value
	 */
	public EditingPolicyRequestImpl(ProcessingKind processingKind, EObject target, PropertyBinding propertyBinding, Object value) {
		this.processingKind = processingKind;
		this.target = target;
		this.propertyBinding = propertyBinding;
		this.value = value;
	}

	/**
	 * @param processingKind
	 * @param target
	 * @param feature
	 */
	public EditingPolicyRequestImpl(ProcessingKind processingKind, EObject target, PropertyBinding propertyBinding) {
		this.processingKind = processingKind;
		this.target = target;
		this.propertyBinding = propertyBinding;
	}

	/**
	 * @param processingKind
	 * @param target
	 * @param feature
	 * @param newIndex
	 * @param oldIndex
	 */
	public EditingPolicyRequestImpl(ProcessingKind processingKind, EObject target, PropertyBinding propertyBinding, int newIndex, int oldIndex) {
		this.processingKind = processingKind;
		this.target = target;
		this.propertyBinding = propertyBinding;
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
	 * @return the propertyBinding
	 */
	public PropertyBinding getPropertyBinding() {
		return propertyBinding;
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
