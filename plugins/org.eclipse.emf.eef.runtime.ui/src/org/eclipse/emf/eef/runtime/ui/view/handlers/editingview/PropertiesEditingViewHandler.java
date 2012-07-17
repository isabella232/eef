/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.handlers.editingview;

import java.util.Collection;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.emf.eef.runtime.ui.internal.view.impl.FormImplPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.internal.view.impl.SWTImplPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewHandlingException;
import org.eclipse.emf.eef.views.View;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

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
				PropertiesEditingComponent editingComponent = (PropertiesEditingComponent) args[0];
				FormToolkit toolkit = editingComponent.getEditingContext().getOptions().getOption(UIConstants.FORM_TOOLKIT);
				if (toolkit != null) {
					view = new FormImplPropertiesEditingView(editingComponent, viewDescriptor);
					view.setPropertyEditorProvider(handlerProvider.getPropertyEditorProvider());
					((FormImplPropertiesEditingView) view).createContents(toolkit, (Composite)args[1]);
				} else {
					view = new SWTImplPropertiesEditingView(editingComponent, viewDescriptor);					
					view.setPropertyEditorProvider(handlerProvider.getPropertyEditorProvider());
					((SWTImplPropertiesEditingView) view).createContents((Composite)args[1]);
				}
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
	 * Force the view of this {@link ViewHandler}.
	 * @param propertiesEditingView the view to define for this handler.
	 */
	public void setView(PropertiesEditingView propertiesEditingView) {
		view = propertiesEditingView;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#getProvider()
	 */
	public ViewHandlerProvider getProvider() {
		return handlerProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#setValue(java.lang.Object, java.lang.Object)
	 */
	public void setValue(Object field, Object value) throws ViewHandlingException {
		if (view != null) {
			view.setValue(field, value);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#unsetValue(java.lang.Object)
	 */
	public void unsetValue(Object field) throws ViewHandlingException {
		if (view != null) {
			view.unsetValue(field);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#addValue(java.lang.Object, java.lang.Object)
	 */
	public void addValue(Object field, Object newValue) throws ViewHandlingException {
		if (view != null) {
			view.addValue(field, newValue);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#addAllValues(java.lang.Object, java.util.Collection)
	 */
	public void addAllValues(Object field, Collection<?> values) throws ViewHandlingException {
		if (view != null) {
			view.addAllValues(field, values);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#removeValue(java.lang.Object, java.lang.Object)
	 */
	public void removeValue(Object field, Object value) throws ViewHandlingException {
		if (view != null) {
			view.removeValue(field, value);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#removeAllValues(java.lang.Object, java.util.Collection)
	 */
	public void removeAllValues(Object field, Collection<?> values) throws ViewHandlingException {
		if (view != null) {
			view.removeAllValues(field, values);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#moveValue(java.lang.Object, java.lang.Object, int)
	 */
	public void moveValue(Object field, Object value, int newIndex) throws ViewHandlingException {
		if (view != null) {
			view.moveValue(field, value, newIndex);
		}
	}

}
