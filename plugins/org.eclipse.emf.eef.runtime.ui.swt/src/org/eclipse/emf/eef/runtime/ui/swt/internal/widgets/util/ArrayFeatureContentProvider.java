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
package org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.util;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.ui.provider.NotifyChangedToViewerRefresh;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class ArrayFeatureContentProvider implements IStructuredContentProvider, ITreeContentProvider, INotifyChangedListener {

	private EEFEditingServiceProvider eefEditingServiceProvider;
	private SWTViewService viewService;
	private PropertiesEditingContext editingContext;
	private ElementEditor elementEditor;
	private Viewer viewer;

	/**
	 * @param propertyBinding
	 */
	public ArrayFeatureContentProvider(EEFEditingServiceProvider eefEditingServiceProvider, SWTViewService viewService, PropertiesEditingContext editingContext, ElementEditor elementEditor) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.viewService = viewService;
		this.editingContext = editingContext;
		this.elementEditor = elementEditor;
		if (editingContext.getAdapterFactory() instanceof IChangeNotifier) {
			((IChangeNotifier) editingContext.getAdapterFactory()).addListener(this);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof EObject) {
			EObject eObject = (EObject) inputElement;
			Object value = eefEditingServiceProvider.getEditingService(eObject).getValue(editingContext, eObject, elementEditor);
			if (value instanceof Collection<?>) {
				return ((Collection<?>) value).toArray();
			} else if (value instanceof Object[]) {
				return (Object[]) value;
			} else if (value != null) {
				return new Object[] { value };
			}
		}
		return new Object[0];
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object parentElement) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element) {
		return false;
	}

	public void notifyChanged(Notification notification) {
		if (viewer != null && viewer.getControl() != null && !viewer.getControl().isDisposed()) {
			viewService.executeAsyncUIRunnable(viewer.getControl().getDisplay(), new Runnable() {

				public void run() {
					if (viewer != null && viewer.getControl() != null && !viewer.getControl().isDisposed()) {
						viewer.refresh();
					}
				}
			});
		} else {
			NotifyChangedToViewerRefresh.handleNotifyChanged(viewer, notification.getNotifier(), notification.getEventType(), notification.getFeature(), notification.getOldValue(),
					notification.getNewValue(), notification.getPosition());
		}
	}
}
