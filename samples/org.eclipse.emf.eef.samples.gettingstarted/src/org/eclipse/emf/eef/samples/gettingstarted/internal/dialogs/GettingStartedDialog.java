/*******************************************************************************
 * Copyright (c) 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.samples.gettingstarted.internal.dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.eef.samples.gettingstarted.actions.EEFGettingStartedAction;
import org.eclipse.emf.eef.samples.gettingstarted.internal.actions.showingmodel.ShowingAModelWithEEF;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class GettingStartedDialog extends Dialog {
	
	private List<EEFGettingStartedAction> actions;
	private DialogSelectionListener listener;

	public GettingStartedDialog(Shell parentShell) {
		super(parentShell);
		listener = new DialogSelectionListener();
		
		actions = new ArrayList<EEFGettingStartedAction>();
		actions.add(new ShowingAModelWithEEF());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite contents = (Composite) super.createDialogArea(parent);
		GridLayout layout = (GridLayout) contents.getLayout();
		layout.numColumns = 1;
		layout.makeColumnsEqualWidth = false;
		Label label = new Label(contents, SWT.NONE);
		label.setText("Choose the action to execute:");
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		for (EEFGettingStartedAction action : actions) {
			Button actionButton = new Button(contents, SWT.PUSH);
			actionButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			actionButton.setText(action.getTitle());
			actionButton.addSelectionListener(listener);
			listener.register(action).on(actionButton);
		}
		return contents;
	}
	
	private static final class DialogSelectionListener extends SelectionAdapter {
		
		private Map<Control, EEFGettingStartedAction> mapping;

		public DialogSelectionListener() {
			mapping = new HashMap<Control, EEFGettingStartedAction>();
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			EEFGettingStartedAction action = mapping.get(e.widget);
			if (action != null)  {
				action.execute();
			}
		}
		
		public ActionMark register(EEFGettingStartedAction action) {
			return new ActionMark(mapping, action);
		}
		
		private static final class ActionMark {
			
			private Map<Control, EEFGettingStartedAction> mapping;
			private EEFGettingStartedAction action;

			private ActionMark(Map<Control, EEFGettingStartedAction> mapping, EEFGettingStartedAction action) {
				this.mapping = mapping;
				this.action = action;
			}
			
			public void on(Control control) {
				mapping.put(control, action);
			}
			
		}
		
	}


}
