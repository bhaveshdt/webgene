package com.delrima.webgene.arch.client.utils;

import java.util.Date;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * <p>
 * Common class providing getValue() and setValue() methods to be used during validation and data-binding
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Sep 22, 2008
 * 
 */
public class UIUtils {

	public static Date getValue(DateBox db) {
		return db.getDatePicker().getValue();
	}

	public static void setValue(DateBox db, Date date) {
		db.getDatePicker().setValue(date);
	}

	public static String getValue(Label lb) {
		return lb.getText();
	}

	public static Boolean getValue(CheckBox lb) {
		return lb.getValue();
	}

	public static String getValue(TextBox lb) {
		return lb.getText();
	}

	public static String getValue(ListBox lb) {
		return lb.getItemText(lb.getSelectedIndex());
	}

	public static void setValue(ListBox lb, Object iValue) {
		if (lb.getItemCount() > 0) {
			String value = String.valueOf(iValue);
			int listBoxIndex = 0;
			for (int i = 0; i < lb.getItemCount(); i++) {
				listBoxIndex = lb.getItemText(i).equals(value) ? i : 0;
			}
			lb.setItemSelected(listBoxIndex, true);
		}
	}

	public static void setValue(HTML html, String htmlContent) {
		html.setHTML(htmlContent);
	}

}
