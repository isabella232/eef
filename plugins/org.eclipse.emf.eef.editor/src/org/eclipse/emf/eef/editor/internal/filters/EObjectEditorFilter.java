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

import java.util.List;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.View;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectEditorFilter {

	/**
	 * @param currentComponent
	 * @param element
	 * @return
	 */
	public boolean bindableEditor(PropertiesEditingComponent currentComponent, Notifier element) {
		EClassBinding editedBinding = getEditedBinding(currentComponent);
		if (editedBinding != null && editedBinding.getEClass() != null) {
			
			if (computeAncestors(editedBinding).contains(element)) {
				return true;
			} else if (element instanceof EObject && isAnElementOfBindedViews(editedBinding, (EObject) element)){
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
	
	
	private EClassBinding getEditedBinding(PropertiesEditingComponent editingComponent) {
		PropertiesEditingContext context = editingComponent.getEditingContext();
		while (context != null) {
			if (context.getEditingComponent().getEObject() instanceof EClassBinding) {
				return (EClassBinding) context.getEditingComponent().getEObject();
			} else {
				EClassBinding bindingFromAncestors = findBindingInAncestors(context.getEditingComponent().getEObject());
				if (bindingFromAncestors != null) {
					return bindingFromAncestors;
				} else {
					context = context.getParentContext();
				}
			}
		}
		return null;
	}
	
	private EClassBinding findBindingInAncestors(EObject root) {
		EObject parent = root;
		while (parent != null) {
			if (parent instanceof EClassBinding) {
				return (EClassBinding) parent;
			} else {
				parent = parent.eContainer();
			}
		}
		return null;
	}
	
	private List<Notifier> computeAncestors(EClassBinding root) {
		List<Notifier> result = Lists.newArrayList();
		for (View view : root.getViews()) {
			if (view instanceof EObjectView) { 
				Notifier parent = ((EObjectView) view).getDefinition();
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
			}
		}
		return result;
	}
	
	private boolean isAnElementOfBindedViews(EClassBinding binding, EObject element) {
		for (View view : binding.getViews()) {
			if (view instanceof EObjectView) {
				TreeIterator<EObject> eAllContents = ((EObjectView) view).getDefinition().eAllContents();
				while (eAllContents.hasNext()) {
					if (eAllContents.next().equals(element)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
