/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.view.impl;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.ui.view.SWTPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.SWTPropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SWTImplPropertiesEditingView extends AbstractPropertiesEditingView implements SWTPropertiesEditingView {

	/**
	 * @param editingComponent
	 * @param viewDescriptor
	 */
	public SWTImplPropertiesEditingView(PropertiesEditingComponent editingComponent, View viewDescriptor) {
		super(editingComponent, viewDescriptor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.SWTPropertiesEditingView#createContents(org.eclipse.swt.widgets.Composite)
	 */
	public void createContents(Composite composite) {
		contentsComposite = new Composite(composite, SWT.NONE);
		contentsComposite.setLayout(new GridLayout(3, false));
		if (propertyEditorProvider != null) {
			TreeIterator<EObject> eAllContents = viewDescriptor.eAllContents();
			while (eAllContents.hasNext()) {
				EObject next = eAllContents.next();
				if (next instanceof ElementEditor) {
					ElementEditor elementEditor = (ElementEditor) next;
					if (propertyEditorProvider.canHandle(this, elementEditor)) {
						PropertyEditor propertyEditor = propertyEditorProvider.getPropertyEditor(this, elementEditor);
						if (propertyEditor.getPropertyEditorViewer() instanceof SWTPropertyEditor) {
							((SWTPropertyEditor<?>)propertyEditor.getPropertyEditorViewer()).build(contentsComposite);
							this.propertyEditors.put(elementEditor, propertyEditor);
						}
					}
				}
			}
		}
	}
	
}
