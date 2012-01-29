/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.view.helpers;

import java.beans.PropertyChangeListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ReflectHelper<T> {

	private Class<? extends T> clazz;

	/**
	 * @param clazz {@link Class} to analyze.
	 */
	public ReflectHelper(Class<? extends T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Search a constructor callable with the given arguments.
	 * @param args arguments to match
	 * @return a constructor that can be called with the given arguments <code>null</code> otherwise.
	 */
	public Constructor<? extends T> searchAvailableConstructor(Object... args) {
		Class<?>[] paramTypes = new Class<?>[args.length];
		Constructor<? extends T> result = null;
		for (int i = 0; i < args.length; i++) {
			paramTypes[i] = args[i].getClass();
		}
		try {
			result = clazz.getConstructor(paramTypes);
		} catch (SecurityException e) {
			result = null;
		} catch (NoSuchMethodException e) {
			result = null;
		}
		return result;
	}

	/**
	 * Search a setter for the given field.
	 * @param field field to set.
	 * @param setterArgType type of setter arg.
	 * @return the setter.
	 */
	public Method searchSetter(String field, Class<? extends Object> setterArgType) {
		Method method = null;
		try {
			//TODO: this method doesn't manage autoboxing e.g. can't use boolean primitive type for EBool attribute.
			method = clazz.getMethod("set" + toUpperFirst(field), setterArgType);
		} catch (SecurityException e) {
			method = null;
		} catch (NoSuchMethodException e) {
			method = null;
		}
		return method;
	}
	
	/**
	 * Search an unsetter for the given field.
	 * @param field field to unset.
	 * @return the unsetter.
	 */
	public Method searchUnsetter(String field) {
		Method method = null;
		try {
			method = clazz.getMethod("unset" + toUpperFirst(field));
		} catch (SecurityException e) {
			method = null;
		} catch (NoSuchMethodException e) {
			method = null;
		}
		return method;
	}

	/**
	 * Search an adder for the given field.
	 * @param field field where to add.
	 * @return the adder.
	 */
	public Method searchAdder(String field) {
		Method method = null;
		try {
			// Could try to remove an potential s a the end of the field name
			method = clazz.getMethod("add" + toUpperFirst(field));
		} catch (SecurityException e) {
			method = null;
		} catch (NoSuchMethodException e) {
			method = null;
		}
		return method;
	}

	/**
	 * Search an remover for the given field.
	 * @param field field where to add.
	 * @return the adder.
	 */
	public Method searchRemover(String field) {
		Method method = null;
		try {
			// Could try to remove an potential s a the end of the field name
			method = clazz.getMethod("remove" + toUpperFirst(field));
		} catch (SecurityException e) {
			method = null;
		} catch (NoSuchMethodException e) {
			method = null;
		}
		return method;
	}

	/**
	 * @return
	 */
	public Method searchListenerAdder() {
		Method method = null;
		try {
			method = clazz.getMethod("addPropertyChangeListener", PropertyChangeListener.class);
		} catch (SecurityException e) {
			method = null;
		} catch (NoSuchMethodException e) {
			method = null;
		}
		return method;
	}
	
	/**
	 * Sets the first character of the string to upper.
	 * @param str String to process
	 * @return the given string with first character in upper case.
	 */
	private String toUpperFirst(String str) {
		if (str == null) {
			return null;
		} else if (str.length() == 1) {
			return str.toUpperCase();
		} else {
			return Character.toUpperCase(str.charAt(0)) + str.substring(1);
		}
	}


	
}
