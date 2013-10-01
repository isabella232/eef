 /**
  * 
  */
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.ui.swt.e3.tabbed.view.section.SectionPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.swt.e3.view.FormPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.FormPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.SWTPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.undefined.editor.UndefinedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.AbstractPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkit;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class FormImplPropertiesEditingView extends AbstractPropertiesEditingView<Composite> implements FormPropertiesEditingView {

	/**
	 * Non-parameterized constructor for {@link SectionPropertiesEditingView} purpose.
	 * Mustn't be use otherwise.
	 */
	public FormImplPropertiesEditingView() { }

	/**
	 * @param editingComponent
	 * @param viewDescriptor
	 */
	public FormImplPropertiesEditingView(PropertiesEditingComponent editingComponent, View viewDescriptor) {
		super(editingComponent, viewDescriptor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e3.view.FormPropertiesEditingView#createContents(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 */
	public void createContents(FormToolkit toolkit, Composite composite) {
		contentsComposite = toolkit.createComposite(composite);
		contentsComposite.setLayout(new GridLayout(3, false));
		EClassBinding binding = editingComponent.getBinding();
		boolean autowire = editingComponent.getEditingContext().getOptions().autowire();
		for (EObject content : viewDescriptor.eContents()) {
			if (binding.feature(content, autowire) != null) {
				buildElement(toolkit, contentsComposite, binding.propertyBinding(content, autowire), content);
			}
		}
	}

	private void buildElement(FormToolkit toolkit, Composite currentContainer, PropertyBinding propertyBinding, EObject content) {
		if (content instanceof ElementEditor) {
			ElementEditor elementEditor = (ElementEditor) content;
			PropertyEditorContext editorContext = new PropertyEditorContext(this, propertyBinding, elementEditor);
			EEFToolkit<Composite> propertyEditorProvider = eefToolkitProvider.getToolkit(editorContext);
			if (propertyEditorProvider != null) {
				PropertyEditor propertyEditor = propertyEditorProvider.getPropertyEditor(editorContext);
				if (propertyEditor.getPropertyEditorViewer() instanceof FormPropertyEditor) {
					((FormPropertyEditor<?>)propertyEditor.getPropertyEditorViewer()).build(toolkit, currentContainer);
				} else if (propertyEditor.getPropertyEditorViewer() instanceof SWTPropertyEditor) {
					((SWTPropertyEditor<?>)propertyEditor.getPropertyEditorViewer()).build(currentContainer);					
				}
				this.propertyEditors.put(elementEditor, propertyEditor);
			}
		} else if (content instanceof Container) {
			Container container = (Container) content;
			PropertyEditorContext editorContext = new PropertyEditorContext(this, propertyBinding, container);
			EEFToolkit<Composite> propertyEditorProvider = eefToolkitProvider.getToolkit(editorContext);
			if (propertyEditorProvider != null) {
				PropertyEditor propertyEditor = propertyEditorProvider.getPropertyEditor(editorContext);
				if (propertyEditor.getPropertyEditorViewer() instanceof FormPropertyEditor) {
					((FormPropertyEditor<?>)propertyEditor.getPropertyEditorViewer()).build(toolkit, currentContainer);
				} else if (propertyEditor.getPropertyEditorViewer() instanceof SWTPropertyEditor) {
					((SWTPropertyEditor<?>)propertyEditor.getPropertyEditorViewer()).build(currentContainer);					
				}
				this.propertyEditors.put(container, propertyEditor);
				if (!(propertyEditor instanceof UndefinedPropertyEditor)) {
					for (EObject subContent : content.eContents()) {
						EClassBinding binding = editingComponent.getBinding();
						boolean autowire = editingComponent.getEditingContext().getOptions().autowire();
						Composite viewerControl = (Composite) ((Viewer) propertyEditor.getPropertyEditorViewer().getViewer()).getControl();
						if ((Viewer) propertyEditor.getPropertyEditorViewer().getViewer() instanceof Viewer) {
							if (binding.feature(content, autowire) != null) {
								buildElement(toolkit, viewerControl, binding.propertyBinding(subContent, autowire), subContent);
							}
						}
					}
				}
			}
		}
	}
	
}
