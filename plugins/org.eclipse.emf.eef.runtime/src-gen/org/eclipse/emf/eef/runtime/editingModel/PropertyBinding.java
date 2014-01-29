/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.query.JavaBody;

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
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getGetter <em>Getter</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getValueProvider <em>Value Provider</em>}</li>
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
	 * Returns the value of the '<em><b>Getter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Getter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Getter</em>' containment reference.
	 * @see #setGetter(JavaBody)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getPropertyBinding_Getter()
	 * @model type="org.eclipse.emf.eef.runtime.query.JavaBody<org.eclipse.emf.ecore.EJavaObject>" containment="true"
	 * @generated
	 */
	JavaBody<Object> getGetter();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getGetter <em>Getter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Getter</em>' containment reference.
	 * @see #getGetter()
	 * @generated
	 */
	void setGetter(JavaBody<Object> value);

	/**
	 * Returns the value of the '<em><b>Value Provider</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Provider</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Provider</em>' containment reference.
	 * @see #setValueProvider(JavaBody)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getPropertyBinding_ValueProvider()
	 * @model type="org.eclipse.emf.eef.runtime.query.JavaBody<org.eclipse.emf.ecore.EEList<org.eclipse.emf.ecore.EJavaObject>>" containment="true"
	 * @generated
	 */
	JavaBody<EList<Object>> getValueProvider();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getValueProvider <em>Value Provider</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Provider</em>' containment reference.
	 * @see #getValueProvider()
	 * @generated
	 */
	void setValueProvider(JavaBody<EList<Object>> value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true" loaderDataType="org.eclipse.emf.eef.runtime.query.ClassLoader" loaderRequired="true" targetRequired="true"
	 * @generated
	 */
	Object getValue(ClassLoader loader, EObject target);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model loaderDataType="org.eclipse.emf.eef.runtime.query.ClassLoader" loaderRequired="true" targetRequired="true"
	 * @generated
	 */
	EList<Object> getChoiceOfValue(ClassLoader loader, EObject target);

} // PropertyBinding
