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
import org.eclipse.emf.eef.eefnr.AdvancedEObjectFlatComboViewerSample;
import org.eclipse.emf.eef.eefnr.EefnrFactory;
import org.eclipse.emf.eef.eefnr.EefnrPackage;
import org.eclipse.emf.eef.eefnr.TotalSample;
import org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart;
import org.eclipse.emf.eef.eefnr.parts.EefnrViewsRepository;
import org.eclipse.emf.eef.runtime.api.notify.EStructuralFeatureNotificationFilter;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.api.notify.NotificationFilter;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.context.impl.EReferencePropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.components.SinglePartPropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.impl.CreateEditingPolicy;
import org.eclipse.emf.eef.runtime.providers.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.ui.widgets.ButtonsModeEnum;
import org.eclipse.emf.eef.runtime.ui.widgets.eobjflatcombo.EObjectFlatComboSettings;


// End of user code

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class AdvancedEObjectFlatComboViewerSamplePropertiesEditionComponent extends SinglePartPropertiesEditingComponent {

	
	public static String BASE_PART = "Base"; //$NON-NLS-1$

	
	/**
	 * Settings for advancedeobjectflatcomboviewerRequiredProperty EObjectFlatComboViewer
	 */
	private EObjectFlatComboSettings advancedeobjectflatcomboviewerRequiredPropertySettings;
	
	/**
	 * Settings for advancedeobjectflatcomboviewerOptionalProperty EObjectFlatComboViewer
	 */
	private EObjectFlatComboSettings advancedeobjectflatcomboviewerOptionalPropertySettings;
	
	/**
	 * Settings for advancedeobjectflatcomboviewerROProperty EObjectFlatComboViewer
	 */
	private EObjectFlatComboSettings advancedeobjectflatcomboviewerROPropertySettings;
	
	/**
	 * Settings for advancedeobjectflatcomboviewerCompoRequiredProperty EObjectFlatComboViewer
	 */
	private EObjectFlatComboSettings advancedeobjectflatcomboviewerCompoRequiredPropertySettings;
	
	/**
	 * Settings for advancedeobjectflatcomboviewerCompoOptionalProperty EObjectFlatComboViewer
	 */
	private EObjectFlatComboSettings advancedeobjectflatcomboviewerCompoOptionalPropertySettings;
	
	
	/**
	 * Default constructor
	 * 
	 */
	public AdvancedEObjectFlatComboViewerSamplePropertiesEditionComponent(PropertiesEditingContext editingContext, EObject advancedEObjectFlatComboViewerSample, String editing_mode) {
		super(editingContext, advancedEObjectFlatComboViewerSample, editing_mode);
		parts = new String[] { BASE_PART };
		repositoryKey = EefnrViewsRepository.class;
		partKey = EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.class;
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
			final AdvancedEObjectFlatComboViewerSample advancedEObjectFlatComboViewerSample = (AdvancedEObjectFlatComboViewerSample)elt;
			final AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart basePart = (AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart)editingPart;
			// init values
			if (isAccessible(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerRequiredProperty)) {
				// init part
				advancedeobjectflatcomboviewerRequiredPropertySettings = new EObjectFlatComboSettings(advancedEObjectFlatComboViewerSample, EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerRequiredProperty());
				basePart.initAdvancedeobjectflatcomboviewerRequiredProperty(advancedeobjectflatcomboviewerRequiredPropertySettings);
				// set the button mode
				basePart.setAdvancedeobjectflatcomboviewerRequiredPropertyButtonMode(ButtonsModeEnum.BROWSE);
			}
			if (isAccessible(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerOptionalProperty)) {
				// init part
				advancedeobjectflatcomboviewerOptionalPropertySettings = new EObjectFlatComboSettings(advancedEObjectFlatComboViewerSample, EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerOptionalProperty());
				basePart.initAdvancedeobjectflatcomboviewerOptionalProperty(advancedeobjectflatcomboviewerOptionalPropertySettings);
				// set the button mode
				basePart.setAdvancedeobjectflatcomboviewerOptionalPropertyButtonMode(ButtonsModeEnum.BROWSE);
			}
			if (isAccessible(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerROProperty)) {
				// init part
				advancedeobjectflatcomboviewerROPropertySettings = new EObjectFlatComboSettings(advancedEObjectFlatComboViewerSample, EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerROPropery());
				basePart.initAdvancedeobjectflatcomboviewerROProperty(advancedeobjectflatcomboviewerROPropertySettings);
				// set the button mode
				basePart.setAdvancedeobjectflatcomboviewerROPropertyButtonMode(ButtonsModeEnum.BROWSE);
			}
			if (isAccessible(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoRequiredProperty)) {
				// init part
				advancedeobjectflatcomboviewerCompoRequiredPropertySettings = new EObjectFlatComboSettings(advancedEObjectFlatComboViewerSample, EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerCompoRequiredProperty());
				basePart.initAdvancedeobjectflatcomboviewerCompoRequiredProperty(advancedeobjectflatcomboviewerCompoRequiredPropertySettings);
				// set the button mode
				basePart.setAdvancedeobjectflatcomboviewerCompoRequiredPropertyButtonMode(ButtonsModeEnum.CREATE);
			}
			if (isAccessible(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoOptionalProperty)) {
				// init part
				advancedeobjectflatcomboviewerCompoOptionalPropertySettings = new EObjectFlatComboSettings(advancedEObjectFlatComboViewerSample, EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerCompoOptionalProperty());
				basePart.initAdvancedeobjectflatcomboviewerCompoOptionalProperty(advancedeobjectflatcomboviewerCompoOptionalPropertySettings);
				// set the button mode
				basePart.setAdvancedeobjectflatcomboviewerCompoOptionalPropertyButtonMode(ButtonsModeEnum.CREATE);
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
		if (editorKey == EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerRequiredProperty) {
			return EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerRequiredProperty();
		}
		if (editorKey == EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerOptionalProperty) {
			return EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerOptionalProperty();
		}
		if (editorKey == EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerROProperty) {
			return EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerROPropery();
		}
		if (editorKey == EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoRequiredProperty) {
			return EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerCompoRequiredProperty();
		}
		if (editorKey == EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoOptionalProperty) {
			return EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerCompoOptionalProperty();
		}
		return super.associatedFeature(editorKey);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updateSemanticModel(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public void updateSemanticModel(final IPropertiesEditionEvent event) {
		AdvancedEObjectFlatComboViewerSample advancedEObjectFlatComboViewerSample = (AdvancedEObjectFlatComboViewerSample)semanticObject;
		if (EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerRequiredProperty == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.SET) {
				advancedeobjectflatcomboviewerRequiredPropertySettings.setToReference((TotalSample)event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.ADD) {
				TotalSample eObject = EefnrFactory.eINSTANCE.createTotalSample();
				EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, eObject, editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(eObject, PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy policy = provider.getPolicy(context);
					if (policy != null) {
						policy.execute();
					}
				}
				advancedeobjectflatcomboviewerRequiredPropertySettings.setToReference(eObject);
			}
		}
		if (EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerOptionalProperty == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.SET) {
				advancedeobjectflatcomboviewerOptionalPropertySettings.setToReference((TotalSample)event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.ADD) {
				TotalSample eObject = EefnrFactory.eINSTANCE.createTotalSample();
				EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, eObject, editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(eObject, PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy policy = provider.getPolicy(context);
					if (policy != null) {
						policy.execute();
					}
				}
				advancedeobjectflatcomboviewerOptionalPropertySettings.setToReference(eObject);
			}
		}
		if (EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoRequiredProperty == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.EDIT) {
				if (advancedeobjectflatcomboviewerCompoRequiredPropertySettings.getValue() == "") {
					EReferencePropertiesEditionContext context = new EReferencePropertiesEditionContext(editingContext, this, advancedeobjectflatcomboviewerCompoRequiredPropertySettings, editingContext.getAdapterFactory());
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
						advancedeobjectflatcomboviewerCompoRequiredPropertySettings.setToReference(result);
					}
				} else {
					EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, (EObject) advancedeobjectflatcomboviewerCompoRequiredPropertySettings.getValue(), editingContext.getAdapterFactory());
					PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(advancedeobjectflatcomboviewerCompoRequiredPropertySettings.getValue(), PropertiesEditingProvider.class);
					if (provider != null) {
						PropertiesEditingPolicy policy = provider.getPolicy(context);
						if (policy != null) {
							policy.execute();
						}
					}
				}
			} else if (event.getKind() == PropertiesEditionEvent.UNSET) {
				advancedeobjectflatcomboviewerCompoRequiredPropertySettings.setToReference(null);
			}
		}
		if (EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoOptionalProperty == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.SET) {
				advancedeobjectflatcomboviewerCompoOptionalPropertySettings.setToReference((TotalSample)event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.ADD) {
				TotalSample eObject = EefnrFactory.eINSTANCE.createTotalSample();
				EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, eObject, editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(eObject, PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy policy = provider.getPolicy(context);
					if (policy != null) {
						policy.execute();
					}
				}
				advancedeobjectflatcomboviewerCompoOptionalPropertySettings.setToReference(eObject);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updatePart(org.eclipse.emf.common.notify.Notification)
	 */
	public void updatePart(Notification msg) {
		if (editingPart.isVisible()) {
			AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart basePart = (AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart)editingPart;
			if (EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerRequiredProperty().equals(msg.getFeature()) && basePart != null && isAccessible(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerRequiredProperty))
				basePart.setAdvancedeobjectflatcomboviewerRequiredProperty((EObject)msg.getNewValue());
			if (EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerOptionalProperty().equals(msg.getFeature()) && basePart != null && isAccessible(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerOptionalProperty))
				basePart.setAdvancedeobjectflatcomboviewerOptionalProperty((EObject)msg.getNewValue());
			if (EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerROPropery().equals(msg.getFeature()) && basePart != null && isAccessible(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerROProperty))
				basePart.setAdvancedeobjectflatcomboviewerROProperty((EObject)msg.getNewValue());
			if (EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerCompoRequiredProperty().equals(msg.getFeature()) && basePart != null && isAccessible(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoRequiredProperty))
				basePart.setAdvancedeobjectflatcomboviewerCompoRequiredProperty((EObject)msg.getNewValue());
			if (EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerCompoOptionalProperty().equals(msg.getFeature()) && basePart != null && isAccessible(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoOptionalProperty))
				basePart.setAdvancedeobjectflatcomboviewerCompoOptionalProperty((EObject)msg.getNewValue());
			
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getNotificationFilters()
	 */
	@Override
	protected NotificationFilter[] getNotificationFilters() {
		NotificationFilter filter = new EStructuralFeatureNotificationFilter(
			EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerRequiredProperty(),
			EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerOptionalProperty(),
			EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerROPropery(),
			EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerCompoRequiredProperty(),
			EefnrPackage.eINSTANCE.getAdvancedEObjectFlatComboViewerSample_AdvancedeobjectflatcomboviewerCompoOptionalProperty()		);
		return new NotificationFilter[] {filter,};
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#isRequired(java.lang.Object, int)
	 * 
	 */
	public boolean isRequired(Object key, int kind) {
		return key == EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerRequiredProperty || key == EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoRequiredProperty;
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
