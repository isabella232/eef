/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.example.eef.application.handlers;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Named;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.platform.parts.E4EEFPart;
import org.eclipse.emf.eef.runtime.ui.platform.utils.ApplicationModelBuilder;
import org.eclipse.emf.example.eef.application.ConferenceApplicationConstants;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class OpenHandler {

	@Execute
	public void execute(IEclipseContext context, EModelService modelService, EPartService partService, EEFServiceRegistry serviceRegistry, MApplication applicationModel, @Named(IServiceConstants.ACTIVE_SHELL) Shell shell) throws InvocationTargetException, InterruptedException {
		FileDialog dialog = new FileDialog(shell);
		dialog.setFilterExtensions(new String[] { "*.conference" });
		String path = dialog.open();
		if (path != null && !path.isEmpty()) {
			URI fileURI = URI.createFileURI(path);
			AdapterFactoryEditingDomain editingDomain = (AdapterFactoryEditingDomain) context.get(ConferenceApplicationConstants.EDITINGDOMAIN);
			if (editingDomain == null) {
				editingDomain = new AdapterFactoryEditingDomain(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE), new BasicCommandStack());
				context.set(ConferenceApplicationConstants.EDITINGDOMAIN, editingDomain);
			}
			Resource resource = editingDomain.getResourceSet().getResource(fileURI, true);
			if (resource == null || resource.getContents().isEmpty()) {
				// error
			} else {
				ApplicationModelBuilder builder = new ApplicationModelBuilder(applicationModel);
				builder.addEEFPartDescriptor();
				EObject root = resource.getContents().get(0);
				MPartStack partStack = (MPartStack) modelService.find("org.eclipse.emf.example.eef.application.partsatck", applicationModel);
				MPart mPart = partService.createPart(ApplicationModelBuilder.EEF_PART_DESCRIPTOR);
				partStack.getChildren().add(mPart);
				partService.showPart(mPart, PartState.ACTIVATE);
				E4EEFPart partImpl = (E4EEFPart) mPart.getObject();
				PropertiesEditingContextFactory contextFactory = serviceRegistry.getService(PropertiesEditingContextFactory.class, root);
				PropertiesEditingContext editingContext = contextFactory.createPropertiesEditingContext(editingDomain, root);
				partImpl.getViewer().setInput(editingContext);
			}
		}
		
	}
	
}
