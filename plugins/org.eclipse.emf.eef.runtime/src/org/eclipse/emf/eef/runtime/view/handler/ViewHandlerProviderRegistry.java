/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.handler;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewHandlerProviderRegistry {

	ViewHandlerProvider getViewHandlerProvider(Object element);
	
}
