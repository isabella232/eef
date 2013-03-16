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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.eef.runtime.ui.swt.e4.handlers.AbstractEEFOpenHandler;
import org.eclipse.emf.eef.runtime.ui.swt.e4.model.utils.ApplicationModelBuilder;
import org.eclipse.emf.eef.runtime.ui.swt.e4.parts.E4EEFPart;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.filters.ViewFilter;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewsRepository;
import org.eclipse.emf.example.eef.application.ConferenceApplicationConstants;
import org.eclipse.emf.example.eef.application.ConferenceManagerActivator;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class OpenHandler extends AbstractEEFOpenHandler {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e4.handlers.AbstractEEFOpenHandler#getElementContainerID()
	 */
	@Override
	protected String getElementContainerID() {
		return ConferenceApplicationConstants.MAIN_PARTSTACK;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e4.handlers.AbstractEEFOpenHandler#getFilterExtensions()
	 */
	@Override
	protected String[] getFilterExtensions() {
		return new String[] { "*.conference" };
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e4.handlers.AbstractEEFOpenHandler#configurePart(org.eclipse.e4.ui.workbench.modeling.EModelService, org.eclipse.e4.ui.model.application.MApplication, org.eclipse.e4.ui.model.application.ui.basic.MPart)
	 */
	@Override
	protected void preparePartCreation(EModelService modelService, MApplication applicationModel) {
		changePartCloseability(applicationModel, false);
	}


	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e4.handlers.AbstractEEFOpenHandler#configureCreatedPart(org.eclipse.e4.ui.workbench.modeling.EModelService, org.eclipse.e4.ui.model.application.MApplication, org.eclipse.e4.ui.model.application.ui.basic.MPart)
	 */
	@Override
	protected void configureCreatedPart(EModelService modelService, MApplication applicationModel, MPart mPart) {
		changePartCloseability(applicationModel, true);
		E4EEFPart partImpl = (E4EEFPart) mPart.getObject();
		Collection<String> selectedViews = new ArrayList<String>();
		selectedViews.add("Conference::Conference");
		partImpl.addFilter(new ViewFilter(selectedViews));
	}

	private MPartDescriptor getEEFPartDescriptor(MApplication applicationModel) {
		List<MPartDescriptor> descriptors = applicationModel.getDescriptors();
		MPartDescriptor descriptor = null;
		Iterator<MPartDescriptor> iterator = descriptors.iterator();
		while (descriptor == null && iterator.hasNext()) {
			MPartDescriptor mPartDescriptor = iterator.next();
			if (ApplicationModelBuilder.EEF_PART_DESCRIPTOR.equals(mPartDescriptor.getElementId())) {
				descriptor = mPartDescriptor;
			}
		}
		return descriptor;
	}

	/**
	 * @param applicationModel
	 * @param closeability
	 */
	private void changePartCloseability(MApplication applicationModel,
			boolean closeability) {
		MPartDescriptor descriptor = getEEFPartDescriptor(applicationModel);
		if (descriptor != null) {
			descriptor.setCloseable(closeability);
		}
	}
}
