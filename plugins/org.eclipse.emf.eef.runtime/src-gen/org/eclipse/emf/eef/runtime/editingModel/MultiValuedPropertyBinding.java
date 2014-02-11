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
 * A representation of the model object '<em><b>Multi Valued Property Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.MultiValuedPropertyBinding#getAdder <em>Adder</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.MultiValuedPropertyBinding#getRemover <em>Remover</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getMultiValuedPropertyBinding()
 * @model
 * @generated
 */
public interface MultiValuedPropertyBinding extends PropertyBinding {
	/**
	 * Returns the value of the '<em><b>Adder</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adder</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Adder</em>' containment reference.
	 * @see #setAdder(JavaBody)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getMultiValuedPropertyBinding_Adder()
	 * @model type="org.eclipse.emf.eef.runtime.query.JavaBody<org.eclipse.emf.eef.runtime.editingModel.Void>" containment="true"
	 * @generated
	 */
	JavaBody<Void> getAdder();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.MultiValuedPropertyBinding#getAdder <em>Adder</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Adder</em>' containment reference.
	 * @see #getAdder()
	 * @generated
	 */
	void setAdder(JavaBody<Void> value);

	/**
	 * Returns the value of the '<em><b>Remover</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remover</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remover</em>' containment reference.
	 * @see #setRemover(JavaBody)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getMultiValuedPropertyBinding_Remover()
	 * @model type="org.eclipse.emf.eef.runtime.query.JavaBody<org.eclipse.emf.eef.runtime.editingModel.Void>" containment="true"
	 * @generated
	 */
	JavaBody<Void> getRemover();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.MultiValuedPropertyBinding#getRemover <em>Remover</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remover</em>' containment reference.
	 * @see #getRemover()
	 * @generated
	 */
	void setRemover(JavaBody<Void> value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.emf.eef.runtime.editingModel.Void" required="true" loaderDataType="org.eclipse.emf.eef.runtime.query.ClassLoader" loaderRequired="true" targetRequired="true"
	 * @generated
	 */
	Void addValue(ClassLoader loader, EObject target, Object value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.emf.eef.runtime.editingModel.Void" required="true" loaderDataType="org.eclipse.emf.eef.runtime.query.ClassLoader" loaderRequired="true" targetRequired="true"
	 * @generated
	 */
	Void removeValue(ClassLoader loader, EObject target, Object value);

} // MultiValuedPropertyBinding
