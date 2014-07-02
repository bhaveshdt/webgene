package com.delrima.webgene.arch.client.base;

import com.delrima.webgene.arch.client.interfaces.View;
import com.delrima.webgene.arch.client.utils.WidgetUtils;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * <code><B>AbstractViewWidget<code><B>
 * <p>
 * Abstract class to be used for all views specified
 * in the module's "<module-name>Config.xml" file
 * </p>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Sep 22, 2008
 * @param <Model>
 */
public abstract class AbstractView extends LazyPanel implements View {

    private final Panel initPanel;
    protected final Panel viewPanel;

    public AbstractView() {
        initPanel = WidgetUtils.createFlowPanel();
        viewPanel = WidgetUtils.createFlowPanel();
        initPanel.add(viewPanel);
    }

    /**
     * <p>
     * See {@link #setviewPanel(Panel)}
     * </p>
     * 
     * @return Returns the viewPanel.
     */
    public Panel getViewPanel() {
        return viewPanel;
    }

}
