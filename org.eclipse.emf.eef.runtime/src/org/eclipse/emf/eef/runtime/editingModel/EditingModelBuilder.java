/**
 * 
 */
package org.eclipse.emf.eef.runtime.editingModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
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
			EClassBinding binding = bindingBuilder.buildBinding();
			if (binding != null) {
				result.getBindings().add(binding);
			}
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
		private List<PropertyBindingBuilder> propertyBuilders;
		private List<EClassViewBindingBuilder> viewBuilders;

		private EClassBindingBuilder(EditingModelBuilder parentBuilder, EClass eClass) {
			this.parentBuilder = parentBuilder;
			this.propertyBuilders = new ArrayList<EditingModelBuilder.PropertyBindingBuilder>();
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
		private EClassBinding buildBinding() {
			if (viewBuilders.size() > 0) {
				EClassBinding eClassBinding = EditingModelFactory.eINSTANCE.createEClassBinding();
				eClassBinding.setEClass(eClass);
				for (PropertyBindingBuilder propertyBuilder : propertyBuilders) {
					eClassBinding.getPropertyBindings().add(propertyBuilder.buildPropertyBinding());
				}
				for (EClassViewBindingBuilder viewBuilder : viewBuilders)  {
					eClassBinding.getViews().add(viewBuilder.buildView());
				}
				return eClassBinding;
			} else {
				return null;
			}
		}

		/**
		 * @param eClass
		 * @return
		 */
		public EClassBindingBuilder bindClass(EClass eClass) {
			return parentBuilder.bindClass(eClass);
		}
		
		/**
		 * @param feature
		 * @param editor
		 * @return
		 */
		public PropertyBindingBuilder bindProperty(EStructuralFeature feature, Object editor) {
			PropertyBindingBuilder propertyBindingBuilder = new PropertyBindingBuilder(this, feature, editor);
			propertyBuilders.add(propertyBindingBuilder);
			return propertyBindingBuilder;
		}

		/**
		 * Build the {@link PropertiesEditingModel}.
		 * @return the {@link PropertiesEditingModel}.
		 */
		public PropertiesEditingModel build() {
			return parentBuilder.build();
		}

	}
	
	public class PropertyBindingBuilder {
		
		private EClassBindingBuilder parentBuilder;
		private EStructuralFeature feature;
		private Object editor;
		
		/**
		 * @param feature
		 * @param editor
		 */
		private PropertyBindingBuilder(EClassBindingBuilder parentBuilder, EStructuralFeature feature, Object editor) {
			this.parentBuilder = parentBuilder;
			this.feature = feature;
			this.editor = editor;
		}
		
		/**
		 * @param eClass
		 * @return
		 */
		public EClassBindingBuilder bindClass(EClass eClass) {
			return parentBuilder.bindClass(eClass);
		}
		
		/**
		 * @param feature
		 * @param editor
		 * @return
		 */
		public PropertyBindingBuilder bindProperty(EStructuralFeature feature, Object editor) {
			return parentBuilder.bindProperty(feature, editor);
		}
		
		/**
		 * @return
		 */
		private PropertyBinding buildPropertyBinding() {
			PropertyBinding propertyBinding = EditingModelFactory.eINSTANCE.createPropertyBinding();
			propertyBinding.setFeature(feature);
			propertyBinding.setEditor(editor);
			return propertyBinding;
		}
		
		/**
		 * @return
		 */
		public PropertiesEditingModel build() {
			return parentBuilder.build();
		}

		/**
		 * @param view
		 * @return 
		 * @return
		 */
		public EClassViewBindingBuilder withView(Object view) {
			return parentBuilder.withView(view);
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

		private View buildView() {
			JavaView javaView = EditingModelFactory.eINSTANCE.createJavaView();
			javaView.setDefinition(view);
			if (handler != null) {
				javaView.setHandler(handler);
			}
			return javaView;
		}
		
		/**
		 * @param view
		 * @return
		 */
		public EClassViewBindingBuilder withView(Object view) {
			return parentBuilder.withView(view);
		}

		/**
		 * @param eClass
		 * @return
		 */
		public EClassBindingBuilder bindClass(EClass eClass) {
			return parentBuilder.bindClass(eClass);
		}

		/**
		 * @return
		 */
		public PropertiesEditingModel build() {
			return parentBuilder.build();
		}

		/**
		 * @param feature
		 * @param editor
		 * @return
		 */
		public PropertyBindingBuilder bindProperty(EStructuralFeature feature, Object editor) {
			return parentBuilder.bindProperty(feature, editor);
		}

	}
}
