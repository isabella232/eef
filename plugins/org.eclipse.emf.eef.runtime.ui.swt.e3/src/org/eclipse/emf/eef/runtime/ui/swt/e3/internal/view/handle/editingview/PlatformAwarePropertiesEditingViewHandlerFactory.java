/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.handle.editingview;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.impl.FormImplPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.editingview.PropertiesEditingViewHandlerFactory;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.impl.SWTImplPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.view.handle.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.views.View;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PlatformAwarePropertiesEditingViewHandlerFactory extends PropertiesEditingViewHandlerFactory {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.editingview.PropertiesEditingViewHandlerFactory#serviceFor(org.eclipse.emf.eef.runtime.editingModel.View)
	 */
	@Override
	public boolean serviceFor(org.eclipse.emf.eef.runtime.editingModel.View view) {
		return super.serviceFor(view);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.editingview.PropertiesEditingViewHandlerFactory#createView(java.lang.Object, java.lang.Object[])
	 */
	@Override
	public PropertiesEditingView<Composite> createView(Object viewDescriptor, Object... args) throws ViewConstructionException {
		PropertiesEditingView<Composite> view;
		if (viewDescriptor instanceof EObjectView && ((EObjectView)viewDescriptor).getDefinition() instanceof View && args.length > 1 && args[0] instanceof PropertiesEditingComponent && args[1] instanceof Composite) {
			View eObjectViewDescriptor = (View) ((EObjectView)viewDescriptor).getDefinition();
			PropertiesEditingComponent editingComponent = (PropertiesEditingComponent) args[0];
			FormToolkit toolkit = editingComponent.getEditingContext().getOptions().getOption(EEFSWTConstants.FORM_TOOLKIT);
			if (toolkit != null) {
				view = new FormImplPropertiesEditingView(editingComponent, eObjectViewDescriptor);
				view.setEMFServiceProvider(getEMFServiceProvider());
				view.setViewServiceProvider(getViewServiceProvider());
				view.setToolkitPropertyEditorFactory(getEEFToolkitProvider());
				((FormImplPropertiesEditingView) view).createContents(toolkit, (Composite)args[1]);
			} else {
				view = new SWTImplPropertiesEditingView(editingComponent, eObjectViewDescriptor);					
				view.setEMFServiceProvider(getEMFServiceProvider());
				view.setViewServiceProvider(getViewServiceProvider());
				view.setToolkitPropertyEditorFactory(getEEFToolkitProvider());
				((SWTImplPropertiesEditingView) view).createContents((Composite)args[1]);
			}
			return view;
		}
		return super.createView(viewDescriptor, args);
	}

	

}
