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

package org.eclipse.emf.eef.runtime.ui.internal.view.util;

import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.EditingOptions;
import org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider;
import org.eclipse.emf.eef.runtime.ui.view.ViewHelper;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * TODO: manage a Semanticless mode.
 */
public class ViewHelperImpl implements ViewHelper {
		
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/**
	 * Image registry key for help image (value <code>"dialog_help_image"</code> ).
	 */
	public static final String DLG_IMG_HELP = "dialog_help_image"; //$NON-NLS-1$

	private PropertiesEditingComponent editingComponent;
	private FormToolkit toolkit;
	
	/**
	 * Creates a semanticless helper.
	 */
	public ViewHelperImpl() { }

	/**
	 * @param editingComponent
	 */
	public ViewHelperImpl(PropertiesEditingComponent editingComponent) {
		this.editingComponent = editingComponent;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewHelper#getEditingComponent()
	 */
	public PropertiesEditingComponent getEditingComponent() {
		return editingComponent;
	}
 
	/**
	 * @param toolkit the toolkit to set
	 */
	public void setToolkit(FormToolkit toolkit) {
		this.toolkit = toolkit;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.parts.ViewHelper#createLabel(org.eclipse.swt.widgets.Composite, java.lang.Object, java.lang.String)
	 */
	public Label createLabel(Composite parent, Object editor, String alternate) {
		String text = getDescription(editor, alternate);
		if (!text.endsWith(": ") && !text.endsWith(":")) {
			text += ": ";
		}
		Label label;
		if (toolkit != null) {
			label = toolkit.createLabel(parent, text);
		} else {
			label = new Label(parent, SWT.NONE);
			label.setText(text);
		}
		EStructuralFeature associatedFeature = feature(editor);
		if (associatedFeature != null && associatedFeature.isRequired()) {
			label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		}
		return label;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.parts.ViewHelper#getDescription(java.lang.Object, java.lang.String)
	 */
	public String getDescription(Object editor, String alternate) {
		String text = alternate;
		EStructuralFeature associatedFeature = feature(editor);
		if (associatedFeature != null) {
			IItemPropertySource labelProvider = (IItemPropertySource) editingComponent.getEditingContext().getAdapterFactory().adapt(editingComponent.getTarget(), org.eclipse.emf.edit.provider.IItemPropertySource.class);
			if (labelProvider != null) {
				IItemPropertyDescriptor propertyDescriptor = labelProvider.getPropertyDescriptor(editingComponent.getTarget(), associatedFeature);
				if (propertyDescriptor != null) {
					text = propertyDescriptor.getDisplayName(editor);
				}
			}
		}
		return text;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.parts.ViewHelper#createHelpButton(org.eclipse.swt.widgets.Composite, java.lang.Object)
	 */
	@SuppressWarnings("unused")
	public Control createHelpButton(Composite parent, Object editor ) {
		//To manage in future
		String helpID = null;
		String alternate = getHelpContent(editor);
		Image image = JFaceResources.getImage(DLG_IMG_HELP);
		if (helpID != null && !EMPTY_STRING.equals(helpID)) { //$NON-NLS-1$
			ToolBar result = new ToolBar(parent, SWT.FLAT | SWT.NO_FOCUS);
			((GridLayout)parent.getLayout()).numColumns++;
			result.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
			ToolItem item = new ToolItem(result, SWT.NONE);
			item.setImage(image);
			if (alternate != null && !EMPTY_STRING.equals(alternate)) //$NON-NLS-1$
				item.setToolTipText(alternate);
			return result;
		} else {
			Label result = null; 
			if (toolkit != null) {
				result = toolkit.createLabel(parent, EMPTY_STRING); //$NON-NLS-1$
			} else {
				result = new Label(parent, SWT.NONE);
			}
			if (alternate != null && !EMPTY_STRING.equals(alternate)) { //$NON-NLS-1$
				result.setImage(image);
				result.setToolTipText(alternate);
			}
			return result;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewHelper#setID(org.eclipse.swt.widgets.Control, java.lang.Object)
	 */
	public void setID(Control widget, Object value) {
		if (widget != null)
			widget.setData(EEF_WIDGET_ID_KEY, value);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewHelper#getID(org.eclipse.swt.widgets.Control)
	 */
	public Object getID(Control widget) {
		if (widget != null)
			return widget.getData(EEF_WIDGET_ID_KEY);
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewHelper#setEEFtype(org.eclipse.swt.widgets.Control, java.lang.String)
	 */
	public void setEEFtype(Control widget, String value) {
		if (widget != null)
			widget.setData(EEF_WIDGET_TYPE_KEY, value);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewHelper#getEEFType(org.eclipse.swt.widgets.Control)
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
	 * @param editor
	 * @return
	 */
	private EStructuralFeature feature(Object editor) {
		return editingComponent.getBinding().feature(editor, editingComponent.getEditingContext().getOptions().autowire());
	}

	/**
	 * Returns documentation about the feature binded to the given editor. There is two strategies for getting this documentation:
	 * 	- getting the property description of the {@link GenFeature} associated
	 *  - getting the ecore documentation of the feature
	 * The choice of strategy is defined by the {@link EditingOptions} of the {@link PropertiesEditingMessageManager}:
	 * 	- if the options are null or the {@link FeatureDocumentationProvider#GENMODEL_PROPERTY_DESCRIPTION} value is set, the first strategy is chosen
	 *  - if the {@link FeatureDocumentationProvider#ECORE_DOCUMENTATION} value is set, the second strategy is chosen
	 * @param editor which to get the documentation.
	 * @return the found documentation if exists, <code>null</code> otherwise.
	 */
	private String getHelpContent(Object editor) {
		EStructuralFeature feature = editingComponent.getBinding().feature(editor, editingComponent.getEditingContext().getOptions().autowire());
		if (feature != null) {
			EditingOptions options = editingComponent.getBinding().getEditingModel().getOptions();
			if (options == null || options.getFeatureDocumentationProvider() == FeatureDocumentationProvider.GENMODEL_PROPERTY_DESCRIPTION) {
				EditingModelEnvironment editingModelEnvironment = editingComponent.getEditingModelEnvironment();
				GenFeature genFeature = editingModelEnvironment.genFeature(feature);
				String documentation = genFeature.getPropertyDescription();
				if (documentation != null && documentation.length() > 0) {
					return documentation;
				}
			} else {
				if (options.getFeatureDocumentationProvider() == FeatureDocumentationProvider.ECORE_DOCUMENTATION) {
					String documentation = EcoreUtil.getDocumentation(feature);
					if (documentation != null && documentation.length() > 0) {
						return documentation;
					}
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewHelper#getBestInput(java.lang.Object)
	 */
	public Object getBestInput(Object sourceInput) {
		Resource resource = null;
		if (sourceInput instanceof EObject) {
			resource = ((EObject) sourceInput).eResource();
		} else if (sourceInput instanceof Resource){
			resource = (Resource) sourceInput;
		}
		ResourceSet resourceSet = null;
		if (resource != null) {
			resourceSet = resource.getResourceSet();
		} else if (sourceInput instanceof ResourceSet) {
			resourceSet = (ResourceSet) sourceInput;
		}
		if (resourceSet != null) {
			return resourceSet;
		} else if (resource != null) {
			return resource;
		}
		return sourceInput;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewHelper#getEditingDomain(org.eclipse.ui.IWorkbenchPart)
	 */
	public EditingDomain getEditingDomain(IWorkbenchPart part) {
		EditingDomain editingDomain = null;
		if (part instanceof IEditingDomainProvider) {
			editingDomain = ((IEditingDomainProvider)part).getEditingDomain();
		} else if (part instanceof IEditorPart) {
			if ((((IEditorPart)part).getAdapter(IEditingDomainProvider.class)) != null) {
				editingDomain = ((IEditingDomainProvider)((IEditorPart)part).getAdapter(IEditingDomainProvider.class)).getEditingDomain();
			} else if ((((IEditorPart)part).getAdapter(EditingDomain.class)) != null) {
				editingDomain = (EditingDomain)((IEditorPart)part).getAdapter(EditingDomain.class);
			}
		}
		return editingDomain;
	}

}

