package com.delrima.webgene.arch.client.widgets.extensions;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;

/**
 * <code><B>ListPopulatorWidget</code></b>
 * <p>
 * Provides convenience methods getSelectedValue() and setSelectedValue()
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Nov 7, 2008
 */
public class ExtendedListBox extends ListBox implements HasValue<String> {

    public final String getSelectedValue() {
        return getValue(getSelectedIndex());
    }

    public final void setSelectedValue(String value) {
        if (getItemCount() > 0) {
            int i = 0;
            for (i = 0; i < getItemCount(); i++) {
                if (getValue(i).equals(value)) {
                    setItemSelected(i, true);
                    break;
                }
            }

        }
    }

    public String getValue() {
        return this.getSelectedValue();
    }

    public void setValue(String value) {
        this.setSelectedValue(value);
    }

    public void setValue(String value, boolean arg1) {
        this.setSelectedValue(value);
    }

    @Deprecated
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> arg0) {
        return null;
    }
}
