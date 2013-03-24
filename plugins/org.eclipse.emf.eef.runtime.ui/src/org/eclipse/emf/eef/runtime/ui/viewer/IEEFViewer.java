/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.viewer;

import org.eclipse.emf.eef.runtime.ui.viewer.filters.EEFViewerFilter;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface IEEFViewer {

	/**
	 * @return the edited context by the current viewer.
	 */
	Object getInput();

	/**
	 * Defines the context to edit with this viewer.
	 * @param context the edited context.
	 */
	void setInput(Object context);

	/**
	 * Adds a filter to the viewer.
	 * @param filter the {@link EEFViewerFilter} to add.
	 */
	void addFilter(EEFViewerFilter filter);

	/**
	 * Removes a filter to the viewer.
	 * @param filter the {@link EEFViewerFilter} to remove.
	 */
	void removeFilter(EEFViewerFilter filter);

}
