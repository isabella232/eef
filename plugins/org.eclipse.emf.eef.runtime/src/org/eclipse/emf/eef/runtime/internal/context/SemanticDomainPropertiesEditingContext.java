/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SemanticDomainPropertiesEditingContext extends SemanticPropertiesEditingContextImpl implements DomainAwarePropertiesEditingContext {

	/**
	 * @param parentContext
	 * @param editingEvent
	 */
	SemanticDomainPropertiesEditingContext(DomainAwarePropertiesEditingContext parentContext, PropertiesEditingEvent editingEvent) {
		super(parentContext, editingEvent);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContextImpl#getParentContext()
	 */
	@Override
	public DomainAwarePropertiesEditingContext getParentContext() {
		return (DomainAwarePropertiesEditingContext) super.getParentContext();
	}

	/**
	 * @return the editingDomain of the current context.
	 */
	public EditingDomain getEditingDomain() {
		return getParentContext().getEditingDomain();
	}
	
}
