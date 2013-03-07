 
package org.eclipse.emf.example.eef.application.parts;

import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.viewer.EEFViewer;
import org.eclipse.emf.samples.conference.ConferenceFactory;
import org.eclipse.emf.samples.conference.Participant;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class MainPart {
	
	private EEFServiceRegistry serviceRegistry;
	
	private ComposedAdapterFactory adapterFactory;
	private AdapterFactoryEditingDomain editingDomain;
	private EEFViewer viewer;


	@Inject
	public MainPart(EEFServiceRegistry serviceRegistry, Composite parent) {
		this.serviceRegistry = serviceRegistry;
		// Initializing EMF editing elements
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, new BasicCommandStack());
		
		// Initializing model
		Participant person = ConferenceFactory.eINSTANCE.createParticipant();
		
		// Initializing EEF editing context
		PropertiesEditingContextFactory contextFactory = this.serviceRegistry.getService(PropertiesEditingContextFactory.class, person);
		PropertiesEditingContext editingContext = contextFactory.createPropertiesEditingContext(editingDomain, person);
		
		viewer = new EEFViewer(parent, SWT.NONE);
		viewer.setContentProvider(new EEFContentProvider());
		viewer.setInput(editingContext);
	}

	@Focus
	public void onFocus() {
		//TODO Your code here
	}

}