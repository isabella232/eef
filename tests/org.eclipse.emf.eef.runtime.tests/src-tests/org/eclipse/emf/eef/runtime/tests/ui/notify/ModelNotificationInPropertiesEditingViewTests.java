/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.notify;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * NOTE: I don't know how to cause the 'Unset Event'
 */
public class ModelNotificationInPropertiesEditingViewTests extends UIEditingTestCase {

	private static final String NEW_ECLASS_NAME = "New EClass name";

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase#initEnvironmentBuilder()
	 */
	@Override
	protected Builder initEnvironmentBuilder() {
		Builder builder = super.initEnvironmentBuilder();
		return builder.setEditingModel(builder.createEditingModelWithPropertiesEditingViews());
	}

	@Test
	public void testSetRefresh() {
		disposeUI();
		initUI();
		EClass editedElement = (EClass) editingContext.getEditingComponent().getEObject();
		PropertiesEditingView<Composite> view = (PropertiesEditingView<Composite>) views.get(0);
		editedElement.setName(NEW_ECLASS_NAME);
		Text nameText = getText(view);
		assertEquals(editedElement.getName(), nameText.getText());
		
		editedElement.setAbstract(false);
		Button abstractButton = getButton(view);
		assertEquals(editedElement.isAbstract(), abstractButton.getSelection());
	}
	
	@Test
	public void testAddRefresh() {
		disposeUI();
		initUI();
		EClass editedElement = (EClass) editingContext.getEditingComponent().getEObject();
		PropertiesEditingView<Composite> view = (PropertiesEditingView<Composite>) views.get(0);
		EClassifier eClassifier = editedElement.getEPackage().getEClassifiers().get(3);
		editedElement.getESuperTypes().add((EClass) eClassifier);
		Table table = getReferenceEditorTable(view.getContents());
		assertEquals(editedElement.getESuperTypes().size(), table.getItemCount());
	}
	
	@Test
	public void testAddAllRefresh() {
		disposeUI();
		initUI();
		EClass editedElement = (EClass)editingContext.getEditingComponent().getEObject();
		PropertiesEditingView<Composite> view = (PropertiesEditingView<Composite>) views.get(0);
		EClassifier eClassifier1 = editedElement.getEPackage().getEClassifiers().get(1);
		EClassifier eClassifier2 = editedElement.getEPackage().getEClassifiers().get(2);
		editedElement.getESuperTypes().clear();
		List<EClass> newESuperClasses = new ArrayList<EClass>();
		newESuperClasses.add((EClass) eClassifier1);
		newESuperClasses.add((EClass) eClassifier2);
		editedElement.getESuperTypes()
			.addAll(newESuperClasses);
		//NOTE: This doesn't throw a ADD_MANY notification
		Table table = getReferenceEditorTable(view.getContents());
		assertEquals(editedElement.getESuperTypes().size(), table.getItemCount());
	}

	@Test
	public void testRemoveRefresh() {
		disposeUI();
		initUI();
		EClass editedElement = (EClass)editingContext.getEditingComponent().getEObject();
		PropertiesEditingView<Composite> view = (PropertiesEditingView<Composite>) views.get(0);
		editedElement.getESuperTypes().remove(editedElement.getESuperTypes().get(0));
		Table table = getReferenceEditorTable(view.getContents());
		assertEquals(editedElement.getESuperTypes().size(), table.getItemCount());
	}

	@Test
	public void testRemoveAllRefresh() {
		disposeUI();
		initUI();
		EClass editedElement = (EClass)editingContext.getEditingComponent().getEObject();
		PropertiesEditingView<Composite> view = (PropertiesEditingView<Composite>) views.get(0);
		editedElement.getESuperTypes().clear();
		Table table = getReferenceEditorTable(view.getContents());
		assertEquals(editedElement.getESuperTypes().size(), table.getItemCount());
	}

	@Test
	public void testMoveRefresh() {
		disposeUI();
		initUI();
		EClass editedElement = (EClass)editingContext.getEditingComponent().getEObject();
		PropertiesEditingView<Composite> view = (PropertiesEditingView<Composite>) views.get(0);
		Table table = getReferenceEditorTable(view.getContents());
		Object eSuperType1 = table.getItem(1).getData();
		editedElement.getESuperTypes().move(0, editedElement.getESuperTypes().get(1));
		assertEquals(eSuperType1, table.getItem(0).getData());
	}

	private Table getReferenceEditorTable(Composite view) {
		Control[] children = view.getChildren();
		for (Control control : children) {
			if (control instanceof Table) {
				return (Table) control;
			} else if (control instanceof Composite) {
				Table referenceEditorTree = getReferenceEditorTable((Composite) control);
				if (referenceEditorTree != null) {
					return referenceEditorTree;
				}
			}
		}
		return null;
	}

	private Button getButton(PropertiesEditingView<Composite> view) {
		Control[] children = view.getContents().getChildren();
		for (Control control : children) {
			if (control instanceof Button) {
				return (Button) control;
			}
		}
		return null;
	}

	private Text getText(PropertiesEditingView<Composite> view) {
		Control[] children = view.getContents().getChildren();
		for (Control control : children) {
			if (control instanceof Text) {
				return (Text) control;
			}
		}
		return null;
	}
	
}
