/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Feature Documentation Provider</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage#getFeatureDocumentationProvider()
 * @model
 * @generated
 */
public enum FeatureDocumentationProvider implements Enumerator {
	/**
	 * The '<em><b>Genmodel Property Description</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GENMODEL_PROPERTY_DESCRIPTION_VALUE
	 * @generated
	 * @ordered
	 */
	GENMODEL_PROPERTY_DESCRIPTION(0, "GenmodelPropertyDescription", "GenmodelPropertyDescription"),

	/**
	 * The '<em><b>Ecore Documentation</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ECORE_DOCUMENTATION_VALUE
	 * @generated
	 * @ordered
	 */
	ECORE_DOCUMENTATION(1, "EcoreDocumentation", "EcoreDocumentation");

	/**
	 * The '<em><b>Genmodel Property Description</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Genmodel Property Description</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GENMODEL_PROPERTY_DESCRIPTION
	 * @model name="GenmodelPropertyDescription"
	 * @generated
	 * @ordered
	 */
	public static final int GENMODEL_PROPERTY_DESCRIPTION_VALUE = 0;

	/**
	 * The '<em><b>Ecore Documentation</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Ecore Documentation</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ECORE_DOCUMENTATION
	 * @model name="EcoreDocumentation"
	 * @generated
	 * @ordered
	 */
	public static final int ECORE_DOCUMENTATION_VALUE = 1;

	/**
	 * An array of all the '<em><b>Feature Documentation Provider</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final FeatureDocumentationProvider[] VALUES_ARRAY =
		new FeatureDocumentationProvider[] {
			GENMODEL_PROPERTY_DESCRIPTION,
			ECORE_DOCUMENTATION,
		};

	/**
	 * A public read-only list of all the '<em><b>Feature Documentation Provider</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<FeatureDocumentationProvider> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Feature Documentation Provider</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FeatureDocumentationProvider get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FeatureDocumentationProvider result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Feature Documentation Provider</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FeatureDocumentationProvider getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FeatureDocumentationProvider result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Feature Documentation Provider</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FeatureDocumentationProvider get(int value) {
		switch (value) {
			case GENMODEL_PROPERTY_DESCRIPTION_VALUE: return GENMODEL_PROPERTY_DESCRIPTION;
			case ECORE_DOCUMENTATION_VALUE: return ECORE_DOCUMENTATION;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private FeatureDocumentationProvider(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
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
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //FeatureDocumentationProvider
