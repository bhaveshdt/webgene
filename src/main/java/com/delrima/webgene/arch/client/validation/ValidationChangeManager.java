package com.delrima.webgene.arch.client.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.delrima.webgene.arch.client.utils.GWTFrameworkLogger;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * <code><B>ValidationManager</code></b>
 * <p>
 * This manager notifies any registered listeners of changes tot he validation feedback panel. If change occurs, listeners can execute code accordingly
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since May 8, 2009
 * 
 */
@SuppressWarnings("deprecation")
public class ValidationChangeManager {

	private static Logger sLogger = GWTFrameworkLogger.getCustomLogger(ValidationChangeManager.class);

	private static List<ChangeListener> changeListenerCollection;

	public static void addChangeListener(ChangeListener listener) {
		if (changeListenerCollection == null) {
			changeListenerCollection = new ArrayList<ChangeListener>();
		}
		changeListenerCollection.add(listener);
	}

	public static void fireOnValidationChange(Widget sender) {
		if (changeListenerCollection != null) {
			for (final ChangeListener listener : changeListenerCollection) {
				sLogger.fine("Firing validation onChange event");
				listener.onChange(sender);
			}
		}
	}

}
