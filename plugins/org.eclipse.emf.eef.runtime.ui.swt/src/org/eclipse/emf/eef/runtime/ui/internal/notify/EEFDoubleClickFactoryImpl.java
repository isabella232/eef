/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.notify;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.notify.EEFDoubleClickFactory;
import org.eclipse.emf.eef.runtime.ui.notify.OpenWizardOnDoubleClick;
import org.eclipse.jface.viewers.IDoubleClickListener;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFDoubleClickFactoryImpl implements EEFDoubleClickFactory {

	private EEFServiceRegistry serviceRegistry;
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.notify.EEFDoubleClickFactory#setServiceRegistry(org.eclipse.emf.eef.runtime.services.EEFServiceRegistry)
	 */
	public void setServiceRegistry(EEFServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.notify.EEFDoubleClickFactory#createListener(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.common.notify.AdapterFactory)
	 */
	public IDoubleClickListener createListener(EditingDomain domain, AdapterFactory adapterFactory) {
		OpenWizardOnDoubleClick openWizardOnDoubleClick = new OpenWizardOnDoubleClick(domain, adapterFactory);
		openWizardOnDoubleClick.setServiceRegistry(serviceRegistry);
		return openWizardOnDoubleClick;
		
	}
	
}
