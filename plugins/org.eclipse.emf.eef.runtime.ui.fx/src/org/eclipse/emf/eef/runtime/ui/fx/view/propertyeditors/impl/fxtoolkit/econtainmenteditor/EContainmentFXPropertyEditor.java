/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.econtainmenteditor;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import org.eclipse.emf.eef.runtime.ui.fx.services.view.FXViewService;
import org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.FXPropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.fx.widgets.MultiLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EContainmentFXPropertyEditor implements FXPropertyEditorViewer<MultiLinePropertyViewer> {

	protected PropertiesEditingView<Pane> view;
	protected ElementEditor elementEditor;
	private MultiLinePropertyViewer multiLinePropertyViewer;

	/**
	 * @param view
	 * @param elementEditor
	 */
	public EContainmentFXPropertyEditor(PropertiesEditingView<Pane> view, ElementEditor elementEditor) {
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
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.SWTPropertyEditor#build(org.eclipse.swt.widgets.Composite)
	 */
	public void build(final Pane parent) {
		final ViewService viewService = view.getViewService();
		if (viewService instanceof FXViewService) {
			Label label = ((FXViewService) viewService).createLabel(parent, elementEditor, elementEditor.getName());
			GridPane.setConstraints(label, 1, ((FXViewService) viewService).viewElementRow(elementEditor), 1, 1, HPos.RIGHT, VPos.CENTER);
		}
		multiLinePropertyViewer = new MultiLinePropertyViewer() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.swt.widgets.MultiLinePropertyViewer#buildAdditionnalActionControls(org.eclipse.swt.widgets.Composite)
			 */
			@Override
			protected void buildAdditionnalActionControls(Pane parent) {
				if (viewService instanceof FXViewService) {
					Label helpButton = ((FXViewService) viewService).createHelpButton(parent, elementEditor);
					helpButton.setMaxWidth(Double.MAX_VALUE);
				}
			}
			
		};
		if (parent instanceof BorderPane) {
			((BorderPane) parent).setCenter(multiLinePropertyViewer);
		} else if (parent instanceof GridPane) {
			parent.getChildren().add(multiLinePropertyViewer);
			if (viewService instanceof FXViewService) {
				FXViewService fxViewService = ((FXViewService) viewService);
				int columnSpan = fxViewService.containerColumnsCount(view, (Container) elementEditor.eContainer()) - 1;
				GridPane.setConstraints(multiLinePropertyViewer, 2, fxViewService.viewElementRow(elementEditor), columnSpan, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
			}
		}
//		for (EObject subEditor : elementEditor.eContents()) {
//			if (subEditor instanceof ElementEditor) {
//				multiLinePropertyViewer.addColumn(((ElementEditor) subEditor).getName(), FXUIConstants.DEFAULT_COLUMN_WIDTH);
//			}
//		}
//		ImageManager imageManager = view.getEditingComponent().getEditingContext().getServiceRegistry().getService(ImageManager.class, this);
//		multiLinePropertyViewer.setImageManager(imageManager);
//		multiLinePropertyViewer.createContents();
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
		multiLinePropertyViewer.setLocked(false);		
	}


}
