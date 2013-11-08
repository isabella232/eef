/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getFeature <em>Feature</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getEditor <em>Editor</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getSubPropertyBindings <em>Sub Property Bindings</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getPropertyBinding()
 * @model
 * @extends EModelElement
 * @generated
 */
public interface PropertyBinding extends EModelElement {
	/**
	 * Returns the value of the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature</em>' reference.
	 * @see #setFeature(EStructuralFeature)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getPropertyBinding_Feature()
	 * @model required="true"
	 * @generated
	 */
	EStructuralFeature getFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getFeature <em>Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature</em>' reference.
	 * @see #getFeature()
	 * @generated
	 */
	void setFeature(EStructuralFeature value);

	/**
	 * Returns the value of the '<em><b>Editor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Editor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Editor</em>' containment reference.
	 * @see #setEditor(Editor)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getPropertyBinding_Editor()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Editor getEditor();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getEditor <em>Editor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Editor</em>' containment reference.
	 * @see #getEditor()
	 * @generated
	 */
	void setEditor(Editor value);

	/**
	 * Returns the value of the '<em><b>Sub Property Bindings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Property Bindings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Property Bindings</em>' containment reference list.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getPropertyBinding_SubPropertyBindings()
	 * @model containment="true"
	 * @generated
	 */
	EList<PropertyBinding> getSubPropertyBindings();

} // PropertyBinding
