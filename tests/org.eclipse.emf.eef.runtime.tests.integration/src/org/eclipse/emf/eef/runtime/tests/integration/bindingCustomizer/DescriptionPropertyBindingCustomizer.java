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
package org.eclipse.emf.eef.runtime.tests.integration.bindingCustomizer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer;
import org.eclipse.emf.eef.runtime.binding.MonoPropertyBindingCustomizer;
import org.eclipse.emf.eef.runtime.util.EEFInvocationParameters;
import org.eclipse.emf.eef.runtime.util.EEFModifierInvocationParameters;

/**
 * Description BindingCustomizer
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class DescriptionPropertyBindingCustomizer extends MonoPropertyBindingCustomizer {

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.MonoPropertyBindingCustomizer#getGetter()
	 */
	@Override
	public EEFModifierCustomizer<Object> getGetter() {
		return new EEFModifierCustomizer<Object>() {

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer#execute(org.eclipse.emf.eef.runtime.util.EEFInvocationParameters)
			 */
			@Override
			public Object execute(EEFInvocationParameters parameters) {
				if (parameters.getEditedObject() instanceof EClass) {
					return ((EClass) parameters.getEditedObject()).getName();
				}
				return "";
			}
		};
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.MonoPropertyBindingCustomizer#getEventFilter()
	 */
	@Override
	public EEFModifierCustomizer<Boolean> getEventFilter() {
		return new EEFModifierCustomizer<Boolean>() {

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer#execute(org.eclipse.emf.eef.runtime.util.EEFInvocationParameters)
			 */
			@Override
			public Boolean execute(EEFInvocationParameters parameters) {
				return true;
			}

		};
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.MonoPropertyBindingCustomizer#getSetter()
	 */
	@Override
	public EEFModifierCustomizer<Void> getSetter() {
		return new EEFModifierCustomizer<Void>() {

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer#execute(org.eclipse.emf.eef.runtime.util.EEFInvocationParameters)
			 */
			@Override
			public Void execute(EEFInvocationParameters parameters) {
				if (parameters.getEditedObject() instanceof EClass) {
					((EClass) parameters.getEditedObject()).setName((String) ((EEFModifierInvocationParameters) parameters).getValue());
				}
				return null;
			}
		};
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.MonoPropertyBindingCustomizer#getUnsetter()
	 */
	@Override
	public EEFModifierCustomizer<Void> getUnsetter() {
		return new EEFModifierCustomizer<Void>() {

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer#execute(org.eclipse.emf.eef.runtime.util.EEFInvocationParameters)
			 */
			@Override
			public Void execute(EEFInvocationParameters parameters) {
				if (parameters.getEditedObject() instanceof EClass) {
					((EClass) parameters.getEditedObject()).setName("");
				}
				return null;
			}
		};
	}

}
