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

import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.EEFRuntime;
import org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.binding.PropertyBindingCustomizer;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectEditor;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.Editor;
import org.eclipse.emf.eef.runtime.editingModel.JavaEditor;
import org.eclipse.emf.eef.runtime.editingModel.JavaView;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.internal.binding.NullModifierCustomizer;
import org.eclipse.emf.eef.runtime.internal.policies.editingstrategy.EditingStrategyNotFoundException;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.util.EEFEditingService;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class EEFEditingServiceImpl implements EEFEditingService, DefaultService {

	private EMFServiceProvider emfServiceProvider;

	/**
	 * @param emfServiceProvider
	 *            the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(EObject element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#featureFromEditor(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext,
	 *      java.lang.Object)
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
				associatedFeature = binding.getEClass().getEStructuralFeature((String) editor);
			}
			if (editor instanceof EObject) {
				// Here we don't have an PropertyBinding to help us. We check if
				// the editor is an EObject (for instance an ElementEditor)
				// We looking for an "name" structural feature and if this
				// feature is type of String, we try to associate this name
				// with a structural feature of the handled EClass. For instance
				// if an ElementEditor (which has a "name" feature) is named
				// "active" and the current EClass has a feature named "active",
				// we return this feature.
				EStructuralFeature nameFeature = ((EObject) editor).eClass().getEStructuralFeature("name");
				if (nameFeature != null && "java.lang.String".equals(nameFeature.getEType().getInstanceClassName())) {
					associatedFeature = binding.getEClass().getEStructuralFeature((String) ((EObject) editor).eGet(nameFeature));
				}
			}
		}
		if (associatedFeature != null && !editedObject.eClass().getEAllStructuralFeatures().contains(associatedFeature)) {
			associatedFeature = emfServiceProvider.getEMFService(editedObject.eClass().getEPackage()).mapFeature(editedObject.eClass(), associatedFeature);
		}
		return associatedFeature;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#getValue(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext,
	 *      org.eclipse.emf.ecore.EObject, java.lang.Object)
	 */
	public Object getValue(final PropertiesEditingContext editingContext, final EObject target, Object editor) {
		EEFEditingStrategy<Object, Object> strategy = new EEFEditingStrategy<Object, Object>(editingContext, editor, PropertyBindingCustomizer.GETTER) {

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
			 */
			@Override
			protected Object processByAccessor(EEFModifierCustomizer<Object> modifierCustomizer) {
				return modifierCustomizer.execute(new EEFInvocationParametersImpl(editingContext));
			}

			/**
			 * {@inheritDoc}
			 * 
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
	 * 
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#getValueOfSubbinding(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext,
	 *      org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.eef.runtime.editingModel.PropertyBinding)
	 */
	public Object getValueOfSubbinding(final PropertiesEditingContext editingContext, final EObject target, PropertyBinding propertyBinding) {
		EEFEditingStrategy<Object, Object> strategy = new EEFEditingStrategy<Object, Object>(editingContext, propertyBinding, PropertyBindingCustomizer.VALUES_PROVIDER) {

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
			 */
			@Override
			protected Object processByAccessor(EEFModifierCustomizer<Object> modifierCustomizer) {
				return modifierCustomizer.execute(new EEFInvocationParametersImpl(editingContext));
			}

			/**
			 * {@inheritDoc}
			 * 
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
	 * 
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#getChoiceOfValue(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext,
	 *      org.eclipse.emf.ecore.EObject, java.lang.Object)
	 */
	public Object getChoiceOfValue(final PropertiesEditingContext editingContext, final EObject target, final Object editor) {
		EEFEditingStrategy<Object, Object> strategy = new EEFEditingStrategy<Object, Object>(editingContext, editor, PropertyBindingCustomizer.VALUES_PROVIDER) {

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
			 */
			@Override
			protected Object processByAccessor(EEFModifierCustomizer<Object> modifierCustomizer) {
				return modifierCustomizer.execute(new EEFInvocationParametersImpl(editingContext));
			}

			/**
			 * {@inheritDoc}
			 * 
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
	 * 
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
	 * 
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#isAddingInContainmentEvent(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext,
	 *      org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public boolean isAddingInContainmentEvent(PropertiesEditingContext context, PropertiesEditingEvent editingEvent) {
		if (editingEvent.getAffectedEditor() != null) {
			PropertyBinding propertyBinding = context.getEditingComponent().getBinding().propertyBinding(editingEvent.getAffectedEditor(), context.getOptions().autowire());
			EStructuralFeature feature;
			if (propertyBinding instanceof EStructuralFeatureBinding) {
				feature = ((EStructuralFeatureBinding) propertyBinding).getFeature();
			} else {
				feature = null;
			}
			return feature != null && feature instanceof EReference && ((EReference) feature).isContainment() && editingEvent.getNewValue() == null
					&& (((editingEvent.getEventType() == PropertiesEditingEvent.ADD) && feature.isMany()) || ((editingEvent.getEventType() == PropertiesEditingEvent.SET) && !feature.isMany()));
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#canBeReferencedByEditingModel(org.eclipse.emf.ecore.EObject)
	 */
	public boolean canBeReferencedByEditingModel(EObject target) {
		return target instanceof EPackage || target instanceof EClass || target instanceof EStructuralFeature;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#referencingEEFElement(org.eclipse.emf.ecore.EObject)
	 */
	public Collection<EObject> referencingEEFElement(EObject target) {
		Set<EObject> result = Sets.newHashSet();
		// Only Ecore elements can be processed and only if they are loaded in a
		// ResourceSet.
		if (canBeReferencedByEditingModel(target) && target.eResource() != null && target.eResource().getResourceSet() != null) {
			// TODO: can be optimized
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
	 * 
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

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#isAffectingEventDueToCustomization(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext,
	 *      org.eclipse.emf.common.notify.Notification)
	 */
	public boolean isAffectingEventDueToCustomization(PropertiesEditingContext context, Notification notification) {
		for (PropertyBinding propertyBinding : context.getEditingComponent().getBinding().getPropertyBindings()) {
			if (isAffectingEventDueToCustomization(context, propertyBinding, notification)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#isAffectingEventDueToCustomization(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext,
	 *      org.eclipse.emf.eef.runtime.editingModel.PropertyBinding,
	 *      org.eclipse.emf.common.notify.Notification)
	 */
	public boolean isAffectingEventDueToCustomization(PropertiesEditingContext context, PropertyBinding propertyBinding, Notification notification) {
		if (!Strings.isNullOrEmpty(propertyBinding.getBindingCustomizer())) {
			EEFModifierCustomizer<Boolean> eventFilter = getEventFilterFromCustomizer(propertyBinding, context.getEditingComponent().getBindingSettings());
			if (!(eventFilter instanceof NullModifierCustomizer)) {
				if (eventFilter.execute(new EEFNotificationFilterInvocationParametersImpl(context, notification))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#getEventFilterFromCustomizer(org.eclipse.emf.eef.runtime.editingModel.PropertyBinding,
	 *      org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings)
	 */
	@SuppressWarnings("unchecked")
	public EEFModifierCustomizer<Boolean> getEventFilterFromCustomizer(PropertyBinding propertyBinding, EEFBindingSettings<?> bindingSettings) {
		EEFModifierCustomizer<Boolean> result = null;
		String bindingCustomizer = propertyBinding.getBindingCustomizer();
		if (!Strings.isNullOrEmpty(bindingCustomizer)) {
			ClassLoader classLoader = bindingSettings.getClass().getClassLoader();
			try {
				Class<PropertyBindingCustomizer> loadClass = (Class<PropertyBindingCustomizer>) classLoader.loadClass(bindingCustomizer);
				PropertyBindingCustomizer newInstance = loadClass.newInstance();
				result = newInstance.getEventFilter();
			} catch (ClassNotFoundException e) {
				result = new NullModifierCustomizer<Boolean>();
				EEFRuntime.getPlugin().getLog().log(new Status(Status.ERROR, EEFRuntime.PLUGIN_ID, "Error when loading binding customizer class : " + bindingCustomizer, e));
			} catch (ClassCastException e) {
				result = new NullModifierCustomizer<Boolean>();
				EEFRuntime.getPlugin().getLog().log(new Status(Status.ERROR, EEFRuntime.PLUGIN_ID, "Error when loading binding customizer class : " + bindingCustomizer, e));
			} catch (InstantiationException e) {
				result = new NullModifierCustomizer<Boolean>();
				EEFRuntime.getPlugin().getLog().log(new Status(Status.ERROR, EEFRuntime.PLUGIN_ID, "Error when loading binding customizer class : " + bindingCustomizer, e));
			} catch (IllegalAccessException e) {
				result = new NullModifierCustomizer<Boolean>();
				EEFRuntime.getPlugin().getLog().log(new Status(Status.ERROR, EEFRuntime.PLUGIN_ID, "Error when loading binding customizer class : " + bindingCustomizer, e));
			}
		}
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.util.EEFEditingService#affectedEditors(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent,
	 *      org.eclipse.emf.common.notify.Notification)
	 */
	public Collection<Object> affectedEditors(PropertiesEditingComponent editingComponent, Notification notification) {
		Collection<Object> result = Lists.newArrayList();
		EClassBinding eclassBinding = editingComponent.getBinding();
		EStructuralFeature structuralFeature = (EStructuralFeature) notification.getFeature();
		for (PropertyBinding binding : eclassBinding.getPropertyBindings()) {
			if (isBindedFeature(editingComponent, binding, structuralFeature) || isEditorAffectedByNotificationDueToCustomization(editingComponent, binding, notification)) {
				Editor editor = binding.getEditor();
				if (editor instanceof EObjectEditor) {
					result.add(((EObjectEditor) editor).getDefinition());
				} else if (editor instanceof JavaEditor) {
					result.add(((JavaEditor) editor).getDefinition());
				} else {
					result.add(editor);
				}
			}
		}
		if (editingComponent.getEditingContext().getOptions().autowire() && result.isEmpty()) {
			for (Object view : eclassBinding.getViews()) {
				if (view instanceof EObjectView) {
					TreeIterator<EObject> eAllContents = ((EObjectView) view).getDefinition().eAllContents();
					while (eAllContents.hasNext()) {
						EObject next = eAllContents.next();
						EStructuralFeature nameFeature = next.eClass().getEStructuralFeature("name");
						if (nameFeature != null) {
							Object name = next.eGet(nameFeature);
							if (name instanceof String && name.equals(structuralFeature.getName())) {
								result.add(next);
							}
						}
					}
				}
			}
			if (Collections2.filter(eclassBinding.getViews(), new Predicate<Object>() {

				/**
				 * {@inheritDoc}
				 * 
				 * @see com.google.common.base.Predicate#apply(java.lang.Object)
				 */
				public boolean apply(Object input) {
					return input instanceof JavaView;
				}

			}).size() > 0 && structuralFeature != null) {
				result.add(structuralFeature.getName());
			}
		}
		return result;
	}

	/**
	 * @param editingComponent
	 *            PropertiesEditingComponent
	 * @param binding
	 *            PropertyBinding
	 * @param structuralFeature
	 *            EStructuralFeature
	 * @return if the binding binds the feature
	 */
	private boolean isBindedFeature(PropertiesEditingComponent editingComponent, PropertyBinding binding, EStructuralFeature structuralFeature) {
		EMFService emfService = emfServiceProvider.getEMFService(editingComponent.getEObject().eClass().getEPackage());
		return binding instanceof EStructuralFeatureBinding && structuralFeature != null && emfService.equals(((EStructuralFeatureBinding) binding).getFeature(), structuralFeature);
	}

	/**
	 * @param editingComponent
	 *            PropertiesEditingComponent
	 * @param binding
	 *            PropertyBinding
	 * @param notification
	 *            Notification
	 * @return for customized editors, if it is affected by the notification.
	 */
	private boolean isEditorAffectedByNotificationDueToCustomization(PropertiesEditingComponent editingComponent, PropertyBinding binding, Notification notification) {
		return !Strings.isNullOrEmpty(binding.getBindingCustomizer()) && isAffectingEventDueToCustomization(editingComponent.getEditingContext(), binding, notification);
	}

}
