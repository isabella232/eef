/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.handlers.reflect;

import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ReflectViewHandlerProvider implements ViewHandlerProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider#canHandle(java.lang.Object)
	 */
	public boolean canHandle(Object view) {
		if (view instanceof Class<?>) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider#getHandler(java.lang.Object)
	 */
	public ViewHandler<Object> getHandler(Object view) {
		return new ReflectViewHandler<Object>((Class<?>) view);
	}

}
