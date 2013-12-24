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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.eef.runtime.services.EEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ReflectService extends EEFService<Class<?>> {

	/**
	 * Returns a method of the given class matching the methodeName and invokable with the given collection of arguments.
	 * @param clazz the {@link Class} to process.
	 * @param methodName the method name.
	 * @param availableParameters a list of potential parameters.
	 * @return an invokable method if exists <code>null</code> otherwise.
	 */
	Method getApplicableMethod(Class<?> clazz, String methodName, Collection<Object> availableParameters);
	
	/**
	 * Invoke the given method.
	 * @param method Method to invoke.
	 * @param target Target object for the method.
	 * @param availableParameters Available parameters.
	 * @return the result of invocation.
	 */
	Object invokeMethod(Method method, Object target, List<Object> availableParameters) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException;

}
