/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.notify;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.services.editing.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.ui.swt.notify.EEFDoubleClickFactory;
import org.eclipse.emf.eef.runtime.ui.swt.notify.OpenWizardOnDoubleClick;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.jface.viewers.IDoubleClickListener;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFDoubleClickFactoryImpl implements EEFDoubleClickFactory {

	private EditingContextFactoryProvider contextFactoryProvider;
	private EMFServiceProvider emfServiceProvider;
	private EEFEditingServiceProvider eefEditingServiceProvider;
	private EditUIProvidersFactory editUIProvidersFactory;

	/**
	 * @param contextFactoryProvider the contextFactoryProvider to set
	 */
	public void setContextFactoryProvider(EditingContextFactoryProvider contextFactoryProvider) {
		this.contextFactoryProvider = contextFactoryProvider;
	}

	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * @param eefEditingServiceProvider the eefEditingServiceProvider to set
	 */
	public void setEEFEditingServiceProvider(EEFEditingServiceProvider eefEditingServiceProvider) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
	}

	/**
	 * @param editUIProvidersFactory the editUIProvidersFactory to set
	 */
	public void setEditUIProvidersFactory(EditUIProvidersFactory editUIProvidersFactory) {
		this.editUIProvidersFactory = editUIProvidersFactory;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.notify.EEFDoubleClickFactory#createListener(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.common.notify.AdapterFactory)
	 */
	public IDoubleClickListener createListener(EditingDomain domain, AdapterFactory adapterFactory) {
		OpenWizardOnDoubleClick openWizardOnDoubleClick = new OpenWizardOnDoubleClick(domain, adapterFactory);
		openWizardOnDoubleClick.setContextFactoryProvider(contextFactoryProvider);
		openWizardOnDoubleClick.setEMFServiceProvider(emfServiceProvider);
		openWizardOnDoubleClick.setEEFEditingServiceProvider(eefEditingServiceProvider);
		openWizardOnDoubleClick.setEditUIProvidersFactory(editUIProvidersFactory);
		return openWizardOnDoubleClick;
		
	}
	
}
