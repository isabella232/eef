/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.group;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import org.eclipse.emf.eef.runtime.ui.fx.services.view.FXViewService;
import org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.FXPropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.Container;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class GroupFXPropertyEditor implements FXPropertyEditorViewer<Pane> {

	private PropertiesEditingView<Pane> view;
	private Container container;
	
	private TitledPane group;
	private GridPane groupContainer;

	
	/**
	 * @param viewElement
	 */
	public GroupFXPropertyEditor(PropertiesEditingView<Pane> view, Container viewElement) {
		this.view = view;
		this.container = viewElement;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public Pane getViewer() {
		return groupContainer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.SWTPropertyEditor#build(org.eclipse.swt.widgets.Composite)
	 */
	public void build(Pane parent) {
		group = new TitledPane();
		group.setText(container.getName());
		groupContainer = new GridPane();
		group.setContent(groupContainer);
		parent.getChildren().add(group);
		ViewService viewService = view.getViewService();
		if (viewService instanceof FXViewService) {
			FXViewService fxViewService = (FXViewService)viewService;
			GridPane.setConstraints(group, 1, fxViewService.viewElementRow(container), fxViewService.containerColumnsCount((Container) container.eContainer()), 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#lock()
	 */
	public void lock() {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#unlock()
	 */
	public void unlock() {
		// Do nothing
	}
		
}
