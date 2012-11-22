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
package org.eclipse.emf.eef.eefnr.parts.forms;

// Start of user code for imports
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.eef.eefnr.parts.EefnrViewsRepository;
import org.eclipse.emf.eef.eefnr.parts.SingleCompositionEditorSamplePropertiesEditionPart;

import org.eclipse.emf.eef.eefnr.providers.EefnrMessages;

import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;

import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;

import org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart;

import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;

import org.eclipse.emf.eef.runtime.part.impl.SectionPropertiesEditingPart;

import org.eclipse.emf.eef.runtime.ui.parts.PartComposer;

import org.eclipse.emf.eef.runtime.ui.parts.sequence.BindingCompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionStep;

import org.eclipse.emf.eef.runtime.ui.widgets.FormUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.SingleCompositionEditor;

import org.eclipse.emf.eef.runtime.ui.widgets.SingleCompositionEditor.SingleCompositionListener;

import org.eclipse.emf.eef.runtime.ui.widgets.eobjflatcombo.EObjectFlatComboSettings;

import org.eclipse.swt.SWT;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

// End of user code

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class SingleCompositionEditorSamplePropertiesEditionPartForm extends SectionPropertiesEditingPart implements IFormPropertiesEditionPart, SingleCompositionEditorSamplePropertiesEditionPart {

	protected SingleCompositionEditor singleCompositionEditorRequiredProperty;
	protected SingleCompositionEditor singleCompositionEditorOptionalProperty;
	protected SingleCompositionEditor singleCompositionEditorRequiredAbstractProperty;
	protected SingleCompositionEditor singleCompositionEditorOptionalAbstractProperty;



	/**
	 * For {@link ISection} use only.
	 */
	public SingleCompositionEditorSamplePropertiesEditionPartForm() { super(); }

	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 * 
	 */
	public SingleCompositionEditorSamplePropertiesEditionPartForm(IPropertiesEditionComponent editionComponent) {
		super(editionComponent);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart#
	 *  createFigure(org.eclipse.swt.widgets.Composite, org.eclipse.ui.forms.widgets.FormToolkit)
	 * 
	 */
	public Composite createFigure(final Composite parent, final FormToolkit widgetFactory) {
		ScrolledForm scrolledForm = widgetFactory.createScrolledForm(parent);
		Form form = scrolledForm.getForm();
		view = form.getBody();
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		view.setLayout(layout);
		createControls(widgetFactory, view);
		return scrolledForm;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart#
	 *  createControls(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 * 
	 */
	public void createControls(final FormToolkit widgetFactory, Composite view) {
		CompositionSequence singleCompositionEditorSampleStep = new BindingCompositionSequence(propertiesEditionComponent);
		CompositionStep propertiesStep = singleCompositionEditorSampleStep.addStep(EefnrViewsRepository.SingleCompositionEditorSample.Properties.class);
		propertiesStep.addStep(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredProperty);
		propertiesStep.addStep(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalProperty);
		propertiesStep.addStep(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredAbstractProperty);
		propertiesStep.addStep(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalAbstractProperty);
		
		
		composer = new PartComposer(singleCompositionEditorSampleStep) {

			@Override
			public Composite addToPart(Composite parent, Object key) {
				if (key == EefnrViewsRepository.SingleCompositionEditorSample.Properties.class) {
					return createPropertiesGroup(widgetFactory, parent);
				}
				if (key == EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredProperty) {
					return createSingleCompositionEditorRequiredPropertySingleCompositionEditor(parent, widgetFactory);
				}
				if (key == EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalProperty) {
					return createSingleCompositionEditorOptionalPropertySingleCompositionEditor(parent, widgetFactory);
				}
				if (key == EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredAbstractProperty) {
					return createSingleCompositionEditorRequiredAbstractPropertySingleCompositionEditor(parent, widgetFactory);
				}
				if (key == EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalAbstractProperty) {
					return createSingleCompositionEditorOptionalAbstractPropertySingleCompositionEditor(parent, widgetFactory);
				}
				return parent;
			}
		};
		composer.compose(view);
	}
	/**
	 * 
	 */
	protected Composite createPropertiesGroup(FormToolkit widgetFactory, final Composite parent) {
		Section propertiesSection = widgetFactory.createSection(parent, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		propertiesSection.setText(EefnrMessages.SingleCompositionEditorSamplePropertiesEditionPart_PropertiesGroupLabel);
		GridData propertiesSectionData = new GridData(GridData.FILL_HORIZONTAL);
		propertiesSectionData.horizontalSpan = 3;
		propertiesSection.setLayoutData(propertiesSectionData);
		Composite propertiesGroup = widgetFactory.createComposite(propertiesSection);
		GridLayout propertiesGroupLayout = new GridLayout();
		propertiesGroupLayout.numColumns = 3;
		propertiesGroup.setLayout(propertiesGroupLayout);
		propertiesSection.setClient(propertiesGroup);
		return propertiesGroup;
	}

	/**
	 * @param parent the parent composite
	 * @param widgetFactory factory to use to instanciante widget of the form
	 * 
	 */
	protected Composite createSingleCompositionEditorRequiredPropertySingleCompositionEditor(Composite parent, FormToolkit widgetFactory) {
		createDescription(parent, EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredProperty, EefnrMessages.SingleCompositionEditorSamplePropertiesEditionPart_SingleCompositionEditorRequiredPropertyLabel);
		//create widget
		singleCompositionEditorRequiredProperty = new SingleCompositionEditor(widgetFactory, parent, SWT.NONE);
		GridData singleCompositionEditorRequiredPropertyData = new GridData(GridData.FILL_HORIZONTAL);
		singleCompositionEditorRequiredProperty.setLayoutData(singleCompositionEditorRequiredPropertyData);
		singleCompositionEditorRequiredProperty.addEditorListener(new SingleCompositionListener() {
			
			public void edit() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(SingleCompositionEditorSamplePropertiesEditionPartForm.this,  EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.EDIT, null, null));				
				singleCompositionEditorRequiredProperty.refresh();
			}
			
			public void clear() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(SingleCompositionEditorSamplePropertiesEditionPartForm.this,  EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.UNSET, null, null));
				singleCompositionEditorRequiredProperty.refresh();
			}
		});
		singleCompositionEditorRequiredProperty.setID(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredProperty);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredProperty, EefnrViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		return parent;
	}


	/**
	 * @param parent the parent composite
	 * @param widgetFactory factory to use to instanciante widget of the form
	 * 
	 */
	protected Composite createSingleCompositionEditorOptionalPropertySingleCompositionEditor(Composite parent, FormToolkit widgetFactory) {
		createDescription(parent, EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalProperty, EefnrMessages.SingleCompositionEditorSamplePropertiesEditionPart_SingleCompositionEditorOptionalPropertyLabel);
		//create widget
		singleCompositionEditorOptionalProperty = new SingleCompositionEditor(widgetFactory, parent, SWT.NONE);
		GridData singleCompositionEditorOptionalPropertyData = new GridData(GridData.FILL_HORIZONTAL);
		singleCompositionEditorOptionalProperty.setLayoutData(singleCompositionEditorOptionalPropertyData);
		singleCompositionEditorOptionalProperty.addEditorListener(new SingleCompositionListener() {
			
			public void edit() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(SingleCompositionEditorSamplePropertiesEditionPartForm.this,  EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.EDIT, null, null));				
				singleCompositionEditorOptionalProperty.refresh();
			}
			
			public void clear() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(SingleCompositionEditorSamplePropertiesEditionPartForm.this,  EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.UNSET, null, null));
				singleCompositionEditorOptionalProperty.refresh();
			}
		});
		singleCompositionEditorOptionalProperty.setID(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalProperty);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalProperty, EefnrViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		return parent;
	}


	/**
	 * @param parent the parent composite
	 * @param widgetFactory factory to use to instanciante widget of the form
	 * 
	 */
	protected Composite createSingleCompositionEditorRequiredAbstractPropertySingleCompositionEditor(Composite parent, FormToolkit widgetFactory) {
		createDescription(parent, EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredAbstractProperty, EefnrMessages.SingleCompositionEditorSamplePropertiesEditionPart_SingleCompositionEditorRequiredAbstractPropertyLabel);
		//create widget
		singleCompositionEditorRequiredAbstractProperty = new SingleCompositionEditor(widgetFactory, parent, SWT.NONE);
		GridData singleCompositionEditorRequiredAbstractPropertyData = new GridData(GridData.FILL_HORIZONTAL);
		singleCompositionEditorRequiredAbstractProperty.setLayoutData(singleCompositionEditorRequiredAbstractPropertyData);
		singleCompositionEditorRequiredAbstractProperty.addEditorListener(new SingleCompositionListener() {
			
			public void edit() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(SingleCompositionEditorSamplePropertiesEditionPartForm.this,  EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredAbstractProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.EDIT, null, null));				
				singleCompositionEditorRequiredAbstractProperty.refresh();
			}
			
			public void clear() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(SingleCompositionEditorSamplePropertiesEditionPartForm.this,  EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredAbstractProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.UNSET, null, null));
				singleCompositionEditorRequiredAbstractProperty.refresh();
			}
		});
		singleCompositionEditorRequiredAbstractProperty.setID(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredAbstractProperty);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorRequiredAbstractProperty, EefnrViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		return parent;
	}


	/**
	 * @param parent the parent composite
	 * @param widgetFactory factory to use to instanciante widget of the form
	 * 
	 */
	protected Composite createSingleCompositionEditorOptionalAbstractPropertySingleCompositionEditor(Composite parent, FormToolkit widgetFactory) {
		createDescription(parent, EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalAbstractProperty, EefnrMessages.SingleCompositionEditorSamplePropertiesEditionPart_SingleCompositionEditorOptionalAbstractPropertyLabel);
		//create widget
		singleCompositionEditorOptionalAbstractProperty = new SingleCompositionEditor(widgetFactory, parent, SWT.NONE);
		GridData singleCompositionEditorOptionalAbstractPropertyData = new GridData(GridData.FILL_HORIZONTAL);
		singleCompositionEditorOptionalAbstractProperty.setLayoutData(singleCompositionEditorOptionalAbstractPropertyData);
		singleCompositionEditorOptionalAbstractProperty.addEditorListener(new SingleCompositionListener() {
			
			public void edit() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(SingleCompositionEditorSamplePropertiesEditionPartForm.this,  EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalAbstractProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.EDIT, null, null));				
				singleCompositionEditorOptionalAbstractProperty.refresh();
			}
			
			public void clear() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(SingleCompositionEditorSamplePropertiesEditionPartForm.this,  EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalAbstractProperty, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.UNSET, null, null));
				singleCompositionEditorOptionalAbstractProperty.refresh();
			}
		});
		singleCompositionEditorOptionalAbstractProperty.setID(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalAbstractProperty);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EefnrViewsRepository.SingleCompositionEditorSample.Properties.singleCompositionEditorOptionalAbstractProperty, EefnrViewsRepository.FORM_KIND), null); //$NON-NLS-1$
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
	 * @see org.eclipse.emf.eef.eefnr.parts.SingleCompositionEditorSamplePropertiesEditionPart#getSingleCompositionEditorRequiredProperty()
	 * 
	 */
	public EObject getSingleCompositionEditorRequiredProperty() {
		return (EObject) singleCompositionEditorRequiredProperty.getInput();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.SingleCompositionEditorSamplePropertiesEditionPart#initSingleCompositionEditorRequiredProperty(EObjectFlatComboSettings)
	 */
	public void initSingleCompositionEditorRequiredProperty(EObjectFlatComboSettings settings) {
		singleCompositionEditorRequiredProperty.setAdapterFactory(adapterFactory);
		singleCompositionEditorRequiredProperty.setInput(settings);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.SingleCompositionEditorSamplePropertiesEditionPart#setSingleCompositionEditorRequiredProperty(EObject newValue)
	 * 
	 */
	public void setSingleCompositionEditorRequiredProperty(EObject newValue) {
		singleCompositionEditorRequiredProperty.refresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.SingleCompositionEditorSamplePropertiesEditionPart#getSingleCompositionEditorOptionalProperty()
	 * 
	 */
	public EObject getSingleCompositionEditorOptionalProperty() {
		return (EObject) singleCompositionEditorOptionalProperty.getInput();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.SingleCompositionEditorSamplePropertiesEditionPart#initSingleCompositionEditorOptionalProperty(EObjectFlatComboSettings)
	 */
	public void initSingleCompositionEditorOptionalProperty(EObjectFlatComboSettings settings) {
		singleCompositionEditorOptionalProperty.setAdapterFactory(adapterFactory);
		singleCompositionEditorOptionalProperty.setInput(settings);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.SingleCompositionEditorSamplePropertiesEditionPart#setSingleCompositionEditorOptionalProperty(EObject newValue)
	 * 
	 */
	public void setSingleCompositionEditorOptionalProperty(EObject newValue) {
		singleCompositionEditorOptionalProperty.refresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.SingleCompositionEditorSamplePropertiesEditionPart#getSingleCompositionEditorRequiredAbstractProperty()
	 * 
	 */
	public EObject getSingleCompositionEditorRequiredAbstractProperty() {
		return (EObject) singleCompositionEditorRequiredAbstractProperty.getInput();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.SingleCompositionEditorSamplePropertiesEditionPart#initSingleCompositionEditorRequiredAbstractProperty(EObjectFlatComboSettings)
	 */
	public void initSingleCompositionEditorRequiredAbstractProperty(EObjectFlatComboSettings settings) {
		singleCompositionEditorRequiredAbstractProperty.setAdapterFactory(adapterFactory);
		singleCompositionEditorRequiredAbstractProperty.setInput(settings);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.SingleCompositionEditorSamplePropertiesEditionPart#setSingleCompositionEditorRequiredAbstractProperty(EObject newValue)
	 * 
	 */
	public void setSingleCompositionEditorRequiredAbstractProperty(EObject newValue) {
		singleCompositionEditorRequiredAbstractProperty.refresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.SingleCompositionEditorSamplePropertiesEditionPart#getSingleCompositionEditorOptionalAbstractProperty()
	 * 
	 */
	public EObject getSingleCompositionEditorOptionalAbstractProperty() {
		return (EObject) singleCompositionEditorOptionalAbstractProperty.getInput();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.SingleCompositionEditorSamplePropertiesEditionPart#initSingleCompositionEditorOptionalAbstractProperty(EObjectFlatComboSettings)
	 */
	public void initSingleCompositionEditorOptionalAbstractProperty(EObjectFlatComboSettings settings) {
		singleCompositionEditorOptionalAbstractProperty.setAdapterFactory(adapterFactory);
		singleCompositionEditorOptionalAbstractProperty.setInput(settings);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.eefnr.parts.SingleCompositionEditorSamplePropertiesEditionPart#setSingleCompositionEditorOptionalAbstractProperty(EObject newValue)
	 * 
	 */
	public void setSingleCompositionEditorOptionalAbstractProperty(EObject newValue) {
		singleCompositionEditorOptionalAbstractProperty.refresh();
	}






	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart#getTitle()
	 * 
	 */
	public String getTitle() {
		return EefnrMessages.SingleCompositionEditorSample_Part_Title;
	}

	// Start of user code additional methods
	
	// End of user code


}
