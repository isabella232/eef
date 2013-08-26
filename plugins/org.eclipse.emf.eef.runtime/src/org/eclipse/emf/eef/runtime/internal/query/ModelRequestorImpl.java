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
package org.eclipse.emf.eef.runtime.internal.query;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.query.Filter;
import org.eclipse.emf.eef.runtime.query.JavaBody;
import org.eclipse.emf.eef.runtime.query.ModelRequestor;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ModelRequestorImpl implements ModelRequestor, DefaultService {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(EPackage element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.query.ModelRequestor#filter(java.util.Collection, org.eclipse.emf.eef.runtime.query.Filter)
	 */
	public <ANY_EOBJECT extends EObject> Collection<ANY_EOBJECT> filter(Collection<ANY_EOBJECT> elements, final Filter filter) {
		return Collections2.filter(elements, new Predicate<ANY_EOBJECT>() {

			/**
			 * {@inheritDoc}
			 * @see com.google.common.base.Predicate#apply(java.lang.Object)
			 */
			public boolean apply(ANY_EOBJECT arg0) {
				if (filter.getBody() instanceof JavaBody<?>) {
					return processJavaBody((JavaBody<Boolean>)filter.getBody(), arg0);
				}
				return false;
			}
		});
	}
	
	public <TYPE, ANY_EOBJECT extends EObject> TYPE processJavaBody(JavaBody<TYPE> body, ANY_EOBJECT element) {
		String qualifiedClass = body.getPackage() + "." + body.getClass_();
		return null;
	}

}