/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.handlers.swt;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandler;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SWTViewHandler extends ReflectViewHandler<Composite> {

	/**
	 * @param handlerProvider
	 * @param editingComponent 
	 * @param viewClass
	 */
	public SWTViewHandler(ViewHandlerProvider handlerProvider, PropertiesEditingComponent editingComponent, Class<? extends Composite> viewClass) {
		super(handlerProvider, editingComponent, viewClass);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandler#dispose()
	 */
	@Override
	public void dispose() {
		if (view != null) {
			view.dispose();
		}
		super.dispose();
	}
	
	
	
}
