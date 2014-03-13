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
package org.eclipse.emf.eef.runtime.internal.binding.settings;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.internal.binding.ReflectivePropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.internal.editingModel.EditingModelEnvironmentImpl;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
import org.osgi.service.event.EventAdmin;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public abstract class AbstractEEFBindingSettings implements EEFBindingSettings<PropertiesEditingModel> {

	private EMFServiceProvider emfServiceProvider;

	private List<PropertiesEditingModel> editingModels;
	private EditingModelEnvironment editingModelEnvironment;
	private EventAdmin eventAdmin;

	/**
	 * @param eventAdmin
	 *            the event admin to set
	 */
	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

	/**
	 * @param emfServiceProvider
	 *            the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * Determines if the current provider is designed to provide
	 * {@link PropertiesEditingComponent} for the given {@link EPackage}.
	 * 
	 * @param ePackage
	 *            {@link EPackage} to test.
	 * @return <code>true</code> if the current provider can process the given
	 *         {@link EPackage}.
	 */
	public boolean serviceFor(final EPackage element) {
		List<EClassBinding> allBindings = Lists.newArrayList();
		for (PropertiesEditingModel editingModel : getEditingModels()) {
			allBindings.addAll(editingModel.getBindings());
		}
		Collection<EClassBinding> filter = Collections2.filter(allBindings, Predicates.compose(new Predicate<EPackage>() {
			/**
			 * {@inheritDoc}
			 * 
			 * @see com.google.common.base.Predicate#apply(java.lang.Object)
			 */
			public boolean apply(EPackage input) {
				EMFService emfService = emfServiceProvider.getEMFService(element);
				boolean b = (emfService != null && emfService.equals(element, input)) || element == input;
				return b;
			}

		}, new Function<EClassBinding, EPackage>() {
			/**
			 * {@inheritDoc}
			 * 
			 * @see com.google.common.base.Function#apply(java.lang.Object)
			 */
			public EPackage apply(EClassBinding input) {
				return input.getEClass().getEPackage();
			}
		}));
		return filter.size() > 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEditingModelEnvironment()
	 */
	public EditingModelEnvironment getEditingModelEnvironment() {
		if (editingModelEnvironment == null) {
			editingModelEnvironment = new EditingModelEnvironmentImpl(eventAdmin);
		}
		return editingModelEnvironment;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEEFDescription(org.eclipse.emf.ecore.EObject)
	 *      This method is not final anymore for
	 *      {@link ReflectivePropertiesEditingComponent} requirements.
	 */
	public PropertiesEditingModel getEEFDescription(EObject eObject) {
		for (PropertiesEditingModel editingModel : getEditingModels()) {
			if (editingModel.getEMFServiceProvider() == null) {
				editingModel.setEMFServiceProvider(emfServiceProvider);
			}
			if (editingModel.binding(eObject) != null) {
				return editingModel;
			}
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEEFDescription(org.eclipse.emf.ecore.EClass)
	 */
	public PropertiesEditingModel getEEFDescription(EClass eClass) {
		for (PropertiesEditingModel editingModel : getEditingModels()) {
			if (editingModel.getEMFServiceProvider() == null) {
				editingModel.setEMFServiceProvider(emfServiceProvider);
			}
			if (editingModel.binding(eClass) != null) {
				return editingModel;
			}
		}
		return null;
	}

	/**
	 * @return the specific {@link PropertiesEditingModel}s to use from this
	 *         {@link AbstractEEFBindingSettings}.
	 */
	protected Collection<? extends PropertiesEditingModel> initSpecificEditingModel() {
		return Collections.emptyList();
	}

	/**
	 * Compute the list of available editingModel in this context.
	 * 
	 * @return a list of {@link PropertiesEditingModel} available from this
	 *         {@link AbstractEEFBindingSettings}.
	 */
	private List<PropertiesEditingModel> getEditingModels() {
		if (editingModels == null) {
			editingModels = Lists.newArrayList();
			editingModels.addAll(initSpecificEditingModel());
		}
		return editingModels;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#enableLockPolicy(org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy)
	 */
	public boolean enableLockPolicy(EEFLockPolicy lockPolicy) {
		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEditingModel(org.eclipse.emf.ecore.EPackage)
	 */
	public PropertiesEditingModel getEditingModel(EPackage ePackage) {
		Iterable<EClass> filter = Iterables.filter(ePackage.getEClassifiers(), EClass.class);
		if (!Iterables.isEmpty(filter)) {
			return getEEFDescription(filter.iterator().next());
		}
		return null;
	}
}
