/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.view.helpers;

import java.beans.PropertyChangeListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

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
			method = getMethod("set" + toUpperFirst(field), setterArgType);
		} catch (SecurityException e) {
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
			method = getMethod("unset" + toUpperFirst(field));
		} catch (SecurityException e) {
			method = null;
		} 
		return method;
	}

	/**
	 * Search an adder for the given field.
	 * @param field field where to add.
	 * @return the adder.
	 */
	public Method searchAdder(String field, Class<? extends Object> setterArgType) {
		Method method = null;
		try {
			// Could try to remove an potential s a the end of the field name
			method = getMethod("add" + toUpperFirst(field), setterArgType);
		} catch (SecurityException e) {
			method = null;
		} 
		return method;
	}

	/**
	 * Search an remover for the given field.
	 * @param field field where to add.
	 * @return the adder.
	 */
	public Method searchRemover(String field, Class<? extends Object> setterArgType) {
		Method method = null;
		try {
			// Could try to remove an potential s a the end of the field name
			method = getMethod("remove" + toUpperFirst(field), setterArgType);
		} catch (SecurityException e) {
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

	
	private Method getMethod(final String name, Class<?>... argTypes) {
		Method method = null;
		try {
			method = clazz.getMethod(name, argTypes);
		} catch (SecurityException e) {
			//NOTE: Silent catch
		} catch (NoSuchMethodException e) {
			//NOTE: Silent catch
		}
		if (method != null) {
			return method;
		} else {
			Collection<Method> wellNamedMethods = Collections2.filter(Arrays.asList(clazz.getMethods()), new Predicate<Method>() {
				public boolean apply(Method input) {
					return name.equals(input.getName());
				}
			});
			for (Method wellNamedMethod : wellNamedMethods) {
				Class<?>[] parameterTypes = wellNamedMethod.getParameterTypes();
				if (parameterTypes.length == argTypes.length) {
					for (int i = 0; i < argTypes.length; i++) {
						Class<?> actualType = argTypes[i];
						Class<?> expectedType = parameterTypes[i];
						if (!areCompatible(actualType, expectedType)) {
							continue;
						}
					}
					return wellNamedMethod;
				}
			}
		}
		return null;
	}

	private boolean areCompatible(Class<?> actualType, Class<?> expectedType) {
		if (actualType.equals(expectedType)) {
			return true;
		} else {
			if (!actualType.isPrimitive() && !expectedType.isPrimitive()) {
				if (!expectedType.isInterface()) {
					Class<?> superclass = actualType.getSuperclass();
					while (superclass != null) {
						if (expectedType.equals(superclass)) {
							return true;
						}
						superclass = superclass.getSuperclass();
					}
				} else {
					for (Class<?> interface_ : actualType.getInterfaces()) {
						if (checkInterfacesCompatibility(interface_, expectedType)) {
							return true;
						}
					}
				}
			}
			if (actualType.isPrimitive() && !expectedType.isPrimitive()) {
				//Check for autoboxing
				Class<?> translateFromPrimitive = translateFromPrimitive(actualType);
				if (translateFromPrimitive != null && translateFromPrimitive.equals(expectedType)) {
					return true;					
				}
			}
			if (expectedType.isPrimitive() && !actualType.isPrimitive()) {
				//Check for autoboxing
				Class<?> translateFromPrimitive = translateFromPrimitive(expectedType);
				if (translateFromPrimitive != null && translateFromPrimitive.equals(actualType)) {
					return true;					
				}
			}
			
		}
		return false;
	}

	private boolean checkInterfacesCompatibility(Class<?> actual, Class<?> expectedInterface) {
		if (actual.equals(expectedInterface)) {
			return true;
		} else {
			for (Class<?> interface_ : actual.getInterfaces()) {
				if (checkInterfacesCompatibility(interface_, expectedInterface)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param type
	 * @return
	 */
	public Class<?> translateFromPrimitive(Class<?> type) {
		if (Boolean.TYPE.equals(type)) {
			return (Boolean.class);			
		}
		if (Byte.TYPE.equals(type)) {
			return (Byte.class);			
		}
		if (Character.TYPE.equals(type)) {
			return (Character.class);		
		}
		if (Double.TYPE.equals(type)) {
			return (Double.class);			
		}
		if (Integer.TYPE.equals(type)) {
			return (Integer.class);			
		}
		if (Float.TYPE.equals(type)) {
			return (Float.class);			
		}
		if (Long.TYPE.equals(type)) {
			return (Long.class);			
		}
		if (Short.TYPE.equals(type)) {
			return (Short.class);			
		}
		return null;
	}

}
