package org.eclipse.emf.eef.runtime.tests.ui.views;

import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelFactory;
import org.eclipse.emf.eef.eeftests.bindingmodel.Root;
import org.eclipse.emf.eef.eeftests.bindingmodel.Sample;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandler;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
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

	private Root model;

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
		EEFTestStuffsBuilder builder = new EEFTestStuffsBuilder();
		context = builder.buildEditingContextWithPropertiesEditingViews();
		final PropertiesEditingComponent component = context.getEditingComponent();
		for (ViewHandler<?> viewHandler : component.getViewHandlers()) {
			if (viewHandler instanceof SWTViewHandler) {
				SWTViewHandler swtHandler = (SWTViewHandler)viewHandler;
				try {
					Composite view = swtHandler.createView(parent);
					view.setLayoutData(new GridData(GridData.FILL_BOTH));
					viewHandler.initView(component);
				} catch (ViewConstructionException e) {
					//EEF.getDefault().getLog().log(new Status(IStatus.ERROR, EEFTester.PLUGIN_ID, "Unable to create view.", e));
					e.printStackTrace();
				}
			} else if (viewHandler instanceof PropertiesEditingViewHandler) {
				PropertiesEditingViewHandler propertiesEditingHandler = (PropertiesEditingViewHandler) viewHandler;
				try {
					PropertiesEditingView view = propertiesEditingHandler.createView(component, parent);
					view.getContents().setLayoutData(new GridData(GridData.FILL_BOTH));
					propertiesEditingHandler.initView(component);
				} catch (ViewConstructionException e) {
					e.printStackTrace();
				}
				
			}
		}
		makeActions();
		contributeToActionBars();
	}

	private Root getModel() {
		if (model == null) {
			model = BindingmodelFactory.eINSTANCE.createRoot();
			Sample sample1 = BindingmodelFactory.eINSTANCE.createSample();
			sample1.setName("Sample 1");
			sample1.setActive(true);
			model.getSamples().add(sample1);
			Sample sample2 = BindingmodelFactory.eINSTANCE.createSample();
			sample2.setName("Sample 2");
			sample2.setActive(false);
			model.getSamples().add(sample2);
		}
		return model;
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
				Sample sample = (Sample)context.getEditingComponent().getTarget();
				InputDialog dialog = new InputDialog(new Shell(), "New semantic contents", "Enter the new semantic contents", 
						context != null?sample.getName():"", null);
				if (dialog.open() == Window.OK) {
					sample.setName(dialog.getValue());
					sample.setActive(!sample.isActive());
				}
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