/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.eef.runtime.binding.PropertyBindingCustomizer;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getEditor <em>Editor</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getSubPropertyBindings <em>Sub Property Bindings</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getSettings <em>Settings</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getBindingCustomizer <em>Binding Customizer</em>}</li>
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

	/**
	 * Returns the value of the '<em><b>Settings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.eef.runtime.editingModel.EditorSettings}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Settings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Settings</em>' containment reference list.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getPropertyBinding_Settings()
	 * @model containment="true"
	 * @generated
	 */
	EList<EditorSettings> getSettings();

	/**
	 * Returns the value of the '<em><b>Binding Customizer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Binding Customizer</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Binding Customizer</em>' attribute.
	 * @see #setBindingCustomizer(String)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getPropertyBinding_BindingCustomizer()
	 * @model
	 * @generated
	 */
	String getBindingCustomizer();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getBindingCustomizer <em>Binding Customizer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binding Customizer</em>' attribute.
	 * @see #getBindingCustomizer()
	 * @generated
	 */
	void setBindingCustomizer(String value);

} // PropertyBinding
