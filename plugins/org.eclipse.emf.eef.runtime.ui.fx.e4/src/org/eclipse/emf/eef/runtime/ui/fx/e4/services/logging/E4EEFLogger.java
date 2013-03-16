/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.e4.services.logging;

import javax.inject.Inject;

import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.services.logging.EEFLogger;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class E4EEFLogger extends AbstractEEFService<Object> implements EEFLogger {

	@Inject
	private Logger logger;
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Object element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.logging.EEFLogger#logWarning(java.lang.String, java.lang.String, java.lang.Throwable)
	 */
	public void logWarning(String pluginID, String message, Throwable t) {
		logger.error(message, t);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.logging.EEFLogger#logError(java.lang.String, java.lang.String, java.lang.Throwable)
	 */
	public void logError(String pluginID, String message, Throwable t) {
		logger.warn(message, t);
	}

}
