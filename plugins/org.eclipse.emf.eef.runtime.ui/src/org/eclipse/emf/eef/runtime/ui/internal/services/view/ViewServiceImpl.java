/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.emf.eef.runtime.ui.internal.services.view;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.EditingOptions;
import org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class ViewServiceImpl implements ViewService, DefaultService {
	
	private EMFServiceProvider emfServiceProvider;
	
	private PropertiesEditingComponent editingComponent;
	
	/**
	 * Creates a semanticless helper.
	 */
	public ViewServiceImpl() { }

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
	public boolean serviceFor(View element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.view.ViewService#getEditingComponent()
	 */
	public PropertiesEditingComponent getEditingComponent() {
		return editingComponent;
	}
 
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.view.ViewService#setEditingComponent(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	public void setEditingComponent(PropertiesEditingComponent editingComponent) {
		this.editingComponent = editingComponent;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.view.ViewService.ViewHelper#getDescription(java.lang.Object, java.lang.String)
	 */
	public String getDescription(Object editor, String alternate) {
		String text = alternate;
		EStructuralFeature associatedFeature = feature(editor);
		EObject eObject = editingComponent.getEObject();
		if (!eObject.eClass().getEAllStructuralFeatures().contains(associatedFeature)) {
			EMFService service = emfServiceProvider.getEMFService(eObject.eClass().getEPackage());
			associatedFeature = service.mapFeature(eObject, associatedFeature);
		}
		if (associatedFeature != null) {
			IItemPropertySource labelProvider = (IItemPropertySource) editingComponent.getEditingContext().getAdapterFactory().adapt(eObject, org.eclipse.emf.edit.provider.IItemPropertySource.class);
			if (labelProvider != null) {
				IItemPropertyDescriptor propertyDescriptor = labelProvider.getPropertyDescriptor(eObject, associatedFeature);
				if (propertyDescriptor != null) {
					text = propertyDescriptor.getDisplayName(editor);
				}
			}
		}
		return text;
	}

	/**
	 * @param editor
	 * @return
	 */
	protected EStructuralFeature feature(Object editor) {
		return editingComponent.getBinding().feature(editor, editingComponent.getEditingContext().getOptions().autowire());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.view.ViewService#getHelpContent(java.lang.Object)
	 */
	public String getHelpContent(Object editor) {
		EStructuralFeature feature = editingComponent.getBinding().feature(editor, editingComponent.getEditingContext().getOptions().autowire());
		if (feature != null) {
			EditingOptions options = editingComponent.getBinding().getEditingModel().getOptions();
			if (options == null || options.getFeatureDocumentationProvider() == FeatureDocumentationProvider.GENMODEL_PROPERTY_DESCRIPTION) {
				EditingModelEnvironment editingModelEnvironment = editingComponent.getEditingModelEnvironment();
				EObject genFeature = editingModelEnvironment.genFeature(feature);
				if (genFeature != null) {
					EStructuralFeature esf = genFeature.eClass().getEStructuralFeature("propertyDescription");
					if (esf != null) {
						String documentation = (String) genFeature.eGet(esf);
						if (documentation != null && documentation.length() > 0) {
							return documentation;
						}
					}
				}
			} else {
				if (options.getFeatureDocumentationProvider() == FeatureDocumentationProvider.ECORE_DOCUMENTATION) {
					String documentation = EcoreUtil.getDocumentation(feature);
					if (documentation != null && documentation.length() > 0) {
						return documentation;
					}
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.view.ViewService#getBestInput(java.lang.Object)
	 */
	public Object getBestInput(Object sourceInput) {
		Resource resource = null;
		if (sourceInput instanceof EObject) {
			resource = ((EObject) sourceInput).eResource();
		} else if (sourceInput instanceof Resource){
			resource = (Resource) sourceInput;
		}
		ResourceSet resourceSet = null;
		if (resource != null) {
			resourceSet = resource.getResourceSet();
		} else if (sourceInput instanceof ResourceSet) {
			resourceSet = (ResourceSet) sourceInput;
		}
		if (resourceSet != null) {
			return resourceSet;
		} else if (resource != null) {
			return resource;
		}
		return sourceInput;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.view.ViewService#getEditingDomain(org.eclipse.ui.IWorkbenchPart)
	 */
	public EditingDomain getEditingDomain(IAdaptable part) {
		EditingDomain editingDomain = null;
		if (part instanceof IEditingDomainProvider) {
			editingDomain = ((IEditingDomainProvider)part).getEditingDomain();
		} else {
			if ((part.getAdapter(IEditingDomainProvider.class)) != null) {
				editingDomain = ((IEditingDomainProvider)part.getAdapter(IEditingDomainProvider.class)).getEditingDomain();
			} else if ((part.getAdapter(EditingDomain.class)) != null) {
				editingDomain = (EditingDomain)part.getAdapter(EditingDomain.class);
			}
		}
		return editingDomain;
	}

}

