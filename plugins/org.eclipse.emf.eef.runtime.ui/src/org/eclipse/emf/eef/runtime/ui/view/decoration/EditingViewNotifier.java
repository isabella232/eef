/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.decoration;

import java.util.Map;

import org.eclipse.emf.eef.runtime.services.viewhandler.notify.EEFNotification;
import org.eclipse.emf.eef.runtime.services.viewhandler.notify.EEFNotifier;
import org.eclipse.emf.eef.runtime.services.viewhandler.notify.EEFPropertyNotification;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public final class EditingViewNotifier implements EEFNotifier {

	private PropertiesEditingView view;
	private Map<Object, ControlDecoration> decorations;
	
	/**
	 * @param view
	 */
	public EditingViewNotifier(PropertiesEditingView view) {
		this.view = view;
		decorations = Maps.newHashMap();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.notify.EEFNotifier#notify(org.eclipse.emf.eef.runtime.services.viewhandler.notify.EEFNotification)
	 */
	public void notify(final EEFNotification notification) {
		if (notification instanceof EEFPropertyNotification) {
			executeUIRunnable(new AddDecorationRunnable(view, decorations, (EEFPropertyNotification) notification));
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.notify.EEFNotifier#clearViewNotification()
	 */
	public void clearViewNotification() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.notify.EEFNotifier#clearEditorNotification(java.lang.Object)
	 */
	public void clearEditorNotification(Object editor) {
		executeUIRunnable(new RemoveDecorationRunnable(view, decorations, editor));
	}

	private final void executeUIRunnable(Runnable updateRunnable) {
		if (null == Display.getCurrent()) {
			PlatformUI.getWorkbench().getDisplay().syncExec(updateRunnable);
		} else {
			updateRunnable.run();
		}
	}

	private static final class AddDecorationRunnable implements Runnable {

		private PropertiesEditingView view;
		private Map<Object, ControlDecoration> decorations;
		private EEFPropertyNotification notification;
		
		public AddDecorationRunnable(PropertiesEditingView view, Map<Object, ControlDecoration> decorations, EEFPropertyNotification notification) {
			this.view = view;
			this.decorations = decorations;
			this.notification = notification;
		}

		/**
		 * {@inheritDoc}
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			Object editor = ((EEFPropertyNotification) notification).getEditor();
			PropertyEditor propertyEditor = view.getPropertyEditor(editor);
			Image image = null;
			if (notification.getKind() == EEFNotification.ERROR) {
				image = view.getViewSettings().getErrorDecorationImage();
			} else if (notification.getKind() == EEFNotification.WARNING) {
				image = view.getViewSettings().getWarningDecorationImage();	
			}
			if (image != null) {
				final Control control = propertyEditor.getPropertyEditorViewer().getViewer().getControl();
				final int position = view.getViewSettings().getDecoratorPositioning();
				ControlDecoration decoration = new ControlDecoration(control, position);
				decoration.setDescriptionText(notification.getMessage());
				decoration.setImage(image);
				this.decorations.put(editor, decoration);			
			}
		}
		
	}
	
	private static final class RemoveDecorationRunnable implements Runnable {

		private PropertiesEditingView view;
		private Map<Object, ControlDecoration> decorations;
		private Object editor;
		
		public RemoveDecorationRunnable(PropertiesEditingView view, Map<Object, ControlDecoration> decorations, Object editor) {
			this.view = view;
			this.decorations = decorations;
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
}
