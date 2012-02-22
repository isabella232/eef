/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl;

import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.FormPropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class StandardFormPropertyEditor<VIEWER extends Viewer> implements FormPropertyEditor<VIEWER> {

	protected PropertiesEditingView view;
	protected ElementEditor elementEditor;

	/**
	 * @param view {@link PropertiesEditingView} where the PropertyEditor is built.
	 * @param elementEditor {@link ElementEditor} specifying the Property Editor.
	 */
	public StandardFormPropertyEditor(PropertiesEditingView view, ElementEditor elementEditor) {
		this.view = view;
		this.elementEditor = elementEditor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.FormPropertyEditor#build(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 */
	public void build(FormToolkit toolkit, Composite parent) {
		view.getViewHelper().createLabel(parent, elementEditor, elementEditor.getName());
		createEditorContents(toolkit, parent);
		view.getViewHelper().createHelpButton(parent, elementEditor);
	}

	/**
	 * Create the contents of the property editor in the owning Composite.
	 * @param toolkit {@link FormToolkit} to use to build the control.
	 * @param parent the owning {@link Composite}.
	 */
	protected abstract void createEditorContents(FormToolkit toolkit, Composite parent);
}
