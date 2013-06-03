/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit.edatepicker;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.swt.services.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.StandardSWTPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.swt.widgets.SingleLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EDatePickerSWTPropertyEditor extends StandardSWTPropertyEditor<SingleLinePropertyViewer> {
	
	private EditUIProvidersFactory editUIProvidersFactory;

	private SingleLinePropertyViewer eDatePicker;

	/**
	 * @param editUIProvidersFactory 
	 * @param view
	 * @param elementEditor
	 */
	public EDatePickerSWTPropertyEditor(EditUIProvidersFactory editUIProvidersFactory, PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		super(view, elementEditor);
		this.editUIProvidersFactory = editUIProvidersFactory;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public SingleLinePropertyViewer getViewer() {
		return eDatePicker;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.StandardSWTPropertyEditor#createEditorContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createEditorContents(Composite parent) {
		eDatePicker = new SingleLinePropertyViewer(parent, SWT.BORDER);
		PropertiesEditingContext editingContext = view.getEditingComponent().getEditingContext();
		EEFServiceRegistry serviceRegistry = editingContext.getServiceRegistry();
		eDatePicker.setLabelProvider(editUIProvidersFactory.createLabelProvider(editingContext.getAdapterFactory()));
		ImageManager imageManager = serviceRegistry.getService(ImageManager.class, this);
		eDatePicker.setImageManager(imageManager);
		eDatePicker.createContents();
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		eDatePicker.getControl().setLayoutData(layoutData);
	}

	
	
}
