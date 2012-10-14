/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.notify;

import java.util.Map;

import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFComponent;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotification;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifier;
import org.eclipse.emf.eef.runtime.view.notify.EEFPropertyNotification;
import org.eclipse.emf.eef.runtime.view.notify.PropertiesEditingMessageManager;
import org.eclipse.emf.eef.views.ViewElement;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;

import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public final class EditingViewNotifier extends AbstractEEFComponent implements EEFNotifier {

	private Map<PropertiesEditingView, DecorationSettings> decorationSettings;
	
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
			PropertiesEditingView editingView = (PropertiesEditingView) view;
			if (notification instanceof EEFPropertyNotification) {
				editingView.getViewService().executeUIRunnable(new AddDecorationOnEditor(editingView, (EEFPropertyNotification) notification));
			} else {
				editingView.getViewService().executeUIRunnable(new AddDecorationOnView(editingView, notification));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#clearViewNotification(java.lang.Object)
	 */
	public void clearViewNotification(Object view) {
		if (view instanceof PropertiesEditingView) {
			PropertiesEditingView editingView = (PropertiesEditingView) view;
			editingView.getViewService().executeUIRunnable(new RemoveDecorationOnView(editingView));
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#clearEditorNotification(java.lang.Object, java.lang.Object)
	 */
	public void clearEditorNotification(Object view, Object editor) {
		if (view instanceof PropertiesEditingView) {
			PropertiesEditingView editingView = (PropertiesEditingView) view;
			if (editor instanceof ViewElement) {
				editingView.getViewService().executeUIRunnable(new RemoveDecorationOnEditor(editingView, (ViewElement) editor));
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
		
		protected PropertiesEditingView view;
		
		public AbstractAddDecoration(PropertiesEditingView view) {
			this.view = view;
		}

		protected ControlDecoration decorateControl(final Control control, final EEFNotification notification) {
			ControlDecoration decoration = null;
			Image image = null;
			if (notification.getKind() == EEFNotification.ERROR) {
				image = view.getViewSettings().getErrorDecorationImage();
			} else if (notification.getKind() == EEFNotification.WARNING) {
				image = view.getViewSettings().getWarningDecorationImage();	
			} else if (notification.getKind() == EEFNotification.LOCK) {
				image = view.getViewSettings().getLockDecorationImage();	
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

		public AddDecorationOnEditor(PropertiesEditingView editingView, EEFPropertyNotification notification) {
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
				final Control control = propertyEditor.getPropertyEditorViewer().getViewer().getControl();
				ControlDecoration decoration = decorateControl(control, notification);
				if (decoration != null) {
					DecorationSettings settings = decorationSettings.get(view);
					if (settings == null) {
						settings = new DecorationSettings();
						decorationSettings.put(view, settings);
					}
					settings.decorations.put(editor, decoration);
				}
			}
		}

	}

	private final class AddDecorationOnView extends AbstractAddDecoration implements Runnable {

		private EEFNotification notification;

		public AddDecorationOnView(PropertiesEditingView editingView, EEFNotification notification) {
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
		
		private PropertiesEditingView view;
		private ViewElement editor;

		public RemoveDecorationOnEditor(PropertiesEditingView editingView, ViewElement editor) {
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
				view.getPropertyEditor(editor).getPropertyEditorViewer().getViewer().getControl().getParent().redraw();
			}
		}
	}

	private final class RemoveDecorationOnView implements Runnable {

		private PropertiesEditingView view;
		
		public RemoveDecorationOnView(PropertiesEditingView editingView) {
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
