/**
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 * 
 */
package org.eclipse.emf.eef.views;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element Editor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.views.ElementEditor#isReadOnly <em>Read Only</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.views.ElementEditor#isNameAsLabel <em>Name As Label</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.views.ElementEditor#getSubElementEditors <em>Sub Element Editors</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.eef.views.ViewsPackage#getElementEditor()
 * @model
 * @generated
 */
public interface ElementEditor extends ViewElement, IdentifiedElement {
	/**
	 * Returns the value of the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Read Only</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Read Only</em>' attribute.
	 * @see #setReadOnly(boolean)
	 * @see org.eclipse.emf.eef.views.ViewsPackage#getElementEditor_ReadOnly()
	 * @model required="true"
	 * @generated
	 */
	boolean isReadOnly();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.views.ElementEditor#isReadOnly <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read Only</em>' attribute.
	 * @see #isReadOnly()
	 * @generated
	 */
	void setReadOnly(boolean value);

	/**
	 * Returns the value of the '<em><b>Name As Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name As Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name As Label</em>' attribute.
	 * @see #setNameAsLabel(boolean)
	 * @see org.eclipse.emf.eef.views.ViewsPackage#getElementEditor_NameAsLabel()
	 * @model
	 * @generated
	 */
	boolean isNameAsLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.views.ElementEditor#isNameAsLabel <em>Name As Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name As Label</em>' attribute.
	 * @see #isNameAsLabel()
	 * @generated
	 */
	void setNameAsLabel(boolean value);

	/**
	 * Returns the value of the '<em><b>Sub Element Editors</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.eef.views.ElementEditor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Element Editors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Element Editors</em>' containment reference list.
	 * @see org.eclipse.emf.eef.views.ViewsPackage#getElementEditor_SubElementEditors()
	 * @model containment="true"
	 * @generated
	 */
	EList<ElementEditor> getSubElementEditors();

} // ElementEditor
