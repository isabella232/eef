/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.handler;

import java.util.List;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewHandlerProviderRegistry {

	List<ViewHandlerProvider> getViewHandlerProviders();
	
}
