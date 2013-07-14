/**
 * 
 */
package org.eclipse.emf.eef.runtime.notify;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.emf.eef.runtime.binding.BindingHandlerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewChangeNotifier implements PropertyChangeListener {

	private BindingHandlerProvider bindingHandlerProvider;
	private PropertiesEditingComponent component;
	
	/**
	 * @param bindingHandlerProvider
	 * @param component
	 */
	public ViewChangeNotifier(BindingHandlerProvider bindingHandlerProvider, PropertiesEditingComponent component) {
		this.bindingHandlerProvider = bindingHandlerProvider;
		this.component = component;
	}

	/**
	 * {@inheritDoc}
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		bindingHandlerProvider.getBindingManager(component.getEObject()).firePropertiesChanged(component, new PropertiesEditingEventImpl(
				evt.getSource(), evt.getPropertyName(), 
				((evt instanceof TypedPropertyChangedEvent)?((TypedPropertyChangedEvent)evt).getEventType():PropertiesEditingEvent.SET), 
				evt.getOldValue(), evt.getNewValue()));
	}

}
