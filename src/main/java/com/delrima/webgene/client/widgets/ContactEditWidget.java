package com.delrima.webgene.client.widgets;

// Generated Feb 23, 2008 11:17:51 PM by Hibernate Tools 3.2.0.b11

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.delrima.webgene.arch.client.widgets.extensions.ExtendedListBox;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Contact generated by hbm2java
 */
public class ContactEditWidget extends Composite {

	private VerticalPanel mainPanel = new VerticalPanel();

	private final Label id = new Label();
	private final TextBox name = new TextBox();
	private final TextBox street1 = new TextBox();
	private final TextBox street2 = new TextBox();
	private final TextBox city = new TextBox();
	private final ExtendedListBox state = new ExtendedListBox();
	{
		state.addItem("Select State", "");
	}
	private final TextBox zip = new TextBox();
	private final ExtendedListBox country = new ExtendedListBox();
	{
		country.addItem("Select Country", "");
	}
	private final TextBox homephone = new TextBox();
	private final TextBox mobilephone = new TextBox();
	private final TextBox workphone = new TextBox();
	private final TextBox fax = new TextBox();
	private final TextBox email = new TextBox();
	private final TextBox im = new TextBox();
	private final TextBox website = new TextBox();

	private Button submit = new Button("Submit");

	private Map<String, Widget> widgetMap = new LinkedHashMap<String, Widget>();
	{
		widgetMap.put("Id", id);
		widgetMap.put("Type", name);
		widgetMap.put("Address 1", street1);
		widgetMap.put("Address 2", street2);
		widgetMap.put("City", city);
		widgetMap.put("State", state);
		widgetMap.put("Zip", zip);
		widgetMap.put("Country", country);
		widgetMap.put("Home", homephone);
		widgetMap.put("Mobile", mobilephone);
		widgetMap.put("Work", workphone);
		widgetMap.put("Fax", fax);
		widgetMap.put("Email", email);
		widgetMap.put("IM", im);
		widgetMap.put("Site", website);
	};

	private Grid formGrid = new Grid(widgetMap.size(), 2);

	public ContactEditWidget() {
		initWidget(mainPanel);

		mainPanel.add(new Label("Address Information"));

		int c = 0;
		Entry<String, Widget> entry = null;
		for (Iterator<Entry<String, Widget>> i = widgetMap.entrySet().iterator(); i.hasNext(); c++) {
			entry = i.next();
			formGrid.setWidget(c, 0, new Label(entry.getKey()));
			formGrid.setWidget(c, 1, entry.getValue());
		}
		mainPanel.add(formGrid);
	}

	public HasValue<String> getCity() {
		return city;
	}

	public HasValue<String> getCountry() {
		return country;
	}

	public HasValue<String> getEmail() {
		return email;
	}

	public HasValue<String> getFax() {
		return fax;
	}

	public HasValue<String> getHomephone() {
		return homephone;
	}

	public HasText getId() {
		return id;
	}

	public HasValue<String> getIm() {
		return im;
	}

	public HasValue<String> getMobilephone() {
		return mobilephone;
	}

	public HasValue<String> getName() {
		return name;
	}

	public HasValue<String> getState() {
		return state;
	}

	public HasValue<String> getStreet1() {
		return street1;
	}

	public HasValue<String> getStreet2() {
		return street2;
	}

	public HasClickHandlers getSubmit() {
		return submit;
	}

	public HasValue<String> getWebsite() {
		return website;
	}

	public HasValue<String> getWorkphone() {
		return workphone;
	}

	public HasValue<String> getZip() {
		return zip;
	}

}
