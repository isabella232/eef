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
package org.eclipse.emf.eef.editor.internal.propertyeditors.extended.treecontents;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.editor.internal.widgets.TreeEEFViewer;
import org.eclipse.emf.eef.editor.internal.widgets.TreeEEFViewer.SelectionInterpreter;
import org.eclipse.emf.eef.editor.internal.widgets.TreeEEFViewer.TreeEEFViewerListener;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.TargetedEditingEvent;
import org.eclipse.emf.eef.runtime.ui.swt.util.EEFViewerInput;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.PropertyEditorImpl;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TreeContentsPropertyEditor extends PropertyEditorImpl implements MultivaluedPropertyEditor {

	private EditingContextFactoryProvider contextFactoryProvider;
	private EMFServiceProvider emfServiceProvider;
	private EEFEditingServiceProvider eefEditingServiceProvider;
	
	private PropertiesEditingView<Composite> view;
	private ElementEditor elementEditor;
	private PropertyEditorViewer<TreeEEFViewer> propertyEditorViewer;

	public TreeContentsPropertyEditor(EditingContextFactoryProvider contextFactoryProvider, EMFServiceProvider emfServiceProvider, EEFEditingServiceProvider eefEditingServiceProvider, PropertiesEditingView<Composite> view, ElementEditor elementEditor, PropertyEditorViewer<TreeEEFViewer> propertyEditorViewer) {
		this.contextFactoryProvider = contextFactoryProvider;
		this.emfServiceProvider = emfServiceProvider;
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.view = view;
		this.elementEditor = elementEditor;
		this.propertyEditorViewer = propertyEditorViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#init()
	 */
	public void init() {
		AdapterFactory adapterFactory = view.getEditingComponent().getEditingContext().getAdapterFactory();
		final EObject input = view.getEditingComponent().getEObject();
		propertyEditorViewer.getViewer().setContentProvider(new AdapterFactoryContentProvider(view.getEditingComponent().getEditingContext().getAdapterFactory()) {
			
			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getElements(java.lang.Object)
			 */
			@Override
			public Object[] getElements(Object object) {
				if (object instanceof Collection<?>) {
					return ((Collection<?>) object).toArray();
				}
				return super.getElements(object);
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getChildren(java.lang.Object)
			 */
			@Override
			public Object[] getChildren(Object object) {
				if (object == input) {
					EList<?> children = (EList<?>)((EObject)input).eGet(TreeContentsPropertyEditor.this.propertyBinding);
					return children.toArray(); 
				}
				return super.getChildren(object);
			}
			
			
			
		});
		propertyEditorViewer.getViewer().setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		propertyEditorViewer.getViewer().setSelectionInterpreter(new SelectionInterpreter() {
			
			public PropertiesEditingContext createContextFromSelection(ISelection selection) {
				EObject selectedElement = unwrapSelection(selection);
				PropertiesEditingContext context;
				Object viewerInput = propertyEditorViewer.getViewer().getInput();
				if (selectedElement != null && viewerInput instanceof EEFViewerInput && selectedElement != ((EEFViewerInput)viewerInput).getEditedObject()) {
					context = contextFactoryProvider.getEditingContextFactory(selectedElement).createPropertiesEditingContext(view.getEditingComponent().getEditingContext(), selectedElement);
				} else {
					context = contextFactoryProvider.getEditingContextFactory(selectedElement).createNullEditingContext();
				}
				return context;
			}

		});
		EEFViewerInput eefViewerInput = new EEFViewerInput(eefEditingServiceProvider, view.getEditingComponent().getEditingContext(), elementEditor);
		propertyEditorViewer.getViewer().setInput(eefViewerInput);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = view.getViewSettings().getMultiEditorHeight();
		layoutData.horizontalSpan = 2;
		propertyEditorViewer.getViewer().getControl().setLayoutData(layoutData);
		propertyEditorViewer.getViewer().addListener(new TreeEEFViewerListener() {
			
			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.editor.internal.widgets.TreeEEFViewer.TreeEEFViewerListener#handleAdd(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EClass)
			 */
			public void handleAdd(EObject owner, EClass elementType) {
				firePropertiesChanged(view.getEditingComponent(), new TargetedEditingEvent(view, elementEditor, owner, PropertiesEditingEvent.ADD, null, elementType));
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.editor.internal.widgets.TreeEEFViewer.TreeEEFViewerListener#handleRemove(org.eclipse.emf.ecore.EObject)
			 */
			public void handleRemove(EObject element) {
				//Ok here it's a bit tricky. I have to find the object referencing the selected by the propertyBinding binding to the given elementEditor.
				PropertiesEditingContext editingContext = view.getEditingComponent().getEditingContext();
				EClassBinding binding = editingContext.getEditingComponent().getBinding();
				EStructuralFeature bindedFeature = binding.feature(elementEditor, editingContext.getOptions().autowire());
				EObject target = null;
				// First I check if the propertyBinding where I have to remove the element is its containing propertyBinding (a crossreferencer don't find this information)
				EMFService emfService = emfServiceProvider.getEMFService(element.eClass().getEPackage());
				if (emfService.equals(element.eContainingFeature(), bindedFeature)) {
					target = element.eContainer();
				} else {
					// Otherwise I use a crossreferencer
					Object bestInput = view.getViewService().getBestInput(element);
					Collection<Setting> usage = null;
					if (bestInput instanceof ResourceSet) {
						usage = UsageCrossReferencer.find(element, (ResourceSet)bestInput);
					} else if (bestInput instanceof Resource) {
						usage = UsageCrossReferencer.find(element, (Resource)bestInput);
					} else if (bestInput instanceof EObject) {
						usage = UsageCrossReferencer.find(element, (EObject)bestInput);
					}
					if (usage != null) {
						Iterator<Setting> iterator = usage.iterator();
						while (target == null && iterator.hasNext()) {
							for (Setting setting : usage) {
								if (emfService.equals(setting.getEStructuralFeature(), bindedFeature)) {
									target = setting.getEObject();
									break;
								}
							}
						}
					}
				}
				if (target != null) {
					firePropertiesChanged(view.getEditingComponent(), new TargetedEditingEvent(view, elementEditor, target, PropertiesEditingEvent.REMOVE, element, null));
				}
			}
			
		});
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#getPropertyEditorViewer()
	 */
	public PropertyEditorViewer<?> getPropertyEditorViewer() {
		return propertyEditorViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor#addValue(java.lang.Object)
	 */
	public void addValue(Object value) {
		propertyEditorViewer.getViewer().refresh();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor#addAllValues(java.util.Collection)
	 */
	public void addAllValues(Collection<?> values) {
		propertyEditorViewer.getViewer().refresh();		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor#removeValue(java.lang.Object)
	 */
	public void removeValue(Object value) {
		propertyEditorViewer.getViewer().refresh();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor#removeAllValues(java.util.Collection)
	 */
	public void removeAllValues(Collection<?> values) {
		propertyEditorViewer.getViewer().refresh();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor#moveValue(java.lang.Object, int)
	 */
	public void moveValue(Object value, int newIndex) {
		propertyEditorViewer.getViewer().refresh();
	}

	private EObject unwrapSelection(ISelection selection) {
		if (selection instanceof StructuredSelection) {
			return (EObject) ((StructuredSelection)selection).getFirstElement();
		}
		return null;
	}
}
