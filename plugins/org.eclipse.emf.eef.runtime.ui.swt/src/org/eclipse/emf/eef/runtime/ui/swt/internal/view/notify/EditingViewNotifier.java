/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.notify;

import java.util.Map;

import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotification;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifier;
import org.eclipse.emf.eef.runtime.view.notify.EEFPropertyNotification;
import org.eclipse.emf.eef.runtime.view.notify.PropertiesEditingMessageManager;
import org.eclipse.emf.eef.views.ViewElement;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingViewNotifier implements EEFNotifier {

	private Map<PropertiesEditingView<Composite>, DecorationSettings> decorationSettings;

	public EditingViewNotifier() {
		decorationSettings = Maps.newHashMap();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Object element) {
		return element instanceof PropertiesEditingView;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#notify(java.lang.Object, org.eclipse.emf.eef.runtime.view.notify.EEFNotification)
	 */
	public void notify(Object view, final EEFNotification notification) {
		if (view instanceof PropertiesEditingView) {
			@SuppressWarnings("unchecked")
			PropertiesEditingView<Composite> editingView = (PropertiesEditingView<Composite>) view;
			if (notification instanceof EEFPropertyNotification) {
				if (editingView.getViewService() instanceof SWTViewService) {
					((SWTViewService) editingView.getViewService()).executeAsyncUIRunnable(editingView.getContents().getDisplay(), new AddDecorationOnEditor(editingView, (EEFPropertyNotification) notification));
				}
			} else {
				if (editingView.getViewService() instanceof SWTViewService) {
					((SWTViewService) editingView.getViewService()).executeAsyncUIRunnable(editingView.getContents().getDisplay(), new AddDecorationOnView(editingView, notification));
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#clearViewNotification(java.lang.Object)
	 */
	public void clearViewNotification(Object view) {
		if (view instanceof PropertiesEditingView) {
			@SuppressWarnings("unchecked")
			PropertiesEditingView<Composite> editingView = (PropertiesEditingView<Composite>) view;
			if (editingView.getViewService() instanceof SWTViewService) {
				((SWTViewService) editingView.getViewService()).executeAsyncUIRunnable(editingView.getContents().getDisplay(), new RemoveDecorationOnView(editingView));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#clearEditorNotification(java.lang.Object, java.lang.Object)
	 */
	public void clearEditorNotification(Object view, Object editor) {
		if (view instanceof PropertiesEditingView) {
			@SuppressWarnings("unchecked")
			PropertiesEditingView<Composite> editingView = (PropertiesEditingView<Composite>) view;
			if (editor instanceof ViewElement) {
				if (editingView.getViewService() instanceof SWTViewService) {
					((SWTViewService) editingView.getViewService()).executeAsyncUIRunnable(editingView.getContents().getDisplay(), new RemoveDecorationOnEditor(editingView, (ViewElement) editor));
				}
			}
		}
	}

	private static class DecorationSettings {

		private Map<Object, ControlDecoration> decorations;
		private ControlDecoration viewDecoration;

		public DecorationSettings() {
			this.decorations = Maps.newHashMap();
			this.viewDecoration = null;
		}

	}

	private class AbstractAddDecoration {

		protected PropertiesEditingView<Composite> view;

		public AbstractAddDecoration(PropertiesEditingView<Composite> view) {
			this.view = view;
		}

		protected ControlDecoration decorateControl(final Control control, final EEFNotification notification) {
			final int position = EEFSWTConstants.DECORATOR_POSITIONING;
			ControlDecoration decoration = new ControlDecoration(control, position);
			updateDecoration(decoration, notification);
			return decoration;
		}

		protected ControlDecoration updateDecoration(final ControlDecoration decoration, final EEFNotification notification) {
			Image image = null;
			if (notification.getKind() == EEFNotification.ERROR) {
				image = EEFSWTConstants.ERROR_DECORATOR;
			} else if (notification.getKind() == EEFNotification.WARNING) {
				image = EEFSWTConstants.WARNING_DECORATOR;	
			} else if (notification.getKind() == EEFNotification.LOCK) {
				image = EEFSWTConstants.LOCK_DECORATOR;	
			}
			if (image != null) {
				decoration.setDescriptionText(notification.getMessage());
				decoration.setImage(image);
			}
			return decoration;
		}

	}

	private final class AddDecorationOnEditor extends AbstractAddDecoration implements Runnable {

		private EEFPropertyNotification notification;

		public AddDecorationOnEditor(PropertiesEditingView<Composite> editingView, EEFPropertyNotification notification) {
			super(editingView);
			this.notification = notification;
		}

		/**
		 * {@inheritDoc}
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			Object editor = ((EEFPropertyNotification) notification).getEditor();
			if (editor instanceof ViewElement) {
				PropertyEditor propertyEditor = view.getPropertyEditor((ViewElement) editor);
				Object viewer = propertyEditor.getPropertyEditorViewer().getViewer();
				if (viewer instanceof Viewer) {
					final Control control = ((Viewer) viewer).getControl();
					DecorationSettings settings = decorationSettings.get(view);
					if (settings != null) {
						ControlDecoration existingDecoration = settings.decorations.get(editor);
						if (existingDecoration != null) {
							updateDecoration(existingDecoration, notification);
						} else {
							ControlDecoration decoration = decorateControl(control, notification);
							if (decoration != null) {
								settings.decorations.put(editor, decoration);
							}
						}
					} else {
						ControlDecoration decoration = decorateControl(control, notification);
						if (decoration != null) {
							settings = new DecorationSettings();
							decorationSettings.put(view, settings);
							settings.decorations.put(editor, decoration);
						}
					}
				}
			}
		}

	}

	private final class AddDecorationOnView extends AbstractAddDecoration implements Runnable {

		private EEFNotification notification;

		public AddDecorationOnView(PropertiesEditingView<Composite> editingView, EEFNotification notification) {
			super(editingView);
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
				DecorationSettings settings = decorationSettings.get(view);
				if (settings == null) {
					settings = new DecorationSettings();
					decorationSettings.put(view, settings);
				}
				settings.viewDecoration = decorateControl(view.getContents(), notification);
			}
		}

	}

	private final class RemoveDecorationOnEditor implements Runnable {

		private PropertiesEditingView<Composite> view;
		private ViewElement editor;

		public RemoveDecorationOnEditor(PropertiesEditingView<Composite> editingView, ViewElement editor) {
			this.view = editingView;
			this.editor = editor;
		}

		/**
		 * {@inheritDoc}
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			DecorationSettings settings = decorationSettings.get(view);
			if (settings != null && settings.decorations.get(editor) != null) {
				settings.decorations.get(editor).dispose();
				settings.decorations.remove(editor);
				Object viewer = view.getPropertyEditor(editor).getPropertyEditorViewer().getViewer();
				if (viewer instanceof Viewer) {
					((Viewer) viewer).getControl().getParent().redraw();
				}
			}
		}
	}

	private final class RemoveDecorationOnView implements Runnable {

		private PropertiesEditingView<Composite> view;

		public RemoveDecorationOnView(PropertiesEditingView<Composite> editingView) {
			this.view = editingView;
		}

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
				DecorationSettings settings = decorationSettings.get(view);
				if (settings != null && settings.viewDecoration != null) {
					settings.viewDecoration.dispose();
					view.getContents().getParent().redraw();
				}
			}
		}
	}
}
