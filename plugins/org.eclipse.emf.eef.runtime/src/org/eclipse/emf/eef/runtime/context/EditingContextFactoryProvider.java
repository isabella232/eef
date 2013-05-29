/**
 * 
 */
package org.eclipse.emf.eef.runtime.context;

import org.eclipse.emf.ecore.EObject;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditingContextFactoryProvider {

	/**
	 * Returns a {@link PropertiesEditingContextFactory} able to provide a {@link PropertiesEditingContextFactory} for the given {@link EObject}.
	 * @param source the {@link EObject} to process.
	 * @return the more appropriate {@link PropertiesEditingContextFactory}.
	 */
	PropertiesEditingContextFactory getEditingContextFactory(EObject source);
	
	
}
