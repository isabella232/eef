/*******************************************************************************
 * Copyright (c) 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.cdo.runtime.policies;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.api.providers.IReadOnlyPolicy;
import org.eclipse.emf.spi.cdo.FSMUtil;

/**
 * Read Only policy for CDO. If an element is locked -> the widget is read only.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class CDOReadOnlyPolicy implements IReadOnlyPolicy {

	
	/** (non-Javadoc)
	 * @see org.eclipse.emf.eef.runtime.api.providers.IReadOnlyPolicy#isReadOnly(org.eclipse.emf.ecore.EObject, java.lang.Object)
	 */
	public boolean isReadOnly(EObject context, Object eStructuralFeature) {
		if (context != null && context.eResource() instanceof CDOResource) {
			CDOObject cdoObject = CDOUtil.getCDOObject(context);
			if (cdoObject != null && !FSMUtil.isTransient(cdoObject)
					&& cdoObject.cdoWriteLock().isLockedByOthers()) {
				return true;
			}
		}

		return false;
	}

}
