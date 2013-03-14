/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.internal.view.impl;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.ui.fx.view.FXPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.FXPropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.undefined.editor.UndefinedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.internal.view.impl.AbstractPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorProvider;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class FXImplPropertiesEditingView extends AbstractPropertiesEditingView<Pane> implements FXPropertiesEditingView {
	
	/**
	 * @param editingComponent
	 * @param viewDescriptor
	 */
	public FXImplPropertiesEditingView(PropertiesEditingComponent editingComponent, View viewDescriptor) {
		super(editingComponent, viewDescriptor);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.fx.view.FXPropertiesEditingView#createContents(javafx.scene.layout.BorderPane)
	 */
	public void createContents(Pane container) {
		contentsComposite = new GridPane();
		if (container instanceof BorderPane) {
			((BorderPane) container).setCenter(contentsComposite);
		} else {
			container.getChildren().add(contentsComposite);
		}
		for (EObject content : viewDescriptor.eContents()) {
			buildElement(contentsComposite, content);
		}
	}

	private void buildElement(Pane currentContainer, EObject content) {
		if (content instanceof ElementEditor) {
			ElementEditor elementEditor = (ElementEditor) content;
			PropertyEditorContext<Pane> editorContext = new PropertyEditorContext<Pane>(this, elementEditor);
			ToolkitPropertyEditorProvider<Pane> propertyEditorProvider = serviceRegistry.getService(ToolkitPropertyEditorProvider.class, editorContext);
			if (propertyEditorProvider != null) {
				PropertyEditor propertyEditor = propertyEditorProvider.getPropertyEditor(editorContext);
				if (propertyEditor.getPropertyEditorViewer() instanceof FXPropertyEditorViewer) {
					((FXPropertyEditorViewer<?>)propertyEditor.getPropertyEditorViewer()).build(currentContainer);
					this.propertyEditors.put(elementEditor, propertyEditor);
				}
			}
		} else if (content instanceof Container) {
			Container container = (Container) content;
			PropertyEditorContext<Pane> editorContext = new PropertyEditorContext<Pane>(this, container);
			ToolkitPropertyEditorProvider<Pane> propertyEditorProvider = serviceRegistry.getService(ToolkitPropertyEditorProvider.class, editorContext);
			if (propertyEditorProvider != null) {
				PropertyEditor propertyEditor = propertyEditorProvider.getPropertyEditor(editorContext);
				if (propertyEditor.getPropertyEditorViewer() instanceof FXPropertyEditorViewer) {
					((FXPropertyEditorViewer<?>)propertyEditor.getPropertyEditorViewer()).build(currentContainer);
					this.propertyEditors.put(container, propertyEditor);
					if (!(propertyEditor instanceof UndefinedPropertyEditor)) {
						for (EObject subContent : content.eContents()) {
							buildElement((Pane)(propertyEditor.getPropertyEditorViewer().getViewer()), subContent);
						}
					}
				}
			}
		}
	}

}
