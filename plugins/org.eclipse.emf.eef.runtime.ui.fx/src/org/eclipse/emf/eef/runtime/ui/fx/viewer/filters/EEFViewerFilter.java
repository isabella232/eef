/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.viewer.filters;

import org.eclipse.emf.eef.runtime.ui.fx.viewer.FXViewer;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFViewerFilter {

    /**
     * Returns whether the given element makes it through this filter.
     *
     * @param viewer the viewer
     * @param element the element
     * @return <code>true</code> if element is included in the filtered set, and <code>false</code> if excluded
     */
	boolean select(FXViewer viewer, Object element);

}
