/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.handlers.swt;

import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SWTViewHandlerProvider implements ViewHandlerProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider#canHandle(java.lang.Object)
	 */
	public boolean canHandle(Object view) {
		if (view instanceof Class<?> ) {
			return isCompositeClass((Class<?>) view);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider#getHandler(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public ViewHandler<? extends Composite> getHandler(Object view) {
		return new SWTViewHandler(this, (Class<? extends Composite>) view);
	}

	/**
	 * @param view
	 * @return
	 */
	private boolean isCompositeClass(Class<?> view) {
		if (view == Composite.class) {
			return true;
		} else if (view.getSuperclass() != null) {
			return isCompositeClass(view.getSuperclass());
		} else {
			return false;
		}
	}
	
}
