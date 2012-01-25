/**
 * 
 */
package org.eclipse.emf.eef.runtime.model;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.internal.model.PropertiesEditingModelImpl;
import org.eclipse.emf.eef.runtime.model.EClassBinding.EClassBindingBuilder;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingModel extends AdapterFactory {

	/**
	 * Add a new binding in the model.
	 * @param binding {@link EClassBinding} to add.
	 */
	void addBinding(EClassBinding binding);
	
	/**
	 * Remove a binding of the model.
	 * @param binding {@link EClassBinding} to remove.
	 */
	void removeBinding(EClassBinding binding);
	

	/**
	 * Returns the view associated to the given {@link EObject}.
	 * @param eObject {@link EObject} to edit.
	 * @return the associated view.
	 */
	Object getAssociatedView(EObject eObject);
	

	public class Builder {
		
		private Collection<EClassBindingBuilder> bindingBuilders;
		
		public Builder() {
			this.bindingBuilders = new ArrayList<EClassBindingBuilder>();
		}

		public PropertiesEditingModel build() {
			PropertiesEditingModel result = new PropertiesEditingModelImpl();
			for (EClassBindingBuilder bindingBuilder : bindingBuilders) {
				result.addBinding(bindingBuilder.buildBinding());
			}
			return result;
		}
		
		public EClassBindingBuilder bindClass(EClass eClass, Object view) {
			EClassBindingBuilder builder = new EClassBindingBuilder(this);
			builder.eClass = eClass;
			builder.view = view;
			bindingBuilders.add(builder);
			return builder;
		}
				
	}
}
