/**
 * ******************************************************************************
 *  Copyright (c) 2008 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 *  ******************************************************************************
 *
 * $Id: ToolkitsPackageImpl.java,v 1.1 2009/04/30 17:16:00 glefur Exp $
 */
package org.eclipse.emf.eef.toolkits.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.eef.toolkits.Toolkit;
import org.eclipse.emf.eef.toolkits.ToolkitsFactory;
import org.eclipse.emf.eef.toolkits.ToolkitsPackage;
import org.eclipse.emf.eef.toolkits.Widget;

import org.eclipse.emf.eef.views.ViewsPackage;

import org.eclipse.emf.eef.views.impl.ViewsPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ToolkitsPackageImpl extends EPackageImpl implements ToolkitsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass toolkitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass widgetEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.eef.toolkits.ToolkitsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ToolkitsPackageImpl() {
		super(eNS_URI, ToolkitsFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ToolkitsPackage init() {
		if (isInited)
			return (ToolkitsPackage)EPackage.Registry.INSTANCE.getEPackage(ToolkitsPackage.eNS_URI);

		// Obtain or create and register package
		ToolkitsPackageImpl theToolkitsPackage = (ToolkitsPackageImpl)(EPackage.Registry.INSTANCE
				.getEPackage(eNS_URI) instanceof ToolkitsPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(eNS_URI) : new ToolkitsPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		ViewsPackageImpl theViewsPackage = (ViewsPackageImpl)(EPackage.Registry.INSTANCE
				.getEPackage(ViewsPackage.eNS_URI) instanceof ViewsPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(ViewsPackage.eNS_URI) : ViewsPackage.eINSTANCE);

		// Create package meta-data objects
		theToolkitsPackage.createPackageContents();
		theViewsPackage.createPackageContents();

		// Initialize created meta-data
		theToolkitsPackage.initializePackageContents();
		theViewsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theToolkitsPackage.freeze();

		return theToolkitsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getToolkit() {
		return toolkitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getToolkit_Widgets() {
		return (EReference)toolkitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getToolkit_Name() {
		return (EAttribute)toolkitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWidget() {
		return widgetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWidget_Name() {
		return (EAttribute)widgetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWidget_Icon() {
		return (EAttribute)widgetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWidget_Implementation() {
		return (EAttribute)widgetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWidget_Toolkit() {
		return (EReference)widgetEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ToolkitsFactory getToolkitsFactory() {
		return (ToolkitsFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		toolkitEClass = createEClass(TOOLKIT);
		createEReference(toolkitEClass, TOOLKIT__WIDGETS);
		createEAttribute(toolkitEClass, TOOLKIT__NAME);

		widgetEClass = createEClass(WIDGET);
		createEAttribute(widgetEClass, WIDGET__NAME);
		createEAttribute(widgetEClass, WIDGET__ICON);
		createEAttribute(widgetEClass, WIDGET__IMPLEMENTATION);
		createEReference(widgetEClass, WIDGET__TOOLKIT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(toolkitEClass, Toolkit.class,
				"Toolkit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getToolkit_Widgets(),
				this.getWidget(),
				this.getWidget_Toolkit(),
				"widgets", null, 0, -1, Toolkit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getToolkit_Name(),
				ecorePackage.getEString(),
				"name", null, 1, 1, Toolkit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(widgetEClass, Widget.class,
				"Widget", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getWidget_Name(),
				ecorePackage.getEString(),
				"name", null, 1, 1, Widget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getWidget_Icon(),
				ecorePackage.getEString(),
				"icon", null, 0, 1, Widget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getWidget_Implementation(),
				ecorePackage.getEString(),
				"implementation", null, 0, 1, Widget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getWidget_Toolkit(),
				this.getToolkit(),
				this.getToolkit_Widgets(),
				"toolkit", null, 0, 1, Widget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
	}

} //ToolkitsPackageImpl
