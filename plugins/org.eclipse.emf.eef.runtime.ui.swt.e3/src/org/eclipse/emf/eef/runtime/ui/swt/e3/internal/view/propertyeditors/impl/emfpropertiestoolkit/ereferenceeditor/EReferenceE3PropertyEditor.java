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
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit.ereferenceeditor;

import java.util.Collection;

import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.widgets.EEFSelectionDialogWithFilter;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.ereferenceeditor.EReferencePropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.EEFSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewerListener;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.util.ChoiceOfValuesFilter;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.filters.ViewerFilterBuilderProvider;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferenceE3PropertyEditor extends EReferencePropertyEditor {

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
	public EReferenceE3PropertyEditor(EMFServiceProvider emfServiceProvider, EEFEditingServiceProvider eefEditingServiceProvider,
			EditUIProvidersFactory editUIProvidersFactory, ImageManager imageManager, ViewerFilterBuilderProvider filterBuilderProvider,
			PropertiesEditingView<Composite> view, ElementEditor elementEditor,
			PropertyEditorViewer<MultiLinePropertyViewer> propertyEditorViewer) {
		super(emfServiceProvider, eefEditingServiceProvider, editUIProvidersFactory, imageManager, filterBuilderProvider, view,
				elementEditor, propertyEditorViewer);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.ereferenceeditor.EReferencePropertyEditor#createPropertyViewerListener()
	 */
	@Override
	protected MultiLinePropertyViewerListener createPropertyViewerListener() {
		MultiLinePropertyViewerListener propertyViewerListener = super.createPropertyViewerListener();
		return new DelegatingMultiLinePropertyViewerListener(propertyViewerListener) {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit.ereferenceeditor.EReferenceE3PropertyEditor.DelegatingMultiLinePropertyViewerListener#add()
			 */
			@Override
			public void add() {
				if (isEnabled()) {
					EEFSelectionDialog dialog = new EEFSelectionDialogWithFilter(propertyEditorViewer.getViewer().getControl().getShell(), true);
					dialog.setTitle("Choose the element to add to the reference:");
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
					dialog.setInput(view.getViewService().getBestInput(view.getEditingComponent().getEObject()));
					if (dialog.open() == Window.OK) {
						if (dialog.getSelection() != null) {
							if (dialog.getSelection() instanceof Collection<?>) {
								firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.ADD_MANY, null, dialog.getSelection()));
							} else {
								firePropertiesChanged(view.getEditingComponent(),new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.ADD, null, dialog.getSelection()));
							}
							propertyEditorViewer.getViewer().refresh();				
						}
					}
				}
			}

		};
	}

	private static class DelegatingMultiLinePropertyViewerListener extends MultiLinePropertyViewerListener {

		private MultiLinePropertyViewerListener delegatedListener;

		/**
		 * @param delegatedListener
		 */
		public DelegatingMultiLinePropertyViewerListener(MultiLinePropertyViewerListener delegatedListener) {
			super(delegatedListener.getPropertyEditor(), delegatedListener.getView(), delegatedListener.getElementEditor(), delegatedListener.getViewer());
			this.delegatedListener = delegatedListener;
		}

		/**
		 * 
		 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer.MultiLinePropertyViewerListener#add()
		 */
		public void add() {
			delegatedListener.add();
		}

		/**
		 * @param editedElement
		 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer.MultiLinePropertyViewerListener#edit(java.lang.Object)
		 */
		public void edit(Object editedElement) {
			delegatedListener.edit(editedElement);
		}

		/**
		 * @param removedElement
		 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer.MultiLinePropertyViewerListener#remove(java.lang.Object)
		 */
		public void remove(Object removedElement) {
			delegatedListener.remove(removedElement);
		}

		/**
		 * @param removedElements
		 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer.MultiLinePropertyViewerListener#removeAll(java.util.Collection)
		 */
		public void removeAll(Collection<?> removedElements) {
			delegatedListener.removeAll(removedElements);
		}

		/**
		 * @param movedElement
		 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer.MultiLinePropertyViewerListener#moveUp(java.lang.Object)
		 */
		public void moveUp(Object movedElement) {
			delegatedListener.moveUp(movedElement);
		}

		/**
		 * @param movedElement
		 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer.MultiLinePropertyViewerListener#moveDown(java.lang.Object)
		 */
		public void moveDown(Object movedElement) {
			delegatedListener.moveDown(movedElement);
		}

	}

}
