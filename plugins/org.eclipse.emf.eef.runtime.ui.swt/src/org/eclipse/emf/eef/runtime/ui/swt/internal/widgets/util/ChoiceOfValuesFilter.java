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
package org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.util;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.util.EEFEditingService;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ChoiceOfValuesFilter extends ViewerFilter {

	private final EEFEditingServiceProvider eefEditingServiceProvider;
	private final PropertiesEditingContext editingContext;
	private final EObject editedElement;
	private final Object editor;
	private final SelectionMode mode;

	private EStructuralFeature featureForCache;
	private EObject elementForCache;

	private Collection<?> choiceOfValues = null;
	private Collection<Notifier> intermediateChoices = Lists.newArrayList();

	/**
	 * @param eefEditingServiceProvider
	 * @param editingContext
	 * @param editedElement
	 * @param editor
	 * @param mode
	 */
	public ChoiceOfValuesFilter(EEFEditingServiceProvider eefEditingServiceProvider, PropertiesEditingContext editingContext, EObject editedElement, Object editor, SelectionMode mode) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.editingContext = editingContext;
		this.editedElement = editedElement;
		this.editor = editor;
		this.mode = mode;
	}

	private void populateIntermediateChoices(Collection<Notifier> intermediateChoices, EObject object) {
		EObject container = object.eContainer();
		while (container != null) {
			intermediateChoices.add(container);
			container = container.eContainer();
		}
		if (object.eResource() != null) {
			intermediateChoices.add(object.eResource());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object) TODO: need cache.
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (!editedElement.equals(elementForCache)) {
			elementForCache = editedElement;
			EEFEditingService editingService = eefEditingServiceProvider.getEditingService(editedElement);
			EStructuralFeature feature = editingService.featureFromEditor(editingContext, editor);
			if (!feature.equals(featureForCache)) {
				featureForCache = feature;
				Object serviceResult = editingService.getChoiceOfValue(editingContext, editedElement, editor);
				if (serviceResult instanceof Collection<?>) {
					choiceOfValues = (Collection<?>) serviceResult;
				} else if (serviceResult != null) {
					choiceOfValues = Lists.newArrayList(serviceResult);
				} else {
					choiceOfValues = Lists.newArrayList();
				}
				for (Object object : choiceOfValues) {
					if (object instanceof EObject) {
						populateIntermediateChoices(intermediateChoices, (EObject) object);
					}
				}
			}
		}
		return choiceOfValues.contains(element) || ((mode == SelectionMode.TREE) && (intermediateChoices.contains(element)));
	}
}
