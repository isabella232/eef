package org.eclipse.emf.eef.runtime.tests.ui.views;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.services.impl.PriorityCircularityException;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironmentBuilder;
import org.eclipse.emf.eef.runtime.ui.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.viewer.EEFViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFTesterView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.eclipse.emf.eef.runtime.tester.views.EEFTesterView";

	private Action action;

	private PropertiesEditingContext context;
	
	/**
	 * The constructor.
	 */
	public EEFTesterView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		EEFTestEnvironmentBuilder builder = new EEFTestEnvironmentBuilder();
		try {
			context = builder.buildEditingContext(
						builder.buildAdapterFactory(), 
						null, 
						builder.createEMFServiceProvider(builder.createEMFServices()), 
						EcoreFactory.eINSTANCE.createEClass());
		} catch (PriorityCircularityException e) {
			e.printStackTrace();
		}

		EEFViewer viewer = new EEFViewer(parent, SWT.NONE);
		viewer.setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer.setContentProvider(new EEFContentProvider());
		viewer.setInput(context);
		makeActions();
		contributeToActionBars();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		
	}

	private void makeActions() {
		action = new Action() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.jface.action.Action#run()
			 */
			public void run() {
				EClass eClass = (EClass) context.getEditingComponent().getEObject();
				eClass.getESuperTypes().move(0, eClass.getESuperTypes().get(1));
			}
			
		};
		action.setText("Edit");
		action.setToolTipText("Edit semantic contents");
		action.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));
		

	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action);
	}

}