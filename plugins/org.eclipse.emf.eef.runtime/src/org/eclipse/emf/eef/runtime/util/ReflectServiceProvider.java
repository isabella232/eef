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

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ReflectServiceProvider {

	
	/**
	 * Returns the most appropriate {@link ReflectService} for the given class.
	 * @param clazz the class to process.
	 * @return the most appropriate {@link ReflectService}.
	 */
	ReflectService getReflectService(Class<?> clazz);
	
}
