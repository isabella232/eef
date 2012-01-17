/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.model.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.eef.runtime.model.EClassBinding;
import org.eclipse.emf.eef.runtime.model.PropertiesEditingModel;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingModelImpl implements PropertiesEditingModel {

	private Collection<EClassBinding> bindings;

	public PropertiesEditingModelImpl() {
		this.bindings = new ArrayList<EClassBinding>();
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
	
	
	
}
