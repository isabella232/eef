/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.ui.swt.internal.widgets;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.emf.eef.runtime.ui.swt.EEFRuntimeUISWT;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class EEFSelectionDialog extends TrayDialog {

	private ImageManager imageManager;

	private String title;
	private TreeViewer selectionViewer;
	private boolean multi;
	private Menu addModelMenu;

	private AdapterFactory adapterFactory;
	private IContentProvider contentProvider;
	private IBaseLabelProvider labelProvider;
	private Object input;
	private Object selection;
	private Collection<ViewerFilter> filters;
	private EditUIProvidersFactory providersFactory;

	private Text urisToLoad;

	/**
	 * @param parent
	 */
	public EEFSelectionDialog(Shell parent, boolean multi) {
		super(parent);
		this.multi = multi;
		this.filters = Lists.newArrayList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (title != null) {
			newShell.setText(title);
		}
		newShell.setSize(UIConstants.EEF_SELECTION_DIALOG_WIDTH, UIConstants.EEF_SELECTION_DIALOG_HEIGHT);
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param input
	 *            the input to set
	 */
	public void setInput(Object input) {
		this.input = input;
	}

	/**
	 * @return the input
	 */
	public Object getInput() {
		return input;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	protected boolean isResizable() {
		return true;
	}

	/**
	 * @return the selectionViewer
	 */
	protected final TreeViewer getSelectionViewer() {
		return selectionViewer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite control = new Composite(parent, SWT.NONE);
		control.setLayoutData(new GridData(GridData.FILL_BOTH));
		control.setLayout(new GridLayout());
		Label message = new Label(control, SWT.NONE);
		message.setText("Select the element(s) to process:");
		int style = SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;
		if (multi) {
			style |= SWT.MULTI;
		}
		selectionViewer = createSelectionViewer(control, style);
		selectionViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

		for (ViewerFilter filter : filters) {
			selectionViewer.addFilter(filter);
		}

		selectionViewer.setContentProvider(getContentProvider());
		selectionViewer.setLabelProvider(getLabelProvider());
		selectionViewer.setInput(input);
		selectionViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection sSel = (StructuredSelection) selectionViewer.getSelection();
				if (sSel.size() == 0) {
					EEFSelectionDialog.this.selection = null;
				} else if (sSel.size() == 1) {
					EEFSelectionDialog.this.selection = sSel.getFirstElement();
				} else {
					EEFSelectionDialog.this.selection = sSel.toList();
				}
			}
		});
		selectionViewer.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				okPressed();
			}
		});
		Group modelLoading = new Group(control, SWT.BORDER);
		modelLoading.setText("Model loading");
		modelLoading.setLayout(new GridLayout(3, false));
		modelLoading.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		urisToLoad = new Text(modelLoading, SWT.BORDER);
		urisToLoad.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		final Button addModel = new Button(modelLoading, SWT.PUSH);
		if (imageManager != null) {
			addModel.setImage(imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "Add"));
		} else {
			addModel.setText("add");
		}
		addModel.setToolTipText("Load model...");
		addModel.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (addModelMenu != null && !addModelMenu.isDisposed()) {
					addModelMenu.dispose();
				}
				addModelMenu = new Menu(e.display.getActiveShell(), SWT.POP_UP);
				Menu menu = addModelMenu;
				buildLoadModelMenu(menu);

				Rectangle rect = addModel.getBounds();
				Point pt = new Point(rect.x, rect.y + rect.height);
				pt = addModel.getParent().toDisplay(pt);
				addModelMenu.setLocation(pt.x, pt.y);
				addModelMenu.setVisible(true);
			}
		});
		final Button loadModel = new Button(modelLoading, SWT.PUSH);
		if (imageManager != null) {
			loadModel.setImage(imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "Load"));
		} else {
			loadModel.setText("...");
		}
		loadModel.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				loadModels();
			}

		});
		return control;
	}

	/**
	 * Creates the selection viewer.
	 * 
	 * @param parent
	 *            the parent control.
	 * @param style
	 *            the viewer style.
	 * @return the created viewer.
	 */
	protected TreeViewer createSelectionViewer(Composite parent, int style) {
		return new TreeViewer(parent, style);
	}

	/**
	 * @param adapterFactory
	 *            the adapterFactory to set
	 */
	public void setAdapterFactory(AdapterFactory adapterFactory) {
		this.adapterFactory = adapterFactory;
	}

	/**
	 * @param providersFactory
	 *            the {@link EditUIProvidersFactory} to use in this dialog.
	 */
	public void setEditUIProvidersFactory(EditUIProvidersFactory providersFactory) {
		this.providersFactory = providersFactory;
	}

	/**
	 * @param imageManager
	 *            the imageManager to set
	 */
	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	/**
	 * @return the contentProvider TODO: need a real improvement
	 */
	public IContentProvider getContentProvider() {
		if (contentProvider == null && providersFactory != null) {
			contentProvider = providersFactory.createContentProvider(adapterFactory);
		}
		return contentProvider;
	}

	/**
	 * @param contentProvider
	 *            the contentProvider to set
	 */
	public void setContentProvider(IContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	/**
	 * @return the labelProvider
	 */
	public IBaseLabelProvider getLabelProvider() {
		if (labelProvider == null) {
			labelProvider = providersFactory.createLabelProvider(adapterFactory);
		}
		return labelProvider;
	}

	/**
	 * @param labelProvider
	 *            the labelProvider to set
	 */
	public void setLabelProvider(IBaseLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	/**
	 * @return the selection
	 */
	public Object getSelection() {
		return selection;
	}

	/**
	 * @param choiceOfValuesFilter
	 */
	public void addFilter(ViewerFilter filter) {
		this.filters.add(filter);
	}

	/**
	 * Adds a URI to the list of URI to load.
	 * 
	 * @param uri
	 *            the {@link URI} to add.
	 */
	protected final void addURIsToLoad(String uri) {
		StringBuilder builder = new StringBuilder(urisToLoad.getText());
		if (builder.length() > 0) {
			builder.append(' ');
		}
		if (uri.indexOf(' ') > 0) {
			builder.append("\"");
		}
		builder.append(uri);
		if (uri.indexOf(' ') > 0) {
			builder.append("\"");
		}
		urisToLoad.setText(builder.toString());
	}

	/**
	 * Builds the "Load Model" menu.
	 * 
	 * @param menu
	 *            the "Load Model" {@link Menu}.
	 */
	protected void buildLoadModelMenu(Menu menu) {
		MenuItem filesystemItem = new MenuItem(menu, SWT.PUSH);
		filesystemItem.setText("From filesystem...");
		filesystemItem.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(getShell());
				String pathName = dialog.open();
				if (pathName != null) {
					URI uri = URI.createFileURI(pathName);
					addURIsToLoad(uri.toString());
				}
			}
		});
	}

	private void loadModels() {
		String urisText = urisToLoad.getText();
		if (urisText != null) {
			urisText = urisText.trim();
			if (urisText.length() > 0) {
				List<URI> uris = extractURIs(urisText);
				Object dialogInput = getInput();
				if (dialogInput instanceof ResourceSet) {
					for (URI uri : uris) {
						((ResourceSet) dialogInput).getResource(uri, true);
					}
					urisToLoad.setText("");
				}
			}
		}
	}

	/*
	 * TODO: this method should be externalized in a service to be tested!
	 */
	private List<URI> extractURIs(String urisText) {
		List<URI> result = Lists.newArrayList();
		boolean quoteOpen = false;
		String currentString = "";
		for (int i = 0; i < urisText.length(); i++) {
			char uriChar = urisText.charAt(i);
			switch (uriChar) {
			case '\"':
				if (quoteOpen) {
					if (currentString.length() > 0) {
						result.add(URI.createURI(currentString));
					}
					currentString = "";
					quoteOpen = false;
				} else {
					quoteOpen = true;
					currentString = "";
				}
				break;
			case ' ':
				if (!quoteOpen) {
					if (currentString.length() > 0) {
						result.add(URI.createURI(currentString));
					}
					currentString = "";
				} else {
					currentString += uriChar;
				}
				break;
			default:
				currentString += uriChar;
				break;
			}
		}
		if (currentString.length() > 0) {
			result.add(URI.createURI(currentString));
		}
		return result;
	}

}
