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

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest.ProcessingKind;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequestFactory;
import org.eclipse.emf.eef.runtime.util.EEFEditingService;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class EReferenceEditingPolicyRequestFactory implements EditingPolicyRequestFactory {

	private EMFServiceProvider emfServiceProvider;
	private EEFEditingServiceProvider eefEditingServiceProvider;

	/**
	 * @param emfServiceProvider
	 *            the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * @param eefEditingServiceProvider
	 *            the eefEditingServiceProvider to set
	 */
	public void setEEFEditingServiceProvider(EEFEditingServiceProvider eefEditingServiceProvider) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext editingContext) {
		if (editingContext instanceof SemanticPropertiesEditingContext) {
			EEFEditingService editingService = eefEditingServiceProvider.getEditingService(editingContext.getEditingComponent().getEObject());
			return editingService.isAddingInContainmentEvent(editingContext, ((SemanticPropertiesEditingContext) editingContext).getEditingEvent());
		}
		return false;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyRequestFactory#createProcessing(org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext)
	 */
	public EditingPolicyRequest createProcessing(SemanticPropertiesEditingContext editingContext) {
		EditingPolicyRequest.Builder requestBuilder = new EditingPolicyRequest.Builder();
		requestBuilder.setTarget(editingContext.getEditingComponent().getEObject());
		requestBuilder.setEditingContext(editingContext);
		PropertyBinding propertyBinding = editingContext.getEditingComponent().getBinding().propertyBinding(editingContext.getEditingEvent().getAffectedEditor(), editingContext.getOptions().autowire());
		if (propertyBinding instanceof EStructuralFeatureBinding) {
			EReference feature = (EReference) ((EStructuralFeatureBinding) propertyBinding).getFeature();
			requestBuilder.setValue(defineEObjectToSet(editingContext, feature));
			if (feature.isMany()) {
				requestBuilder.setProcessingKind(ProcessingKind.ADD);
			} else {
				requestBuilder.setProcessingKind(ProcessingKind.SET);
			}
		} else {
			// TODO: TBD but I think it will be a little tricky in this case
			// :)
		}
		return requestBuilder.build();
	}

	/**
	 * @param editedReference
	 *            {@link EReference} to edit.
	 * @return the {@link EObject} to set in thce given {@link EReference}.
	 */
	private EObject defineEObjectToSet(PropertiesEditingContext editingContext, EReference editedReference) {
		EObject createdEObject = null;
		if (editedReference.getEReferenceType() != null && !editedReference.getEReferenceType().isAbstract()) {
			createdEObject = EcoreUtil.create(editedReference.getEReferenceType());
		} else {
			EMFService emfService = emfServiceProvider.getEMFService(editingContext.getEditingComponent().getEObject().eClass().getEPackage());
			Collection<EClass> listOfInstanciableType = emfService.listOfInstanciableType(null, editingContext.getEditingComponent().getEObject(), editedReference);
			if (listOfInstanciableType.size() > 0) {
				createdEObject = EcoreUtil.create(listOfInstanciableType.iterator().next());
			} else {
				// TODO: logging ?
			}
		}
		return createdEObject;
	}
}
