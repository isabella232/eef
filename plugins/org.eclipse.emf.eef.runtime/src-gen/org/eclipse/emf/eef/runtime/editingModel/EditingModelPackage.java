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
import org.eclipse.emf.ecore.EEnum;
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
	 * The feature id for the '<em><b>Involved Models</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTIES_EDITING_MODEL__INVOLVED_MODELS = 2;

	/**
	 * The feature id for the '<em><b>Options</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTIES_EDITING_MODEL__OPTIONS = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTIES_EDITING_MODEL__ID = 4;

	/**
	 * The number of structural features of the '<em>Properties Editing Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTIES_EDITING_MODEL_FEATURE_COUNT = 5;

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
	 * The feature id for the '<em><b>Editing Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_BINDING__EDITING_MODEL = 0;

	/**
	 * The feature id for the '<em><b>EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_BINDING__ECLASS = 1;

	/**
	 * The feature id for the '<em><b>Views</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_BINDING__VIEWS = 2;

	/**
	 * The feature id for the '<em><b>Property Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_BINDING__PROPERTY_BINDINGS = 3;

	/**
	 * The number of structural features of the '<em>EClass Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECLASS_BINDING_FEATURE_COUNT = 4;


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
	 * The number of structural features of the '<em>View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.EditorImpl <em>Editor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditorImpl
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEditor()
	 * @generated
	 */
	int EDITOR = 3;

	/**
	 * The number of structural features of the '<em>Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.JavaViewImpl <em>Java View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.JavaViewImpl
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getJavaView()
	 * @generated
	 */
	int JAVA_VIEW = 6;

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
	 * The feature id for the '<em><b>Editor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_BINDING__EDITOR = 0;

	/**
	 * The feature id for the '<em><b>Sub Property Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_BINDING__SUB_PROPERTY_BINDINGS = 1;

	/**
	 * The feature id for the '<em><b>Settings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_BINDING__SETTINGS = 2;

	/**
	 * The feature id for the '<em><b>Binding Customizer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_BINDING__BINDING_CUSTOMIZER = 3;

	/**
	 * The number of structural features of the '<em>Property Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_BINDING_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.EStructuralFeatureBindingImpl <em>EStructural Feature Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EStructuralFeatureBindingImpl
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEStructuralFeatureBinding()
	 * @generated
	 */
	int ESTRUCTURAL_FEATURE_BINDING = 5;

	/**
	 * The feature id for the '<em><b>Editor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRUCTURAL_FEATURE_BINDING__EDITOR = PROPERTY_BINDING__EDITOR;

	/**
	 * The feature id for the '<em><b>Sub Property Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRUCTURAL_FEATURE_BINDING__SUB_PROPERTY_BINDINGS = PROPERTY_BINDING__SUB_PROPERTY_BINDINGS;

	/**
	 * The feature id for the '<em><b>Settings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRUCTURAL_FEATURE_BINDING__SETTINGS = PROPERTY_BINDING__SETTINGS;

	/**
	 * The feature id for the '<em><b>Binding Customizer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRUCTURAL_FEATURE_BINDING__BINDING_CUSTOMIZER = PROPERTY_BINDING__BINDING_CUSTOMIZER;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRUCTURAL_FEATURE_BINDING__FEATURE = PROPERTY_BINDING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EStructural Feature Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRUCTURAL_FEATURE_BINDING_FEATURE_COUNT = PROPERTY_BINDING_FEATURE_COUNT + 1;

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
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.EObjectViewImpl <em>EObject View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EObjectViewImpl
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEObjectView()
	 * @generated
	 */
	int EOBJECT_VIEW = 7;

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
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.JavaEditorImpl <em>Java Editor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.JavaEditorImpl
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getJavaEditor()
	 * @generated
	 */
	int JAVA_EDITOR = 8;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_EDITOR__DEFINITION = EDITOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Java Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_EDITOR_FEATURE_COUNT = EDITOR_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.EObjectEditorImpl <em>EObject Editor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EObjectEditorImpl
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEObjectEditor()
	 * @generated
	 */
	int EOBJECT_EDITOR = 9;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_EDITOR__DEFINITION = EDITOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EObject Editor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_EDITOR_FEATURE_COUNT = EDITOR_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.EditingOptionsImpl <em>Editing Options</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingOptionsImpl
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEditingOptions()
	 * @generated
	 */
	int EDITING_OPTIONS = 10;

	/**
	 * The feature id for the '<em><b>Feature Documentation Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITING_OPTIONS__FEATURE_DOCUMENTATION_PROVIDER = 0;

	/**
	 * The number of structural features of the '<em>Editing Options</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITING_OPTIONS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.EditorSettings <em>Editor Settings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditorSettings
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEditorSettings()
	 * @generated
	 */
	int EDITOR_SETTINGS = 11;

	/**
	 * The number of structural features of the '<em>Editor Settings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDITOR_SETTINGS_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.EReferenceFilterImpl <em>EReference Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EReferenceFilterImpl
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEReferenceFilter()
	 * @generated
	 */
	int EREFERENCE_FILTER = 12;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EREFERENCE_FILTER__BODY = EDITOR_SETTINGS_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EReference Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EREFERENCE_FILTER_FEATURE_COUNT = EDITOR_SETTINGS_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider <em>Feature Documentation Provider</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getFeatureDocumentationProvider()
	 * @generated
	 */
	int FEATURE_DOCUMENTATION_PROVIDER = 13;

	/**
	 * The meta object id for the '<em>Void</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Void
	 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getVoid()
	 * @generated
	 */
	int VOID = 14;

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
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getInvolvedModels <em>Involved Models</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Involved Models</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getInvolvedModels()
	 * @see #getPropertiesEditingModel()
	 * @generated
	 */
	EReference getPropertiesEditingModel_InvolvedModels();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getOptions <em>Options</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Options</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getOptions()
	 * @see #getPropertiesEditingModel()
	 * @generated
	 */
	EReference getPropertiesEditingModel_Options();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel#getId()
	 * @see #getPropertiesEditingModel()
	 * @generated
	 */
	EAttribute getPropertiesEditingModel_Id();

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
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getEditingModel <em>Editing Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Editing Model</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EClassBinding#getEditingModel()
	 * @see #getEClassBinding()
	 * @generated
	 */
	EReference getEClassBinding_EditingModel();

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
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.editingModel.Editor <em>Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Editor</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.Editor
	 * @generated
	 */
	EClass getEditor();

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
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getEditor <em>Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Editor</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getEditor()
	 * @see #getPropertyBinding()
	 * @generated
	 */
	EReference getPropertyBinding_Editor();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getSubPropertyBindings <em>Sub Property Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Property Bindings</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getSubPropertyBindings()
	 * @see #getPropertyBinding()
	 * @generated
	 */
	EReference getPropertyBinding_SubPropertyBindings();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getSettings <em>Settings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Settings</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getSettings()
	 * @see #getPropertyBinding()
	 * @generated
	 */
	EReference getPropertyBinding_Settings();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getBindingCustomizer <em>Binding Customizer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Binding Customizer</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertyBinding#getBindingCustomizer()
	 * @see #getPropertyBinding()
	 * @generated
	 */
	EAttribute getPropertyBinding_BindingCustomizer();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding <em>EStructural Feature Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EStructural Feature Binding</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding
	 * @generated
	 */
	EClass getEStructuralFeatureBinding();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding#getFeature()
	 * @see #getEStructuralFeatureBinding()
	 * @generated
	 */
	EReference getEStructuralFeatureBinding_Feature();

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
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.editingModel.JavaEditor <em>Java Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Editor</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.JavaEditor
	 * @generated
	 */
	EClass getJavaEditor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.eef.runtime.editingModel.JavaEditor#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Definition</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.JavaEditor#getDefinition()
	 * @see #getJavaEditor()
	 * @generated
	 */
	EAttribute getJavaEditor_Definition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.editingModel.EObjectEditor <em>EObject Editor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EObject Editor</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EObjectEditor
	 * @generated
	 */
	EClass getEObjectEditor();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.eef.runtime.editingModel.EObjectEditor#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Definition</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EObjectEditor#getDefinition()
	 * @see #getEObjectEditor()
	 * @generated
	 */
	EReference getEObjectEditor_Definition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.editingModel.EditingOptions <em>Editing Options</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Editing Options</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingOptions
	 * @generated
	 */
	EClass getEditingOptions();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.eef.runtime.editingModel.EditingOptions#getFeatureDocumentationProvider <em>Feature Documentation Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feature Documentation Provider</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingOptions#getFeatureDocumentationProvider()
	 * @see #getEditingOptions()
	 * @generated
	 */
	EAttribute getEditingOptions_FeatureDocumentationProvider();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.editingModel.EditorSettings <em>Editor Settings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Editor Settings</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditorSettings
	 * @generated
	 */
	EClass getEditorSettings();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.editingModel.EReferenceFilter <em>EReference Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EReference Filter</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.EReferenceFilter
	 * @generated
	 */
	EClass getEReferenceFilter();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider <em>Feature Documentation Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Feature Documentation Provider</em>'.
	 * @see org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider
	 * @generated
	 */
	EEnum getFeatureDocumentationProvider();

	/**
	 * Returns the meta object for data type '{@link java.lang.Void <em>Void</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Void</em>'.
	 * @see java.lang.Void
	 * @model instanceClass="java.lang.Void"
	 * @generated
	 */
	EDataType getVoid();

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
		 * The meta object literal for the '<em><b>Involved Models</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTIES_EDITING_MODEL__INVOLVED_MODELS = eINSTANCE.getPropertiesEditingModel_InvolvedModels();

		/**
		 * The meta object literal for the '<em><b>Options</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTIES_EDITING_MODEL__OPTIONS = eINSTANCE.getPropertiesEditingModel_Options();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTIES_EDITING_MODEL__ID = eINSTANCE.getPropertiesEditingModel_Id();

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
		 * The meta object literal for the '<em><b>Editing Model</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECLASS_BINDING__EDITING_MODEL = eINSTANCE.getEClassBinding_EditingModel();

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
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.EditorImpl <em>Editor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditorImpl
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEditor()
		 * @generated
		 */
		EClass EDITOR = eINSTANCE.getEditor();

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
		 * The meta object literal for the '<em><b>Editor</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_BINDING__EDITOR = eINSTANCE.getPropertyBinding_Editor();

		/**
		 * The meta object literal for the '<em><b>Sub Property Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_BINDING__SUB_PROPERTY_BINDINGS = eINSTANCE.getPropertyBinding_SubPropertyBindings();

		/**
		 * The meta object literal for the '<em><b>Settings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_BINDING__SETTINGS = eINSTANCE.getPropertyBinding_Settings();

		/**
		 * The meta object literal for the '<em><b>Binding Customizer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_BINDING__BINDING_CUSTOMIZER = eINSTANCE.getPropertyBinding_BindingCustomizer();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.EStructuralFeatureBindingImpl <em>EStructural Feature Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EStructuralFeatureBindingImpl
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEStructuralFeatureBinding()
		 * @generated
		 */
		EClass ESTRUCTURAL_FEATURE_BINDING = eINSTANCE.getEStructuralFeatureBinding();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ESTRUCTURAL_FEATURE_BINDING__FEATURE = eINSTANCE.getEStructuralFeatureBinding_Feature();

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
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.JavaEditorImpl <em>Java Editor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.JavaEditorImpl
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getJavaEditor()
		 * @generated
		 */
		EClass JAVA_EDITOR = eINSTANCE.getJavaEditor();

		/**
		 * The meta object literal for the '<em><b>Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_EDITOR__DEFINITION = eINSTANCE.getJavaEditor_Definition();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.EObjectEditorImpl <em>EObject Editor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EObjectEditorImpl
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEObjectEditor()
		 * @generated
		 */
		EClass EOBJECT_EDITOR = eINSTANCE.getEObjectEditor();

		/**
		 * The meta object literal for the '<em><b>Definition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EOBJECT_EDITOR__DEFINITION = eINSTANCE.getEObjectEditor_Definition();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.EditingOptionsImpl <em>Editing Options</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingOptionsImpl
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEditingOptions()
		 * @generated
		 */
		EClass EDITING_OPTIONS = eINSTANCE.getEditingOptions();

		/**
		 * The meta object literal for the '<em><b>Feature Documentation Provider</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDITING_OPTIONS__FEATURE_DOCUMENTATION_PROVIDER = eINSTANCE.getEditingOptions_FeatureDocumentationProvider();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.editingModel.EditorSettings <em>Editor Settings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.editingModel.EditorSettings
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEditorSettings()
		 * @generated
		 */
		EClass EDITOR_SETTINGS = eINSTANCE.getEditorSettings();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.editingModel.impl.EReferenceFilterImpl <em>EReference Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EReferenceFilterImpl
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getEReferenceFilter()
		 * @generated
		 */
		EClass EREFERENCE_FILTER = eINSTANCE.getEReferenceFilter();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider <em>Feature Documentation Provider</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getFeatureDocumentationProvider()
		 * @generated
		 */
		EEnum FEATURE_DOCUMENTATION_PROVIDER = eINSTANCE.getFeatureDocumentationProvider();

		/**
		 * The meta object literal for the '<em>Void</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Void
		 * @see org.eclipse.emf.eef.runtime.editingModel.impl.EditingModelPackageImpl#getVoid()
		 * @generated
		 */
		EDataType VOID = eINSTANCE.getVoid();

	}

} //EditingModelPackage
