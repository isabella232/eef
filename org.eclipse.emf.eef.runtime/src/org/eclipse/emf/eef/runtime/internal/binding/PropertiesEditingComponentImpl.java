/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.model.PropertiesEditingModel;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingComponentImpl extends AdapterImpl implements PropertiesEditingComponent {

	private PropertiesEditingModel editingModel;
	
	/**
	 * @param editingModel
	 */
	public PropertiesEditingComponentImpl(PropertiesEditingModel editingModel) {
		this.editingModel = editingModel;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
	 */
	@Override
	public boolean isAdapterForType(Object type) {
		return type == PropertiesEditingComponent.class;
	}
	
}
