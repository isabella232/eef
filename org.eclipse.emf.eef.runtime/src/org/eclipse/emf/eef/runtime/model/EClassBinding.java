/**
 * 
 */
package org.eclipse.emf.eef.runtime.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.eef.runtime.internal.model.EClassBindingImpl;
import org.eclipse.emf.eef.runtime.model.PropertiesEditingModel.Builder;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;

/**
 * @author glefur
 *
 */
public interface EClassBinding {

	/**
	 * Binded EClass.
	 * @return the binded {@link EClass}.
	 */
	EClass getEClass();

	/**
	 * View of the binding.
	 * @return the view of the binding.
	 */
	Object getView();
	
	
	/**
	 * Handler of the view.
	 * @return {@link ViewHandler} to use for the view.
	 */
	ViewHandler<?> getHandler();

	/**
	 * Sets a Handler for the view.
	 * @param handler {@link ViewHandler} to set.
	 */
	void setHandler(ViewHandler<?> handler);
	
	
	public class EClassBindingBuilder {
		
		EClass eClass;
		Object view;
		ViewHandler<?> handler;
		
		private Builder parentBuilder;
		
		public EClassBindingBuilder(Builder parentBuilder) {
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
			EClassBinding eClassBinding = new EClassBindingImpl(eClass, view);
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
