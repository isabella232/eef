/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.cases;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingViewEditingTestCase extends UIEditingTestCase {
	
	protected EEFViewer viewer;
	private List<Composite> compositeViews;
		
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase#initEnvironmentBuilder()
	 */
	@Override
	protected Builder initEnvironmentBuilder() {
		Builder builder = super.initEnvironmentBuilder();
		return builder.setEditingModel(builder.createEditingModelWithPropertiesEditingViews());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase#initUI()
	 */
	protected void initUI() {
		shell = new Shell();
		shell.setLayout (new FillLayout());
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		viewHandlers = editingContext.getEditingComponent().getViewDescriptors();

		viewer = new EEFViewer(composite, SWT.NONE);
		viewer.setContentProvider(new EEFContentProvider());
		viewer.setInput(editingContext);
		shell.pack();
		shell.open();
	}

	/**
	 * @return the viewer views.
	 */
	protected List<Composite> getViews() {
		if (compositeViews == null) {
			compositeViews = new ArrayList<Composite>();
			Composite viewerControl = (Composite)((Composite)viewer.getControl()).getChildren()[0];
			CTabFolder folder = (CTabFolder)viewerControl.getChildren()[0];
			for (CTabItem cTabItem : folder.getItems()) {
				compositeViews.add((Composite)cTabItem.getControl());
			}
		}
		return compositeViews;
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

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase#disposeUI()
	 */
	@Override
	protected void disposeUI() {
		for (ViewHandler<?> handler : viewHandlers) {
			handler.dispose();
		}
		shell.dispose();
		if (compositeViews != null)
			compositeViews.clear();
	}
	
}
