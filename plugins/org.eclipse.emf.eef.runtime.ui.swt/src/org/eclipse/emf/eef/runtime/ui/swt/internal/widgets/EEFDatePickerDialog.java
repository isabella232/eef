/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.ui.swt.internal.widgets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFDatePickerDialog extends Dialog {

	private String title;
	private Date date;
	private DateTime calendar;
	private DateTime time;
	private Date selection;
	
	/**
	 * @param parentShell
	 */
	public EEFDatePickerDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (title != null) {
			newShell.setText(title);
		}
		newShell.setSize(UIConstants.EEF_SELECTION_DIALOG_WIDTH, UIConstants.EEF_SELECTION_DIALOG_HEIGHT);
	}
		
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Defines the default date of the dialog;
	 * @param date default {@link Date}.
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	protected boolean isResizable() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite control = new Composite(parent, SWT.NONE);
		GridData fillBothData = new GridData(GridData.FILL_BOTH);
		control.setLayoutData(fillBothData);
		control.setLayout(new GridLayout());
		Label message = new Label(control, SWT.NONE);
		message.setText("Select the date:");
		Calendar calendarUtil = GregorianCalendar.getInstance();
		if (date != null) {
			calendarUtil.setTime(date);
		} else {
			calendarUtil.setTime(GregorianCalendar.getInstance().getTime());
		}
		calendar = new DateTime (control, SWT.CALENDAR | SWT.BORDER);
		calendar.setDate(calendarUtil.get(Calendar.YEAR), calendarUtil.get(Calendar.MONTH), calendarUtil.get(Calendar.DAY_OF_MONTH));
		calendar.setTime(calendarUtil.get(Calendar.HOUR_OF_DAY), calendarUtil.get(Calendar.MINUTE), calendarUtil.get(Calendar.SECOND));
		calendar.setLayoutData(fillBothData);
		calendar.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateSelection();
			}
			
			
		});
		time = new DateTime (control, SWT.TIME | SWT.SHORT);
		time.setDate(calendarUtil.get(Calendar.YEAR), calendarUtil.get(Calendar.MONTH), calendarUtil.get(Calendar.DAY_OF_MONTH));
		time.setTime(calendarUtil.get(Calendar.HOUR_OF_DAY), calendarUtil.get(Calendar.MINUTE), calendarUtil.get(Calendar.SECOND));
		time.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		time.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateSelection();
			}
			
			
		});
		return control;
	}
	
	private void updateSelection() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
		StringBuilder builder = new StringBuilder();
		builder.append(calendar.getYear());
		builder.append('/');
		builder.append(calendar.getMonth() + 1);
		builder.append('/');
		builder.append(calendar.getDay());
		builder.append('/');
		builder.append(time.getHours());
		builder.append('/');
		builder.append(time.getMinutes());
		builder.append('/');
		builder.append(time.getSeconds());
		try {
			selection = format.parse(builder.toString());
		} catch (ParseException e) {
			//TODO: log
			selection = null;
		}
	}
	

	/**
	 * @return
	 */
	public Date getSelection() {
		return selection;
	}

}
