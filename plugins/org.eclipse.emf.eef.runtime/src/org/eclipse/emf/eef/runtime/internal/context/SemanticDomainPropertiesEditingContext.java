/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import java.util.Dictionary;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.osgi.service.component.ComponentContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SemanticDomainPropertiesEditingContext extends SemanticPropertiesEditingContextImpl implements DomainAwarePropertiesEditingContext {

	public static final String FACTORY_ID = "org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext";
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContextImpl#configure(org.osgi.service.component.ComponentContext)
	 */
	@Override
	public void configure(ComponentContext context) {
		@SuppressWarnings("rawtypes")
		Dictionary properties = context.getProperties();		
		Object parentContextParam = properties.get(PARENTCONTEXT_PARAM);
		Object editingEventParam = properties.get(EDITINGEVENT_PARAM);
		if (parentContextParam instanceof PropertiesEditingContext && editingEventParam instanceof PropertiesEditingEvent) {
			this.delegatingContext = (PropertiesEditingContext) parentContextParam;
			this.editingEvent = (PropertiesEditingEvent) editingEventParam;
			this.options = new ContextOptions(delegatingContext.getOptions());
		} else {
			throw new IllegalArgumentException("Unable to instanciate an SemanticPropertiesEditingContextImpl with the given parameters.");
		}
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
