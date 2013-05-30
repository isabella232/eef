/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.osgi.service.component.ComponentContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SemanticPropertiesEditingContextImpl extends DelegatingPropertiesEditingContext implements SemanticPropertiesEditingContext {

	protected PropertiesEditingEvent editingEvent;
	protected ContextOptions options;
	
	/**
	 * Configure the current component instance with the given properties.
	 * @param context {@link ComponentContext} to use to configure the current instance.
	 */
	SemanticPropertiesEditingContextImpl(PropertiesEditingContext parentContext, PropertiesEditingEvent editingEvent) {
		super(parentContext);
		this.editingEvent = editingEvent;
		this.options = new ContextOptions(parentContext.getOptions());
	}

	/**
	 * @return the parent {@link PropertiesEditingContext} of the current context.
	 */
	public PropertiesEditingContext getParentContext() {
		return getDelegatingContext();
	}
	
	/**
	 * @return the {@link PropertiesEditingEvent} which have generated this context.
	 */
	public PropertiesEditingEvent getEditingEvent() {
		return editingEvent;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.context.DelegatingPropertiesEditingContext#getOptions()
	 */
	@Override
	public ContextOptions getOptions() {
		return options;
	}
	
}
