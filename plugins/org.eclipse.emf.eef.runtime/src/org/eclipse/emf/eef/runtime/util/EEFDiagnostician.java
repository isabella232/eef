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
package org.eclipse.emf.eef.runtime.util;

import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.edit.provider.IItemLabelProvider;

/**
 * Diagnostician with correct EObject label in messages.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 *
 */
public class EEFDiagnostician extends Diagnostician {
	private AdapterFactory adapterFactory;

	public EEFDiagnostician(AdapterFactory adapterFactory) {
		super();
		this.adapterFactory = adapterFactory;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.util.Diagnostician#doValidateContents(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 */
	@Override
	protected boolean doValidateContents(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	@Override
	public String getObjectLabel(EObject eObject) {
		if (adapterFactory != null && !eObject.eIsProxy()) {
			IItemLabelProvider itemLabelProvider = (IItemLabelProvider) adapterFactory.adapt(eObject, IItemLabelProvider.class);
			if (itemLabelProvider != null) {
				return itemLabelProvider.getText(eObject);
			}
		}

		return super.getObjectLabel(eObject);
	}

	/**
	 * @return the adapterFactory
	 */
	public AdapterFactory getAdapterFactory() {
		return adapterFactory;
	}
}
