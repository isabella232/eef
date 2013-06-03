/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.view.handlers.editingview;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.viewhandler.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.impl.FormImplPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.impl.SWTImplPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.swt.view.handlers.editingview.PropertiesEditingViewHandler;
import org.eclipse.emf.eef.views.View;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PlatformAwarePropertiesEditingViewHandler extends PropertiesEditingViewHandler {

	/**
	 * @param handlerProvider
	 * @param editingComponent
	 * @param viewDescriptor
	 */
	public PlatformAwarePropertiesEditingViewHandler(PlatformAwarePropertiesEditingViewHandlerProvider handlerProvider, PropertiesEditingComponent editingComponent, View viewDescriptor) {
		super(handlerProvider, editingComponent, viewDescriptor);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandler#createView(java.lang.Object[])
	 */
	public PropertiesEditingView<Composite> createView(Object... args) throws ViewConstructionException {
		if (view == null) {
			if (args.length > 1 && args[0] instanceof PropertiesEditingComponent && args[1] instanceof Composite) {
				PropertiesEditingComponent editingComponent = (PropertiesEditingComponent) args[0];
				FormToolkit toolkit = editingComponent.getEditingContext().getOptions().getOption(EEFSWTConstants.FORM_TOOLKIT);
				if (toolkit != null) {
					view = new FormImplPropertiesEditingView(editingComponent, viewDescriptor);
					view.setViewServiceProvider(handlerProvider.getViewServiceProvider());
					view.setToolkitPropertyEditorFactory(handlerProvider.getEEFToolkitProvider());
					((FormImplPropertiesEditingView) view).createContents(toolkit, (Composite)args[1]);
				} else {
					view = new SWTImplPropertiesEditingView(editingComponent, viewDescriptor);					
					view.setViewServiceProvider(handlerProvider.getViewServiceProvider());
					view.setToolkitPropertyEditorFactory(handlerProvider.getEEFToolkitProvider());
					((SWTImplPropertiesEditingView) view).createContents((Composite)args[1]);
				}
			}
		}
		return view;
	}


}
