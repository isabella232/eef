/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.internal.services.view;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.ui.fx.services.view.FXViewService;
import org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.FXWidgetPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.internal.services.view.ViewServiceImpl;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorProvider;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.emf.eef.views.ViewElement;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class FXViewServiceImpl extends ViewServiceImpl implements FXViewService {

	private static final String EMPTY_STRING = "";

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.fx.services.view.FXViewService#createLabel(javafx.scene.layout.Pane, java.lang.Object, java.lang.String)
	 */
	public Label createLabel(Pane parent, Object editor, String alternate) {
		String text = getDescription(editor, alternate);
		if (!text.endsWith(": ") && !text.endsWith(":")) {
			text += ": ";
		}
		Label label;
		label = new Label(text);
		EStructuralFeature associatedFeature = feature(editor);
		if (associatedFeature != null && associatedFeature.isRequired()) {
			label.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD,Font.getDefault().getSize()));
		}
		parent.getChildren().add(label);
		return label;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.fx.services.view.FXViewService#createHelpButton(javafx.scene.layout.Pane, java.lang.Object)
	 */
	public Label createHelpButton(Pane parent, Object editor) {
		String alternate = getHelpContent(editor);
//		Image image = JFaceResources.getImage(DLG_IMG_HELP);
		Label result = new Label();
		if (alternate != null && !EMPTY_STRING.equals(alternate)) { //$NON-NLS-1$
//			result.setImage(image);
			result.setText("?");
			result.setTooltip(new Tooltip(alternate));
		}
		parent.getChildren().add(result);
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.fx.services.view.FXViewService#viewElementRow(org.eclipse.emf.eef.views.ViewElement)
	 */
	@SuppressWarnings("unchecked")
	public int viewElementRow(ViewElement element) {
		return ((List<EObject>) element.eContainer().eGet(element.eContainingFeature())).indexOf(element) + 1;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.fx.services.view.FXViewService#containerColumnsCount(org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView, org.eclipse.emf.eef.views.Container)
	 */
	public int containerColumnsCount(PropertiesEditingView<Pane> view, Container container) {
		int result = 1;
		for (EObject element : container.eContents()) {
			PropertyEditorContext<Pane> editorContext = new PropertyEditorContext<Pane>((PropertiesEditingView<Pane>) view, (ViewElement) element);
			ToolkitPropertyEditorProvider<Pane> toolkitProvider = view.getEditingComponent().getEditingContext().getServiceRegistry().getService(ToolkitPropertyEditorProvider.class, editorContext);
			for (WidgetPropertyEditorProvider<Pane> widgetProvider : toolkitProvider.getWidgetProviders()) {
				if (widgetProvider.serviceFor(editorContext)) {
					int columnsConsumption = ((FXWidgetPropertyEditorProvider<?>)widgetProvider).columnsConsumption();
					if(columnsConsumption > result) {
						result = columnsConsumption;
					}
				}
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.fx.services.view.FXViewService#executeSyncUIRunnable(java.lang.Runnable)
	 */
	public void executeSyncUIRunnable(Runnable job) {
		if (javafx.application.Platform.isFxApplicationThread()) {
			job.run();
		} else {
			RunnableFuture<?> task = new FutureTask<Void>(job, null);
			
			javafx.application.Platform.runLater(task);

			try {
				task.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} finally {
				task.cancel(true);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.fx.services.view.FXViewService#executeAsyncUIRunnable(java.lang.Runnable)
	 */
	public void executeAsyncUIRunnable(Runnable job) {
		javafx.application.Platform.runLater(job);
	}

}
