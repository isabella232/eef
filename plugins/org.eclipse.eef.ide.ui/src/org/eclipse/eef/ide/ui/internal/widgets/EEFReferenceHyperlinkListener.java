/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.eef.ide.ui.internal.widgets;

import org.eclipse.eef.core.api.EEFExpressionUtils;
import org.eclipse.eef.core.api.controllers.IEEFReferenceController;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 * This class will be used to run the onClick expression once the used click on the hyperlink.
 *
 * @author sbegaudeau
 */
public class EEFReferenceHyperlinkListener implements IHyperlinkListener {

	/**
	 * The controller.
	 */
	private IEEFReferenceController controller;

	/**
	 * The constructor.
	 *
	 * @param controller
	 *            The controller
	 */
	public EEFReferenceHyperlinkListener(IEEFReferenceController controller) {
		this.controller = controller;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.forms.events.IHyperlinkListener#linkEntered(org.eclipse.ui.forms.events.HyperlinkEvent)
	 */
	@Override
	public void linkEntered(HyperlinkEvent event) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.forms.events.IHyperlinkListener#linkExited(org.eclipse.ui.forms.events.HyperlinkEvent)
	 */
	@Override
	public void linkExited(HyperlinkEvent event) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.forms.events.IHyperlinkListener#linkActivated(org.eclipse.ui.forms.events.HyperlinkEvent)
	 */
	@Override
	public void linkActivated(HyperlinkEvent event) {
		Hyperlink link = (Hyperlink) event.getSource();
		if (link != null) {
			Object element = link.getData();
			this.controller.onClick(element, EEFExpressionUtils.EEFReference.SINGLE_CLICK);
		}
	}

}
