/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.model.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingComponentImpl extends AdapterImpl implements PropertiesEditingComponent {

	private PropertiesEditingContext editingContext;
	private PropertiesEditingModel editingModel;
	private ViewHandler viewHandler;
	
	/**
	 * @param editingModel
	 */
	public PropertiesEditingComponentImpl(PropertiesEditingModel editingModel) {
		this.editingModel = editingModel;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
	 */
	@Override
	public boolean isAdapterForType(Object type) {
		return type == PropertiesEditingComponent.class;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#setEditingContext(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public void setEditingContext(PropertiesEditingContext editingContext) {
		this.editingContext = editingContext;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getViewHandler()
	 */
	public ViewHandler getViewHandler() {
		ViewHandlerProvider viewHandlerProvider = editingContext.getViewHandlerProvider();
		if (viewHandlerProvider != null) {
			Object associatedView = editingModel.getAssociatedView((EObject) getTarget());
			if (viewHandlerProvider.canHandle(associatedView)) {
				ViewHandler handler = viewHandlerProvider.getHandler(associatedView);
				if (handler != null) {
					this.viewHandler = handler;
					return handler;
				}
			}
		}
		return null;
	}
}
