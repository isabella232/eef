/**
 * 
 */
package org.eclipse.emf.eef.runtime.context;

import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface DomainAwarePropertiesEditingContext extends PropertiesEditingContext {
	
	final String EDITINGDOMAIN_PARAM = "EditingDomain Parameter";

	/**
	 * @return the {@link EditingDomain} owned by the current context.
	 */
	EditingDomain getEditingDomain();
	
}
