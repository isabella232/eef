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
import org.eclipse.emf.eef.runtime.services.EEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFInvoker extends EEFService<Body> {
	
	/**
	 * Invokes the code defined by the given body
	 * @param loader the classloader to use to invoke the body.
	 * @param body the {@link Body} defining the code to invoke
	 * @param parameters the {@link EEFInvocationParameters} to use as parameter of the invoker method. 
	 * @return invocation result.
	 */
	Object invoke(ClassLoader loader, Body body, EEFInvocationParameters parameters);

}
