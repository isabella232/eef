/**
 * 
 */
package org.eclipse.emf.eef.runtime.editingModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.EEFRuntime;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.internal.binding.PropertiesEditingComponentImpl;
import org.eclipse.emf.eef.runtime.internal.editingModel.EditingModelEnvironmentImpl;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class AbstractPropertiesEditingProvider extends AdapterFactoryImpl implements AdapterFactory {

	private List<PropertiesEditingModel> editingModels;
	private ViewHandlerProvider viewHandlerProvider;
	private EditingModelEnvironment editingModelEnvironment;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.common.notify.impl.AdapterFactoryImpl#createAdapter(org.eclipse.emf.common.notify.Notifier, java.lang.Object)
	 */
	protected final Adapter createAdapter(Notifier target, Object type) {
		return new PropertiesEditingComponentImpl(this);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.common.notify.impl.AdapterFactoryImpl#isFactoryForType(java.lang.Object)
	 */
	public boolean isFactoryForType(final Object type) {
		if (type == PropertiesEditingComponent.class) {
			return true;
		} else {
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
							if (type instanceof EPackage) {
								EMFService emfService = EEFRuntime.getPlugin().getEMFService((EPackage) type);
								return ((emfService != null && emfService.equals((EPackage)type, input)) || type == input);
							}
							return false;
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
	}
	
	/**
	 * @return the editingModelEnvironment of this provider.
	 */
	public EditingModelEnvironment getEditingModelEnvironment() {
		if (editingModelEnvironment == null) {
			editingModelEnvironment = new EditingModelEnvironmentImpl();
		}
		return editingModelEnvironment;
	}

	/**
	 * Returns the EditingModel describing the editing forms to edit the given object.
	 * @param eObject the {@link EObject} to edit.
	 * @return the {@link PropertiesEditingModel} to use for edit the given EObject.
	 */
	public final PropertiesEditingModel getEditingModel(EObject eObject) {
		for (PropertiesEditingModel editingModel : getEditingModels()) {
			if (editingModel.binding(eObject) != null) {
				return editingModel;
			}
		}
		return null;
	}

	/**
	 * @return the {@link ViewHandlerProvider} to use from this {@link AbstractPropertiesEditingProvider}.
	 */
	public final ViewHandlerProvider getViewHandlerProvider() {
		if (viewHandlerProvider == null) {
			viewHandlerProvider = initViewHandlerProvider();
		}
		return viewHandlerProvider;
	}

	/**
	 * @return the specific {@link PropertiesEditingModel}s to use from this {@link AbstractPropertiesEditingProvider}.
	 */
	protected Collection<? extends PropertiesEditingModel> initSpecificEditingModel() {
		return Collections.emptyList();
	}
	
	/**
	 * @return the {@link ViewHandlerProvider} of this {@link AbstractPropertiesEditingProvider}.
	 */
	protected ViewHandlerProvider initViewHandlerProvider() {
		return null;
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
