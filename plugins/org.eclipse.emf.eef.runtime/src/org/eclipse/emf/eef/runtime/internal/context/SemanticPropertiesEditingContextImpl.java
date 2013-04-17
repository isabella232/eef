/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import java.util.Dictionary;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.SemanticPropertiesEditingContext;
import org.osgi.service.component.ComponentContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SemanticPropertiesEditingContextImpl extends DelegatingPropertiesEditingContext implements SemanticPropertiesEditingContext {

	public static final String FACTORY_ID = "org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContextImpl";
	
	public static final String EDITINGEVENT_PARAM = "EditingEvent Parameter";
	
	protected PropertiesEditingEvent editingEvent;
	protected ContextOptions options;
	
	/**
	 * Configure the current component instance with the given properties.
	 * @param context {@link ComponentContext} to use to configure the current instance.
	 */
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
