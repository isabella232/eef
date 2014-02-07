package org.eclipse.emf.samples.conference.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.eef.runtime.ui.swt.e3.tabbed.view.section.SectionPropertiesEditingView;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.views.View;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SwitchReadonlyness extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public SwitchReadonlyness() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		View sectionView = extractActiveSectionViewDescriptor(window);
		MessageDialog.openInformation(
				window.getShell(),
				"Conference Editor",
				sectionView.toString());
		return null;
	}

	/**
	 * @param window
	 * @return
	 */
	private View extractActiveSectionViewDescriptor(IWorkbenchWindow window) {
		IViewReference[] viewReferences = window.getActivePage().getViewReferences();
		StringBuilder sb = new StringBuilder("List of views:");
		for (IViewReference iViewReference : viewReferences) {
			IViewPart view = iViewReference.getView(true);
			if (view instanceof PropertySheet) {
				sb.append(view.getTitle());
				PropertySheet sheet = (PropertySheet) view;
				IPage currentPage = sheet.getCurrentPage();
				if (currentPage instanceof TabbedPropertySheetPage) {
					TabbedPropertySheetPage page = (TabbedPropertySheetPage) currentPage;
					ISection sectionAtIndex = page.getCurrentTab().getSectionAtIndex(0);
					if (sectionAtIndex instanceof SectionPropertiesEditingView) {
						SectionPropertiesEditingView editingView = (SectionPropertiesEditingView) sectionAtIndex;
						PropertiesEditingComponent editingComponent = editingView.getEditingComponent();
						org.eclipse.emf.eef.runtime.editingModel.View viewDescriptor = editingComponent.getDescriptorForView(editingView);
						if (viewDescriptor instanceof EObjectView && ((EObjectView) viewDescriptor).getDefinition() instanceof View) {
							return (View)((EObjectView) viewDescriptor).getDefinition();
						}
					}
				}
			}
		}
		return null;
	}
}
