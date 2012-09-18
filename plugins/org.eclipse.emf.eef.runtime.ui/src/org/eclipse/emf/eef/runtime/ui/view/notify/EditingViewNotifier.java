/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.notify;

import java.util.Map;

import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotification;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifier;
import org.eclipse.emf.eef.runtime.view.notify.EEFPropertyNotification;
import org.eclipse.emf.eef.runtime.view.notify.PropertiesEditingMessageManager;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;

import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public final class EditingViewNotifier implements EEFNotifier {

	private PropertiesEditingView view;
	private Map<Object, ControlDecoration> decorations;
	private ControlDecoration viewDecoration;

	/**
	 * @param view
	 */
	public EditingViewNotifier(PropertiesEditingView view) {
		this.view = view;
		decorations = Maps.newHashMap();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#notify(org.eclipse.emf.eef.runtime.view.notify.EEFNotification)
	 */
	public void notify(final EEFNotification notification) {
		if (notification instanceof EEFPropertyNotification) {
			view.getViewService().executeUIRunnable(new AddDecorationOnEditor((EEFPropertyNotification) notification));
		} else {
			view.getViewService().executeUIRunnable(new AddDecorationOnView(notification));
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#clearViewNotification()
	 */
	public void clearViewNotification() {
		view.getViewService().executeUIRunnable(new RemoveDecorationOnView());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#clearEditorNotification(java.lang.Object)
	 */
	public void clearEditorNotification(Object editor) {
		view.getViewService().executeUIRunnable(new RemoveDecorationOnEditor(editor));
	}

	private class AbstractAddDecoration {

		protected ControlDecoration decorateControl(final Control control, final EEFNotification notification) {
			ControlDecoration decoration = null;
			Image image = null;
			if (notification.getKind() == EEFNotification.ERROR) {
				image = view.getViewSettings().getErrorDecorationImage();
			} else if (notification.getKind() == EEFNotification.WARNING) {
				image = view.getViewSettings().getWarningDecorationImage();	
			}
			if (image != null) {
				final int position = view.getViewSettings().getDecoratorPositioning();
				decoration = new ControlDecoration(control, position);
				decoration.setDescriptionText(notification.getMessage());
				decoration.setImage(image);
			}
			return decoration;
		}

	}

	private final class AddDecorationOnEditor extends AbstractAddDecoration implements Runnable {

		private EEFPropertyNotification notification;

		public AddDecorationOnEditor(EEFPropertyNotification notification) {
			this.notification = notification;
		}

		/**
		 * {@inheritDoc}
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			Object editor = ((EEFPropertyNotification) notification).getEditor();
			PropertyEditor propertyEditor = view.getPropertyEditor(editor);
			final Control control = propertyEditor.getPropertyEditorViewer().getViewer().getControl();
			ControlDecoration decoration = decorateControl(control, notification);
			if (decoration != null) {
				decorations.put(editor, decoration);
			}
		}

	}

	private final class AddDecorationOnView extends AbstractAddDecoration implements Runnable {

		private EEFNotification notification;

		public AddDecorationOnView(EEFNotification notification) {
			this.notification = notification;
		}

		/**
		 * {@inheritDoc}
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			ContextOptions options = view.getEditingComponent().getEditingContext().getOptions();
			if (options.specificMessageManagerDesigned()) {
				PropertiesEditingMessageManager messageManager = options.getMessageManager();
				messageManager.processMessage(notification);
			} else {
				viewDecoration = decorateControl(view.getContents(), notification);
			}
		}

	}

	private final class RemoveDecorationOnEditor implements Runnable {

		private Object editor;

		public RemoveDecorationOnEditor(Object editor) {
			this.editor = editor;
		}

		/**
		 * {@inheritDoc}
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			if (decorations.get(editor) != null) {
				decorations.get(editor).dispose();
				decorations.remove(editor);
				view.getPropertyEditor(editor).getPropertyEditorViewer().getViewer().getControl().getParent().redraw();
			}
		}
	}

	private final class RemoveDecorationOnView implements Runnable {

		/**
		 * {@inheritDoc}
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			ContextOptions options = view.getEditingComponent().getEditingContext().getOptions();
			if (options.specificMessageManagerDesigned()) {
				PropertiesEditingMessageManager messageManager = options.getMessageManager();
				messageManager.clearMessage();
			} else {
				if (viewDecoration != null) {
					viewDecoration.dispose();
					view.getContents().getParent().redraw();
				}
			}
		}
	}
}
