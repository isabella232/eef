/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.services.editingProvider;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.internal.binding.PropertiesEditingComponentImpl;
import org.eclipse.emf.eef.runtime.internal.editingModel.EditingModelEnvironmentImpl;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.services.EEFComponentRegistry;
import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;

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
	
	private EEFComponentRegistry componentRegistry;
	private ModelChangesNotificationManager notificationManager;
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider#setComponentRegistry(org.eclipse.emf.eef.runtime.services.impl.EEFComponentRegistryImpl)
	 */
	public void setComponentRegistry(EEFComponentRegistry componentRegistry) {
		this.componentRegistry = componentRegistry;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider#setNotificationManager(ModelChangesNotificationManager)
	 */
	public void setNotificationManager(ModelChangesNotificationManager notificationManager) {
		this.notificationManager = notificationManager;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider#getViewHandlerProvider(java.lang.Object)
	 */
	public ViewHandlerProvider getViewHandlerProvider(Object view) {
		return (ViewHandlerProvider) componentRegistry.getService(ViewHandlerProvider.class, view);
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
						EMFService emfService = (EMFService) componentRegistry.getService(EMFService.class, element);
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
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider#createComponent(org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingComponent createComponent(EObject target) {
		PropertiesEditingComponent component = new PropertiesEditingComponentImpl(this, target);
		notificationManager.registerEditingComponentAsEventHandler(component);
		return component;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider#disposeComponent(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	public void disposeComponent(PropertiesEditingComponent component) {
		notificationManager.unregisterEditingComponent(component);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider#getEditingModelEnvironment()
	 */
	public EditingModelEnvironment getEditingModelEnvironment() {
		if (editingModelEnvironment == null) {
			editingModelEnvironment = new EditingModelEnvironmentImpl();
		}
		return editingModelEnvironment;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider#getEditingModel(org.eclipse.emf.ecore.EObject)
	 */
	public final PropertiesEditingModel getEditingModel(EObject eObject) {
		for (PropertiesEditingModel editingModel : getEditingModels()) {
			if (editingModel.getComponentRegistry() == null) {
				editingModel.setComponentRegistry(componentRegistry);
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
