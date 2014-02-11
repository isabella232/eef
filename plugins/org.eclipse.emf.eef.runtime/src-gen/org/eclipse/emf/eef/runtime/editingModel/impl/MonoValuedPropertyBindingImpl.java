/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.MonoValuedPropertyBinding;
import org.eclipse.emf.eef.runtime.query.JavaBody;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mono Valued Property Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.MonoValuedPropertyBindingImpl#getSetter <em>Setter</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.MonoValuedPropertyBindingImpl#getUnsetter <em>Unsetter</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MonoValuedPropertyBindingImpl extends PropertyBindingImpl implements MonoValuedPropertyBinding {
	/**
	 * The cached value of the '{@link #getSetter() <em>Setter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSetter()
	 * @generated
	 * @ordered
	 */
	protected JavaBody<Void> setter;

	/**
	 * The cached value of the '{@link #getUnsetter() <em>Unsetter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnsetter()
	 * @generated
	 * @ordered
	 */
	protected JavaBody<Void> unsetter;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MonoValuedPropertyBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EditingModelPackage.Literals.MONO_VALUED_PROPERTY_BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaBody<Void> getSetter() {
		return setter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSetter(JavaBody<Void> newSetter, NotificationChain msgs) {
		JavaBody<Void> oldSetter = setter;
		setter = newSetter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__SETTER, oldSetter, newSetter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSetter(JavaBody<Void> newSetter) {
		if (newSetter != setter) {
			NotificationChain msgs = null;
			if (setter != null)
				msgs = ((InternalEObject)setter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__SETTER, null, msgs);
			if (newSetter != null)
				msgs = ((InternalEObject)newSetter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__SETTER, null, msgs);
			msgs = basicSetSetter(newSetter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__SETTER, newSetter, newSetter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaBody<Void> getUnsetter() {
		return unsetter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUnsetter(JavaBody<Void> newUnsetter, NotificationChain msgs) {
		JavaBody<Void> oldUnsetter = unsetter;
		unsetter = newUnsetter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__UNSETTER, oldUnsetter, newUnsetter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnsetter(JavaBody<Void> newUnsetter) {
		if (newUnsetter != unsetter) {
			NotificationChain msgs = null;
			if (unsetter != null)
				msgs = ((InternalEObject)unsetter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__UNSETTER, null, msgs);
			if (newUnsetter != null)
				msgs = ((InternalEObject)newUnsetter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__UNSETTER, null, msgs);
			msgs = basicSetUnsetter(newUnsetter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__UNSETTER, newUnsetter, newUnsetter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Void setValue(ClassLoader loader, EObject target, Object value) {
		if (getSetter() != null) {
			BasicEList<Object> parameters = new BasicEList<Object>();
			parameters.add(value);
			getSetter().invoke(loader, target, parameters);
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Void unsetValue(ClassLoader loader, EObject target, Object value) {
		if (getUnsetter() != null) {
			BasicEList<Object> parameters = new BasicEList<Object>();
			if (value != null) {	
				parameters.add(value);
			}
			getSetter().invoke(loader, target, parameters);
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__SETTER:
				return basicSetSetter(null, msgs);
			case EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__UNSETTER:
				return basicSetUnsetter(null, msgs);
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
			case EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__SETTER:
				return getSetter();
			case EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__UNSETTER:
				return getUnsetter();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__SETTER:
				setSetter((JavaBody<Void>)newValue);
				return;
			case EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__UNSETTER:
				setUnsetter((JavaBody<Void>)newValue);
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
			case EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__SETTER:
				setSetter((JavaBody<Void>)null);
				return;
			case EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__UNSETTER:
				setUnsetter((JavaBody<Void>)null);
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
			case EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__SETTER:
				return setter != null;
			case EditingModelPackage.MONO_VALUED_PROPERTY_BINDING__UNSETTER:
				return unsetter != null;
		}
		return super.eIsSet(featureID);
	}

} //MonoValuedPropertyBindingImpl
