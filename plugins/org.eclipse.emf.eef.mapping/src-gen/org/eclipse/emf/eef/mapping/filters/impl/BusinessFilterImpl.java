/**
 *  Copyright (c) 2008 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 * 
 *
 * $Id: BusinessFilterImpl.java,v 1.4 2009/09/10 10:27:16 sbouchet Exp $
 */
package org.eclipse.emf.eef.mapping.filters.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.eef.mapping.filters.BusinessFilter;
import org.eclipse.emf.eef.mapping.filters.FiltersPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Business Filter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class BusinessFilterImpl extends BindingFilterImpl implements
		BusinessFilter {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BusinessFilterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FiltersPackage.Literals.BUSINESS_FILTER;
	}

} //BusinessFilterImpl
