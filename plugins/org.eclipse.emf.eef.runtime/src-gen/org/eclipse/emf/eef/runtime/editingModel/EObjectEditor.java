/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EObject Editor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.EObjectEditor#getDefinition <em>Definition</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getEObjectEditor()
 * @model
 * @generated
 */
public interface EObjectEditor extends Editor {
	/**
	 * Returns the value of the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Definition</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Definition</em>' reference.
	 * @see #setDefinition(EObject)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getEObjectEditor_Definition()
	 * @model required="true"
	 * @generated
	 */
	EObject getDefinition();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.EObjectEditor#getDefinition <em>Definition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Definition</em>' reference.
	 * @see #getDefinition()
	 * @generated
	 */
	void setDefinition(EObject value);

} // EObjectEditor
