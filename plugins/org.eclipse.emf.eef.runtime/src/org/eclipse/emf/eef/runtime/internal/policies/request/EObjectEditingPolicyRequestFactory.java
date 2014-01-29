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
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.TargetedEditingEvent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest.ProcessingKind;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequestFactory;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectEditingPolicyRequestFactory implements EditingPolicyRequestFactory {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
		return element instanceof SemanticPropertiesEditingContext;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyRequestFactory#createProcessing(org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext)
	 */
	public EditingPolicyRequest createProcessing(SemanticPropertiesEditingContext editingContext) {
		EditingPolicyRequest.Builder requestBuilder = new EditingPolicyRequest.Builder();
		PropertiesEditingEvent editingEvent = ((SemanticPropertiesEditingContext) editingContext).getEditingEvent();
		EClassBinding binding = editingContext.getEditingComponent().getBinding();
		PropertyBinding propertyBinding = binding.propertyBinding(editingEvent.getAffectedEditor(), editingContext.getOptions().autowire());
		EObject editedObject;
		if (editingEvent instanceof TargetedEditingEvent) {
			editedObject = ((TargetedEditingEvent) editingEvent).getTarget();
		} else {
			editedObject = (EObject)editingContext.getEditingComponent().getEObject();
		}
		if (propertyBinding != null) {
			requestBuilder.setTarget(editedObject);
			requestBuilder.setPropertyBinding(propertyBinding);
			switch (editingEvent.getEventType()) {
			case PropertiesEditingEvent.SET:
				requestBuilder.setProcessingKind(ProcessingKind.SET);
				requestBuilder.setValue(editingEvent.getNewValue());
				break;
			case PropertiesEditingEvent.UNSET:
				requestBuilder.setProcessingKind(ProcessingKind.UNSET);
				break;
			case PropertiesEditingEvent.EDIT:
				requestBuilder.setProcessingKind(ProcessingKind.EDIT);
				requestBuilder.setValue(editingEvent.getNewValue());
				break;
			case PropertiesEditingEvent.ADD:
				requestBuilder.setProcessingKind(ProcessingKind.ADD);
				requestBuilder.setValue(editingEvent.getNewValue());
				break;
			case PropertiesEditingEvent.ADD_MANY:
				requestBuilder.setProcessingKind(ProcessingKind.ADD_MANY);
				requestBuilder.setValue(editingEvent.getNewValue());
				break;	
			case PropertiesEditingEvent.REMOVE:
				requestBuilder.setProcessingKind(ProcessingKind.REMOVE);
				requestBuilder.setValue(editingEvent.getOldValue());
				break;
			case PropertiesEditingEvent.REMOVE_MANY:
				requestBuilder.setProcessingKind(ProcessingKind.REMOVE_MANY);
				requestBuilder.setValue(editingEvent.getOldValue());
				break;
			case PropertiesEditingEvent.MOVE:
				requestBuilder.setProcessingKind(ProcessingKind.MOVE);
				requestBuilder.setOldIndex((Integer)editingEvent.getOldValue());
				requestBuilder.setNewIndex((Integer)editingEvent.getNewValue());
				break;
			default:
				requestBuilder.setProcessingKind(ProcessingKind.SET);
				requestBuilder.setValue(editingEvent.getNewValue());
				break;
			}
		}
		return requestBuilder.build();
	}

}
