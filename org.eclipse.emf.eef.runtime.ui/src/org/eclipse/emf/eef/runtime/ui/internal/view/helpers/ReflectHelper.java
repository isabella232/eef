/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.view.helpers;

import java.lang.reflect.Constructor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ReflectHelper {

	private Class<?> clazz;

	/**
	 * @param clazz {@link Class} to analyze.
	 */
	public ReflectHelper(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Search a constructor callable with the given arguments.
	 * @param args arguments to match
	 * @return a constructor that can be called with the given arguments <code>null</code> otherwise.
	 */
	public Constructor<?> searchAvailableConstructor(Object... args) {
		Class<?>[] paramTypes = new Class<?>[args.length];
		Constructor<?> result = null;
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
	
	
}
