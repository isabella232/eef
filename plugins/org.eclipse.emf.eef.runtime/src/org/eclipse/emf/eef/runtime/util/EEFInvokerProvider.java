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

import org.eclipse.emf.eef.runtime.query.Body;
import org.eclipse.emf.eef.runtime.services.EEFServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFInvokerProvider extends EEFServiceProvider<Body, EEFInvoker> {
	
	/**
	 * @param body {@link Body} to invoke.
	 * @return a {@link EEFInvoker} able to handle the given body.
	 */
	EEFInvoker getInvoker(Body body);

}
