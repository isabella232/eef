/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.handlers;

import java.lang.reflect.Method;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.services.viewhandler.exceptions.ViewHandlingException;
import org.eclipse.emf.eef.runtime.tests.views.SampleCustomView;
import org.eclipse.emf.eef.runtime.ui.internal.view.util.ReflectHelper;
import org.eclipse.emf.eef.runtime.ui.swt.view.handlers.swt.SWTViewHandler;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class CustomSWTViewHandler extends SWTViewHandler {

	private ReflectHelper<Composite> helper;

	/**
	 * @param handlerProvider
	 * @param editingComponent
	 * @param viewClass
	 */
	public CustomSWTViewHandler(ViewHandlerProvider handlerProvider, PropertiesEditingComponent editingComponent, Class<? extends Composite> viewClass) {
		super(handlerProvider, editingComponent, viewClass);
	}

	/**
	 * @return a {@link ReflectHelper} on the view class.
	 */
	private ReflectHelper<Composite> getHelper() {
		if (helper == null) {
			helper = new ReflectHelper<Composite>(viewClass);
		}
		return helper;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandler#initView(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	@Override
	public void initView(PropertiesEditingComponent component) {
		EObject eObject = component.getEObject();
		for (EStructuralFeature feature : eObject.eClass().getEAllStructuralFeatures()) {
			try {
				setValue(feature.getName(), eObject.eGet(feature));
			} catch (ViewHandlingException e) {
				//NOTE: Silent catch
			}
		}
		Method searchListenerAdder = getHelper().searchListenerAdder();
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
	 * @see org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandler#setValue(java.lang.Object, java.lang.Object)
	 */
	public void setValue(Object field, Object value) throws ViewHandlingException {
		if (view != null && value instanceof Boolean) {
			if (field instanceof String && "abstract".equals(field)) {
				((SampleCustomView)view).setAbstractState((Boolean) value);
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
