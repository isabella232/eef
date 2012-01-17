/**
 * 
 */
package org.eclipse.emf.eef.runtime.model;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.eef.runtime.internal.model.impl.EClassBindingImpl;
import org.eclipse.emf.eef.runtime.internal.model.impl.PropertiesEditingModelImpl;

/**
 * @author glefur
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
	
	public class Builder {
		
		private Collection<ComponentSettings> settings;
		
		public Builder() {
			this.settings = new ArrayList<PropertiesEditingModel.Builder.ComponentSettings>();
		}

		public PropertiesEditingModel build() {
			PropertiesEditingModel result = new PropertiesEditingModelImpl();
			for (ComponentSettings cSettings : settings) {
				result.addBinding(new EClassBindingImpl(cSettings.eClass, cSettings.view));
			}
			return result;
		}
		
		public Builder bind(EClass eClass, Object view) {
			ComponentSettings newSettings = new ComponentSettings();
			newSettings.eClass = eClass;
			newSettings.view = view;
			settings.add(newSettings);
			return this;
		}
		

		private class ComponentSettings {
			
			EClass eClass;
			Object view;
			
		}
		
	}
	
	
}
