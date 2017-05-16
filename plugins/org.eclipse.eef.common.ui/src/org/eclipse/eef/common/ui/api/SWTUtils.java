/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.eef.common.ui.api;

import java.util.function.Consumer;

import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;

/**
 * Utility methods for SWT.
 *
 * @author sbegaudeau
 */
public final class SWTUtils {

	/**
	 * The constructor.
	 */
	private SWTUtils() {
		// Prevent instantiation
	}

	/**
	 * Static helper method to create a selection listener for the {@link #widgetSelected(SelectionEvent event)}) method
	 * with a lambda expression.
	 *
	 * @param consumer
	 *            the consumer of the event
	 * @return SelectionListener
	 */
	public static SelectionListener widgetSelectedAdapter(Consumer<SelectionEvent> consumer) {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				consumer.accept(event);
			}
		};
	}

	/**
	 * Static helper method to create a focus listener for the {@link #focusLost(FocusEvent event)}) method with a
	 * lambda expression.
	 *
	 * @param consumer
	 *            the consumer of the event
	 * @return FocusListener
	 */
	public static FocusListener focusLostAdapter(Consumer<FocusEvent> consumer) {
		return new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent event) {
				consumer.accept(event);
			}
		};
	}

	/**
	 * Static helper method to create a key listener for the {@link #keyReleased(KeyEvent event)}) method with a lambda
	 * expression.
	 *
	 * @param consumer
	 *            the consumer of the event
	 * @return KeyListener
	 */
	public static KeyListener keyReleasedAdapter(Consumer<KeyEvent> consumer) {
		return new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				consumer.accept(event);
			}
		};
	}

	/**
	 * Static helper method to create an expansion listener for {@link #expansionStateChanged(ExpansionEvent event)})
	 * method with a lambda expression.
	 * 
	 * @param consumer
	 *            The consumer of the event
	 * @return IExpansionListener
	 */
	public static IExpansionListener expansionListenerAdapter(Consumer<ExpansionEvent> consumer) {
		return new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent event) {
				consumer.accept(event);
			}
		};
	}
}
