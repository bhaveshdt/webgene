package com.delrima.webgene.arch.client.base;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.delrima.webgene.arch.client.utils.ClientSession;
import com.delrima.webgene.arch.client.utils.GWTClientUtils;
import com.delrima.webgene.arch.client.utils.GWTFrameworkLogger;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * <code><B>AbstractEntryPoint<code><B>
 * <p>
 * Extend your module EntryPoint with this abstract class for automatic
 * provision of the history mechanism. In order to provide views for a
 * particular module, provide your views via an xml file located in  
 * the same package under resources folder
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Sep 25, 2008
 */
public abstract class AbstractEntryPoint implements EntryPoint, ValueChangeHandler<String> {

	private static Logger sLogger = GWTFrameworkLogger.getCustomLogger(AbstractEntryPoint.class);

	protected Map<String, AbstractView> viewInstances = new LinkedHashMap<String, AbstractView>();
	/**
	 * viewContainer - FlowPanel Container widget for the views
	 */
	private final transient Panel viewContainer = new FlowPanel();

	protected abstract void populateViewInstances();

	/**
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public final void onModuleLoad() {

		RootPanel.get().add(viewContainer);
		populateViewInstances();

		// store navigation tokens
		String[] tokensArray = new String[viewInstances.size()];
		viewInstances.keySet().toArray(tokensArray);
		ClientSession.getFrameworkContextBean().setApplicationViewTokens(tokensArray);

		History.addValueChangeHandler(AbstractEntryPoint.this);

		doLoadModule();

		History.fireCurrentHistoryState();

	}

	/**
	 * <p>
	 * The entry point of loading module, all sub class should implement it.
	 * </p>
	 */
	protected void doLoadModule() {
		History.fireCurrentHistoryState();
	}

	/**
	 * This method is called whenever the application's history changes.
	 * 
	 * @param historyToken
	 *            the histrory token
	 */
	public final void onValueChange(ValueChangeEvent<String> event) {
		String viewToken = GWTClientUtils.getCurrentViewName();
		manageViewVisibility(viewToken.toLowerCase());
	}

	private AbstractView ensureView(String viewName) {
		AbstractView view = viewInstances.get(viewName.toUpperCase());
		if (view != null) {
			try {
				view.setVisible(true);
				viewContainer.add(view);
				sLogger.fine("Instantiated view: " + view.getClass().getName());
			} catch (Throwable t) {
				sLogger.log(Level.SEVERE, "Error instantiating view in ensureView()", t);
			}
		}
		return view;
	}

	/**
	 * <p>
	 * Manage the visibility for the various views that are part of this module
	 * </p>
	 * 
	 * @param token
	 *            - name of the view to be made visible
	 */
	private void manageViewVisibility(final String token) {

		// hide all views
		for (final AbstractView view : viewInstances.values()) {
			view.setVisible(false);
		}

		AbstractView view = ensureView(token);
		if (view != null) {
			// instantiate, add and show theview
			view.setVisible(true);

			String viewToken = GWTClientUtils.getCurrentViewName();

			sLogger.fine("Execute View.process()");
			try {
				view.process();
			} catch (Throwable t) {
				sLogger.log(Level.SEVERE, "Error executing view.process();", t);
			}

			// keep track of the views visited by the user
			ClientSession.getFrameworkContextBean().getNavigationHistory().add(viewToken);

		}

	}
}
