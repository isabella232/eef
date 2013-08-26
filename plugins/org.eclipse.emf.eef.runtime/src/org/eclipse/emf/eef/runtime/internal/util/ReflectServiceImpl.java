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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.util.ReflectService;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ReflectServiceImpl implements ReflectService, DefaultService {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Class<?> element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.ReflectService#getApplicableMethod(java.lang.Class, java.lang.String, java.util.Collection)
	 */
	public Method getApplicableMethod(Class<?> clazz, String methodName, Collection<Object> availableParameters) {
		for (Method method : clazz.getMethods()) {
			if (method.getName().equals(methodName)) {
				if (isValuable(method, availableParameters)) {
					return method;
				}
			}
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.ReflectService#invokeMethod(java.lang.reflect.Method, java.lang.Object, java.util.List)
	 */
	public Object invokeMethod(Method method, Object target, List<Object> availableParameters) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		List<Object> parameters = Lists.newArrayList();
		Set<Object> consumedParameters = Sets.newHashSet();
		Class<?>[] parameterTypes = method.getParameterTypes();
		for (Class<?> clazz : parameterTypes) {
			Object potentialParameter = null;
			Object matchingParameter = null;
			Iterator<Object> iterator = availableParameters.iterator();
			while (iterator.hasNext() && (matchingParameter == null)) {
				Object param = iterator.next();
				if (clazz.isInstance(param)) {
					if (consumedParameters.contains(param)) {
						potentialParameter = param;
					} else {
						matchingParameter = param;
						consumedParameters.add(param);
					}
				}
			}
			if (matchingParameter == null) {
				if (potentialParameter == null) {
					throw new IllegalArgumentException("Unable to find a matching parameter for " + clazz);
				} else {
					matchingParameter = potentialParameter;
				}
			}
			parameters.add(matchingParameter);
			
		}
		Object[] array = parameters.toArray();
		return method.invoke(target, array);
	}

	private boolean isValuable(Method method, Collection<Object> availableParameters) {
		Class<?>[] parameterTypes = method.getParameterTypes();
		for (Class<?> clazz : parameterTypes) {
			if (!hasAnInstance(clazz, availableParameters)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean hasAnInstance(Class<?> clazz, Collection<Object> availableParameters) {
		for (Object param : availableParameters) {
			if (clazz.isInstance(param)) {
				return true;
			}
		}
		return false;
	}
	
}
