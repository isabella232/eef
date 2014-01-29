/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.eef.runtime.query.JavaBody;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mono Valued Property Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.MonoValuedPropertyBinding#getSetter <em>Setter</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.MonoValuedPropertyBinding#getUnsetter <em>Unsetter</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getMonoValuedPropertyBinding()
 * @model
 * @generated
 */
public interface MonoValuedPropertyBinding extends PropertyBinding {
	/**
	 * Returns the value of the '<em><b>Setter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Setter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Setter</em>' containment reference.
	 * @see #setSetter(JavaBody)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getMonoValuedPropertyBinding_Setter()
	 * @model type="org.eclipse.emf.eef.runtime.query.JavaBody<org.eclipse.emf.eef.runtime.editingModel.Void>" containment="true"
	 * @generated
	 */
	JavaBody<Void> getSetter();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.MonoValuedPropertyBinding#getSetter <em>Setter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Setter</em>' containment reference.
	 * @see #getSetter()
	 * @generated
	 */
	void setSetter(JavaBody<Void> value);

	/**
	 * Returns the value of the '<em><b>Unsetter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unsetter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unsetter</em>' containment reference.
	 * @see #setUnsetter(JavaBody)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getMonoValuedPropertyBinding_Unsetter()
	 * @model type="org.eclipse.emf.eef.runtime.query.JavaBody<org.eclipse.emf.eef.runtime.editingModel.Void>" containment="true"
	 * @generated
	 */
	JavaBody<Void> getUnsetter();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.MonoValuedPropertyBinding#getUnsetter <em>Unsetter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unsetter</em>' containment reference.
	 * @see #getUnsetter()
	 * @generated
	 */
	void setUnsetter(JavaBody<Void> value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.emf.eef.runtime.editingModel.Void" required="true" loaderDataType="org.eclipse.emf.eef.runtime.query.ClassLoader" loaderRequired="true" targetRequired="true"
	 * @generated
	 */
	Void setValue(ClassLoader loader, EObject target, Object value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.emf.eef.runtime.editingModel.Void" required="true" loaderDataType="org.eclipse.emf.eef.runtime.query.ClassLoader" loaderRequired="true" targetRequired="true"
	 * @generated
	 */
	Void unsetValue(ClassLoader loader, EObject target, Object value);

} // MonoValuedPropertyBinding
