/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.notify;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.ui.swt.notify.EEFDoubleClickFactory;
import org.eclipse.emf.eef.runtime.ui.swt.notify.OpenWizardOnDoubleClick;
import org.eclipse.jface.viewers.IDoubleClickListener;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFDoubleClickFactoryImpl implements EEFDoubleClickFactory {

	private EditingContextFactoryProvider contextFactoryProvider;

	/**
	 * @param contextFactoryProvider the contextFactoryProvider to set
	 */
	public void setContextFactoryProvider(EditingContextFactoryProvider contextFactoryProvider) {
		this.contextFactoryProvider = contextFactoryProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.notify.EEFDoubleClickFactory#createListener(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.common.notify.AdapterFactory)
	 */
	public IDoubleClickListener createListener(EditingDomain domain, AdapterFactory adapterFactory) {
		OpenWizardOnDoubleClick openWizardOnDoubleClick = new OpenWizardOnDoubleClick(domain, adapterFactory);
		openWizardOnDoubleClick.setContextFactoryProvider(contextFactoryProvider);
		return openWizardOnDoubleClick;
		
	}
	
}
