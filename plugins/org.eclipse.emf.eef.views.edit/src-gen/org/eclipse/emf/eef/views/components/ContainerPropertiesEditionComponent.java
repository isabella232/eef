/**
 *  Copyright (c) 2008-2009 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 * 
 *
 * $Id: ContainerPropertiesEditionComponent.java,v 1.4 2009/05/19 09:16:41 glefur Exp $
 */
package org.eclipse.emf.eef.views.components;

// Start of user code for imports

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.impl.components.ComposedPropertiesEditionComponent;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.emf.eef.views.parts.ContainerPropertiesEditionPart;
import org.eclipse.emf.eef.views.parts.ViewsViewsRepository;

// End of user code
/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 */
public class ContainerPropertiesEditionComponent extends ComposedPropertiesEditionComponent {

	/**
	 * The Base part
	 */
	private ContainerPropertiesEditionPart basePart;

	/**
	 * The ContainerBasePropertiesEditionComponent sub component
	 */
	protected ContainerBasePropertiesEditionComponent containerBasePropertiesEditionComponent;

	/**
	 * The DocumentedElementPropertiesEditionComponent sub component
	 */
	protected DocumentedElementPropertiesEditionComponent documentedElementPropertiesEditionComponent;
	/**
	 * Parameterized constructor
	 * 
	 * @param container
	 *            the EObject to edit
	 */
	public ContainerPropertiesEditionComponent(EObject container, String editing_mode) {
		super(editing_mode);
		if (container instanceof Container) {
			containerBasePropertiesEditionComponent = new ContainerBasePropertiesEditionComponent(container, editing_mode); 
			addSubComponent(containerBasePropertiesEditionComponent);
			documentedElementPropertiesEditionComponent = new DocumentedElementPropertiesEditionComponent(container, editing_mode); 	
			addSubComponent(documentedElementPropertiesEditionComponent);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.ComposedPropertiesEditionComponent#
	 * 		getPropertiesEditionPart(int, java.lang.String)
	 */
	public IPropertiesEditionPart getPropertiesEditionPart(int kind, String key) {
		if ("Base".equals(key)) {
			basePart = (ContainerPropertiesEditionPart)containerBasePropertiesEditionComponent.getPropertiesEditionPart(kind, key);
			return (IPropertiesEditionPart)basePart;
		}
		return super.getPropertiesEditionPart(kind, key);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.ComposedPropertiesEditionComponent#
	 * setPropertiesEditionPart(java.lang.Class, int, org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart)
	 */
	public void setPropertiesEditionPart(java.lang.Class key, int kind, IPropertiesEditionPart propertiesEditionPart) {
		if (ViewsViewsRepository.Container.class == key) {
			super.setPropertiesEditionPart(key, kind, propertiesEditionPart);
			basePart = (ContainerPropertiesEditionPart)propertiesEditionPart;
		}
	}

	/** 
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.ComposedPropertiesEditionComponent
	 *	#initPart(java.lang.Class, int, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.resource.ResourceSet)
	 */
	public void initPart(java.lang.Class key, int kind, EObject element, ResourceSet allResource) {
		if (key == ViewsViewsRepository.Container.class) {
			super.initPart(key, kind, element, allResource);
		}
	}
}

