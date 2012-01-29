/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>View</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.View#getHandler <em>Handler</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getView()
 * @model abstract="true"
 * @generated
 */
public interface View extends EObject {
	/**
	 * Returns the value of the '<em><b>Handler</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Handler</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Handler</em>' attribute.
	 * @see #setHandler(ViewHandler)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getView_Handler()
	 * @model dataType="org.eclipse.emf.eef.runtime.editingModel.ViewHandler<?>"
	 * @generated
	 */
	ViewHandler<?> getHandler();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.View#getHandler <em>Handler</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Handler</em>' attribute.
	 * @see #getHandler()
	 * @generated
	 */
	void setHandler(ViewHandler<?> value);

} // View
