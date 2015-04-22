/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.editor.internal.binding.command;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.edit.command.DragAndDropCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.ui.swt.internal.binding.settings.GenericBindingSettings;
import org.eclipse.emf.eef.runtime.ui.util.BindingSettingsBuilder;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.runtime.util.OSGiHelper;
import org.eclipse.emf.eef.views.ViewsRepository;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * Drag and drop for EObject on EditingModel element. If editing modle element
 * does not already exist, create it.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 *
 */
public abstract class AbstractDragAndDropCommand extends DragAndDropCommand {

	/**
	 * Editing domain.
	 */
	private EditingDomain domain;
	/**
	 * EClass list to drop.
	 */
	private Collection<?> elementToDrop;

	/**
	 * PropertiesEditingModel
	 */
	private PropertiesEditingModel propertiesEditingModel;
	private ViewsRepository viewsRepository;

	/**
	 * EEF Services.
	 */
	private EEFToolkitProvider eefToolkitProvider;
	private BindingSettingsBuilder builder;
	private EditingModelEnvironment editingModelEnvironment;

	/**
	 * @param domain
	 *            EditingDomain
	 * @param propertiesEditingModel
	 *            PropertiesEditingModel
	 * @param location
	 * @param operations
	 * @param operation
	 * @param collection
	 *            eClassToDrop
	 */
	public AbstractDragAndDropCommand(EditingDomain domain, PropertiesEditingModel propertiesEditingModel, float location, int operations, int operation, Collection<?> collection) {
		super(domain, propertiesEditingModel, location, operations, operation, collection);
		this.domain = domain;
		this.propertiesEditingModel = propertiesEditingModel;
		this.elementToDrop = collection;

		// init BindingSettingsBuilder
		initBindingSettingsBuilder();
	}

	/**
	 * init BindingSettingsBuilder.
	 */
	protected void initBindingSettingsBuilder() {
		viewsRepository = getViewsRepository(propertiesEditingModel.eResource());
		BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		setToolkitPropertyEditorFactory(OSGiHelper.getService(bundleContext, EEFToolkitProvider.class));
		builder = new BindingSettingsBuilder(propertiesEditingModel, viewsRepository, eefToolkitProvider, GenericBindingSettings.GROUP_CONTAINER_NAME, GenericBindingSettings.TEXT_WIDGET_NAME, GenericBindingSettings.TEXTAREA_WIDGET_NAME);
		editingModelEnvironment = new EditingModelEnvironment() {
			private ResourceSet resourceSet;
			private ECrossReferenceAdapter crossReferenceAdapter;

			/**
			 * @return the {@link ResourceSet} of this provider.
			 */
			public ResourceSet getResourceSet() {
				if (resourceSet == null) {
					resourceSet = domain.getResourceSet();
					crossReferenceAdapter = new ECrossReferenceAdapter();
					resourceSet.eAdapters().add(crossReferenceAdapter);
				}
				return resourceSet;
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment#genFeature(org.eclipse.emf.ecore.EStructuralFeature)
			 */
			public EObject genFeature(EStructuralFeature feature) {
				if (crossReferenceAdapter != null) {
					Collection<Setting> inverseReferences = crossReferenceAdapter.getInverseReferences(feature);
					for (Setting setting : inverseReferences) {
						if ("GenFeature".equals(setting.getEObject().eClass().getName())) {
							return setting.getEObject();
						}
					}
				}
				return null;
			}

		};
		editingModelEnvironment.getResourceSet();
	}

	/**
	 * @param resource
	 *            Resource
	 * @return ViewsRepository
	 */
	protected ViewsRepository getViewsRepository(Resource resource) {
		if (resource.getContents().size() == 2 && resource.getContents().get(1) instanceof ViewsRepository) {
			return (ViewsRepository) resource.getContents().get(1);
		}
		return null;
	}

	/**
	 * @param eefToolkitProvider
	 *            the eefToolkitProvider to set
	 */
	protected void setToolkitPropertyEditorFactory(EEFToolkitProvider eefToolkitProvider) {
		this.eefToolkitProvider = eefToolkitProvider;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.edit.command.DragAndDropCommand#execute()
	 */
	@Override
	public void execute() {
		// execute only drop command
		if (dropCommand.canExecute()) {
			dropCommand.execute();
		}
	}

	/**
	 * @return the domain
	 */
	public EditingDomain getDomain() {
		return domain;
	}

	/**
	 * @return the elementToDrop
	 */
	public Collection<?> getElementToDrop() {
		return elementToDrop;
	}

	/**
	 * @return the propertiesEditingModel
	 */
	public PropertiesEditingModel getPropertiesEditingModel() {
		return propertiesEditingModel;
	}

	/**
	 * @return the viewsRepository
	 */
	public ViewsRepository getViewsRepository() {
		return viewsRepository;
	}

	/**
	 * @return the eefToolkitProvider
	 */
	public EEFToolkitProvider getEefToolkitProvider() {
		return eefToolkitProvider;
	}

	/**
	 * @return the builder
	 */
	public BindingSettingsBuilder getBuilder() {
		return builder;
	}

	/**
	 * @return the editingModelEnvironment
	 */
	public EditingModelEnvironment getEditingModelEnvironment() {
		return editingModelEnvironment;
	}

}
