package org.eclipse.emf.example.eef.application.fx;
import java.util.Collection;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.ui.fx.view.handlers.editingview.FXEditingViewHandler;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.samples.conference.ConferenceFactory;
import org.eclipse.emf.samples.conference.Participant;

@SuppressWarnings("restriction")
public class FirstFXpart {

	private Collection<ViewHandler<?>> viewHandlers;

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

		TabPane container = new TabPane();
		viewHandlers = context.getEditingComponent().createViewHandlers();
		PropertiesEditingComponent component = context.getEditingComponent();		
		for (ViewHandler<?> handler : viewHandlers) {
			if (handler instanceof FXEditingViewHandler) {
				FXEditingViewHandler propertiesEditingViewHandler = (FXEditingViewHandler) handler;
				View viewDescriptor = propertiesEditingViewHandler.getViewDescriptor();
				try {
					Tab tab = new Tab(viewDescriptor.getName());
					BorderPane tabContent = new BorderPane();
					PropertiesEditingView<Pane> view = propertiesEditingViewHandler.createView(component, tabContent);
					handler.initView(component);
					tab.setContent(tabContent);
					tab.setClosable(false);
					container.getTabs().add(tab);
				} catch (ViewConstructionException e) {
					EEFLogger logger = handler.getProvider().getServiceRegistry().getService(EEFLogger.class, this);
					logger.logError("org.eclipse.emf.eef.runtime.ui.swt", "An error occured during view creation.", e);
				}	
			}
		}
		
		parent.setCenter(container);
		
	}
	
}
