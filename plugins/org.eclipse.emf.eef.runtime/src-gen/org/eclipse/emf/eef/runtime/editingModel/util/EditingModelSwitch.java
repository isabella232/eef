package org.eclipse.emf.eef.runtime.editingModel.util;


import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectEditor;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.EReferenceFilter;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.EditingOptions;
import org.eclipse.emf.eef.runtime.editingModel.Editor;
import org.eclipse.emf.eef.runtime.editingModel.EditorSettings;
import org.eclipse.emf.eef.runtime.editingModel.JavaEditor;
import org.eclipse.emf.eef.runtime.editingModel.JavaView;
import org.eclipse.emf.eef.runtime.editingModel.MonoValuedPropertyBinding;
import org.eclipse.emf.eef.runtime.editingModel.MultiValuedPropertyBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.query.Filter;
import org.eclipse.emf.eef.runtime.query.Query;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage
 * @generated
 */
public class EditingModelSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EditingModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditingModelSwitch() {
		if (modelPackage == null) {
			modelPackage = EditingModelPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case EditingModelPackage.PROPERTIES_EDITING_MODEL: {
				PropertiesEditingModel propertiesEditingModel = (PropertiesEditingModel)theEObject;
				T result = casePropertiesEditingModel(propertiesEditingModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EditingModelPackage.ECLASS_BINDING: {
				EClassBinding eClassBinding = (EClassBinding)theEObject;
				T result = caseEClassBinding(eClassBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EditingModelPackage.VIEW: {
				View view = (View)theEObject;
				T result = caseView(view);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EditingModelPackage.EDITOR: {
				Editor editor = (Editor)theEObject;
				T result = caseEditor(editor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EditingModelPackage.PROPERTY_BINDING: {
				PropertyBinding propertyBinding = (PropertyBinding)theEObject;
				T result = casePropertyBinding(propertyBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EditingModelPackage.MONO_VALUED_PROPERTY_BINDING: {
				MonoValuedPropertyBinding monoValuedPropertyBinding = (MonoValuedPropertyBinding)theEObject;
				T result = caseMonoValuedPropertyBinding(monoValuedPropertyBinding);
				if (result == null) result = casePropertyBinding(monoValuedPropertyBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EditingModelPackage.MULTI_VALUED_PROPERTY_BINDING: {
				MultiValuedPropertyBinding multiValuedPropertyBinding = (MultiValuedPropertyBinding)theEObject;
				T result = caseMultiValuedPropertyBinding(multiValuedPropertyBinding);
				if (result == null) result = casePropertyBinding(multiValuedPropertyBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING: {
				EStructuralFeatureBinding eStructuralFeatureBinding = (EStructuralFeatureBinding)theEObject;
				T result = caseEStructuralFeatureBinding(eStructuralFeatureBinding);
				if (result == null) result = caseMonoValuedPropertyBinding(eStructuralFeatureBinding);
				if (result == null) result = caseMultiValuedPropertyBinding(eStructuralFeatureBinding);
				if (result == null) result = casePropertyBinding(eStructuralFeatureBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EditingModelPackage.JAVA_VIEW: {
				JavaView javaView = (JavaView)theEObject;
				T result = caseJavaView(javaView);
				if (result == null) result = caseView(javaView);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EditingModelPackage.EOBJECT_VIEW: {
				EObjectView eObjectView = (EObjectView)theEObject;
				T result = caseEObjectView(eObjectView);
				if (result == null) result = caseView(eObjectView);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EditingModelPackage.JAVA_EDITOR: {
				JavaEditor javaEditor = (JavaEditor)theEObject;
				T result = caseJavaEditor(javaEditor);
				if (result == null) result = caseEditor(javaEditor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EditingModelPackage.EOBJECT_EDITOR: {
				EObjectEditor eObjectEditor = (EObjectEditor)theEObject;
				T result = caseEObjectEditor(eObjectEditor);
				if (result == null) result = caseEditor(eObjectEditor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EditingModelPackage.EDITING_OPTIONS: {
				EditingOptions editingOptions = (EditingOptions)theEObject;
				T result = caseEditingOptions(editingOptions);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EditingModelPackage.EDITOR_SETTINGS: {
				EditorSettings editorSettings = (EditorSettings)theEObject;
				T result = caseEditorSettings(editorSettings);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EditingModelPackage.EREFERENCE_FILTER: {
				EReferenceFilter eReferenceFilter = (EReferenceFilter)theEObject;
				T result = caseEReferenceFilter(eReferenceFilter);
				if (result == null) result = caseEditorSettings(eReferenceFilter);
				if (result == null) result = caseFilter(eReferenceFilter);
				if (result == null) result = caseQuery(eReferenceFilter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Properties Editing Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Properties Editing Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertiesEditingModel(PropertiesEditingModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EClass Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EClass Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEClassBinding(EClassBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>View</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>View</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseView(View object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Editor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Editor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEditor(Editor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePropertyBinding(PropertyBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mono Valued Property Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mono Valued Property Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMonoValuedPropertyBinding(MonoValuedPropertyBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Multi Valued Property Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Multi Valued Property Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultiValuedPropertyBinding(MultiValuedPropertyBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EStructural Feature Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EStructural Feature Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEStructuralFeatureBinding(EStructuralFeatureBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java View</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java View</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaView(JavaView object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject View</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject View</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEObjectView(EObjectView object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Editor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Editor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaEditor(JavaEditor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject Editor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject Editor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEObjectEditor(EObjectEditor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Editing Options</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Editing Options</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEditingOptions(EditingOptions object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Editor Settings</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Editor Settings</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEditorSettings(EditorSettings object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EReference Filter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EReference Filter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEReferenceFilter(EReferenceFilter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <TYPE> T caseQuery(Query<TYPE> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Filter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Filter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFilter(Filter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //EditingModelSwitch