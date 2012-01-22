/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.handlers;

import java.lang.reflect.Constructor;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.ui.internal.view.helpers.ReflectHelper;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ReflectViewHandler implements ViewHandler {

	protected Class<?> viewClass;
	protected Object view;
	
	private ReflectHelper helper;

	/**
	 * @param viewClass {@link Composite} to handle.
	 */
	public ReflectViewHandler(final Class<?> viewClass) {
		this.viewClass = viewClass;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#createView(java.lang.Object[])
	 */
	public Object createView(Object... viewConstructArgs) throws ViewConstructionException {
		if (view == null) {
			Constructor<?> availableConstructor = getHelper().searchAvailableConstructor(viewConstructArgs);
			if (availableConstructor != null) {
				try {
					view = availableConstructor.newInstance(viewConstructArgs);
				} catch (Exception e) {
					throw new ViewConstructionException("An error occured during view construction.", e);
				}
			} else {
				throw new ViewConstructionException("Invalid arguments for view construction.");				
			}
		}
		return view;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#getView()
	 */
	public Object getView() {
		return view;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#setValue(java.lang.Object, java.lang.Object)
	 */
	public void setValue(Object field, Object value) {
		if (field instanceof String) {
			reflectSet((String) field, value);
		} else if (field instanceof EStructuralFeature) {
			reflectSet(((EStructuralFeature) field).getName(), value);
		}
	}

	/**
	 * @return a {@link ReflectHelper} on the view class.
	 */
	private ReflectHelper getHelper() {
		if (helper == null) {
			helper = new ReflectHelper(viewClass);
		}
		return helper;
	}
	
	private void reflectSet(String feature, Object value) {
		
	}

}
