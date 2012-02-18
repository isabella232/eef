/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage
 * @generated
 */
public interface EditingModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EditingModelFactory eINSTANCE = org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Properties Editing Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Properties Editing Model</em>'.
	 * @generated
	 */
	PropertiesEditingModel createPropertiesEditingModel();

	/**
	 * Returns a new object of class '<em>EClass Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EClass Binding</em>'.
	 * @generated
	 */
	EClassBinding createEClassBinding();

	/**
	 * Returns a new object of class '<em>Java View</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java View</em>'.
	 * @generated
	 */
	JavaView createJavaView();

	/**
	 * Returns a new object of class '<em>Property Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Binding</em>'.
	 * @generated
	 */
	PropertyBinding createPropertyBinding();

	/**
	 * Returns a new object of class '<em>EObject View</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EObject View</em>'.
	 * @generated
	 */
	EObjectView createEObjectView();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EditingModelPackage getEditingModelPackage();

} //EditingModelFactory
