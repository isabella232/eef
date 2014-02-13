/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.MultiValuedPropertyBinding;
import org.eclipse.emf.eef.runtime.query.JavaBody;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EStructural Feature Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.EStructuralFeatureBindingImpl#getAdder <em>Adder</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.EStructuralFeatureBindingImpl#getRemover <em>Remover</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.EStructuralFeatureBindingImpl#getFeature <em>Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EStructuralFeatureBindingImpl extends MonoValuedPropertyBindingImpl implements EStructuralFeatureBinding {
	/**
	 * The cached value of the '{@link #getAdder() <em>Adder</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdder()
	 * @generated
	 * @ordered
	 */
	protected JavaBody adder;
	/**
	 * The cached value of the '{@link #getRemover() <em>Remover</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemover()
	 * @generated
	 * @ordered
	 */
	protected JavaBody remover;
	/**
	 * The cached value of the '{@link #getFeature() <em>Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeature()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature feature;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EStructuralFeatureBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EditingModelPackage.Literals.ESTRUCTURAL_FEATURE_BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaBody getAdder() {
		return adder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAdder(JavaBody newAdder, NotificationChain msgs) {
		JavaBody oldAdder = adder;
		adder = newAdder;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__ADDER, oldAdder, newAdder);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdder(JavaBody newAdder) {
		if (newAdder != adder) {
			NotificationChain msgs = null;
			if (adder != null)
				msgs = ((InternalEObject)adder).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__ADDER, null, msgs);
			if (newAdder != null)
				msgs = ((InternalEObject)newAdder).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__ADDER, null, msgs);
			msgs = basicSetAdder(newAdder, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__ADDER, newAdder, newAdder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaBody getRemover() {
		return remover;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRemover(JavaBody newRemover, NotificationChain msgs) {
		JavaBody oldRemover = remover;
		remover = newRemover;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__REMOVER, oldRemover, newRemover);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemover(JavaBody newRemover) {
		if (newRemover != remover) {
			NotificationChain msgs = null;
			if (remover != null)
				msgs = ((InternalEObject)remover).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__REMOVER, null, msgs);
			if (newRemover != null)
				msgs = ((InternalEObject)newRemover).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__REMOVER, null, msgs);
			msgs = basicSetRemover(newRemover, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__REMOVER, newRemover, newRemover));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EStructuralFeature getFeature() {
		if (feature != null && feature.eIsProxy()) {
			InternalEObject oldFeature = (InternalEObject)feature;
			feature = (EStructuralFeature)eResolveProxy(oldFeature);
			if (feature != oldFeature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__FEATURE, oldFeature, feature));
			}
		}
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EStructuralFeature basicGetFeature() {
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeature(EStructuralFeature newFeature) {
		EStructuralFeature oldFeature = feature;
		feature = newFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__FEATURE, oldFeature, feature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__ADDER:
				return basicSetAdder(null, msgs);
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__REMOVER:
				return basicSetRemover(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__ADDER:
				return getAdder();
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__REMOVER:
				return getRemover();
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__FEATURE:
				if (resolve) return getFeature();
				return basicGetFeature();
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
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__ADDER:
				setAdder((JavaBody)newValue);
				return;
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__REMOVER:
				setRemover((JavaBody)newValue);
				return;
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__FEATURE:
				setFeature((EStructuralFeature)newValue);
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
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__ADDER:
				setAdder((JavaBody)null);
				return;
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__REMOVER:
				setRemover((JavaBody)null);
				return;
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__FEATURE:
				setFeature((EStructuralFeature)null);
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
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__ADDER:
				return adder != null;
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__REMOVER:
				return remover != null;
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__FEATURE:
				return feature != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == MultiValuedPropertyBinding.class) {
			switch (derivedFeatureID) {
				case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__ADDER: return EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__ADDER;
				case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__REMOVER: return EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__REMOVER;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == MultiValuedPropertyBinding.class) {
			switch (baseFeatureID) {
				case EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__ADDER: return EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__ADDER;
				case EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__REMOVER: return EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__REMOVER;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //EStructuralFeatureBindingImpl
