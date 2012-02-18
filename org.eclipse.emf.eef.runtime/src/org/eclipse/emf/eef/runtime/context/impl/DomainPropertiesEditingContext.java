/**
 * 
 */
package org.eclipse.emf.eef.runtime.context.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.SemanticDomainEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class DomainPropertiesEditingContext extends EObjectPropertiesEditingContext {

	private EditingDomain editingDomain;

	/**
	 * @param eObject
	 */
	public DomainPropertiesEditingContext(EditingDomain editingDomain, EObject eObject) {
		super(eObject);
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
			return new SemanticDomainEditingPolicy(editingDomain, semanticEditingContext.getEditingComponent(), semanticEditingContext.getEditingEvent());
		}
		return super.getEditingPolicy(editingContext);
	}

	
	
	
}
