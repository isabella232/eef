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
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.EditingOptions;
import org.eclipse.emf.eef.runtime.editingModel.JavaView;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.services.EEFComponentRegistry;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Properties Editing Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.PropertiesEditingModelImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.PropertiesEditingModelImpl#getBindings <em>Bindings</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.PropertiesEditingModelImpl#getInvolvedModels <em>Involved Models</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.PropertiesEditingModelImpl#getOptions <em>Options</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.editingModel.impl.PropertiesEditingModelImpl#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertiesEditingModelImpl extends EObjectImpl implements PropertiesEditingModel {
	
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBindings() <em>Bindings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBindings()
	 * @generated
	 * @ordered
	 */
	protected EList<EClassBinding> bindings;

	/**
	 * The cached value of the '{@link #getInvolvedModels() <em>Involved Models</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvolvedModels()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> involvedModels;

	/**
	 * The cached value of the '{@link #getOptions() <em>Options</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOptions()
	 * @generated
	 * @ordered
	 */
	protected EditingOptions options;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertiesEditingModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EditingModelPackage.Literals.PROPERTIES_EDITING_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditingModelPackage.PROPERTIES_EDITING_MODEL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EClassBinding> getBindings() {
		if (bindings == null) {
			bindings = new EObjectContainmentWithInverseEList<EClassBinding>(EClassBinding.class, this, EditingModelPackage.PROPERTIES_EDITING_MODEL__BINDINGS, EditingModelPackage.ECLASS_BINDING__EDITING_MODEL);
		}
		return bindings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getInvolvedModels() {
		if (involvedModels == null) {
			involvedModels = new EObjectResolvingEList<EObject>(EObject.class, this, EditingModelPackage.PROPERTIES_EDITING_MODEL__INVOLVED_MODELS);
		}
		return involvedModels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditingOptions getOptions() {
		return options;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOptions(EditingOptions newOptions, NotificationChain msgs) {
		EditingOptions oldOptions = options;
		options = newOptions;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EditingModelPackage.PROPERTIES_EDITING_MODEL__OPTIONS, oldOptions, newOptions);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOptions(EditingOptions newOptions) {
		if (newOptions != options) {
			NotificationChain msgs = null;
			if (options != null)
				msgs = ((InternalEObject)options).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.PROPERTIES_EDITING_MODEL__OPTIONS, null, msgs);
			if (newOptions != null)
				msgs = ((InternalEObject)newOptions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EditingModelPackage.PROPERTIES_EDITING_MODEL__OPTIONS, null, msgs);
			msgs = basicSetOptions(newOptions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditingModelPackage.PROPERTIES_EDITING_MODEL__OPTIONS, newOptions, newOptions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditingModelPackage.PROPERTIES_EDITING_MODEL__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EClassBinding binding(EObject eObject) {
		EMFService emfService = componentRegistry.getService(EMFService.class, eObject.eClass().getEPackage());
		for (EClassBinding binding : bindings) {
			if ((emfService != null && emfService.equals(eObject.eClass(), binding.getEClass())) || eObject.eClass().equals(binding.getEClass())) {
				return binding;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<Object> views(EObject eObject) {
		EList<Object> result = new BasicEList<Object>();
		EClassBinding binding = binding(eObject);
		if (binding != null) {
			for (View view : binding.getViews()) {
				if (view instanceof JavaView) {
					result.add(((JavaView) view).getDefinition());					
				} else if (view instanceof EObjectView) {
					result.add(((EObjectView) view).getDefinition());					
				}
			}
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public ViewHandler<?> viewHandler(EObject eObject, Object view) {
		EClassBinding binding = binding(eObject);
		if (binding != null) {
			//TODO: I think this part of code should be an EOperation
			for (View viewDefinition : binding.getViews()) {
				if (viewDefinition instanceof JavaView) {
					if (view.equals(((JavaView) viewDefinition).getDefinition())) {
						return viewDefinition.getHandler();
					}
				}
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__BINDINGS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getBindings()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__BINDINGS:
				return ((InternalEList<?>)getBindings()).basicRemove(otherEnd, msgs);
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__OPTIONS:
				return basicSetOptions(null, msgs);
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
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__NAME:
				return getName();
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__BINDINGS:
				return getBindings();
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__INVOLVED_MODELS:
				return getInvolvedModels();
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__OPTIONS:
				return getOptions();
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__ID:
				return getId();
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
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__NAME:
				setName((String)newValue);
				return;
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__BINDINGS:
				getBindings().clear();
				getBindings().addAll((Collection<? extends EClassBinding>)newValue);
				return;
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__INVOLVED_MODELS:
				getInvolvedModels().clear();
				getInvolvedModels().addAll((Collection<? extends EObject>)newValue);
				return;
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__OPTIONS:
				setOptions((EditingOptions)newValue);
				return;
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__ID:
				setId((String)newValue);
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
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__BINDINGS:
				getBindings().clear();
				return;
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__INVOLVED_MODELS:
				getInvolvedModels().clear();
				return;
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__OPTIONS:
				setOptions((EditingOptions)null);
				return;
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__ID:
				setId(ID_EDEFAULT);
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
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__BINDINGS:
				return bindings != null && !bindings.isEmpty();
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__INVOLVED_MODELS:
				return involvedModels != null && !involvedModels.isEmpty();
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__OPTIONS:
				return options != null;
			case EditingModelPackage.PROPERTIES_EDITING_MODEL__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

	private EEFComponentRegistry componentRegistry;
	
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getComponentRegistry()
	 */
	public EEFComponentRegistry getComponentRegistry() {
		return componentRegistry;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#setComponentRegistry(org.eclipse.emf.eef.runtime.services.impl.EEFComponentRegistryImpl)
	 */
	public void setComponentRegistry(EEFComponentRegistry componentRegistry) {
		this.componentRegistry = componentRegistry;
	}
	
} //PropertiesEditingModelImpl
