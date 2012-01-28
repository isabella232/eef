/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EClass Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getEClass <em>EClass</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getView <em>View</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getHandler <em>Handler</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getEClassBinding()
 * @model
 * @generated
 */
public interface EClassBinding extends EObject {
	/**
	 * Returns the value of the '<em><b>EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EClass</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EClass</em>' reference.
	 * @see #setEClass(EClass)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getEClassBinding_EClass()
	 * @model required="true"
	 * @generated
	 */
	EClass getEClass();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getEClass <em>EClass</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EClass</em>' reference.
	 * @see #getEClass()
	 * @generated
	 */
	void setEClass(EClass value);

	/**
	 * Returns the value of the '<em><b>View</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>View</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>View</em>' attribute.
	 * @see #setView(Object)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getEClassBinding_View()
	 * @model required="true"
	 * @generated
	 */
	Object getView();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getView <em>View</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>View</em>' attribute.
	 * @see #getView()
	 * @generated
	 */
	void setView(Object value);

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
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getEClassBinding_Handler()
	 * @model dataType="org.eclipse.emf.eef.runtime.editingModel.ViewHandler<?>"
	 * @generated
	 */
	ViewHandler<?> getHandler();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getHandler <em>Handler</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Handler</em>' attribute.
	 * @see #getHandler()
	 * @generated
	 */
	void setHandler(ViewHandler<?> value);

} // EClassBinding
