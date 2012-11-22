/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.eefnr.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.eef.eefnr.AbstractSample;
import org.eclipse.emf.eef.eefnr.EefnrPackage;
import org.eclipse.emf.eef.eefnr.SingleCompositionEditorSample;
import org.eclipse.emf.eef.eefnr.TextSample;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Single Composition Editor Sample</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.eefnr.impl.SingleCompositionEditorSampleImpl#getSingleCompositionEditorRequiredProperty <em>Single Composition Editor Required Property</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.eefnr.impl.SingleCompositionEditorSampleImpl#getSingleCompositionEditorrOptionalProperty <em>Single Composition Editorr Optional Property</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.eefnr.impl.SingleCompositionEditorSampleImpl#getSingleCompositionEditorRequiredAbstractProperty <em>Single Composition Editor Required Abstract Property</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.eefnr.impl.SingleCompositionEditorSampleImpl#getSingleCompositionEditorOptionalAbstractProperty <em>Single Composition Editor Optional Abstract Property</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SingleCompositionEditorSampleImpl extends AbstractSampleImpl implements SingleCompositionEditorSample {
	/**
	 * The cached value of the '{@link #getSingleCompositionEditorRequiredProperty() <em>Single Composition Editor Required Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleCompositionEditorRequiredProperty()
	 * @generated
	 * @ordered
	 */
	protected TextSample singleCompositionEditorRequiredProperty;

	/**
	 * The cached value of the '{@link #getSingleCompositionEditorrOptionalProperty() <em>Single Composition Editorr Optional Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleCompositionEditorrOptionalProperty()
	 * @generated
	 * @ordered
	 */
	protected TextSample singleCompositionEditorrOptionalProperty;

	/**
	 * The cached value of the '{@link #getSingleCompositionEditorRequiredAbstractProperty() <em>Single Composition Editor Required Abstract Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleCompositionEditorRequiredAbstractProperty()
	 * @generated
	 * @ordered
	 */
	protected AbstractSample singleCompositionEditorRequiredAbstractProperty;

	/**
	 * The cached value of the '{@link #getSingleCompositionEditorOptionalAbstractProperty() <em>Single Composition Editor Optional Abstract Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSingleCompositionEditorOptionalAbstractProperty()
	 * @generated
	 * @ordered
	 */
	protected AbstractSample singleCompositionEditorOptionalAbstractProperty;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SingleCompositionEditorSampleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EefnrPackage.Literals.SINGLE_COMPOSITION_EDITOR_SAMPLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextSample getSingleCompositionEditorRequiredProperty() {
		return singleCompositionEditorRequiredProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSingleCompositionEditorRequiredProperty(TextSample newSingleCompositionEditorRequiredProperty, NotificationChain msgs) {
		TextSample oldSingleCompositionEditorRequiredProperty = singleCompositionEditorRequiredProperty;
		singleCompositionEditorRequiredProperty = newSingleCompositionEditorRequiredProperty;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_PROPERTY, oldSingleCompositionEditorRequiredProperty, newSingleCompositionEditorRequiredProperty);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleCompositionEditorRequiredProperty(TextSample newSingleCompositionEditorRequiredProperty) {
		if (newSingleCompositionEditorRequiredProperty != singleCompositionEditorRequiredProperty) {
			NotificationChain msgs = null;
			if (singleCompositionEditorRequiredProperty != null)
				msgs = ((InternalEObject)singleCompositionEditorRequiredProperty).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_PROPERTY, null, msgs);
			if (newSingleCompositionEditorRequiredProperty != null)
				msgs = ((InternalEObject)newSingleCompositionEditorRequiredProperty).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_PROPERTY, null, msgs);
			msgs = basicSetSingleCompositionEditorRequiredProperty(newSingleCompositionEditorRequiredProperty, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_PROPERTY, newSingleCompositionEditorRequiredProperty, newSingleCompositionEditorRequiredProperty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextSample getSingleCompositionEditorrOptionalProperty() {
		return singleCompositionEditorrOptionalProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSingleCompositionEditorrOptionalProperty(TextSample newSingleCompositionEditorrOptionalProperty, NotificationChain msgs) {
		TextSample oldSingleCompositionEditorrOptionalProperty = singleCompositionEditorrOptionalProperty;
		singleCompositionEditorrOptionalProperty = newSingleCompositionEditorrOptionalProperty;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITORR_OPTIONAL_PROPERTY, oldSingleCompositionEditorrOptionalProperty, newSingleCompositionEditorrOptionalProperty);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleCompositionEditorrOptionalProperty(TextSample newSingleCompositionEditorrOptionalProperty) {
		if (newSingleCompositionEditorrOptionalProperty != singleCompositionEditorrOptionalProperty) {
			NotificationChain msgs = null;
			if (singleCompositionEditorrOptionalProperty != null)
				msgs = ((InternalEObject)singleCompositionEditorrOptionalProperty).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITORR_OPTIONAL_PROPERTY, null, msgs);
			if (newSingleCompositionEditorrOptionalProperty != null)
				msgs = ((InternalEObject)newSingleCompositionEditorrOptionalProperty).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITORR_OPTIONAL_PROPERTY, null, msgs);
			msgs = basicSetSingleCompositionEditorrOptionalProperty(newSingleCompositionEditorrOptionalProperty, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITORR_OPTIONAL_PROPERTY, newSingleCompositionEditorrOptionalProperty, newSingleCompositionEditorrOptionalProperty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractSample getSingleCompositionEditorRequiredAbstractProperty() {
		return singleCompositionEditorRequiredAbstractProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSingleCompositionEditorRequiredAbstractProperty(AbstractSample newSingleCompositionEditorRequiredAbstractProperty, NotificationChain msgs) {
		AbstractSample oldSingleCompositionEditorRequiredAbstractProperty = singleCompositionEditorRequiredAbstractProperty;
		singleCompositionEditorRequiredAbstractProperty = newSingleCompositionEditorRequiredAbstractProperty;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_ABSTRACT_PROPERTY, oldSingleCompositionEditorRequiredAbstractProperty, newSingleCompositionEditorRequiredAbstractProperty);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleCompositionEditorRequiredAbstractProperty(AbstractSample newSingleCompositionEditorRequiredAbstractProperty) {
		if (newSingleCompositionEditorRequiredAbstractProperty != singleCompositionEditorRequiredAbstractProperty) {
			NotificationChain msgs = null;
			if (singleCompositionEditorRequiredAbstractProperty != null)
				msgs = ((InternalEObject)singleCompositionEditorRequiredAbstractProperty).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_ABSTRACT_PROPERTY, null, msgs);
			if (newSingleCompositionEditorRequiredAbstractProperty != null)
				msgs = ((InternalEObject)newSingleCompositionEditorRequiredAbstractProperty).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_ABSTRACT_PROPERTY, null, msgs);
			msgs = basicSetSingleCompositionEditorRequiredAbstractProperty(newSingleCompositionEditorRequiredAbstractProperty, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_ABSTRACT_PROPERTY, newSingleCompositionEditorRequiredAbstractProperty, newSingleCompositionEditorRequiredAbstractProperty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractSample getSingleCompositionEditorOptionalAbstractProperty() {
		return singleCompositionEditorOptionalAbstractProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSingleCompositionEditorOptionalAbstractProperty(AbstractSample newSingleCompositionEditorOptionalAbstractProperty, NotificationChain msgs) {
		AbstractSample oldSingleCompositionEditorOptionalAbstractProperty = singleCompositionEditorOptionalAbstractProperty;
		singleCompositionEditorOptionalAbstractProperty = newSingleCompositionEditorOptionalAbstractProperty;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_OPTIONAL_ABSTRACT_PROPERTY, oldSingleCompositionEditorOptionalAbstractProperty, newSingleCompositionEditorOptionalAbstractProperty);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleCompositionEditorOptionalAbstractProperty(AbstractSample newSingleCompositionEditorOptionalAbstractProperty) {
		if (newSingleCompositionEditorOptionalAbstractProperty != singleCompositionEditorOptionalAbstractProperty) {
			NotificationChain msgs = null;
			if (singleCompositionEditorOptionalAbstractProperty != null)
				msgs = ((InternalEObject)singleCompositionEditorOptionalAbstractProperty).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_OPTIONAL_ABSTRACT_PROPERTY, null, msgs);
			if (newSingleCompositionEditorOptionalAbstractProperty != null)
				msgs = ((InternalEObject)newSingleCompositionEditorOptionalAbstractProperty).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_OPTIONAL_ABSTRACT_PROPERTY, null, msgs);
			msgs = basicSetSingleCompositionEditorOptionalAbstractProperty(newSingleCompositionEditorOptionalAbstractProperty, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_OPTIONAL_ABSTRACT_PROPERTY, newSingleCompositionEditorOptionalAbstractProperty, newSingleCompositionEditorOptionalAbstractProperty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_PROPERTY:
				return basicSetSingleCompositionEditorRequiredProperty(null, msgs);
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITORR_OPTIONAL_PROPERTY:
				return basicSetSingleCompositionEditorrOptionalProperty(null, msgs);
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_ABSTRACT_PROPERTY:
				return basicSetSingleCompositionEditorRequiredAbstractProperty(null, msgs);
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_OPTIONAL_ABSTRACT_PROPERTY:
				return basicSetSingleCompositionEditorOptionalAbstractProperty(null, msgs);
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
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_PROPERTY:
				return getSingleCompositionEditorRequiredProperty();
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITORR_OPTIONAL_PROPERTY:
				return getSingleCompositionEditorrOptionalProperty();
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_ABSTRACT_PROPERTY:
				return getSingleCompositionEditorRequiredAbstractProperty();
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_OPTIONAL_ABSTRACT_PROPERTY:
				return getSingleCompositionEditorOptionalAbstractProperty();
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
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_PROPERTY:
				setSingleCompositionEditorRequiredProperty((TextSample)newValue);
				return;
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITORR_OPTIONAL_PROPERTY:
				setSingleCompositionEditorrOptionalProperty((TextSample)newValue);
				return;
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_ABSTRACT_PROPERTY:
				setSingleCompositionEditorRequiredAbstractProperty((AbstractSample)newValue);
				return;
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_OPTIONAL_ABSTRACT_PROPERTY:
				setSingleCompositionEditorOptionalAbstractProperty((AbstractSample)newValue);
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
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_PROPERTY:
				setSingleCompositionEditorRequiredProperty((TextSample)null);
				return;
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITORR_OPTIONAL_PROPERTY:
				setSingleCompositionEditorrOptionalProperty((TextSample)null);
				return;
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_ABSTRACT_PROPERTY:
				setSingleCompositionEditorRequiredAbstractProperty((AbstractSample)null);
				return;
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_OPTIONAL_ABSTRACT_PROPERTY:
				setSingleCompositionEditorOptionalAbstractProperty((AbstractSample)null);
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
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_PROPERTY:
				return singleCompositionEditorRequiredProperty != null;
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITORR_OPTIONAL_PROPERTY:
				return singleCompositionEditorrOptionalProperty != null;
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_REQUIRED_ABSTRACT_PROPERTY:
				return singleCompositionEditorRequiredAbstractProperty != null;
			case EefnrPackage.SINGLE_COMPOSITION_EDITOR_SAMPLE__SINGLE_COMPOSITION_EDITOR_OPTIONAL_ABSTRACT_PROPERTY:
				return singleCompositionEditorOptionalAbstractProperty != null;
		}
		return super.eIsSet(featureID);
	}

} //SingleCompositionEditorSampleImpl
