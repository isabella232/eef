/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.handlers.editingview;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.ui.internal.view.PropertiesEditingViewImpl;
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
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#createView(java.lang.Object[])
	 */
	public PropertiesEditingView createView(Object... args) throws ViewConstructionException {
		if (view == null) {
			if (args.length > 1 && args[0] instanceof PropertiesEditingComponent && args[1] instanceof Composite) {
				view = new PropertiesEditingViewImpl((PropertiesEditingComponent) args[0], viewDescriptor);
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
		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#unsetValue(java.lang.Object)
	 */
	public void unsetValue(Object field) throws ViewHandlingException {
		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#addValue(java.lang.Object, java.lang.Object)
	 */
	public void addValue(Object field, Object newValue) throws ViewHandlingException {
		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandler#removeValue(java.lang.Object, java.lang.Object)
	 */
	public void removeValue(Object field, Object value) throws ViewHandlingException {
		
	}

}
