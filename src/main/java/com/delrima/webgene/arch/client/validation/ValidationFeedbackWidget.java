package com.delrima.webgene.arch.client.validation;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * <p>
 * Composite widget used to display validation error messages
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Sep 22, 2008
 * 
 */
public class ValidationFeedbackWidget extends Composite {

	private VerticalPanel validationMessagesPanel = new VerticalPanel();

	/**
	 * <p>
	 * Create a new instance of ValidationFeedbackWidget.
	 * </p>
	 */
	public ValidationFeedbackWidget() {
		validationMessagesPanel.setVisible(false);
		initWidget(validationMessagesPanel);
	}

	/**
	 * <p>
	 * Populate widget with the errors messages
	 * </p>
	 * 
	 * @param messages
	 *            - Error messages
	 */
	public void setMessages(List<String> messages) {
		validationMessagesPanel.clear();
		for (Iterator<String> i = messages.iterator(); i.hasNext();)
			validationMessagesPanel.add(new Label(i.next()));
	}

	/**
	 * <p>
	 * Add error message to widget
	 * </p>
	 * 
	 * @param message
	 *            - Error message
	 */
	public void addMessage(String message) {
		validationMessagesPanel.add(new Label(message));
	}

}
