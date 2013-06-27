/**
 * 
 */
package org.eclipse.emf.eef.runtime.notify;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.emf.eef.runtime.binding.BindingManagerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewChangeNotifier implements PropertyChangeListener {

	private BindingManagerProvider bindingManagerProvider;
	private PropertiesEditingComponent component;
	
	/**
	 * @param bindingManagerProvider
	 * @param component
	 */
	public ViewChangeNotifier(BindingManagerProvider bindingManagerProvider, PropertiesEditingComponent component) {
		this.bindingManagerProvider = bindingManagerProvider;
		this.component = component;
	}

	/**
	 * {@inheritDoc}
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		bindingManagerProvider.getBindingManager(component.getEObject()).firePropertiesChanged(component, new PropertiesEditingEventImpl(
				evt.getSource(), evt.getPropertyName(), 
				((evt instanceof TypedPropertyChangedEvent)?((TypedPropertyChangedEvent)evt).getEventType():PropertiesEditingEvent.SET), 
				evt.getOldValue(), evt.getNewValue()));
	}

}
