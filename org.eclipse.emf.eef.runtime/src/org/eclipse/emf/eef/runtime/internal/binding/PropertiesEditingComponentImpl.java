/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.model.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.ViewChangeNotifier;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewHandlingException;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingComponentImpl extends AdapterImpl implements PropertiesEditingComponent {

	private PropertiesEditingContext editingContext;
	private PropertiesEditingModel editingModel;
	private ViewHandler<?> viewHandler;
	private ViewChangeNotifier viewChangeNotifier;
	
	/**
	 * @param editingModel
	 */
	public PropertiesEditingComponentImpl(PropertiesEditingModel editingModel) {
		this.editingModel = editingModel;
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
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
	 */
	public boolean isAdapterForType(Object type) {
		return type == PropertiesEditingComponent.class;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	public void notifyChanged(Notification msg) {
		if (msg.getEventType() == Notification.SET) {
			if (msg.getFeature() instanceof EStructuralFeature) {
				EStructuralFeature structuralFeature = (EStructuralFeature)msg.getFeature();
				if (structuralFeature.isMany()) {
					//TODO: to be continued!
				} else {
					try {
						viewHandler.setValue(structuralFeature.getName(), msg.getNewValue());
					} catch (ViewHandlingException e) {
						//TODO: define an error management strategy
					}
				}
			}
		}
	}
	
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getViewChangeNotifier()
	 */
	public ViewChangeNotifier getViewChangeNotifier() {
		if (viewChangeNotifier == null) {
			viewChangeNotifier = new ViewChangeNotifier(this);
		}
		return viewChangeNotifier;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#fireViewChange(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public void fireViewChange(PropertiesEditingEvent editingEvent) {
		EObject editedObject = (EObject) getTarget();
		EStructuralFeature feature = editedObject.eClass().getEStructuralFeature((String)editingEvent.getAffectedEditor());
		//TODO: version super triviale. Il faut checker le type d'event pour décider de l'opération à effectuer.
		//      il faudra également checker les config interne de l'editingModel pour voir s'il n'y a pas un comportement de redéfini.
		// 		Je pense également peut etre à un getSF case insensitive ...
		if (feature != null) {
			editedObject.eSet(feature, editingEvent.getNewValue());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getViewHandler()
	 */
	public ViewHandler<?> getViewHandler() {
		ViewHandlerProvider viewHandlerProvider = editingContext.getViewHandlerProvider();
		if (viewHandlerProvider != null) {
			Object associatedView = editingModel.getAssociatedView((EObject) getTarget());
			if (viewHandlerProvider.canHandle(associatedView)) {
				ViewHandler<?> handler = viewHandlerProvider.getHandler(associatedView);
				if (handler != null) {
					this.viewHandler = handler;
					return handler;
				}
			}
		}
		return null;
	}
}
