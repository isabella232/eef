/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit.ereferenceeditor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.widgets.FormEReferenceEditor;
import org.eclipse.emf.eef.runtime.ui.swt.e3.util.PlatformAwareViewService;
import org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.FormPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.util.ViewService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferenceFormPropertyEditor implements FormPropertyEditor<MultiLinePropertyViewer> {

	private ImageManager imageManager;

	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	private MultiLinePropertyViewer multiLinePropertyViewer;

	/**
	 * @param imageManager
	 * @param view
	 * @param elementEditor
	 */
	public EReferenceFormPropertyEditor(ImageManager imageManager, PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		this.imageManager = imageManager;
		this.view = view;
		this.elementEditor = elementEditor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public MultiLinePropertyViewer getViewer() {
		return multiLinePropertyViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.FormPropertyEditor#build(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 */
	public void build(final FormToolkit toolkit, final Composite parent) {
		final ViewService viewService = view.getViewService();
		if (viewService instanceof PlatformAwareViewService) {
			((PlatformAwareViewService)viewService).createLabel(toolkit, parent, elementEditor, elementEditor.getName());
		} else if (viewService instanceof SWTViewService){
			((SWTViewService) viewService).createLabel(parent, elementEditor, elementEditor.getName());			
		}
		multiLinePropertyViewer = new FormEReferenceEditor(toolkit, parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL) {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.MultiLinePropertyViewer#buildAdditionnalActionControls(org.eclipse.swt.widgets.Composite)
			 */
			@Override
			protected void buildAdditionnalActionControls(Composite parent) {
				if (viewService instanceof PlatformAwareViewService) {
					((PlatformAwareViewService)viewService).createHelpButton(toolkit, parent, elementEditor);
				}else if (viewService instanceof SWTViewService){
					((SWTViewService) viewService).createHelpButton(parent, elementEditor);
				}
			}
			
		};
		for (EObject subEditor : elementEditor.eContents()) {
			if (subEditor instanceof ElementEditor) {
				multiLinePropertyViewer.addColumn(((ElementEditor) subEditor).getName(), UIConstants.DEFAULT_COLUMN_WIDTH);
			}
		}
		multiLinePropertyViewer.setImageManager(imageManager);
		multiLinePropertyViewer.createContents();
		toolkit.adapt((Composite) multiLinePropertyViewer.getControl());
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = view.getViewSettings().getMultiEditorHeight();
		layoutData.horizontalSpan = 2;
		multiLinePropertyViewer.setLayoutData(layoutData);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#lock()
	 */
	public void lock() {
		multiLinePropertyViewer.setLocked(true);		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#unlock()
	 */
	public void unlock() {
		multiLinePropertyViewer.setLocked(true);		
	}


}
