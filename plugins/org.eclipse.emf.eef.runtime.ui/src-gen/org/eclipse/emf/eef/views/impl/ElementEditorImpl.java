/**
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 * 
 */
package org.eclipse.emf.eef.views.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.eef.runtime.ui.internal.util.NamingHelper;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.IdentifiedElement;
import org.eclipse.emf.eef.views.ViewsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Editor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.views.impl.ElementEditorImpl#getQualifiedIdentifier <em>Qualified Identifier</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.views.impl.ElementEditorImpl#isReadOnly <em>Read Only</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.views.impl.ElementEditorImpl#isNameAsLabel <em>Name As Label</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.views.impl.ElementEditorImpl#getSubElementEditors <em>Sub Element Editors</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ElementEditorImpl extends ViewElementImpl implements ElementEditor {
	/**
	 * The default value of the '{@link #getQualifiedIdentifier() <em>Qualified Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedIdentifier()
	 * @generated
	 * @ordered
	 */
	protected static final String QUALIFIED_IDENTIFIER_EDEFAULT = null;

	/**
	 * The default value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadOnly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean READ_ONLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadOnly()
	 * @generated
	 * @ordered
	 */
	protected boolean readOnly = READ_ONLY_EDEFAULT;

	/**
	 * The default value of the '{@link #isNameAsLabel() <em>Name As Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNameAsLabel()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NAME_AS_LABEL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNameAsLabel() <em>Name As Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNameAsLabel()
	 * @generated
	 * @ordered
	 */
	protected boolean nameAsLabel = NAME_AS_LABEL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSubElementEditors() <em>Sub Element Editors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubElementEditors()
	 * @generated
	 * @ordered
	 */
	protected EList<ElementEditor> subElementEditors;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ElementEditorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ViewsPackage.Literals.ELEMENT_EDITOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getQualifiedIdentifier() {
		if (name == null) {
			setName("");
		}
		StringBuilder result = new StringBuilder(name);
		result.append(NamingHelper.nameDiscriminator(this));
		EObject container = this.eContainer();
		while (container != null) {
			if (container instanceof IdentifiedElement) {
				result.insert(0, "::"); //$NON-NLS-1$
				result.insert(0, ((IdentifiedElement)container).getQualifiedIdentifier());
				return result.toString();
			}
			container = container.eContainer();
		}
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReadOnly(boolean newReadOnly) {
		boolean oldReadOnly = readOnly;
		readOnly = newReadOnly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewsPackage.ELEMENT_EDITOR__READ_ONLY, oldReadOnly, readOnly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNameAsLabel() {
		return nameAsLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameAsLabel(boolean newNameAsLabel) {
		boolean oldNameAsLabel = nameAsLabel;
		nameAsLabel = newNameAsLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewsPackage.ELEMENT_EDITOR__NAME_AS_LABEL, oldNameAsLabel, nameAsLabel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ElementEditor> getSubElementEditors() {
		if (subElementEditors == null) {
			subElementEditors = new EObjectContainmentEList<ElementEditor>(ElementEditor.class, this, ViewsPackage.ELEMENT_EDITOR__SUB_ELEMENT_EDITORS);
		}
		return subElementEditors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ViewsPackage.ELEMENT_EDITOR__SUB_ELEMENT_EDITORS:
				return ((InternalEList<?>)getSubElementEditors()).basicRemove(otherEnd, msgs);
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
			case ViewsPackage.ELEMENT_EDITOR__QUALIFIED_IDENTIFIER:
				return getQualifiedIdentifier();
			case ViewsPackage.ELEMENT_EDITOR__READ_ONLY:
				return isReadOnly();
			case ViewsPackage.ELEMENT_EDITOR__NAME_AS_LABEL:
				return isNameAsLabel();
			case ViewsPackage.ELEMENT_EDITOR__SUB_ELEMENT_EDITORS:
				return getSubElementEditors();
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
			case ViewsPackage.ELEMENT_EDITOR__READ_ONLY:
				setReadOnly((Boolean)newValue);
				return;
			case ViewsPackage.ELEMENT_EDITOR__NAME_AS_LABEL:
				setNameAsLabel((Boolean)newValue);
				return;
			case ViewsPackage.ELEMENT_EDITOR__SUB_ELEMENT_EDITORS:
				getSubElementEditors().clear();
				getSubElementEditors().addAll((Collection<? extends ElementEditor>)newValue);
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
			case ViewsPackage.ELEMENT_EDITOR__READ_ONLY:
				setReadOnly(READ_ONLY_EDEFAULT);
				return;
			case ViewsPackage.ELEMENT_EDITOR__NAME_AS_LABEL:
				setNameAsLabel(NAME_AS_LABEL_EDEFAULT);
				return;
			case ViewsPackage.ELEMENT_EDITOR__SUB_ELEMENT_EDITORS:
				getSubElementEditors().clear();
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
			case ViewsPackage.ELEMENT_EDITOR__QUALIFIED_IDENTIFIER:
				return QUALIFIED_IDENTIFIER_EDEFAULT == null ? getQualifiedIdentifier() != null : !QUALIFIED_IDENTIFIER_EDEFAULT.equals(getQualifiedIdentifier());
			case ViewsPackage.ELEMENT_EDITOR__READ_ONLY:
				return readOnly != READ_ONLY_EDEFAULT;
			case ViewsPackage.ELEMENT_EDITOR__NAME_AS_LABEL:
				return nameAsLabel != NAME_AS_LABEL_EDEFAULT;
			case ViewsPackage.ELEMENT_EDITOR__SUB_ELEMENT_EDITORS:
				return subElementEditors != null && !subElementEditors.isEmpty();
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
		if (baseClass == IdentifiedElement.class) {
			switch (derivedFeatureID) {
				case ViewsPackage.ELEMENT_EDITOR__QUALIFIED_IDENTIFIER: return ViewsPackage.IDENTIFIED_ELEMENT__QUALIFIED_IDENTIFIER;
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
		if (baseClass == IdentifiedElement.class) {
			switch (baseFeatureID) {
				case ViewsPackage.IDENTIFIED_ELEMENT__QUALIFIED_IDENTIFIER: return ViewsPackage.ELEMENT_EDITOR__QUALIFIED_IDENTIFIER;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (readOnly: ");
		result.append(readOnly);
		result.append(", nameAsLabel: ");
		result.append(nameAsLabel);
		result.append(')');
		return result.toString();
	}

} //ElementEditorImpl
