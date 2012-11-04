/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class DomainPropertiesEditingContext extends EObjectPropertiesEditingContext implements DomainAwarePropertiesEditingContext {

	private EditingDomain editingDomain;

	/**
	 * @param editingDomain
	 * @param adapterFactory
	 * @param eObject
	 */
	DomainPropertiesEditingContext(EditingDomain editingDomain, AdapterFactory adapterFactory, EObject eObject) {
		super(adapterFactory, eObject);
		this.editingDomain = editingDomain;
	}

	DomainPropertiesEditingContext(AdapterFactoryEditingDomain editingDomain, EObject eObject) {
		super(editingDomain.getAdapterFactory(), eObject);
		this.editingDomain = editingDomain;
	}
	
	/**
	 * @return the editingDomain
	 */
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

}
