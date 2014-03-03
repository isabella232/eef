/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Properties Editing Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getBindings <em>Bindings</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getInvolvedModels <em>Involved Models</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getOptions <em>Options</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getPropertiesEditingModel()
 * @model
 * @extends EModelElement
 * @generated
 */
public interface PropertiesEditingModel extends EModelElement {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getPropertiesEditingModel_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Bindings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getEditingModel <em>Editing Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bindings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bindings</em>' containment reference list.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getPropertiesEditingModel_Bindings()
	 * @see org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getEditingModel
	 * @model opposite="editingModel" containment="true"
	 * @generated
	 */
	EList<EClassBinding> getBindings();

	/**
	 * Returns the value of the '<em><b>Involved Models</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Involved Models</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Involved Models</em>' reference list.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getPropertiesEditingModel_InvolvedModels()
	 * @model
	 * @generated
	 */
	EList<EObject> getInvolvedModels();

	/**
	 * Returns the value of the '<em><b>Options</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Options</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Options</em>' containment reference.
	 * @see #setOptions(EditingOptions)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getPropertiesEditingModel_Options()
	 * @model containment="true"
	 * @generated
	 */
	EditingOptions getOptions();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getOptions <em>Options</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Options</em>' containment reference.
	 * @see #getOptions()
	 * @generated
	 */
	void setOptions(EditingOptions value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getPropertiesEditingModel_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model eObjectRequired="true"
	 * @generated
	 */
	EClassBinding binding(EObject eObject);
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model eObjectRequired="true"
	 * @generated
	 */
	EClassBinding binding(EClass eClass);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model eObjectRequired="true"
	 * @generated
	 */
	EList<View> views(EObject eObject);

	EMFServiceProvider getEMFServiceProvider();
	
	void setEMFServiceProvider(EMFServiceProvider emfServiceProvider);

} // PropertiesEditingModel
