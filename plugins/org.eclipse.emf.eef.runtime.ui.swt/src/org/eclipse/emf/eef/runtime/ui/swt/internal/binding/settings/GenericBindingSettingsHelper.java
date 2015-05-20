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
package org.eclipse.emf.eef.runtime.ui.swt.internal.binding.settings;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.internal.editingModel.EditingModelEnvironmentImpl;
import org.eclipse.emf.eef.runtime.ui.swt.EEFRuntimeUISWT;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.util.OSGiHelper;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.ViewsPackage;
import org.eclipse.emf.eef.views.ViewsRepository;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;


/**
 * Generic binding settings helper : init editing model.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
@SuppressWarnings("restriction")
public class GenericBindingSettingsHelper {
	
	
	/**
	 * @param editingDomain EditingDomain
	 * @param modelFile EDitingModel IFile
	 * @param uri Ecore URI
	 * @param initModel 
	 * @param isModel 
	 * @param validURI  
	 * @param progressMonitor
	 * 
	 * @return Ecore EPackage
	 */
	public EPackage initEditingModel(EditingDomain editingDomain, final IFile modelFile, URI uri, boolean validURI, boolean isModel, boolean initModel, IProgressMonitor progressMonitor) {
		try {
			// Create a resource set
			//
			ResourceSet resourceSet = new ResourceSetImpl();

			// Get the URI of the model file.
			//
			URI fileURI = URI.createPlatformResourceURI(modelFile.getFullPath().toString(), true);

			// Create a resource for this file.
			//
			Resource resource = resourceSet.createResource(fileURI);
			
			EPackage ePackage = null;

			// Add the initial model object to the contents.
			//
			if (validURI && uri != null && !Strings.isNullOrEmpty(uri.toString())) {
				URI resourceURI = uri;
				Resource modelResource = null;
				if (!isModel) {
					ResourceSet rs = new ResourceSetImpl();
					modelResource = rs.getResource(resourceURI, true);
				} else {
					modelResource = editingDomain.getResourceSet().getResource(resourceURI, true);
				}

				if (!modelResource.getContents().isEmpty()) {
					EObject root = modelResource.getContents().get(0);

					ePackage = root.eClass().getEPackage();
					if (!isModel && root instanceof EPackage) {
						ePackage = (EPackage) root;
					}
					
					PropertiesEditingModel editingModel = null;
					ViewsRepository viewsRepository = null;
					BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
					
					if (initModel) {
						List<EPackage> packagesToProcesses = listPackagesToProcess(ePackage);
						EEFBindingSettingsProvider bindingSettingsProvider = OSGiHelper.getService(bundleContext, EEFBindingSettingsProvider.class);
						List<PropertiesEditingModel> allEditingModels = Lists.newArrayList();
						for (EPackage pack : packagesToProcesses) {
							if (containsEClass(pack)) {
								EEFBindingSettings<?> bindingSettings = bindingSettingsProvider.getBindingSettings(pack);
								if (!(bindingSettings instanceof GenericBindingSettings)) {
									Iterable<GenericBindingSettings> filter = Iterables.filter(bindingSettingsProvider.getAllBindingSettings(pack), GenericBindingSettings.class);
									Iterator<GenericBindingSettings> it = filter.iterator();
									if (it.hasNext()) {
										bindingSettings = it.next();
									}
								}
								if (bindingSettings instanceof GenericBindingSettings) {
									allEditingModels.add(getEEFDescription(pack, bindingSettings));
								}
							}
						}
						
						editingModel = EditingModelFactory.eINSTANCE.createPropertiesEditingModel();
						viewsRepository = ViewsFactory.eINSTANCE.createViewsRepository();
						for (PropertiesEditingModel propertiesEditingModel : allEditingModels) {
							Collection<EObject> copy = EcoreUtil.copyAll(propertiesEditingModel.eResource().getContents());
							for (EObject eObject : copy) {
								if (eObject instanceof PropertiesEditingModel) {
									PropertiesEditingModel model = (PropertiesEditingModel)eObject;
									editingModel.getBindings().addAll(model.getBindings());
									EList<EObject> involvedModels = model.getInvolvedModels();
									for (EObject involvedModel : involvedModels) {
										if (!editingModel.getInvolvedModels().contains(involvedModel)) {
											editingModel.getInvolvedModels().add(involvedModel);
											editingDomain.getResourceSet().getResources().add(involvedModel.eResource());
										}
									}												
								} else if (eObject instanceof ViewsRepository) {
									viewsRepository.getViews().addAll(((ViewsRepository)eObject).getViews());																						
								}
							}
						}
						EcoreUtil.resolveAll(editingDomain.getResourceSet());
						resource.getContents().add(editingModel);
						resource.getContents().add(viewsRepository);
					} else {
						EMFServiceProvider emfServiceProvider = OSGiHelper.getService(bundleContext, EMFServiceProvider.class);
						Resource propertiesEditingModelResource = GenericBindingSettingsUtil.initPropertiesEditingModel(ePackage, emfServiceProvider, new EditingModelEnvironmentImpl(null));
						editingModel = GenericBindingSettingsUtil.getPropertiesEditionModel(propertiesEditingModelResource);
					}

					if (editingModel == null) {
						EObject rootObject = createEditingModel();
						if (rootObject != null) {
							resource.getContents().add(rootObject);
						}
						rootObject = createViewsRepository();
						if (rootObject != null) {
							resource.getContents().add(rootObject);
						}
					}
				}
			} else {
				EObject rootObject = createEditingModel();
				if (rootObject != null) {
					resource.getContents().add(rootObject);
				}
				rootObject = createViewsRepository();
				if (rootObject != null) {
					resource.getContents().add(rootObject);
				}
			}

			// Save the contents of the resource to the file system.
			//
			Map<Object, Object> options = new HashMap<Object, Object>();
			options.put(XMLResource.OPTION_ENCODING, "UTF-8");
			resource.save(options);
			return ePackage;

		} catch (Exception exception) {
			EEFRuntimeUISWT.INSTANCE.log(exception);
		} finally {
			progressMonitor.done();
		}
		return null;
	}
	
	/**
	 * @param ePackage EPackage
	 * @param bindingSettings EEFBindingSettings
	 */
	public PropertiesEditingModel getEEFDescription(EPackage ePackage, EEFBindingSettings<?> bindingSettings) {
		for (EClassifier content : ePackage.getEClassifiers()) {
			if (content instanceof EClass) {
				((GenericBindingSettings) bindingSettings).getEEFDescription((EClass) content);				
			}
		}
		return bindingSettings.getEditingModel(ePackage);
	}


	
	/**
	 * @param ePackage
	 * @return 
	 */
	private List<EPackage> listPackagesToProcess(EPackage ePackage) {
		List<EPackage> packagesToProcess = Lists.newArrayList();
		packagesToProcess.add(ePackage);
		for (EPackage subPackage : ePackage.getESubpackages()) {
			packagesToProcess.addAll(listPackagesToProcess(subPackage));
		}
		return packagesToProcess;
	}
	
	/**
	 * Create a new {@link PropertiesEditingModel}.
	 * 
	 * @generated NOT
	 */
	protected EObject createEditingModel() {
		return EditingModelFactory.eINSTANCE.create(EditingModelPackage.eINSTANCE.getPropertiesEditingModel());
	}

	/**
	 * Create a new {@link ViewsRepository}.
	 * 
	 * @generated NOT
	 */
	protected EObject createViewsRepository() {
		return ViewsFactory.eINSTANCE.create(ViewsPackage.eINSTANCE.getViewsRepository());
	}
	
	protected boolean containsEClass(EPackage ePackage) {
		Iterable<EClass> filter = Iterables.filter(ePackage.getEClassifiers(), EClass.class);
		return !Iterables.isEmpty(filter);
	}

}
