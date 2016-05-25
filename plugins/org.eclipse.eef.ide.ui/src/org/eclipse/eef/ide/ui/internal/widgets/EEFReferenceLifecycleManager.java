/*******************************************************************************
 * Copyright (c) 2015, 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.eef.ide.ui.internal.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.eef.EEFReferenceDescription;
import org.eclipse.eef.EEFWidgetAction;
import org.eclipse.eef.EEFWidgetDescription;
import org.eclipse.eef.common.api.utils.Util;
import org.eclipse.eef.common.ui.api.EEFWidgetFactory;
import org.eclipse.eef.common.ui.api.IEEFFormContainer;
import org.eclipse.eef.core.api.EEFExpressionUtils;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.core.api.controllers.EEFControllersFactory;
import org.eclipse.eef.core.api.controllers.IConsumer;
import org.eclipse.eef.core.api.controllers.IEEFReferenceController;
import org.eclipse.eef.core.api.controllers.IEEFWidgetController;
import org.eclipse.eef.core.api.utils.EvalFactory;
import org.eclipse.eef.ide.ui.api.widgets.AbstractEEFWidgetLifecycleManager;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 * This class will be used in order to manage the lifecycle of a reference widget.
 *
 * @author mbats
 */
public class EEFReferenceLifecycleManager extends AbstractEEFWidgetLifecycleManager {

	/**
	 * Default height.
	 */
	private static final int DEFAULT_HEIGHT = 34;

	/**
	 * The description.
	 */
	private EEFReferenceDescription description;

	/**
	 * The text.
	 */
	private Hyperlink hyperlink;

	/**
	 * The text.
	 */
	private Label text;

	/**
	 * The action buttons.
	 */
	private List<ActionButton> actionButtons = new ArrayList<ActionButton>();

	/**
	 * The controller.
	 */
	private IEEFReferenceController controller;

	/**
	 * The listener on the text.
	 */
	private IHyperlinkListener onClickListener;

	/**
	 * The widget factory.
	 */
	private EEFWidgetFactory widgetFactory;

	/**
	 * The default background color of the text field.
	 */
	private Color defaultBackgroundColor;

	/**
	 * The constructor.
	 *
	 * @param description
	 *            The description
	 * @param variableManager
	 *            The variable manager
	 * @param interpreter
	 *            The interpreter
	 * @param contextAdapter
	 *            The editing context adapter
	 */
	public EEFReferenceLifecycleManager(EEFReferenceDescription description, IVariableManager variableManager, IInterpreter interpreter,
			EditingContextAdapter contextAdapter) {
		super(variableManager, interpreter, contextAdapter);
		this.description = description;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.eef.ide.ui.api.widgets.AbstractEEFWidgetLifecycleManager#createMainControl(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.eef.common.ui.api.IEEFFormContainer)
	 */
	@Override
	protected void createMainControl(Composite parent, IEEFFormContainer formContainer) {
		widgetFactory = formContainer.getWidgetFactory();
		defaultBackgroundColor = parent.getBackground();

		// this is the parent composite
		Composite reference = widgetFactory.createFlatFormComposite(parent);
		GridLayout layout = new GridLayout(2, false);
		reference.setLayout(layout);

		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		reference.setLayoutData(gridData);

		this.createSingleValuedReferenceWidget(reference);
		this.createWidgetActionButtons(reference);

		widgetFactory.paintBordersFor(parent);

		this.controller = new EEFControllersFactory().createReferenceController(this.description, this.variableManager, this.interpreter,
				this.contextAdapter);
	}

	/**
	 * Create widget action buttons.
	 *
	 * @param parent
	 *            The parent composite
	 */
	private void createWidgetActionButtons(Composite parent) {
		Composite buttons = widgetFactory.createFlatFormComposite(parent);

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		buttons.setLayoutData(gridData);

		buttons.setLayout(new GridLayout(this.description.getActions().size(), true));

		// Buttons are visible only if an action is defined
		for (EEFWidgetAction action : this.description.getActions()) {
			ActionButton actionButton = new ActionButton(action, buttons, widgetFactory, this.interpreter, this.variableManager);
			actionButtons.add(actionButton);
		}

	}

	/**
	 * Create a single valued reference widget : a text field or a label field if the onclick expression exists.
	 *
	 * @param parent
	 *            The parent composite
	 */
	private void createSingleValuedReferenceWidget(Composite parent) {
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;

		// Use hyperlink if the onclick expression exists
		final int clientWidth = parent.getClientArea().width;
		if (!Util.isBlank(this.description.getOnClickExpression())) {
			this.hyperlink = widgetFactory.createHyperlink(parent, "", SWT.NONE); //$NON-NLS-1$
			hyperlink.setLayoutData(gridData);
			hyperlink.setSize(clientWidth, DEFAULT_HEIGHT);
		} else {
			this.text = widgetFactory.createLabel(parent, "", SWT.NONE); //$NON-NLS-1$
			text.setLayoutData(gridData);
			text.setSize(clientWidth, DEFAULT_HEIGHT);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.eef.ide.ui.internal.widgets.AbstractEEFWidgetLifecycleManager#getController()
	 */
	@Override
	protected IEEFWidgetController getController() {
		return this.controller;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.eef.ide.ui.api.widgets.AbstractEEFWidgetLifecycleManager#aboutToBeShown()
	 */
	@Override
	public void aboutToBeShown() {
		super.aboutToBeShown();
		if (this.hyperlink != null) {
			this.onClickListener = new EEFReferenceHyperlinkListener(this.controller);
			this.hyperlink.addHyperlinkListener(this.onClickListener);
		}

		this.controller.onNewValue(new IConsumer<Object>() {
			@Override
			public void apply(Object value) {
				if (value == null) {
					return;
				}
				EEFReferenceLifecycleManager.this.setSingleValuedReference(value);
			}
		});

		for (final ActionButton actionButton : actionButtons) {
			SelectionAdapter selectionListener = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					List<Object> selections = new ArrayList<Object>();
					if (EEFReferenceLifecycleManager.this.hyperlink != null) {
						selections.add(EEFReferenceLifecycleManager.this.hyperlink.getData());
					} else {
						selections.add(EEFReferenceLifecycleManager.this.text.getData());
					}
					controller.action(actionButton.getAction(), selections);
				}
			};

			actionButton.addSelectionListener(selectionListener);
		}
	}

	/**
	 * Set single valued reference.
	 *
	 * @param value
	 *            Value to set
	 */
	private void setSingleValuedReference(Object value) {
		String expression = description.getDisplayExpression();

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.putAll(this.variableManager.getVariables());
		variables.put(EEFExpressionUtils.EEFReference.VALUE, value);
		String display = EvalFactory.of(EEFReferenceLifecycleManager.this.interpreter, variables).logIfInvalidType(String.class).evaluate(expression);

		if (!Util.isBlank(display)) {
			if (hyperlink != null && !hyperlink.isDisposed() && !(hyperlink.getText() != null && hyperlink.getText().equals(value))) {
				hyperlink.setText(display);
				hyperlink.setData(value);
				if (!hyperlink.isEnabled() && this.isEnabled()) {
					hyperlink.setEnabled(true);
				}
			} else if (text != null && !text.isDisposed() && !(text.getText() != null && text.getText().equals(value))) {
				text.setText(display);
				text.setData(value);
				if (!text.isEnabled() && this.isEnabled()) {
					text.setEnabled(true);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.eef.ide.ui.internal.widgets.AbstractEEFWidgetLifecycleManager#getValidationControl()
	 */
	@Override
	protected Control getValidationControl() {
		if (this.hyperlink != null) {
			return this.hyperlink;
		}
		return this.text;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.eef.ide.ui.internal.widgets.AbstractEEFWidgetLifecycleManager#aboutToBeHidden()
	 */
	@Override
	public void aboutToBeHidden() {
		super.aboutToBeHidden();

		if (this.hyperlink != null && !this.hyperlink.isDisposed()) {
			this.hyperlink.removeHyperlinkListener(this.onClickListener);
		}

		for (ActionButton actionButton : this.actionButtons) {
			actionButton.removeSelectionListener();
		}

		this.controller.removeNewValueConsumer();
		this.actionButtons.clear();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.eef.ide.ui.internal.widgets.AbstractEEFWidgetLifecycleManager#getWidgetDescription()
	 */
	@Override
	protected EEFWidgetDescription getWidgetDescription() {
		return this.description;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.eef.ide.ui.api.widgets.AbstractEEFWidgetLifecycleManager#getLabelVerticalAlignment()
	 */
	@Override
	protected int getLabelVerticalAlignment() {
		if (!description.isMultiple()) {
			return GridData.VERTICAL_ALIGN_CENTER;
		}
		return super.getLabelVerticalAlignment();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.eef.ide.ui.api.widgets.AbstractEEFWidgetLifecycleManager#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		if (this.text != null) {
			this.text.setEnabled(isEnabled());
			this.text.setBackground(getBackgroundColor());
		} else if (this.hyperlink != null) {
			this.hyperlink.setEnabled(isEnabled());
			this.hyperlink.setBackground(getBackgroundColor());
		}

		for (ActionButton actionButton : this.actionButtons) {
			actionButton.setEnabled(this.isEnabled());
		}
	}

	/**
	 * Get the background color according to the current valid style.
	 *
	 * @return The background color to use in the text field.
	 */
	private Color getBackgroundColor() {
		Color color = defaultBackgroundColor;
		if (!isEnabled()) {
			color = widgetFactory.getColors().getInactiveBackground();
		}
		return color;
	}
}
