/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.services.impl.PriorityCircularityException;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingContextFactoryProviderImpl extends EEFServiceProviderImpl<EObject, PropertiesEditingContextFactory> implements EditingContextFactoryProvider {
	
	public final void addContextFactory(PropertiesEditingContextFactory contextFactory, Map<String, ?> properties) throws PriorityCircularityException {
		addService(contextFactory, properties);
		contextFactory.setProvider(this);
	}
	
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider#getEditingContextFactory(org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContextFactory getEditingContextFactory(EObject source) {
		return getService(source);
	}

}
