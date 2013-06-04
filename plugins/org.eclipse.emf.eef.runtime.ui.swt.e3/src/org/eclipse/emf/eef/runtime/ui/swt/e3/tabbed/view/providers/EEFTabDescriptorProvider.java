/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.tabbed.view.providers;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.services.bindingSettings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.ui.adapters.SemanticAdapter;
import org.eclipse.emf.eef.runtime.ui.swt.e3.E3EEFRuntimeUIPlatformPlugin;
import org.eclipse.emf.eef.runtime.ui.swt.e3.tabbed.view.section.SectionPropertiesEditingView;
import org.eclipse.emf.eef.views.View;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractSectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.AbstractTabDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptorProvider;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFTabDescriptorProvider implements ITabDescriptorProvider {

	private EClassBinding binding;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ITabDescriptorProvider#getTabDescriptors(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public ITabDescriptor[] getTabDescriptors(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) selection).getFirstElement();
			if (firstElement instanceof EObject) {
				EObject editedEObject = (EObject)firstElement;
				EEFBindingSettings bindingSettings = E3EEFRuntimeUIPlatformPlugin.getPlugin().getBindingSettingsProvider().getBindingSettings(editedEObject.eClass().getEPackage());
				if (bindingSettings != null) {
					PropertiesEditingModel editingModel = bindingSettings.getEditingModel(editedEObject);
					if (editingModel != null) {
						binding = editingModel.binding(editedEObject);
						if (binding != null) {
							List<ITabDescriptor> result = Lists.newArrayList();
							for (org.eclipse.emf.eef.runtime.editingModel.View view : binding.getViews()) {
								if (view instanceof EObjectView && ((EObjectView) view).getDefinition() instanceof View) {
									result.add(new EEFTabDescriptor((View) ((EObjectView) view).getDefinition()));
								}
							}
							return result.toArray(new ITabDescriptor[result.size()]);
						}
					}
				}
			}
		}
		return new ITabDescriptor[0];
	}
	
	public final class EEFTabDescriptor extends AbstractTabDescriptor {

		private View descriptor;
		
		/**
		 * @param descriptor
		 */
		public EEFTabDescriptor(View descriptor) {
			this.descriptor = descriptor;
			initSectionDescriptors();
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.ui.views.properties.tabbed.ITabDescriptor#getCategory()
		 */
		public String getCategory() {
			return "extended";
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.ui.views.properties.tabbed.ITabDescriptor#getId()
		 */
		public String getId() {
			return descriptor.getName();
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.ui.views.properties.tabbed.ITabDescriptor#getLabel()
		 */
		public String getLabel() {
			return descriptor.getName();
		}
		
		@SuppressWarnings("unchecked")
		private void initSectionDescriptors() {
			getSectionDescriptors().add(new EEFSectionDescriptor());			
		}
		
		public final class EEFSectionDescriptor extends AbstractSectionDescriptor {
			
			private ISection sectionClass;

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.ui.views.properties.tabbed.ISectionDescriptor#getId()
			 */
			public String getId() {
				return "org.eclipse.emf.eef.properties.sections." + EEFTabDescriptor.this.descriptor.getName();
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.ui.views.properties.tabbed.ISectionDescriptor#getSectionClass()
			 * TODO: check this stateful aspect...
			 */
			public ISection getSectionClass() {
				if (sectionClass == null) {
					sectionClass = new SectionPropertiesEditingView();
				}
				return sectionClass;
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.ui.views.properties.tabbed.ISectionDescriptor#getTargetTab()
			 */
			public String getTargetTab() {
				return EEFTabDescriptor.this.getId();
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.ui.views.properties.tabbed.AbstractSectionDescriptor#getFilter()
			 */
			@Override
			public IFilter getFilter() {
				return new IFilter() {
					
					public boolean select(Object toTest) {
						EObject resolveSemanticObject = resolveSemanticObject(toTest);
						if (resolveSemanticObject != null) {
							EMFService emfService = E3EEFRuntimeUIPlatformPlugin.getPlugin().getEMFServiceProvider().getEMFService(resolveSemanticObject.eClass().getEPackage());
							return emfService.equals(binding.getEClass(), resolveSemanticObject.eClass());
						}
						return false;
					}
					/**
					 * This method analyze an input to exact the EObject to edit.
					 * First we try to adapt this object in {@link SemanticAdapter}. If this can't be done, 
					 * we check if this object is an {@link EObject}. Finally, if this object isn't an
					 * {@link EObject}, we try to adapt it in EObject.
					 * @param object element to test
					 * @return the EObject to edit with EEF.
					 */
					protected EObject resolveSemanticObject(Object object) {
						IAdaptable adaptable = null;
						if (object instanceof IAdaptable) {
							adaptable = (IAdaptable)object;
						}
						if (adaptable != null) {
							if (adaptable.getAdapter(SemanticAdapter.class) != null) {
								SemanticAdapter semanticAdapter = (SemanticAdapter)adaptable.getAdapter(SemanticAdapter.class);
								return semanticAdapter.getEObject();
							} 
						}
						if (object instanceof EObject) {
							return (EObject)object;
						} 
						if (adaptable != null) {
							if (adaptable.getAdapter(EObject.class) != null) {
								return (EObject)adaptable.getAdapter(EObject.class);
							}
						}
						return null;
					}
				};
				
			}
			
			
		}

	}

}
