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
import java.lang.reflect.Modifier;

import org.eclipse.emf.eef.runtime.EEFRuntime;
import org.eclipse.emf.eef.runtime.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.query.Body;
import org.eclipse.emf.eef.runtime.query.JavaBody;
import org.eclipse.emf.eef.runtime.util.EEFInvocationParameters;
import org.eclipse.emf.eef.runtime.util.EEFInvoker;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFInvokerImpl implements EEFInvoker {
	
	private EEFLogger eefLogger;

	/**
	 * @param eefLogger the eefLogger to set
	 */
	public final void setEEFLogger(EEFLogger eefLogger) {
		this.eefLogger = eefLogger;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Body element) {
		return element instanceof JavaBody;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EEFInvoker#invoke(java.lang.ClassLoader, org.eclipse.emf.eef.runtime.query.Body, org.eclipse.emf.eef.runtime.util.EEFInvocationParameters)
	 */
	public Object invoke(ClassLoader loader, Body body, EEFInvocationParameters parameters) {
		JavaBody javaBody = (JavaBody) body;
		try {
			Class<?> loadClass = loader.loadClass(javaBody.getQualifiedClass());
			Method method = searchMethod(javaBody, loadClass);
			if (method != null) {
				return invokeMethod(parameters, loadClass, method);
			}
		} catch (ClassNotFoundException e) {
			eefLogger.logError(EEFRuntime.PLUGIN_ID, "An error occured during Java Body invocation.", e);
		} catch (IllegalArgumentException e) {
			eefLogger.logError(EEFRuntime.PLUGIN_ID, "An error occured during Java Body invocation.", e);
		} catch (IllegalAccessException e) {
			eefLogger.logError(EEFRuntime.PLUGIN_ID, "An error occured during Java Body invocation.", e);
		} catch (InvocationTargetException e) {
			eefLogger.logError(EEFRuntime.PLUGIN_ID, "An error occured during Java Body invocation.", e);
		} catch (SecurityException e) {
			eefLogger.logError(EEFRuntime.PLUGIN_ID, "An error occured during Java Body invocation.", e);
		} catch (NoSuchMethodException e) {
			eefLogger.logError(EEFRuntime.PLUGIN_ID, "An error occured during Java Body invocation.", e);
		} catch (InstantiationException e) {
			eefLogger.logError(EEFRuntime.PLUGIN_ID, "An error occured during Java Body invocation.", e);
		}
		return null;
	}

	/**
	 * @param javaBody
	 * @param loadClass
	 * @return
	 * @throws NoSuchMethodException
	 */
	protected Method searchMethod(JavaBody javaBody, Class<?> loadClass) throws NoSuchMethodException {
		Method method = loadClass.getMethod(javaBody.getMethod(), EEFInvocationParameters.class);
		return method;
	}

	/**
	 * @param parameters
	 * @param loadClass
	 * @param method
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	protected Object invokeMethod(EEFInvocationParameters parameters, Class<?> loadClass, Method method) throws IllegalAccessException, InvocationTargetException, InstantiationException {
		if (Modifier.isStatic(method.getModifiers())) {
			return method.invoke(null, parameters);
		} else {
			Object newInstance = loadClass.newInstance();
			return method.invoke(newInstance, parameters);
		}
	}

}
