/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.notify;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.jface.viewers.IDoubleClickListener;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFDoubleClickFactory {

	/**
	 * Defines the {@link PropertiesEditingContextFactory} to use in the {@link IDoubleClickListener}.
	 * @param editingContextFactory {@link PropertiesEditingContextFactory} to set.
	 */
	void setEditingContextFactory(PropertiesEditingContextFactory editingContextFactory);

	/**
	 * Creates a {@link IDoubleClickListener} that open an EEF wizard to 
	 * @param domain {@link EditingDomain} to use to edit the obkects.
	 * @param adapterFactory {@link AdapterFactory} to use in the EEF context.
	 * @return an {@link IDoubleClickListener} to edit {@link EObject} with EEF.
	 */
	IDoubleClickListener createListener(EditingDomain domain, AdapterFactory adapterFactory);

}
