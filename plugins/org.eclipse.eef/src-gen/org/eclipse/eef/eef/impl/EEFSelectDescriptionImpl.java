/**
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.htm
 * Contributors: Obeo - initial API and implementation
 */
package org.eclipse.eef.eef.impl;

import org.eclipse.eef.eef.EEFSelectDescription;
import org.eclipse.eef.eef.EefPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EEF Select Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.eef.eef.impl.EEFSelectDescriptionImpl#getValueExpression <em>Value Expression</em>}</li>
 *   <li>{@link org.eclipse.eef.eef.impl.EEFSelectDescriptionImpl#getEditExpression <em>Edit Expression</em>}</li>
 *   <li>{@link org.eclipse.eef.eef.impl.EEFSelectDescriptionImpl#getCandidatesExpression <em>Candidates Expression</em>}</li>
 *   <li>{@link org.eclipse.eef.eef.impl.EEFSelectDescriptionImpl#isMultiple <em>Multiple</em>}</li>
 *   <li>{@link org.eclipse.eef.eef.impl.EEFSelectDescriptionImpl#getCandidateDisplayExpression <em>Candidate Display Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EEFSelectDescriptionImpl extends EEFCellWidgetDescriptionImpl implements EEFSelectDescription {
	/**
	 * The default value of the '{@link #getValueExpression() <em>Value Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValueExpression() <em>Value Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueExpression()
	 * @generated
	 * @ordered
	 */
	protected String valueExpression = VALUE_EXPRESSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getEditExpression() <em>Edit Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String EDIT_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEditExpression() <em>Edit Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditExpression()
	 * @generated
	 * @ordered
	 */
	protected String editExpression = EDIT_EXPRESSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getCandidatesExpression() <em>Candidates Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCandidatesExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String CANDIDATES_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCandidatesExpression() <em>Candidates Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCandidatesExpression()
	 * @generated
	 * @ordered
	 */
	protected String candidatesExpression = CANDIDATES_EXPRESSION_EDEFAULT;

	/**
	 * The default value of the '{@link #isMultiple() <em>Multiple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultiple()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MULTIPLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMultiple() <em>Multiple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultiple()
	 * @generated
	 * @ordered
	 */
	protected boolean multiple = MULTIPLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCandidateDisplayExpression() <em>Candidate Display Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCandidateDisplayExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String CANDIDATE_DISPLAY_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCandidateDisplayExpression() <em>Candidate Display Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCandidateDisplayExpression()
	 * @generated
	 * @ordered
	 */
	protected String candidateDisplayExpression = CANDIDATE_DISPLAY_EXPRESSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EEFSelectDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EefPackage.Literals.EEF_SELECT_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValueExpression() {
		return valueExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValueExpression(String newValueExpression) {
		String oldValueExpression = valueExpression;
		valueExpression = newValueExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EefPackage.EEF_SELECT_DESCRIPTION__VALUE_EXPRESSION,
					oldValueExpression, valueExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getEditExpression() {
		return editExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEditExpression(String newEditExpression) {
		String oldEditExpression = editExpression;
		editExpression = newEditExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EefPackage.EEF_SELECT_DESCRIPTION__EDIT_EXPRESSION,
					oldEditExpression, editExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCandidatesExpression() {
		return candidatesExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCandidatesExpression(String newCandidatesExpression) {
		String oldCandidatesExpression = candidatesExpression;
		candidatesExpression = newCandidatesExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EefPackage.EEF_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION, oldCandidatesExpression,
					candidatesExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isMultiple() {
		return multiple;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMultiple(boolean newMultiple) {
		boolean oldMultiple = multiple;
		multiple = newMultiple;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EefPackage.EEF_SELECT_DESCRIPTION__MULTIPLE,
					oldMultiple, multiple));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCandidateDisplayExpression() {
		return candidateDisplayExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCandidateDisplayExpression(String newCandidateDisplayExpression) {
		String oldCandidateDisplayExpression = candidateDisplayExpression;
		candidateDisplayExpression = newCandidateDisplayExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EefPackage.EEF_SELECT_DESCRIPTION__CANDIDATE_DISPLAY_EXPRESSION, oldCandidateDisplayExpression,
					candidateDisplayExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EefPackage.EEF_SELECT_DESCRIPTION__VALUE_EXPRESSION:
			return getValueExpression();
		case EefPackage.EEF_SELECT_DESCRIPTION__EDIT_EXPRESSION:
			return getEditExpression();
		case EefPackage.EEF_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION:
			return getCandidatesExpression();
		case EefPackage.EEF_SELECT_DESCRIPTION__MULTIPLE:
			return isMultiple();
		case EefPackage.EEF_SELECT_DESCRIPTION__CANDIDATE_DISPLAY_EXPRESSION:
			return getCandidateDisplayExpression();
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
		case EefPackage.EEF_SELECT_DESCRIPTION__VALUE_EXPRESSION:
			setValueExpression((String) newValue);
			return;
		case EefPackage.EEF_SELECT_DESCRIPTION__EDIT_EXPRESSION:
			setEditExpression((String) newValue);
			return;
		case EefPackage.EEF_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION:
			setCandidatesExpression((String) newValue);
			return;
		case EefPackage.EEF_SELECT_DESCRIPTION__MULTIPLE:
			setMultiple((Boolean) newValue);
			return;
		case EefPackage.EEF_SELECT_DESCRIPTION__CANDIDATE_DISPLAY_EXPRESSION:
			setCandidateDisplayExpression((String) newValue);
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
		case EefPackage.EEF_SELECT_DESCRIPTION__VALUE_EXPRESSION:
			setValueExpression(VALUE_EXPRESSION_EDEFAULT);
			return;
		case EefPackage.EEF_SELECT_DESCRIPTION__EDIT_EXPRESSION:
			setEditExpression(EDIT_EXPRESSION_EDEFAULT);
			return;
		case EefPackage.EEF_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION:
			setCandidatesExpression(CANDIDATES_EXPRESSION_EDEFAULT);
			return;
		case EefPackage.EEF_SELECT_DESCRIPTION__MULTIPLE:
			setMultiple(MULTIPLE_EDEFAULT);
			return;
		case EefPackage.EEF_SELECT_DESCRIPTION__CANDIDATE_DISPLAY_EXPRESSION:
			setCandidateDisplayExpression(CANDIDATE_DISPLAY_EXPRESSION_EDEFAULT);
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
		case EefPackage.EEF_SELECT_DESCRIPTION__VALUE_EXPRESSION:
			return VALUE_EXPRESSION_EDEFAULT == null ? valueExpression != null
					: !VALUE_EXPRESSION_EDEFAULT.equals(valueExpression);
		case EefPackage.EEF_SELECT_DESCRIPTION__EDIT_EXPRESSION:
			return EDIT_EXPRESSION_EDEFAULT == null ? editExpression != null
					: !EDIT_EXPRESSION_EDEFAULT.equals(editExpression);
		case EefPackage.EEF_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION:
			return CANDIDATES_EXPRESSION_EDEFAULT == null ? candidatesExpression != null
					: !CANDIDATES_EXPRESSION_EDEFAULT.equals(candidatesExpression);
		case EefPackage.EEF_SELECT_DESCRIPTION__MULTIPLE:
			return multiple != MULTIPLE_EDEFAULT;
		case EefPackage.EEF_SELECT_DESCRIPTION__CANDIDATE_DISPLAY_EXPRESSION:
			return CANDIDATE_DISPLAY_EXPRESSION_EDEFAULT == null ? candidateDisplayExpression != null
					: !CANDIDATE_DISPLAY_EXPRESSION_EDEFAULT.equals(candidateDisplayExpression);
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
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (valueExpression: "); //$NON-NLS-1$
		result.append(valueExpression);
		result.append(", editExpression: "); //$NON-NLS-1$
		result.append(editExpression);
		result.append(", candidatesExpression: "); //$NON-NLS-1$
		result.append(candidatesExpression);
		result.append(", multiple: "); //$NON-NLS-1$
		result.append(multiple);
		result.append(", candidateDisplayExpression: "); //$NON-NLS-1$
		result.append(candidateDisplayExpression);
		result.append(')');
		return result.toString();
	}

} //EEFSelectDescriptionImpl
