/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.widgets.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.INotifyChangedListener;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class AdapterFactoryFeatureObservableList<T> implements ObservableList<T> {

	private static final String CHANGES_THROUGH_MODEL = "An AdapterFactoryObservableList cannot be manipulated directly. Changes must be made via the model.";
	protected AdapterFactory adapterFactory;
	protected Object root;
	/* package */ObservableList<T> elements = FXCollections.observableArrayList();

	/**
	 * @param adapterFactory
	 * @param root
	 * @param feature
	 */
	public AdapterFactoryFeatureObservableList(AdapterFactory adapterFactory, final Object root, final EStructuralFeature feature) {
		super();

		if (adapterFactory == null)
			throw new IllegalArgumentException("AdapterFactory must not be null.");

		this.adapterFactory = adapterFactory;
		this.root = root;
		updateContents(root, feature);
		

		if (root instanceof Notifier) {
			AdapterImpl adapter = new AdapterImpl() {

				@Override
				public void notifyChanged(Notification msg) {
					updateContents(root, feature);
				}
			};

			((Notifier) root).eAdapters().add(adapter);
		} else if (root instanceof IChangeNotifier) {
			((IChangeNotifier) root).addListener(new INotifyChangedListener() {

				public void notifyChanged(Notification notification) {
					updateContents(root, feature);
				}
				
			});
		}

	}

	@SuppressWarnings("unchecked")
	private void updateContents(final Object root, EStructuralFeature feature) {
		if (root instanceof EObject) {
			Object value = ((EObject) root).eGet(feature);
			if (value instanceof Collection) {
				elements.addAll((Collection<? extends T>) value);
			} else {
				elements.add((T) value);
			}
		} else {
			throw new IllegalArgumentException("Provided root object cannot be treated.");
		}
	}	
	
	public boolean add(Object e) {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

	
	public void add(int index, Object element) {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

	
	public boolean addAll(Collection<? extends T> c) {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

	
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

	
	public void clear() {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

	
	public boolean contains(Object o) {
		return elements.contains(o);
	}

	
	public boolean containsAll(Collection<?> c) {
		return elements.containsAll(c);
	}

	
	public T get(int index) {
		return elements.get(index);
	}

	
	public int indexOf(Object o) {
		return elements.indexOf(o);
	}

	
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	
	public Iterator<T> iterator() {
		return elements.iterator();
	}

	
	public int lastIndexOf(Object o) {
		return elements.lastIndexOf(o);
	}

	
	public ListIterator<T> listIterator() {
		return elements.listIterator();
	}

	
	public ListIterator<T> listIterator(int index) {
		return elements.listIterator(index);
	}

	
	public boolean remove(Object o) {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

	
	public T remove(int index) {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

	
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

	
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

	
	public T set(int index, Object element) {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

	
	public int size() {
		return elements.size();
	}

	
	public List<T> subList(int fromIndex, int toIndex) {
		return elements.subList(fromIndex, toIndex);
	}

	
	public Object[] toArray() {
		return elements.toArray();
	}

	
	public <A> A[] toArray(A[] a) {
		return elements.toArray(a);
	}

	
	public void addListener(InvalidationListener listener) {
		elements.addListener(listener);
	}

	
	public void removeListener(InvalidationListener listener) {
		elements.removeListener(listener);
	}

	
	public boolean addAll(Object... arg0) {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

	
	public void addListener(ListChangeListener<? super T> listener) {
		elements.addListener(listener);
	}

	
	public void remove(int arg0, int arg1) {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

	
	public boolean removeAll(Object... arg0) {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

	
	public void removeListener(ListChangeListener<? super T> listener) {
		elements.removeListener(listener);
	}

	
	public boolean retainAll(Object... arg0) {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

	
	public boolean setAll(Object... arg0) {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

	
	public boolean setAll(Collection<? extends T> arg0) {
		throw new UnsupportedOperationException(CHANGES_THROUGH_MODEL);
	}

}