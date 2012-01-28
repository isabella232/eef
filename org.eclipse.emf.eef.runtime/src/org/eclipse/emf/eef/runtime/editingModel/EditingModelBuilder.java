/**
 * 
 */
package org.eclipse.emf.eef.runtime.editingModel;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingModelBuilder {

	private Collection<EClassBindingBuilder> bindingBuilders;
	
	public EditingModelBuilder() {
		this.bindingBuilders = new ArrayList<EClassBindingBuilder>();
	}

	public PropertiesEditingModel build() {
		PropertiesEditingModel result = EditingModelFactory.eINSTANCE.createPropertiesEditingModel();
		for (EClassBindingBuilder bindingBuilder : bindingBuilders) {
			result.getBindings().add(bindingBuilder.buildBinding());
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
	
	public class EClassBindingBuilder {
		
		EClass eClass;
		Object view;
		ViewHandler<?> handler;
		
		private EditingModelBuilder parentBuilder;
		
		public EClassBindingBuilder(EditingModelBuilder parentBuilder) {
			this.parentBuilder = parentBuilder;
		}

		/**
		 * @param handler the handler to set
		 * @return 
		 */
		public EClassBindingBuilder setHandler(ViewHandler<?> handler) {
			this.handler = handler;
			return this;
		}
		
		/**
		 * @param eClass
		 * @param view
		 * @return
		 */
		public EClassBindingBuilder bindClass(EClass eClass, Object view) {
			return parentBuilder.bindClass(eClass, view);
		}
		
		/**
		 * Build the EClassBinding
		 * @return a {@link EClassBinding}.
		 */
		public EClassBinding buildBinding() {
			EClassBinding eClassBinding = EditingModelFactory.eINSTANCE.createEClassBinding();
			eClassBinding.setEClass(eClass);
			eClassBinding.setView(view);
			if (handler != null) {
				eClassBinding.setHandler(handler);
			}
			return eClassBinding;
		}
		
		/**
		 * Build the {@link PropertiesEditingModel}.
		 * @return the {@link PropertiesEditingModel}.
		 */
		public PropertiesEditingModel build() {
			return parentBuilder.build();
		}
	}
}
