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
package org.eclipse.emf.eef.runtime.ui.swt.internal.viewer.filters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.query.Filter;
import org.eclipse.emf.eef.runtime.query.JavaBody;
import org.eclipse.emf.eef.runtime.ui.swt.EEFRuntimeUISWT;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.util.ReflectService;
import org.eclipse.emf.eef.runtime.util.ReflectServiceProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class JavaViewerFilter extends ViewerFilter {
	
	private ReflectServiceProvider reflectServiceProvider;
	private EEFLogger logger;
	
	private ClassLoader loader;
	private Filter filter;
	private PropertiesEditingContext editingContext;
	private PropertiesEditingView<Composite> editingView;

	public JavaViewerFilter(ReflectServiceProvider reflectServiceProvider, EEFLogger logger, PropertiesEditingContext editingContext, PropertiesEditingView<Composite> editingView, ClassLoader loader, Filter filter) {
		this.reflectServiceProvider = reflectServiceProvider;
		this.logger = logger;
		this.editingContext = editingContext;
		this.editingView = editingView;
		this.loader = loader;
		this.filter = filter;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		JavaBody<Boolean> body = (JavaBody<Boolean>) filter.getBody();
		String qualifiedClass = body.getQualifiedClass();
		try {
			Class<?> loadClass = loader.loadClass(qualifiedClass);
			List<Object> applicableArguments = Lists.newArrayList();
			applicableArguments.add(editingContext);
			applicableArguments.add(editingContext.getEditingComponent());
			applicableArguments.add(editingView);
			applicableArguments.add(element);
			ReflectService reflectService = reflectServiceProvider.getReflectService(loadClass);
			Method method = reflectService.getApplicableMethod(loadClass, body.getMethod(), applicableArguments);
			if (method != null) {
				Object instance = body.isStatic()?null:loadClass.newInstance();
				Boolean invocationResult = (Boolean) reflectService.invokeMethod(method, instance, applicableArguments);
				return invocationResult;
			}
		} catch (ClassNotFoundException e) {
			logger.logError(EEFRuntimeUISWT.PLUGIN_ID, "Unable to load the class " + qualifiedClass, e);
			return true;
		} catch (IllegalAccessException e) {
			logger.logError(EEFRuntimeUISWT.PLUGIN_ID, "Unable to create a new instance of the class " + qualifiedClass, e);
			return true;
		} catch (InvocationTargetException e) {
			logger.logError(EEFRuntimeUISWT.PLUGIN_ID, "Unable to execute the method " + body.getMethod() + " of the class " + qualifiedClass, e);
			return true;
		} catch (InstantiationException e) {
			logger.logError(EEFRuntimeUISWT.PLUGIN_ID, "Unable to create a new instance of the class " + qualifiedClass, e);
			return true;
		}
		return true;
	}

}
