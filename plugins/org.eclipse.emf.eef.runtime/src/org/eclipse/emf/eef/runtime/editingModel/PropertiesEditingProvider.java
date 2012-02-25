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
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.internal.binding.PropertiesEditingComponentImpl;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingProvider extends AdapterFactoryImpl implements AdapterFactory {

	private List<PropertiesEditingModel> editingModels;

	/**
	 * Default constructor.
	 */
	public PropertiesEditingProvider() {
	}

	/**
	 * Returns the EditingModel describing the editing forms to edit the given object.
	 * @param eObject the {@link EObject} to edit.
	 * @return the {@link PropertiesEditingModel} to use for edit the given EObject.
	 */
	public PropertiesEditingModel getEditingModel(EObject eObject) {
		for (PropertiesEditingModel editingModel : getEditingModels()) {
			if (editingModel.binding(eObject) != null) {
				return editingModel;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.common.notify.impl.AdapterFactoryImpl#createAdapter(org.eclipse.emf.common.notify.Notifier, java.lang.Object)
	 */
	protected Adapter createAdapter(Notifier target, Object type) {
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
							return type == input;
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
	 * @return the specific {@link PropertiesEditingModel}s to use from this {@link PropertiesEditingProvider}.
	 */
	protected Collection<? extends PropertiesEditingModel> initSpecificEditingModel() {
		return Collections.emptyList();
	}

	/**
	 * Compute the list of available editingModel in this context.
	 * @return a list of {@link PropertiesEditingModel} available from this {@link PropertiesEditingProvider}.
	 */
	private List<PropertiesEditingModel> getEditingModels() {
		if (editingModels == null) {
			editingModels = Lists.newArrayList();
			editingModels.addAll(initSpecificEditingModel());
			// Init here the default editingModels from the registry.
		}
		return editingModels;
	}

}
