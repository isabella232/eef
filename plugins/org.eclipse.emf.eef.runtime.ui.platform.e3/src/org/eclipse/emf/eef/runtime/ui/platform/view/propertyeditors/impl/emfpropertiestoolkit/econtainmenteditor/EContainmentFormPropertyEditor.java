/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.emfpropertiestoolkit.econtainmenteditor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.emf.eef.runtime.ui.platform.services.view.PlatformAwareViewService;
import org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.FormPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.platform.widgets.FormEReferenceEditor;
import org.eclipse.emf.eef.runtime.ui.services.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EContainmentFormPropertyEditor implements FormPropertyEditor<EReferenceEditor> {

	protected PropertiesEditingView view;
	protected ElementEditor elementEditor;
	private EReferenceEditor eReferenceEditor;

	/**
	 * @param view
	 * @param elementEditor
	 */
	public EContainmentFormPropertyEditor(PropertiesEditingView view, ElementEditor elementEditor) {
		this.view = view;
		this.elementEditor = elementEditor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public EReferenceEditor getViewer() {
		return eReferenceEditor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.FormPropertyEditor#build(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 */
	public void build(final FormToolkit toolkit, final Composite parent) {
		final ViewService viewService = view.getViewService();
		if (viewService instanceof PlatformAwareViewService) {
			((PlatformAwareViewService)viewService).createLabel(toolkit, parent, elementEditor, elementEditor.getName());
		} else {
			viewService.createLabel(parent, elementEditor, elementEditor.getName());
		}
		eReferenceEditor = new FormEReferenceEditor(toolkit, parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL) {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor#buildAdditionnalActionControls(org.eclipse.swt.widgets.Composite)
			 */
			@Override
			protected void buildAdditionnalActionControls(Composite parent) {
				if (viewService instanceof PlatformAwareViewService) {
					((PlatformAwareViewService)viewService).createHelpButton(toolkit, parent, elementEditor);
				} else {
					viewService.createHelpButton(parent, elementEditor);
				}
			}
			
		};
		for (EObject subEditor : elementEditor.eContents()) {
			if (subEditor instanceof ElementEditor) {
				eReferenceEditor.addColumn(((ElementEditor) subEditor).getName(), UIConstants.DEFAULT_COLUMN_WIDTH);
			}
		}
		ImageManager imageManager = view.getEditingComponent().getEditingContext().getServiceRegistry().getService(ImageManager.class, this);
		eReferenceEditor.setImageManager(imageManager);
		eReferenceEditor.createContents();
		toolkit.adapt((Composite) eReferenceEditor.getControl());
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = view.getViewSettings().getMultiEditorHeight();
		layoutData.horizontalSpan = 2;
		eReferenceEditor.setLayoutData(layoutData);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#lock()
	 */
	public void lock() {
		eReferenceEditor.setLocked(true);		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#unlock()
	 */
	public void unlock() {
		eReferenceEditor.setLocked(true);		
	}


}
