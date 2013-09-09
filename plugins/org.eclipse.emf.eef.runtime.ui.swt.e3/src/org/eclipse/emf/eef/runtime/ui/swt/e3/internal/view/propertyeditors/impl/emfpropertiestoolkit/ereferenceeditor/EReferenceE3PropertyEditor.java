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
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer.MultiLinePropertyViewerListener;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.util.ChoiceOfValuesFilter;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferenceE3PropertyEditor extends EReferencePropertyEditor {

	public EReferenceE3PropertyEditor(EditUIProvidersFactory editUIProvidersFactory, ImageManager imageManager, PropertiesEditingView<Composite> view, ElementEditor elementEditor, PropertyEditorViewer<MultiLinePropertyViewer> propertyEditorViewer) {
		super(editUIProvidersFactory, imageManager, view, elementEditor, propertyEditorViewer);
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
				EEFSelectionDialog dialog = new EEFSelectionDialogWithFilter(propertyEditorViewer.getViewer().getControl().getShell(), true);
				dialog.setTitle("Choose the element to add to the " + feature.getName() + " reference:");
				dialog.setAdapterFactory(view.getEditingComponent().getEditingContext().getAdapterFactory());
				dialog.setEditUIProvidersFactory(editUIProvidersFactory);
				dialog.setImageManager(imageManager);
				dialog.addFilter(
						new ChoiceOfValuesFilter(
								view.getEditingComponent().getEditingContext().getAdapterFactory(), 
								view.getEditingComponent().getEObject(), 
								EReferenceE3PropertyEditor.this.feature, 
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
			
		};
	}

	private static class DelegatingMultiLinePropertyViewerListener implements MultiLinePropertyViewerListener {
		
		private MultiLinePropertyViewerListener delegatedListener;

		/**
		 * @param delegatedListener
		 */
		public DelegatingMultiLinePropertyViewerListener(MultiLinePropertyViewerListener delegatedListener) {
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
