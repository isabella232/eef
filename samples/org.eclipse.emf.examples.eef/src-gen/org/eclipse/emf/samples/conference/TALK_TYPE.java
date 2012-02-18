/**
 * <copyright>
 * </copyright>
 *
 * $Id: TALK_TYPE.java,v 1.3 2011/01/05 15:06:12 glefur Exp $
 */
package org.eclipse.emf.samples.conference;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>TALK TYPE</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.samples.conference.ConferencePackage#getTALK_TYPE()
 * @model
 * @generated
 */
public enum TALK_TYPE implements Enumerator {
	/**
	 * The '<em><b>WORKSHOP</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WORKSHOP_VALUE
	 * @generated
	 * @ordered
	 */
	WORKSHOP(0, "WORKSHOP", "WORKSHOP"),

	/**
	 * The '<em><b>DEMONSTRATION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DEMONSTRATION_VALUE
	 * @generated
	 * @ordered
	 */
	DEMONSTRATION(1, "DEMONSTRATION", "DEMONSTRATION"),

	/**
	 * The '<em><b>CONFERENCE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONFERENCE_VALUE
	 * @generated
	 * @ordered
	 */
	CONFERENCE(2, "CONFERENCE", "CONFERENCE");

	/**
	 * The '<em><b>WORKSHOP</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>WORKSHOP</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WORKSHOP
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int WORKSHOP_VALUE = 0;

	/**
	 * The '<em><b>DEMONSTRATION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DEMONSTRATION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DEMONSTRATION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DEMONSTRATION_VALUE = 1;

	/**
	 * The '<em><b>CONFERENCE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CONFERENCE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONFERENCE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CONFERENCE_VALUE = 2;

	/**
	 * An array of all the '<em><b>TALK TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final TALK_TYPE[] VALUES_ARRAY =
		new TALK_TYPE[] {
			WORKSHOP,
			DEMONSTRATION,
			CONFERENCE,
		};

	/**
	 * A public read-only list of all the '<em><b>TALK TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<TALK_TYPE> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>TALK TYPE</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TALK_TYPE get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TALK_TYPE result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>TALK TYPE</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TALK_TYPE getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TALK_TYPE result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>TALK TYPE</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TALK_TYPE get(int value) {
		switch (value) {
			case WORKSHOP_VALUE: return WORKSHOP;
			case DEMONSTRATION_VALUE: return DEMONSTRATION;
			case CONFERENCE_VALUE: return CONFERENCE;
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
	private TALK_TYPE(int value, String name, String literal) {
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
	
} //TALK_TYPE
