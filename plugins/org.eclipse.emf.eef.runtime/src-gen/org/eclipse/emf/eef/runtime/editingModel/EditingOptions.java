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
 * A representation of the model object '<em><b>Editing Options</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.EditingOptions#getFeatureDocumentationProvider <em>Feature Documentation Provider</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getEditingOptions()
 * @model
 * @generated
 */
public interface EditingOptions extends EObject {
	/**
	 * Returns the value of the '<em><b>Feature Documentation Provider</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature Documentation Provider</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Documentation Provider</em>' attribute.
	 * @see org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider
	 * @see #setFeatureDocumentationProvider(FeatureDocumentationProvider)
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getEditingOptions_FeatureDocumentationProvider()
	 * @model
	 * @generated
	 */
	FeatureDocumentationProvider getFeatureDocumentationProvider();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.editingModel.EditingOptions#getFeatureDocumentationProvider <em>Feature Documentation Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature Documentation Provider</em>' attribute.
	 * @see org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider
	 * @see #getFeatureDocumentationProvider()
	 * @generated
	 */
	void setFeatureDocumentationProvider(FeatureDocumentationProvider value);

} // EditingOptions
