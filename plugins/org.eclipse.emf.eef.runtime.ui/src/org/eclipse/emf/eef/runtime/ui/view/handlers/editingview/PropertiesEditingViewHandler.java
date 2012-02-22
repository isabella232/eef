/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.handlers.editingview;

import java.util.Collection;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.ui.internal.view.PropertiesEditingViewSWT;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewHandlingException;
import org.eclipse.emf.eef.views.View;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingViewHandler implements ViewHandler<PropertiesEditingView> {

	protected View viewDescriptor;
	protected PropertiesEditingViewHandlerProvider handlerProvider;
	protected PropertiesEditingView view;
	
	/**
	 * @param handlerProvider 
	 * @param viewDescriptor {@link View} to handle.
	 */
	public PropertiesEditingViewHandler(PropertiesEditingViewHandlerProvider handlerProvider, View viewDescriptor) {
		this.viewDescriptor = viewDescriptor;
		this.handlerProvider = handlerProvider;
	}

	/**
	 * @return the viewDescriptor
	 */
	public View getViewDescriptor() {
		return viewDescriptor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#createView(java.lang.Object[])
	 */
	public PropertiesEditingView createView(Object... args) throws ViewConstructionException {
		if (view == null) {
			if (args.length > 1 && args[0] instanceof PropertiesEditingComponent && args[1] instanceof Composite) {
				view = new PropertiesEditingViewSWT((PropertiesEditingComponent) args[0], viewDescriptor);
				view.setPropertyEditorProvider(handlerProvider.getPropertyEditorProvider());
				view.createContents((Composite)args[1]);
			}
		}
		return view;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#initView(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	public void initView(PropertiesEditingComponent component) {
		if (view != null) {
			view.init();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#getView()
	 */
	public PropertiesEditingView getView() { 
		return view;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#setValue(java.lang.Object, java.lang.Object)
	 */
	public void setValue(Object field, Object value) throws ViewHandlingException {
		view.setValue(field, value);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#unsetValue(java.lang.Object)
	 */
	public void unsetValue(Object field) throws ViewHandlingException {
		view.unsetValue(field);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#addValue(java.lang.Object, java.lang.Object)
	 */
	public void addValue(Object field, Object newValue) throws ViewHandlingException {
		view.addValue(field, newValue);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#addAllValues(java.lang.Object, java.util.Collection)
	 */
	public void addAllValues(Object field, Collection<?> values) throws ViewHandlingException {
		view.addAllValues(field, values);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#removeValue(java.lang.Object, java.lang.Object)
	 */
	public void removeValue(Object field, Object value) throws ViewHandlingException {
		view.removeValue(field, value);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#removeAllValues(java.lang.Object, java.util.Collection)
	 */
	public void removeAllValues(Object field, Collection<?> values) throws ViewHandlingException {
		view.removeAllValues(field, values);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#moveValue(java.lang.Object, java.lang.Object, int)
	 */
	public void moveValue(Object field, Object value, int newIndex) throws ViewHandlingException {
		view.moveValue(field, value, newIndex);
	}

}
