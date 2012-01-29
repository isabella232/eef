/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
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
	private List<ViewHandler<?>> viewHandlers;
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
		switch (msg.getEventType()) {
		case Notification.SET:
			if (msg.getFeature() instanceof EStructuralFeature) {
				EStructuralFeature structuralFeature = (EStructuralFeature)msg.getFeature();
				EClassBinding binding = editingModel.binding((EObject) getTarget());
				Object propertyEditor = binding.propertyEditor(structuralFeature);
				try {
					// TODO: Ici se joue la résolution de feature. 
					//		 Ensuite la logique est transmise au handler.
					//		 Dans le cas du reflect, si c'est une string, il bidouille un setter, sinon, il faudrait qu'il appelle la bonne methode
					//		 Ce qui implique de pouvoir définir cette méthode ...
					for (ViewHandler<?> viewHandler : viewHandlers) {
						viewHandler.setValue(propertyEditor, msg.getNewValue());						
					}
				} catch (ViewHandlingException e) {
					//TODO: define an error management strategy
				}
			}			
			break;
		case Notification.UNSET: {
			
		}
		default:
			break;
		}
		if (msg.getEventType() == Notification.SET) {
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
		EClassBinding binding = editingModel.binding(editedObject);
		EStructuralFeature feature = binding.feature(editingEvent.getAffectedEditor());
		//TODO: version super triviale. Il faut checker le type d'event pour décider de l'opération à effectuer.
		// 		Je pense également peut etre à un getSF case insensitive ...
		if (feature != null) {
			editingContext.performSet(editedObject, feature, editingEvent.getNewValue());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getViewHandlers()
	 */
	public List<ViewHandler<?>> getViewHandlers() {
		viewHandlers = new ArrayList<ViewHandler<?>>();
		ViewHandlerProvider viewHandlerProvider = editingContext.getViewHandlerProvider();
		if (viewHandlerProvider != null) {
			List<Object> associatedViews = editingModel.views((EObject) getTarget());
			for (Object associatedView : associatedViews) {
				ViewHandler<?> specifiedHandler = editingModel.viewHandler((EObject) getTarget(), associatedView);
				if (specifiedHandler != null) {
					viewHandlers.add(specifiedHandler);
				} else {
					if (viewHandlerProvider.canHandle(associatedView)) {
						ViewHandler<?> handler = viewHandlerProvider.getHandler(associatedView);
						if (handler != null) {
							viewHandlers.add(handler);
						}
					}
				}
			}
		}
		return viewHandlers;
	}
}
