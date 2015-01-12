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
package org.eclipse.emf.eef.editor.internal.filters;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eef.editor.internal.filters.utils.EStructuralFeatureBindingChoiceFilter;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.ui.swt.util.EEFViewerFilterInvocationParameters;
import org.eclipse.emf.eef.runtime.util.EEFInvocationParameters;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertyBindingFilter {

	/**
	 * @param currentComponent
	 * @param element
	 * @return
	 */
	public boolean bindableFeature(EEFInvocationParameters parameters) {
		if (parameters instanceof EEFViewerFilterInvocationParameters) {
			Object element = ((EEFViewerFilterInvocationParameters) parameters).getElement();
			EObject editedBinding = computeContext(parameters.getEditingContext().getEditingComponent());
			if (editedBinding != null) {
				EObject target = null;
				if (editedBinding instanceof EClassBinding) { 
					target = ((EClassBinding) editedBinding).getEClass();
				} else if (editedBinding instanceof EStructuralFeatureBinding) {
					target = ((EStructuralFeatureBinding) editedBinding).getFeature();
				} else {
					// What ????
				}
				if (target != null) {
					EStructuralFeatureBindingChoiceFilter filter = new EStructuralFeatureBindingChoiceFilter(editedBinding);
					Collection<?> reachableObjects = filter.getReachableObjects(target, EcorePackage.Literals.ECLASS__EALL_STRUCTURAL_FEATURES);
					Collection<?> choiceOfValues = filter.filterPropertyBindingChoiceOfValues(reachableObjects);
					if (choiceOfValues.contains(element)){
						return true;
					} else {
						return computeAncestors(choiceOfValues).contains(element);
					}
				}
			}
		}
		return true;
	}

	private EObject computeContext(PropertiesEditingComponent currentComponent) {
		EObject eObject = currentComponent.getEObject();
		if (eObject instanceof PropertyBinding) {
			PropertyBinding currentBinding = (PropertyBinding) eObject;
			if (currentBinding.eContainer() != null) {
				return currentBinding.eContainer();
			} else {
				PropertiesEditingContext parentContext = currentComponent.getEditingContext().getParentContext();
				while (parentContext != null) {
					EObject eObject2 = parentContext.getEditingComponent().getEObject();
					if (eObject2 instanceof EClassBinding || ((eObject2 instanceof PropertyBinding) && (eObject != eObject2))) {
						return eObject2;
					}
				}
			}
		}
		return null;
	}

	private Set<Notifier> computeAncestors(Collection<?> values) {
		Set<Notifier> result = Sets.newHashSet();
		for (Object element : values) {
			if (element instanceof Notifier) {
				result.addAll(computeAncestors((Notifier)element));
			}
		}
		return result;
	}
	
	private List<Notifier> computeAncestors(Notifier root) {
		List<Notifier> result = Lists.newArrayList();
		Notifier parent = root;
		while (parent != null) {
			result.add(parent);
			if (parent instanceof EObject) {
				EObject parentEObject = (EObject) parent;
				if (parentEObject.eContainer() == null) {
					parent = parentEObject.eResource();
				} else {
					parent = parentEObject.eContainer();
				}
			} else if (parent instanceof Resource) {
				parent = ((Resource) parent).getResourceSet();
			} else if (parent instanceof ResourceSet) {
				parent = null;
			}
		}
		return result;
	}
}
