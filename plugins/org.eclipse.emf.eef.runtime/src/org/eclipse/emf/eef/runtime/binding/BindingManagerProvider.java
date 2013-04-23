/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface BindingManagerProvider {

	/**
	 * Returns a {@link PropertiesBindingManager} able to handle the given component.
	 * @param editingComponent the {@link PropertiesEditingComponent} to manage.
	 * @return a {@link PropertiesBindingManager}.
	 */
	PropertiesBindingManager getBindingManager(PropertiesEditingComponent editingComponent);
	
}
