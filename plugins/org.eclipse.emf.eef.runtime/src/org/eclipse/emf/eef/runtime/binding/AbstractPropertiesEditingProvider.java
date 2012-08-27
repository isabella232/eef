/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.internal.binding.PropertiesEditingComponentImpl;
import org.eclipse.emf.eef.runtime.internal.editingModel.EditingModelEnvironmentImpl;
import org.eclipse.emf.eef.runtime.internal.notify.ModelChangesNotifier;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.util.impl.EMFServiceRegistry;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProviderRegistry;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class AbstractPropertiesEditingProvider implements PropertiesEditingProvider {

	private List<PropertiesEditingModel> editingModels;
	private EditingModelEnvironment editingModelEnvironment;
	
	private EMFServiceProvider emfServiceProvider;
	private ViewHandlerProviderRegistry viewHandlerProviderRegistry;
	private BundleContext bundleContext;
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider#setEMFServiceProvider(EMFServiceRegistry)
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider#setViewHandlerProviderRegistry(ViewHandlerProviderRegistry)
	 */
	public void setViewHandlerProviderRegistry(ViewHandlerProviderRegistry viewHandlerProviderRegistry) {
		this.viewHandlerProviderRegistry = viewHandlerProviderRegistry;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider#unsetViewHandlerProviderRegistry(org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProviderRegistry)
	 */
	public void unsetViewHandlerProviderRegistry(ViewHandlerProviderRegistry viewHandlerProviderRegistry) {
		if (viewHandlerProviderRegistry == this.viewHandlerProviderRegistry) {
			this.viewHandlerProviderRegistry = null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider#setBundleContext(org.osgi.framework.BundleContext)
	 */
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider#getViewHandlerProvider(java.lang.Object)
	 */
	public ViewHandlerProvider getViewHandlerProvider(Object view) {
		return viewHandlerProviderRegistry.getViewHandlerProvider(view);
	}

	/**
	 * Determines if the current provided is designed to provide
	 * {@link PropertiesEditingComponent} for the given {@link EPackage}.
	 * 
	 * @param ePackage {@link EPackage} to test.
	 * @return <code>true</code> if the current provider can process the given {@link EPackage}.
	 */
	public boolean serviceFor(final EPackage element) {
		List<EClassBinding> allBindings = Lists.newArrayList();
		for (PropertiesEditingModel editingModel : getEditingModels()) {
			allBindings.addAll(editingModel.getBindings());
		}
		Collection<EClassBinding> filter = Collections2.filter(allBindings, Predicates.compose(
				new Predicate<EPackage>() {
					/**
					 * {@inheritDoc}
					 * @see com.google.common.base.Predicate#apply(java.lang.Object)
					 */
					public boolean apply(EPackage input) {
						EMFService emfService = emfServiceProvider.getEMFServiceForPackage(element);
						return ((emfService != null && emfService.equals(element, input)) || element == input);
					}

				}, 
				new Function<EClassBinding, EPackage>() {
					/**
					 * {@inheritDoc}
					 * @see com.google.common.base.Function#apply(java.lang.Object)
					 */
					public EPackage apply(EClassBinding input) {
						return input.getEClass().getEPackage();
					}
				}));
		return filter.size() > 0;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider#createComponent(org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingComponent createComponent(EObject target) {
		PropertiesEditingComponentImpl component = new PropertiesEditingComponentImpl(this, target);
		Dictionary<String, Object> properties = new Hashtable<String, Object>(); 
		properties.put(EventConstants.EVENT_TOPIC, ModelChangesNotifier.EEF_EVENT_BASE_TOPIC + ModelChangesNotifier.EEF_ECLASS_NOTIFICATION_TOPIC + target.eClass().getEPackage().getName() + "_" + target.eClass().getName());		
		bundleContext.registerService(EventHandler.class.getName(), component, properties);
		return component;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider#getEditingModelEnvironment()
	 */
	public EditingModelEnvironment getEditingModelEnvironment() {
		if (editingModelEnvironment == null) {
			editingModelEnvironment = new EditingModelEnvironmentImpl();
		}
		return editingModelEnvironment;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider#getEditingModel(org.eclipse.emf.ecore.EObject)
	 */
	public final PropertiesEditingModel getEditingModel(EObject eObject) {
		for (PropertiesEditingModel editingModel : getEditingModels()) {
			if (editingModel.getEMFServiceProvider() == null) {
				editingModel.setEMFServiceProvider(emfServiceProvider);
			}
			if (editingModel.binding(eObject) != null) {
				return editingModel;
			}
		}
		return null;
	}

	/**
	 * @return the specific {@link PropertiesEditingModel}s to use from this {@link AbstractPropertiesEditingProvider}.
	 */
	protected Collection<? extends PropertiesEditingModel> initSpecificEditingModel() {
		return Collections.emptyList();
	}
	
	/**
	 * Compute the list of available editingModel in this context.
	 * @return a list of {@link PropertiesEditingModel} available from this {@link AbstractPropertiesEditingProvider}.
	 */
	private List<PropertiesEditingModel> getEditingModels() {
		if (editingModels == null) {
			editingModels = Lists.newArrayList();
			editingModels.addAll(initSpecificEditingModel());
		}
		return editingModels;
	}

}
