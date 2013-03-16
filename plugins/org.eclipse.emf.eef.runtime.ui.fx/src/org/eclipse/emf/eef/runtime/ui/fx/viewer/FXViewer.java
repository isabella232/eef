/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.viewer;

import java.util.Collection;

import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.services.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.ui.fx.view.handlers.editingview.FXEditingViewHandler;
import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class FXViewer {

	
	private TabPane container;
	private PropertiesEditingContext context; 
	private Collection<ViewHandler<?>> viewHandlers;
	
	/**
	 * @return the viewer input.
	 */
	public Object getInput() {
		return this.context;
	}
	
	/**
	 * Defines the {@link PropertiesEditingContext} to edit.
	 * @param context  the input {@link PropertiesEditingContext}
	 */
	public void setInput(PropertiesEditingContext context) {
		this.context = context;
		refresh();
	}
	
	/**
	 * @return the container
	 */
	public Control getControl() {
		return container;
	}

	/**
	 * 
	 */
	public void refresh() {
		container = new TabPane();
		if (context != null) {
			container.getTabs().clear();
			viewHandlers = context.getEditingComponent().createViewHandlers();
			PropertiesEditingComponent component = context.getEditingComponent();		
			for (ViewHandler<?> handler : viewHandlers) {
				if (handler instanceof FXEditingViewHandler) {
					FXEditingViewHandler propertiesEditingViewHandler = (FXEditingViewHandler) handler;
					View viewDescriptor = propertiesEditingViewHandler.getViewDescriptor();
					try {
						Tab tab = new Tab(viewDescriptor.getName());
						BorderPane tabContent = new BorderPane();
						tabContent.setPadding(new Insets(10, 10, 10, 10));
						propertiesEditingViewHandler.createView(component, tabContent);
						handler.initView(component);
						tab.setContent(tabContent);
						tab.setClosable(false);
						container.getTabs().add(tab);
					} catch (ViewConstructionException e) {
						EEFLogger logger = handler.getProvider().getServiceRegistry().getService(EEFLogger.class, this);
						logger.logError("org.eclipse.emf.eef.runtime.ui.fx", "An error occured during view creation.", e);
					}	
				}
			}
		}
	}
}
