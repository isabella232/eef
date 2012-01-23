package org.eclipse.emf.eef.runtime.tests.ui.views;

import org.eclipse.emf.eef.eeftests.bindingmodel.AbstractSample;
import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelFactory;
import org.eclipse.emf.eef.eeftests.bindingmodel.Root;
import org.eclipse.emf.eef.eeftests.bindingmodel.Sample;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
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
		AbstractSample eObjectToEdit = getModel().getSamples().get(0);
		EObjectPropertiesEditingContext context = new EObjectPropertiesEditingContext(eObjectToEdit);
		context.setEditingModel(builder.buildEditingModel());
		context.setViewHandlerProvider(builder.buildViewHandlerProvider());
		PropertiesEditingComponent component = context.getComponent();
		ViewHandler<?> viewHandler = component.getViewHandler();
		if (viewHandler instanceof SWTViewHandler) {
			SWTViewHandler swtHandler = (SWTViewHandler)viewHandler;
			try {
				Composite view = swtHandler.createView(parent);
				view.setLayoutData(new GridData(GridData.FILL_BOTH));
				viewHandler.initView(eObjectToEdit);
			} catch (ViewConstructionException e) {
//				EEF.getDefault().getLog().log(new Status(IStatus.ERROR, EEFTester.PLUGIN_ID, "Unable to create view.", e));
				e.printStackTrace();
			}
		}
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

}