/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.viewer.util;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.jface.viewers.IContentProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFContentProvider extends IContentProvider {

	PropertiesEditingContext getContext();
	
}
