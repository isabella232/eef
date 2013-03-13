/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.widgets.util;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ChoiceOfValuesFilter extends ViewerFilter {

	private final AdapterFactory adapterFactory;
	private final EObject editedElement;
	private final EStructuralFeature feature;
	private final SelectionMode mode;
	
	private Collection<?> choiceOfValues;
	private Collection<Notifier> intermediateChoices;
	
	
	
	/**
	 * @param adapterFactory {@link AdapterFactory} to use for the choice of value.
	 * @param editedElement {@link EObject} to edit.
	 * @param feature edited {@link EStructuralFeature}.
	 */
	public ChoiceOfValuesFilter(AdapterFactory adapterFactory, EObject editedElement, EStructuralFeature feature, SelectionMode mode) {
		this.adapterFactory = adapterFactory;
		this.editedElement = editedElement;
		this.feature = feature;
		this.mode = mode;
		IItemPropertySource propertySource = (IItemPropertySource) this.adapterFactory.adapt(this.editedElement, IItemPropertySource.class);
		if (propertySource != null) {
			IItemPropertyDescriptor descriptor = propertySource.getPropertyDescriptor(this.editedElement, this.feature);
			choiceOfValues = descriptor.getChoiceOfValues(this.editedElement);
			intermediateChoices = Lists.newArrayList();
			for (Object object : choiceOfValues) {
				if (object instanceof EObject) {
					populateIntermediateChoices(intermediateChoices, (EObject)object);
				}
			}
		}
	}

	private void populateIntermediateChoices(Collection<Notifier> intermediateChoices, EObject object) {
		EObject container = object.eContainer();
		while (container != null) {
			intermediateChoices.add(container);
			container = container.eContainer();
		}
		if (object.eResource() != null) {
			intermediateChoices.add(object.eResource());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return choiceOfValues.contains(element) 
				|| ((mode == SelectionMode.TREE) && (intermediateChoices.contains(element)));
	}

}
