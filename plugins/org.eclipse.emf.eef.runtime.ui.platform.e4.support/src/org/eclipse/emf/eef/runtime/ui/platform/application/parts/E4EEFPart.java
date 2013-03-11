
package org.eclipse.emf.eef.runtime.ui.platform.application.parts;


import java.io.IOException;
import java.io.InputStream;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.platform.application.utils.EditingInput;
import org.eclipse.emf.eef.runtime.ui.platform.application.utils.impl.URIEditingInput;
import org.eclipse.emf.eef.runtime.ui.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.viewer.EEFViewer;
import org.eclipse.emf.eef.runtime.ui.viewer.filters.EEFViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class E4EEFPart {

	private EditingDomain editingDomain;
	private EEFViewer viewer;
	
	@Inject
	private MDirtyable dirty;

	@Inject
	private EEFServiceRegistry serviceRegistry;


	/**
	 * @param serviceRegistry
	 * @param parent
	 */
	@Inject
	public E4EEFPart(EEFServiceRegistry serviceRegistry, Composite parent) {
		viewer = new EEFViewer(parent, SWT.NONE);
		viewer.setContentProvider(new EEFContentProvider());

	}
	
	/**
	 * @return the input {@link PropertiesEditingContext} of the nested {@link EEFViewer}.
	 */
	public Object getInput() {
		return viewer.getInput();
	}

	/**
	 * Defines the new input of the nested {@link EEFViewer}.
	 * @param input the new input.
	 */
	public void setInput(EditingInput input) {
		editingDomain = input.getEditingDomain();
		CommandStack commandStack = editingDomain.getCommandStack();
		if (commandStack instanceof BasicCommandStack) {
			commandStack.addCommandStackListener(new EEFCommandStackListener((BasicCommandStack) commandStack, viewer.getControl(), dirty));
		}
		if (input instanceof URIEditingInput) {
			Resource resource = editingDomain.getResourceSet().getResource(((URIEditingInput) input).getUri(), true);
			EObject root = resource.getContents().get(0);
			PropertiesEditingContextFactory contextFactory = serviceRegistry.getService(PropertiesEditingContextFactory.class, root);
			//TODO: is the ED always an AFED ?
			PropertiesEditingContext editingContext = contextFactory.createPropertiesEditingContext((AdapterFactoryEditingDomain)editingDomain, root);
			viewer.setInput(editingContext);
		}
	}
	
	/**
	 * Adds a new filter in the nested {@link EEFViewer}.
	 * @param filter 
	 */
	public void addFilter(EEFViewerFilter filter) {
		viewer.addFilter(filter);
	}
	
	/**
	 * Removes a new filter in the nested {@link EEFViewer}.
	 * @param filter 
	 */
	public void removeFilter(EEFViewerFilter filter) {
		viewer.removeFilter(filter);
	}
	

	@Focus
	public void onFocus() {
		viewer.getControl().setFocus();
	}

	@Persist
	public void save() {
		if (editingDomain != null) {
			// Save only resources that have actually changed.
			//
			final Map<Object, Object> saveOptions = new HashMap<Object, Object>();
			saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
			
			Job job = new Job("Model Saving") {

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
				 */
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					// Save the resources to the file system.
					//
					boolean first = true;
					for (Resource resource : editingDomain.getResourceSet().getResources()) {
						if ((first || !resource.getContents().isEmpty() || isPersisted(resource)) && !editingDomain.isReadOnly(resource)) {
							try {
								resource.save(saveOptions);
							}
							catch (Exception exception) {
								return new Status(IStatus.ERROR, "org.eclipse.emf.eef.runtime.ui.platform.e4.support", "An error occured during model saving.", exception);
							}
							first = false;
						}
					}
					dirty.setDirty(false);
					return Status.OK_STATUS;
				}
				
			};
			
			job.schedule();

		}
	}

	private static class EEFCommandStackListener implements CommandStackListener {

		private BasicCommandStack commandStack;
		private Control control;
		private MDirtyable dirty;


		/**
		 * @param commandStack2
		 * @param control
		 * @param dirty
		 */
		public EEFCommandStackListener(BasicCommandStack commandStack, Control control, MDirtyable dirty) {
			this.commandStack = commandStack;
			this.control = control;
			this.dirty = dirty;
		}




		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.common.command.CommandStackListener#commandStackChanged(java.util.EventObject)
		 */
		public void commandStackChanged(EventObject event) {
			control.getDisplay().asyncExec(new Runnable() {
				public void run() {
					dirty.setDirty(commandStack.isSaveNeeded());
				}
			});
		}

	}


	/**
	 * This returns whether something has been persisted to the URI of the specified resource.
	 * The implementation uses the URI converter from the editor's resource set to try to open an input stream. 
	 */
	protected boolean isPersisted(Resource resource) {
		boolean result = false;
		try {
			InputStream stream = editingDomain.getResourceSet().getURIConverter().createInputStream(resource.getURI());
			if (stream != null) {
				result = true;
				stream.close();
			}
		}
		catch (IOException e) {
			// Ignore
		}
		return result;
	}
}

