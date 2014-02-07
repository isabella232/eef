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
package org.eclipse.emf.eef.editor.internal.filters.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EStructuralFeatureBindingChoiceFilter {

	private EObject context;
	
	/**
	 * @param context
	 */
	public EStructuralFeatureBindingChoiceFilter(EObject context) {
		this.context = context;
	}

	/**
	 * TODO: doc
	 * @param choiceOfValues
	 * @return
	 */
	public final Collection<?> filterPropertyBindingChoiceOfValues(Collection<?> choiceOfValues) {
		if (context instanceof EClassBinding) {
			final EClassBinding eClassBinding = (EClassBinding)context; 
			return Collections2.filter(choiceOfValues, new EClassFeaturesFilter(eClassBinding.getEClass()));
		} else if (context instanceof EStructuralFeatureBinding) {
			final EStructuralFeatureBinding parentPropertyBinding = (EStructuralFeatureBinding)context;
			if (parentPropertyBinding.getFeature() instanceof EReference) {
				return Collections2.filter(choiceOfValues, new EClassFeaturesFilter((EClass) parentPropertyBinding.getFeature().getEType()));
			}
		}
		return choiceOfValues;
	}
	
	
	  /**
	   * This will be called to populate a list of choices.
	   * The label provider will be used to determine the labels for the objects this returns.
	   * This default implementation uses {@link #getReachableObjectsOfType getReachableObjectsOfType}.
	   * inspired from {@link ItemPropertyDescriptor#getComboBoxObjects(Object)}
	   */
	public Collection<?> getReachableObjects(Object object, EStructuralFeature feature) {
		if (object instanceof EObject) {
			if (feature != null) {
				if (feature instanceof EReference) {
					Collection<EObject> result = ItemPropertyDescriptor.getReachableObjectsOfType((EObject) object, feature.getEType());
					if (!feature.isMany() && !result.contains(null)) {
						result.add(null);
					}
					return result;
				} else if (feature.getEType() instanceof EEnum) {
					EEnum eEnum = (EEnum) feature.getEType();
					List<Enumerator> enumerators = new ArrayList<Enumerator>();
					for (EEnumLiteral eEnumLiteral : eEnum.getELiterals()) {
						enumerators.add(eEnumLiteral.getInstance());
					}
					return enumerators;
				} else {
					EDataType eDataType = (EDataType) feature.getEType();
					List<String> enumeration = ExtendedMetaData.INSTANCE.getEnumerationFacet(eDataType);
					if (!enumeration.isEmpty()) {
						List<Object> enumerators = new ArrayList<Object>();
						for (String enumerator : enumeration) {
							enumerators.add(EcoreUtil.createFromString(eDataType, enumerator));
						}
						return enumerators;
					}
				}
			}
		}

		return null;
	}

	private static final class EClassFeaturesFilter implements Predicate<Object> {
		
		private final EClass targetClass;

		private EClassFeaturesFilter(EClass targetClass) {
			this.targetClass = targetClass;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see com.google.common.base.Predicate#apply(java.lang.Object)
		 */
		public boolean apply(Object input) {
			return input instanceof EStructuralFeature && targetClass.getEAllStructuralFeatures().contains(input);
		}
	}
	
}
