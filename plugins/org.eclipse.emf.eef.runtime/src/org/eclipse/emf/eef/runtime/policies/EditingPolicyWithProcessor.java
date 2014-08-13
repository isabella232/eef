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

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.RecordingPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.TargetedEditingEvent;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class EditingPolicyWithProcessor implements PropertiesEditingPolicy {

	private final EEFEditingServiceProvider eefEditingServiceProvider;
	private final EditingPolicyRequest request;
	private final EditingPolicyProcessor processor;

	/**
	 * @param request
	 * @param processor
	 */
	public EditingPolicyWithProcessor(EEFEditingServiceProvider eefEditingServiceProvider, EditingPolicyRequest request, EditingPolicyProcessor processor) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.request = request;
		this.processor = processor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy#validateEditing(org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public EditingPolicyValidation validateEditing(SemanticPropertiesEditingContext editingContext) {
		PropertiesEditingEvent editingEvent = editingContext.getEditingEvent();
		EObject eObject;
		if (editingEvent instanceof TargetedEditingEvent) {
			eObject = ((TargetedEditingEvent) editingEvent).getTarget();
		} else {
			eObject = editingContext.getEditingComponent().getEObject();
		}
		EStructuralFeature feature = eefEditingServiceProvider.getEditingService(eObject).featureFromEditor(editingContext, editingEvent.getAffectedEditor());
		if (feature != null) {
			if (!eObject.eClass().getEAllStructuralFeatures().contains(feature)) {
				EMFService emfService = editingContext.getEMFServiceProvider().getEMFService(eObject.eClass().getEPackage());
				feature = emfService.mapFeature(eObject.eClass(), feature);
			}
			if (eObject.eClass().getEAllStructuralFeatures().contains(feature)) {
				boolean validationResult = false;
				Object currentValue = eObject.eGet(feature);
				if (feature instanceof EAttribute) {
					validationResult = validateAttributeEditing(editingEvent, feature, currentValue);
				} else if (feature instanceof EReference) {
					validationResult = validateReferenceEditing(editingEvent, eObject, feature, currentValue);
				}
				return new EditingPolicyValidation(this, validationResult);
			}
		} else {
			Object currentValue = eefEditingServiceProvider.getEditingService(eObject).getValue(editingContext, eObject, editingEvent.getAffectedEditor());
			boolean validated = false;
			switch (editingEvent.getEventType()) {
			case PropertiesEditingEvent.ADD:
				// is not already contained
				validated = currentValue instanceof List && !((List) currentValue).contains(request.getValue());
				break;
			case PropertiesEditingEvent.ADD_MANY:
				// all are not already contained
				validated = currentValue instanceof List && request.getValue() instanceof Collection && !((List) currentValue).containsAll((Collection) request.getValue());
				break;
			case PropertiesEditingEvent.EDIT:
				// nothing
				validated = true;
				break;
			case PropertiesEditingEvent.MOVE:
				// TODO
				validated = true;
				break;
			case PropertiesEditingEvent.REMOVE:
				// is already contained
				validated = currentValue instanceof List && ((List) currentValue).contains(request.getValue());
				break;
			case PropertiesEditingEvent.REMOVE_MANY:
				// all are already contained
				validated = currentValue instanceof List && request.getValue() instanceof Collection && ((List) currentValue).containsAll((Collection) request.getValue());
				break;
			case PropertiesEditingEvent.SET:
				// different values
				validated = ((request.getValue() != null && !request.getValue().equals(currentValue)) || request.getValue() == null);
				break;
			case PropertiesEditingEvent.UNSET:
				// nothing
				validated = true;
				break;
			default:
				break;
			}
			if (validated) {
				return new EditingPolicyValidation(this, true, "Editing a feature by accessor. Unable to check validity of the value.");
			}
		}
		return new EditingPolicyValidation(this, false, "The feature doesn't seem to affected the edited element.");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy#execute(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public final void execute(PropertiesEditingContext editingContext) {
		if (editingContext instanceof RecordingPropertiesEditingContext && ((RecordingPropertiesEditingContext) editingContext).getChangeRecorder() == null) {
			((RecordingPropertiesEditingContext) editingContext).startEditing();
		}
		processor.process(editingContext, request);
		if (editingContext instanceof RecordingPropertiesEditingContext) {
			((RecordingPropertiesEditingContext) editingContext).stopEditing();
		}
	}

	private boolean validateAttributeEditing(PropertiesEditingEvent editingEvent, EStructuralFeature feature, Object currentValue) {
		boolean validationResult;
		Object newValue = null;
		if (editingEvent.getNewValue() != null) {
			if (feature instanceof EAttribute && editingEvent.getNewValue() instanceof String) {
				try {
					newValue = EcoreUtil.createFromString(((EAttribute) feature).getEAttributeType(), (String) editingEvent.getNewValue());
				} catch (Exception e) {
					// Silent catch
				}
			}
			if (newValue == null) {
				newValue = editingEvent.getNewValue();
			}
		}
		validationResult = (currentValue == null && newValue != null) || (currentValue != null && !currentValue.equals(newValue));
		return validationResult;
	}

	private boolean validateReferenceEditing(PropertiesEditingEvent editingEvent, EObject editedEObject, EStructuralFeature feature, Object currentValue) {
		if (feature.isMany()) {
			switch (editingEvent.getEventType()) {
			case PropertiesEditingEvent.ADD:
				// Note: if we're in the case of a containment, the value is
				// null.
				return !((Collection<?>) currentValue).contains(request.getValue());
			case PropertiesEditingEvent.ADD_MANY:
				// TODO: need specifications
				return true;
			case PropertiesEditingEvent.REMOVE:
				return ((Collection<?>) currentValue).contains(editingEvent.getOldValue());
			case PropertiesEditingEvent.REMOVE_MANY:
				// TODO: need specifications
				return true;
			case PropertiesEditingEvent.MOVE:
				Object newValue = editingEvent.getNewValue();
				if (newValue instanceof Integer) {
					Integer newIndex = (Integer) newValue;
					return newIndex >= 0 && newIndex < ((List<?>) editedEObject.eGet(feature)).size();
				}
				break;
			case PropertiesEditingEvent.EDIT:
				return true;
			default:
				return false;
			}
		} else {
			if (editingEvent.getEventType() == PropertiesEditingEvent.SET) {
				return (currentValue != request.getValue());
			} else if (editingEvent.getEventType() == PropertiesEditingEvent.UNSET) {
				return true;
			}
		}
		return false;
	}

}
