/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.viewer.filters;

import java.util.Collection;

import org.eclipse.emf.eef.runtime.ui.fx.viewer.FXViewer;
import org.eclipse.emf.eef.runtime.ui.viewer.IEEFViewer;
import org.eclipse.emf.eef.runtime.ui.viewer.filters.EEFViewerFilter;
import org.eclipse.emf.eef.views.View;

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
	 * @see org.eclipse.emf.eef.runtime.ui.viewer.filters.EEFViewerFilter#select(org.eclipse.emf.eef.runtime.ui.viewer.IEEFViewer, java.lang.Object)
	 */
	public boolean select(IEEFViewer viewer, Object element) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.viewer.filters.EEFViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object)
	 */
	public boolean select(FXViewer viewer, Object element) {
		return element instanceof View && selectedViews.contains(((View)element).getQualifiedIdentifier());
	}

}
