/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.viewer;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.services.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.ui.fx.view.handlers.editingview.FXEditingViewHandler;
import org.eclipse.emf.eef.runtime.ui.fx.viewer.filters.EEFViewerFilter;
import org.eclipse.emf.eef.runtime.ui.fx.viewer.filters.ViewFilter;
import org.eclipse.emf.eef.views.View;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class FXViewer {

	
	private BorderPane container;
	private PropertiesEditingContext context; 
	private Collection<ViewHandler<?>> viewHandlers;
	private List<EEFViewerFilter> filters;
	private boolean dynamicTabHeader = true;
	
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
	public Pane getControl() {
		return container;
	}

	/**
	 * @param dynamicTabHeader <code>true</code> if the TabHeaders must be dynamic (i.e. disappear when there is only one view).
	 */
	public void setDynamicTabHeader(boolean dynamicTabHeader) {
		this.dynamicTabHeader  = dynamicTabHeader;
	}

	/**
	 * 
	 */
	public void refresh() {
		if (context != null) {
			viewHandlers = context.getEditingComponent().createViewHandlers();
			Collection<ViewHandler<?>> filteredHandlers;
			if (filters != null && filters.size() > 0) {  
			filteredHandlers = Collections2.filter(viewHandlers, new Predicate<ViewHandler<?>>() {
				
				/**
				 * @param handler
				 * @return
				 */
				public boolean apply(ViewHandler<?> handler) {
					if (handler instanceof FXEditingViewHandler) {
						Object view = ((FXEditingViewHandler) handler).getViewDescriptor();
						return view instanceof View && isFiltered((View) view);
					}
					return false;
				}
			});
			} else {
				filteredHandlers = viewHandlers;
			}
			// We display tab if dynamicTabHeader is set to false OR if there is more than 1 view to display
			boolean isTabbed = dynamicTabHeader == false || filteredHandlers.size() > 1;
			// If the container doesn't exists, we create one.
			if (container == null) {
				container = new BorderPane();
			} else {
				// Else we clear the existing views.
				if (container.getCenter() instanceof TabPane) {
					((TabPane) container.getCenter()).getTabs().clear();
				} else if (container.getCenter() instanceof BorderPane) {
					((BorderPane)container.getCenter()).getChildren().remove(container.getCenter());
				}
			}
			PropertiesEditingComponent component = context.getEditingComponent();		
			if (isTabbed) {
				TabPane viewContainer = new TabPane();
				container.setCenter(viewContainer);
				for (ViewHandler<?> handler : filteredHandlers) {
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
							viewContainer.getTabs().add(tab);
						} catch (ViewConstructionException e) {
							EEFLogger logger = handler.getProvider().getServiceRegistry().getService(EEFLogger.class, this);
							logger.logError("org.eclipse.emf.eef.runtime.ui.fx", "An error occured during view creation.", e);
						}	
					}
				}
			} else {
				if (filteredHandlers.size() == 1) {
					
					ViewHandler<?> handler = filteredHandlers.iterator().next();
					if (handler instanceof FXEditingViewHandler) {
						FXEditingViewHandler propertiesEditingViewHandler = (FXEditingViewHandler) handler;
						try {
							BorderPane viewContainer = new BorderPane();
							viewContainer.setPadding(new Insets(10, 10, 10, 10));
							propertiesEditingViewHandler.createView(component, viewContainer);
							handler.initView(component);
							container.setCenter(viewContainer);
						} catch (ViewConstructionException e) {
							EEFLogger logger = handler.getProvider().getServiceRegistry().getService(EEFLogger.class, this);
							logger.logError("org.eclipse.emf.eef.runtime.ui.fx", "An error occured during view creation.", e);
						}	
					}
				}
			}
		}
	}


	/**
	 * @param viewDescriptor
	 * @return
	 */
	private boolean isFiltered(View viewDescriptor) {
		if (filters != null) {
			for (EEFViewerFilter filter : filters) {
				if (filter instanceof ViewFilter) {
					if (!filter.select(this, viewDescriptor)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Adds the given filter to this viewer, and triggers refiltering and
	 * resorting of the elements. If you want to add more than one filter
	 * consider using {@link StructuredViewer#setFilters(ViewerFilter[])}.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see StructuredViewer#setFilters(ViewerFilter[])
	 */
	public void addFilter(EEFViewerFilter filter) {
		if (filters == null) {
			filters = Lists.newArrayList();
		}
		filters.add(filter);
		refresh();
	}

	/**
	 * Removes the given filter from this viewer, and triggers refiltering and
	 * resorting of the elements if required. Has no effect if the identical
	 * filter is not registered. If you want to remove more than one filter
	 * consider using {@link StructuredViewer#setFilters(ViewerFilter[])}.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see StructuredViewer#setFilters(ViewerFilter[])
	 */
	public void removeFilter(EEFViewerFilter filter) {
		Assert.isNotNull(filter);
		if (filters != null) {
			// Note: can't use List.remove(Object). Use identity comparison
			// instead.
			for (Iterator<EEFViewerFilter> i = filters.iterator(); i.hasNext();) {
				Object o = i.next();
				if (o == filter) {
					i.remove();
					refresh();
					if (filters.size() == 0) {
						filters = null;
					}
					return;
				}
			}
		}
	}

	/**
	 * Sets the filters, replacing any previous filters, and triggers
	 * refiltering and resorting of the elements.
	 * 
	 * @param filters an array of viewer filters
	 */
	public void setFilters(EEFViewerFilter[] filters) {
		if (filters.length == 0) {
			resetFilters();
		} else {
			this.filters = Lists.newArrayList(filters);
			refresh();
		}
	}
	
	/**
	 * Discards this viewer's filters and triggers refiltering and resorting of
	 * the elements.
	 */
	public void resetFilters() {
		if (filters != null) {
			filters = null;
			refresh();
		}
	}

}
