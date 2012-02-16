/**
 * 
 */
package org.eclipse.emf.eef.runtime.notify;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewChangeNotifier implements PropertyChangeListener {

	private PropertiesEditingComponent component;
	
	/**
	 * @param component {@link PropertiesEditingComponent} to notify.
	 */
	public ViewChangeNotifier(PropertiesEditingComponent component) {
		this.component = component;
	}

	/**
	 * {@inheritDoc}
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		component.firePropertiesChanged(new PropertiesEditingEventImpl(
				evt.getSource(), evt.getPropertyName(), 
				((evt instanceof TypedPropertyChangedEvent)?((TypedPropertyChangedEvent)evt).getEventType():PropertiesEditingEvent.SET), 
				evt.getOldValue(), evt.getNewValue()));
	}

}
