package org.eclipse.emf.example.eef.application.fx;

import javafx.scene.layout.BorderPane;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.fx.viewer.FXViewer;
import org.eclipse.emf.samples.conference.ConferenceFactory;
import org.eclipse.emf.samples.conference.Participant;

@SuppressWarnings("restriction")
public class FirstFXpart {


	@Inject
	public FirstFXpart(BorderPane parent, final MApplication application, EEFServiceRegistry serviceRegistry) {
		
		Participant eObj = ConferenceFactory.eINSTANCE.createParticipant();
		eObj.setFirstname("John");
		eObj.setLastname("Doe");
		eObj.setIsRegistered(true);
//		Conference eObj = ConferenceFactory.eINSTANCE.createConference();
//		eObj.setName("DeathFest");
//		eObj.setOverview("A fest about the death !!!!!");
		AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE), new BasicCommandStack());
		PropertiesEditingContextFactory contextFactory = serviceRegistry.getService(PropertiesEditingContextFactory.class, eObj);
		PropertiesEditingContext context = contextFactory.createPropertiesEditingContext(editingDomain, eObj);
		FXViewer viewer = new FXViewer();
		viewer.setInput(context);
		parent.setCenter(viewer.getControl());
	
	}
	
}
