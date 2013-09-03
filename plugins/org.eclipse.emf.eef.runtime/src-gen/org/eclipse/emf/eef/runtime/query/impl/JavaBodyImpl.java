/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.query.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.eef.runtime.query.JavaBody;
import org.eclipse.emf.eef.runtime.query.QueryPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Java Body</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.query.impl.JavaBodyImpl#getQualifiedClass <em>Qualified Class</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.query.impl.JavaBodyImpl#getMethod <em>Method</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.query.impl.JavaBodyImpl#isStatic <em>Static</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JavaBodyImpl<TYPE> extends BodyImpl<TYPE> implements JavaBody<TYPE> {
	/**
	 * The default value of the '{@link #getQualifiedClass() <em>Qualified Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedClass()
	 * @generated
	 * @ordered
	 */
	protected static final String QUALIFIED_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQualifiedClass() <em>Qualified Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedClass()
	 * @generated
	 * @ordered
	 */
	protected String qualifiedClass = QUALIFIED_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMethod() <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethod()
	 * @generated
	 * @ordered
	 */
	protected static final String METHOD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMethod() <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethod()
	 * @generated
	 * @ordered
	 */
	protected String method = METHOD_EDEFAULT;

	/**
	 * The default value of the '{@link #isStatic() <em>Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStatic()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STATIC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isStatic() <em>Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStatic()
	 * @generated
	 * @ordered
	 */
	protected boolean static_ = STATIC_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaBodyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return QueryPackage.Literals.JAVA_BODY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getQualifiedClass() {
		return qualifiedClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQualifiedClass(String newQualifiedClass) {
		String oldQualifiedClass = qualifiedClass;
		qualifiedClass = newQualifiedClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.JAVA_BODY__QUALIFIED_CLASS, oldQualifiedClass, qualifiedClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMethod(String newMethod) {
		String oldMethod = method;
		method = newMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.JAVA_BODY__METHOD, oldMethod, method));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStatic() {
		return static_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatic(boolean newStatic) {
		boolean oldStatic = static_;
		static_ = newStatic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.JAVA_BODY__STATIC, oldStatic, static_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case QueryPackage.JAVA_BODY__QUALIFIED_CLASS:
				return getQualifiedClass();
			case QueryPackage.JAVA_BODY__METHOD:
				return getMethod();
			case QueryPackage.JAVA_BODY__STATIC:
				return isStatic();
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
			case QueryPackage.JAVA_BODY__QUALIFIED_CLASS:
				setQualifiedClass((String)newValue);
				return;
			case QueryPackage.JAVA_BODY__METHOD:
				setMethod((String)newValue);
				return;
			case QueryPackage.JAVA_BODY__STATIC:
				setStatic((Boolean)newValue);
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
			case QueryPackage.JAVA_BODY__QUALIFIED_CLASS:
				setQualifiedClass(QUALIFIED_CLASS_EDEFAULT);
				return;
			case QueryPackage.JAVA_BODY__METHOD:
				setMethod(METHOD_EDEFAULT);
				return;
			case QueryPackage.JAVA_BODY__STATIC:
				setStatic(STATIC_EDEFAULT);
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
			case QueryPackage.JAVA_BODY__QUALIFIED_CLASS:
				return QUALIFIED_CLASS_EDEFAULT == null ? qualifiedClass != null : !QUALIFIED_CLASS_EDEFAULT.equals(qualifiedClass);
			case QueryPackage.JAVA_BODY__METHOD:
				return METHOD_EDEFAULT == null ? method != null : !METHOD_EDEFAULT.equals(method);
			case QueryPackage.JAVA_BODY__STATIC:
				return static_ != STATIC_EDEFAULT;
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
		result.append(" (qualifiedClass: ");
		result.append(qualifiedClass);
		result.append(", method: ");
		result.append(method);
		result.append(", static: ");
		result.append(static_);
		result.append(')');
		return result.toString();
	}

} //JavaBodyImpl
