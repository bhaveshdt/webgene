package com.delrima.webgene.arch.client.utils;

import java.util.logging.Logger;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

/**
 * <code><B>WidgetUtils<code><B>
 * <p>
 * Common code used to create/organize widgets
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Oct 24, 2008
 */
public class WidgetUtils {

	private static Logger sLogger = GWTFrameworkLogger.getCustomLogger(WidgetUtils.class);

	public static void addStylesToWidget(UIObject widget, String... styles) {
		if (styles == null || styles.length == 0) {
			return;
		}

		for (String style : styles) {
			try {
				if (style != null && style.trim().length() > 0) {
					widget.addStyleName(style);
				}
			} catch (Throwable e) {
				sLogger.info("Error adding style to widget: ('" + style + "')");
			}
		}
	}

	public static Anchor createFocusableHiddenAnchor() {
		Anchor anchor = new Anchor("", "#");
		anchor.setStyleName("focus-here-anchor");
		return anchor;
	}

	public static Hidden createHidden(String name, String value) {
		Hidden hidden = new Hidden(name, value);
		return hidden;
	}

	public static Label createLabel(String text, String... styles) {
		Label l = new Label(text);
		addStylesToWidget(l, styles);
		return l;
	}

	public static Panel createFlowPanel() {
		return new FlowPanel();
	}

	public static FocusPanel createFocusPanel(Widget child) {
		FocusPanel f = createFocusPanel();
		f.add(child);
		return f;
	}

	public static FocusPanel createFocusPanel() {
		FocusPanel f = new FocusPanel();
		f.setStyleName("arch-FocusPanel");
		return f;
	}

	/**
	 * Return a styled flow panel
	 * 
	 * @param style
	 * @return
	 */
	public static Panel createFlowPanel(String... style) {
		Panel p = new FlowPanel();
		addStylesToWidget(p, style);
		return p;
	}

	public static Widget getEagerInstance(LazyPanel widget) {
		widget.setVisible(true);
		return widget;
	}

	/**
	 * <p>
	 * Provide a panel with the label and Widget laid out horizontally
	 * </p>
	 * 
	 * @param label
	 * @param widget
	 * @return
	 */
	public static Panel getHorizontallyLabeledWidget(String label, Widget widget) {
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(new Label(label));
		hp.add(widget);
		return hp;
	}

	/**
	 * <p>
	 * Provide a panel with the label and Widget laid out vertically
	 * </p>
	 * 
	 * @param label
	 * @param widget
	 * @return
	 */
	public static Panel getVerticallyLabeledWidget(String label, Widget widget) {
		VerticalPanel vp = new VerticalPanel();
		vp.add(new Label(label));
		vp.add(widget);
		return vp;
	}

	/**
	 * <p>
	 * Provide a panel with all widget laid out horizontally
	 * </p>
	 * 
	 * @param variableWidget
	 * @return
	 */
	public static Panel getHorizontalPanelWithWidgets(String style, Widget... widgets) {
		HorizontalPanel hp = new HorizontalPanel();
		addWidgetsToPanel(hp, widgets);
		if (StringUtils.isSet(style)) {
			addStylesToWidget(hp, style);
		}
		return hp;
	}

	/**
	 * <p>
	 * Provide a panel with all widget laid out vertically
	 * </p>
	 * 
	 * @param variableWidget
	 * @return
	 */
	public static Panel getVerticalPanelWithWidgets(String style, Widget... widgets) {
		VerticalPanel vp = new VerticalPanel();
		addWidgetsToPanel(vp, widgets);
		if (StringUtils.isSet(style)) {
			addStylesToWidget(vp, style);
		}
		return vp;
	}

	/**
	 * <p>
	 * Provide a panel with all widgets laid out in a flow
	 * </p>
	 * 
	 * @param variableWidgets
	 * @return
	 */
	public static Panel getFlowPanelWithWidgets(Widget... widgets) {
		return getFlowPanelWithWidgets("", widgets);
	}

	public static Panel getFlowPanelWithWidgets(String style, Widget... widgets) {
		Panel fp = new FlowPanel();
		addWidgetsToPanel(fp, widgets);
		if (style.trim() != "")
			addStylesToWidget(fp, style);
		return fp;
	}

	private static void addWidgetsToPanel(Panel p, Widget... widgets) {
		for (int i = 0; i < widgets.length; i++) {
			p.add(widgets[i]);
		}
	}

	public static void addStylesToWidget(Widget widget, String... styles) {
		for (int i = 0; i < styles.length; i++) {
			widget.addStyleName(styles[i]);
		}
	}

	/**
	 * <p>
	 * Populate a ListBox with provided numerical value range
	 * </p>
	 * 
	 * @param lb
	 * @param start
	 * @param end
	 */
	public static void populateNumericalListBox(ListBox lb, int start, int end) {
		for (int i = start; i <= end; i++) {
			lb.addItem(String.valueOf(i), String.valueOf(i));
		}
	}

	public static DatePicker getDatePicker() {
		DatePicker dp = new DatePicker();
		dp.setVisible(false);
		return dp;
	}

	public static DateBox getDateBox() {
		DateBox db = new DateBox();
		return db;
	}
}
