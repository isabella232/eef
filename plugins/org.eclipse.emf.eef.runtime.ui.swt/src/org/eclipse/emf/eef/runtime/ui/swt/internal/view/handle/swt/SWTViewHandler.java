/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.swt;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.ui.view.handle.reflect.ReflectViewHandler;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SWTViewHandler extends ReflectViewHandler<Composite> {

	/**
	 * @param handlerFactory
	 * @param editingComponent 
	 * @param viewClass
	 */
	public SWTViewHandler(ViewHandlerFactory handlerFactory, PropertiesEditingComponent editingComponent, Class<? extends Composite> viewClass) {
		super(handlerFactory, editingComponent, viewClass);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.handle.reflect.ReflectViewHandler#dispose()
	 */
	@Override
	public void dispose() {
		if (view != null) {
			view.dispose();
		}
		super.dispose();
	}
	
	
	
}
