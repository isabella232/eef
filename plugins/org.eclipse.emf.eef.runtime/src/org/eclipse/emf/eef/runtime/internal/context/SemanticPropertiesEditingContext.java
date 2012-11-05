/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SemanticPropertiesEditingContext extends DelegatingPropertiesEditingContext {

	private PropertiesEditingEvent editingEvent;
	private ContextOptions options;
	
	/**
	 * @param parentContext
	 * @param editingEvent
	 */
	SemanticPropertiesEditingContext(PropertiesEditingContext parentContext, PropertiesEditingEvent editingEvent) {
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
