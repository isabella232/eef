/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.util;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.ui.swt.notify.EEFDoubleClickFactory;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFDoubleClickFactoryUtil {

	/**
	 * @param bundle
	 * @param editingDomain
	 * @param adapterFactory
	 * @return
	 */
	public final static IDoubleClickListener getEEFDoubleClickListener(final Bundle bundle, EditingDomain editingDomain, AdapterFactory adapterFactory) {
		ServiceReference serviceReference = (ServiceReference) bundle.getBundleContext().getServiceReference(EEFDoubleClickFactory.class.getName());
		EEFDoubleClickFactory service = (EEFDoubleClickFactory) bundle.getBundleContext().getService(serviceReference);
		IDoubleClickListener listener = service.createListener(editingDomain, adapterFactory);
		bundle.getBundleContext().ungetService(serviceReference);
		return listener;
	}
	
}
