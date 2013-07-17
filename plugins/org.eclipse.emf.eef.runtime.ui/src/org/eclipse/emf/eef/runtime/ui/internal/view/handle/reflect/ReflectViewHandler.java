/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.view.handle.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.JavaView;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.ui.internal.util.ReflectHelper;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handle.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.view.handle.exceptions.ViewHandlingException;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ReflectViewHandler<T> implements ViewHandler<T> {

	private EEFLockManagerProvider lockManagerProvider;
	private EEFLogger logger;
	
	/**
	 * @param lockManagerProvider the lockManagerProvider to set
	 */
	public void setLockManagerProvider(EEFLockManagerProvider lockManagerProvider) {
		this.lockManagerProvider = lockManagerProvider;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(EEFLogger logger) {
		this.logger = logger;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(View view) {
		if (view instanceof JavaView && ((JavaView) view).getDefinition() instanceof Class<?>) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#getLockManager(java.lang.Object)
	 */
	public EEFLockManager getLockManager(Object view) {
		return lockManagerProvider.getLockManager(view);
	}

	/**
	 * @return the logger
	 */
	public EEFLogger getLogger() {
		return logger;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#createView(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, org.eclipse.emf.eef.runtime.editingModel.View, java.lang.Object[])
	 */
	public T createView(PropertiesEditingComponent editingComponent, View descriptor, Object... viewConstructArgs) throws ViewConstructionException {
		T view = null;
		if (descriptor instanceof JavaView && ((JavaView) descriptor).getDefinition() instanceof Class) {
			// I can't ensure this ... ViewHandler should have 2 parameterized types ...
			Constructor<? extends T> availableConstructor = getHelper((Class<? extends T>) ((JavaView) descriptor).getDefinition()).searchAvailableConstructor(viewConstructArgs);
			if (availableConstructor != null) {
				try {
					view  = availableConstructor.newInstance(viewConstructArgs);
				} catch (Exception e) {
					throw new ViewConstructionException("An error occured during view construction.", e);
				}
			} else {
				throw new ViewConstructionException("Invalid arguments for view construction.");				
			}
		}
		editingComponent.setViewForDescriptor(descriptor, view);
		return view;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#initView(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
	 */
	public void initView(PropertiesEditingComponent component, T view) {
		EObject eObject = component.getEObject();
		for (EStructuralFeature feature : eObject.eClass().getEAllStructuralFeatures()) {
			try {
				setValue(view, feature.getName(), eObject.eGet(feature));
			} catch (ViewHandlingException e) {
				//NOTE: Silent catch
			}
		}
		@SuppressWarnings("unchecked")
		Method searchListenerAdder = getHelper((Class<? extends T>) view.getClass()).searchListenerAdder();
		if (searchListenerAdder != null) {
			try {
				searchListenerAdder.invoke(view, component.getViewChangeNotifier());
			} catch (Exception e) {
				//NOTE: Silent catch
			}
		}
		EEFLockManager lockManager = getLockManager(view);
		lockManager.initView(view);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#setValue(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public void setValue(Object view, Object field, Object value) throws ViewHandlingException {
		if (field instanceof String && value != null) {
			@SuppressWarnings("unchecked")
			Method setter = getHelper((Class<? extends T>) view.getClass()).searchSetter((String)field, value.getClass());
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
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#unsetValue(java.lang.Object, java.lang.Object)
	 */
	public void unsetValue(Object view, Object field) throws ViewHandlingException {
		if (field instanceof String) {
			@SuppressWarnings("unchecked")
			Method unsetter = getHelper((Class<? extends T>) view.getClass()).searchUnsetter((String)field);
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
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#addValue(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public void addValue(Object view, Object field, Object newValue) throws ViewHandlingException {
		if (field instanceof String) {
			@SuppressWarnings("unchecked")
			Method adder = getHelper((Class<? extends T>) view.getClass()).searchAdder((String)field, newValue.getClass());
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
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#addAllValues(java.lang.Object, java.lang.Object, java.util.Collection)
	 */
	public void addAllValues(Object view, Object field, Collection<?> values) throws ViewHandlingException {
		for (Object value : values) {
			addValue(view, field, value);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#removeValue(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public void removeValue(Object view, Object field, Object newValue) throws ViewHandlingException {
		if (field instanceof String) {
			@SuppressWarnings("unchecked")
			Method remover = getHelper((Class<? extends T>) view.getClass()).searchRemover((String)field, newValue.getClass());
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
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#removeAllValues(java.lang.Object, java.lang.Object, java.util.Collection)
	 */
	public void removeAllValues(Object view, Object field, Collection<?> values) throws ViewHandlingException {
		for (Object value : values) {
			removeValue(view, field, value);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#moveValue(java.lang.Object, java.lang.Object, java.lang.Object, int)
	 */
	public void moveValue(Object view, Object field, Object value, int newIndex) throws ViewHandlingException {
		// TODO not handle for the moment.
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#dispose(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
	 */
	public void dispose(PropertiesEditingComponent editingComponent, Object view) {
		editingComponent.removeView(view);
	}

	/**
	 * @return a {@link ReflectHelper} on the view class.
	 */
	private ReflectHelper<T> getHelper(Class<? extends T> viewClass) {
		return new ReflectHelper<T>(viewClass);
	}


}
