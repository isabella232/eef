/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.notify;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironmentBuilder;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * NOTE: I don't know how to cause the 'Unset Event'
 */
public class ModelNotificationInPropertiesEditingViewTests extends UIEditingTestCase {

	private static final String NEW_ECLASS_NAME = "New EClass name";

	
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase#buildEditingModel()
	 */
	@Override
	protected PropertiesEditingModel buildEditingModel() {
		return new EEFTestEnvironmentBuilder().buildEditingModelWithPropertiesEditingViews();
	}

	@Test
	public void testSetRefresh() {
		disposeUI();
		initUI();
		EClass editedElement = (EClass) editingContext.getEditingComponent().getEObject();
		PropertiesEditingView view = (PropertiesEditingView) views.get(0);
		editedElement.setName(NEW_ECLASS_NAME);
		Text nameText = getText(view);
		assertEquals(editedElement.getName(), nameText.getText());
		
		editedElement.setAbstract(false);
		Button abstractButton = getButton(view);
		assertEquals(editedElement.isAbstract(), abstractButton.getSelection());
	}
	
//	@Test
//	public void testAddRefresh() {
//		disposeUI();
//		initUI();
//		EClass editedElement = (EClass) editingContext.getEditingComponent().getEObject();
//		PropertiesEditingView view = (PropertiesEditingView) views.get(0);
//		EClassifier eClassifier = editedElement.getEPackage().getEClassifiers().get(3);
//		editedElement.getESuperTypes().add((EClass) eClassifier);
//		Tree tree = getReferenceEditorTree(view.getContents());
//		assertEquals(editedElement.getESuperTypes().size(), tree.getItemCount());
//	}
//	
//	@Test
//	public void testAddAllRefresh() {
//		disposeUI();
//		initUI();
//		EClass editedElement = (EClass)editingContext.getEditingComponent().getEObject();
//		PropertiesEditingView view = (PropertiesEditingView) views.get(0);
//		EClassifier eClassifier1 = editedElement.getEPackage().getEClassifiers().get(1);
//		EClassifier eClassifier2 = editedElement.getEPackage().getEClassifiers().get(2);
//		editedElement.getESuperTypes().clear();
//		List<EClass> newESuperClasses = new ArrayList<EClass>();
//		newESuperClasses.add((EClass) eClassifier1);
//		newESuperClasses.add((EClass) eClassifier2);
//		editedElement.getESuperTypes()
//			.addAll(newESuperClasses);
//		//NOTE: This doesn't throw a ADD_MANY notification
//		Tree tree = getReferenceEditorTree(view.getContents());
//		assertEquals(editedElement.getESuperTypes().size(), tree.getItemCount());
//	}
//
//	@Test
//	public void testRemoveRefresh() {
//		disposeUI();
//		initUI();
//		EClass editedElement = (EClass)editingContext.getEditingComponent().getEObject();
//		PropertiesEditingView view = (PropertiesEditingView) views.get(0);
//		editedElement.getESuperTypes().remove(editedElement.getESuperTypes().get(0));
//		Tree tree = getReferenceEditorTree(view.getContents());
//		assertEquals(editedElement.getESuperTypes().size(), tree.getItemCount());
//	}
//
//	@Test
//	public void testRemoveAllRefresh() {
//		disposeUI();
//		initUI();
//		EClass editedElement = (EClass)editingContext.getEditingComponent().getEObject();
//		PropertiesEditingView view = (PropertiesEditingView) views.get(0);
//		editedElement.getESuperTypes().clear();
//		Tree tree = getReferenceEditorTree(view.getContents());
//		assertEquals(editedElement.getESuperTypes().size(), tree.getItemCount());
//	}
//
//	@Test
//	public void testMoveRefresh() {
//		disposeUI();
//		initUI();
//		EClass editedElement = (EClass)editingContext.getEditingComponent().getEObject();
//		PropertiesEditingView view = (PropertiesEditingView) views.get(0);
//		Tree tree = getReferenceEditorTree(view.getContents());
//		Object eSuperType1 = tree.getItem(1).getData();
//		editedElement.getESuperTypes().move(0, editedElement.getESuperTypes().get(1));
//		assertEquals(eSuperType1, tree.getItem(0).getData());
//	}

	private Tree getReferenceEditorTree(Composite view) {
		Control[] children = view.getChildren();
		for (Control control : children) {
			if (control instanceof Tree) {
				return (Tree) control;
			} else if (control instanceof Composite) {
				Tree referenceEditorTree = getReferenceEditorTree((Composite) control);
				if (referenceEditorTree != null) {
					return referenceEditorTree;
				}
			}
		}
		return null;
	}

	private Button getButton(PropertiesEditingView view) {
		Control[] children = view.getContents().getChildren();
		for (Control control : children) {
			if (control instanceof Button) {
				return (Button) control;
			}
		}
		return null;
	}

	private Text getText(PropertiesEditingView view) {
		Control[] children = view.getContents().getChildren();
		for (Control control : children) {
			if (control instanceof Text) {
				return (Text) control;
			}
		}
		return null;
	}
	
}
