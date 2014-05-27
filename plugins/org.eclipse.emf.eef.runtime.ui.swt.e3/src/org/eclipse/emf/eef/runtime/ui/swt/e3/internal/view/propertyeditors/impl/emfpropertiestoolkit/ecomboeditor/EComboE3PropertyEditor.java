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
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor;

import java.util.Collection;

import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.widgets.EEFSelectionDialogWithFilter;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor.EComboPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.EEFSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.SingleLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.SingleLinePropertyViewerListener;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.util.ChoiceOfValuesFilter;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.FilterablePropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.filters.ViewerFilterBuilderProvider;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EComboE3PropertyEditor extends EComboPropertyEditor {

	/**
	 * @param emfServiceProvider
	 * @param eefEditingServiceProvider
	 * @param editUIProvidersFactory
	 * @param imageManager
	 * @param filterBuilderProvider
	 * @param view
	 * @param propertyBinding
	 * @param elementEditor
	 * @param propertyEditorViewer
	 */
	public EComboE3PropertyEditor(EMFServiceProvider emfServiceProvider, EEFEditingServiceProvider eefEditingServiceProvider,
			EditUIProvidersFactory editUIProvidersFactory, ImageManager imageManager, ViewerFilterBuilderProvider filterBuilderProvider,
			PropertiesEditingView<Composite> view, ElementEditor elementEditor,
			PropertyEditorViewer<SingleLinePropertyViewer> propertyEditorViewer) {
		super(emfServiceProvider, eefEditingServiceProvider, editUIProvidersFactory, imageManager, filterBuilderProvider, view,
				elementEditor, propertyEditorViewer);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor.EComboPropertyEditor#createPropertyViewerListener()
	 */
	@Override
	protected SingleLinePropertyViewerListener createPropertyViewerListener() {
		SingleLinePropertyViewerListener propertyViewerListener = super.createPropertyViewerListener();
		return new DelegatingSingleLinePropertyViewerListener(propertyViewerListener) {

			public void set() {
				if (isEnabled()) {
					EEFSelectionDialog dialog = new EEFSelectionDialogWithFilter(propertyEditorViewer.getViewer().getControl().getShell(), true);
					dialog.setTitle("Choose the element to set to the reference:");
					dialog.setAdapterFactory(view.getEditingComponent().getEditingContext().getAdapterFactory());
					dialog.setEditUIProvidersFactory(editUIProvidersFactory);
					dialog.setImageManager(imageManager);
					dialog.addFilter(
							new ChoiceOfValuesFilter(
									eefEditingServiceProvider,
									view.getEditingComponent().getEditingContext(), 
									view.getEditingComponent().getEObject(), 
									elementEditor, 
									EEFSWTConstants.DEFAULT_SELECTION_MODE));
					Collection<ViewerFilter> filters = ((FilterablePropertyEditor)propertyEditorViewer).getFilters();
					if (!filters.isEmpty()) {
						for (ViewerFilter viewerFilter : filters) {
							dialog.addFilter(viewerFilter);
						}
					}
					dialog.setInput(view.getViewService().getBestInput(view.getEditingComponent().getEObject()));
					if (dialog.open() == Window.OK) {
						if (dialog.getSelection() != null) {
							firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.SET, null, dialog.getSelection()));
							propertyEditorViewer.getViewer().refresh();
						}
					}
				}
			}

		};
	}

	private static class DelegatingSingleLinePropertyViewerListener extends SingleLinePropertyViewerListener {

		private SingleLinePropertyViewerListener delegatedListener;

		/**
		 * @param delegatedListener
		 */
		public DelegatingSingleLinePropertyViewerListener(SingleLinePropertyViewerListener delegatedListener) {
			super(delegatedListener.getPropertyEditor(), delegatedListener.getView(), delegatedListener.getElementEditor(), delegatedListener.getViewer());
			this.delegatedListener = delegatedListener;
		}

		/**
		 * 
		 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.SingleLinePropertyViewer.SingleLinePropertyViewerListener#set()
		 */
		public void set() {
			delegatedListener.set();
		}

		/**
		 * 
		 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.SingleLinePropertyViewer.SingleLinePropertyViewerListener#clear()
		 */
		public void clear() {
			delegatedListener.clear();
		}

	}

}
