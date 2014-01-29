/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectEditor;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.EReferenceFilter;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.EditingOptions;
import org.eclipse.emf.eef.runtime.editingModel.Editor;
import org.eclipse.emf.eef.runtime.editingModel.EditorSettings;
import org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider;
import org.eclipse.emf.eef.runtime.editingModel.JavaEditor;
import org.eclipse.emf.eef.runtime.editingModel.JavaView;
import org.eclipse.emf.eef.runtime.editingModel.MonoValuedPropertyBinding;
import org.eclipse.emf.eef.runtime.editingModel.MultiValuedPropertyBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.query.QueryPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EditingModelPackageImpl extends EPackageImpl implements EditingModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertiesEditingModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eClassBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass viewEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass editorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaViewEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass monoValuedPropertyBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multiValuedPropertyBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eStructuralFeatureBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eObjectViewEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaEditorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eObjectEditorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass editingOptionsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass editorSettingsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eReferenceFilterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum featureDocumentationProviderEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType voidEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EditingModelPackageImpl() {
		super(eNS_URI, EditingModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link EditingModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EditingModelPackage init() {
		if (isInited) return (EditingModelPackage)EPackage.Registry.INSTANCE.getEPackage(EditingModelPackage.eNS_URI);

		// Obtain or create and register package
		EditingModelPackageImpl theEditingModelPackage = (EditingModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EditingModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EditingModelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		QueryPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theEditingModelPackage.createPackageContents();

		// Initialize created meta-data
		theEditingModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEditingModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EditingModelPackage.eNS_URI, theEditingModelPackage);
		return theEditingModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertiesEditingModel() {
		return propertiesEditingModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertiesEditingModel_Name() {
		return (EAttribute)propertiesEditingModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertiesEditingModel_Bindings() {
		return (EReference)propertiesEditingModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertiesEditingModel_InvolvedModels() {
		return (EReference)propertiesEditingModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertiesEditingModel_Options() {
		return (EReference)propertiesEditingModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertiesEditingModel_Id() {
		return (EAttribute)propertiesEditingModelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEClassBinding() {
		return eClassBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEClassBinding_EditingModel() {
		return (EReference)eClassBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEClassBinding_EClass() {
		return (EReference)eClassBindingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEClassBinding_Views() {
		return (EReference)eClassBindingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEClassBinding_PropertyBindings() {
		return (EReference)eClassBindingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getView() {
		return viewEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEditor() {
		return editorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJavaView() {
		return javaViewEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJavaView_Definition() {
		return (EAttribute)javaViewEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyBinding() {
		return propertyBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyBinding_Editor() {
		return (EReference)propertyBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyBinding_SubPropertyBindings() {
		return (EReference)propertyBindingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyBinding_Settings() {
		return (EReference)propertyBindingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyBinding_Getter() {
		return (EReference)propertyBindingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyBinding_ValueProvider() {
		return (EReference)propertyBindingEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMonoValuedPropertyBinding() {
		return monoValuedPropertyBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMonoValuedPropertyBinding_Setter() {
		return (EReference)monoValuedPropertyBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMonoValuedPropertyBinding_Unsetter() {
		return (EReference)monoValuedPropertyBindingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMultiValuedPropertyBinding() {
		return multiValuedPropertyBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMultiValuedPropertyBinding_Adder() {
		return (EReference)multiValuedPropertyBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMultiValuedPropertyBinding_Remover() {
		return (EReference)multiValuedPropertyBindingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEStructuralFeatureBinding() {
		return eStructuralFeatureBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEStructuralFeatureBinding_Feature() {
		return (EReference)eStructuralFeatureBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEObjectView() {
		return eObjectViewEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEObjectView_Definition() {
		return (EReference)eObjectViewEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJavaEditor() {
		return javaEditorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJavaEditor_Definition() {
		return (EAttribute)javaEditorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEObjectEditor() {
		return eObjectEditorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEObjectEditor_Definition() {
		return (EReference)eObjectEditorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEditingOptions() {
		return editingOptionsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEditingOptions_FeatureDocumentationProvider() {
		return (EAttribute)editingOptionsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEditorSettings() {
		return editorSettingsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEReferenceFilter() {
		return eReferenceFilterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getFeatureDocumentationProvider() {
		return featureDocumentationProviderEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getVoid() {
		return voidEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditingModelFactory getEditingModelFactory() {
		return (EditingModelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		propertiesEditingModelEClass = createEClass(PROPERTIES_EDITING_MODEL);
		createEAttribute(propertiesEditingModelEClass, PROPERTIES_EDITING_MODEL__NAME);
		createEReference(propertiesEditingModelEClass, PROPERTIES_EDITING_MODEL__BINDINGS);
		createEReference(propertiesEditingModelEClass, PROPERTIES_EDITING_MODEL__INVOLVED_MODELS);
		createEReference(propertiesEditingModelEClass, PROPERTIES_EDITING_MODEL__OPTIONS);
		createEAttribute(propertiesEditingModelEClass, PROPERTIES_EDITING_MODEL__ID);

		eClassBindingEClass = createEClass(ECLASS_BINDING);
		createEReference(eClassBindingEClass, ECLASS_BINDING__EDITING_MODEL);
		createEReference(eClassBindingEClass, ECLASS_BINDING__ECLASS);
		createEReference(eClassBindingEClass, ECLASS_BINDING__VIEWS);
		createEReference(eClassBindingEClass, ECLASS_BINDING__PROPERTY_BINDINGS);

		viewEClass = createEClass(VIEW);

		editorEClass = createEClass(EDITOR);

		propertyBindingEClass = createEClass(PROPERTY_BINDING);
		createEReference(propertyBindingEClass, PROPERTY_BINDING__EDITOR);
		createEReference(propertyBindingEClass, PROPERTY_BINDING__SUB_PROPERTY_BINDINGS);
		createEReference(propertyBindingEClass, PROPERTY_BINDING__SETTINGS);
		createEReference(propertyBindingEClass, PROPERTY_BINDING__GETTER);
		createEReference(propertyBindingEClass, PROPERTY_BINDING__VALUE_PROVIDER);

		monoValuedPropertyBindingEClass = createEClass(MONO_VALUED_PROPERTY_BINDING);
		createEReference(monoValuedPropertyBindingEClass, MONO_VALUED_PROPERTY_BINDING__SETTER);
		createEReference(monoValuedPropertyBindingEClass, MONO_VALUED_PROPERTY_BINDING__UNSETTER);

		multiValuedPropertyBindingEClass = createEClass(MULTI_VALUED_PROPERTY_BINDING);
		createEReference(multiValuedPropertyBindingEClass, MULTI_VALUED_PROPERTY_BINDING__ADDER);
		createEReference(multiValuedPropertyBindingEClass, MULTI_VALUED_PROPERTY_BINDING__REMOVER);

		eStructuralFeatureBindingEClass = createEClass(ESTRUCTURAL_FEATURE_BINDING);
		createEReference(eStructuralFeatureBindingEClass, ESTRUCTURAL_FEATURE_BINDING__FEATURE);

		javaViewEClass = createEClass(JAVA_VIEW);
		createEAttribute(javaViewEClass, JAVA_VIEW__DEFINITION);

		eObjectViewEClass = createEClass(EOBJECT_VIEW);
		createEReference(eObjectViewEClass, EOBJECT_VIEW__DEFINITION);

		javaEditorEClass = createEClass(JAVA_EDITOR);
		createEAttribute(javaEditorEClass, JAVA_EDITOR__DEFINITION);

		eObjectEditorEClass = createEClass(EOBJECT_EDITOR);
		createEReference(eObjectEditorEClass, EOBJECT_EDITOR__DEFINITION);

		editingOptionsEClass = createEClass(EDITING_OPTIONS);
		createEAttribute(editingOptionsEClass, EDITING_OPTIONS__FEATURE_DOCUMENTATION_PROVIDER);

		editorSettingsEClass = createEClass(EDITOR_SETTINGS);

		eReferenceFilterEClass = createEClass(EREFERENCE_FILTER);

		// Create enums
		featureDocumentationProviderEEnum = createEEnum(FEATURE_DOCUMENTATION_PROVIDER);

		// Create data types
		voidEDataType = createEDataType(VOID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		QueryPackage theQueryPackage = (QueryPackage)EPackage.Registry.INSTANCE.getEPackage(QueryPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		monoValuedPropertyBindingEClass.getESuperTypes().add(this.getPropertyBinding());
		multiValuedPropertyBindingEClass.getESuperTypes().add(this.getPropertyBinding());
		eStructuralFeatureBindingEClass.getESuperTypes().add(this.getMonoValuedPropertyBinding());
		eStructuralFeatureBindingEClass.getESuperTypes().add(this.getMultiValuedPropertyBinding());
		javaViewEClass.getESuperTypes().add(this.getView());
		eObjectViewEClass.getESuperTypes().add(this.getView());
		javaEditorEClass.getESuperTypes().add(this.getEditor());
		eObjectEditorEClass.getESuperTypes().add(this.getEditor());
		eReferenceFilterEClass.getESuperTypes().add(this.getEditorSettings());
		eReferenceFilterEClass.getESuperTypes().add(theQueryPackage.getFilter());

		// Initialize classes and features; add operations and parameters
		initEClass(propertiesEditingModelEClass, PropertiesEditingModel.class, "PropertiesEditingModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPropertiesEditingModel_Name(), ecorePackage.getEString(), "name", null, 0, 1, PropertiesEditingModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertiesEditingModel_Bindings(), this.getEClassBinding(), this.getEClassBinding_EditingModel(), "bindings", null, 0, -1, PropertiesEditingModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertiesEditingModel_InvolvedModels(), theEcorePackage.getEObject(), null, "involvedModels", null, 0, -1, PropertiesEditingModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertiesEditingModel_Options(), this.getEditingOptions(), null, "options", null, 0, 1, PropertiesEditingModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertiesEditingModel_Id(), ecorePackage.getEString(), "id", null, 1, 1, PropertiesEditingModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(propertiesEditingModelEClass, this.getEClassBinding(), "binding", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEObject(), "eObject", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(propertiesEditingModelEClass, this.getView(), "views", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEObject(), "eObject", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(eClassBindingEClass, EClassBinding.class, "EClassBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEClassBinding_EditingModel(), this.getPropertiesEditingModel(), this.getPropertiesEditingModel_Bindings(), "editingModel", null, 1, 1, EClassBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEClassBinding_EClass(), theEcorePackage.getEClass(), null, "eClass", null, 1, 1, EClassBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEClassBinding_Views(), this.getView(), null, "views", null, 0, -1, EClassBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEClassBinding_PropertyBindings(), this.getPropertyBinding(), null, "propertyBindings", null, 0, -1, EClassBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(eClassBindingEClass, ecorePackage.getEJavaObject(), "propertyEditor", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "eObject", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEStructuralFeature(), "feature", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEBoolean(), "autowire", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(eClassBindingEClass, this.getPropertyBinding(), "propertyBinding", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEJavaObject(), "view", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEBoolean(), "autowire", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(viewEClass, View.class, "View", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(editorEClass, Editor.class, "Editor", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(propertyBindingEClass, PropertyBinding.class, "PropertyBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPropertyBinding_Editor(), this.getEditor(), null, "editor", null, 1, 1, PropertyBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertyBinding_SubPropertyBindings(), this.getPropertyBinding(), null, "subPropertyBindings", null, 0, -1, PropertyBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertyBinding_Settings(), this.getEditorSettings(), null, "settings", null, 0, -1, PropertyBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		EGenericType g1 = createEGenericType(theQueryPackage.getJavaBody());
		EGenericType g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		initEReference(getPropertyBinding_Getter(), g1, null, "getter", null, 0, 1, PropertyBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theQueryPackage.getJavaBody());
		g2 = createEGenericType(ecorePackage.getEEList());
		g1.getETypeArguments().add(g2);
		EGenericType g3 = createEGenericType(theEcorePackage.getEJavaObject());
		g2.getETypeArguments().add(g3);
		initEReference(getPropertyBinding_ValueProvider(), g1, null, "valueProvider", null, 0, 1, PropertyBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(propertyBindingEClass, ecorePackage.getEJavaObject(), "getValue", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theQueryPackage.getClassLoader(), "loader", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEObject(), "target", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(propertyBindingEClass, ecorePackage.getEJavaObject(), "getChoiceOfValue", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theQueryPackage.getClassLoader(), "loader", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEObject(), "target", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(monoValuedPropertyBindingEClass, MonoValuedPropertyBinding.class, "MonoValuedPropertyBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theQueryPackage.getJavaBody());
		g2 = createEGenericType(this.getVoid());
		g1.getETypeArguments().add(g2);
		initEReference(getMonoValuedPropertyBinding_Setter(), g1, null, "setter", null, 0, 1, MonoValuedPropertyBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theQueryPackage.getJavaBody());
		g2 = createEGenericType(this.getVoid());
		g1.getETypeArguments().add(g2);
		initEReference(getMonoValuedPropertyBinding_Unsetter(), g1, null, "unsetter", null, 0, 1, MonoValuedPropertyBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(monoValuedPropertyBindingEClass, this.getVoid(), "setValue", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theQueryPackage.getClassLoader(), "loader", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEObject(), "target", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEJavaObject(), "value", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(monoValuedPropertyBindingEClass, this.getVoid(), "unsetValue", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theQueryPackage.getClassLoader(), "loader", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEObject(), "target", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEJavaObject(), "value", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(multiValuedPropertyBindingEClass, MultiValuedPropertyBinding.class, "MultiValuedPropertyBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theQueryPackage.getJavaBody());
		g2 = createEGenericType(this.getVoid());
		g1.getETypeArguments().add(g2);
		initEReference(getMultiValuedPropertyBinding_Adder(), g1, null, "adder", null, 0, 1, MultiValuedPropertyBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theQueryPackage.getJavaBody());
		g2 = createEGenericType(this.getVoid());
		g1.getETypeArguments().add(g2);
		initEReference(getMultiValuedPropertyBinding_Remover(), g1, null, "remover", null, 0, 1, MultiValuedPropertyBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(multiValuedPropertyBindingEClass, this.getVoid(), "addValue", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theQueryPackage.getClassLoader(), "loader", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEObject(), "target", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEJavaObject(), "value", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(multiValuedPropertyBindingEClass, this.getVoid(), "removeValue", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theQueryPackage.getClassLoader(), "loader", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEObject(), "target", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEJavaObject(), "value", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(eStructuralFeatureBindingEClass, EStructuralFeatureBinding.class, "EStructuralFeatureBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEStructuralFeatureBinding_Feature(), theEcorePackage.getEStructuralFeature(), null, "feature", null, 1, 1, EStructuralFeatureBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(javaViewEClass, JavaView.class, "JavaView", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJavaView_Definition(), ecorePackage.getEJavaObject(), "definition", null, 1, 1, JavaView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eObjectViewEClass, EObjectView.class, "EObjectView", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEObjectView_Definition(), theEcorePackage.getEObject(), null, "definition", null, 1, 1, EObjectView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(javaEditorEClass, JavaEditor.class, "JavaEditor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJavaEditor_Definition(), ecorePackage.getEJavaObject(), "definition", null, 1, 1, JavaEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eObjectEditorEClass, EObjectEditor.class, "EObjectEditor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEObjectEditor_Definition(), theEcorePackage.getEObject(), null, "definition", null, 1, 1, EObjectEditor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(editingOptionsEClass, EditingOptions.class, "EditingOptions", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEditingOptions_FeatureDocumentationProvider(), this.getFeatureDocumentationProvider(), "featureDocumentationProvider", null, 0, 1, EditingOptions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(editorSettingsEClass, EditorSettings.class, "EditorSettings", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(eReferenceFilterEClass, EReferenceFilter.class, "EReferenceFilter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(featureDocumentationProviderEEnum, FeatureDocumentationProvider.class, "FeatureDocumentationProvider");
		addEEnumLiteral(featureDocumentationProviderEEnum, FeatureDocumentationProvider.GENMODEL_PROPERTY_DESCRIPTION);
		addEEnumLiteral(featureDocumentationProviderEEnum, FeatureDocumentationProvider.ECORE_DOCUMENTATION);

		// Initialize data types
		initEDataType(voidEDataType, Void.class, "Void", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //EditingModelPackageImpl
