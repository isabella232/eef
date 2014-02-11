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

import org.eclipse.emf.common.util.BasicEList;
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
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.internal.policies.editingstrategy.EditingStrategyNotFoundException;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.query.JavaBody;
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
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#featureFromEditor(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, java.lang.Object)
	 */
	public EStructuralFeature featureFromEditor(PropertiesEditingContext editingContext, Object editor) {
		EStructuralFeature associatedFeature = null;
		EObject editedObject = editingContext.getEditingComponent().getEObject();
		PropertyBinding propertyBinding = editingContext.getEditingComponent().getBinding().propertyBinding(editor, editingContext.getOptions().autowire());
		if (propertyBinding instanceof EStructuralFeatureBinding) {
			associatedFeature = ((EStructuralFeatureBinding) propertyBinding).getFeature();
		} else if (propertyBinding == null && editingContext.getOptions().autowire()) {
			EClassBinding binding = editingContext.getEditingComponent().getBinding();
			if (editor instanceof String) {
				associatedFeature = binding.getEClass().getEStructuralFeature((String)editor);
			}
			if (editor instanceof EObject) {
				// Here we don't have an PropertyBinding to help us. We check if the editor is an EObject (for instance an ElementEditor)
				// We looking for an "name" structural feature and if this feature is type of String, we try to associate this name
				// with a structural feature of the handled EClass. For instance if an ElementEditor (which has a "name" feature) is named
				// "active" and the current EClass has a feature named "active", we return this feature.
				EStructuralFeature nameFeature = ((EObject) editor).eClass().getEStructuralFeature("name");
				if (nameFeature != null && "java.lang.String".equals(nameFeature.getEType().getInstanceClassName())) {
					associatedFeature = binding.getEClass().getEStructuralFeature((String)((EObject) editor).eGet(nameFeature));
				}
			}
		}
		if (associatedFeature != null && !editedObject.eClass().getEAllStructuralFeatures().contains(associatedFeature)) {
			associatedFeature = emfServiceProvider.getEMFService(editedObject.eClass().getEPackage()).mapFeature(editedObject, associatedFeature);
		}
		return associatedFeature;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#getValue(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.EObject, java.lang.Object)
	 */
	public Object getValue(final PropertiesEditingContext editingContext, final EObject target, Object editor) {
		EEFEditingStrategy<Object> strategy = new EEFEditingStrategy<Object>(editingContext, editor, EditingModelPackage.Literals.PROPERTY_BINDING__GETTER) {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.query.JavaBody)
			 */
			@Override
			protected Object processByAccessor(JavaBody<Void> accessor) {
				return accessor.invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), target, new BasicEList<Object>());
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
			 */
			@Override
			protected Object processByFeature(EStructuralFeature feature) {
				return target.eGet(feature);
			}
			
		};			
		try {
			return strategy.process();
		} catch (EditingStrategyNotFoundException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#getValueOfSubbinding(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.EObject, org.eclipse.emf.eef.runtime.editingModel.PropertyBinding)
	 */
	public Object getValueOfSubbinding(PropertiesEditingContext editingContext, EObject target, PropertyBinding propertyBinding) {
		if (propertyBinding.getGetter() != null) {
			return propertyBinding.getGetter().invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), target, new BasicEList<Object>());
		} else {
			if (propertyBinding instanceof EStructuralFeatureBinding) {
				EStructuralFeature feature = ((EStructuralFeatureBinding) propertyBinding).getFeature();
				if (!target.eClass().getEAllStructuralFeatures().contains(feature)) {
					feature = emfServiceProvider.getEMFService(target.eClass().getEPackage()).mapFeature(target, feature);
				}
				return target.eGet(feature);
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#getChoiceOfValue(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.EObject, java.lang.Object)
	 */
	public Object getChoiceOfValue(final PropertiesEditingContext editingContext, final EObject target, final Object editor) {
		EEFEditingStrategy<Object> strategy = new EEFEditingStrategy<Object>(editingContext, editor, EditingModelPackage.Literals.PROPERTY_BINDING__VALUE_PROVIDER) {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.query.JavaBody)
			 */
			@Override
			protected Object processByAccessor(JavaBody<Void> accessor) {
				return accessor.invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), target, new BasicEList<Object>());
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
			 */
			@Override
			protected Object processByFeature(EStructuralFeature feature) {
				EMFService emfService = emfServiceProvider.getEMFService(target.eClass().getEPackage());
				return emfService.choiceOfValues(editingContext.getAdapterFactory(), target, feature);
			}
			
		};
		try {
			return strategy.process();
		} catch (EditingStrategyNotFoundException e) {
			return null;
		}
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
		PropertyBinding propertyBinding = context.getEditingComponent().getBinding().propertyBinding(editingEvent.getAffectedEditor(), context.getOptions().autowire());
		EStructuralFeature feature;
		if (propertyBinding instanceof EStructuralFeatureBinding) {
			feature = ((EStructuralFeatureBinding) propertyBinding).getFeature();
		} else {
			feature = null;
		}
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
				revelantFeature = EditingModelPackage.Literals.ESTRUCTURAL_FEATURE_BINDING__FEATURE;
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
