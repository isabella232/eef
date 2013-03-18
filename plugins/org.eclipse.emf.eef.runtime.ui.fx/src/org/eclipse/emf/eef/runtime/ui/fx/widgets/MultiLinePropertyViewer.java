/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.widgets;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.ui.fx.FXUIConstants;
import org.eclipse.emf.eef.runtime.ui.fx.widgets.utils.AdapterFactoryFeatureObservableList;

import at.bestsolution.efxclipse.runtime.emf.edit.ui.AdapterFactoryTableCellFactory;
import at.bestsolution.efxclipse.runtime.emf.edit.ui.ProxyCellValueFactory;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class MultiLinePropertyViewer extends BorderPane  {
	
	private TableView<Object> table;
	private Button addButton;
	private Button removeButton;
	private Button upButton;
	private Button downButton;
	private AdapterFactory adapterFactory;

	public MultiLinePropertyViewer() {
		table = new TableView<Object>();
		TableColumn<Object, Object> column = new TableColumn<Object, Object>("Elements");
		column.setPrefWidth(FXUIConstants.DEFAULT_COLUMN_WIDTH);
		table.getColumns().add(column);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		setCenter(table);
		VBox vbox = createControlPanel();
		vbox.setPadding(new Insets(0, 10, 0, 10));
		vbox.setSpacing(5);
		setRight(vbox);
	}

	private VBox createControlPanel() {
		VBox vbox = new VBox();
		addButton = createButton("icons/Add.gif");
		removeButton = createButton("icons/Delete.gif");
		upButton = createButton("icons/ArrowUp.gif");
		downButton = createButton("icons/ArrowDown.gif");
		vbox.getChildren().addAll(addButton, removeButton, upButton, downButton);
		buildAdditionnalActionControls(vbox);
		return vbox;
	}

	private Button createButton(String iconPath) {
		Button result;
		URL bundleEntry = org.osgi.framework.FrameworkUtil.getBundle(this.getClass()).getEntry(iconPath);
		try {
			Image addImage = new Image(bundleEntry.openStream());
			result = new Button("", new ImageView(addImage));
		} catch (IOException e) {
			result = new Button("+");
		}
		return result;
	}
	
	public void setInput(AdapterFactory adapterFactory, EObject input, EStructuralFeature feature) {
		this.adapterFactory = adapterFactory;
		for (int i = 0; i< table.getColumns().size(); i++) {
			@SuppressWarnings("unchecked")
			TableColumn<Object, Object> column = (TableColumn<Object, Object>) table.getColumns().get(i);
			column.setCellFactory(new AdapterFactoryTableCellFactory<Object, Object>(this.adapterFactory, i));
			column.setCellValueFactory(new ProxyCellValueFactory<Object, Object>());
		}
		table.setItems(new AdapterFactoryFeatureObservableList<Object>(adapterFactory, input, feature));
	}
	
	public void setLocked(boolean b) {
		// TODO Auto-generated method stub
		
	}

	protected void buildAdditionnalActionControls(Pane parent) {
		//Do nothing
	}

	public void setLowerBound(int lowerBound) {
		// TODO Auto-generated method stub
		
	}

	public void setUpperBound(int upperBound) {
		// TODO Auto-generated method stub
		
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	public void addColumn(String name, int defaultColumnWidth) {
		// TODO Auto-generated method stub
		
	}	
	
	public void addReferenceEditorListener(MultiLinePropertyViewerListener listener) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
	 *
	 */
	public interface MultiLinePropertyViewerListener {

		/**
		 * Handle a "add" request.
		 */
		void add();
		
		/**
		 * Handle a "edit" request.
		 * @param editedElement Edited Element.
		 */
		void edit(Object editedElement);
		
		/**
		 * Handle a "remove" request.
		 * @param removedElement Removed Element.
		 */
		void remove(Object removedElement);
		
		/**
		 * Handle a "remove" request for several elements.
		 * @param removedElements Removed Elements.
		 */
		void removeAll(Collection<?> removedElements);
		
		/**
		 * Handle a "move up" request.
		 * @param movedElement Moved Element.
		 */
		void moveUp(Object movedElement);
		
		/**
		 * Handle a "move down" request.
		 * @param movedElement Moved Element.
		 */
		void moveDown(Object movedElement);
		
	}
}
