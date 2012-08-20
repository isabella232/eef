/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ComposedViewHandlerProvider implements ViewHandlerProvider {
	
	private List<ViewHandlerProvider> providers;
	
	/**
	 * @param providers {@link ViewHandler}s to manage.
	 */
	private ComposedViewHandlerProvider(List<ViewHandlerProvider> providers) {
		this.providers = providers;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Object view) {
		for (ViewHandlerProvider provider : providers) {
			if (provider.serviceFor(view)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider#getHandler(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
	 */
	public ViewHandler<?> getHandler(PropertiesEditingComponent editingComponent, Object view) {
		for (ViewHandlerProvider provider : providers) {
			if (provider.serviceFor(view)) {
				return provider.getHandler(editingComponent, view);
			}
		}
		return null;
	}
	
	/**
	 * @return the composed {@link ViewHandlerProvider}.
	 */
	public List<ViewHandlerProvider> getProviders() {
		return providers;
	}

	/**
	 * Builder for {@link ComposedViewHandlerProvider}.
	 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
	 */
	public static final class Builder {
		
		private List<ViewHandlerProvider> handlers;


		/**
		 * Builder for {@link ComposedViewHandlerProvider}.
		 */
		public Builder() {
			handlers = new ArrayList<ViewHandlerProvider>();
		}
		
		/**
		 * @param handler {@link ViewHandlerProvider} to add.
		 * @return this builder.
		 */
		public Builder addHandler(ViewHandlerProvider handler) {
			handlers.add(handler);
			return this;
		}
		
		/**
		 * Add a collection of {@link ViewHandlerProvider} to this builder.
		 * This method use {@link Builder#addHandler(ViewHandlerProvider)}.
		 * @param specificViewHandlerProviders a collection of {@link ViewHandlerProvider} to add to this builder. 
		 * @return this builder.
		 */
		public Builder addAllHandlers(Collection<ViewHandlerProvider> specificViewHandlerProviders) {
			for (ViewHandlerProvider viewHandlerProvider : specificViewHandlerProviders) {
				addHandler(viewHandlerProvider);
			}
			return this;
		}

		/**
		 * @param handler {@link ViewHandlerProvider} to remove.
		 * @return this builder.
		 */
		public Builder removeHandler(ViewHandlerProvider handler) {
			handlers.remove(handler);
			return this;
		}
		
		/**
		 * Remove a collection of {@link ViewHandlerProvider} to this builder.
		 * This method use {@link Builder#removeHandler(ViewHandlerProvider)}.
		 * @param specificViewHandlerProviders a collection of {@link ViewHandlerProvider} to remove to this builder. 
		 * @return this builder.
		 */
		public Builder removeAllHandlers(Collection<ViewHandlerProvider> specificViewHandlerProviders) {
			for (ViewHandlerProvider viewHandlerProvider : specificViewHandlerProviders) {
				removeHandler(viewHandlerProvider);
			}
			return this;
		}

		/**
		 * @return the builded {@link ComposedViewHandlerProvider}.
		 */
		public ComposedViewHandlerProvider build() {
			return new ComposedViewHandlerProvider(handlers);
		}
		
	}

}
