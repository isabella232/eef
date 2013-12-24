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
package org.eclipse.emf.eef.runtime.internal.util;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.util.EEFEditingService;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;

import com.google.common.collect.Sets;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFEditingServiceImpl implements EEFEditingService, DefaultService {

	private EMFServiceProvider emfServiceProvider;
	
	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(EObject element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#isReflectiveBinding(org.eclipse.emf.eef.runtime.editingModel.EClassBinding)
	 */
	public boolean isReflectiveBinding(EClassBinding binding) {
		if (binding != null) {
			EAnnotation eAnnotation = binding.getEAnnotation(PropertiesEditingComponent.EEF_EANNOTATION_SOURCE);
			if (eAnnotation != null && eAnnotation.getDetails().get(PropertiesEditingComponent.BINDING_KIND_KIND) == PropertiesEditingComponent.REFLECTIVE_BINDING_KIND) {
				return true;
			}
		}
		return false;
	}
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#isAddingInContainmentEvent(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public boolean isAddingInContainmentEvent(PropertiesEditingContext context, PropertiesEditingEvent editingEvent) {
		EStructuralFeature feature = context.getEditingComponent().getBinding().feature(editingEvent.getAffectedEditor(), context.getOptions().autowire());
		return feature != null 
				&& feature instanceof EReference 
				&& ((EReference)feature).isContainment() 
				&& editingEvent.getNewValue() == null 
				&& (
						((editingEvent.getEventType() == PropertiesEditingEvent.ADD) && feature.isMany())
						|| ((editingEvent.getEventType() == PropertiesEditingEvent.SET) && !feature.isMany())
					);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#getReferenceToEdit(org.org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext)
	 */
	public EReference getReferenceToEdit(SemanticPropertiesEditingContext editingContext) {
		EStructuralFeature feature = editingContext.getEditingComponent().getBinding().feature(editingContext.getEditingEvent().getAffectedEditor(), editingContext.getOptions().autowire());
		EMFService service = emfServiceProvider.getEMFService(editingContext.getEditingComponent().getEObject().eClass().getEPackage());
		return (EReference) service.mapFeature(editingContext.getEditingComponent().getEObject(), feature);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#canBeReferencedByEditingModel(org.eclipse.emf.ecore.EObject)
	 */
	public boolean canBeReferencedByEditingModel(EObject target) {
		return target instanceof EPackage || target instanceof EClass || target instanceof EStructuralFeature;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#referencingEEFElement(org.eclipse.emf.ecore.EObject)
	 */
	public Collection<EObject> referencingEEFElement(EObject target) {
		Set<EObject> result = Sets.newHashSet();
		// Only Ecore elements can be processed and only if they are loaded in a ResourceSet.
		if (canBeReferencedByEditingModel(target) && target.eResource() != null && target.eResource().getResourceSet() != null) {
			//TODO: can be optimized
			Collection<Setting> usages = UsageCrossReferencer.find(target, target.eResource().getResourceSet());
			EStructuralFeature revelantFeature = null;
			if (target instanceof EPackage) {
				revelantFeature = EditingModelPackage.Literals.PROPERTIES_EDITING_MODEL__INVOLVED_MODELS;
			} else if (target instanceof EClass) {
				revelantFeature = EditingModelPackage.Literals.ECLASS_BINDING__ECLASS;
			} else if (target instanceof EStructuralFeature) {
				revelantFeature = EditingModelPackage.Literals.PROPERTY_BINDING__FEATURE;
			}
			if (revelantFeature != null) {
				for (Setting setting : usages) {
					if (setting.getEStructuralFeature() == revelantFeature) {
						result.add(setting.getEObject());
					}
				}
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#searchEditingDomain(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public EditingDomain searchEditingDomain(PropertiesEditingContext editingContext) {
		PropertiesEditingContext currentEditingContext = editingContext;
		while (currentEditingContext != null) {
			if (currentEditingContext instanceof DomainAwarePropertiesEditingContext) {
				return ((DomainAwarePropertiesEditingContext) currentEditingContext).getEditingDomain();
			}
			currentEditingContext = currentEditingContext.getParentContext();
		}
		return null;
	}

}
