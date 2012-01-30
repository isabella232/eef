/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SemanticPropertiesEditingContext extends DelegatingPropertiesEditingContext {

	private PropertiesEditingComponent editingComponent;
	private PropertiesEditingEvent editingEvent;
	
	/**
	 * @param editingComponent
	 * @param viewChangeNotifier
	 */
	public SemanticPropertiesEditingContext(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		super(editingComponent.getEditingContext());
		this.editingComponent = editingComponent;
		this.editingEvent = editingEvent;
		
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.context.DelegatingPropertiesEditingContext#getEditingComponent()
	 */
	public PropertiesEditingComponent getEditingComponent() {
		return editingComponent;
	}



	/**
	 * @return the viewChangeNotifier
	 */
	public PropertiesEditingEvent getEditingEvent() {
		return editingEvent;
	}

}
