/**
 * 
 */
package org.eclipse.emf.eef.runtime.editingModel;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingModelBuilder {

	private String id;
	private Collection<EClassBindingBuilder> bindingBuilders;
	private FeatureDocumentationProvider documentationProvider;
	private Collection<EObject> involvedModels;

	public EditingModelBuilder(String id) {
		this.id = id;
		this.bindingBuilders = Lists.newArrayList();
		this.documentationProvider = null;
		this.involvedModels = Lists.newArrayList();
	}

	public PropertiesEditingModel build() {
		PropertiesEditingModel result = EditingModelFactory.eINSTANCE.createPropertiesEditingModel();
		result.setId(id);
		for (EClassBindingBuilder bindingBuilder : bindingBuilders) {
			EClassBinding binding = bindingBuilder.buildBinding();
			if (binding != null) {
				result.getBindings().add(binding);
			}
		}
		if (documentationProvider != null && documentationProvider == FeatureDocumentationProvider.ECORE_DOCUMENTATION) {
			EditingOptions options = EditingModelFactory.eINSTANCE.createEditingOptions();
			options.setFeatureDocumentationProvider(documentationProvider);
			result.setOptions(options);
		}
		for (EObject model : involvedModels) {
			result.getInvolvedModels().add(model);
		}
		return result;
	}

	public EClassBindingBuilder bindClass(EClass eClass) {
		EClassBindingBuilder builder = new EClassBindingBuilder(this, eClass);
		bindingBuilders.add(builder);
		return builder;
	}
	
	public EditingModelBuilder setDocumentationProvider(FeatureDocumentationProvider provider) {
		this.documentationProvider = provider;
		return this;
	}
	
	public EditingModelBuilder addInvolvedModel(EObject model) {
		this.involvedModels.add(model);
		return this;
	}

	public class EClassBindingBuilder {

		private EClass eClass;		
		private EditingModelBuilder parentBuilder;
		private List<PropertyBindingBuilder> propertyBuilders;
		private List<EClassViewBindingBuilder> viewBuilders;

		private EClassBindingBuilder(EditingModelBuilder parentBuilder, EClass eClass) {
			this.parentBuilder = parentBuilder;
			this.propertyBuilders = Lists.newArrayList();
			this.viewBuilders = Lists.newArrayList();
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
		public PropertyBindingBuilder bindProperty(EStructuralFeature feature) {
			PropertyBindingBuilder propertyBindingBuilder = new PropertyBindingBuilder(this, feature);
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
		private PropertyEditorBindingBuilder editorBuilder;
		
		/**
		 * @param feature
		 * @param editor
		 */
		private PropertyBindingBuilder(EClassBindingBuilder parentBuilder, EStructuralFeature feature) {
			this.parentBuilder = parentBuilder;
			this.feature = feature;
		}
		
		/**
		 * @param editor
		 * @return
		 */
		public PropertyEditorBindingBuilder withEditor(Object editor) {
			editorBuilder = new PropertyEditorBindingBuilder(this, editor);
			return editorBuilder;
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
		public PropertyBindingBuilder bindProperty(EStructuralFeature feature) {
			return parentBuilder.bindProperty(feature);
		}
		
		/**
		 * @return
		 */
		private PropertyBinding buildPropertyBinding() {
			PropertyBinding propertyBinding = EditingModelFactory.eINSTANCE.createPropertyBinding();
			propertyBinding.setFeature(feature);
			if (editorBuilder != null) {
				propertyBinding.setEditor(editorBuilder.buildEditor());
			}// else should throw an exception
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
			View modelView = null;
			if (view instanceof EObject) {
				modelView = EditingModelFactory.eINSTANCE.createEObjectView();
				((EObjectView) modelView).setDefinition((EObject) view);
			} else  {
				modelView = EditingModelFactory.eINSTANCE.createJavaView();
				((JavaView) modelView).setDefinition(view);				
			}
			if (view != null && handler != null) {
				modelView.setHandler(handler);
			}
			return modelView;
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
		public PropertyBindingBuilder bindProperty(EStructuralFeature feature) {
			return parentBuilder.bindProperty(feature);
		}

	}
	
	public class PropertyEditorBindingBuilder {
		
		private PropertyBindingBuilder parentBuilder;
		private Object editor;
		
		
		public PropertyEditorBindingBuilder(PropertyBindingBuilder parentBuilder, Object editor) {
			this.parentBuilder = parentBuilder;
			this.editor = editor;
		}
		
		private Editor buildEditor() {
			Editor buildedEditor = null;
			if (editor instanceof EObject) {
				buildedEditor = EditingModelFactory.eINSTANCE.createEObjectEditor();
				((EObjectEditor) buildedEditor).setDefinition((EObject) editor);
			} else {
				buildedEditor = EditingModelFactory.eINSTANCE.createJavaEditor();
				((JavaEditor) buildedEditor).setDefinition(editor);
			}
			return buildedEditor;
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
		 * @param feature
		 * @param editor
		 * @return
		 */
		public PropertyBindingBuilder bindProperty(EStructuralFeature feature) {
			return parentBuilder.bindProperty(feature);
		}
 
		/**
		 * @return
		 */
		public PropertiesEditingModel build() {
			return parentBuilder.build();
		}

	}
}
