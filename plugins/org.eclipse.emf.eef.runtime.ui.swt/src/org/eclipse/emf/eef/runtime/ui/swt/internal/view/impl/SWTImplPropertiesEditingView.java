/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.impl;


import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext;
import org.eclipse.emf.eef.runtime.ui.swt.view.SWTPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.SWTPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.undefined.editor.UndefinedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.AbstractPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorProvider;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SWTImplPropertiesEditingView extends AbstractPropertiesEditingView<Composite> implements SWTPropertiesEditingView {

	/**
	 * @param editingComponent
	 * @param viewDescriptor
	 */
	public SWTImplPropertiesEditingView(PropertiesEditingComponent editingComponent, View viewDescriptor) {
		super(editingComponent, viewDescriptor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.SWTPropertiesEditingView#createContents(org.eclipse.swt.widgets.Composite)
	 */
	public void createContents(Composite composite) {
		contentsComposite = new Composite(composite, SWT.NONE);
		contentsComposite.setLayout(new GridLayout(3, false));
		for (EObject content : viewDescriptor.eContents()) {
			buildElement(contentsComposite, content);
		}
	}

	private void buildElement(Composite currentContainer, EObject content) {
		if (content instanceof ElementEditor) {
			ElementEditor elementEditor = (ElementEditor) content;
			PropertyEditorContext<Composite> editorContext = new PropertyEditorContext<Composite>(this, elementEditor);
			ToolkitPropertyEditorProvider<Composite> propertyEditorProvider = serviceRegistry.getService(ToolkitPropertyEditorProvider.class, editorContext);
			if (propertyEditorProvider != null) {
				PropertyEditor propertyEditor = propertyEditorProvider.getPropertyEditor(editorContext);
				if (propertyEditor.getPropertyEditorViewer() instanceof SWTPropertyEditor) {
					((SWTPropertyEditor<?>)propertyEditor.getPropertyEditorViewer()).build(currentContainer);
					this.propertyEditors.put(elementEditor, propertyEditor);
				}
			}
		} else if (content instanceof Container) {
			Container container = (Container) content;
			PropertyEditorContext<Composite> editorContext = new PropertyEditorContext<Composite>(this, container);
			ToolkitPropertyEditorProvider<Composite> propertyEditorProvider = serviceRegistry.getService(ToolkitPropertyEditorProvider.class, editorContext);
			if (propertyEditorProvider != null) {
				PropertyEditor propertyEditor = propertyEditorProvider.getPropertyEditor(editorContext);
				if (propertyEditor.getPropertyEditorViewer() instanceof SWTPropertyEditor) {
					((SWTPropertyEditor<?>)propertyEditor.getPropertyEditorViewer()).build(currentContainer);
					this.propertyEditors.put(container, propertyEditor);
					if (!(propertyEditor instanceof UndefinedPropertyEditor)) {
						for (EObject subContent : content.eContents()) {
							buildElement((Composite)((Viewer) propertyEditor.getPropertyEditorViewer().getViewer()).getControl(), subContent);
						}
					}
				}
			}
		}
	}
	
}
