/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.handlers.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.ui.internal.view.util.ReflectHelper;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewHandlingException;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * TODO: Add ability to defines view via annotations.
 */
public class ReflectViewHandler<T> implements ViewHandler<T> {

	protected Class<? extends T> viewClass;
	protected T view;
	
	private ReflectHelper<T> helper;

	/**
	 * @param viewClass View class to handle.
	 */
	public ReflectViewHandler(final Class<? extends T> viewClass) {
		this.viewClass = viewClass;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#createView(java.lang.Object[])
	 */
	public T createView(Object... viewConstructArgs) throws ViewConstructionException {
		if (view == null) {
			Constructor<? extends T> availableConstructor = getHelper().searchAvailableConstructor(viewConstructArgs);
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
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#initView(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	public void initView(PropertiesEditingComponent component) {
		EObject eObject = (EObject) component.getTarget();
		for (EStructuralFeature feature : eObject.eClass().getEAllStructuralFeatures()) {
			try {
				setValue(feature.getName(), eObject.eGet(feature));
			} catch (ViewHandlingException e) {
				//NOTE: Silent catch
			}
		}
		Method searchListenerAdder = helper.searchListenerAdder();
		if (searchListenerAdder != null) {
			try {
				searchListenerAdder.invoke(view, component.getViewChangeNotifier());
			} catch (Exception e) {
				//NOTE: Silent catch
			}
		}
		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#getView()
	 */
	public T getView() {
		return view;
	}

	/**
	 * {@inheritDoc}
	 * @throws Exception 
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#setValue(java.lang.Object, java.lang.Object)
	 */
	public void setValue(Object field, Object value) throws ViewHandlingException {
		if (field instanceof String && value != null) {
			Method setter = helper.searchSetter((String)field, value.getClass());
			if (setter != null) {
				try {
					setter.invoke(view, value);
				} catch (Exception e) {
					throw new ViewHandlingException("An error occured during view handling.", e);
				}
			}
		} 
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#unsetValue(java.lang.Object)
	 */
	public void unsetValue(Object field) throws ViewHandlingException {
		if (field instanceof String) {
			Method unsetter = helper.searchUnsetter((String)field);
			if (unsetter != null) {
				try {
					unsetter.invoke(view);
				} catch (Exception e) {
					throw new ViewHandlingException("An error occured during view handling.", e);
				}
			}
		} 		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#addValue(java.lang.Object, java.lang.Object)
	 */
	public void addValue(Object field, Object newValue) throws ViewHandlingException {
		if (field instanceof String) {
			Method adder = helper.searchAdder((String)field, newValue.getClass());
			if (adder != null) {
				try {
					adder.invoke(view, newValue);
				} catch (Exception e) {
					throw new ViewHandlingException("An error occured during view handling.", e);
				}
			}
		} 		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#removeValue(java.lang.Object, java.lang.Object)
	 */
	public void removeValue(Object field, Object newValue) throws ViewHandlingException {
		if (field instanceof String) {
			Method remover = helper.searchRemover((String)field, newValue.getClass());
			if (remover != null) {
				try {
					remover.invoke(view, newValue);
				} catch (Exception e) {
					throw new ViewHandlingException("An error occured during view handling.", e);
				}
			}
		} 		
	}

	/**
	 * @return a {@link ReflectHelper} on the view class.
	 */
	private ReflectHelper<T> getHelper() {
		if (helper == null) {
			helper = new ReflectHelper<T>(viewClass);
		}
		return helper;
	}
	
}
