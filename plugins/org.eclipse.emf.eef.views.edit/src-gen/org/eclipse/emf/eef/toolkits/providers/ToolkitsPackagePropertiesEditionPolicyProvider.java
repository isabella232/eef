/**
 *  Copyright (c) 2008 - 2010 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 *
 */
package org.eclipse.emf.eef.toolkits.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.api.policies.IPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.policies.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.policies.impl.StandardPropertiesEditingPolicyProvider;
import org.eclipse.emf.eef.toolkits.ToolkitsPackage;

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class ToolkitsPackagePropertiesEditionPolicyProvider extends StandardPropertiesEditingPolicyProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider#provides(org.eclipse.emf.eef.runtime.api.policies.IPropertiesEditionContext)
	 * 
	 */
	public boolean provides(IPropertiesEditionContext context) {
		if (context instanceof EObjectPropertiesEditionContext) {
			EObject eObject = ((EObjectPropertiesEditionContext) context).getEObject();
			return eObject != null && (ToolkitsPackage.eINSTANCE.equals(eObject.eClass().getEPackage()) || ToolkitsPackage.eINSTANCE.getESubpackages().contains(eObject.eClass().getEPackage()));
		}
		return false;
	}

}
