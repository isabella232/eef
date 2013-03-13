/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.viewer.filters;

import java.util.Collection;

import org.eclipse.emf.eef.views.View;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewFilter implements EEFViewerFilter {

	private Collection<String> selectedViews;
	
	/**
	 * @param selectedViews
	 */
	public ViewFilter(Collection<String> selectedViews) {
		this.selectedViews = selectedViews;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.viewer.filters.EEFViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object element) {
		return element instanceof View && selectedViews.contains(((View)element).getQualifiedIdentifier());
	}

}
