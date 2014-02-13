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
package org.eclipse.emf.eef.runtime.internal.util;

import org.eclipse.emf.eef.runtime.query.Body;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.util.EEFInvoker;
import org.eclipse.emf.eef.runtime.util.EEFInvokerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFInvokerProviderImpl extends EEFServiceProviderImpl<Body, EEFInvoker> implements EEFInvokerProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EEFInvokerProvider#getInvoker(org.eclipse.emf.eef.runtime.query.Body)
	 */
	public EEFInvoker getInvoker(Body body) {
		return getService(body);
	}

}
