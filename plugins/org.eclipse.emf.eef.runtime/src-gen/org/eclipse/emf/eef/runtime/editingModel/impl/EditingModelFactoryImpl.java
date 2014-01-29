/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.eef.runtime.editingModel.*;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectEditor;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.EReferenceFilter;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.EditingOptions;
import org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider;
import org.eclipse.emf.eef.runtime.editingModel.JavaEditor;
import org.eclipse.emf.eef.runtime.editingModel.JavaView;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EditingModelFactoryImpl extends EFactoryImpl implements EditingModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EditingModelFactory init() {
		try {
			EditingModelFactory theEditingModelFactory = (EditingModelFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.eclipse.org/emf/eef/editingmodel/1.0.0"); 
			if (theEditingModelFactory != null) {
				return theEditingModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EditingModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditingModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case EditingModelPackage.PROPERTIES_EDITING_MODEL: return (EObject)createPropertiesEditingModel();
			case EditingModelPackage.ECLASS_BINDING: return (EObject)createEClassBinding();
			case EditingModelPackage.PROPERTY_BINDING: return (EObject)createPropertyBinding();
			case EditingModelPackage.MONO_VALUED_PROPERTY_BINDING: return (EObject)createMonoValuedPropertyBinding();
			case EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING: return (EObject)createMultiValuedPropertyBinding();
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING: return (EObject)createEStructuralFeatureBinding();
			case EditingModelPackage.JAVA_VIEW: return (EObject)createJavaView();
			case EditingModelPackage.EOBJECT_VIEW: return (EObject)createEObjectView();
			case EditingModelPackage.JAVA_EDITOR: return (EObject)createJavaEditor();
			case EditingModelPackage.EOBJECT_EDITOR: return (EObject)createEObjectEditor();
			case EditingModelPackage.EDITING_OPTIONS: return (EObject)createEditingOptions();
			case EditingModelPackage.EREFERENCE_FILTER: return createEReferenceFilter();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case EditingModelPackage.FEATURE_DOCUMENTATION_PROVIDER:
				return createFeatureDocumentationProviderFromString(eDataType, initialValue);
			case EditingModelPackage.VOID:
				return createVoidFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case EditingModelPackage.FEATURE_DOCUMENTATION_PROVIDER:
				return convertFeatureDocumentationProviderToString(eDataType, instanceValue);
			case EditingModelPackage.VOID:
				return convertVoidToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertiesEditingModel createPropertiesEditingModel() {
		PropertiesEditingModelImpl propertiesEditingModel = new PropertiesEditingModelImpl();
		return propertiesEditingModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassBinding createEClassBinding() {
		EClassBindingImpl eClassBinding = new EClassBindingImpl();
		return eClassBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaView createJavaView() {
		JavaViewImpl javaView = new JavaViewImpl();
		return javaView;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyBinding createPropertyBinding() {
		PropertyBindingImpl propertyBinding = new PropertyBindingImpl();
		return propertyBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MonoValuedPropertyBinding createMonoValuedPropertyBinding() {
		MonoValuedPropertyBindingImpl monoValuedPropertyBinding = new MonoValuedPropertyBindingImpl();
		return monoValuedPropertyBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultiValuedPropertyBinding createMultiValuedPropertyBinding() {
		MultiValuedPropertyBindingImpl multiValuedPropertyBinding = new MultiValuedPropertyBindingImpl();
		return multiValuedPropertyBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EStructuralFeatureBinding createEStructuralFeatureBinding() {
		EStructuralFeatureBindingImpl eStructuralFeatureBinding = new EStructuralFeatureBindingImpl();
		return eStructuralFeatureBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectView createEObjectView() {
		EObjectViewImpl eObjectView = new EObjectViewImpl();
		return eObjectView;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaEditor createJavaEditor() {
		JavaEditorImpl javaEditor = new JavaEditorImpl();
		return javaEditor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectEditor createEObjectEditor() {
		EObjectEditorImpl eObjectEditor = new EObjectEditorImpl();
		return eObjectEditor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditingOptions createEditingOptions() {
		EditingOptionsImpl editingOptions = new EditingOptionsImpl();
		return editingOptions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReferenceFilter createEReferenceFilter() {
		EReferenceFilterImpl eReferenceFilter = new EReferenceFilterImpl();
		return eReferenceFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureDocumentationProvider createFeatureDocumentationProviderFromString(EDataType eDataType, String initialValue) {
		FeatureDocumentationProvider result = FeatureDocumentationProvider.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFeatureDocumentationProviderToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Void createVoidFromString(EDataType eDataType, String initialValue) {
		return (Void)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVoidToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditingModelPackage getEditingModelPackage() {
		return (EditingModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EditingModelPackage getPackage() {
		return EditingModelPackage.eINSTANCE;
	}

} //EditingModelFactoryImpl
