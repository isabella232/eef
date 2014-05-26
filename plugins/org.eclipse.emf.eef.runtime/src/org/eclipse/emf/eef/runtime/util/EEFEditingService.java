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
package org.eclipse.emf.eef.runtime.util;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.services.EEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public interface EEFEditingService extends EEFService<EObject> {

	/**
	 * Try to find and returns a {@link EStructuralFeature} edited by the given
	 * editor.
	 * 
	 * @param editingContext
	 *            the current {@link PropertiesEditingContext}.
	 * @param editor
	 *            editing element
	 * @return a corresponding {@link EStructuralFeature} if possible
	 *         <code>null</code> otherwise.
	 */
	EStructuralFeature featureFromEditor(PropertiesEditingContext editingContext, Object editor);

	/**
	 * Returns the value of the editor for the given EObject.
	 * 
	 * @param editingContext
	 *            current {@link PropertiesEditingContext}.
	 * @param target
	 *            EObject to inspect.
	 * @param editor
	 *            the concerning editor.
	 * @return the value for the given editor.
	 */
	Object getValue(PropertiesEditingContext editingContext, EObject target, Object editor);

	/**
	 * Returns the value of the given sub-{@link PropertyBinding} for the given
	 * EObject.
	 * 
	 * @param editingContext
	 *            current {@link PropertiesEditingContext}.
	 * @param target
	 *            EObject to inspect.
	 * @param propertyBinding
	 *            the concerning {@link PropertyBinding}.
	 * @return the value for the given {@link PropertyBinding}.
	 */
	Object getValueOfSubbinding(PropertiesEditingContext editingContext, EObject target, PropertyBinding propertyBinding);

	/**
	 * Returns the choice of value for the property binding for the given
	 * EObject.
	 * 
	 * @param editingContext
	 *            current {@link PropertiesEditingContext}.
	 * @param target
	 *            EObject to inspect.
	 * @param editor
	 *            the concerning view.
	 * @return the choice of value for the given {@link PropertyBinding}.
	 */
	Object getChoiceOfValue(PropertiesEditingContext editingContext, EObject target, Object editor);

	/**
	 * Defines if the given binding is a {@link EClassBinding} generated for a
	 * view rendering need or not.
	 * 
	 * @param binding
	 *            the binding to test (can be null)
	 * @return <code>true</code> if the given {@link EClassBinding} is a
	 *         reflective binding.
	 */
	boolean isReflectiveBinding(EClassBinding binding);

	/**
	 * Defines if a {@link PropertiesEditingEvent} must generate a
	 * {@link EReferenceEditingPolicy}. This means:
	 * <ul>
	 * <li>The edited feature is a containment {@link EReference}</li>
	 * <li>There is no newValue defined</li>
	 * <li>The event kind is a 'ADD'</li>
	 * </ul>
	 * 
	 * @param context
	 *            parent {@link PropertiesEditingContext}.
	 * @param editingEvent
	 *            the {@link PropertiesEditingEvent} to process.
	 * @return <code>true</code> if the event must generate
	 *         {@link EReferenceEditingPolicy}.
	 */
	boolean isAddingInContainmentEvent(PropertiesEditingContext context, PropertiesEditingEvent editingEvent);

	/**
	 * Defines if the given object can be referenced in an EEF editing model.
	 * 
	 * @param target
	 *            the object to test.
	 * @return <code>true</code> if the given object can be edited with EEF.
	 */
	boolean canBeReferencedByEditingModel(EObject target);

	/**
	 * Searches EEF EditingModels elements
	 * (PropertiesEditingModel,EClassBinding, ...) referencing the given
	 * element.
	 * 
	 * @param target
	 *            the referenced element
	 * @return a collection of element referencing the given element.
	 */
	Collection<EObject> referencingEEFElement(EObject target);

	/**
	 * Searches for an {@link EditingDomain} in the hierarchy of the given
	 * {@link PropertiesEditingContext}.
	 * 
	 * @param editingContext
	 *            the start point for the research.
	 * @return the found {@link EditingDomain} if exists <code>null</code>
	 *         otherwise.
	 */
	EditingDomain searchEditingDomain(PropertiesEditingContext editingContext);

	/**
	 * @param context
	 *            {@link PropertiesEditingContext}
	 * @param notification
	 *            {@link Notification}
	 * @return if the binding customizer can be applied on EClassBinding
	 *         properties bindings
	 */
	boolean isAffectingEventDueToCustomization(PropertiesEditingContext context, Notification notification);

	/**
	 * @param context
	 *            {@link PropertiesEditingContext}
	 * @param binding
	 *            {@link PropertyBinding}
	 * @param notification
	 *            {@link Notification}
	 * @return if the binding customizer can be applied on PropertyBinding
	 * 
	 */
	boolean isAffectingEventDueToCustomization(PropertiesEditingContext context, PropertyBinding binding, Notification notification);

	/**
	 * @param propertyBinding
	 *            PropertyBinding
	 * @param bindingSettings
	 *            EEFBindingSettings
	 * @return EEFModifierCustomizer on propertyBinding, NullModifierCustomizer
	 *         if does not exist
	 */
	EEFModifierCustomizer<Boolean> getEventFilterFromCustomizer(PropertyBinding propertyBinding, EEFBindingSettings<?> bindingSettings);

	/**
	 * @param editingComponent
	 *            PropertiesEditingComponent
	 * @param notification
	 *            Notification
	 * @return editors list affected by the notification in editingComponent
	 */
	Collection<Object> affectedEditors(PropertiesEditingComponent editingComponent, Notification notification);

}
