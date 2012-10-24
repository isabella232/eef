/**
 * 
 */
package org.eclipse.emf.ecore.presentation;

import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class EcoreTabbedEditor extends EcoreEditor implements ITabbedPropertySheetPageContributor {

	public static final String PROPERTIES_SHEET_CONTRIBUTOR_ID = "org.eclipse.emf.ecore.editor.tabbed.properties";

	private TabbedPropertySheetPage propertySheetPage;
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor#getContributorId()
	 */
	public String getContributorId() {
		return PROPERTIES_SHEET_CONTRIBUTOR_ID;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.presentation.EcoreEditor#getPropertySheetPage()
	 */
	@Override
	public IPropertySheetPage getPropertySheetPage() {
		if (propertySheetPage == null) {
			propertySheetPage = new TabbedPropertySheetPage(this);
		}

		return propertySheetPage;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecore.presentation.EcoreEditor#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		if (propertySheetPage != null) {
			propertySheetPage.dispose();
		}
	}

}
