/**
 * 
 */
package org.eclipse.emf.eef.runtime.logging;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFLogger {
	
	/**
	 * Logs a warning.
	 * @param pluginID Plugin ID were the error occured.
	 * @param message error message.
	 * @param t the {@link Throwable} which was thrown. Can be <code>null</code>.
	 */
	void logWarning(String pluginID, String message, Throwable t);

	/**
	 * Logs an error.
	 * @param pluginID Plugin ID were the error occured.
	 * @param message error message.
	 * @param t the {@link Throwable} which was thrown. Can be <code>null</code>.
	 */
	void logError(String pluginID, String message, Throwable t);

}
