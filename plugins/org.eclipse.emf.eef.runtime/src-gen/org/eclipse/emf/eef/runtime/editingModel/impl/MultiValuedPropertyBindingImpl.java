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
import org.eclipse.emf.eef.runtime.editingModel.MultiValuedPropertyBinding;
import org.eclipse.emf.eef.runtime.query.JavaBody;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multi Valued Property Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.MultiValuedPropertyBindingImpl#getAdder <em>Adder</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.MultiValuedPropertyBindingImpl#getRemover <em>Remover</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MultiValuedPropertyBindingImpl extends PropertyBindingImpl implements MultiValuedPropertyBinding {
	/**
	 * The cached value of the '{@link #getAdder() <em>Adder</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdder()
	 * @generated
	 * @ordered
	 */
	protected JavaBody<Void> adder;

	/**
	 * The cached value of the '{@link #getRemover() <em>Remover</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemover()
	 * @generated
	 * @ordered
	 */
	protected JavaBody<Void> remover;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MultiValuedPropertyBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EditingModelPackage.Literals.MULTI_VALUED_PROPERTY_BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaBody<Void> getAdder() {
		return adder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAdder(JavaBody<Void> newAdder, NotificationChain msgs) {
		JavaBody<Void> oldAdder = adder;
		adder = newAdder;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__ADDER, oldAdder, newAdder);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdder(JavaBody<Void> newAdder) {
		if (newAdder != adder) {
			NotificationChain msgs = null;
			if (adder != null)
				msgs = ((InternalEObject)adder).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__ADDER, null, msgs);
			if (newAdder != null)
				msgs = ((InternalEObject)newAdder).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__ADDER, null, msgs);
			msgs = basicSetAdder(newAdder, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__ADDER, newAdder, newAdder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaBody<Void> getRemover() {
		return remover;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRemover(JavaBody<Void> newRemover, NotificationChain msgs) {
		JavaBody<Void> oldRemover = remover;
		remover = newRemover;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__REMOVER, oldRemover, newRemover);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemover(JavaBody<Void> newRemover) {
		if (newRemover != remover) {
			NotificationChain msgs = null;
			if (remover != null)
				msgs = ((InternalEObject)remover).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__REMOVER, null, msgs);
			if (newRemover != null)
				msgs = ((InternalEObject)newRemover).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__REMOVER, null, msgs);
			msgs = basicSetRemover(newRemover, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__REMOVER, newRemover, newRemover));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Void addValue(ClassLoader loader, EObject target, Object value) {
		if (getAdder() != null) {
			BasicEList<Object> parameters = new BasicEList<Object>();
			parameters.add(value);
			getAdder().invoke(loader, target, parameters);
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Void removeValue(ClassLoader loader, EObject target, Object value) {
		if (getRemover() != null) {
			BasicEList<Object> parameters = new BasicEList<Object>();
			parameters.add(value);
			getRemover().invoke(loader, target, parameters);
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
			case EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__ADDER:
				return basicSetAdder(null, msgs);
			case EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__REMOVER:
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
			case EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__ADDER:
				return getAdder();
			case EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__REMOVER:
				return getRemover();
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
			case EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__ADDER:
				setAdder((JavaBody<Void>)newValue);
				return;
			case EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__REMOVER:
				setRemover((JavaBody<Void>)newValue);
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
			case EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__ADDER:
				setAdder((JavaBody<Void>)null);
				return;
			case EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__REMOVER:
				setRemover((JavaBody<Void>)null);
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
			case EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__ADDER:
				return adder != null;
			case EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING__REMOVER:
				return remover != null;
		}
		return super.eIsSet(featureID);
	}

} //MultiValuedPropertyBindingImpl
