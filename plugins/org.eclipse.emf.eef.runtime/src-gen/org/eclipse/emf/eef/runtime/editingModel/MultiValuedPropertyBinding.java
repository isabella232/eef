/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel;

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
	 * @model containment="true"
	 * @generated
	 */
	JavaBody getAdder();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.MultiValuedPropertyBinding#getAdder <em>Adder</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Adder</em>' containment reference.
	 * @see #getAdder()
	 * @generated
	 */
	void setAdder(JavaBody value);

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
	 * @model containment="true"
	 * @generated
	 */
	JavaBody getRemover();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.MultiValuedPropertyBinding#getRemover <em>Remover</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remover</em>' containment reference.
	 * @see #getRemover()
	 * @generated
	 */
	void setRemover(JavaBody value);

} // MultiValuedPropertyBinding
