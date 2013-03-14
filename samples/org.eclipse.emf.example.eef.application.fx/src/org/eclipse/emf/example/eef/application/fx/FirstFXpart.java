package org.eclipse.emf.example.eef.application.fx;
import java.util.Collection;

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
import org.eclipse.emf.samples.conference.Conference;
import org.eclipse.emf.samples.conference.ConferenceFactory;

@SuppressWarnings("restriction")
public class FirstFXpart {

	private Collection<ViewHandler<?>> viewHandlers;

	@Inject
	public FirstFXpart(BorderPane parent, final MApplication application, EEFServiceRegistry serviceRegistry) {
		
//		GridPane center = new GridPane();
//		Label nameLabel = new Label("Name:");
//		GridPane.setConstraints(nameLabel, 1, 1);
//		final TextField name = new TextField();
//		GridPane.setConstraints(name, 2, 1);
//		name.setOnKeyReleased(new EventHandler<KeyEvent>() {
//
//			@Override
//			public void handle(KeyEvent event) {
//				System.out.println("Released - Text value: " + ((TextField)event.getSource()).getText());
//			}
//			
//		});
//		center.getChildren().addAll(nameLabel, name);
//		parent.setCenter(center);

		Conference conference = ConferenceFactory.eINSTANCE.createConference();
		conference.setName("EclipseCon 2013");
		AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE), new BasicCommandStack());
		PropertiesEditingContextFactory contextFactory = serviceRegistry.getService(PropertiesEditingContextFactory.class, conference);
		PropertiesEditingContext context = contextFactory.createPropertiesEditingContext(editingDomain, conference);
		viewHandlers = context.getEditingComponent().createViewHandlers();
		PropertiesEditingComponent component = context.getEditingComponent();
		for (ViewHandler<?> handler : viewHandlers) {
			if (handler instanceof FXEditingViewHandler) {
				FXEditingViewHandler propertiesEditingViewHandler = (FXEditingViewHandler) handler;
				View viewDescriptor = propertiesEditingViewHandler.getViewDescriptor();
				try {
					PropertiesEditingView<Pane> view = propertiesEditingViewHandler.createView(component, parent);
					handler.initView(component);
				} catch (ViewConstructionException e) {
					EEFLogger logger = handler.getProvider().getServiceRegistry().getService(EEFLogger.class, this);
					logger.logError("org.eclipse.emf.eef.runtime.ui.swt", "An error occured during view creation.", e);
				}	
			}
		}
	
	}
	
}
