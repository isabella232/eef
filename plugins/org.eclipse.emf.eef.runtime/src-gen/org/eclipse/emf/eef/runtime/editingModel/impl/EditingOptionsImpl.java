/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EModelElementImpl;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.EditingOptions;
import org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Editing Options</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.EditingOptionsImpl#getFeatureDocumentationProvider <em>Feature Documentation Provider</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EditingOptionsImpl extends EModelElementImpl implements EditingOptions {
	/**
	 * The default value of the '{@link #getFeatureDocumentationProvider() <em>Feature Documentation Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureDocumentationProvider()
	 * @generated
	 * @ordered
	 */
	protected static final FeatureDocumentationProvider FEATURE_DOCUMENTATION_PROVIDER_EDEFAULT = FeatureDocumentationProvider.GENMODEL_PROPERTY_DESCRIPTION;

	/**
	 * The cached value of the '{@link #getFeatureDocumentationProvider() <em>Feature Documentation Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureDocumentationProvider()
	 * @generated
	 * @ordered
	 */
	protected FeatureDocumentationProvider featureDocumentationProvider = FEATURE_DOCUMENTATION_PROVIDER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EditingOptionsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EditingModelPackage.Literals.EDITING_OPTIONS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureDocumentationProvider getFeatureDocumentationProvider() {
		return featureDocumentationProvider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeatureDocumentationProvider(FeatureDocumentationProvider newFeatureDocumentationProvider) {
		FeatureDocumentationProvider oldFeatureDocumentationProvider = featureDocumentationProvider;
		featureDocumentationProvider = newFeatureDocumentationProvider == null ? FEATURE_DOCUMENTATION_PROVIDER_EDEFAULT : newFeatureDocumentationProvider;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditingModelPackage.EDITING_OPTIONS__FEATURE_DOCUMENTATION_PROVIDER, oldFeatureDocumentationProvider, featureDocumentationProvider));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EditingModelPackage.EDITING_OPTIONS__FEATURE_DOCUMENTATION_PROVIDER:
				return getFeatureDocumentationProvider();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EditingModelPackage.EDITING_OPTIONS__FEATURE_DOCUMENTATION_PROVIDER:
				setFeatureDocumentationProvider((FeatureDocumentationProvider)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case EditingModelPackage.EDITING_OPTIONS__FEATURE_DOCUMENTATION_PROVIDER:
				setFeatureDocumentationProvider(FEATURE_DOCUMENTATION_PROVIDER_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case EditingModelPackage.EDITING_OPTIONS__FEATURE_DOCUMENTATION_PROVIDER:
				return featureDocumentationProvider != FEATURE_DOCUMENTATION_PROVIDER_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (featureDocumentationProvider: ");
		result.append(featureDocumentationProvider);
		result.append(')');
		return result.toString();
	}

} //EditingOptionsImpl
