/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EModelElementImpl;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.eef.runtime.binding.PropertyBindingCustomizer;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.Editor;
import org.eclipse.emf.eef.runtime.editingModel.EditorSettings;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.PropertyBindingImpl#getEditor <em>Editor</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.PropertyBindingImpl#getSubPropertyBindings <em>Sub Property Bindings</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.PropertyBindingImpl#getSettings <em>Settings</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.PropertyBindingImpl#getBindingCustomizer <em>Binding Customizer</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PropertyBindingImpl extends EModelElementImpl implements PropertyBinding {
	/**
	 * The cached value of the '{@link #getEditor() <em>Editor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditor()
	 * @generated
	 * @ordered
	 */
	protected Editor editor;

	/**
	 * The cached value of the '{@link #getSubPropertyBindings() <em>Sub Property Bindings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubPropertyBindings()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyBinding> subPropertyBindings;

	/**
	 * The cached value of the '{@link #getSettings() <em>Settings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSettings()
	 * @generated
	 * @ordered
	 */
	protected EList<EditorSettings> settings;

	/**
	 * The default value of the '{@link #getBindingCustomizer() <em>Binding Customizer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBindingCustomizer()
	 * @generated
	 * @ordered
	 */
	protected static final String BINDING_CUSTOMIZER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBindingCustomizer() <em>Binding Customizer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBindingCustomizer()
	 * @generated
	 * @ordered
	 */
	protected String bindingCustomizer = BINDING_CUSTOMIZER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EditingModelPackage.Literals.PROPERTY_BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Editor getEditor() {
		return editor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEditor(Editor newEditor, NotificationChain msgs) {
		Editor oldEditor = editor;
		editor = newEditor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EditingModelPackage.PROPERTY_BINDING__EDITOR, oldEditor, newEditor);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditor(Editor newEditor) {
		if (newEditor != editor) {
			NotificationChain msgs = null;
			if (editor != null)
				msgs = ((InternalEObject)editor).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.PROPERTY_BINDING__EDITOR, null, msgs);
			if (newEditor != null)
				msgs = ((InternalEObject)newEditor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.PROPERTY_BINDING__EDITOR, null, msgs);
			msgs = basicSetEditor(newEditor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditingModelPackage.PROPERTY_BINDING__EDITOR, newEditor, newEditor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyBinding> getSubPropertyBindings() {
		if (subPropertyBindings == null) {
			subPropertyBindings = new EObjectContainmentEList<PropertyBinding>(PropertyBinding.class, this, EditingModelPackage.PROPERTY_BINDING__SUB_PROPERTY_BINDINGS);
		}
		return subPropertyBindings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EditorSettings> getSettings() {
		if (settings == null) {
			settings = new EObjectContainmentEList<EditorSettings>(EditorSettings.class, this, EditingModelPackage.PROPERTY_BINDING__SETTINGS);
		}
		return settings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBindingCustomizer() {
		return bindingCustomizer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBindingCustomizer(String newBindingCustomizer) {
		String oldBindingCustomizer = bindingCustomizer;
		bindingCustomizer = newBindingCustomizer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditingModelPackage.PROPERTY_BINDING__BINDING_CUSTOMIZER, oldBindingCustomizer, bindingCustomizer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public EClassBinding getEClassBinding() {
		if (eContainer() instanceof EClassBinding) {
			return (EClassBinding) eContainer();
		}
		EObject parent = eContainer(); 
		while (parent.eContainer() != null && !(parent.eContainer() instanceof EClassBinding)) {
			parent = parent.eContainer();
		}
		if (parent instanceof EClassBinding) {
			return (EClassBinding) parent;
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
			case EditingModelPackage.PROPERTY_BINDING__EDITOR:
				return basicSetEditor(null, msgs);
			case EditingModelPackage.PROPERTY_BINDING__SUB_PROPERTY_BINDINGS:
				return ((InternalEList<?>)getSubPropertyBindings()).basicRemove(otherEnd, msgs);
			case EditingModelPackage.PROPERTY_BINDING__SETTINGS:
				return ((InternalEList<?>)getSettings()).basicRemove(otherEnd, msgs);
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
			case EditingModelPackage.PROPERTY_BINDING__EDITOR:
				return getEditor();
			case EditingModelPackage.PROPERTY_BINDING__SUB_PROPERTY_BINDINGS:
				return getSubPropertyBindings();
			case EditingModelPackage.PROPERTY_BINDING__SETTINGS:
				return getSettings();
			case EditingModelPackage.PROPERTY_BINDING__BINDING_CUSTOMIZER:
				return getBindingCustomizer();
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
			case EditingModelPackage.PROPERTY_BINDING__EDITOR:
				setEditor((Editor)newValue);
				return;
			case EditingModelPackage.PROPERTY_BINDING__SUB_PROPERTY_BINDINGS:
				getSubPropertyBindings().clear();
				getSubPropertyBindings().addAll((Collection<? extends PropertyBinding>)newValue);
				return;
			case EditingModelPackage.PROPERTY_BINDING__SETTINGS:
				getSettings().clear();
				getSettings().addAll((Collection<? extends EditorSettings>)newValue);
				return;
			case EditingModelPackage.PROPERTY_BINDING__BINDING_CUSTOMIZER:
				setBindingCustomizer((String)newValue);
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
			case EditingModelPackage.PROPERTY_BINDING__EDITOR:
				setEditor((Editor)null);
				return;
			case EditingModelPackage.PROPERTY_BINDING__SUB_PROPERTY_BINDINGS:
				getSubPropertyBindings().clear();
				return;
			case EditingModelPackage.PROPERTY_BINDING__SETTINGS:
				getSettings().clear();
				return;
			case EditingModelPackage.PROPERTY_BINDING__BINDING_CUSTOMIZER:
				setBindingCustomizer(BINDING_CUSTOMIZER_EDEFAULT);
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
			case EditingModelPackage.PROPERTY_BINDING__EDITOR:
				return editor != null;
			case EditingModelPackage.PROPERTY_BINDING__SUB_PROPERTY_BINDINGS:
				return subPropertyBindings != null && !subPropertyBindings.isEmpty();
			case EditingModelPackage.PROPERTY_BINDING__SETTINGS:
				return settings != null && !settings.isEmpty();
			case EditingModelPackage.PROPERTY_BINDING__BINDING_CUSTOMIZER:
				return BINDING_CUSTOMIZER_EDEFAULT == null ? bindingCustomizer != null : !BINDING_CUSTOMIZER_EDEFAULT.equals(bindingCustomizer);
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
		result.append(" (bindingCustomizer: ");
		result.append(bindingCustomizer);
		result.append(')');
		return result.toString();
	}

} //PropertyBindingImpl
