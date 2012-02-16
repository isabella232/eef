/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.cases;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
import org.eclipse.emf.eef.runtime.ui.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.viewer.EEFViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class PropertiesEditingViewEditingTestCase extends TestCase {

	protected PropertiesEditingContext context;
	protected PropertiesEditingComponent component;

	protected Shell shell;
	protected EEFViewer viewer;
	private List<Composite> views;



	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		initUI();
	}

	/**
	 * @return the {@link PropertiesEditingModel} for the test case.
	 */
	protected PropertiesEditingModel buildEditingModel() {
		return new EEFTestStuffsBuilder().buildEditingModelWithSWTViews();
	}

	/**
	 * @return {@link PropertiesEditingContext} for the test case.
	 */
	protected PropertiesEditingContext buildEditingContext() {
		return new EEFTestStuffsBuilder().buildEditingContextWithPropertiesEditingViewsForEcore();
	}
	
	/**
	 * @return the element to edit.
	 */
	@SuppressWarnings("unchecked")
	public <T extends EObject> T getElementToEdit() {
		if (context instanceof EObjectPropertiesEditingContext) {
			return (T)((EObjectPropertiesEditingContext) context).getEObject();
		}
		return null;
	}

	/**
	 * @return the viewer views.
	 */
	protected List<Composite> getViews() {
		if (views == null) {
			views = new ArrayList<Composite>();
			Composite viewerControl = (Composite)((Composite)viewer.getControl()).getChildren()[0];
			CTabFolder folder = (CTabFolder)viewerControl.getChildren()[0];
			for (CTabItem cTabItem : folder.getItems()) {
				views.add((Composite)cTabItem.getControl());
			}
		}
		return views;
	}
	
	/**
	 * Search the control at the given index and cast it in the given type.
	 * @param view {@link Composite} to process.
	 * @param index index of the composite.
	 * @return the control at the given index.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getControl(Composite view, int index) {
		return (T) view.getChildren()[index];
	}
	
	@After
	public void tearDown() {
		disposeUI();
	}

	/**
	 * 
	 */
	protected void initUI() {
		context = buildEditingContext();
		shell = new Shell();
		shell.setLayout (new FillLayout());
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		viewer = new EEFViewer(composite, SWT.NONE);
		viewer.setContentProvider(new EEFContentProvider());
		viewer.setInput(context);
		shell.pack();
		shell.open();
	}

	/**
	 * 
	 */
	protected void disposeUI() {
		shell.dispose();
	}

	
}
