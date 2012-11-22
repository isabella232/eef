/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.eefnr.components;

// Start of user code for imports
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eef.eefnr.EefnrPackage;
import org.eclipse.emf.eef.eefnr.SingleCompositionEditorSample;
import org.eclipse.emf.eef.eefnr.parts.EefnrViewsRepository;
import org.eclipse.emf.eef.eefnr.parts.SingleCompositionEditorSamplePropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.context.impl.EReferencePropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.components.SinglePartPropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.impl.CreateEditingPolicy;
import org.eclipse.emf.eef.runtime.providers.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.ui.widgets.eobjflatcombo.EObjectFlatComboSettings;


// End of user code

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class SingleCompositionEditorSamplePropertiesEditionComponent extends SinglePartPropertiesEditingComponent {

	
	public static String BASE_PART = "Base"; //$NON-NLS-1$

	
	/**
	 * Settings for singleCompositionEditorRequiredProperty SingleCompositionEditor
	 */
	private EObjectFlatComboSettings singleCompositionEditorRequiredPropertySettings;
	
	/**
	 * Settings for singleCompositionEditorOptionalProperty SingleCompositionEditor
	 */
	private EObjectFlatComboSettings singleCompositionEditorOptionalPropertySettings;
	
	/**
	 * Settings for singleCompositionEditorRequiredAbstractProperty SingleCompositionEditor
	 */
	private EObjectFlatComboSettings singleCompositionEditorRequiredAbstractPropertySettings;
	
	/**
	 * Settings for singleCompositionEditorOptionalAbstractProperty SingleCompositionEditor
	 */
	private EObjectFlatComboSettings singleCompositionEditorOptionalAbstractPropertySettings;
	
	
	/**
	 * Default constructor
	 * 
	 */
	public SingleCompositionEditorSamplePropertiesEditionComponent(PropertiesEditingContext editingContext, EObject singleCompositionEditorSample, String editing_mode) {
		super(editingContext, singleCompositionEditorSample, editing_mode);
		parts = new String[] { BASE_PART };
		repositoryKey = EefnrViewsRepository.class;
		partKey = EefnrViewsRepository.SingleCompositionEditorSample.class;
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
			final SingleCompositionEditorSample singleCompositionEditorSample = (SingleCompositionEditorSample)elt;
			final SingleCompositionEditorSamplePropertiesEditionPart basePart = (SingleCompositionEditorSamplePropertiesEditionPart)editingPart;
			// init values
			if (isAccessible(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredProperty)) {
				// init part
				singleCompositionEditorRequiredPropertySettings = new EObjectFlatComboSettings(singleCompositionEditorSample, EefnrPackage.eINSTANCE.getSingleCompositionEditorSample_SingleCompositionEditorRequiredProperty());
				basePart.initSingleCompositionEditorRequiredProperty(singleCompositionEditorRequiredPropertySettings);
			}
			if (isAccessible(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalProperty)) {
				// init part
				singleCompositionEditorOptionalPropertySettings = new EObjectFlatComboSettings(singleCompositionEditorSample, EefnrPackage.eINSTANCE.getSingleCompositionEditorSample_SingleCompositionEditorrOptionalProperty());
				basePart.initSingleCompositionEditorOptionalProperty(singleCompositionEditorOptionalPropertySettings);
			}
			if (isAccessible(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredAbstractProperty)) {
				// init part
				singleCompositionEditorRequiredAbstractPropertySettings = new EObjectFlatComboSettings(singleCompositionEditorSample, EefnrPackage.eINSTANCE.getSingleCompositionEditorSample_SingleCompositionEditorRequiredAbstractProperty());
				basePart.initSingleCompositionEditorRequiredAbstractProperty(singleCompositionEditorRequiredAbstractPropertySettings);
			}
			if (isAccessible(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalAbstractProperty)) {
				// init part
				singleCompositionEditorOptionalAbstractPropertySettings = new EObjectFlatComboSettings(singleCompositionEditorSample, EefnrPackage.eINSTANCE.getSingleCompositionEditorSample_SingleCompositionEditorOptionalAbstractProperty());
				basePart.initSingleCompositionEditorOptionalAbstractProperty(singleCompositionEditorOptionalAbstractPropertySettings);
			}
			// init filters
			
			
			
			
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
		if (editorKey == EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredProperty) {
			return EefnrPackage.eINSTANCE.getSingleCompositionEditorSample_SingleCompositionEditorRequiredProperty();
		}
		if (editorKey == EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalProperty) {
			return EefnrPackage.eINSTANCE.getSingleCompositionEditorSample_SingleCompositionEditorrOptionalProperty();
		}
		if (editorKey == EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredAbstractProperty) {
			return EefnrPackage.eINSTANCE.getSingleCompositionEditorSample_SingleCompositionEditorRequiredAbstractProperty();
		}
		if (editorKey == EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalAbstractProperty) {
			return EefnrPackage.eINSTANCE.getSingleCompositionEditorSample_SingleCompositionEditorOptionalAbstractProperty();
		}
		return super.associatedFeature(editorKey);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updateSemanticModel(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public void updateSemanticModel(final IPropertiesEditionEvent event) {
		SingleCompositionEditorSample singleCompositionEditorSample = (SingleCompositionEditorSample)semanticObject;
		if (EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredProperty == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.EDIT) {
				if (singleCompositionEditorRequiredPropertySettings.getValue() == "") {
					EReferencePropertiesEditionContext context = new EReferencePropertiesEditionContext(editingContext, this, singleCompositionEditorRequiredPropertySettings, editingContext.getAdapterFactory());
					PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(editingContext.getEObject(), PropertiesEditingProvider.class);
					Object result = null;
					if (provider != null) {
						PropertiesEditingPolicy policy = provider.getPolicy(context);
						if (policy instanceof CreateEditingPolicy) {
							policy.execute();
							result = ((CreateEditingPolicy) policy).getResult();
						}
					}
					if (result != null) {
						singleCompositionEditorRequiredPropertySettings.setToReference(result);
					}
				} else {
					EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, (EObject) singleCompositionEditorRequiredPropertySettings.getValue(), editingContext.getAdapterFactory());
					PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(singleCompositionEditorRequiredPropertySettings.getValue(), PropertiesEditingProvider.class);
					if (provider != null) {
						PropertiesEditingPolicy policy = provider.getPolicy(context);
						if (policy != null) {
							policy.execute();
						}
					}
				}
			} else if (event.getKind() == PropertiesEditionEvent.UNSET) {
				singleCompositionEditorRequiredPropertySettings.setToReference(null);
			}
			
		}
		if (EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalProperty == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.EDIT) {
				if (singleCompositionEditorOptionalPropertySettings.getValue() == "") {
					EReferencePropertiesEditionContext context = new EReferencePropertiesEditionContext(editingContext, this, singleCompositionEditorOptionalPropertySettings, editingContext.getAdapterFactory());
					PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(editingContext.getEObject(), PropertiesEditingProvider.class);
					Object result = null;
					if (provider != null) {
						PropertiesEditingPolicy policy = provider.getPolicy(context);
						if (policy instanceof CreateEditingPolicy) {
							policy.execute();
							result = ((CreateEditingPolicy) policy).getResult();
						}
					}
					if (result != null) {
						singleCompositionEditorOptionalPropertySettings.setToReference(result);
					}
				} else {
					EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, (EObject) singleCompositionEditorOptionalPropertySettings.getValue(), editingContext.getAdapterFactory());
					PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(singleCompositionEditorOptionalPropertySettings.getValue(), PropertiesEditingProvider.class);
					if (provider != null) {
						PropertiesEditingPolicy policy = provider.getPolicy(context);
						if (policy != null) {
							policy.execute();
						}
					}
				}
			} else if (event.getKind() == PropertiesEditionEvent.UNSET) {
				singleCompositionEditorOptionalPropertySettings.setToReference(null);
			}
			
		}
		if (EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredAbstractProperty == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.EDIT) {
				if (singleCompositionEditorRequiredAbstractPropertySettings.getValue() == "") {
					EReferencePropertiesEditionContext context = new EReferencePropertiesEditionContext(editingContext, this, singleCompositionEditorRequiredAbstractPropertySettings, editingContext.getAdapterFactory());
					PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(editingContext.getEObject(), PropertiesEditingProvider.class);
					Object result = null;
					if (provider != null) {
						PropertiesEditingPolicy policy = provider.getPolicy(context);
						if (policy instanceof CreateEditingPolicy) {
							policy.execute();
							result = ((CreateEditingPolicy) policy).getResult();
						}
					}
					if (result != null) {
						singleCompositionEditorRequiredAbstractPropertySettings.setToReference(result);
					}
				} else {
					EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, (EObject) singleCompositionEditorRequiredAbstractPropertySettings.getValue(), editingContext.getAdapterFactory());
					PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(singleCompositionEditorRequiredAbstractPropertySettings.getValue(), PropertiesEditingProvider.class);
					if (provider != null) {
						PropertiesEditingPolicy policy = provider.getPolicy(context);
						if (policy != null) {
							policy.execute();
						}
					}
				}
			} else if (event.getKind() == PropertiesEditionEvent.UNSET) {
				singleCompositionEditorRequiredAbstractPropertySettings.setToReference(null);
			}
			
		}
		if (EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalAbstractProperty == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.EDIT) {
				if (singleCompositionEditorOptionalAbstractPropertySettings.getValue() == "") {
					EReferencePropertiesEditionContext context = new EReferencePropertiesEditionContext(editingContext, this, singleCompositionEditorOptionalAbstractPropertySettings, editingContext.getAdapterFactory());
					PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(editingContext.getEObject(), PropertiesEditingProvider.class);
					Object result = null;
					if (provider != null) {
						PropertiesEditingPolicy policy = provider.getPolicy(context);
						if (policy instanceof CreateEditingPolicy) {
							policy.execute();
							result = ((CreateEditingPolicy) policy).getResult();
						}
					}
					if (result != null) {
						singleCompositionEditorOptionalAbstractPropertySettings.setToReference(result);
					}
				} else {
					EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, (EObject) singleCompositionEditorOptionalAbstractPropertySettings.getValue(), editingContext.getAdapterFactory());
					PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(singleCompositionEditorOptionalAbstractPropertySettings.getValue(), PropertiesEditingProvider.class);
					if (provider != null) {
						PropertiesEditingPolicy policy = provider.getPolicy(context);
						if (policy != null) {
							policy.execute();
						}
					}
				}
			} else if (event.getKind() == PropertiesEditionEvent.UNSET) {
				singleCompositionEditorOptionalAbstractPropertySettings.setToReference(null);
			}
			
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updatePart(org.eclipse.emf.common.notify.Notification)
	 */
	public void updatePart(Notification msg) {
		if (editingPart.isVisible()) {
			SingleCompositionEditorSamplePropertiesEditionPart basePart = (SingleCompositionEditorSamplePropertiesEditionPart)editingPart;
			if (EefnrPackage.eINSTANCE.getSingleCompositionEditorSample_SingleCompositionEditorRequiredProperty().equals(msg.getFeature()) && basePart != null && isAccessible(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredProperty))
				basePart.setSingleCompositionEditorRequiredProperty((EObject)msg.getNewValue());
			if (EefnrPackage.eINSTANCE.getSingleCompositionEditorSample_SingleCompositionEditorrOptionalProperty().equals(msg.getFeature()) && basePart != null && isAccessible(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalProperty))
				basePart.setSingleCompositionEditorOptionalProperty((EObject)msg.getNewValue());
			if (EefnrPackage.eINSTANCE.getSingleCompositionEditorSample_SingleCompositionEditorRequiredAbstractProperty().equals(msg.getFeature()) && basePart != null && isAccessible(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredAbstractProperty))
				basePart.setSingleCompositionEditorRequiredAbstractProperty((EObject)msg.getNewValue());
			if (EefnrPackage.eINSTANCE.getSingleCompositionEditorSample_SingleCompositionEditorOptionalAbstractProperty().equals(msg.getFeature()) && basePart != null && isAccessible(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalAbstractProperty))
				basePart.setSingleCompositionEditorOptionalAbstractProperty((EObject)msg.getNewValue());
			
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#isRequired(java.lang.Object, int)
	 * 
	 */
	public boolean isRequired(Object key, int kind) {
		return key == EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredProperty || key == EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredAbstractProperty;
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
			} catch (IllegalArgumentException iae) {
				ret = BasicDiagnostic.toDiagnostic(iae);
			} catch (WrappedException we) {
				ret = BasicDiagnostic.toDiagnostic(we);
			}
		}
		return ret;
	}

}
