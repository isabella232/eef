/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.eeftests.bindingmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.eeftests.bindingmodel.Root#getSamples <em>Samples</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelPackage#getRoot()
 * @model
 * @generated
 */
public interface Root extends EObject {
	/**
	 * Returns the value of the '<em><b>Samples</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.eef.eeftests.bindingmodel.AbstractSample}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Samples</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Samples</em>' containment reference list.
	 * @see org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelPackage#getRoot_Samples()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractSample> getSamples();

} // Root
