/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface SemanticPropertiesEditingContext extends PropertiesEditingContext {
	
	/**
	 * @return the {@link PropertiesEditingEvent} which have generated this context.
	 */
	PropertiesEditingEvent getEditingEvent();

}
