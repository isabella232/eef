/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.handlers;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.tests.views.SampleCustomView;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewHandlingException;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class CustomSWTViewHandler extends SWTViewHandler {

	/**
	 * @param handlerProvider
	 * @param editingComponent
	 * @param viewClass
	 */
	public CustomSWTViewHandler(ViewHandlerProvider handlerProvider, PropertiesEditingComponent editingComponent, Class<? extends Composite> viewClass) {
		super(handlerProvider, editingComponent, viewClass);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandler#setValue(java.lang.Object, java.lang.Object)
	 */
	public void setValue(Object field, Object value) throws ViewHandlingException {
		if (view != null && value instanceof Boolean) {
			if (field instanceof String && "active".equals(field)) {
				((SampleCustomView)view).setActiveState((Boolean) value);
			}
		}
		super.setValue(field, value);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandler#dispose()
	 */
	@Override
	public void dispose() {
		if (editingComponent != null) {
			super.dispose();
		}
	}

}
