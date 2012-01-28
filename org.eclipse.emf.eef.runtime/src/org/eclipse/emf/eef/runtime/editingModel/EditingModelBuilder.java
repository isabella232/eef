/**
 * 
 */
package org.eclipse.emf.eef.runtime.editingModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
			result.getBindings().addAll(bindingBuilder.buildBinding());
		}
		return result;
	}
	
	public EClassBindingBuilder bindClass(EClass eClass) {
		EClassBindingBuilder builder = new EClassBindingBuilder(this, eClass);
		bindingBuilders.add(builder);
		return builder;
	}
	
	public class EClassBindingBuilder {
		
		private EClass eClass;		
		private EditingModelBuilder parentBuilder;
		private List<EClassViewBindingBuilder> viewBuilders;
		
		private EClassBindingBuilder(EditingModelBuilder parentBuilder, EClass eClass) {
			this.parentBuilder = parentBuilder;
			this.viewBuilders = new ArrayList<EClassViewBindingBuilder>();
			this.eClass = eClass;
		}
		
		/**
		 * @param view
		 * @return
		 */
		public EClassViewBindingBuilder withView(Object view) {
			EClassViewBindingBuilder builder = new EClassViewBindingBuilder(this, view);
			viewBuilders.add(builder);
			return builder;
		}
		
		/**
		 * Build the EClassBinding
		 * @return a {@link EClassBinding}.
		 */
		private Collection<EClassBinding> buildBinding() {
			Collection<EClassBinding> result = new ArrayList<EClassBinding>();
			for (EClassViewBindingBuilder viewBuilder : viewBuilders)  {
				EClassBinding eClassBinding = EditingModelFactory.eINSTANCE.createEClassBinding();
				eClassBinding.setEClass(eClass);
				eClassBinding.setView(viewBuilder.view);
				if (viewBuilder.handler != null) {
					eClassBinding.setHandler(viewBuilder.handler);
				}
				result.add(eClassBinding);
			}
			return result;
		}
		
		/**
		 * @param eClass
		 * @return
		 */
		public EClassBindingBuilder bindClass(EClass eClass) {
			return parentBuilder.bindClass(eClass);
		}
		
		/**
		 * Build the {@link PropertiesEditingModel}.
		 * @return the {@link PropertiesEditingModel}.
		 */
		public PropertiesEditingModel build() {
			return parentBuilder.build();
		}

	}
	
	public class EClassViewBindingBuilder {

		private EClassBindingBuilder parentBuilder;
		private Object view;
		private ViewHandler<?> handler;

		/**
		 * @param parentBuilder
		 * @param view
		 */
		private EClassViewBindingBuilder(EClassBindingBuilder parentBuilder, Object view) {
			this.parentBuilder = parentBuilder;
			this.view = view;
		}

		/**
		 * @param handler the handler to set
		 * @return 
		 */
		public EClassViewBindingBuilder handler(ViewHandler<?> handler) {
			this.handler = handler;
			return this;
		}
		
		public EClassBindingBuilder bindClass(EClass eClass) {
			return parentBuilder.bindClass(eClass);
		}
		
		/**
		 * @return
		 */
		public PropertiesEditingModel build() {
			return parentBuilder.build();
		}
		
	}
}
