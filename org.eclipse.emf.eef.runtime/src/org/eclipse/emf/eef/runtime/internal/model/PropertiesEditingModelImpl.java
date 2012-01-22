/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.model;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.internal.binding.PropertiesEditingComponentImpl;
import org.eclipse.emf.eef.runtime.model.EClassBinding;
import org.eclipse.emf.eef.runtime.model.PropertiesEditingModel;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingModelImpl extends AdapterFactoryImpl implements PropertiesEditingModel {

	private Collection<EClassBinding> bindings;

	public PropertiesEditingModelImpl() {
		this.bindings = new ArrayList<EClassBinding>();
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
		return Collections2.filter(bindings, Predicates.compose(
				new Predicate<EClass>() {
					/**
					 * {@inheritDoc}
					 * @see com.google.common.base.Predicate#apply(java.lang.Object)
					 */
					public boolean apply(EClass input) {
						return type == input;
					}
					
				}, 
				new Function<EClassBinding, EClass>() {
					/**
					 * {@inheritDoc}
					 * @see com.google.common.base.Function#apply(java.lang.Object)
					 */
					public EClass apply(EClassBinding input) {
						return input.getEClass();
					}
			})).size() > 0;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.model.PropertiesEditingModel#addBinding(org.eclipse.emf.eef.runtime.model.EClassBinding)
	 */
	public void addBinding(EClassBinding binding) {
		bindings.add(binding);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.model.PropertiesEditingModel#removeBinding(org.eclipse.emf.eef.runtime.model.EClassBinding)
	 */
	public void removeBinding(EClassBinding binding) {
		bindings.remove(binding);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.model.PropertiesEditingModel#getAssociatedView(org.eclipse.emf.ecore.EObject)
	 */
	public Object getAssociatedView(EObject eObject) {
		for (EClassBinding binding : bindings) {
			if (eObject.eClass().equals(binding.getEClass())) {
				return binding.getView();
			}
		}
		return null;
	}
	
}
