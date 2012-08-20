/**
 * 
 */
package org.eclipse.emf.eef.runtime.context.impl;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.BatchSemanticDomainEditingPolicy;
import org.eclipse.emf.eef.runtime.internal.policies.LiveSemanticDomainEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class DomainPropertiesEditingContext extends EObjectPropertiesEditingContext {

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

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext#getEditingPolicy(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public PropertiesEditingPolicy getEditingPolicy(PropertiesEditingContext editingContext) {
		if (editingContext instanceof SemanticPropertiesEditingContext) {
			SemanticPropertiesEditingContext semanticEditingContext = (SemanticPropertiesEditingContext) editingContext;
			if (semanticEditingContext.getOptions().liveMode()) {
				return new LiveSemanticDomainEditingPolicy(editingDomain, semanticEditingContext.getEditingComponent(), semanticEditingContext.getEditingEvent());
			} 
			if (semanticEditingContext.getOptions().batchMode()) {
				return new BatchSemanticDomainEditingPolicy(editingDomain, semanticEditingContext.getEditingComponent(), semanticEditingContext.getEditingEvent());
			} 
		}
		return super.getEditingPolicy(editingContext);
	}

}
