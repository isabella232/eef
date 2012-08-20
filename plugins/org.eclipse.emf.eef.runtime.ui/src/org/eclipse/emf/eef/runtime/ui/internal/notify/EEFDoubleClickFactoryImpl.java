/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.notify;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.ui.notify.EEFDoubleClickFactory;
import org.eclipse.emf.eef.runtime.ui.notify.OpenWizardOnDoubleClick;
import org.eclipse.jface.viewers.IDoubleClickListener;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFDoubleClickFactoryImpl implements EEFDoubleClickFactory {

	
	PropertiesEditingContextFactory editingContextFactory;
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.notify.EEFDoubleClickFactory#setEditingContextFactory(org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory)
	 */
	public void setEditingContextFactory(PropertiesEditingContextFactory editingContextFactory) {
		if (editingContextFactory == this.editingContextFactory) {
			this.editingContextFactory = null;			
		} else {
			this.editingContextFactory = editingContextFactory;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.notify.EEFDoubleClickFactory#createListener(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.common.notify.AdapterFactory)
	 */
	public IDoubleClickListener createListener(EditingDomain domain, AdapterFactory adapterFactory) {
		OpenWizardOnDoubleClick openWizardOnDoubleClick = new OpenWizardOnDoubleClick(domain, adapterFactory);
		openWizardOnDoubleClick.setEditingContextFactory(editingContextFactory);
		return openWizardOnDoubleClick;
		
	}
	
}
