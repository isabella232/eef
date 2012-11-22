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
package org.eclipse.emf.eef.eefnr.parts.impl;

// Start of user code for imports
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart;
import org.eclipse.emf.eef.eefnr.parts.EefnrViewsRepository;
import org.eclipse.emf.eef.eefnr.providers.EefnrMessages;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.api.parts.ISWTPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.parts.CompositePropertiesEditionPart;
import org.eclipse.emf.eef.runtime.ui.parts.PartComposer;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.BindingCompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionStep;
import org.eclipse.emf.eef.runtime.ui.widgets.AdvancedEObjectFlatComboViewer;
import org.eclipse.emf.eef.runtime.ui.widgets.AdvancedEObjectFlatComboViewer.EObjectFlatComboViewerListener;
import org.eclipse.emf.eef.runtime.ui.widgets.ButtonsModeEnum;
import org.eclipse.emf.eef.runtime.ui.widgets.SingleCompositionEditor.SingleCompositionListener;
import org.eclipse.emf.eef.runtime.ui.widgets.SWTUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.SingleCompositionEditor;
import org.eclipse.emf.eef.runtime.ui.widgets.eobjflatcombo.EObjectFlatComboSettings;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;


// End of user code

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class AdvancedEObjectFlatComboViewerSamplePropertiesEditionPartImpl extends CompositePropertiesEditionPart implements ISWTPropertiesEditionPart, AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart {

	private AdvancedEObjectFlatComboViewer advancedeobjectflatcomboviewerRequiredProperty;
	protected ViewerFilter advancedeobjectflatcomboviewerRequiredPropertyFilter;
	private AdvancedEObjectFlatComboViewer advancedeobjectflatcomboviewerOptionalProperty;
	protected ViewerFilter advancedeobjectflatcomboviewerOptionalPropertyFilter;
	private AdvancedEObjectFlatComboViewer advancedeobjectflatcomboviewerROProperty;
	protected ViewerFilter advancedeobjectflatcomboviewerROPropertyFilter;
	private SingleCompositionEditor advancedeobjectflatcomboviewerCompoRequiredProperty;
	protected ViewerFilter advancedeobjectflatcomboviewerCompoRequiredPropertyFilter;
	private AdvancedEObjectFlatComboViewer advancedeobjectflatcomboviewerCompoOptionalProperty;
	protected ViewerFilter advancedeobjectflatcomboviewerCompoOptionalPropertyFilter;



	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 * 
	 */
	public AdvancedEObjectFlatComboViewerSamplePropertiesEditionPartImpl(IPropertiesEditionComponent editionComponent) {
		super(editionComponent);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.parts.ISWTPropertiesEditionPart#
	 * 			createFigure(org.eclipse.swt.widgets.Composite)
	 * 
	 */
	public Composite createFigure(final Composite parent) {
		view = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		view.setLayout(layout);
		createControls(view);
		return view;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.parts.ISWTPropertiesEditionPart#
	 * 			createControls(org.eclipse.swt.widgets.Composite)
	 * 
	 */
	public void createControls(Composite view) { 
		CompositionSequence advancedEObjectFlatComboViewerSampleStep = new BindingCompositionSequence(propertiesEditionComponent);
		CompositionStep propertiesStep = advancedEObjectFlatComboViewerSampleStep.addStep(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.class);
		propertiesStep.addStep(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerRequiredProperty);
		propertiesStep.addStep(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerOptionalProperty);
		propertiesStep.addStep(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerROProperty);
		propertiesStep.addStep(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoRequiredProperty);
		propertiesStep.addStep(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoOptionalProperty);
		
		
		composer = new PartComposer(advancedEObjectFlatComboViewerSampleStep) {

			@Override
			public Composite addToPart(Composite parent, Object key) {
				if (key == EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.class) {
					return createPropertiesGroup(parent);
				}
				if (key == EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerRequiredProperty) {
					return createAdvancedeobjectflatcomboviewerRequiredPropertyAdvancedFlatComboViewer(parent);
				}
				if (key == EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerOptionalProperty) {
					return createAdvancedeobjectflatcomboviewerOptionalPropertyAdvancedFlatComboViewer(parent);
				}
				if (key == EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerROProperty) {
					return createAdvancedeobjectflatcomboviewerROPropertyAdvancedFlatComboViewer(parent);
				}
				if (key == EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoRequiredProperty) {
					return createAdvancedeobjectflatcomboviewerCompoRequiredPropertyAdvancedFlatComboViewer(parent);
				}
				if (key == EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoOptionalProperty) {
					return createAdvancedeobjectflatcomboviewerCompoOptionalPropertyAdvancedFlatComboViewer(parent);
				}
				return parent;
			}
		};
		composer.compose(view);
	}

	/**
	 * 
	 */
	protected Composite createPropertiesGroup(Composite parent) {
		Group propertiesGroup = new Group(parent, SWT.NONE);
		propertiesGroup.setText(EefnrMessages.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart_PropertiesGroupLabel);
		GridData propertiesGroupData = new GridData(GridData.FILL_HORIZONTAL);
		propertiesGroupData.horizontalSpan = 3;
		propertiesGroup.setLayoutData(propertiesGroupData);
		GridLayout propertiesGroupLayout = new GridLayout();
		propertiesGroupLayout.numColumns = 3;
		propertiesGroup.setLayout(propertiesGroupLayout);
		return propertiesGroup;
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createAdvancedeobjectflatcomboviewerRequiredPropertyAdvancedFlatComboViewer(Composite parent) {
		createDescription(parent, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerRequiredProperty, EefnrMessages.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart_AdvancedeobjectflatcomboviewerRequiredPropertyLabel);
		// create callback listener
		EObjectFlatComboViewerListener listener = new EObjectFlatComboViewerListener(){
			public void handleSet(EObject element){
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AdvancedEObjectFlatComboViewerSamplePropertiesEditionPartImpl.this, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerRequiredProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, element)); 
			}
			public void navigateTo(EObject element){ }

			public EObject handleCreate() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AdvancedEObjectFlatComboViewerSamplePropertiesEditionPartImpl.this, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerRequiredProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, null)); 
				return null;
			}
		};
		//create widget
		advancedeobjectflatcomboviewerRequiredProperty = new AdvancedEObjectFlatComboViewer(getDescription(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerRequiredProperty, EefnrMessages.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart_AdvancedeobjectflatcomboviewerRequiredPropertyLabel), resourceSet, advancedeobjectflatcomboviewerRequiredPropertyFilter, propertiesEditionComponent.getEditingContext().getAdapterFactory(), listener);
		advancedeobjectflatcomboviewerRequiredProperty.createControls(parent);
		GridData advancedeobjectflatcomboviewerRequiredPropertyData = new GridData(GridData.FILL_HORIZONTAL);
		advancedeobjectflatcomboviewerRequiredProperty.setLayoutData(advancedeobjectflatcomboviewerRequiredPropertyData);
		advancedeobjectflatcomboviewerRequiredProperty.setID(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerRequiredProperty);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerRequiredProperty, EefnrViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createAdvancedeobjectflatcomboviewerOptionalPropertyAdvancedFlatComboViewer(Composite parent) {
		createDescription(parent, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerOptionalProperty, EefnrMessages.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart_AdvancedeobjectflatcomboviewerOptionalPropertyLabel);
		// create callback listener
		EObjectFlatComboViewerListener listener = new EObjectFlatComboViewerListener(){
			public void handleSet(EObject element){
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AdvancedEObjectFlatComboViewerSamplePropertiesEditionPartImpl.this, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerOptionalProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, element)); 
			}
			public void navigateTo(EObject element){ }

			public EObject handleCreate() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AdvancedEObjectFlatComboViewerSamplePropertiesEditionPartImpl.this, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerOptionalProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, null)); 
				return null;
			}
		};
		//create widget
		advancedeobjectflatcomboviewerOptionalProperty = new AdvancedEObjectFlatComboViewer(getDescription(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerOptionalProperty, EefnrMessages.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart_AdvancedeobjectflatcomboviewerOptionalPropertyLabel), resourceSet, advancedeobjectflatcomboviewerOptionalPropertyFilter, propertiesEditionComponent.getEditingContext().getAdapterFactory(), listener);
		advancedeobjectflatcomboviewerOptionalProperty.createControls(parent);
		GridData advancedeobjectflatcomboviewerOptionalPropertyData = new GridData(GridData.FILL_HORIZONTAL);
		advancedeobjectflatcomboviewerOptionalProperty.setLayoutData(advancedeobjectflatcomboviewerOptionalPropertyData);
		advancedeobjectflatcomboviewerOptionalProperty.setID(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerOptionalProperty);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerOptionalProperty, EefnrViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createAdvancedeobjectflatcomboviewerROPropertyAdvancedFlatComboViewer(Composite parent) {
		createDescription(parent, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerROProperty, EefnrMessages.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart_AdvancedeobjectflatcomboviewerROPropertyLabel);
		// create callback listener
		EObjectFlatComboViewerListener listener = new EObjectFlatComboViewerListener(){
			public void handleSet(EObject element){
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AdvancedEObjectFlatComboViewerSamplePropertiesEditionPartImpl.this, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerROProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, element)); 
			}
			public void navigateTo(EObject element){ }

			public EObject handleCreate() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AdvancedEObjectFlatComboViewerSamplePropertiesEditionPartImpl.this, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerROProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, null)); 
				return null;
			}
		};
		//create widget
		advancedeobjectflatcomboviewerROProperty = new AdvancedEObjectFlatComboViewer(getDescription(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerROProperty, EefnrMessages.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart_AdvancedeobjectflatcomboviewerROPropertyLabel), resourceSet, advancedeobjectflatcomboviewerROPropertyFilter, propertiesEditionComponent.getEditingContext().getAdapterFactory(), listener);
		advancedeobjectflatcomboviewerROProperty.createControls(parent);
		advancedeobjectflatcomboviewerROProperty.setEnabled(false);
		advancedeobjectflatcomboviewerROProperty.setToolTipText(EefnrMessages.AdvancedEObjectFlatComboViewerSample_ReadOnly);
		GridData advancedeobjectflatcomboviewerROPropertyData = new GridData(GridData.FILL_HORIZONTAL);
		advancedeobjectflatcomboviewerROProperty.setLayoutData(advancedeobjectflatcomboviewerROPropertyData);
		advancedeobjectflatcomboviewerROProperty.setID(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerROProperty);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerROProperty, EefnrViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createAdvancedeobjectflatcomboviewerCompoRequiredPropertyAdvancedFlatComboViewer(Composite parent) {
		createDescription(parent, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoRequiredProperty, EefnrMessages.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart_AdvancedeobjectflatcomboviewerCompoRequiredPropertyLabel);
		// create callback listener
//		EObjectFlatComboViewerListener listener = new EObjectFlatComboViewerListener(){
//			public void handleSet(EObject element){
//				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AdvancedEObjectFlatComboViewerSamplePropertiesEditionPartImpl.this, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoRequiredProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, element)); 
//			}
//			public void navigateTo(EObject element){ }
//
//			public EObject handleCreate() {
//				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AdvancedEObjectFlatComboViewerSamplePropertiesEditionPartImpl.this, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoRequiredProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, null)); 
//				return null;
//			}
//		};
		//create widget
		advancedeobjectflatcomboviewerCompoRequiredProperty = new SingleCompositionEditor(parent, SWT.NONE);
//		advancedeobjectflatcomboviewerCompoRequiredProperty.createControls(parent);
		GridData advancedeobjectflatcomboviewerCompoRequiredPropertyData = new GridData(GridData.FILL_HORIZONTAL);
		advancedeobjectflatcomboviewerCompoRequiredProperty.setLayoutData(advancedeobjectflatcomboviewerCompoRequiredPropertyData);
		advancedeobjectflatcomboviewerCompoRequiredProperty.addEditorListener(new SingleCompositionListener() {
			
			public void edit() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AdvancedEObjectFlatComboViewerSamplePropertiesEditionPartImpl.this, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoRequiredProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.EDIT, null, null));				
				advancedeobjectflatcomboviewerCompoRequiredProperty.refresh();
			}
			
			public void clear() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AdvancedEObjectFlatComboViewerSamplePropertiesEditionPartImpl.this, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoRequiredProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.UNSET, null, null));
				advancedeobjectflatcomboviewerCompoRequiredProperty.refresh();
			}
		});
		//TODO: TODO
//		advancedeobjectflatcomboviewerCompoRequiredProperty.setID(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoRequiredProperty);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoRequiredProperty, EefnrViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createAdvancedeobjectflatcomboviewerCompoOptionalPropertyAdvancedFlatComboViewer(Composite parent) {
		createDescription(parent, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoOptionalProperty, EefnrMessages.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart_AdvancedeobjectflatcomboviewerCompoOptionalPropertyLabel);
		// create callback listener
		EObjectFlatComboViewerListener listener = new EObjectFlatComboViewerListener(){
			public void handleSet(EObject element){
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AdvancedEObjectFlatComboViewerSamplePropertiesEditionPartImpl.this, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoOptionalProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, element)); 
			}
			public void navigateTo(EObject element){ }

			public EObject handleCreate() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AdvancedEObjectFlatComboViewerSamplePropertiesEditionPartImpl.this, EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoOptionalProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, null)); 
				return null;
			}
		};
		//create widget
		advancedeobjectflatcomboviewerCompoOptionalProperty = new AdvancedEObjectFlatComboViewer(getDescription(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoOptionalProperty, EefnrMessages.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart_AdvancedeobjectflatcomboviewerCompoOptionalPropertyLabel), resourceSet, advancedeobjectflatcomboviewerCompoOptionalPropertyFilter, propertiesEditionComponent.getEditingContext().getAdapterFactory(), listener);
		advancedeobjectflatcomboviewerCompoOptionalProperty.createControls(parent);
		GridData advancedeobjectflatcomboviewerCompoOptionalPropertyData = new GridData(GridData.FILL_HORIZONTAL);
		advancedeobjectflatcomboviewerCompoOptionalProperty.setLayoutData(advancedeobjectflatcomboviewerCompoOptionalPropertyData);
		advancedeobjectflatcomboviewerCompoOptionalProperty.setID(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoOptionalProperty);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EefnrViewsRepository.AdvancedEObjectFlatComboViewerSample.Properties.advancedeobjectflatcomboviewerCompoOptionalProperty, EefnrViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public void firePropertiesChanged(IPropertiesEditionEvent event) {
		// Start of user code for tab synchronization
		
		// End of user code
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#getAdvancedeobjectflatcomboviewerRequiredProperty()
	 * 
	 */
	public EObject getAdvancedeobjectflatcomboviewerRequiredProperty() {
		return advancedeobjectflatcomboviewerRequiredProperty.getSelection();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#initAdvancedeobjectflatcomboviewerRequiredProperty(EObjectFlatComboSettings)
	 */
	public void initAdvancedeobjectflatcomboviewerRequiredProperty(EObjectFlatComboSettings settings) {
		advancedeobjectflatcomboviewerRequiredProperty.setInput(settings);
		if (current != null) {
			advancedeobjectflatcomboviewerRequiredProperty.setSelection(new StructuredSelection(settings.getValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#setAdvancedeobjectflatcomboviewerRequiredProperty(EObject newValue)
	 * 
	 */
	public void setAdvancedeobjectflatcomboviewerRequiredProperty(EObject newValue) {
		if (newValue != null) {
			advancedeobjectflatcomboviewerRequiredProperty.setSelection(new StructuredSelection(newValue));
		} else {
			advancedeobjectflatcomboviewerRequiredProperty.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#setAdvancedeobjectflatcomboviewerRequiredPropertyButtonMode(ButtonsModeEnum newValue)
	 */
	public void setAdvancedeobjectflatcomboviewerRequiredPropertyButtonMode(ButtonsModeEnum newValue) {
		advancedeobjectflatcomboviewerRequiredProperty.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#addFilterAdvancedeobjectflatcomboviewerRequiredProperty(ViewerFilter filter)
	 * 
	 */
	public void addFilterToAdvancedeobjectflatcomboviewerRequiredProperty(ViewerFilter filter) {
		advancedeobjectflatcomboviewerRequiredProperty.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#addBusinessFilterAdvancedeobjectflatcomboviewerRequiredProperty(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToAdvancedeobjectflatcomboviewerRequiredProperty(ViewerFilter filter) {
		advancedeobjectflatcomboviewerRequiredProperty.addBusinessRuleFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#getAdvancedeobjectflatcomboviewerOptionalProperty()
	 * 
	 */
	public EObject getAdvancedeobjectflatcomboviewerOptionalProperty() {
		return advancedeobjectflatcomboviewerOptionalProperty.getSelection();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#initAdvancedeobjectflatcomboviewerOptionalProperty(EObjectFlatComboSettings)
	 */
	public void initAdvancedeobjectflatcomboviewerOptionalProperty(EObjectFlatComboSettings settings) {
		advancedeobjectflatcomboviewerOptionalProperty.setInput(settings);
		if (current != null) {
			advancedeobjectflatcomboviewerOptionalProperty.setSelection(new StructuredSelection(settings.getValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#setAdvancedeobjectflatcomboviewerOptionalProperty(EObject newValue)
	 * 
	 */
	public void setAdvancedeobjectflatcomboviewerOptionalProperty(EObject newValue) {
		if (newValue != null) {
			advancedeobjectflatcomboviewerOptionalProperty.setSelection(new StructuredSelection(newValue));
		} else {
			advancedeobjectflatcomboviewerOptionalProperty.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#setAdvancedeobjectflatcomboviewerOptionalPropertyButtonMode(ButtonsModeEnum newValue)
	 */
	public void setAdvancedeobjectflatcomboviewerOptionalPropertyButtonMode(ButtonsModeEnum newValue) {
		advancedeobjectflatcomboviewerOptionalProperty.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#addFilterAdvancedeobjectflatcomboviewerOptionalProperty(ViewerFilter filter)
	 * 
	 */
	public void addFilterToAdvancedeobjectflatcomboviewerOptionalProperty(ViewerFilter filter) {
		advancedeobjectflatcomboviewerOptionalProperty.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#addBusinessFilterAdvancedeobjectflatcomboviewerOptionalProperty(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToAdvancedeobjectflatcomboviewerOptionalProperty(ViewerFilter filter) {
		advancedeobjectflatcomboviewerOptionalProperty.addBusinessRuleFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#getAdvancedeobjectflatcomboviewerROProperty()
	 * 
	 */
	public EObject getAdvancedeobjectflatcomboviewerROProperty() {
		return advancedeobjectflatcomboviewerROProperty.getSelection();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#initAdvancedeobjectflatcomboviewerROProperty(EObjectFlatComboSettings)
	 */
	public void initAdvancedeobjectflatcomboviewerROProperty(EObjectFlatComboSettings settings) {
		advancedeobjectflatcomboviewerROProperty.setInput(settings);
		if (current != null) {
			advancedeobjectflatcomboviewerROProperty.setSelection(new StructuredSelection(settings.getValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#setAdvancedeobjectflatcomboviewerROProperty(EObject newValue)
	 * 
	 */
	public void setAdvancedeobjectflatcomboviewerROProperty(EObject newValue) {
		if (newValue != null) {
			advancedeobjectflatcomboviewerROProperty.setSelection(new StructuredSelection(newValue));
		} else {
			advancedeobjectflatcomboviewerROProperty.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#setAdvancedeobjectflatcomboviewerROPropertyButtonMode(ButtonsModeEnum newValue)
	 */
	public void setAdvancedeobjectflatcomboviewerROPropertyButtonMode(ButtonsModeEnum newValue) {
		advancedeobjectflatcomboviewerROProperty.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#addFilterAdvancedeobjectflatcomboviewerROProperty(ViewerFilter filter)
	 * 
	 */
	public void addFilterToAdvancedeobjectflatcomboviewerROProperty(ViewerFilter filter) {
		advancedeobjectflatcomboviewerROProperty.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#addBusinessFilterAdvancedeobjectflatcomboviewerROProperty(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToAdvancedeobjectflatcomboviewerROProperty(ViewerFilter filter) {
		advancedeobjectflatcomboviewerROProperty.addBusinessRuleFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#getAdvancedeobjectflatcomboviewerCompoRequiredProperty()
	 * 
	 */
	public EObject getAdvancedeobjectflatcomboviewerCompoRequiredProperty() {
		return (EObject) advancedeobjectflatcomboviewerCompoRequiredProperty.getInput();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#initAdvancedeobjectflatcomboviewerCompoRequiredProperty(EObjectFlatComboSettings)
	 */
	public void initAdvancedeobjectflatcomboviewerCompoRequiredProperty(EObjectFlatComboSettings settings) {
		advancedeobjectflatcomboviewerCompoRequiredProperty.setAdapterFactory(adapterFactory);
		advancedeobjectflatcomboviewerCompoRequiredProperty.setInput(settings);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#setAdvancedeobjectflatcomboviewerCompoRequiredProperty(EObject newValue)
	 * 
	 */
	public void setAdvancedeobjectflatcomboviewerCompoRequiredProperty(EObject newValue) {
		if (newValue != null) {
			advancedeobjectflatcomboviewerCompoRequiredProperty.setSelection(new StructuredSelection(newValue));
		} else {
			advancedeobjectflatcomboviewerCompoRequiredProperty.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#setAdvancedeobjectflatcomboviewerCompoRequiredPropertyButtonMode(ButtonsModeEnum newValue)
	 */
	public void setAdvancedeobjectflatcomboviewerCompoRequiredPropertyButtonMode(ButtonsModeEnum newValue) {
//		advancedeobjectflatcomboviewerCompoRequiredProperty.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#addFilterAdvancedeobjectflatcomboviewerCompoRequiredProperty(ViewerFilter filter)
	 * 
	 */
	public void addFilterToAdvancedeobjectflatcomboviewerCompoRequiredProperty(ViewerFilter filter) {
//		advancedeobjectflatcomboviewerCompoRequiredProperty.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#addBusinessFilterAdvancedeobjectflatcomboviewerCompoRequiredProperty(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToAdvancedeobjectflatcomboviewerCompoRequiredProperty(ViewerFilter filter) {
//		advancedeobjectflatcomboviewerCompoRequiredProperty.addBusinessRuleFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#getAdvancedeobjectflatcomboviewerCompoOptionalProperty()
	 * 
	 */
	public EObject getAdvancedeobjectflatcomboviewerCompoOptionalProperty() {
		return advancedeobjectflatcomboviewerCompoOptionalProperty.getSelection();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#initAdvancedeobjectflatcomboviewerCompoOptionalProperty(EObjectFlatComboSettings)
	 */
	public void initAdvancedeobjectflatcomboviewerCompoOptionalProperty(EObjectFlatComboSettings settings) {
		advancedeobjectflatcomboviewerCompoOptionalProperty.setInput(settings);
		if (current != null) {
			advancedeobjectflatcomboviewerCompoOptionalProperty.setSelection(new StructuredSelection(settings.getValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#setAdvancedeobjectflatcomboviewerCompoOptionalProperty(EObject newValue)
	 * 
	 */
	public void setAdvancedeobjectflatcomboviewerCompoOptionalProperty(EObject newValue) {
		if (newValue != null) {
			advancedeobjectflatcomboviewerCompoOptionalProperty.setSelection(new StructuredSelection(newValue));
		} else {
			advancedeobjectflatcomboviewerCompoOptionalProperty.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#setAdvancedeobjectflatcomboviewerCompoOptionalPropertyButtonMode(ButtonsModeEnum newValue)
	 */
	public void setAdvancedeobjectflatcomboviewerCompoOptionalPropertyButtonMode(ButtonsModeEnum newValue) {
		advancedeobjectflatcomboviewerCompoOptionalProperty.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#addFilterAdvancedeobjectflatcomboviewerCompoOptionalProperty(ViewerFilter filter)
	 * 
	 */
	public void addFilterToAdvancedeobjectflatcomboviewerCompoOptionalProperty(ViewerFilter filter) {
		advancedeobjectflatcomboviewerCompoOptionalProperty.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.AdvancedEObjectFlatComboViewerSamplePropertiesEditionPart#addBusinessFilterAdvancedeobjectflatcomboviewerCompoOptionalProperty(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToAdvancedeobjectflatcomboviewerCompoOptionalProperty(ViewerFilter filter) {
		advancedeobjectflatcomboviewerCompoOptionalProperty.addBusinessRuleFilter(filter);
	}






	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart#getTitle()
	 * 
	 */
	public String getTitle() {
		return EefnrMessages.AdvancedEObjectFlatComboViewerSample_Part_Title;
	}

	// Start of user code additional methods
	
	// End of user code


}
