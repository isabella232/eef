/**
 *  Copyright (c) 2008 - 2010 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 *
 */
package org.eclipse.emf.eef.views.components;

// Start of user code for imports
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.components.SinglePartPropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.utils.EEFConverterUtil;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.providers.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.ui.widgets.ButtonsModeEnum;
import org.eclipse.emf.eef.runtime.ui.widgets.eobjflatcombo.EObjectFlatComboSettings;
import org.eclipse.emf.eef.toolkits.ToolkitsFactory;
import org.eclipse.emf.eef.toolkits.Widget;
import org.eclipse.emf.eef.views.CustomElementEditor;
import org.eclipse.emf.eef.views.ViewsPackage;
import org.eclipse.emf.eef.views.parts.CustomElementEditorPropertiesEditionPart;
import org.eclipse.emf.eef.views.parts.ViewsViewsRepository;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
	

// End of user code

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class CustomElementEditorBasePropertiesEditionComponent extends SinglePartPropertiesEditingComponent {

	
	public static String BASE_PART = "Base"; //$NON-NLS-1$

	
	/**
	 * Settings for representation EObjectFlatComboViewer
	 */
	private EObjectFlatComboSettings representationSettings;
	
	
	/**
	 * Default constructor
	 * 
	 */
	public CustomElementEditorBasePropertiesEditionComponent(PropertiesEditingContext editingContext, EObject customElementEditor, String editing_mode) {
		super(editingContext, customElementEditor, editing_mode);
		parts = new String[] { BASE_PART };
		repositoryKey = ViewsViewsRepository.class;
		partKey = ViewsViewsRepository.CustomElementEditor.class;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#initPart(java.lang.Object, int, org.eclipse.emf.ecore.EObject, 
	 *      org.eclipse.emf.ecore.resource.ResourceSet)
	 * 
	 */
	public void initPart(Object key, int kind, EObject elt, ResourceSet allResource) {
		setInitializing(true);
		if (editingPart != null && key == partKey) {
			editingPart.setContext(elt, allResource);
			final CustomElementEditor customElementEditor = (CustomElementEditor)elt;
			final CustomElementEditorPropertiesEditionPart basePart = (CustomElementEditorPropertiesEditionPart)editingPart;
			// init values
			if (isAccessible(ViewsViewsRepository.CustomElementEditor.Properties.representation)) {
				// init part
				representationSettings = new EObjectFlatComboSettings(customElementEditor, ViewsPackage.eINSTANCE.getViewElement_Representation());
				basePart.initRepresentation(representationSettings);
				// set the button mode
				basePart.setRepresentationButtonMode(ButtonsModeEnum.BROWSE);
			}
			if (customElementEditor.getName() != null && isAccessible(ViewsViewsRepository.CustomElementEditor.Properties.name))
				basePart.setName(EEFConverterUtil.convertToString(EcorePackage.eINSTANCE.getEString(), customElementEditor.getName()));
			
			if (isAccessible(ViewsViewsRepository.CustomElementEditor.Properties.readOnly)) {
				basePart.setReadOnly(customElementEditor.isReadOnly());
			}
			// init filters
			basePart.addFilterToRepresentation(new ViewerFilter() {
			
			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
			 */
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				return (element instanceof String && element.equals("")) || (element instanceof Widget); //$NON-NLS-1$ 
				}
			
			});
			// Start of user code for additional businessfilters for representation
																																																						
																																																						// End of user code
			
			
			
			// init values for referenced views
			
			// init filters for referenced views
			
		}
		setInitializing(false);
	}






	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#associatedFeature(java.lang.Object)
	 */
	public EStructuralFeature associatedFeature(Object editorKey) {
		if (editorKey == ViewsViewsRepository.CustomElementEditor.Properties.representation) {
			return ViewsPackage.eINSTANCE.getViewElement_Representation();
		}
		if (editorKey == ViewsViewsRepository.CustomElementEditor.Properties.name) {
			return ViewsPackage.eINSTANCE.getViewElement_Name();
		}
		if (editorKey == ViewsViewsRepository.CustomElementEditor.Properties.readOnly) {
			return ViewsPackage.eINSTANCE.getElementEditor_ReadOnly();
		}
		return super.associatedFeature(editorKey);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updateSemanticModel(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public void updateSemanticModel(final IPropertiesEditionEvent event) {
		CustomElementEditor customElementEditor = (CustomElementEditor)semanticObject;
		if (ViewsViewsRepository.CustomElementEditor.Properties.representation == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.SET) {
				representationSettings.setToReference((Widget)event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.ADD) {
				Widget eObject = ToolkitsFactory.eINSTANCE.createWidget();
				EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, eObject, editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(eObject, PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy policy = provider.getPolicy(context);
					if (policy != null) {
						policy.execute();
					}
				}
				representationSettings.setToReference(eObject);
			}
		}
		if (ViewsViewsRepository.CustomElementEditor.Properties.name == event.getAffectedEditor()) {
			customElementEditor.setName((java.lang.String)EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEString(), (String)event.getNewValue()));
		}
		if (ViewsViewsRepository.CustomElementEditor.Properties.readOnly == event.getAffectedEditor()) {
			customElementEditor.setReadOnly((Boolean)event.getNewValue());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updatePart(org.eclipse.emf.common.notify.Notification)
	 */
	public void updatePart(Notification msg) {
		if (editingPart.isVisible()) {
			CustomElementEditorPropertiesEditionPart basePart = (CustomElementEditorPropertiesEditionPart)editingPart;
			if (ViewsPackage.eINSTANCE.getViewElement_Representation().equals(msg.getFeature()) && basePart != null && isAccessible(ViewsViewsRepository.CustomElementEditor.Properties.representation))
				basePart.setRepresentation((EObject)msg.getNewValue());
			if (ViewsPackage.eINSTANCE.getViewElement_Name().equals(msg.getFeature()) && basePart != null && isAccessible(ViewsViewsRepository.CustomElementEditor.Properties.name)) {
				if (msg.getNewValue() != null) {
					basePart.setName(EcoreUtil.convertToString(EcorePackage.eINSTANCE.getEString(), msg.getNewValue()));
				} else {
					basePart.setName("");
				}
			}
			if (ViewsPackage.eINSTANCE.getElementEditor_ReadOnly().equals(msg.getFeature()) && basePart != null && isAccessible(ViewsViewsRepository.CustomElementEditor.Properties.readOnly))
				basePart.setReadOnly((Boolean)msg.getNewValue());
			
			
		}
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#isRequired(java.lang.Object, int)
	 * 
	 */
	public boolean isRequired(Object key, int kind) {
		return key == ViewsViewsRepository.CustomElementEditor.Properties.name || key == ViewsViewsRepository.CustomElementEditor.Properties.readOnly;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getHelpContent(java.lang.Object, int)
	 * 
	 */
	public String getHelpContent(Object key, int kind) {
		if (key == ViewsViewsRepository.CustomElementEditor.Properties.representation)
			return "The representation of this part of view"; //$NON-NLS-1$
		if (key == ViewsViewsRepository.CustomElementEditor.Properties.name)
			return "The element name"; //$NON-NLS-1$
		if (key == ViewsViewsRepository.CustomElementEditor.Properties.readOnly)
			return "Defines that this editor is in read only mode"; //$NON-NLS-1$
		return super.getHelpContent(key, kind);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#validateValue(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public Diagnostic validateValue(IPropertiesEditionEvent event) {
		Diagnostic ret = Diagnostic.OK_INSTANCE;
		if (event.getNewValue() != null) {
			try {
				if (ViewsViewsRepository.CustomElementEditor.Properties.name == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(ViewsPackage.eINSTANCE.getViewElement_Name().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(ViewsPackage.eINSTANCE.getViewElement_Name().getEAttributeType(), newValue);
				}
				if (ViewsViewsRepository.CustomElementEditor.Properties.readOnly == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(ViewsPackage.eINSTANCE.getElementEditor_ReadOnly().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(ViewsPackage.eINSTANCE.getElementEditor_ReadOnly().getEAttributeType(), newValue);
				}
			} catch (IllegalArgumentException iae) {
				ret = BasicDiagnostic.toDiagnostic(iae);
			} catch (WrappedException we) {
				ret = BasicDiagnostic.toDiagnostic(we);
			}
		}
		return ret;
	}

}
