/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.emf.eef.runtime.ui.swt.internal.util;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.util.impl.ViewServiceImpl;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Text;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class SWTViewServiceImpl extends ViewServiceImpl implements SWTViewService, DefaultService {
		
	protected static final String EMPTY_STRING = ""; //$NON-NLS-1$
	
	/**
	 * Image registry key for help image (value <code>"dialog_help_image"</code> ).
	 */
	public static final String DLG_IMG_HELP = "dialog_help_image"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService#createLabel(org.eclipse.swt.widgets.Composite, java.lang.Object, java.lang.String)
	 */
	public Label createLabel(Composite parent, Object editor, String alternate) {
		String text = getDescription(editor, alternate);
		if (!text.endsWith(": ") && !text.endsWith(":")) {
			text += ": ";
		}
		Label label;
		label = new Label(parent, SWT.NONE);
		label.setText(text);
		EStructuralFeature associatedFeature = feature(editor);
		if (associatedFeature != null && associatedFeature.isRequired()) {
			label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		}
		return label;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService#createHelpButton(org.eclipse.swt.widgets.Composite, java.lang.Object)
	 */
	public Control createHelpButton(Composite parent, Object editor ) {
		//To manage in future
//		String helpID = null;
		String alternate = getHelpContent(editor);
		Image image = JFaceResources.getImage(DLG_IMG_HELP);
//		if (helpID != null && !EMPTY_STRING.equals(helpID)) { //$NON-NLS-1$
//			ToolBar result = new ToolBar(parent, SWT.FLAT | SWT.NO_FOCUS);
//			((GridLayout)parent.getLayout()).numColumns++;
//			result.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
//			ToolItem item = new ToolItem(result, SWT.NONE);
//			item.setImage(image);
//			if (alternate != null && !EMPTY_STRING.equals(alternate)) //$NON-NLS-1$
//				item.setToolTipText(alternate);
//			return result;
//		} else {
			Label result = new Label(parent, SWT.NONE);
			if (alternate != null && !EMPTY_STRING.equals(alternate)) { //$NON-NLS-1$
				result.setImage(image);
				result.setToolTipText(alternate);
			}
			return result;
//		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService#createScrollableText(org.eclipse.swt.widgets.Composite, int)
	 */
	public Text createScrollableText(Composite parent, int styles) {
		final Text text = new Text(parent, styles);

		// If this text has no scroll bars, simply return it.
		if ((styles & (SWT.H_SCROLL | SWT.V_SCROLL)) == 0) {
			return text;
		}

		// Otherwise, set up its listeners
		setUpScrollableListener(text);

		return text;
	}

	/**
	 * Sets up the listeners allowing us to hide the scroll bars of the given scrollable when they are not
	 * needed.
	 * 
	 * @param scrollable
	 *            The scrollable widget to setup.
	 */
	private void setUpScrollableListener(final Scrollable scrollable) {
		final ControlListener resizeListener = new ScrollableResizeListener(scrollable);
		scrollable.addControlListener(resizeListener);

		final ModifyListener modifyListener = new ScrollableModifyListener(scrollable);
		if (scrollable instanceof Text) {
			((Text)scrollable).addModifyListener(modifyListener);
		} else if (scrollable instanceof StyledText) {
			((StyledText)scrollable).addModifyListener(modifyListener);
		}

		scrollable.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				scrollable.removeControlListener(resizeListener);
				if (scrollable instanceof Text) {
					((Text)scrollable).removeModifyListener(modifyListener);
				} else if (scrollable instanceof StyledText) {
					((StyledText)scrollable).removeModifyListener(modifyListener);
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService#setID(org.eclipse.swt.widgets.Control, java.lang.Object)
	 */
	public void setID(Control widget, Object value) {
		if (widget != null)
			widget.setData(EEF_WIDGET_ID_KEY, value);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService#getID(org.eclipse.swt.widgets.Control)
	 */
	public Object getID(Control widget) {
		if (widget != null)
			return widget.getData(EEF_WIDGET_ID_KEY);
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService#setEEFtype(org.eclipse.swt.widgets.Control, java.lang.String)
	 */
	public void setEEFtype(Control widget, String value) {
		if (widget != null)
			widget.setData(EEF_WIDGET_TYPE_KEY, value);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService#getEEFType(org.eclipse.swt.widgets.Control)
	 */
	public String getEEFType(Control widget) {
		if (widget != null) {
			Object data = widget.getData(EEF_WIDGET_ID_KEY);
			if (data instanceof String)
				return (String)data;
		}
		return UNKNOW_EEF_TYPE;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService#executeSyncUIRunnable(org.eclipse.swt.widgets.Display, java.lang.Runnable)
	 */
	public void executeSyncUIRunnable(Display display, Runnable job) {
		if (display != null) {
			display.syncExec(job);
		} else {
			Display.getCurrent().syncExec(job);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService#executeAsyncUIRunnable(org.eclipse.swt.widgets.Display, java.lang.Runnable)
	 */
	public void executeAsyncUIRunnable(Display display, Runnable job) {
		if (display != null) {
			display.asyncExec(job);
		} else {
			Display.getCurrent().asyncExec(job);
		}
	}

	private static abstract class AbstractScrollableListener {
		
		/**
		 * Computes the size of the text displayed by the given {@link Text} widget.
		 * 
		 * @param widget
		 *            The widget on which is displayed the text.
		 * @param text
		 *            The actual displayed text.
		 * @return The actual size of the {@link Text} widget's content.
		 */
		protected static Point computeTextSize(Control widget, String text) {
			String[] lines = text.split("\r\n|\n|\r"); //$NON-NLS-1$

			String longestLine = ""; //$NON-NLS-1$
			if (lines.length > 0) {
				longestLine = lines[0];
				for (int i = 0; i < lines.length; i++) {
					if (lines[i].length() > longestLine.length()) {
						longestLine = lines[i];
					}
				}
			}
			GC gc = new GC(widget);
			gc.setFont(widget.getFont());
			final int textWidth = gc.stringExtent(longestLine).x;
			final int textHeight = gc.stringExtent("W").y * lines.length; //$NON-NLS-1$
			gc.dispose();

			return new Point(textWidth, textHeight);
		}

	}
	
	
	/**
	 * This will be used as the resize listener for our scrollable text controls in order to determine whether
	 * the scroll bars are needed.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	protected static class ScrollableResizeListener extends AbstractScrollableListener implements ControlListener  {
		/** Keeps a reference to the last size we computed. */
		private Point lastSize;

		/** Keeps a reference to the last text we computed a size for. */
		private String lastText;

		/** The {@link Scrollable} widget against which this listener has been registered. */
		private final Scrollable text;

		/**
		 * Instantiates our resize listener for the given text widget.
		 * 
		 * @param text
		 *            The text widget to listen to.
		 */
		public ScrollableResizeListener(Scrollable text) {
			this.text = text;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.ControlAdapter#controlResized(org.eclipse.swt.events.ControlEvent)
		 */
		public void controlResized(ControlEvent e) {
			final Rectangle clientArea = text.getClientArea();
			final String currentText;
			if (text instanceof Text) {
				currentText = ((Text)text).getText();
			} else if (text instanceof StyledText) {
				currentText = ((StyledText)text).getText();
			} else {
				return;
			}
			Point textSize = lastSize;
			if (textSize == null || !lastText.equals(currentText)) {
				textSize = computeTextSize(text, currentText);
				lastText = currentText;
				lastSize = textSize;
			}
			if (clientArea.width > textSize.x && text.getHorizontalBar() != null) {
				text.getHorizontalBar().setVisible(false);
			} else if (text.getHorizontalBar() != null) {
				text.getHorizontalBar().setVisible(true);
			}
			if (clientArea.height > textSize.y && text.getVerticalBar() != null) {
				text.getVerticalBar().setVisible(false);
			} else if (text.getVerticalBar() != null) {
				text.getVerticalBar().setVisible(true);
			}
		}

		public void controlMoved(ControlEvent e) {
		}
	}

	/**
	 * This will be used as the resize listener for our scrollable text controls in order to determine whether
	 * the scroll bars are needed.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	protected static class ScrollableModifyListener extends AbstractScrollableListener implements ModifyListener {
		/** The {@link Scrollable} widget against which this listener has been registered. */
		private final Scrollable text;

		/**
		 * Instantiates our modify listener for the given text widget.
		 * 
		 * @param text
		 *            The text widget to listen to.
		 */
		public ScrollableModifyListener(Scrollable text) {
			this.text = text;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
		 */
		public void modifyText(ModifyEvent e) {
			final Rectangle clientArea = text.getClientArea();
			final String currentText;
			if (text instanceof Text) {
				currentText = ((Text)text).getText();
			} else if (text instanceof StyledText) {
				currentText = ((StyledText)text).getText();
			} else {
				return;
			}
			final Point textSize = computeTextSize(text, currentText);
			if (clientArea.width > textSize.x && text.getHorizontalBar() != null) {
				text.getHorizontalBar().setVisible(false);
			} else if (text.getHorizontalBar() != null) {
				text.getHorizontalBar().setVisible(true);
			}
			if (clientArea.height > textSize.y && text.getVerticalBar() != null) {
				text.getVerticalBar().setVisible(false);
			} else if (text.getVerticalBar() != null) {
				text.getVerticalBar().setVisible(true);
			}
		}
	}

}

