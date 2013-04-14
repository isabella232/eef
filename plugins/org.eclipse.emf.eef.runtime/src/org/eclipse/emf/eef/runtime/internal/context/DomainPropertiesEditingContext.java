/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import java.util.Dictionary;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.osgi.service.component.ComponentContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class DomainPropertiesEditingContext extends EObjectPropertiesEditingContext implements DomainAwarePropertiesEditingContext {
	
	public static final String FACTORY_ID = "org.eclipse.emf.eef.runtime.internal.context.DomainPropertiesEditingContext";

	private EditingDomain editingDomain;
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.context.EObjectPropertiesEditingContext#configure(org.osgi.service.component.ComponentContext)
	 */
	@Override
	public void configure(ComponentContext context) {
		@SuppressWarnings("rawtypes")
		Dictionary properties = context.getProperties();		
		Object eObjectParam = properties.get(PropertiesEditingContext.EOBJECT_PARAM);
		if (eObjectParam instanceof EObject) {
			this.eObject = (EObject) eObjectParam;
			Object domainParam = properties.get(DomainAwarePropertiesEditingContext.EDITINGDOMAIN_PARAM);
			if (domainParam instanceof AdapterFactoryEditingDomain) {
				this.editingDomain = (AdapterFactoryEditingDomain) domainParam;
				this.adapterFactory = ((AdapterFactoryEditingDomain)this.editingDomain).getAdapterFactory();
				this.options = new ContextOptions();
			} else if (domainParam instanceof EditingDomain) {
				this.editingDomain = (EditingDomain) domainParam;
				Object adapterFactoryParam = properties.get(PropertiesEditingContext.ADAPTERFACTORY_PARAM);
				if (adapterFactoryParam instanceof AdapterFactory) {
					this.adapterFactory = (AdapterFactory) adapterFactoryParam;
					this.options = new ContextOptions();
				} 
			} else {
				Object parentContextParam = properties.get(PropertiesEditingContext.PARENTCONTEXT_PARAM);
				if (parentContextParam instanceof PropertiesEditingContext) {
					this.parentContext = (PropertiesEditingContext) parentContextParam;
					this.options = new ContextOptions(parentContext.getOptions());
				}
			}
			this.editingRecorder = new EditingRecorderImpl();
			editingRecorder.initRecording(eObject);
		} else {
			throw new IllegalArgumentException("Unable to instanciate an DomainPropertiesEditingContext with the given parameters.");
		}
	}

	/**
	 * @return the editingDomain
	 */
	public EditingDomain getEditingDomain() {
		if (editingDomain != null) {
			return editingDomain;
		} else {
			if (parentContext instanceof DomainAwarePropertiesEditingContext) {
				return ((DomainAwarePropertiesEditingContext) parentContext).getEditingDomain();
			}
		}
		return null;
	}

}
