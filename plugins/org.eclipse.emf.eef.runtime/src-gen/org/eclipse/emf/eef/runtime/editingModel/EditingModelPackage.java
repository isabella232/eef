/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory
 * @model kind="package"
 * @generated
 */
public interface EditingModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "editingModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/eef/editingmodel/1.0.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "eef-model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EditingModelPackage eINSTANCE = org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.PropertiesEditingModelImpl <em>Properties Editing Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.PropertiesEditingModelImpl
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getPropertiesEditingModel()
	 * @generated
	 */
	int PROPERTIES_EDITING_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTIES_EDITING_MODEL__NAME = 0;

	/**
	 * The feature id for the '<em><b>Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTIES_EDITING_MODEL__BINDINGS = 1;

	/**
	 * The number of structural features of the '<em>Properties Editing Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTIES_EDITING_MODEL_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.EClassBindingImpl <em>EClass Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EClassBindingImpl
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEClassBinding()
	 * @generated
	 */
	int ECLASS_BINDING = 1;

	/**
	 * The feature id for the '<em><b>EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_BINDING__ECLASS = 0;

	/**
	 * The feature id for the '<em><b>Views</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_BINDING__VIEWS = 1;

	/**
	 * The feature id for the '<em><b>Property Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_BINDING__PROPERTY_BINDINGS = 2;

	/**
	 * The number of structural features of the '<em>EClass Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_BINDING_FEATURE_COUNT = 3;


	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.ViewImpl <em>View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.ViewImpl
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getView()
	 * @generated
	 */
	int VIEW = 2;

	/**
	 * The feature id for the '<em><b>Handler</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__HANDLER = 0;

	/**
	 * The number of structural features of the '<em>View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.JavaViewImpl <em>Java View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.JavaViewImpl
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getJavaView()
	 * @generated
	 */
	int JAVA_VIEW = 3;

	/**
	 * The feature id for the '<em><b>Handler</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_VIEW__HANDLER = VIEW__HANDLER;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_VIEW__DEFINITION = VIEW_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Java View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_VIEW_FEATURE_COUNT = VIEW_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.PropertyBindingImpl <em>Property Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.PropertyBindingImpl
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getPropertyBinding()
	 * @generated
	 */
	int PROPERTY_BINDING = 4;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_BINDING__FEATURE = 0;

	/**
	 * The feature id for the '<em><b>Editor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_BINDING__EDITOR = 1;

	/**
	 * The number of structural features of the '<em>Property Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_BINDING_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.EObjectViewImpl <em>EObject View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EObjectViewImpl
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEObjectView()
	 * @generated
	 */
	int EOBJECT_VIEW = 5;

	/**
	 * The feature id for the '<em><b>Handler</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_VIEW__HANDLER = VIEW__HANDLER;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_VIEW__DEFINITION = VIEW_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EObject View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_VIEW_FEATURE_COUNT = VIEW_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '<em>View Handler</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getViewHandler()
	 * @generated
	 */
	int VIEW_HANDLER = 6;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel <em>Properties Editing Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Properties Editing Model</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel
	 * @generated
	 */
	EClass getPropertiesEditingModel();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getName()
	 * @see #getPropertiesEditingModel()
	 * @generated
	 */
	EAttribute getPropertiesEditingModel_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getBindings <em>Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Bindings</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getBindings()
	 * @see #getPropertiesEditingModel()
	 * @generated
	 */
	EReference getPropertiesEditingModel_Bindings();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding <em>EClass Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EClass Binding</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EClassBinding
	 * @generated
	 */
	EClass getEClassBinding();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getEClass <em>EClass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>EClass</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getEClass()
	 * @see #getEClassBinding()
	 * @generated
	 */
	EReference getEClassBinding_EClass();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getViews <em>Views</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Views</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getViews()
	 * @see #getEClassBinding()
	 * @generated
	 */
	EReference getEClassBinding_Views();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getPropertyBindings <em>Property Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property Bindings</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getPropertyBindings()
	 * @see #getEClassBinding()
	 * @generated
	 */
	EReference getEClassBinding_PropertyBindings();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.editingModel.View <em>View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>View</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.View
	 * @generated
	 */
	EClass getView();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.eef.runtime.editingModel.View#getHandler <em>Handler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Handler</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.View#getHandler()
	 * @see #getView()
	 * @generated
	 */
	EAttribute getView_Handler();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.editingModel.JavaView <em>Java View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java View</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.JavaView
	 * @generated
	 */
	EClass getJavaView();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.eef.runtime.editingModel.JavaView#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Definition</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.JavaView#getDefinition()
	 * @see #getJavaView()
	 * @generated
	 */
	EAttribute getJavaView_Definition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding <em>Property Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Binding</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertyBinding
	 * @generated
	 */
	EClass getPropertyBinding();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getFeature()
	 * @see #getPropertyBinding()
	 * @generated
	 */
	EReference getPropertyBinding_Feature();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getEditor <em>Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Editor</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getEditor()
	 * @see #getPropertyBinding()
	 * @generated
	 */
	EAttribute getPropertyBinding_Editor();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.editingModel.EObjectView <em>EObject View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EObject View</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EObjectView
	 * @generated
	 */
	EClass getEObjectView();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.eef.runtime.editingModel.EObjectView#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Definition</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EObjectView#getDefinition()
	 * @see #getEObjectView()
	 * @generated
	 */
	EReference getEObjectView_Definition();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.eef.runtime.view.handler.ViewHandler <em>View Handler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>View Handler</em>'.
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler
	 * @model instanceClass="org.eclipse.emf.eef.runtime.view.handler.ViewHandler" typeParameters="T"
	 * @generated
	 */
	EDataType getViewHandler();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EditingModelFactory getEditingModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.PropertiesEditingModelImpl <em>Properties Editing Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.PropertiesEditingModelImpl
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getPropertiesEditingModel()
		 * @generated
		 */
		EClass PROPERTIES_EDITING_MODEL = eINSTANCE.getPropertiesEditingModel();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTIES_EDITING_MODEL__NAME = eINSTANCE.getPropertiesEditingModel_Name();

		/**
		 * The meta object literal for the '<em><b>Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTIES_EDITING_MODEL__BINDINGS = eINSTANCE.getPropertiesEditingModel_Bindings();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.EClassBindingImpl <em>EClass Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EClassBindingImpl
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEClassBinding()
		 * @generated
		 */
		EClass ECLASS_BINDING = eINSTANCE.getEClassBinding();

		/**
		 * The meta object literal for the '<em><b>EClass</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLASS_BINDING__ECLASS = eINSTANCE.getEClassBinding_EClass();

		/**
		 * The meta object literal for the '<em><b>Views</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLASS_BINDING__VIEWS = eINSTANCE.getEClassBinding_Views();

		/**
		 * The meta object literal for the '<em><b>Property Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLASS_BINDING__PROPERTY_BINDINGS = eINSTANCE.getEClassBinding_PropertyBindings();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.ViewImpl <em>View</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.ViewImpl
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getView()
		 * @generated
		 */
		EClass VIEW = eINSTANCE.getView();

		/**
		 * The meta object literal for the '<em><b>Handler</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VIEW__HANDLER = eINSTANCE.getView_Handler();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.JavaViewImpl <em>Java View</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.JavaViewImpl
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getJavaView()
		 * @generated
		 */
		EClass JAVA_VIEW = eINSTANCE.getJavaView();

		/**
		 * The meta object literal for the '<em><b>Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_VIEW__DEFINITION = eINSTANCE.getJavaView_Definition();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.PropertyBindingImpl <em>Property Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.PropertyBindingImpl
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getPropertyBinding()
		 * @generated
		 */
		EClass PROPERTY_BINDING = eINSTANCE.getPropertyBinding();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_BINDING__FEATURE = eINSTANCE.getPropertyBinding_Feature();

		/**
		 * The meta object literal for the '<em><b>Editor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_BINDING__EDITOR = eINSTANCE.getPropertyBinding_Editor();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.EObjectViewImpl <em>EObject View</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EObjectViewImpl
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEObjectView()
		 * @generated
		 */
		EClass EOBJECT_VIEW = eINSTANCE.getEObjectView();

		/**
		 * The meta object literal for the '<em><b>Definition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EOBJECT_VIEW__DEFINITION = eINSTANCE.getEObjectView_Definition();

		/**
		 * The meta object literal for the '<em>View Handler</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getViewHandler()
		 * @generated
		 */
		EDataType VIEW_HANDLER = eINSTANCE.getViewHandler();

	}

} //EditingModelPackage
