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
package org.eclipse.emf.eef.editor.internal.widgets.util;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SelectionMEEFVContentProvider implements ITreeContentProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		List<EObject> result = Lists.newArrayList();
		if (inputElement instanceof EClass) {
			EClass selectedClass = (EClass) inputElement;
			// Let search in the class ResourceSet, if exists (but it should exist!), all the binding referencing this class.
			Collection<Setting> find = UsageCrossReferencer.find(selectedClass, selectedClass.eResource().getResourceSet());
			for (Setting setting : find) {
				if (EditingModelPackage.Literals.ECLASS_BINDING__ECLASS == setting.getEStructuralFeature()) {
					result.add(setting.getEObject());
				}
			}
		} else if (inputElement instanceof EStructuralFeature) {
			EStructuralFeature selectedFeature = (EStructuralFeature)inputElement;
			// Let search in the class ResourceSet, if exists (but it should exist!), all the binding referencing this propertyBinding.
			Collection<Setting> find = UsageCrossReferencer.find(selectedFeature, selectedFeature.eResource().getResourceSet());
			for (Setting setting : find) {
				if (EditingModelPackage.Literals.PROPERTY_BINDING__FEATURE == setting.getEStructuralFeature()) {
					result.add(setting.getEObject());
				}
			}
		}
		return result.toArray();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object parentElement) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element) {
		return false;
	}

}
